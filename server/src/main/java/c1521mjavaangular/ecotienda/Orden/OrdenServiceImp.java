package c1521mjavaangular.ecotienda.Orden;

import c1521mjavaangular.ecotienda.Categoria.Categorias;
import c1521mjavaangular.ecotienda.OrdenDetalles.OrdenDetalles;
import c1521mjavaangular.ecotienda.OrdenDetalles.OrdenDetallesRepository;
import c1521mjavaangular.ecotienda.Producto.ProductoDto;
import c1521mjavaangular.ecotienda.Producto.ProductoRepository;
import c1521mjavaangular.ecotienda.Producto.Productos;
import c1521mjavaangular.ecotienda.Usuarios.Usuarios;
import c1521mjavaangular.ecotienda.Usuarios.UsuariosDto;
import c1521mjavaangular.ecotienda.Usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdenServiceImp implements IOrdenService{
    @Autowired
    private OrdenRepository ordenRepository;
    @Autowired
    private OrdenDetallesRepository ordenDetallesRepository;
    @Autowired
    private UsuariosRepository usuarioRepository;
    @Autowired
    private ProductoRepository productoRepository;


    @Override
    public void crearOrden(OrdenDto ordenDto){
        Optional<Usuarios> usuarioExistenteOptional = usuarioRepository.findById(ordenDto.getUsuariosDto().getId());
        Orden orden = new Orden();

            if (usuarioExistenteOptional.isPresent()){
                Usuarios usuarioExistente = usuarioExistenteOptional.get();
                orden.setUsuarios(usuarioExistente);
                orden.setPrecioTotal(500.0);

                ordenRepository.save(orden);
                List<OrdenDetalles> ordenDetallesList = getOrdenDetallesList(ordenDto, orden);
                ordenDetallesRepository.saveAll(ordenDetallesList);
            }

    }

    private List<OrdenDetalles> getOrdenDetallesList(OrdenDto ordenDto, Orden orden) {
        int total = 0;
        List<OrdenDetalles> ordenDetallesList = new ArrayList<>();
        for (ProductoDto productosEnOrden: ordenDto.getListaProducto()){
            Productos productos = productoRepository.findById(productosEnOrden.getId()).get();
            total += productos.getPrecio() * productosEnOrden.getCantidad();
            OrdenDetalles ordenDetalles = new OrdenDetalles();
            ordenDetalles.setDireccion(ordenDto.getDireccion());
            ordenDetalles.setCantidad(productosEnOrden.getCantidad());
            ordenDetalles.setPrecioProducto(productos.getPrecio());
            ordenDetalles.setProductos(productos);
            ordenDetalles.setOrden(orden);
            ordenDetalles.setSumaTotal(total);
            ordenDetallesList.add(ordenDetalles);
        }
        return ordenDetallesList;
    }

    @Override
    public Optional<OrdenDto> buscarOrden(Long id) {
        return Optional.ofNullable(ordenRepository.findById(id)
                .stream()
                .map(this::convertEntityToDtoList)
                .toList().get(0));
    }

    @Override
    public void modificarOrden(OrdenDto ordenDto) {
        Optional<Orden> ordenExistenteOptional = ordenRepository.findById(ordenDto.getId());
        Optional<Usuarios> usuarioExistenteOptional = usuarioRepository.findById(ordenDto.usuariosDto.getId());
        List<ProductoDto> productoDtoList = ordenDto.getListaProducto();
        if (ordenExistenteOptional.isPresent() && usuarioExistenteOptional.isPresent() ){
            Orden ordenExistente = ordenExistenteOptional.get();
            Usuarios usuarioExistente = usuarioExistenteOptional.get();
            ordenExistente.setUsuarios(usuarioExistente);

            ordenRepository.save(ordenExistente);

        }

    }

    @Override
    public void eliminarOrden(Long id) {
        ordenRepository.deleteById(id);

    }

    @Override
    public List<OrdenDto> obtenerOrdenes() {
        return ordenRepository.findAll()
                .stream()
                .map(this::convertEntityToDtoList)
                .collect(Collectors.toList());

    }

    public OrdenDto convertEntityToDtoList(Orden orden) {
        OrdenDto ordenDto = new OrdenDto();
        UsuariosDto usuarioDto = new UsuariosDto();
        List<OrdenDetalles> ordenDetallesList = ordenDetallesRepository.findAllByOrdenId(orden.getId());
        List<ProductoDto> productosList = new ArrayList<>();
        usuarioDto.setId(orden.getUsuarios().getId());
        usuarioDto.setDireccion(orden.getUsuarios().getDireccion());
        usuarioDto.setNombre(orden.getUsuarios().getNombre());
        usuarioDto.setEmail(orden.getUsuarios().getEmail());
        ordenDto.setId(orden.getId());
        ordenDto.setUsuariosDto(usuarioDto);
        for (OrdenDetalles ordenDetalles: ordenDetallesList){
            Productos productos = productoRepository.findById(ordenDetalles.getProductos().getId()).get();
            ProductoDto productoDto = new ProductoDto();
            productoDto.setId(ordenDetalles.getProductos().getId());
            productoDto.setCantidad(ordenDetalles.getCantidad());
            productoDto.setPrecio(productos.getPrecio());
            productoDto.setNombre(productos.getNombre());
            productoDto.setCodigo(productos.getCodigo());
            productoDto.setImagen(productos.getImagen());
            productoDto.setCategorias(productos.getCategorias());
            productoDto.setStock(productos.getStock());
            productoDto.setDescripcion(productos.getDescripcion());
            productosList.add(productoDto);
        }
        ordenDto.setListaProducto(productosList);

        return ordenDto;
    }
}

package c1521mjavaangular.ecotienda.Orden;

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
        Optional<Usuarios> usuarioExistenteOptional = usuarioRepository.findById(ordenDto.getId());
        Optional<Productos> productosExistenteOptional = productoRepository.findById(ordenDto.getId());
        if (usuarioExistenteOptional.isPresent() && productosExistenteOptional.isPresent()){
            Usuarios usuarioExistente = usuarioExistenteOptional.get();
            Productos productosExistente = productosExistenteOptional.get();
            Orden orden = new Orden();
            orden.setDetalles(ordenDto.getOrdenDetalles());
            orden.setUsuarios(usuarioExistente);
            List<OrdenDetalles> ordenDetallesList = getOrdenDetalles(ordenDto, productosExistente);
            ordenRepository.save(orden);
            ordenDetallesRepository.saveAll(ordenDetallesList);
        }
    }

    private List<OrdenDetalles> getOrdenDetalles(OrdenDto ordenDto, Productos productosExistente) {
        int total = 0;
        List<OrdenDetalles> ordenDetallesList = new ArrayList<>();
        for (ProductoDto productoDto: ordenDto.getListaProducto()){
            total += productosExistente.getPrecio() * productoDto.getCantidad();
            OrdenDetalles ordenDetalles = new OrdenDetalles();
            ordenDetalles.setDireccion("mi casa");
            ordenDetalles.setCantidad(productoDto.getCantidad());
            ordenDetalles.setPrecioProducto(productosExistente.getPrecio());
            ordenDetalles.setProductos(productosExistente);
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
        if (ordenExistenteOptional.isPresent() && usuarioExistenteOptional.isPresent() ){
            Orden ordenExistente = ordenExistenteOptional.get();
            Usuarios usuarioExistente = usuarioExistenteOptional.get();
            ordenExistente.setUsuarios(usuarioExistente);
            ordenExistente.setDetalles(ordenDto.getOrdenDetalles());
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
        usuarioDto.setId(orden.getUsuarios().getId());
        usuarioDto.setDireccion(orden.getUsuarios().getDireccion());
        usuarioDto.setNombre(orden.getUsuarios().getNombre());
        usuarioDto.setEmail(orden.getUsuarios().getEmail());
        ordenDto.setId(orden.getId());
        ordenDto.setUsuariosDto(usuarioDto);

        return ordenDto;
    }
}

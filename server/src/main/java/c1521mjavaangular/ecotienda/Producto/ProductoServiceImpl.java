package c1521mjavaangular.ecotienda.Producto;


import c1521mjavaangular.ecotienda.Exceptions.ConflictException;
import c1521mjavaangular.ecotienda.Exceptions.RecursoNoEncontradoException;
import c1521mjavaangular.ecotienda.ProductoRating.ProductRating;
import c1521mjavaangular.ecotienda.ProductoRating.ProductRatingDTO;
import c1521mjavaangular.ecotienda.ProductoRating.ProductRatingRepository;
import c1521mjavaangular.ecotienda.Usuarios.Usuarios;
import c1521mjavaangular.ecotienda.Usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private ProductRatingRepository productRatingRepository;

    @Override
    public List<Productos> listarProductos() {
        return this.productoRepository.findAll();

    }

    @Override
    public Productos buscarProducto(Long id) {
        Productos productos = this.productoRepository.findById(id).orElse(null);
        return productos;
    }

    @Override
    public Productos guardarProducto(Productos productos) {
        return this.productoRepository.save(productos);
    }

    @Override
    public Optional<ProductoDto> buscarProductoOptional(Long id) {
        Optional<Productos> optionalProductos = productoRepository.findById(id);
        return optionalProductos.map(this::convertEntityToDtoList);
    }


    @Override
    public void eliminarProducto(Long id) {
        this.productoRepository.deleteById(id);

    }

    @Override
    public void addRating(Long productId, Long usuariosId, int rating, String comment) {
        Productos product = productoRepository.findById(productId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));

        Usuarios usuarios = usuariosRepository.findById(usuariosId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        if (productRatingRepository.existsByProductAndUsuarios(product, usuarios)) {
            throw new ConflictException("Ya has valorado este producto.");
        }

        ProductRating productRating = new ProductRating();
        productRating.setProduct(product);
        productRating.setUsuarios(usuarios);
        productRating.setRating(rating);
        productRating.setComment(comment);
        productRating.setDate(LocalDateTime.now());

        productRatingRepository.save(productRating);
    }

    @Override
    public double getAverageRating(Long productId) {
        List<ProductRating> ratings = productRatingRepository.findByProduct_Id(productId);

        if (ratings.isEmpty()) {
            return 0;
        }

        double sum = ratings.stream().mapToDouble(ProductRating::getRating).sum();
        return sum / ratings.size();
    }

    @Override
    public List<ProductRatingDTO> getProductRatings(Long productId) {
        List<ProductRating> ratings = productRatingRepository.findByProduct_Id(productId);

        return ratings.stream()
                .map(ProductRatingDTO::new)
                .collect(Collectors.toList());
    }


    public ProductoDto convertEntityToDtoList(Productos productos){
        ProductoDto productoDto = new ProductoDto();
        productoDto.setId(productos.getId());
        productoDto.setNombre(productoDto.getNombre());
        productoDto.setPrecio(productoDto.getPrecio());
        productoDto.setStock(productos.getStock());
        productoDto.setCategorias(productos.getCategorias());
        productoDto.setDescripcion(productoDto.getDescripcion());
        productoDto.setCodigo(productos.getCodigo());
        productoDto.setImagen(productoDto.getImagen());
        productoDto.setCantidad(productoDto.getCantidad());
        return  productoDto;
    }

    }

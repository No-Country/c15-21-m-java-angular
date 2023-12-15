package c1521mjavaangular.ecotienda.Usuarios;

import c1521mjavaangular.ecotienda.Producto.Productos;

import java.util.List;

public interface UsuariosService {

    void update(UsuarioUpdateRequest userRequest,Long id) ;
    UsuariosResponse getByUsername(String username);
    UsuariosResponse getByID(Long id);
    void delete(Long id);
    List<UsuariosResponse> findAll();

    void addToFavorites(Long usuariosId, Long productosId);
    void removeFavoriteProduct(Long usuariosId, Long productosId);
    List<Productos> getFavoriteProducts(Long usuariosId);

}

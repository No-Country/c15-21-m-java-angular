package c1521mjavaangular.ecotienda.services;

public interface IUsuariosService {

    void update(UsuarioUpdateRequest userRequest,Long id) ;
    UsuariosResponse getByUsername(String username);
    UsuariosResponse getByID(Long id);
    void delete(Long id);
}

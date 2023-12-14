package c1521mjavaangular.ecotienda.Usuarios;

import java.util.List;

public interface UsuariosService {

    void update(UsuarioUpdateRequest userRequest,Long id) ;
    UsuariosResponse getByUsername(String username);
    UsuariosResponse getByID(Long id);
    void delete(Long id);
    List<UsuariosResponse> findAll();

}

package c1521mjavaangular.ecotienda.Usuarios;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuariosResponse {
    private Long id;
    private String email;
    private String nombre;
    private String direccion;
    private String telefono;
    private Boolean isEnabled;

}

package c1521mjavaangular.ecotienda.Usuarios;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuariosDto {
    private Long id;
    private String nombre;
    private String email;
    private String direccion;
}

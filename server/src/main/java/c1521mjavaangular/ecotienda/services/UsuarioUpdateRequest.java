package c1521mjavaangular.ecotienda.services;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UsuarioUpdateRequest {
    @Size(min = 3,max = 65)
    private String nombre;
    @Size(min = 3,max = 65)
    private String telefono;
    @Size(min = 3,max = 65)
    private String direccion;
}

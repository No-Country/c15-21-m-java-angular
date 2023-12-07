package c1521mjavaangular.ecotienda.dto;

import c1521mjavaangular.ecotienda.models.OrdenDetalles;
import c1521mjavaangular.ecotienda.models.Usuarios;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenDto {

    public Integer id;
    public UsuarioDto usuarioDto;
    public OrdenDetalles ordenDetalles;
}

package c1521mjavaangular.ecotienda.Orden;

import c1521mjavaangular.ecotienda.Usuarios.UsuariosDto;
import c1521mjavaangular.ecotienda.OrdenDetalles.OrdenDetalles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenDto {

    public Integer id;
    public UsuariosDto usuariosDto;
    public OrdenDetalles ordenDetalles;
}

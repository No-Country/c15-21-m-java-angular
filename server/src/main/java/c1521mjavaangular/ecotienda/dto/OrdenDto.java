package c1521mjavaangular.ecotienda.dto;

import c1521mjavaangular.ecotienda.models.OrdenDetalles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenDto {

    public Long id;
    public UsuarioDto usuarioDto;
    public OrdenDetalles ordenDetalles;
    public List<ProductoDto> listaProductos;
}

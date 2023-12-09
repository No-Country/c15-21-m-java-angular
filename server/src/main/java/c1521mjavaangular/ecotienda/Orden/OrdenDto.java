package c1521mjavaangular.ecotienda.Orden;

import c1521mjavaangular.ecotienda.Producto.ProductoDto;
import c1521mjavaangular.ecotienda.Producto.Productos;
import c1521mjavaangular.ecotienda.Usuarios.UsuariosDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenDto {

    public Long id;
    public UsuariosDto usuariosDto;
    public List<ProductoDto> listaProducto;

}

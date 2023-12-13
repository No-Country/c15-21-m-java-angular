package c1521mjavaangular.ecotienda.Producto;

import c1521mjavaangular.ecotienda.Categoria.Categorias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDto {

    public Long id;
    public String nombre;
    public Integer cantidad;
    public double precio;
    public String codigo;
    public String imagen;
    public Integer stock;
    public String descripcion;
    public List<Categorias> categorias;
}

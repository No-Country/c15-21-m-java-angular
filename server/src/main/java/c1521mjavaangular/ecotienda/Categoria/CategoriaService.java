package c1521mjavaangular.ecotienda.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    void CrearCategoria(CategoriaDto categoriaDto);

    Optional<Categorias> BuscarCategoria(Long id);

    List<Categorias> TraerTodos();

    void ModificarCategoria (Categorias categoria);

    void EliminarCategoria(Long id);
}

package c1521mjavaangular.ecotienda.Categoria;

import c1521mjavaangular.ecotienda.Exceptions.CategoriaNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    private static final Logger logger = LogManager.getLogger(CategoriaServiceImpl.class);

    @Override
    public void CrearCategoria(CategoriaDto categoriaDto) {
        logger.info("Entrando a CrearCategoría");

        if (categoriaRepository.existsByNombre(categoriaDto.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }

        Categorias categoria = Categorias.builder()
                .nombre(categoriaDto.getNombre())
                .descripcion(categoriaDto.getDescripcion())
                .imagen(categoriaDto.getImagen())
                .build();

        categoriaRepository.save(categoria);
        logger.info("Categoría creada");
        logger.info("Saliendo de CrearCategoría");
    }

    @Override
    public Optional<Categorias> BuscarCategoria(Long id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public List<Categorias> TraerTodos() {
        return categoriaRepository.findAll();
    }

    @Override
    public void ModificarCategoria(Categorias categoria) {
        logger.info("Entrando a ModificarCategoria");
        try {

            Optional<Categorias> existingCategoriaOptional = categoriaRepository.findById(categoria.getId());

            if (existingCategoriaOptional.isPresent()) {
                Categorias existingCategoria = existingCategoriaOptional.get();


                existingCategoria.setNombre(categoria.getNombre());
                existingCategoria.setDescripcion(categoria.getDescripcion());
                existingCategoria.setImagen(categoria.getImagen());


                categoriaRepository.save(existingCategoria);

                logger.info("Categoría modificada");
            } else {
                throw new CategoriaNotFoundException("No se encontró la categoría con ID: " + categoria.getId());
            }
        } catch (CategoriaNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error modificando Categoría", e);
        }
        logger.info("Saliendo de ModificarCategoria");
    }

    @Override
    public void EliminarCategoria(Long id) {
        logger.info("Entrando a EliminarCategoria");
        try {
            Optional<Categorias> categoriaOptional = categoriaRepository.findById(id);

            if (categoriaOptional.isPresent()) {
                categoriaRepository.deleteById(id);
                logger.info("Categoría eliminada");
            } else {
                throw new CategoriaNotFoundException("No se encontró la categoría con ID: " + id);
            }
        } catch (CategoriaNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error eliminando Categoría", e);
        }
        logger.info("Saliendo de EliminarCategoria");
    }
}

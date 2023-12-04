package c1521mjavaangular.ecotienda.controllers;

import c1521mjavaangular.ecotienda.exceptions.CategoriaNotFoundException;
import c1521mjavaangular.ecotienda.models.CategoriaDto;
import c1521mjavaangular.ecotienda.models.Categorias;
import c1521mjavaangular.ecotienda.services.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/categorias")
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @Operation(summary = "Crea una categoría")
    @PostMapping
    public ResponseEntity<?> crearCategoria(@RequestBody CategoriaDto categoriaDto) {
        try {
            categoriaService.CrearCategoria(categoriaDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creando categoría");
        }
    }

    @Operation(summary = "Trae una categoría por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoria(@PathVariable Long id) {
        try {
            Optional<Categorias> categoria = categoriaService.BuscarCategoria(id);
            if (categoria.isPresent()) {
                return ResponseEntity.ok(categoria.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una categoría con id: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error trayendo categoría con id: " + id);
        }
    }

    @Operation(summary = "Obtiene todas las categorías")
    @GetMapping
    public ResponseEntity<?> obtenerTodasCategorias() {
        try {
            List<Categorias> categorias = categoriaService.TraerTodos();
            return ResponseEntity.ok().body(categorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error obteniendo categorías");
        }
    }

    @Operation(summary = "Modifica una categoría")
    @PutMapping("/{id}")
    public ResponseEntity<?> modificarCategoria(@PathVariable Long id, @RequestBody Categorias categoria) {
        try {
            categoria.setId(id);
            categoriaService.ModificarCategoria(categoria);
            return ResponseEntity.ok().body("Categoría modificada");
        } catch (CategoriaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error modificando categoría");
        }
    }

    @Operation(summary = "Borra una categoría")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable Long id) {
        try {
            categoriaService.EliminarCategoria(id);
            return ResponseEntity.ok("Categoria con id: " + id + " eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una categoría con id: " + id);
        }
    }
}
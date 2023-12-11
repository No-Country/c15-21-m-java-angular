package c1521mjavaangular.ecotienda.Producto;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("buscar")
@CrossOrigin(value = "http://localhost:4200") //Se Necesita la Url donde esta desplegado el fronted
public class BuscarController {
    @Autowired
    private ProductoRepository productoRepository;

    @Operation(summary = "busca productos en la base de datos")
    @GetMapping("/productos")

    public ResponseEntity<List<Productos>> buscarPorPalabraClave(@RequestParam String palabraClave) {
        List<Productos> productos = productoRepository.findByNombreContainingIgnoreCase(palabraClave);

        if (productos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(productos, HttpStatus.OK);
        }
    }

}

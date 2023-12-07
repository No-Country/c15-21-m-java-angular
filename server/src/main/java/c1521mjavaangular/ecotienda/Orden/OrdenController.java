package c1521mjavaangular.ecotienda.Orden;

import c1521mjavaangular.ecotienda.Orden.OrdenDto;
import c1521mjavaangular.ecotienda.Orden.IOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/orden")
@RestController
public class OrdenController {

    @Autowired
    private IOrdenService ordenService;

    @GetMapping("")
    public List<OrdenDto> obtenerOrdenes(){
        return ordenService.obtenerOrdenes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerOrdenPorId(@PathVariable Integer id){
        try {
            Optional<OrdenDto> orden = ordenService.buscarOrden(id);
            if (orden.isPresent()){
                return ResponseEntity.ok(orden.get());
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una orden con id: " + id);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error trayendo orden con id: " + id);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> guardarOrden(@RequestBody OrdenDto ordenDto){
        try {
            ordenService.crearOrden(ordenDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creando orden");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificarOrden(@PathVariable Integer id, @RequestBody OrdenDto ordenDto) {
        try {
            ordenDto.setId(id);
            ordenService.modificarOrden(ordenDto);
            return ResponseEntity.ok("orden modificada con exito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error modificando orden");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOrden(@PathVariable Integer id){
        try {
            ordenService.eliminarOrden(id);
            return ResponseEntity.ok("Orden con id: " + id + " eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una orden con id: " + id);
        }
    }
}

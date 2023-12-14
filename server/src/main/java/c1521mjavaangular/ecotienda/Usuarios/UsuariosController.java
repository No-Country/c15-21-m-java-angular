package c1521mjavaangular.ecotienda.Usuarios;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UsuariosController {

    private final UsuariosServiceImpl usuariosServiceImpl;

    @Operation(summary = "Busca un usuario por su id")
    @GetMapping("/{id}")
    public ResponseEntity<UsuariosResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(usuariosServiceImpl.getByID(id));
    }
    @Operation(summary = "Busca a un usuario por su Username(Email)")
    @GetMapping("/username/{username}")
    public ResponseEntity<UsuariosResponse> getByUsername(@PathVariable String username)  {
        return ResponseEntity.ok().body(usuariosServiceImpl.getByUsername(username));
    }

    @Operation(summary = "Trae a todos los usuarios")
    @GetMapping("/all")
    public ResponseEntity<List<UsuariosResponse>> traerTodos() {
        return ResponseEntity.ok().body(usuariosServiceImpl.findAll());
    }

    @Operation(summary = "Modifica un usuario")
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@Valid @RequestBody UsuarioUpdateRequest request, @PathVariable Long id) {
        usuariosServiceImpl.update(request,id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Borra a un usuario por su id")
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        usuariosServiceImpl.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

package c1521mjavaangular.ecotienda.controllers;

import c1521mjavaangular.ecotienda.services.UsuarioUpdateRequest;
import c1521mjavaangular.ecotienda.services.UsuariosResponse;
import c1521mjavaangular.ecotienda.services.UsuariosService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsuariosController {

    private final UsuariosService usuariosService;

    @Operation(summary = "Busca un usuario por su id")
    @GetMapping("/{id}")
    public ResponseEntity<UsuariosResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(usuariosService.getByID(id));
    }
    @Operation(summary = "Busca a un usuario por su Username(Email)")
    @GetMapping("/username/{username}")
    public ResponseEntity<UsuariosResponse> getByUsername(@PathVariable String username)  {
        return ResponseEntity.ok().body(usuariosService.getByUsername(username));
    }

    @Operation(summary = "Modifica un usuario")
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@Valid @RequestBody UsuarioUpdateRequest request, @PathVariable Long id) {
        usuariosService.update(request,id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Borra a un usuario por su id")
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        usuariosService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

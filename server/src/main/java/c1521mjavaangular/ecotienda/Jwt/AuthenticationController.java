package c1521mjavaangular.ecotienda.Jwt;

import c1521mjavaangular.ecotienda.Usuarios.Usuarios;
import c1521mjavaangular.ecotienda.Usuarios.UsuariosRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UsuariosRepository usuariosRepository;

    @PostMapping("/registrarse")
    public JwtAuthenticationResponse signup(@RequestBody SignUpRequest request) throws MessagingException, IOException {
        return authenticationService.signup(request);
    }

    @PostMapping("/iniciarSesion")
    public JwtAuthenticationResponse signin(@RequestBody SignInRequest request) {
        return authenticationService.signin(request);
    }

    @GetMapping("/auth/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token) {
        Usuarios user = usuariosRepository.findByVerificationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token de verificación inválido"));

        user.setEnabled(true);
        user.setVerificationToken(null);
        usuariosRepository.save(user);

        return ResponseEntity.ok("Cuenta verificada correctamente");
    }

}

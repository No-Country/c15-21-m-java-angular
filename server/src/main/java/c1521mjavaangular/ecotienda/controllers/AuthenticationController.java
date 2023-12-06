package c1521mjavaangular.ecotienda.controllers;

import c1521mjavaangular.ecotienda.dto.JwtAuthenticationResponse;
import c1521mjavaangular.ecotienda.dto.SignInRequest;
import c1521mjavaangular.ecotienda.dto.SignUpRequest;
import c1521mjavaangular.ecotienda.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/registrarse")
    public JwtAuthenticationResponse signup(@RequestBody SignUpRequest request) {
        return authenticationService.signup(request);
    }

    @PostMapping("/iniciarSesion")
    public JwtAuthenticationResponse signin(@RequestBody SignInRequest request) {
        return authenticationService.signin(request);
    }
}

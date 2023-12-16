package c1521mjavaangular.ecotienda.Jwt;

import c1521mjavaangular.ecotienda.Email.EmailService;
import c1521mjavaangular.ecotienda.Usuarios.UsuariosServiceImpl;
import c1521mjavaangular.ecotienda.Usuarios.Role;
import c1521mjavaangular.ecotienda.Usuarios.Usuarios;
import c1521mjavaangular.ecotienda.Usuarios.UsuariosRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuariosRepository usuariosRepository;
    private final UsuariosServiceImpl usuariosServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final EmailService emailService;

    public JwtAuthenticationResponse signup(SignUpRequest request) throws MessagingException, IOException {
        var user = Usuarios
                .builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();


        String verificationToken = UUID.randomUUID().toString();
        user.setVerificationToken(verificationToken);

        LocalDateTime expirationTime = LocalDateTime.now().plusHours(24);
        user.setTokenExpirationTime(expirationTime);

        user = usuariosServiceImpl.save(user);

        String verificationLink = "http://vps-3785405-x.dattaweb.com:8080//v1/auth/verify?token=" + verificationToken;
        emailService.sendEcoTiendaConfirmationEmail(user.getEmail(), verificationLink, user.getNombre());



        user = usuariosServiceImpl.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }


    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = usuariosRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email o contraseña inválidos."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }



}

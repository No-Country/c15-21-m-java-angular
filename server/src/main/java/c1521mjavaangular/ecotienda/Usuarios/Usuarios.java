package c1521mjavaangular.ecotienda.Usuarios;

import c1521mjavaangular.ecotienda.Producto.Productos;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Usuarios implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "password")
    private String password;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private String telefono;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @Column(name = "verification_token")
    private String verificationToken;
    @Column(name = "token_expiration_time")
    private LocalDateTime tokenExpirationTime;

    @Enumerated(EnumType.STRING)
    Role role;

    private boolean isEnabled;

    @ManyToMany
    @JoinTable(
            name = "productos_favoritos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<Productos> favoriteProducts = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
    public Long getUserId() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;

    }
}

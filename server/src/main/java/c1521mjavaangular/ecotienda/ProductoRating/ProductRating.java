package c1521mjavaangular.ecotienda.ProductoRating;

import c1521mjavaangular.ecotienda.Producto.Productos;
import c1521mjavaangular.ecotienda.Usuarios.Usuarios;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Table(name = "productos_ratings")
public class ProductRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuarios usuarios;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @JsonIgnore
    private Productos product;

    private int rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private LocalDateTime date;

    @Transient
    private String userNombre;

    public String getUserFirstName() {
        return usuarios != null ? usuarios.getNombre() : null;
    }

}
package c1521mjavaangular.ecotienda.Categoria;

import c1521mjavaangular.ecotienda.Producto.Productos;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@Table(name = "categorias")
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Categorias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany(mappedBy = "categorias")
    @JsonIgnore
    private List<Productos> productos;
}

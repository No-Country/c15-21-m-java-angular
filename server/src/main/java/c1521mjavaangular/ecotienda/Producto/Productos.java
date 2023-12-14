package c1521mjavaangular.ecotienda.Producto;

import c1521mjavaangular.ecotienda.Categoria.Categorias;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@Table(name = "productos")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Productos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "precio")
    private Double precio;
    @Column(name = "stock")
    private Integer stock;
    @ManyToMany
    @JoinTable(
            name = "producto_categoria",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categorias> categorias;
    @Column(name="descripcion")
    private String descripcion;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "imagen")
    private String imagen;


}

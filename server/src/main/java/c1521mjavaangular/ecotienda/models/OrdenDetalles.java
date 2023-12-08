package c1521mjavaangular.ecotienda.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "orden_detalles")
@AllArgsConstructor
@NoArgsConstructor
public class OrdenDetalles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "precioProducto")
    private double precioProducto;
    @ManyToOne
    private Productos productos;



}

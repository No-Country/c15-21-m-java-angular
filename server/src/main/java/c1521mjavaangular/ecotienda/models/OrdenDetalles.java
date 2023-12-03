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
    @Column(name = "precio")
    private Double precio;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "direccion")
    private String direccion;


    @OneToOne
    private Productos productos;



}

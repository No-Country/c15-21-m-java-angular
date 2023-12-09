package c1521mjavaangular.ecotienda.Orden;

import c1521mjavaangular.ecotienda.Usuarios.Usuarios;
import c1521mjavaangular.ecotienda.OrdenDetalles.OrdenDetalles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "ordenes")
@AllArgsConstructor
@NoArgsConstructor
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(targetEntity = Usuarios.class)
    @JoinColumn(name = "usuario_id")
    private Usuarios usuarios;
    @Column(name = "precioTotal")
    private double precioTotal;


}

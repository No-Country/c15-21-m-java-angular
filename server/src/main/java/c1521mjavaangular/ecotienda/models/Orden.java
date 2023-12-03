package c1521mjavaangular.ecotienda.models;

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
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuarios usuarios;
    @OneToOne(mappedBy = "orden")
    @JoinColumn(name = "ordenDetalles_id")
    private OrdenDetalles detalles;


}

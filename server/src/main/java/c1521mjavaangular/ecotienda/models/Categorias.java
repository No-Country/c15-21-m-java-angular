    package c1521mjavaangular.ecotienda.models;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.List;


    @Entity
    @Data
    @Table(name = "categorias")
    @AllArgsConstructor
    @NoArgsConstructor

    public class Categorias {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Integer id;
        @Column(name = "nombre")
        private String nombre;
        @Column(name = "descripcion")
        private String descripcion;

        @ManyToMany(mappedBy = "categorias")
        private List<Productos> productos;
    }

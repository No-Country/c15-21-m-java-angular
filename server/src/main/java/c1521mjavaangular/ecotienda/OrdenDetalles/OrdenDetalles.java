    package c1521mjavaangular.ecotienda.OrdenDetalles;

    import c1521mjavaangular.ecotienda.Orden.Orden;
    import c1521mjavaangular.ecotienda.Producto.Productos;
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
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "orden_id")
        private Orden orden;
        @ManyToOne
        private Productos productos;



    }

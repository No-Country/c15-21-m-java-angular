package c1521mjavaangular.ecotienda.ProductoRating;

import c1521mjavaangular.ecotienda.Usuarios.Usuarios;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRatingDTO {

    private Long id;
    private int rating;
    private String comment;
    private String date;
    private String userName;

    public ProductRatingDTO(ProductRating productRating) {
        this.id = productRating.getId();
        this.rating = productRating.getRating();
        this.comment = productRating.getComment();
        this.date = productRating.getDate().toLocalDate().toString();
        Usuarios usuarios = productRating.getUsuarios();
        if (usuarios != null) {
            this.userName = usuarios.getNombre();
        }
    }
}
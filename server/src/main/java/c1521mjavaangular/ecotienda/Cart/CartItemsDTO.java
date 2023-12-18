package c1521mjavaangular.ecotienda.Cart;

import c1521mjavaangular.ecotienda.Producto.ProductoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemsDTO {

    private Long cartItemId;
    private CartDTO cart;
    private ProductoDto product;
    private Integer quantity;
    private double productPrice;
}

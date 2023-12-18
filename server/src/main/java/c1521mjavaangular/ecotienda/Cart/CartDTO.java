package c1521mjavaangular.ecotienda.Cart;

import c1521mjavaangular.ecotienda.Producto.ProductoDto;
import c1521mjavaangular.ecotienda.Usuarios.UsuariosDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CartDTO {

    private Long cartId;
    private Double totalPrice = 0.0;
    private List<ProductoDto> products = new ArrayList<>();
}

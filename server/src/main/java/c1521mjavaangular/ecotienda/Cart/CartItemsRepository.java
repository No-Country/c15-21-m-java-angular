package c1521mjavaangular.ecotienda.Cart;

import c1521mjavaangular.ecotienda.Producto.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartItemsRepository extends JpaRepository<CartItems, Long> {

    @Query("SELECT ci.productos FROM CartItems ci WHERE ci.productos.id = ?1")
    Productos findProductById(Long productId);

//	@Query("SELECT ci.cart FROM CartItem ci WHERE ci.product.id = ?1")
//	List<Cart> findCartsByProductId(Long productId);

    @Query("SELECT ci FROM CartItems ci WHERE ci.cart.id = ?1 AND ci.productos.id = ?2")
    CartItems findCartItemByProductIdAndCartId(Long cartId, Long productId);

//	@Query("SELECT ci.cart FROM CartItem ci WHERE ci.cart.user.email = ?1 AND ci.cart.id = ?2")
//	Cart findCartByEmailAndCartId(String email, Integer cartId);

//	@Query("SELECT ci.order FROM CartItem ci WHERE ci.order.user.email = ?1 AND ci.order.id = ?2")
//	Order findOrderByEmailAndOrderId(String email, Integer orderId);

    @Modifying
    @Query("DELETE FROM CartItems ci WHERE ci.cart.id = ?1 AND ci.productos.id = ?2")

    void deleteCartItemByProductIdAndCartId(Long productId, Long cartId);
}

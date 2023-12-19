package c1521mjavaangular.ecotienda.Cart;


import c1521mjavaangular.ecotienda.Exceptions.APIException;
import c1521mjavaangular.ecotienda.Exceptions.ResourceNotFoundException;
import c1521mjavaangular.ecotienda.Orden.Orden;
import c1521mjavaangular.ecotienda.Orden.OrdenDto;
import c1521mjavaangular.ecotienda.Producto.ProductoDto;
import c1521mjavaangular.ecotienda.Producto.ProductoRepository;
import c1521mjavaangular.ecotienda.Producto.Productos;
import c1521mjavaangular.ecotienda.Usuarios.Usuarios;
import c1521mjavaangular.ecotienda.Usuarios.UsuariosRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDTO createCart(String email) {
        Usuarios usuarios = usuariosRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Usuarios", "emailId", email));
        Cart cart = new Cart();
        cart.setUsuarios(usuarios);
        cart = cartRepository.save(cart);
        return modelMapper.map(cart, CartDTO.class);

    }

    @Override
    public CartDTO getCartDetails(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));
        return mapToCartDTO(cart);
    }

    @Override
    public CartDTO addProductToCart(Long cartId, Long productId, Integer quantity) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        Productos product = productoRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException ("Product", "productId", productId));

        CartItems cartItem = cartItemsRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem != null) {
            throw new APIException("Product " + product.getNombre() + " already exists in the cart");
        }

        if (product.getStock() == 0) {
            throw new APIException(product.getNombre() + " is not available");
        }

        if (product.getStock() < quantity) {
            throw new APIException("Please, make an order of the " + product.getNombre()
                    + " less than or equal to the quantity " + product.getStock() + ".");
        }

        CartItems newCartItem = new CartItems();

        newCartItem.setProductos(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);

        newCartItem.setProductPrice(product.getPrecio());

        cartItemsRepository.save(newCartItem);

        product.setStock(product.getStock() - quantity);

        cart.setTotalPrice(cart.getTotalPrice() + (product.getPrecio() * quantity));

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<ProductoDto> productDTOs = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProductos(), ProductoDto.class)).collect(Collectors.toList());

        cartDTO.setProducts(productDTOs);

        return cartDTO;

    }

    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();

        if (carts.size() == 0) {
            throw new APIException("No cart exists");
        }

        List<CartDTO> cartDTOs = carts.stream().map(cart -> {
            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

            List<ProductoDto> products = cart.getCartItems().stream()
                    .map(p -> modelMapper.map(p.getProductos(), ProductoDto.class)).collect(Collectors.toList());

            cartDTO.setProducts(products);

            return cartDTO;

        }).collect(Collectors.toList());

        return cartDTOs;
    }

    @Override
    public CartDTO getCart(String emailId, Long cartId) {
        Cart cart = cartRepository.findCartByEmailAndCartId(emailId, cartId);

        if (cart == null) {
            throw new ResourceNotFoundException("Cart", "cartId", cartId);
        }

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<ProductoDto> products = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProductos(), ProductoDto.class)).collect(Collectors.toList());

        cartDTO.setProducts(products);

        return cartDTO;
    }

    @Override
    public void updateProductInCarts(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        Productos product = productoRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        CartItems cartItem = cartItemsRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new APIException("Product " + product.getNombre() + " not available in the cart!!!");
        }

        double cartPrice = cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity());

        cartItem.setProductPrice(product.getPrecio());

        cart.setTotalPrice(cartPrice + (cartItem.getProductPrice() * cartItem.getQuantity()));

        cartItem = cartItemsRepository.save(cartItem);
    }

    @Override
    public CartDTO updateProductQuantityInCart(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        Productos product = productoRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        if (product.getStock() == 0) {
            throw new APIException(product.getNombre() + " is not available");
        }

        if (product.getStock() < quantity) {
            throw new APIException("Please, make an order of the " + product.getNombre()
                    + " less than or equal to the quantity " + product.getStock() + ".");
        }

        CartItems cartItem = cartItemsRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new APIException("Product " + product.getNombre() + " not available in the cart!!!");
        }

        double cartPrice = cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity());

        product.setStock(product.getStock() + cartItem.getQuantity() - quantity);

        cartItem.setProductPrice(product.getPrecio());
        cartItem.setQuantity(quantity);
        cart.setTotalPrice(cartPrice + (cartItem.getProductPrice() * quantity));

        cartItem = cartItemsRepository.save(cartItem);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<ProductoDto> productDTOs = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProductos(), ProductoDto.class)).collect(Collectors.toList());

        cartDTO.setProducts(productDTOs);

        return cartDTO;

    }

    @Override
    public String deleteProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        CartItems cartItem = cartItemsRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new ResourceNotFoundException("Product", "productId", productId);
        }

        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity()));

        Productos product = cartItem.getProductos();
        product.setStock(product.getStock() + cartItem.getQuantity());

        cartItemsRepository.deleteCartItemByProductIdAndCartId(cartId, productId);

        return "Product " + cartItem.getProductos().getNombre() + " removed from the cart !!!";
    }
        private CartDTO mapToCartDTO(Cart cart) {
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<ProductoDto> productDTOs = cart.getCartItems().stream()
                .map(cartItems -> {
                    ProductoDto productoDto = modelMapper.map(cartItems.getProductos(), ProductoDto.class);
                    productoDto.setCantidad(cartItems.getQuantity());
                    return productoDto;
                }).collect(Collectors.toList());

        cartDTO.setProducts(productDTOs);

        return cartDTO;
    }

}

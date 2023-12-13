package c1521mjavaangular.ecotienda.checkoutMP;

import c1521mjavaangular.ecotienda.Jwt.JwtService;
import c1521mjavaangular.ecotienda.Usuarios.Usuarios;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    @Autowired
    private JwtService validateToken;

    @Autowired
    private UserDetails userDetails;

    @Value("${mercadopago.access_token}") // Lee el token desde la configuración de Spring Boot
    private String accessToken;

    @PostMapping("/create-preference")
    public String createPreference(@RequestHeader(value="Authorization") String token, @RequestBody PreferenceItem preferenceItem) throws InvalidTokenException {
        MercadoPagoConfig.setAccessToken(accessToken);
        Usuarios usuarios = new Usuarios();
        usuarios.
        if (!validateToken.isTokenValid(token, userDetails )) return "token incorrecto";
        try {

            List<PreferenceItemRequest> items = new ArrayList<>();
            // Aquí configura la preferencia de acuerdo a tus necesidades // Puedes proporcionar la preferencia aquí
            for (ProductoMPDTO producto:preferenceItem.getProductosList()) {
                PreferenceItemRequest itemRequest =
                        PreferenceItemRequest.builder()
                                .id("1234")
                                .title(producto.getNombre())
                                .description("")
                                .pictureUrl(producto.getImagen())
                                .categoryId(producto.getCategoria())
                                .quantity(producto.getQuantity())
                                .currencyId("ARS")
                                .unitPrice(new BigDecimal(producto.getPrecio()))
                                .build();
                items.add(itemRequest);
            }
            PreferenceBackUrlsRequest backUrls =
// ...
                    PreferenceBackUrlsRequest.builder()
                            .success("https://ecommerce-utn.onrender.com/")
                            .pending("https://ecommerce-utn.onrender.com/")
                            .failure("https://www.seu-site/failure")
                            .build();
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items).backUrls(backUrls).autoReturn("approved").build();
            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);



            PreferenceRequest request = PreferenceRequest.builder().backUrls(backUrls).build();

            return preference.getId();
        } catch (MPException | MPApiException e) {
            // Manejo de excepciones
            e.printStackTrace();
            return "Error al crear la preferencia";
        }
    }
}

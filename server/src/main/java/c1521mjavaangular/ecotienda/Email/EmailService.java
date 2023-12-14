package c1521mjavaangular.ecotienda.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String email;

    public void sendEcoTiendaConfirmationEmail(String to, String confirmationLink, String username) throws MessagingException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        String imagePath = "src/main/resources/static/LogoEcoTienda.png";
        Path path = Paths.get(imagePath);
        byte[] imageBytes = Files.readAllBytes(path);
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);



        helper.setTo(to);
        helper.setFrom(email);
        helper.setSubject("Bienvenido a EcoTienda - Verificación de cuenta");

        String htmlContent = "<!DOCTYPE html><html lang=\"en\">"
                + "<head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Confirmación de Email - EcoTienda</title></head>"
                + "<body style=\"background-color: #FBFBFB; color: #000000; font-family: Arial, sans-serif; text-align: center;\">"
                + "<div style=\"background-color: #F8F2CC; padding: 20px;\">"
                + "<img src=\"data:image/png;base64," + base64Image + "\" alt=\"EcoTienda Icon\" style=\"width: 397px; height: 190px; margin-bottom: 10px; margin-right: 100px;\">"
                + "<p style=\"margin-top: 0; font-size: 18px;\">Gracias por registrarte!</p>"
                + "</div>"
                + "<div style=\"padding: 20px;\">"
                + "<p style=\"font-size: 16px;\">Querido " + username + ",</p>"
                + "<p style=\"font-size: 16px;\">Gracias por registrarte en EcoTienda. Para completar el registro, por favor haga click en el link de abajo:</p>"
                + "<p style=\"margin-top: 20px;\">"
                + "<a href=\"" + confirmationLink + "\" style=\"display: inline-block; padding: 10px 20px; background-color: #C1CA46; color: #FFFFFF; text-decoration: none; border-radius: 5px;\">Confirmar Email</a>"
                + "</p>"
                + "<p style=\"font-size: 16px;\">Si el botón de arriba no funciona, puedes copiar y pegar el siguiente link en el navegador:</p>"
                + "<p style=\"font-size: 16px; margin-top: 10px;\">" + confirmationLink + "</p>"
                + "</div>"
                + "<div style=\"padding: 20px; background-color: #F8F2CC;\">"
                + "<p style=\"font-size: 16px;\">Gracias por elegir a EcoTienda.Si tienes alguna pregunta, por favor contacta a nuestro equipo de soporte.</p>"
                + "</div>"
                + "</body></html>";

        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }



}

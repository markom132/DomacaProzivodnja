package com.domaciproizvodi.service;

import com.domaciproizvodi.model.Order;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendOrderConfirmationEmail(Order order) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        Long orderId = order.getId();
        String emailAddress = order.getUser().getEmail();

        helper.setTo(emailAddress);
        helper.setSubject("Order Confirmation - Order #" + orderId);

        Context context = new Context();
        context.setVariable("orderItems", order.getItems());
        context.setVariable("total", order.getTotalPrice());

        String htmlContent = springTemplateEngine.process("order-confirmation", context);
        helper.setText(htmlContent, true);

        mailSender.send(mimeMessage);

    }

    public void sendShipmentEmail(String to, Long orderId) {
        String subject = "Your Order Has Been Shipped - Order #" + orderId;
        String text = "Good news! Your order ID " + orderId + " has been shipped. You will receive it soon.";
        sendEmail(to, subject, text);
    }

    public void sendNewsletterSubscriptionEmail(String to) {
        String subject = "Welcome to Our Newsletter!";
        String text = "Thank you for subscribing to our newsletter. Stay tuned for the latest updates and offers!";
        sendEmail(to, subject, text);
    }

}

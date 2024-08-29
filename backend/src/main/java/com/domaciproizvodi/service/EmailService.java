package com.domaciproizvodi.service;

import com.domaciproizvodi.model.Order;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailService {

  private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

  @Autowired private JavaMailSender mailSender;

  @Autowired private SpringTemplateEngine springTemplateEngine;

  public void sendEmail(String to, String subject, String body) {
    logger.info("Sending simple email to: {} with subject: {}", to, subject);
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(body);
    mailSender.send(message);
    logger.info("Simple email sent successfully to: {}", to);
  }

  public void sendOrderConfirmationEmail(Order order) throws MessagingException {
    logger.info("Preparing order confirmation email for Order ID: {}", order.getId());
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
    logger.info("Order confirmation email sent successfully to: {}", emailAddress);
  }

  public void sendPasswordResetEmail(String to, String code) throws MessagingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
    String htmlMsg =
        "<h3>Password Reset Code</h3>"
            + "<p>Use the following code to reset your password:</p>"
            + "<h2>"
            + code
            + "</h2>";
    helper.setText(htmlMsg, true);
    helper.setTo(to);
    helper.setSubject("Password Reset Code");
    helper.setFrom("no-reply@domaciproizvodi.com");

    mailSender.send(mimeMessage);
  }

  public void sendVerificationCodeEmail(String to, String code) throws MessagingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
    String htmlMsg =
        "<h3>Account Verification<h3>"
            + "<p>Your verification code is:</p>"
            + "<h2>"
            + code
            + "</h2>";
    helper.setText(htmlMsg, true);
    helper.setTo(to);
    helper.setSubject("Account Verification");
    helper.setFrom("no-reply@domaciproizvodi.com");

    mailSender.send(mimeMessage);
  }

  public void sendShipmentEmail(String to, Long orderId) {
    String subject = "Your Order Has Been Shipped - Order #" + orderId;
    String text =
        "Good news! Your order ID " + orderId + " has been shipped. You will receive it soon.";
    logger.info("Sending shipment email for Order ID: {} to: {}", orderId, to);
    sendEmail(to, subject, text);
  }

  public void sendNewsletterSubscriptionEmail(String to) {
    String subject = "Welcome to Our Newsletter!";
    String text =
        "Thank you for subscribing to our newsletter. Stay tuned for the latest updates and offers!";
    logger.info("Sending newsletter subscription email to: {}", to);
    sendEmail(to, subject, text);
  }
}

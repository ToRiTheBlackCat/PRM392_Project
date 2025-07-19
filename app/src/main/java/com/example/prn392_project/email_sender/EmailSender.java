package com.example.prn392_project.email_sender;

import android.util.Log;

import com.example.prn392_project.data_classes.CartItem;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    private static final String TAG = "EmailSender";

    private static String generateBillingHtml(String buyerName, String phone, String address, List<CartItem> cartItems) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\">")
                .append("<style>")
                .append("body { font-family: Arial, sans-serif; color: #333; padding: 20px; }")
                .append("h2 { color: #007BFF; }")
                .append(".buyer-info p { margin: 4px 0; }")
                .append("table { width: 100%; border-collapse: collapse; margin-top: 20px; }")
                .append("table, th, td { border: 1px solid #ccc; }")
                .append("th, td { padding: 10px; text-align: left; }")
                .append("th { background-color: #f8f8f8; }")
                .append(".total { font-weight: bold; text-align: right; }")
                .append(".right { text-align: right; }")
                .append("</style></head><body>");

        html.append("<h2>Purchase Receipt</h2>")
                .append("<div class=\"buyer-info\">")
                .append("<p><strong>Name:</strong> ").append(buyerName).append("</p>")
                .append("<p><strong>Phone:</strong> ").append(phone).append("</p>")
                .append("<p><strong>Address:</strong> ").append(address).append("</p>")
                .append("</div>");

        html.append("<table><thead><tr>")
                .append("<th>Product</th><th>Size</th><th>Price (VND)</th><th>Quantity</th><th>Subtotal (VND)</th>")
                .append("</tr></thead><tbody>");

        int total = 0;
        for (CartItem item : cartItems) {
            int subtotal = item.getProduct().getProductPrice() * item.getQuantity();
            total += subtotal;
            html.append("<tr>")
                    .append("<td>").append(item.getProduct().getProductName()).append("</td>")
                    .append("<td>").append(item.getSize()).append("</td>")
                    .append("<td class=\"right\">").append(String.format("%,d", item.getProduct().getProductPrice())).append("</td>")
                    .append("<td class=\"right\">").append(item.getQuantity()).append("</td>")
                    .append("<td class=\"right\">").append(String.format("%,d", subtotal)).append("</td>")
                    .append("</tr>");
        }

        html.append("</tbody></table>");
        html.append("<p class=\"total\">Total: ").append(String.format("%,d", total)).append(" VND</p>");
        html.append("</body></html>");

        return html.toString();
    }


    public static void sendEmail(String toEmail, String subject, String userName, String phone, String address, List<CartItem> cartItemList) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                // SMTP server config
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");

                // Gmail credentials
                final String username = "tuancase184736@fpt.edu.vn";       // use your Gmail
                final String password = "osdj zinw fzhj zmmf";          // use App Password

                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

                // Email content
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
                message.setSubject(subject);
                var htmlBody = generateBillingHtml(userName, phone, address, cartItemList);
                message.setContent(htmlBody, "text/html; charset=utf-8");

                Transport.send(message);
                Log.d(TAG, "Email sent successfully!");

            } catch (MessagingException e) {
                Log.e(TAG, "Failed to send email: " + e.getMessage());
            }
        });
    }

}

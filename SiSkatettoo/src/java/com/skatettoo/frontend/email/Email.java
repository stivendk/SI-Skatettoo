/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.email;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author APRENDIZ
 */
public class Email {
    
    private final static String HOST = "smtp.gmail.com";
    private final static String PORT = "25";
    private final static String REMITENTE = "siskatettoo@gmail.com";
    private final static String PASS = "elmaschido";
    
    private String asunto;
    private String mensaje;
    private String destinario;
    
    private Properties props;

    public Email(){
        iniciarPropiedades();
    }
    
    public Email(String asunto, String mensaje, String destinario) {
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.destinario = destinario;
        iniciarPropiedades();
    }
    
    public void iniciarPropiedades(){
        props = new Properties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.ssl.trust", HOST);
    } 
    
    private Session getSession(){
        Authenticator a = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication (REMITENTE, PASS);
            }
        };
        return Session.getInstance(props, a);
    }
    
    public void enviarEmail(){
        try {
            Session session = getSession();
            Message msj = new MimeMessage(session);
            msj.setFrom(new InternetAddress(REMITENTE));
            msj.setSubject(this.asunto);
            msj.setText(this.mensaje);
            msj.setRecipient(Message.RecipientType.TO, new InternetAddress(this.destinario));
            
            Transport.send(msj);
        } catch (AddressException e) {
            System.out.println("La direccion del correo es invalida" + e);
        } catch (MessagingException ex){
            System.out.println("Se ha presentado un error al enviar el correo" + ex);
            ex.printStackTrace();
        }
    }
    
    public void enviarEmail2(){
        try {
            Session session = getSession();
            Message msj = new MimeMessage(session);
            msj.setFrom(new InternetAddress(REMITENTE));
            msj.setSubject(this.asunto);
            msj.setText(this.mensaje);
            msj.setRecipients(Message.RecipientType.TO, InternetAddress.parse(this.destinario));
            
            Transport.send(msj);
        } catch (AddressException e) {
            System.out.println("La direccion del correo es invalida" + e);
        } catch (MessagingException ex){
            System.out.println("Se ha presentado un error al enviar el correo");
            ex.printStackTrace();
        }
    }
}

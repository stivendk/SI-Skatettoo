/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author APRENDIZ
 */
public class MiAuthenticator extends Authenticator{
    
    private final static String REMITENTE = "siskatettoo@gmail.com";
    private final static String PASS = "elmaschido";
        
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(REMITENTE, PASS);
    }
    
    
}

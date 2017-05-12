/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author APRENDIZ
 */
public class GeneradorPss {

    public static String generadorPassword() throws NoSuchAlgorithmException {
        String[] symbols = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
        int length = 7;
        Random random = SecureRandom.getInstanceStrong();    // as of JDK 8, this should return the strongest algorithm available to the JVM
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int indexRandom = random.nextInt(symbols.length);
            sb.append(symbols[indexRandom]);
        }
        String password = sb.toString();
        return password;
    }
}

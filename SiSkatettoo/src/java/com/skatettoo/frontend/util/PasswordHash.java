/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author StivenDavid
 */
public class PasswordHash {

    Map<String, String> DB = new HashMap<String, String>();
    public static final String SALT = "my-salt-text";

    public void signup(String username, String password) {
        String saltedPassword = SALT + password;
        String hashedPassword = generateHash(saltedPassword);
        DB.put(username, hashedPassword);
    }

    public Boolean login(String username, String password) {
        Boolean isAuthenticated = false;

        // remember to use the same SALT value use used while storing password
        // for the first time.
        String saltedPassword = SALT + password;
        String hashedPassword = generateHash(saltedPassword);

        String storedPasswordHash = DB.get(username);
        if (hashedPassword.equals(storedPasswordHash)) {
            isAuthenticated = true;
        } else {
            isAuthenticated = false;
        }
        return isAuthenticated;
    }

    public static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            // handle error here.
        }

        return hash.toString();
    }
}

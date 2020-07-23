package com.example.sea_assignment.utils;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Encrypt {
    public String getMd5(String input)
    {
        try {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update("UWU5E4455I9NM3NT".getBytes());
            md.update(input.getBytes());

            byte[] byteData = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte byteDatum : byteData) {
                sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16)
                        .substring(1));
            }
            return sb.toString();
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

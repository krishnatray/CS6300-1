package com.team25project3;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PwdEncrypt {
    public static String encrypt(String plaintext) {
        MessageDigest msgDigest = null;
        String hashValue = null;
        try {
            msgDigest = MessageDigest.getInstance("SHA-1");
            msgDigest.update(plaintext.getBytes("UTF-8"));
            byte rawByte[] = msgDigest.digest();
            
            StringBuffer hexString = new StringBuffer();
        	for (int i=0;i<rawByte.length;i++) {
        	  hexString.append(Integer.toHexString(0xFF & rawByte[i]));
        	}

            hashValue = hexString.toString();
 
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No Such Algorithm Exists");
        } catch (UnsupportedEncodingException e) {
            System.out.println("The Encoding Is Not Supported");
        }
        return hashValue;
    }
}

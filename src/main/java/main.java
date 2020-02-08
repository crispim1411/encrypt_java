
import java.security.SecureRandom;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author crispim
 */
public class main {
    public static void main(String[] args) {
        String originalString = "howtodoinjava.com";
        String password = "a1b2cd4e5";
          
        String encryptedString = AES.encrypt(originalString, password);
        String decryptedString = AES.decrypt(encryptedString, password);

        System.out.println(originalString);
        System.out.println(encryptedString);
        System.out.println(decryptedString);
        //System.out.println("iv:"+iv);
    }
    
}

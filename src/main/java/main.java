
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
        try {
            String originalString = "howtodoinjava.com";
            String password = "a1b2cd4e5";

            String encryptedString = AES.encrypt(originalString, password);
            String decryptedString = AES.decrypt(encryptedString, password);

            System.out.println(originalString);
            System.out.println(encryptedString);
            System.out.println(decryptedString);   
            
            //escrita em texto
            BufferedWriter writer = new BufferedWriter(new FileWriter("encriptado"));
            writer.write(encryptedString);
            writer.close();
            
//            //escrita em bytes
//            FileOutputStream output = new FileOutputStream("encript");
//            byte[] strToBytes = encryptedString.getBytes();
//            output.write(strToBytes);
//            output.close();
            
        } catch (Exception e) {
            System.out.println("Error: "+e.toString());
        }
        //System.out.println("iv:"+iv);
    }
    
}

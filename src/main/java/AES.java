
import java.io.ByteArrayOutputStream;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rodrigo Crispim
 */
public class AES {
    //Encriptação utilizando cifra simétrica por blocos
    //AES: Advanced Encryption Standard
    //PBKDF2WithHmacSHA256: 
        //PBKDF2: Password-Based Key Derivation Function 2
        //Hmac: Hash-based Message Authentication Code
        //SHA256: hash de 256 bits
    //CBC: Cipher Block Chaining
    //PKCS5PaddingPublic: Key Cryptography Standards (Preenche em múltiplos de 8)
    
    private final static String ALGORITHM_NAME = "AES/CBC/PKCS5Padding";
    private final static int ALGORITHM_KEY_SIZE = 256;
    private final static String PBKDF2_NAME = "PBKDF2WithHmacSHA256";
    private final static int PBKDF2_SALT_SIZE = 16;
    private final static int PBKDF2_ITERATIONS = 32767;
        
    public static String encrypt(String str, String password) {
        try {
            //salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[PBKDF2_SALT_SIZE]; 
            random.nextBytes(salt);
            
            //secret key
            SecretKeyFactory factory = SecretKeyFactory.getInstance(PBKDF2_NAME);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, PBKDF2_ITERATIONS, ALGORITHM_KEY_SIZE);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

            //cifra
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            AlgorithmParameters params = cipher.getParameters();
            byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
            byte[] encryptedText = cipher.doFinal(str.getBytes("UTF-8"));
            
            //concatenate salt + iv + ciphertext
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(salt);
            outputStream.write(iv);
            outputStream.write(encryptedText);

            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } 
        catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return "ERROR: Erro ao cifrar";
    }
    
    public static String decrypt(String str, String password) {
        try {           
            //verificaçao
            byte[] ciphertext = Base64.getDecoder().decode(str);
            if (ciphertext.length < 48) {
                return "ERROR: Erro ao decifrar";
            }
            //obtém parâmetros
            byte[] salt = Arrays.copyOfRange(ciphertext, 0, PBKDF2_SALT_SIZE);
            byte[] iv = Arrays.copyOfRange(ciphertext, PBKDF2_SALT_SIZE, 32);
            byte[] ct = Arrays.copyOfRange(ciphertext, 32, ciphertext.length);
            
            //secret key
            SecretKeyFactory factory = SecretKeyFactory.getInstance(PBKDF2_NAME);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, PBKDF2_ITERATIONS, ALGORITHM_KEY_SIZE);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secret = new SecretKeySpec(tmp.getEncoded(), "AES");
           
            //decifra
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
            byte[] plaintext = cipher.doFinal(ct);
            
            return new String(plaintext, "UTF-8");
        } 
        catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return "ERROR: Erro ao decifrar";
    }
    
}

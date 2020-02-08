
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author crispim
 */
public class FileManager {
    public static String readContent(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));     
            String content = "";
            String str; 

            while((str = br.readLine()) != null) {
                content += str;
            }

            return content;
        } catch (Exception e) {
            System.out.println("Erro ao carregar conteúdo");
        }
        return null;
    }
    
    public static void saveContent(String content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("encrypted"));
            writer.write(content);
            writer.close();
        } catch (Exception e) {
            System.out.println("Erro ao salvar");
        }
    }
}

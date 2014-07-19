/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sampleproject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author Ramesh
 */
public class WordBackUP {

    private FileInputStream Fin;                //  Holds the vile name 
    Scanner in = new Scanner(System.in);
    SqlConnection sql = new SqlConnection();
    VerifyWords Verify = new VerifyWords();

    public void ReadNUpdateFile() {
        try {
            Fin = new FileInputStream(Variable.InputFileName);
            File f = new File(new Variable().InputFileName);

            BufferedReader BuffReader = new BufferedReader(new InputStreamReader(new DataInputStream(Fin)));
            String data = null;
            StringTokenizer Token = null;
//            String tokenizor = " *([^(][^ ]+|\\([^\\)]+\\)?)";
            String tokenizor = ", ";
            if (f.exists()) {
                while ((data = BuffReader.readLine()) != null) {
                    
                    System.out.println(data);
                    
                    data=data.replaceAll("'", "");
                    data = Verify.ReplaceIP(data);
                    data = Verify.ReplaceMAC(data);
                    data = Verify.ReplaceDate(data);
                    data = Verify.ReplaceTime(data);
                    data = Verify.ReplacePath(data);
                    data = Verify.ReplaceDigit(data);
                    data = Verify.ReplaceHapazardWord(data);
                    data = Verify.ReplaceIndex(data);
                    
//                    data = Verify.ReplaceDigit2(data);
                    data = data.replaceFirst(":", "");
//                    data = data.replaceAll(",", " ");
                    
                    Token = new StringTokenizer(data, tokenizor);
                    
//                    Pattern p = Pattern.compile(" *([^(][^ ]+|\\([^\\)]+\\)?)");
//                    Matcher m = p.matcher(data);
//                    while(m.find()){
//                        String store = data.substring(m.start(),m.end()).trim();
//                         sql.Addwords(store);         //Add the words to the Sql query...
//                    }

                    String TempToken;
                    while (Token.hasMoreElements()) {
                        TempToken = Token.nextToken();
                        sql.Addwords(TempToken.trim());         //Add the words to the Sql query...
//                        System.out.println(TempToken);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "File doesn't Exist", "File Error", 2);
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "File Not Found Exception", 2);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "IOException", 2);
        }
    }
}

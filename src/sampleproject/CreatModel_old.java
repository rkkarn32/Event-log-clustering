/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sampleproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author Ramesh
 */ 
public class CreatModel_old {
    
    SqlConnection sql =new SqlConnection();
    VerifyWords Verify = new VerifyWords();
    ResultSet rs;
    
    public void ValueCalculation(){
        try{
            FileReader readfile = new FileReader(new Variable().InputFileName);
            BufferedReader bfread = new BufferedReader(readfile);
            String sentences;
            String Delim =" ,";
            StringTokenizer Token;
            
            while ((sentences = bfread.readLine()) != null) {
                
                sentences = sentences.replaceAll("'", "");
                sentences = Verify.ReplaceIP(sentences);
                sentences = Verify.ReplaceMAC(sentences);
                sentences = Verify.ReplaceDate(sentences);
                sentences = Verify.ReplaceTime(sentences);
                sentences = Verify.ReplacePath(sentences);
                sentences = Verify.ReplaceDigit(sentences);
//                sentences = Verify.ReplaceDigit(sentences);
                
                long sum=0;
                
                String CheckWord;
                Token = new StringTokenizer(sentences, Delim);
                while (Token.hasMoreElements()) {
                    CheckWord = Token.nextToken();
                    String query ="SELECT * FROM word WHERE word='"+CheckWord+"'";
//                    System.out.println(query);
                    rs = sql.ExecuteQuery(query);
                    rs.next();
//                    System.out.println(rs.getString("word")+" "+rs.getString("count")+" "+rs.getString("weightage"));
                    sum+= Integer.parseInt(rs.getString("count"))* Integer.parseInt(rs.getString("weightage"))* Str2Num(CheckWord);
                    System.out.println(CheckWord+ " = " + sum);
                }
                System.out.println(sentences +" -->> "+ sum);
                String UpdateQuery = "INSERT INTO model VALUES("+null+",'"+ sentences+"','"+sum+"')";
                sql.UpdateQuery(UpdateQuery);
            }
        }catch(FileNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "File not found", 2);
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "IOException ", 2);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException_CreatModel", 2);
        }
    }
    
    public void PatternAnalyser(){
        try{
            FileReader readfile = new FileReader(new Variable().InputFileName);
            BufferedReader bfread = new BufferedReader(readfile);
            String sentences;
            String Delim =" ,";
            StringTokenizer Token;
            
            FileWriter tempwrite = new FileWriter("temp.txt");
            BufferedWriter tempbuffer = new BufferedWriter(tempwrite);
            
            while ((sentences = bfread.readLine()) != null) {
                
                sentences = sentences.replaceAll("'", "");
                sentences = Verify.ReplaceIP(sentences);
                sentences = Verify.ReplaceMAC(sentences);
                sentences = Verify.ReplaceDate(sentences);
                sentences = Verify.ReplaceTime(sentences);
                sentences = Verify.ReplacePath(sentences);
                sentences = Verify.ReplaceDigit(sentences);
                sentences = Verify.ReplaceIndex(sentences);
                sentences = Verify.ReplaceDigit2(sentences);
                
                long sum=0;
                
                String CheckWord;
                                                     //Holds the frequency of all word of Sentence
                ArrayList<Integer> WordFreq = new ArrayList<Integer>();
                Token = new StringTokenizer(sentences, Delim);
                while (Token.hasMoreElements()) {
                    CheckWord = Token.nextToken();
                    String query ="SELECT * FROM word WHERE word='"+CheckWord+"'";
                    rs = sql.ExecuteQuery(query);
                    rs.next();
                    Variable variable = new Variable();
                    for(int i =0 ; i< variable.ReplaceString.size(); i ++){
                        if(rs.getString("word").equalsIgnoreCase(variable.ReplaceString.get(i))){
                            break;
                        }else if(i== variable.ReplaceString.size()-1){
                            WordFreq.add(Integer.parseInt(rs.getString("count")));
                        }
                    }
//                    System.out.println(rs.getString("word")+" "+rs.getString("count")+" "+rs.getString("weightage"));
                    sum+= Integer.parseInt(rs.getString("count"))* Integer.parseInt(rs.getString("weightage"))* Str2Num(CheckWord);
//                    System.out.println(CheckWord+ " = " + sum);
                }
                //Cut_off frequency calculation...
                
                double SD = new Stat().caclStanDev(WordFreq);
                double mean = new Stat().calcMedian(WordFreq);
                    
                int cut_off_value;
 //               if(mean>SD)
                    cut_off_value= (int) Math.abs(mean - SD*.70);                //Cut off frequency is 70% of SD..
                    if(cut_off_value <= Math.abs(.20 * mean)){
                        cut_off_value = (int) (0.20 * mean);
                    }
                        
//                else
//                    cut_off_value=(int)Math.abs(SD- mean);
                Token = new  StringTokenizer(sentences, Delim);
                StringBuilder sb = new StringBuilder(sentences);
                    
                while(Token.hasMoreElements()){
                    CheckWord = Token.nextToken();
                    String query ="SELECT * FROM word WHERE word='"+CheckWord+"'";
                    rs = sql.ExecuteQuery(query);
                    rs.next();
                    
                    if(Integer.parseInt(rs.getString("count"))< cut_off_value ){
                        String word= rs.getString("word");
                        String replace = "(** "+rs.getString("word")+"="+rs.getString("count") +" **)";
//                        String replace = " **** ";                        
                        sum -= Integer.parseInt(rs.getString("count"))* Str2Num(rs.getString("word")) * Integer.parseInt(rs.getString("weightage"));


                        int index = sb.indexOf(word);
                        if(index>=0){
                            sb.delete(sb.indexOf(word), sb.indexOf(word)+word.length());
                            sb.insert(index, replace);
                        }
                    }
                }
                System.out.println(sb);
//                tempbuffer.write(sb.toString()+"\n");
                for(int i = 0 ; i < WordFreq.size();i++){
                    System.out.print(WordFreq.get(i)+"   ");
                }
                System.out.println("SD = "+SD+"   Mean = "+mean);
                String UpdateQuery = "INSERT INTO model VALUES("+null+",'"+ sb.toString()+"','"+sum+"')";
                sql.UpdateQuery(UpdateQuery);
            }
        }catch(FileNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "File not found", 2);
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "IOException ", 2);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException_CreatModel", 2);
        }
    }
    
    public void Model2File(){
        try{
            FileWriter fwrt = new FileWriter(Variable.OutputFileName);
            BufferedWriter bf =new BufferedWriter(fwrt) ;
            String Query = "SELECT * FROM model ORDER BY value DESC";
            //String Query = "SELECT * FROM model";
            rs = sql.ExecuteQuery(Query);
            int a=0;
            while(rs.next()){
                bf.write(rs.getString("value")+"   "+rs.getString("sentence")+"\n");
                //bf.write(rs.getString("sentence")+"\n");
                bf.flush();
                a++;
            }
            System.out.println(a);
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "IOException Error", 2);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException Error", 2);
        }
        
        
        // Write the data of Model Table by "Desc order". to the file to analyse it visually ...
    }
    private int Str2Num(String Word) {
//        ArrayList<String> sgrings = new ArrayList<String>(Arrays.asList("rameshe","rameshea","ganesh","lekhnath","sanjay"));
//            System.out.println(sgrings.get(i));
            int sum =0;
//            System.out.println("char = value = sum");
            for(int j =0; j< Word.length();j++){
                int value=0;
                char c= Word.charAt(j);
                if(Character.isLetter(c)){
                    if(Character.isLowerCase(c)) 
                        value =c-'a'+21;
                            
                    else if(Character.isUpperCase(c))
                        value =c-'A'+21;
                }else if(Character.isDigit(c))
                    value =c-'0';
                        value*=2;
                        
                        sum+= ((Word.length()-j)/2 +1)*value;
//                        System.out.println(" "+c +"   =   "+ value +"  =  "+sum);
            }
//            System.out.println(sgrings.get(i)+" = "+ sum);
            return sum;                
        }
    
    public static void main(String[] args) {
//        System.out.println("x111f == "+ new CreatModel().Str2Byte("x111f"));
//        System.out.println("x10bd == "+ new CreatModel().Str2Byte("x10bd"));
//        new CreatModel().Model2File();
        
        int test = new CreatModel_old().Str2Num("bridge");
        System.out.println("ramesh ==" +test);
    }
    
}

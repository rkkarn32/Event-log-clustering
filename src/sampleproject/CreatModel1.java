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
public class CreatModel1 {
    
    SqlConnection sql =new SqlConnection();
    VerifyWords Verify = new VerifyWords();
    ResultSet rs;
    
    double td;                  //Term frequency
    double idf;                 //inverse document frequency
    
    public void PatternAnalyser(){
        try{
            FileReader readfile = new FileReader(new Variable().InputFileName);
            BufferedReader bfread = new BufferedReader(readfile);
            String sentences;
            String Delete ="TRUNCATE TABLE  model";
            sql.ExecuteQuery(Delete);
            //String Delim = Variable.Delim;
            StringTokenizer Token;
            
            FileWriter tempwrite = new FileWriter("temp.txt");
            BufferedWriter tempbuffer = new BufferedWriter(tempwrite);
            int count =0;
            while ((sentences = bfread.readLine()) != null) {
//                count++;
//                System.out.println("\n"+sentences);
                
                sentences = sentences.replaceAll("'", "");
                sentences = Verify.ReplaceIP(sentences);
                sentences = Verify.ReplaceMAC(sentences);
                sentences = Verify.ReplaceDate(sentences);
                sentences = Verify.ReplaceTime(sentences);
                sentences = Verify.ReplacePath(sentences);
                sentences = Verify.ReplaceDigit(sentences);
                sentences = Verify.ReplaceIndex(sentences);
                sentences = Verify.ReplaceDigit2(sentences);
                
//                sentences = sentences.replaceFirst(":", "");
                System.out.println(sentences);
                
//                sentences = sentences.replaceAll(",", " ");
                String []part = sentences.split(":",2);
                double grandSum = 0;
                double sum[] = new double[2];
                Upper_loop:
                for(int j = 0 ;j< part.length; j++){
                    sum[j]=0;
//                    ArrayList <String> holdWord = new ArrayList<String>();
//                    Pattern p = Pattern.compile(" *([^(][^ ]+|\\([^\\)]+\\)?)");
//                    Matcher m = p.matcher(sentences);
//                    while(m.find()){
//                        holdWord.add(sentences.substring(m.start(),m.end()).trim());
//                    }
//                    String []words = new String[holdWord.size()];
//                    words = holdWord.toArray(words);
                    
                    String [] words = part[j].trim().split(Variable.Delim);

                    System.out.println("Words lenght = "+ words.length);
                    ArrayList<Integer> wordfreq = new ArrayList<Integer>();     // Holds the Word's frequency which is used for Standard deviations
                    ArrayList<Integer> allWordfreq = new ArrayList<Integer>();  // Holds the word's frequency of all the words appear in the sentences
                    ArrayList<Integer> weightage = new ArrayList<Integer>();    // holds the weightage from database..
                    
                    int totalFreq = 0;
                    
                    for(int i = 0 ; i < words.length;i++){
                        String query = "SELECT * FROM word WHERE word ='"+words[i]+"'";
                        rs = sql.ExecuteQuery(query);
                        if(rs.next()){
                            int freq = Integer.parseInt(rs.getString("count"));
                            int wordWeight= Integer.parseInt(rs.getString("weightage"));
                            allWordfreq.add(freq);
                            weightage.add(wordWeight);
                            totalFreq += freq;
                            if(j>0){
                                if(!Variable.ReplaceString.contains(words[i]))
                                    wordfreq.add(freq);
                            }
                            
    //                        sum += freq * wordWeight * Str2Num(words[i]);
    //                        System.out.println(sum);
                        }else{
//                            System.out.println(words[i]);
                            continue Upper_loop;
                        }
                    }
                    double SD = new Stat().caclStanDev(wordfreq);
                    double mean = new Stat().calcMedian(wordfreq);
                    double cut_off_value;
     //               if(mean>SD)
                    cut_off_value=  Math.abs(mean - SD*.75);                //Cut off frequency is 70% of SD..
                    if(cut_off_value <= Math.abs(.20 * mean))
                        cut_off_value =  0.20 * mean;
                    if(cut_off_value<1)
                        cut_off_value = 1;
                    for(int i =0 ; i <words.length; i++){
                        System.out.println(weightage.get(i));
                        if(j>0){
                            if(allWordfreq.get(i) < cut_off_value){
                                words[i] = "(** "+ words[i]+"="+allWordfreq.get(i)+" **)";      //replaceing the lower frequency word..
                                double weight = Math.abs(Math.log10(allWordfreq.get(i)/((double)totalFreq+1)));
//                                sum[j] += 0.25 * (words.length-i) * Str2Num(words[i])* weight;//* allWordfreq.get(i)/((double)totalFreq+1);                                
//                                sum[j] += weightage.get(i) * Str2Num(words[i])* allWordfreq.get(i);//* allWordfreq.get(i)/((double)totalFreq+1);
                            }else{
//                                double weight = Math.log10(totalFreq/allWordfreq.get(i));
                                double weight = Math.abs(Math.log10(allWordfreq.get(i)/((double)totalFreq+1)));
//                                sum[j] += (words.length-i) * Str2Num(words[i])* weight;//* allWordfreq.get(i)/((double)totalFreq+1);
                                sum[j] += weightage.get(i) * Str2Num(words[i])* allWordfreq.get(i);//* allWordfreq.get(i)/((double)totalFreq+1);
                            }
                        }else{
//                            double weight = Math.log10(totalFreq/allWordfreq.get(i));
                            double weight = Math.abs(Math.log10(allWordfreq.get(i)/((double)totalFreq+1)));
//                            sum[j] += (words.length-i) * Str2Num(words[i])* weight;//* allWordfreq.get(i)/((double)totalFreq+1);
                            sum[j] += weightage.get(i) * Str2Num(words[i])* allWordfreq.get(i);//* allWordfreq.get(i)/((double)totalFreq+1);
                        }
//                        System.out.println(totalFreq+"  "+allWordfreq.get(i) +"  "+weight);
                }
                    part[j] = Array2String(words);
//                    grandSum+= 2*(2-j)*sum;
                    System.out.println(part[j] + " "+sum+"--"+totalFreq);
                    for(int value : allWordfreq){
                        System.out.print(value+"   ");
                    }
                    System.out.println("  === >> CutOff value="+cut_off_value+ ", Mean="+mean+", SD="+SD);
                }
                grandSum = sum[0]+sum[1];
                String updateString = Array2String(part);                
                System.out.println((long)grandSum+ "  "+updateString);
//                String draw = "INSERT INTO draw VALUES("+(long)sum[0]+","+(long)sum[1]+")";                
//                sql.UpdateQuery(draw);
                String UpdateQuery = "INSERT INTO model VALUES("+null+",'"+ updateString+"','"+(long)grandSum+"')";
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
    
    private String Array2String(String []words){
        String Sentence = null;
        if(words.length <= 1)
            return words[0].trim();
        if(words.length ==2 )
            return (words[0].trim()+" "+words[1].trim());
        
        Sentence = words[0].trim();
        for(int i=1; i < words.length; i++){
            Sentence += " "+words[i].trim();
        }
        return Sentence;
    }
        
    public void Model2File(){
        try{
            FileWriter fwrt = new FileWriter("w1.txt");
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
    
    public void CreateCluster_2(){          // Using Some relative value as a threshold value
        double dyn_threshold = 0.01;
        try{
            int a=0;
            String Filename = "clusters1/w"+a+".txt";
            FileWriter fwrt = new FileWriter(Filename);
            BufferedWriter bf =new BufferedWriter(fwrt) ;
            
            String Query = "SELECT * FROM model ORDER BY value DESC";
            rs = sql.ExecuteQuery(Query);
            int prev_value = 0;
            while(rs.next()){
                if(rs.isFirst()){           //If this is the first Row of the messages..
                    prev_value = Integer.parseInt(rs.getString("value"));
                    bf.write(rs.getString("value")+"   "+rs.getString("sentence")+"\n");
                    bf.flush();
                    continue;
                }
                int curr_value = Integer.parseInt(rs.getString("value"));
                int value = Math.abs(prev_value - curr_value);
                double percentage = (double) value / prev_value;
                System.out.println("percentage= "+ percentage);
                if( percentage > dyn_threshold){
//                    a++;
//                    Filename = "clusters1/w"+a+".txt";
//                    fwrt = new FileWriter(Filename);
//                    bf =new BufferedWriter(fwrt) ;
                    bf.write("\n\n");
                }
                prev_value= Integer.parseInt(rs.getString("value"));
                bf.write(rs.getString("value")+"   "+rs.getString("sentence")+"\n");
                //bf.write(rs.getString("sentence")+"\n");
                bf.flush();
            }
            System.out.println(a);
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "IOException Error", 2);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException Error", 2);
        }
    }
    
    public static void main(String[] args) {
//        System.out.println("x111f == "+ new CreatModel().Str2Byte("x111f"));
//        System.out.println("x10bd == "+ new CreatModel().Str2Byte("x10bd"));
//        new CreatModel().Model2File();
        
        int test = new CreatModel1().Str2Num("bridge");
        System.out.println("ramesh ==" +test);
    }
    
}

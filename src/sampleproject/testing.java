
package sampleproject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * testing of the javadoc 
 * and it worked perfectelly 
 * i am happy very happy now .
 */



public class testing {
    public static void main(String[] args) {
//        check();
        CreatModel cr = new CreatModel();
        cr.CreatCluster_test("this is done ok done ", 3434940);
        cr.CreatCluster_test("this is done ok done ", 383883);
        cr.CreatCluster_test("this is done ok done ", 383834);
        
 }
    private void skipParenthesis(){
        String s = "This is the alsdjk(my real job) which is great, and (also somet stuff";

     ArrayList<String> words = new ArrayList<String>();
     
     Pattern p = Pattern.compile(" *([^(][^ ]+|\\([^\\)]+\\)?)");
     Matcher m = p.matcher(s);
     
     
     while(m.find()) {
        words.add(s.substring(m.start(),m.end()).trim());
     }
     for(String word : words) {
        System.out.println(word);
     }
     
     String []array = new String[words.size()];
     words.toArray(array);
     System.out.println(array.length+  " = "+ array[array.length-1]);
    }
    
    private static void check(){
        VerifyWords Verify = new VerifyWords();
        String sentences = "Sep bridge,syslog: syslogd, startup";
        System.out.println(sentences);
        
        String Delim= " ,";
        String []split = sentences.split(Delim);
        for(String show: split){
            System.out.println(show);
        }
        System.out.println(split.length);
        
        
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sampleproject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ramesh
 */
public class VerifyWords {
  
   /**
     * for testing the integer number
     * @param Number 
     * Which number we have to check
     * @return 
     * True if number is integer otherwise false
     */ 
  
  public boolean IsInteger(String Number){             // Checks whether the input String is Number or Not...
      boolean Value= true;
        for(int i = 0; i<Number.length(); i++)
        {
            if(Number.charAt(i)> '9' || Number.charAt(i) < '0')
                return false;
        }
      return Value;
  }
  public String ReplaceIP(String Word){                   // Checks the String that is it IP or not...
      
      String IPPatt ="(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
                                "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
      Pattern pattern = Pattern.compile(IPPatt);
      Matcher matcher = pattern.matcher(Word);
      
      while (matcher.find()){
          Word = Word.replaceFirst(IPPatt, "ReIP");         //Replace all the IP address by "IP
      }
      return Word;
    }
  public String RegTest(String Word){                   // Checks the String that is it IP or not...
      
      String IPPatt ="(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
                                "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
      Pattern pattern = Pattern.compile(IPPatt);
      Matcher matcher = pattern.matcher(Word);
      
      while (matcher.find()){
          Word = Word.replaceFirst(IPPatt, "ReReg");         //Replace all the IP address by "IP
      }
      return Word;
    }
  public String ReplaceMAC(String Word){
      
      String MacPatt="(([0-9a-f][0-9a-fA-F])\\:){5}" + "([0-9a-f][0-9a-fA-F])";         //MAC pattern for Regular expression.
      Pattern pattern = Pattern.compile(MacPatt);
      Matcher matcher = pattern.matcher(Word);
      
      while (matcher.find()){                           
          Word = Word.replaceFirst(MacPatt, "ReMAC");         //Replace all the IP address by ReMAC
      }
      return Word;
  }
  public String ReplaceDate(String Word){
      String DatPatt= "(Jan|Feb|Mar|Apr|Jun|May|June|July|Aug|Sep|Oct|Nov|Dev)(\\s*|[-/])(0[1-9]|[12][0-9]|3[01]|[1-9])";
      Pattern pattern = Pattern.compile(DatPatt);
      Matcher matcher = pattern.matcher(Word);
      
      while (matcher.find()){
          Word = Word.replaceFirst(DatPatt, "ReDate");         //Replace all the IP address by "ReDate
      }
      return Word;
  }
  public String ReplaceDigit(String Word){
      
      //String DigitPatt="(\\d*\\s+)|(\\s+\\d*)|(\\s+\\d*\\s+)";         //MAC pattern for Regular expression.
      String DigitPatt=" \\d+ |^\\d+ | \\d+$";
//      String DigitPatt=" |^+\\d+";
      Pattern pattern = Pattern.compile(DigitPatt);
      Matcher matcher = pattern.matcher(Word);
      
      while (matcher.find()){                           
          Word = Word.replaceFirst(DigitPatt, " ReDigit ");         //Replace all the IP address by ReMAC
      }
      return Word;
  }
  public String ReplaceDigit2(String word){
      
        String DigitPatt = "( \\<\\d+\\> )|( \\<\\d+\\>$)";
        Pattern  pattern = Pattern.compile(DigitPatt);
        Matcher matcher =  pattern.matcher(word);
        
        while(matcher.find()){
            word = word.replaceFirst(DigitPatt, " <**> ");
        }
        return word;
  }
  
  public String ReplaceIndex(String word){
        //String DigitPatt = "( \\<\\d+\\> )|( \\<\\d+\\>$)";
        //String IndexPatt1 = "(\\w*)(\\[\\d+\\])(\\w*)";
        String IndexPatt1 = "\\[\\d+\\]";
        word = word.replaceAll(IndexPatt1, "\\[**\\]");
//        word = word.replaceAll("(.*)(\\[\\d+\\])(.*)", "$1\\[**\\]$3");     //Converts all remaining index into format..
        
        String IndexPatt2 = "\\([\\w+\\s+]{3,}\\)";        //for parenthesis..
        word = word.replaceAll(IndexPatt2, "(**)");
        String IndeString3 ="<\\d+>";             //For angle bracket;
        word = word.replaceAll(IndeString3, "<**>");
        
        String IndexPatt4 = " ([a-zA-Z]+)(\\d+)$";
        word = word.replaceAll(IndexPatt4, " $1**");
        String IndexPatt5 = "^([a-zA-Z]+)(\\d+) ";
        word = word.replaceAll(IndexPatt5, "$1** ");
        String IndexPatt6 = " ([a-zA-Z]+)(\\d+) ";
        word = word.replaceAll(IndexPatt6, " $1** ");
        
        //String patt = "([:=]+) *([\\d*\\w*])+";
        String patt = ":\\d+";
        word = word.replaceAll(patt, ":**");
        return word;
  }
  
  public String ReplaceHapazardWord(String word){
      String patt="\\d*([a-zA-Z]+\\d+){2,}\\w*";
      word = word.replaceAll(patt, "**");
      return word;
  }
  
  public String ReplaceTime(String Word){
      String TimePatt= "([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])";
      Pattern pattern = Pattern.compile(TimePatt);
      Matcher matcher = pattern.matcher(Word);
      
      while (matcher.find()){                           
          Word = Word.replaceFirst(TimePatt, "ReTime");         //Replace all the IP address by "IP
      }
      return Word;
  }
  public String ReplaceDateAndTime(String Word){
      String DatTimePatt= "(Jan|Feb|Mar|Apr|Jun|May|June|Oct|Nov|Dev)(\\s*|[-/])(0[1-9]|[12][0-9]|[1-9])"
                                         + "\\s*"+
                                        "([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])";
      Pattern pattern = Pattern.compile(DatTimePatt);
      Matcher matcher = pattern.matcher(Word);
      
      while (matcher.find()){                           
          Word = Word.replaceFirst(DatTimePatt, "ReDateTime");         //Replace all the IP address by "ReDate
      }
      return Word;
  }
  public String ReplacePath(String Word){
      String PathPatt="(/[a-z0-9]*)+"+"(\\.\\w+)?";
      Pattern pattern = Pattern.compile(PathPatt);
      Matcher matcher = pattern.matcher(Word);
      while(matcher.find()){
          Word = Word.replaceFirst(PathPatt, "RePath");
      }
      return Word;
  }
  public boolean IsMAC(String word){
        return( word.matches("[A-Fa-f1-9][A-Fa-f1-9]:[A-Fa-f1-9][A-Fa-f1-9]:[A-Fa-f1-9][A-Fa-f1-9]:[A-Fa-f1-9][A-Fa-f1-9]:[A-Fa-f1-9][A-Fa-f1-9]:[A-Fa-f1-9][A-Fa-f1-9]"));
    }
  public boolean isValidDate(String inDate) {
        if (inDate == null) {
            return false;
        }
        //set the format to use as a constructor argument
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd HH:mm:ss");
        if (inDate.trim().length() != dateFormat.toPattern().length()) {
            return false;
        }
        dateFormat.setLenient(false);

        try {
            //parse the inDate parameter
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
  public boolean IsTime(String Word) {

        boolean Value = true;               // Determines that String is MAC or not....

        ArrayList<String> MacArray = new ArrayList<String>();
        StringTokenizer Token = null;
        String Delim = ":";
        Token = new StringTokenizer(Word, Delim);
        String TempString;

        while (Token.hasMoreElements()) {
            TempString = Token.nextToken();
            if (TempString.length() == 2 && IsInteger(TempString)) {
                MacArray.add(TempString);
            } else {
                return false;
            }
        }
        if (MacArray.size() == 3) {
            return Value;
        } else {
            return false;
        }
    }
  
  public static void main(String[] args) {
        VerifyWords vf = new VerifyWords();
        String test = "Sep 31 00:01:00 bastion CROND[9467]: (root) CMD (run-parts /etc/cron.hourly) ";
        //String test = "Sep 26 15:01:00 bastion CROND[1231]: (root) CMD (run-parts /etc/cron.hourly)";
        test = vf.ReplaceDate(test);
        
        System.out.println(test);
    }
  
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sampleproject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Ramesh
 */
public class Variable {
    public static final String InputFileName = "working_file.log";
    public static final String OutputFileName ="w.txt";
    public static final ArrayList <String> ReplaceString =new ArrayList<String>(Arrays.asList("ReDate","ReIP","ReReg","ReMAC","ReDigit","ReTime","ReDateTime","RePath","(**),<**>,**"));
//    public static final String Delim = " *([^(][^ ]+|\\([^\\)]+\\)?)";
    public static final String Delim = "[, ]+";
    //public static String InputFileName = "merged.log";
}

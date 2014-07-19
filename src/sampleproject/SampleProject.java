/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sampleproject;
import java.io.IOException;

/**
 *
 * @author Ramesh
 */
public class SampleProject {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
//        WordBackUP Work = new WordBackUP();
//        Work.ReadNUpdateFile();
        
        CreatModel CrModel = new CreatModel();
        CrModel.PatternAnalyser();             //Creates the model in Model Table..
//        CrModel.Model2File();                   //Save the value to the file...
//        CrModel.CreatCluster_2();
      
        CreatModel1 CrModel1 = new CreatModel1();
//        CrModel1.PatternAnalyser();
//        CrModel1.Model2File();
//        CrModel1.CreateCluster_2();
//        new MakeCluster().CreateCluster_1();
        
        
        CreatModel1 CrOld = new CreatModel1();
//        CrOld.PatternAnalyser();
//        CrOld.Model2File();
//        CrOld.CreateCluster_2();
    }
}
package sampleproject;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Ramesh
 */
public class MakeCluster {
    int fix_threshold;                  //Constant Threshold
    double dyn_threshold;               //Dynamic threshold

    public MakeCluster() {
        fix_threshold = 100000;
        dyn_threshold = 0.01;
    }
    
    SqlConnection sql = new SqlConnection();
    ResultSet rs;
    public void CreateCluster_1(){                              //Uses Constant value 1,00,000 as a threshold value
        try{
            int a=0;
            String Filename = "clusters/w"+a+".cluster";
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
                int diff_value = Math.abs(prev_value - curr_value);
                System.out.println("Diff = "+diff_value);
                if(diff_value > fix_threshold){
                    a++;
                    Filename = "clusters/w"+a+".cluster";
                    fwrt = new FileWriter(Filename);
                    bf =new BufferedWriter(fwrt) ;
                }
                bf.write(rs.getString("value")+"   "+rs.getString("sentence")+"\n");
                bf.flush();
                prev_value= Integer.parseInt(rs.getString("value"));
            }
            System.out.println(a);
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "IOException Error", 2);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException Error", 2);
        }
    }

    public void CreateCluster_2(){          // Using Some relative value as a threshold value
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
                float percentage = (float) value / prev_value;
                System.out.println("percentage= "+ percentage);
                if( value > dyn_threshold){
                    a++;
                    Filename = "clusters1/w"+a+".txt";
                    fwrt = new FileWriter(Filename);
                    bf =new BufferedWriter(fwrt) ;
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
    
    public static void main(String []arg){
    new MakeCluster().CreateCluster_2();
}
}

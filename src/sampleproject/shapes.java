/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sampleproject;

import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Ramesh
 */
public class shapes extends JFrame{
    shapes(){
        super("just implementing those");
    }
    
    @Override
    public void paint(Graphics g) {
        SqlConnection sql = new SqlConnection();
        String max= "SELECT max(x) as max_x,max(y) as max_y FROM draw";
        ResultSet rs;
        int max_x=0;
        int max_y=0;
        rs = sql.ExecuteQuery(max);
        try{
        if(rs.next()){
            max_x= Integer.parseInt(rs.getString("max_x"));
            max_y= Integer.parseInt(rs.getString("max_y"));
            System.out.println("max found");
        }else{
            JOptionPane.showMessageDialog(null, "may be a error", "error", 2);
        }
        
        FileWriter fw = new FileWriter("draw.txt");
        BufferedWriter bf = new BufferedWriter(fw);
        super.paint(g);    
        String query = "SELECT * FROM draw";
        rs = sql.ExecuteQuery(query);
        int i=0;
        while(rs.next()){
            i++;
            int x = rs.getInt("x");
            int y = rs.getInt("y");
            int plot_x =(int)( ((double)x*1200.0)/max_x);
            int plot_y =(int)( ((double)y*700.0)/max_y);
            
            if(x==max_x | y==max_y){
                System.out.println("x="+plot_x+"  y="+plot_y+"  count="+ i);
            }
            
            bf.write(plot_x+","+plot_y+"\n");
            bf.flush();
//            g.fillOval(plot_x, plot_y, 5, 5);
//            System.out.println("x="+plot_x+"  y="+plot_y+"  count="+ ++i);
        }
//        g.fillOval(100, 100, 15, 15);
        g.fillOval(100, 288, 35, 35);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException_Shapes", 2);
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "IOException", 2);
        }
    }
    public static void main(String[] args) {
        shapes app = new shapes();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setSize(1200, 700);
        app.setVisible(true);
    }
}

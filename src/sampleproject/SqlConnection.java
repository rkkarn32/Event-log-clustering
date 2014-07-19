/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sampleproject;

import com.mysql.jdbc.Statement;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Ramesh
 */
public class SqlConnection{
    private Connection con;
    private Statement stmt;
    private ResultSet rs; 
    private int IsUpdate;   
//    private ResultSet rs;
    SqlConnection() {
        String uname="root";
        String paswrd= "";
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost/major_project", uname,paswrd);
            stmt =  (Statement)con.createStatement();
        }catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage()+" From SqlConnection ()", "ClassNotFoundException", 2);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage()+" From SqlConnection ()", "SqlException", 2);
        }
    }
    public ResultSet ExecuteQuery(String query){        // Executes the search statements of the query
        try 
        {
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("SQLException in ExecuteQuery is "+ ex.getMessage());
        }
        return rs;
    }
    
    private boolean  IsWordExist(String query){        // Check that the word is exist or not , if yes then increase that words value by 1....
        boolean Isexist=false;
        try{
            rs = stmt.executeQuery(query);
            rs.last();
            int NumRows= rs.getRow();
            if(NumRows != 0){
                Isexist= true ;
                int total = Integer.parseInt(rs.getString("count")) +1;         // increase the word's count at in the database when they already exists.
//                System.out.println("now adding the value "+ total+ " and id is "+ rs.getString("ID")) ;
                String Update = "UPDATE word  SET count = '"+total +"' WHERE id = '"+rs.getString("ID")+"'";
                UpdateQuery(Update);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException in ExecuteQuery is "+ ex.getMessage());
        }   
        return Isexist;
    }
    
    public void Addwords(String word){
        String CheckinWord = "SELECT * FROM word where word ='"+word+"'";            //String for check that Word already exist or not
        
        if( !IsWordExist( CheckinWord )){
            int weightage;
            if(word.equalsIgnoreCase("ReMAC") | word.equalsIgnoreCase("ReIP") | word.equalsIgnoreCase("ReDate") | word.equalsIgnoreCase("ReTime"))
                weightage = 1;
            else
                weightage = 20;
            
            String query = "INSERT INTO word VALUES ("+ null +",'"+ word + "','1','"+weightage+"')";
            UpdateQuery(query);
        }
    }
    
    public void UpdateQuery(String query){              //Update the database ... 
        try {
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 2);
        }
    }
    
    public int UpdateQueryReturn(String query){              //Update the database and return the result... 
        try {
            IsUpdate =stmt.executeUpdate(query);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 2);
        }
        return IsUpdate;
    }
    
    protected void SqlConnection(){
        try {
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error", 3);
        }
    }   
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sampleproject;

import java.util.ArrayList;

/**
 *
 * @author Ramesh
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class Stat {
    
    //this function for calculation of standard deviation 
    static double caclStanDev(ArrayList <Integer>data){
        return Math.pow(caclVariance(data),0.5);
    }
    //this function for calculation of variance of the sequence
    static double caclVariance(ArrayList<Integer> data){
        if (data.size() == 1)           //For the list with single element have varance is 0
            return 0.0;
        
        int n = data.size();
        double total = 0;
        double sTotal = 0;
        double scalar = 1/(double)(n-1);
        for(int i =0; i<n; i++){
            total += data.get(i);
            sTotal += Math.pow(data.get(i), 2);
        }
        return(scalar*(sTotal - (Math.pow(total, 2)/n)));
        
    }
    //this function for calculatoin of median
     static double calcMedian(ArrayList<Integer> data){
         double total = 0 ;
         for (int i = 0; i <data.size() ; i++)
             total += data.get(i);
         return(total/data.size());
     }

    
}
////////////////////////////////////////////////////////////////////////////////////////////////



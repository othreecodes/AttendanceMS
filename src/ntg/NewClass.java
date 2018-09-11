/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntg;

import java.util.ArrayList;

/**
 *
 * @author OTHREE
 */
public class NewClass {
    
    
    public static void main(String[] args) {
        
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1000);
        
        if(list.contains(1000)){
            System.out.println("yes");
        }
        else{
            System.out.println("nah");}
    
    }
    
}

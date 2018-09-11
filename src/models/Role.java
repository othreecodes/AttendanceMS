/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.HashMap;

/**
 *
 * @author OTHREE
 */
public class Role extends HashMap<Object, Object>{

    public Role() {
    this.put("st", "Student");
    this.put("lt", "Lecturer");
    this.put("sb", "Sub Administrator");
    this.put("sa", "Super Administrator");
    this.put("Student","st");
    this.put("Lecturer","lt");
    this.put("Sub Administrator","sb");
    this.put("Super Administrator","sa");
    
    }

    
    
    @Override
    public Object get(Object key) {
        return super.get(key); //To change body of generated methods, choose Tools | Templates.
    }
    

}

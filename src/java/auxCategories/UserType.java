/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxCategories;

import java.io.Serializable;

/**
 *
 * @author ITWannaBe
 */
public enum UserType{
    UNDEFINED ("Undefined"),
    ADMINISTRATOR ("Administrator"),
    ATTENDANT ("Attendant"),
    MANAGER ("Manager")
    ;
    
   private final String label;

  private UserType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return this.label;
  }
}

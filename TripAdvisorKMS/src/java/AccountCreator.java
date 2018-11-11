/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author shenm9730
 */
@Named(value = "accountCreator")
@Dependent
@ManagedBean
@RequestScoped
public class AccountCreator {

    /**
     * Creates a new instance of AccountCreator
     */
  
    private ArrayList<Attraction> managedAttraction= new ArrayList<Attraction>();
    private RegularLogin theLoginAccount;
    private AdminLogin theAdminAccount;
    public AccountCreator(){
        theLoginAccount=null;
        theAdminAccount=null;
    }

    
   

    public ArrayList<Attraction> getmanagedAttraction() {
        return managedAttraction;
    }

    public void setmanagedAttraction(ArrayList<Attraction> managedAttraction) {
        this.managedAttraction = managedAttraction;
    }
    
}

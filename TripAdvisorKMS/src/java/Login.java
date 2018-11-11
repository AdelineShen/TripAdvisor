/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import java.sql.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author shenm9730
 */
@Named(value = "login")
@Dependent
@ManagedBean
@RequestScoped
public class Login implements Serializable{
    private AdminLogin theAdminAccount;
    private RegularLogin theLoginAccount;
    private String id="";
    private String psw ="";
       
    public String Login() 
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ("internalError");
        }
        final String db_url="jdbc:mysql://mis-sql.uhcl.edu/shenm9730";
        Connection conn=null;
        Statement stat=null;
        ResultSet rs=null;
    
    try
    {
        conn=DriverManager.getConnection(db_url,"shenm9730","1636900");
        stat=conn.createStatement();
        rs=stat.executeQuery("Select * from profile where ID = '"+id+"'");
        if(rs.next())
        {
           if(psw.equals("admin"))
            {
                return("WelcomeAdmin");
            }
            else if(psw.equals(rs.getString(2)))
            {
                return("WelcomeRegular");
            }
            else
            {
                return("Wrong password!");
            }
        }
        else
        {
            return("The login ID is not found!");
        }
    }
    catch(SQLException e)
    {
        e.printStackTrace();
        return ("internalError");
    }
    finally
    {
        try
        {
            conn.close();
            stat.close();
            rs.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    }

    public AdminLogin getTheAdminAccount() {
        return theAdminAccount;
    }

    public void setTheAdminAccount(AdminLogin theAdminAccount) {
        this.theAdminAccount = theAdminAccount;
    }

    public RegularLogin getTheLoginAccount() {
        return theLoginAccount;
    }

    public void setTheLoginAccount(RegularLogin theLoginAccount) {
        this.theLoginAccount = theLoginAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }
    
}

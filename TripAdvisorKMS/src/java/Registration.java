/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import java.sql.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author shenm9730
 */
@Named(value = "registration")
@Dependent
@ManagedBean
@RequestScoped
public class Registration {
    
       private String id="";
       private String password ="";
       private String tag="";
    public String register()
     {
         try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
            return ("Internal Error! Please try again later.");
        }
         Connection conn=null;
        Statement stat=null;
        ResultSet rs=null; 
        try
        {
            final String db_url="jdbc:mysql://mis-sql.uhcl.edu/shenm9730";
            conn=DriverManager.getConnection(db_url,"shenm9730","1636900");
            stat=conn.createStatement();
            rs=stat.executeQuery("Select * from profile where ID='"+id+"'");
            if(rs.next())
            {
                return("User ID taken. Account creation failed");
            }
            else
            {
            int r=stat.executeUpdate("Insert into profile values('"+id+"','"+password+"','"+tag+"','regular')");
            return("The new traveler account is created!");
            }
            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return("Account creation failed!");
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
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    
    
}

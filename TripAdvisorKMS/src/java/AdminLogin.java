/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author shenm9730
 */
@Named(value = "adminLogin")
@Dependent
public class AdminLogin {

    /**
     * Creates a new instance of AdminLogin
     */
    private String id;
    private String psw;
    Scanner input= new Scanner(System.in);
    
     public AdminLogin(String i, String p)
     {
        id = i;
        psw = p;
     }
     
        public void WelcomeAdmin(){
        
        String sel="";
            
        while(!sel.equals("x"))
        {
            //display the menu
            System.out.println();
            System.out.println("Please enter your selection");
            System.out.println("1: View pending requests");
            System.out.println("2: Add an attraction");
            
            System.out.println("x: Exit");
            sel=input.nextLine();
            if(sel.equals("1"))
            {
                ViewPending();
            }
            else if(sel.equals("2"))
            {
               add();
            }
            
            else
            {
                ;
            }
        }
        }
             
        public void ViewPending()
        {
        Connection conn=null;
        Statement stat=null;
        Statement stat2=null;
        ResultSet rs=null; 
        ResultSet rs2=null;
        try
        {
            final String db_url="jdbc:mysql://mis-sql.uhcl.edu/shenm9730";
            conn=DriverManager.getConnection(db_url,"shenm9730","1636900");
            stat=conn.createStatement();
            rs=stat.executeQuery("Select * from admin_requests Where Status='Pending'");
        
        while(rs.next())
            {
            System.out.println("*****************************");
            System.out.println("User Name - "+rs.getString(1));
            System.out.println("Name - "+rs.getString(2));
            System.out.println("Description - "+rs.getString(3));
            System.out.println("State - "+rs.getString(4));
            System.out.println("City - "+rs.getString(5));
            System.out.println("Tag - "+rs.getString(6));
            System.out.println("Status - "+rs.getString(7));
            
            System.out.println("Do you approve? Y or N");
            String approve=input.nextLine();
            String Name=rs.getString(2);
            String Desc=rs.getString(3);
            String State=rs.getString(4);
            String City=rs.getString(5);
            String Tag=rs.getString(6);            
            if(approve.equals("Y"))
            {
               stat2=conn.createStatement();
               Statement statExe = conn.createStatement();
               int q=statExe.executeUpdate("Update admin_requests set Status='Approved' Where Name='"+Name+"'");
               rs2=stat2.executeQuery("Select * from admin_requests where Status='Pending'");
               int r=stat2.executeUpdate("Insert into attraction values ('"+Name+"','"+Desc+"','"+State+"','"+City+"','"+Tag+"')");
               System.out.println("The new attraction is approved!");
            }
            else if(approve.equals("N"))
            {
                Statement statExe = conn.createStatement();
                int p=statExe.executeUpdate("Update admin_requests set Status='Rejected'Where Name='"+Name+"'");
                System.out.println("The new attraction is rejected!");
            }
            }
        }
        catch(SQLException e)
        {
            System.err.println("Your attempt failed!");
            e.printStackTrace();
        }
        finally
        {
         //close db
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
        
        public void add(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the name of this place:");
        String place=input.nextLine();
        System.out.println("Please write a short description");
        String desc=input.nextLine();
        System.out.println("What state is it in? (two-letter abbreviation)");
        String state=input.nextLine();
        System.out.println("What city is it in?");
        String city=input.nextLine();
        System.out.println("Please add a tag. Choose from History, Shopping, Beach, Urban, Nature or Family");
        String tag=input.nextLine();
        System.out.println("");
       
        Connection conn=null;
        Statement stat=null;
        ResultSet rs=null; 
        try
        {
            final String db_url="jdbc:mysql://mis-sql.uhcl.edu/shenm9730";
            conn=DriverManager.getConnection(db_url,"shenm9730","1636900");
            stat=conn.createStatement();
            rs=stat.executeQuery("Select * from attraction");
            int r=stat.executeUpdate("Insert into attraction values ('"+place+"','"+desc+"','"+state+"','"+city+"','"+tag+"')");
           
            System.out.println("The new attraction is created!");
        }
        catch(SQLException e)
        {
            System.err.println("Your attempt failed!");
            e.printStackTrace();
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

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }
    
}

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
import java.util.ArrayList;
import java.util.Scanner;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author shenm9730
 */
@Named(value = "regularLogin")
@Dependent
public class RegularLogin {

    /**
     * Creates a new instance of RegularLogin
     */
     private String id;
     private String psw;
     Scanner input = new Scanner(System.in);            
     final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/shenm9730";
     private ArrayList<Attraction> attr = new ArrayList<Attraction>();
   
     public RegularLogin(String i, String p)
     {
        id = i;
        psw = p;
     }
             
        
        public void Welcome(){
        Scanner input= new Scanner(System.in);
        String sel="";
            
        while(!sel.equals("x"))
        {
            //display the menu
            System.out.println();
            System.out.println("Please enter your selection");
            System.out.println("1: Create a new place");
            System.out.println("2: See your past questions");
            System.out.println("3: Post a review or Rate a place");
            System.out.println("4: See the average score of a place");
            System.out.println("5: Go to my favorite destinations");
            System.out.println("6: Answer other people's questions");
            System.out.println("7: You may Like... See top rated attractions");
            System.out.println("8: Search by tags");
            System.out.println("9: Search by state and city");
            System.out.println("x: Exit");
            sel=input.nextLine();
              if(sel.equals("1"))
            {
                createNewPlace();
            }
            else if(sel.equals("2"))
            {
                QandA();
            }
            else if(sel.equals("3"))
            {
                postReview();
            }
            else if(sel.equals("4"))
            {
                average();
            }
            else if(sel.equals("5"))
            {
                favorite();
            }
            else if(sel.equals("6"))
            {
                answer();
            }
            else if(sel.equals("7"))
            {
                mayLike();
            }
            else if(sel.equals("8"))
            {
               showTag();
            }
            else if(sel.equals("9"))
            {
               showCity();
            }
            else
            {
                ;
            }
        }
        }
        
    public void createNewPlace(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the name of this place:");
        String place=input.nextLine();
        System.out.println("Please write a short description");
        String desc=input.nextLine();
        System.out.println("What state is it in? Please enter abbreviation");
        String state=input.nextLine();
        System.out.println("What city is it in?");
        String city=input.nextLine();
        System.out.println("Please add a tag. Choose from History, Shopping, Beach, Urban, Nature or Family");
        String tag=input.nextLine();
        System.out.println();
       
        Connection conn=null;
        Statement stat=null;
        ResultSet rs=null; 
        try
        {
            final String db_url="jdbc:mysql://mis-sql.uhcl.edu/shenm9730";
            conn=DriverManager.getConnection(db_url,"shenm9730","1636900");
            stat=conn.createStatement();
            rs=stat.executeQuery("Select * from admin_requests");
            int r=stat.executeUpdate("Insert into admin_requests values('"+id+"','"+place+"','"+desc+"','"+state+"','"+city+"','"+tag+"','Pending')");
            System.out.println("The new attraction is created! Pending approval!");
     
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
    
    public void QandA()
    {
        Connection conn=null;
        Statement stat=null;
        ResultSet rs=null; 
         try
        {
            conn=DriverManager.getConnection(DATABASE_URL,"shenm9730","1636900");
            stat=conn.createStatement();
            rs=stat.executeQuery("Select * from user_attraction where UserID = '"+id+"' And Not (Question is null or Question ='')");
            while(rs.next())
            {
                System.out.println("*****************************");                
                System.out.println("Question - "+rs.getString(3));
                System.out.println("Answer - "+rs.getString(6));
            }
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
        
    public void postReview()
    {
        System.out.println("Please enter the name of the attraction you want to review:");
        String nameAttr = input.nextLine();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Statement stat2 = null;
        ResultSet rs2 = null;
         try
         {
             connection = DriverManager.getConnection(DATABASE_URL, "shenm9730", "1636900");
             statement = connection.createStatement();
             resultSet = statement.executeQuery("Select * from attraction where Name= '"+nameAttr+"'");
            if(!resultSet.next())
             {
                System.out.println("Attraction not found!!!");
             }
             else
             {
                stat2=connection.createStatement();
                rs2=stat2.executeQuery("Select * from user_attraction");
                System.out.println("You can only post review, score, or question once for each attraction");
                System.out.println("Please jot down your one-line review: no punctuation please");
                String review = input.nextLine();
                System.out.println("Please rate this attraction: score can be integer 1-5, 5 is the highest");
                String scoreString = input.nextLine(); 
                int score=Integer.parseInt(scoreString);
                System.out.println("Post here if you have a question for this place, otherwise press enter (no punctuation please)");
                String question = input.nextLine();
                int r = stat2.executeUpdate("insert into user_attraction values ('"+score+"','" +review+"','"+question+"','"+id+"','"+nameAttr+"','')");
                System.out.println("***The review is posted***");
             }
            
         }
         catch(SQLException e)
         {
             System.out.println("System error!!!");
             e.printStackTrace();
         }
         finally
         {
             try
             {
                 resultSet.close();
                 statement.close();
                 connection.close();
                 rs2.close();
                 stat2.close();
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }
         }
    }
             
    public void average()
    {
           
        System.out.println("Please enter the name of the attraction you want search:");
        String nameAttr = input.nextLine();
        Connection connection = null;
        Statement stat = null;
        ResultSet rs = null;
       
         try
         {
            connection = DriverManager.getConnection(DATABASE_URL, "shenm9730", "1636900");
            stat = connection.createStatement();
            rs = stat.executeQuery("Select * from user_attraction where Attraction= '"+nameAttr+"'");
            int total =0;
            int count=0; //dividebyzero exception           
             while(rs.next())
             {
               total+= Integer.parseInt(rs.getString(1));
               count++;
             }
            if (count == 0) 
            {
                System.out.println(nameAttr+" has not received a score yet");
            }
            else
            {
                System.out.println("-----"+nameAttr+" has an average score of "+(float)total/count+"-----");
            }
         }
         catch(SQLException e)
         {
             System.out.println("System error!!!");
             e.printStackTrace();
         }
         finally
         {
             try
             {
                 rs.close();
                 stat.close();
                 connection.close();
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }
         }
    }
     
    public void favorite()
    {
        Connection conn=null;
        Statement stat=null;
        ResultSet rs=null; 
        try
        {
            conn=DriverManager.getConnection(DATABASE_URL,"shenm9730","1636900");
            stat=conn.createStatement();
            rs=stat.executeQuery("Select * from favorite where UserID = '"+id+"'");
            
            while(rs.next())
            {
                String attr=rs.getString(1);
                Statement stat2=conn.createStatement();
                ResultSet rs2=stat2.executeQuery("Select * from attraction where Name='"+attr+"'");  
                while(rs2.next())
                {
                System.out.println("*****************************");   
                String Name=rs2.getString(1);
                System.out.println("Name - "+ rs2.getString(1));
                System.out.println("Description - "+rs2.getString(2));
                System.out.println("State - "+rs2.getString(3));
                System.out.println("City - "+rs2.getString(4));
                System.out.println("Tag - "+rs2.getString(5));
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
            
    public void answer()
    {
        System.out.println("Please enter the name of the attraction you want search:");
        String nameAttr = input.nextLine();
     
        Connection connection = null;
        Statement stat = null;
        ResultSet rs = null;
        Statement stat2=null;
        ResultSet rs2 = null;
         try
         {
             connection = DriverManager.getConnection(DATABASE_URL, "shenm9730", "1636900");
             stat = connection.createStatement();
             rs = stat.executeQuery("Select * from user_attraction where Attract"
                     + "ion= '"+nameAttr+"' and (Answer is null or Answer ='') and Not (Question is null or Question ='')");
           
             while(rs.next())
             {
           
                System.out.println(nameAttr+" has questions awaiting your answer!");
                String Q1=rs.getString(3);
                System.out.println(Q1);
                System.out.println("Do you have an answer? Y or N");
                String Choice=input.nextLine();
               
                if(Choice.equals("Y")||Choice.equals("y"))
                {
                    stat2 = connection.createStatement();
                    rs2 = stat2.executeQuery("Select * from user_attraction where Question= '"+Q1+"'");
                    System.out.println("Please enter your answer");
                    String answer=input.nextLine();
                    Statement stat3=connection.createStatement();
                    int r = stat3.executeUpdate("Update user_attraction set Answer = '"+answer+"' where Question='"+Q1+"'");
                }
                else{
                     ;
                }
             }
             
            System.out.println();
         }
         catch(SQLException e)
         {
             System.out.println("System error!!!");
             e.printStackTrace();
         }
         finally
         {
             try
             {
                 rs.close();
                 stat.close();
                 //rs2.close();
                 connection.close();
                 //stat2.close();
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }
         }
    }        
  
    public void mayLike()
    {
        Connection conn=null;
        Statement stat=null;
        ResultSet rs=null; 
        
        try
        {
            conn=DriverManager.getConnection(DATABASE_URL,"shenm9730","1636900");
            stat=conn.createStatement();
            rs=stat.executeQuery("Select * from profile Where ID='"+id+"'");
        while(rs.next())
            {
            String tag=rs.getString(3);
            Statement stat2=conn.createStatement();
            ResultSet rs2=stat2.executeQuery("Select * from attraction Where Tag='"+tag+"'");
            while(rs2.next())
            {
            System.out.println("*****************************");
            System.out.println("Name - "+rs2.getString(1));
            System.out.println("Description - "+rs2.getString(2));
            System.out.println("State - "+rs2.getString(3));
            System.out.println("City - "+rs2.getString(4));
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
    
    public void showTag()
    {
        Scanner input = new Scanner(System.in);
        String tag="";
        System.out.println("Please enter your tag: History, Shopping, Beach, Urban, Nature or Family");
        tag = input.nextLine();
        Connection conn=null;
        Statement stat=null;
        ResultSet rs=null; 
        Statement stat2=null;
        ResultSet rs2=null; 
        
        try
        {
            conn=DriverManager.getConnection(DATABASE_URL,"shenm9730","1636900");
            stat=conn.createStatement();
            rs=stat.executeQuery("Select * from attraction where Tag ='"+tag+"'");
            while(rs.next())
            {
                System.out.println("*****************************");
                String Name=rs.getString(1);
                System.out.println("Name - "+Name);
                System.out.println("Description - "+rs.getString(2));
                System.out.println("State - "+rs.getString(3));
                System.out.println("City - "+rs.getString(4));
                System.out.println("Tag - "+rs.getString(5));
                System.out.println("Want to add to your bucket list? Y or N");
                String Fav=input.nextLine();
                if(Fav.equals("Y"))
                {
                    stat2=conn.createStatement();
                    rs2=stat2.executeQuery("Select * from favorite");
                    int r=stat2.executeUpdate("Insert into favorite values ('"+Name+"','"+id+"')");
                    System.out.println("******Favorite destination added******");
                }
                else{
                     ;
                }
            }
        }
        catch(SQLException e)
        {
            System.err.println("Account creation failed!");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                conn.close();
                stat.close();
                rs.close();
                stat2.close();
                rs2.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void showCity(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the state of your interest (two-letter abbreviation)");
        String state = input.nextLine();
        System.out.println("Please enter the city of your interest");
        String city = input.nextLine();
        Connection conn=null;
        Statement stat=null;
        ResultSet rs=null; 
        Statement stat2=null;
        ResultSet rs2=null; 
        try
        {
            conn=DriverManager.getConnection(DATABASE_URL,"shenm9730","1636900");
            stat=conn.createStatement();
            rs=stat.executeQuery("Select * from attraction where State ='"+state+"' and City = '"+city+"'");
            while(rs.next())
            {
                System.out.println("*****************************");
                String Name=rs.getString(1);
                System.out.println("Name - "+ Name);
                System.out.println("Description - "+rs.getString(2));
                System.out.println("State - "+rs.getString(3));
                System.out.println("City - "+rs.getString(4));
                System.out.println("Tag - "+rs.getString(5));
            System.out.println("Want to add to your bucket list? Y or N");
            String Fav=input.nextLine();
            if(Fav.equals("Y"))
                {
                    stat2=conn.createStatement();
                    rs2=stat2.executeQuery("Select * from favorite");
                    int r=stat2.executeUpdate("Insert into favorite values ('"+Name+"','"+id+"')");
                    System.out.println("******Favorite destination added******");
                }
              else{
                     ;
                }  
            }
        }
        catch(SQLException e)
        {
            System.err.println("Account creation failed!");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                conn.close();
                stat.close();
                rs.close();
                stat2.close();
                rs2.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }  
    
}

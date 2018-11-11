/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Scanner;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author shenm9730
 */
@Named(value = "attraction")
@Dependent
public class Attraction {

    /**
     * Creates a new instance of Attraction
     */
    Scanner input = new Scanner(System.in); 
    private String Name;
    private String Desc;
    private String State;
    private String City;
    private String Tag;
     
    public Attraction(String n, String d, String s, String c, String t)
    {
        Name=n;
        Desc=d;
        State=s;
        City=c;
        Tag=t;
    }

    public Scanner getInput() {
        return input;
    }

    public void setInput(Scanner input) {
        this.input = input;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String Desc) {
        this.Desc = Desc;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String Tag) {
        this.Tag = Tag;
    }
    
    
}

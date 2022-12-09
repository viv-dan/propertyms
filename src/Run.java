import java.sql.SQLException;
import java.util.Scanner;

import Model.propertyImpl;
import controller.ControllerFeatures;
import controller.ControllerImpl;
import view.View;
import view.ViewImpl;

public class Run {
  public static void main(String args[]){
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the Database username: ");
    String username = sc.nextLine();
    System.out.println("Enter the Database password: ");
    String password = sc.nextLine();
    propertyImpl p = new propertyImpl();
    try {
      p.setDBUsername(username, password);
      View v=new ViewImpl(System.in,System.out);
      ControllerFeatures c=new ControllerImpl(p,v);
      p.closeConnection();
    }catch(RuntimeException | SQLException e){
      System.out.println(e.getMessage());
    }
    System.exit(0);
  }
}

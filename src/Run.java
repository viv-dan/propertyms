import Model.propertyImpl;
import controller.ControllerFeatures;
import controller.ControllerImpl;
import view.View;
import view.ViewImpl;

public class Run {
  public static void main(String args[]){
    View v=new ViewImpl(System.in,System.out);
    ControllerFeatures c=new ControllerImpl(new propertyImpl(),v);
    System.exit(0);
  }
}

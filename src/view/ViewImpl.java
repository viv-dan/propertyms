package view;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.ControllerFeatures;

public class ViewImpl implements View{

  private PrintStream out;
  private InputStream input;
  private Scanner sc;
  private ControllerFeatures c;
  public ViewImpl(InputStream in, PrintStream out){
    this.out=out;
    input=in;
    this.sc=new Scanner(this.input);
    startView();
  }

  private void startView() {
    int i;
    i=0;
    while(i!=4){
      showLandingMenu();
      try{
        i = Integer.parseInt(sc.nextLine().trim());
      }catch (Exception e){
        this.showInputError();
        this.goBackMessage();
        sc.nextLine();
        continue;
      }
      switch (i){
        case 1:
          //controller call method to get List of building names;
          List<String> l1=new ArrayList<>();
          l1.add("View Building 1");
          l1.add("View Building 2");
          this.showListOfBuildings(l1);
          this.goBackMessage();
          sc.nextLine();
          break;
        case 2:
          this.showBuildingEnterMenu();
          String s=sc.nextLine();
          //call controller to get details;
          //get amenities Map
          //get available unit;
          out.println("printing details of the building");
          this.goBackMessage();
          sc.nextLine();
          break;
        case 3:
          handleLoginSwitch();
          break;
        case 4:
          break;
        default:
          this.showInputError();
          this.goBackMessage();
          sc.nextLine();
      }
      if(i==4){
        break;
      }
    }
    sc.close();
  }

  private void handleLoginSwitch(){
    int j;
    j=0;
    while(j!=4){
      out.println("1.Tenant Login");
      out.println("2.Company Login");
      out.println("3.Register new Company");
      out.println("4.Go Back to Main Menu");
      try{
        j = Integer.parseInt(sc.nextLine().trim());
        switch (j){
          case 1:
            List<String> tenantDetail;
            tenantDetail =this.enterUserPassForm();
            //try login
            //after success
            this.handleTenant(tenantDetail);
            break;
          case 2:
            List<String> companyDetail;
            companyDetail=this.enterUserPassForm();
            //try login
            //after success
            this.handleCompany(companyDetail);
            break;
          case 3:
            List<String> details;
            details=this.registerNewCompanyForm();
          case 4:
            break;
          default:
            this.showInputError();
            this.goBackMessage();
            sc.nextLine();
        }
      }catch (Exception e){
        this.showInputError();
        this.goBackMessage();
        sc.nextLine();
        continue;
      }
      if(j==4){
        break;
      }
    }
  }

  private void handleCompany(List<String> companyDetail) {
    int a;
    a=0;
    while(a!=3){
      this.companyMenu();
      try{
        a=Integer.parseInt(sc.nextLine().trim());
      }catch (Exception e){
        this.showInputError();
        this.goBackMessage();
        sc.nextLine();
        continue;
      }
      switch (a){
        case 1:
          //call controller with companyID;
          this.viewAllBuildingControl();
          break;
        case 2:
          break;
        case 3:
          break;
        default:
          this.showInputError();
          this.goBackMessage();
          sc.nextLine();
      }if(a==3){
        break;
      }
    }
  }

  private void viewAllBuildingControl() {
    //call controller get all buildings
    out.println("Trial Company Building 1 from View");
    out.println("Enter 1 to get a particular building information");
    out.println("Enter any other key to continue");
    int ans;
    try{
      ans = Integer.parseInt(sc.nextLine().trim());
    }catch (Exception e){
      return;
    }
  }

  private void handleTenant(List<String> details){
    int k;
    k=0;
    while(k!=3){
      this.tenantMenu();
      try{
        k = Integer.parseInt(sc.nextLine().trim());
      }catch (Exception e){
        this.showInputError();
        this.goBackMessage();
        sc.nextLine();
        continue;
      }
      switch (k){
        case 1:
          //Check Lease Information
          //display lease information
          out.println("Enter Your Unit Number");
          String unitNo;
          unitNo= sc.nextLine();
          //get details print
          this.goBackMessage();
          sc.nextLine();
          break;
        case 2:
          ArrayList<String> requestDetails;
          requestDetails=this.createMaintenanceRequestForm();
          //controller create request;
          this.successMessage();
        case 3:
          break;
        default:
          this.showInputError();
          this.goBackMessage();
          sc.nextLine();
      }
      if(k==3){
        break;
      }
    }
  }

  @Override
  public void showLandingMenu() {
    out.println("Welcome to the Application");
    out.println("1.List of Buildings");
    out.println("2.View Particular Building Information");
    out.println("3.Login");
    out.println("4.Exit");
    out.println("Enter appropriate number corresponding to the option");
  }

  @Override
  public void showInputError() {
    out.println("Invalid input");
  }

  @Override
  public void goBackMessage() {
    out.println("Press Enter to go Back");
  }

  @Override
  public void showListOfBuildings(List<String> l1) {
    out.println("List of All Buildings");
    for (String s:l1 ) {
      out.println(s);
    }
  }

  @Override
  public void showBuildingEnterMenu() {
    out.println("Enter Building Name");
  }

  @Override
  public List<String> enterUserPassForm() {
    List<String> details=new ArrayList<>();
    out.println("Enter Username");
    details.add(sc.nextLine());
    out.println("Enter password");
    details.add(sc.nextLine());
    return details;
  }

  @Override
  public void tenantMenu() {
    out.println("1.Check Lease Information");
    out.println("2.Maintenance Request");
    out.println("3.Logout");
  }

  @Override
  public void companyMenu() {
    out.println("1.View Buildings Managed By the Company");
    out.println("2.Add new Building under the company");
    out.println("3.Logout");
  }

  @Override
  public List<String> registerNewCompanyForm() {
    return null;
  }

  @Override
  public ArrayList<String> createMaintenanceRequestForm() {
    out.println("Enter Details");
    return null;
  }

  @Override
  public void successMessage() {
    out.println("Done Successfully");
  }

}

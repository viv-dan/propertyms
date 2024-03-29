package view;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  }
  @Override
  public void setView(ControllerFeatures c){
    this.c=c;
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
        continue;
      }
      switch (i){
        case 1:
          c.listOfAllBuildings();
          this.goBackMessage();
          break;
        case 2:
          this.showBuildingEnterMenu();
          String s=sc.nextLine();
          c.getParticularBuilding(s);
          this.goBackMessage();
          break;
        case 3:
          handleLoginSwitch();
          break;
        case 4:
          break;
        default:
          this.showInputError();
          this.goBackMessage();
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
            Map<String,String> tenantDetail;
            tenantDetail =this.enterUserPassForm();
            c.TenantLogin(tenantDetail.get("username"),tenantDetail.get("password"));
            List<String> l=new ArrayList<>();
            l.add(tenantDetail.get("username"));
            l.add(tenantDetail.get("password"));
            out.println("Welcome Tenant");
            this.handleTenant(l);
            break;
          case 2:
            Map<String,String> companyDetail;
            companyDetail=this.enterUserPassForm();
            List<String> l2=new ArrayList<>();
            l2.add(companyDetail.get("username"));
            l2.add(companyDetail.get("password"));
            c.companyLogin(companyDetail.get("username"),companyDetail.get("password"));
            out.println("Welcome Company");
            this.handleCompany(l2);
            break;
          case 3:
            String username,password,emailID,phoneNo;
            out.println("Enter name of the company");
            username=sc.nextLine();
            out.println("Enter the password");
            password=sc.nextLine();
            out.println("Enter the emailID");
            emailID=sc.nextLine();
            out.println("Enter the phone number");
            phoneNo=sc.nextLine();
            c.createNewCompany(username,password,emailID,phoneNo);
            this.goBackMessage();
          case 4:
            break;
          default:
            this.showInputError();
            this.goBackMessage();
        }
      }catch (Exception e){
        if(e.getMessage().contains("Invalid Credentials")){
          this.showMessage(e.getMessage());
        }else{
          this.showInputError();
        }
        this.goBackMessage();
        continue;
      }
      if(j==4){
        break;
      }
    }
  }

  private void handleCompany(List<String> companyDetail) {
    int a;
    String name;
    String pass;
    name=companyDetail.get(0);
    pass=companyDetail.get(1);
    a=0;
    while(a!=13){
      this.companyMenu();
      try{
        a=Integer.parseInt(sc.nextLine().trim());
      }catch (Exception e){
        this.showInputError();
        this.goBackMessage();
        continue;
      }
      switch (a){
        case 1:
          //call controller with companyID;
          this.viewAllBuildingControl(name);
          this.goBackMessage();
          break;
        case 2:
          // create new building
          this.createHelper(name);
          break;
        case 3:
          //add units to building
          List<String> list=new ArrayList<>();
          out.println("Enter the name of the building you want to add units to");
          String bName;
          bName=sc.nextLine();
          list.add("");
          list.add(bName);
          try{
            this.addUnitHelper(list,name);
          }catch (Exception e){
            this.showErrorMessage("Building doesn't exist");
            this.goBackMessage();
          }
          break;
        case 4:
          //view maintenance personnel of a building
          out.println("Enter building name to load maintenance personnel");
          String buildingName=sc.nextLine();
          c.loadMaintenancePersonnel(buildingName,name);
          this.goBackMessage();
          break;
        case 5:
          // add maintenance to building
          out.println("Enter the name of the building");
          String building=sc.nextLine();
          out.println("Enter the name of the person");
          String mName=sc.nextLine();
          out.println("Enter the phone number associated to the personnel");
          String no=sc.nextLine();
          c.addMaintenancePersonnel(building,mName,no,name);
          this.goBackMessage();
          break;
        case 6:
          // add tenants to unit
          this.showBuildingsUnits(name);
          out.println("Select from above units to add");
          this.addTenantstoUnit(name);
          break;
        case 7:
          // get tenants
          this.getTenantOfParticularBuilding(name);
          break;
        case 8:
          //add amenity
          this.addAmenity(name);
          break;
        case 9:
          //Lease information of a unit
          out.println("Enter the name of the tenant");
          String tenantName;
          tenantName=sc.nextLine();
          c.showCompanyTenantInformation(tenantName,name);
          this.goBackMessage();
          break;
        case 10:
          //show active requests and change it to close
          this.handleChangingRequests(name);
          this.goBackMessage();
          break;
        case 11:
          // show meta-data of a building
          String gt;
          out.println("Enter name of the building");
          c.getBuildingMetaDate(name,sc.nextLine());
          this.goBackMessage();
          break;
        case 12:
          //delete a building
          out.println("Enter the name of the building to be deleted");
          c.deleteBuilding(name,sc.nextLine());
          this.goBackMessage();
          break;
        case 13:
          break;
        default:
          this.showInputError();
          this.goBackMessage();
      }if(a==13){
        break;
      }
    }
  }

  private void handleChangingRequests(String name) {
    String bName;
    String unitNo;
    out.println("Enter the name of the building");
    bName=sc.nextLine();
    out.println("Enter the unit number");
    unitNo=sc.nextLine();
    String desc;
    desc=this.showActiveUnitRequest(c.showActiveRequest(bName,unitNo,name));
    if(desc!=null){
      c.updateRequest(desc,bName,unitNo);
      this.successMessage();
    }
  }

  private String showActiveUnitRequest(List<String> request) {
    String desc;
    char ch;
    for (String s : request) {
      if (s.split(" - ")[1].equals("0")) {
        desc=s.split(" - ")[0];
        out.println("The description of the request: " + desc);
        out.println("Do you wish to delete this request(Y/N)");
        ch=sc.next().charAt(0);
        if(ch=='Y' || ch=='y'){
          return desc;
        }
      }
    }
    return null;
  }

  private void showBuildingsUnits(String name) {
    c.showBuildingAndAvailableUnit(name);
  }


  private void showUnitDetails() {
    out.println("1.Check availability of a unit");
    out.println("2.Check lease information of a unit");
    try{
      int i,unitNo;
      i=Integer.parseInt(sc.nextLine().trim());
      if(i==1){
        //check availability
        String buildingName;
        out.println("Enter the name of the building");
        buildingName=sc.nextLine();
        out.println("Enter unit number");
        unitNo=Integer.parseInt(sc.nextLine().trim());
        c.getUnitAvailability(buildingName,unitNo);
        this.goBackMessage();
      } else if (i==2) {
        //check lease information
        out.println("Enter the name of tenant living");
        String tName;
        tName= sc.nextLine();
        c.getLeaseInfo(tName);
        this.goBackMessage();
      }else {
        this.showInputError();
        this.goBackMessage();
      }
    }catch (NumberFormatException e){
      this.showInputError();
      this.goBackMessage();
    }
  }

  private void addAmenity(String cName) {
    String bName;
    out.println("Enter the name of the building to add the amenity");
    bName=sc.nextLine();
    c.showAmenity();
    out.println("Choose one of the amenities from above");
    String am;
    am=sc.nextLine();
    c.addAmenity(bName,am,cName);
    this.goBackMessage();
  }

  private void getTenantOfParticularBuilding(String cName) {
    String bName;
    int unit;
    try{
      out.println("Enter the name of the building");
      bName= sc.nextLine();
      out.println("Enter the unit no");
      unit=Integer.parseInt(sc.nextLine().trim());
      c.getTenantOfParticularBuilding(bName,unit,cName);
      this.goBackMessage();
    }catch (Exception e){
      this.showErrorMessage("Invalid unit Number");
      this.goBackMessage();
    }
  }

  private void addTenantstoUnit(String name) {
    String bName;
    String tName;
    int unitNo;
    String tPass;
    String dob;
    String occupation;
    String phoneNo;
    try{
      out.println("Enter the name of the building to add");
      bName=sc.nextLine();
      out.println("Enter the unit No to add the tenant");
      unitNo=Integer.parseInt(sc.nextLine().trim());
      out.println("Enter the name of the tenant");
      tName=sc.nextLine();
      out.println("Enter password for the tenant for login access");
      tPass=sc.nextLine();
      out.println("Enter date of birth of the tenant");
      dob=sc.nextLine();
      out.println("Enter the occupation of the tenant");
      occupation=sc.nextLine();
      out.println("Enter phone number of the tenant");
      phoneNo=sc.nextLine();
      c.createNewTenant(name,bName,unitNo,tName,tPass,dob,occupation,phoneNo);
      this.goBackMessage();
    }catch (NumberFormatException e){
      this.showErrorMessage("Invalid Unit number");
      this.goBackMessage();
    }


  }

  private void createHelper(String name) {
    List<String> l;
    l=this.createNewBuildingForm();
    try{
      c.createNewBuilding(name,l);
      out.println("Create units in the building");
      addUnitHelper(l,name);
    }catch (Exception e){
      this.goBackMessage();
      return;
    }

  }
  private void addUnitHelper(List<String> l,String name){
    try {
      char ch;
      int unitNo;
      int noOfBedrooms;
      int noOfBathroom;
      double price;
      double area;
      do {
        out.println("Enter unit number");
        unitNo=Integer.parseInt(sc.next().trim());
        out.println("Enter the number of bedrooms in the unit");
        noOfBedrooms=Integer.parseInt(sc.next().trim());
        out.println("Enter the number of bathrooms in the unit");
        noOfBathroom=Integer.parseInt(sc.next().trim());
        out.println("Enter the price of the unit");
        price=Double.parseDouble(sc.next().trim());
        out.println("Enter the square feet area of the unit");
        area=Double.parseDouble(sc.next().trim());
        c.addUnitToBuilding(l.get(1),unitNo,noOfBedrooms,noOfBathroom,price,area,name);
        out.println("To add more units enter Y");
        ch=sc.next().charAt(0);
      }while (ch=='Y' || ch=='y');
      this.goBackMessage();
    }catch (NumberFormatException | IndexOutOfBoundsException e){
      this.showInputError();
      this.goBackMessage();
    } catch (Exception e1){
      showErrorMessage(e1.getMessage());
      this.goBackMessage();
    }
  }

  private void viewAllBuildingControl(String name) {
    //call controller get all buildings
    c.getCompanyBuildings(name);
  }

  private void handleTenant(List<String> details){
    int k;
    String name;
    String pass;
    name=details.get(0);
    pass=details.get(1);
    k=0;
    while(k!=3){
      this.tenantMenu();
      try{
        k = Integer.parseInt(sc.nextLine().trim());
      }catch (Exception e){
        this.showInputError();
        this.goBackMessage();
        continue;
      }
      switch (k){
        case 1:
          c.getLeaseInfo(name);
          //get details print
          this.goBackMessage();
          break;
        case 2:
          this.handleTenantRequest(name);
          break;
        case 3:
          break;
        default:
          this.showInputError();
          this.goBackMessage();
      }
      if(k==3){
        break;
      }
    }
  }

  private void handleTenantRequest(String name) {
    out.println("1.Show requests");
    out.println("2.Create a new Request");
    int ch;
    try{
      ch = Integer.parseInt(sc.nextLine().trim());
      switch (ch){
        case 1:
          //get active request
          c.showMaintenanceRequest(name);
          this.goBackMessage();
          break;
        case 2:
          out.println("Enter the description of your problem in 200 characters");
          String desc;
          desc=sc.nextLine();
          //controller create request;
          c.createMaintenanceRequest(name,desc);
          this.goBackMessage();
          break;
        default:
          this.showInputError();
          this.goBackMessage();
      }
    }catch (Exception e){
      this.showInputError();
      this.goBackMessage();
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
    sc.nextLine();
  }

  @Override
  public void showListOfBuildings(List<String> l1) {
    if(l1==null || l1.isEmpty()){
      out.println("No Buildings present");
    }
    else {
      out.println("List of All Buildings");
      for (String s:l1 ) {
        out.println(s);
      }
    }
  }

  @Override
  public void showBuildingEnterMenu() {
    out.println("Enter Building Name");
  }

  @Override
  public Map<String,String> enterUserPassForm() {
    Map<String,String> details=new HashMap<>();
    out.println("Enter Name");
    details.put("username",sc.nextLine());
    out.println("Enter password");
    details.put("password",sc.nextLine());
    return details;
  }

  @Override
  public void tenantMenu() {
    out.println("1.Check Lease Information");
    out.println("2.Maintenance");
    out.println("3.Logout");
  }

  @Override
  public void companyMenu() {
    out.println("1.View Buildings Managed By the Company");
    out.println("2.Add new Building under the company");
    out.println("3.Add more units to existing Building");
    out.println("4.View Maintenance Personal for a building");
    out.println("5.Add Maintenance Personal to a building");
    out.println("6.Add tenants to a unit");
    out.println("7.Get Tenants of a particular unit in a building");
    out.println("8.Add amenity to a building");
    out.println("9.Show tenant lease information");
    out.println("10.Show active maintenance requests");
    out.println("11.Show details about a building");
    out.println("12.Delete a building");
    out.println("13.Logout");
  }



  @Override
  public List<String> createMaintenanceRequestForm() {
    List<String> entry=new ArrayList<>();
    out.println("Enter Building Name");
    String temp;
    temp=sc.nextLine();
    entry.add(temp);
    out.println("Enter Unit Number");
    temp=sc.nextLine();
    entry.add(temp);
    out.println("Enter the description of your problem in 200 characters");
    temp=sc.nextLine();
    entry.add(temp);
    return entry;
  }

  @Override
  public void successMessage() {
    out.println("Done Successfully");
  }

  @Override
  public void showErrorMessage(String message) {
    out.println(message);
  }

  @Override
  public void showMessage(String message) {
    out.println(message);
  }

  @Override
  public List<String> createNewBuildingForm() {
    List<String> ret=new ArrayList<>();
    out.println("Enter address");
    ret.add(sc.nextLine());
    out.println("Enter the name of the building");
    ret.add(sc.nextLine());
    out.println("Enter zipcode");
    ret.add(sc.nextLine());
    out.println("Enter number of floors");
    ret.add(sc.nextLine());
    out.println("Enter number of parking spots");
    ret.add(sc.nextLine());
    out.println("Enter type of building(apartment/townhouse)");
    ret.add(sc.nextLine());
    return ret;
  }

  @Override
  public void showMaintenancePersonnel(Map<String, String> maintenancePersonnel) {
    if(maintenancePersonnel.size()==0){
      this.showMessage("No Maintenance Personnel added yet to the building.");
    }else{
      for (String name:maintenancePersonnel.keySet() ) {
        out.println("Name: "+name+" Phone Number: "+maintenancePersonnel.get(name));
      }
    }
  }

  @Override
  public void showAmenity(List<String> amenities) {
    if(amenities==null || amenities.isEmpty()){
      out.println("No Amenities are associated with this building");
    }else {
      out.println("List of Amenities");
      for (String s:amenities ) {
        out.println(s);
      }
    }
  }

  @Override
  public void showRequestInformation(List<String> request) {
    for (String s:request ) {
      out.println("The description of the request: "+s.split(" - ")[0]);
      if(s.split(" - ")[1].equals("0")){
        out.println("The status of the request:Open");
      }else {
        out.println("The status of the request:Serviced and Closed");
      }
    }
  }

  @Override
  public void showBuildingMetaData(Map<String, String> buildingMetadata, String buildingName) {
    out.println("Building Name "+buildingName );
    out.println("Address of the building: "+buildingMetadata.get("address"));
    out.println("Zipcode: "+buildingMetadata.get("zipcode"));
    out.println("Number of floors in the building: "+buildingMetadata.get("num_floors"));
    out.println("Number of Parking Spots: "+buildingMetadata.get("parking_spots"));
    out.println("Type of building: "+buildingMetadata.get("type"));
  }
}

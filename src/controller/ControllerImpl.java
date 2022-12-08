package controller;

import java.util.List;
import java.util.Map;

import Model.property;
import view.View;

public class ControllerImpl implements ControllerFeatures{

  private property model;
  private View v;


  public ControllerImpl(property p,View v){
    this.model=p;
    this.v=v;
    v.setView(this);
  }
  @Override
  public void listOfAllBuildings() {
    try{
      v.showListOfBuildings(model.getAllBuildings());
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }
  @Override
  public void getParticularBuilding(String buildingName) {
    try{
      Map<String,String> info;
      info=model.getParticularBuildingInfo(buildingName);
      if(info.get("amenities")==null){
        v.showMessage("No amenities are associated with the building");
      }
      else {
        v.showMessage("The Amenities of the Building");
        v.showMessage(info.get("amenities"));
      }
      if(info.get("unit")==null){
        v.showMessage("No units are available");
      }else {
        v.showMessage("The available units in "+buildingName);
        v.showMessage(info.get("unit"));
      }
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public void companyLogin(String username, String password) {
    int cid=model.validateCompany(username,password);
    if(cid==0){
      throw new RuntimeException("Invalid Credentials");
    }
  }

  @Override
  public void TenantLogin(String username, String password) {
    int tid=model.validateTenant(username,password);
    if(tid==0){
      throw new RuntimeException("Invalid Credentials");
    }
  }

  @Override
  public void createNewBuilding(String companyName, List<String> formData) {
    try {
      int floors,parking;
      floors= Integer.parseInt(formData.get(3));
      parking= Integer.parseInt(formData.get(4));
      model.createBuilding(companyName,formData.get(0),formData.get(1),formData.get(2),
              floors,parking,formData.get(5));
    }catch (NumberFormatException e){
      v.showErrorMessage("Invalid input");
    }
    catch (Exception e){
      v.showErrorMessage(e.getMessage());
      throw new RuntimeException();
    }
  }

  @Override
  public void getLeaseInfo(String username) {
    try{
      List<String> l =model.getLeaseInfo(username);
      if(l.isEmpty() || l==null){
        v.showMessage("Invalid input");
        return;
      }
      v.showMessage("Unit Number Associated"+l.get(0));
      v.showMessage("Start Date of the Lease"+l.get(1));
      v.showMessage("End date of the Lease"+l.get(2));
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public void createMaintenanceRequest(String bName, String unitNumber, String desc) {
    try{
      int unit;
      try{
        unit = Integer.parseInt(unitNumber);
      }catch (Exception e){
        v.showErrorMessage("Invalid Unit Number");
        return;
      }
      model.createMaintenanceRequest(bName,unit,desc);
      v.successMessage();
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public void showMaintenanceRequest(String bName, String unitNo) {
    try{
      int unit;
      try{
        unit = Integer.parseInt(unitNo);
      }catch (Exception e){
        v.showErrorMessage("Invalid Unit Number");
        return;
      }
      List<String> request=model.getMaintenanceRequests(bName,unit);
      if(request.isEmpty() || request==null){
        v.showMessage("No request found");
        return;
      }
      v.showMessage("The request corresponding status shown below");
      v.showRequestInformation(request);
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public void addUnitToBuilding(String s, int unitNo, int noOfBedrooms,int noOfBathroom, double price, double value) {
    model.addUnits(s,noOfBedrooms,noOfBathroom,price,value,unitNo);
  }

  @Override
  public void getCompanyBuildings(String name) {
    try{
      v.showListOfBuildings(model.loadCompanyBuildings(name));
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public void loadMaintenancePersonnel(String buildingName) {
    try{
      v.showMaintenancePersonnel(model.getMaintenancePersonnel(buildingName));
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public void addMaintenancePersonnel(String building, String mName, String no) {
    try{
      model.addMaintenancePersonnel(building,mName,no);
      v.successMessage();
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public void createNewTenant(String Cname, String bName, int unitNo, String tName, String tPass, String dob, String occupation, String phoneNo) {
    try{
      model.addTenantToUnit(tName,bName,unitNo,tPass,dob,occupation,phoneNo);
      v.successMessage();
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public void getTenantOfParticularBuilding(String bName, int unit) {
    try{
      if(model.getTenants(bName,unit)==null){
        v.showMessage("No tenant is associated with the unit");
      }else{
        v.showMessage("The tenant name associated with the unit is " + model.getTenants(bName,unit));
      }
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public void showAmenity() {
    v.showAmenity(model.getAmenities());
  }

  @Override
  public void addAmenity(String bName, String amenity) {
    try{
      model.addAmenity(bName,amenity);
      v.successMessage();
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public void createNewCompany(String username, String password, String emailID, String phoneNumber) {
    try{
      model.createCompany(username,password,emailID,phoneNumber);
      v.successMessage();
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public void getUnitAvailability(String buildingName, int unitNo) {
    try{
      if(model.isUnitAvailable(buildingName,unitNo)){
        v.showMessage("Unit number "+unitNo+" is available in building name: "+buildingName);
      }
      else {
        v.showMessage("Unit number "+unitNo+" is not available in building name: "+buildingName);
      }
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }


}

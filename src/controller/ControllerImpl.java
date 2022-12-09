package controller;

import java.util.List;
import java.util.Map;

import Model.property;
import view.View;

/**
 * The class depicts a controller for the program.
 * The class implements the features of the application.
 */
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
      if(info.size()==0){
        v.showMessage("No building is present with that name.");
        return;
      }
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
      if(checkBuildingOfCompany(companyName, formData.get(1))){
        throw new RuntimeException("The name of the building already exists.");
      }
      else{
        model.createBuilding(companyName,formData.get(0),formData.get(1),formData.get(2),
                floors,parking,formData.get(5));
        v.showMessage("Building created successfully");
      }
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
        v.showMessage("Can't get lease information.");
        return;
      }
      v.showMessage("Unit Number Associated "+l.get(0));
      v.showMessage("Start Date of the Lease "+l.get(1));
      v.showMessage("End date of the Lease "+l.get(2));
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public void createMaintenanceRequest(String tenantName, String desc) {
    try{
      int unit;
      String bName;
      bName= model.getTenantBuilding(tenantName);
      List<String> l =model.getLeaseInfo(tenantName);
      if(l.isEmpty()){
        v.showMessage("No lease associated with user");
        return;
      }
      unit= Integer.parseInt(l.get(0));
      model.createMaintenanceRequest(bName,unit,desc);
      v.successMessage();
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public void showMaintenanceRequest(String tenantName) {
    try{
      int unit;
      String bName;
      bName= model.getTenantBuilding(tenantName);
      List<String> l =model.getLeaseInfo(tenantName);
      if(l.isEmpty()){
        v.showMessage("No lease present for tenant");
        return;
      }
      unit= Integer.parseInt(l.get(0));
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
  public void addUnitToBuilding(String s, int unitNo, int noOfBedrooms,
                                int noOfBathroom, double price,
                                double value,String companyName) {
    try {
      if(checkBuildingOfCompany(companyName,s)){
        model.addUnits(s,noOfBedrooms,noOfBathroom,price,value,unitNo);
      }
      else {
        throw new RuntimeException("Invalid Building for a company");
      }
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }

  private boolean checkBuildingOfCompany(String companyName,String bName){
    List<String> b;
    b=model.loadCompanyBuildings(companyName);
    if(b.contains(bName)){
      return true;
    }else {
      return false;
    }
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
  public void loadMaintenancePersonnel(String buildingName,String companyName) {
    try{
      if(checkBuildingOfCompany(companyName,buildingName)){
        v.showMaintenancePersonnel(model.getMaintenancePersonnel(buildingName));
      }else {
        v.showErrorMessage("Building name not associated with company");
      }
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public void addMaintenancePersonnel(String building, String mName, String no,String companyName) {
    try{
      if(checkBuildingOfCompany(companyName,building)){
        model.addMaintenancePersonnel(building,mName,no);
        v.successMessage();
      }else {
        v.showErrorMessage("Building name not associated with company");
      }
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
  public void getTenantOfParticularBuilding(String bName, int unit,String companyName) {
    try{
      if(checkBuildingOfCompany(companyName,bName)){
        if(model.getTenants(bName,unit)==null){
          v.showMessage("No tenant is associated with the unit");
        }else{
          v.showMessage("The tenant name associated with the unit is " + model.getTenants(bName,unit));
        }
      }else{
        v.showMessage("Building not associated with Company");
        return;
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
  public void addAmenity(String bName, String amenity,String companyName) {
    try{
      if(checkBuildingOfCompany(companyName,bName)){
        model.addAmenity(bName,amenity);
        v.successMessage();
      }else{
        v.showMessage("Building not associated with company");
        return;
      }
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

  @Override
  public void showBuildingAndAvailableUnit(String name) {
    try{
      List<String> bNames;
      bNames=model.loadCompanyBuildings(name);
      Map<String,String> l;
      for (String bn:bNames ) {
        l=model.getParticularBuildingInfo(bn);
        v.showMessage("The available units in "+bn);
        v.showMessage(l.get("unit"));
      }
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public void showCompanyTenantInformation(String tenantName, String name) {
    try{
      String bName;
      bName= model.getTenantBuilding(tenantName);
      if(checkBuildingOfCompany(name,bName)){
        this.getLeaseInfo(tenantName);
      }else {
        v.showMessage("Tenant not associated with the company");
      }
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public List<String> showActiveRequest(String buildingName, String unitNo, String companyName) {
    try{
      if(checkBuildingOfCompany(companyName,buildingName)){
        int unit;
        unit=Integer.parseInt(unitNo);
        List<String>l = model.getMaintenanceRequests(buildingName,unit);
        return l;
      }else {
        throw new RuntimeException("Building not associated with company");
      }
    }catch (NumberFormatException e){
      throw new RuntimeException("Invalid unit number");
    }
    catch (Exception e){
      throw e;
    }
  }

  @Override
  public void updateRequest(String desc, String buildingName, String unitNo) {
    model.updateRequestStatus(desc,buildingName,Integer.parseInt(unitNo));
  }

  @Override
  public void deleteBuilding(String companyName, String buildingName) {
    try{
      if(checkBuildingOfCompany(companyName,buildingName)){
        model.deleteBuilding(buildingName);
        v.successMessage();
      }else {
        v.showErrorMessage("Building not associated with the company");
      }
    }catch (Exception e){
      v.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public void getBuildingMetaDate(String companyName, String buildingName) {
    try{
      if(checkBuildingOfCompany(companyName,buildingName)){
        v.showBuildingMetaData(model.getBuildingMetadata(buildingName),buildingName);

      }else {
        v.showErrorMessage("Building not associated with the company");
      }

    }catch (Exception e){

    }
  }


}

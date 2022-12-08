package controller;


import java.util.List;

public interface ControllerFeatures {
  void listOfAllBuildings();
  void getParticularBuilding(String buildingName);
  void companyLogin(String username,String password);
  void TenantLogin(String username,String password);
  void createNewBuilding(String companyName, List<String> formData);
  void getLeaseInfo(String username);
  void createMaintenanceRequest(String bName,String unitNumber,String desc);
  void showMaintenanceRequest(String bName, String unitNo);

  void addUnitToBuilding(String s, int unitNo, int noOfBedrooms,int noOfBathroom, double price, double value);

  void getCompanyBuildings(String name);

  void loadMaintenancePersonnel(String buildingName);

  void addMaintenancePersonnel(String building, String mName, String no);

  void createNewTenant(String Cname, String bName, int unitNo, String tName, String tPass, String dob, String occupation, String phoneNo);

  void getTenantOfParticularBuilding(String bName, int unit);

  void showAmenity();

  void addAmenity(String bName, String amenity);

  void createNewCompany(String username, String password, String emailID, String phoneNumber);

  void getUnitAvailability(String buildingName, int unitNo);
}

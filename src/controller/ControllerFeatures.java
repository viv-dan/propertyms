package controller;


import java.util.List;

/**
 * The controller features shows the features supported by the application.
 */
public interface ControllerFeatures {
  /**
   * The method shows the list of all buildings by interacting with the model.
   */
  void listOfAllBuildings();

  /**
   * The method show the amenities and available units in the building.
   * @param buildingName the name of building to be displayed.
   */
  void getParticularBuilding(String buildingName);

  /**
   * The method validates the login for a company.
   * @param username the username entered of the company from the view.
   * @param password the password entered of the company from the form.
   */
  void companyLogin(String username,String password);

  /**
   * The method handles the login for a tenant.
   * @param username the username entered of the tenant from the view.
   * @param password the password entered of the tenant from the form.
   */
  void TenantLogin(String username,String password);
  void createNewBuilding(String companyName, List<String> formData);
  void getLeaseInfo(String username);
  void createMaintenanceRequest(String tenantName,String desc);
  void showMaintenanceRequest(String tenantName);

  void addUnitToBuilding(String s, int unitNo, int noOfBedrooms,int noOfBathroom,
                         double price, double value,String companyName);

  void getCompanyBuildings(String name);

  void loadMaintenancePersonnel(String buildingName,String companyName);

  void addMaintenancePersonnel(String building, String mName, String no,String companyName);

  void createNewTenant(String Cname, String bName, int unitNo, String tName, String tPass, String dob, String occupation, String phoneNo);

  void getTenantOfParticularBuilding(String bName, int unit,String companyName);

  void showAmenity();

  void addAmenity(String bName, String amenity,String companyName);

  void createNewCompany(String username, String password, String emailID, String phoneNumber);

  void getUnitAvailability(String buildingName, int unitNo);

  void showBuildingAndAvailableUnit(String name);

  void showCompanyTenantInformation(String tenantName, String companyName);
}

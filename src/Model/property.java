package Model;

import java.util.List;
import java.util.Map;

public interface property {

  /**
   * The method validates the tenant login details and returns the tenant id..
   *
   * @param username the username of the tenant
   * @param password the password of the tenant
   * @return the tenant id in the database
   */
  int validateTenant(String username, String password);

  /**
   * The method validates the company login details and returns the company id.
   *
   * @param username the username of the company
   * @param password the password of the company
   * @return the company id in the database
   */
  int validateCompany(String username, String password);

  /**
   * The method returns the list of all the available building names that are present.
   *
   * @return a list of all the building names
   */
  List<String> getAllBuildings();

  /**
   * The method returns the amenities and the available units present in the building as a map.
   *
   * @param buildingName the name of the building for which the details have to be returned
   * @return a map of amenities as key and their description as value and list of units as value to
   *          the key of unit.
   */
  Map<String, String> getParticularBuildingInfo(String buildingName);

  /**
   * The method creates a maintenance request for the particular unit.
   *
   * @param buildingName the name of the building
   * @param unitNo the number of the unit
   * @param desc the description of the request to be created
   */
  void createMaintenanceRequest(String buildingName, int unitNo, String desc);

  /**
   * The method returns all the past and active maintenance requests that are attached to a
   * particular unit.
   *
   * @param buildingName the name of the building
   * @param unitNo the number of the unit
   * @return a list of maintenance requests as strings that are concatenated as Description and
   *          status of it
   */
  List<String> getMaintenanceRequests(String buildingName, int unitNo);

  /**
   * The method checks if the particular unit is available or not.
   *
   * @param buildingName the name of the particular building
   * @param unitNo the number of the unit
   * @return true if the unit is available to lease out. False otherwise
   */
  boolean isUnitAvailable(String buildingName, int unitNo);

  /**
   * The method adds a maintenance personnel to a particular building.
   *
   * @param buildingName the name of the building to which the personnel should be added to
   * @param personnelName the maintenance personnel's name
   * @param phoneNo the phone number of the personnel.
   */
  void addMaintenancePersonnel(String buildingName, String personnelName, String phoneNo);

  /**
   * The method returns a list of maintenance personnel that are attached to a particular building.
   *
   * @param buildingName the name of the building
   * @return the map of the personnel's name as key and phone number of the personnel
   */
  Map<String, String> getMaintenancePersonnel(String buildingName);

  /**
   * The method returns information about the lease of a particular tenant.
   *
   * @param tenantName the name of the tenant for whom the lease info has to be returned
   * @return the list of unit number, start date and end date as three separate strings
   */
  List<String> getLeaseInfo(String tenantName);

  /**
   * The method helps in adding tenant to a particular unit in a building.
   *
   * @param tenantName the name of the tenant to be added
   * @param buildingName the name of the building
   * @param unitNo the number of the unit
   * @param tenantPassword the password of the tenant
   */
  /**
   * The method helps in adding tenant to a particular unit in a building.
   *
   * @param tenantName the name of the tenant to be added
   * @param buildingName the name of the building
   * @param unitNo the number of the unit
   * @param tenantPassword the password of the tenant
   * @param dob date of birth of the tenant
   * @param occupation the occupation of the tenant
   * @param phno the phone number of the tenant
   */
  void addTenantToUnit(String tenantName, String buildingName, int unitNo, String tenantPassword, String dob, String occupation, String phno);

  /**
   * The method helps create a company in the database.
   *
   * @param companyName the name of the company to be created
   * @param companyPassword the password of the company to be created
   */
  void createCompany(String companyName, String companyPassword, String email, String phno);

  /**
   * The method helps create a building with all the data that are passed such as amenities, name,
   * address etc..
   *
   * @param companyName the name of the company to which the building has to be attached to
   * @param address the address of the building as a string
   * @param buildingName the name of the building to be created
   * @param zipcode the zipcode to which the building has to be attached
   * @param noOfFloors the number of floors in the building
   * @param noOfParkingSpots the number of parking spots in the building
   * @param type the type of the building
   */
  void createBuilding(String companyName, String address, String buildingName,
                      String zipcode, int noOfFloors, int noOfParkingSpots, String type);

  /**
   * The method returns the tenant of a particular unit in the building.
   *
   * @param buildingName the name of the building
   * @param unit the number of the unit
   * @return the tenant of the particular unit
   */
  String getTenants(String buildingName, int unit);

  /**
   * The method helps in adding units to a particular building.
   *
   * @param buildingName the name of the building
   * @param noOfBedrooms the number of bedrooms
   * @param noOfBathrooms the number of bathrooms
   * @param price the price of the unit
   * @param area the area measurement of the unit
   */
  void addUnits(String buildingName, int noOfBedrooms, int noOfBathrooms, Double price, Double area, int unitNo);

  /**
   * The method returns the list of amenities that are available in the database.
   *
   * @return the list of amenities
   */
  List<String> getAmenities();

  /**
   * The method helps add amenities to the building
   *
   * @param buildingName the name of the building to which the amenity should be added
   * @param amenity the amenity to be added
   */
  void addAmenity(String buildingName, String amenity);

  /**
   * The method helps getting all the building names managed by a particular building.
   *
   * @param companyName the name of the company
   * @return the list of building names managed by the company
   */
  List<String> loadCompanyBuildings(String companyName);

  /**
   * The method returns the name of the building the tenant is associated with.
   *
   * @param tenantName the name of the tenant for which the building name has to be returned
   * @return the name of the building
   */
  String getTenantBuilding(String tenantName);

}

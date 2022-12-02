package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class propertyImpl implements property {

  private final String url = "jdbc:mysql://localhost:3306/mysql";
  private final String username = "root";
  private final String password = "bostonA6#";
  private static Connection con;

  private Connection getConnection() {
    if(con==null) {
      try {
        con = DriverManager.getConnection(url, username, password);
      } catch (SQLException e) {
        throw new RuntimeException("cannot connect to database");
      }
    }
    return con;
  }

  /**
   * The method validates the tenant login details.
   *
   * @param username the username of the tenant
   * @param password the password of the tenant
   * @return true if login details are verified. False otherwise
   */
  @Override
  public boolean validateTenant(String username, String password) {
    String sql_string = "SELECT propertyproject.validate_tenant(?,?)";
    try{
      this.getConnection();
      PreparedStatement ps = con.prepareStatement(sql_string);
      ps.setString(1,username);
      ps.setString(2,password);
      ResultSet rs = ps.executeQuery();
      return rs.getBoolean(1);
    }catch(Exception e){
      throw new RuntimeException("Cannot validate tenant!!");
    }
  }

  /**
   * The method validates the company login details.
   *
   * @param username the username of the company
   * @param password the password of the company
   * @return true if login details are verified. False otherwise
   */
  @Override
  public boolean validateCompany(String username, String password) {
    String sql_string = "SELECT propertyproject.validate_company(?,?)";
    try{
      this.getConnection();
      PreparedStatement ps = con.prepareStatement(sql_string);
      ps.setString(1,username);
      ps.setString(2,password);
      ResultSet rs = ps.executeQuery();
      return rs.getBoolean(1);
    }catch(Exception e){
      throw new RuntimeException("Cannot validate tenant!!");
    }
  }

  /**
   * The method returns the list of all the available building names that are present.
   *
   * @return a list of all the building names
   */
  @Override
  public List<String> getAllBuildings() {
    String sql_string = "call propertyproject.get_all_buildings()";
    try{
      List<String> buildings = new ArrayList<>();
      this.getConnection();
      PreparedStatement ps = con.prepareStatement(sql_string);
      ResultSet rs = ps.executeQuery();
      while(rs.next()){
        buildings.add(rs.getString(1));
      }
      return buildings;
    }catch(Exception e){
      throw new RuntimeException("Cannot validate tenant!!");
    }
  }

  /**
   * The method returns the amenities and the available units present in the building as a map.
   *
   * @param buildingName the name of the building for which the details have to be returned
   * @return a map of amenities as key and their description as value and list of units as value to
   * the key of unit.
   */
  @Override
  public Map<String, String> getParticularBuildingInfo(String buildingName) {
    String sql_string = "call propertyproject.get_building_info(?)";
    try{
      Map<String, String> buildingInfo = new HashMap<>();
      this.getConnection();
      PreparedStatement ps = con.prepareStatement(sql_string);
      ps.setString(1, buildingName);
      ResultSet rs = ps.executeQuery();
      while(rs.next()){
        buildingInfo.put("amenities", rs.getString("amenity"));
        buildingInfo.put("unit", rs.getString("unit"));
      }
      return buildingInfo;
    }catch(Exception e){
      throw new RuntimeException("Cannot validate tenant!!");
    }
  }

  /**
   * The method creates a maintenance request for the particular unit.
   *
   * @param buildingName the name of the building
   * @param unitNo       the number of the unit
   * @param desc         the description of the request to be created
   */
  @Override
  public void createMaintenanceRequest(String buildingName, String unitNo, String desc) {

  }

  /**
   * The method returns all the past and active maintenance requests that are attached to a
   * particular unit.
   *
   * @param buildingName the name of the building
   * @param unitNo       the number of the unit
   * @return a list of maintenance requests as strings that are concatenated as Description and
   * status of it
   */
  @Override
  public List<String> getMaintenanceRequests(String buildingName, String unitNo) {
    String sql_string = "call propertyproject.get_maintenance_requests(?, ?)";
    try{
      List<String> requests = new ArrayList<>();
      this.getConnection();
      PreparedStatement ps = con.prepareStatement(sql_string);
      ps.setString(1, buildingName);
      ps.setString(2, unitNo);
      ResultSet rs = ps.executeQuery();
      while(rs.next()){
        requests.add(rs.getString("description")+" - "+rs.getString("status"));
      }
      return requests;
    }catch(Exception e){
      throw new RuntimeException("Cannot validate tenant!!");
    }
  }

  /**
   * The method checks if the particular unit is available or not.
   *
   * @param buildingName the name of the particular building
   * @param unitNo       the number of the unit
   * @return true if the unit is available to lease out. False otherwise
   */
  @Override
  public boolean isUnitAvailable(String buildingName, String unitNo) {
    String sql_string = "SELECT propertyproject.is_unit_available(?,?)";
    try{
      this.getConnection();
      PreparedStatement ps = con.prepareStatement(sql_string);
      ps.setString(1,buildingName);
      ps.setString(2,unitNo);
      ResultSet rs = ps.executeQuery();
      return rs.getBoolean(1);
    }catch(Exception e){
      throw new RuntimeException("Cannot validate tenant!!");
    }
  }

  /**
   * The method adds a maintenance personnel to a particular building.
   *
   * @param buildingName  the name of the building to which the personnel should be added to
   * @param personnelName the maintenance personnel's name
   * @param phoneNo       the phone number of the personnel.
   */
  @Override
  public void addMaintenancePersonnel(String buildingName, String personnelName, String phoneNo) {

  }

  /**
   * The method returns a list of maintenance personnel that are attached to a particular building.
   *
   * @param buildingName the name of the building
   * @return the map of the personnel's name as key and phone number of the personnel
   */
  @Override
  public Map<String, String> getMaintenancePersonnel(String buildingName) {
    return null;
  }

  /**
   * The method returns information about the lease of a particular tenant.
   *
   * @param tenantName the name of the tenant for whom the lease info has to be returned
   * @return the list of unit number, start date and end date as three separate strings
   */
  @Override
  public List<String> getLeaseInfo(String tenantName) {
    return null;
  }

  /**
   * The method helps in adding tenant to a particular unit in a building.
   *
   * @param tenantName     the name of the tenant to be added
   * @param buildingName   the name of the building
   * @param unitNo         the number of the unit
   * @param tenantPassword the password of the tenant
   */
  @Override
  public void addTenantToUnit(String tenantName, String buildingName, String unitNo, String tenantPassword) {

  }

  /**
   * The method helps create a company in the database.
   *
   * @param companyName     the name of the company to be created
   * @param companyPassword the password of the company to be created
   */
  @Override
  public void createCompany(String companyName, String companyPassword) {

  }

  /**
   * The method helps create a building with all the data that are passed such as amenities, name,
   * address etc..
   *
   * @param companyName      the name of the company to which the building has to be attached to
   * @param amenities        the amenities description of the building
   * @param address          the address of the building as a string
   * @param buildingName     the name of the building to be created
   * @param zipcode          the zipcode to which the building has to be attached
   * @param noOfFloors       the number of floors in the building
   * @param noOfParkingSpots the number of parking spots in the building
   * @param type             the type of the building
   */
  @Override
  public void createBuilding(String companyName, String amenities, String address, String buildingName, String zipcode, String noOfFloors, String noOfParkingSpots, String type) {

  }

  /**
   * The method returns the tenant of a particular unit in the building.
   *
   * @param buildingName the name of the building
   * @param unit the number of the unit
   * @return the tenant of the particular unit
   */
  @Override
  public String getTenants(String buildingName, String unit) {
    String sql_string = "SELECT propertyproject.get_tenants(?, ?)";
    try{
      this.getConnection();
      PreparedStatement ps = con.prepareStatement(sql_string);
      ps.setString(1, buildingName);
      ps.setString(2, unit);
      ResultSet rs = ps.executeQuery();
      while(rs.next()){
       return rs.getString(1);
      }
      return null;
    }catch(Exception e){
      throw new RuntimeException("Cannot validate tenant!!");
    }
  }

  /**
   * The method helps in adding units to a particular building.
   *
   * @param buildingName  the name of the building
   * @param noOfBedrooms  the number of bedrooms
   * @param noOfBathrooms the number of bathrooms
   * @param price         the price of the unit
   * @param area          the area measurement of the unit
   */
  @Override
  public void addUnits(String buildingName, int noOfBedrooms, int noOfBathrooms, Double price, Double area) {

  }
}

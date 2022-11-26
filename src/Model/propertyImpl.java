package Model;

import java.util.List;
import java.util.Map;

public class propertyImpl implements property {

  @Override
  public boolean validateTenant(String username, String password) {
    return false;
  }

  @Override
  public boolean validateCompany(String username, String password) {
    return false;
  }

  @Override
  public List<String> getAllBuildings() {
    return null;
  }

  @Override
  public Map<String, String> getParticularBuildingInfo(String buildingName) {
    return null;
  }

  @Override
  public void createMaintenanceRequest(String buildingName, String unitNo, String desc) {

  }

  @Override
  public List<String> getMaintenanceRequests(String buildingName, String unitNo) {
    return null;
  }

  @Override
  public boolean isUnitAvailable(String buildingName, String unitNo) {
    return false;
  }

  @Override
  public void addMaintenancePersonnel(String buildingName, String personnelName, String phoneNo) {

  }

  @Override
  public Map<String, String> getMaintenancePersonnel(String buildingName) {
    return null;
  }

  @Override
  public List<String> getLeaseInfo(String tenantName) {
    return null;
  }

  @Override
  public void addTenantToUnit(String tenantName, String buildingName, String unitNo, String tenantPassword) {

  }

  @Override
  public void createCompany(String companyName, String companyPassword) {

  }

  @Override
  public void createBuilding(String companyName, String amenities, String address, String buildingName, String zipcode, String noOfFloors, String noOfParkingSpots, String type) {

  }

  @Override
  public String getTenants() {
    return null;
  }

  @Override
  public void addUnits(String buildingName, int noOfBedrooms, int noOfBathrooms, Double price, Double area) {

  }
}

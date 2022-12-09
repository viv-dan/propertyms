package Model;

public class demo {
  public static void main(String[] args) {
    property p = new propertyImpl();
    //p.createCompany("dan management", "bostonA6#", "danmanagement@gmail.com", "1234567890");
    //p.createBuilding("dan management","3270 washington St", "The Brynx","02130",3,5,"Apartment");
    //p.addAmenity("The Brynx", "gym");
    //p.addAmenity("The Brynx", "trash");
    //System.out.println(p.getParticularBuildingInfo("The Brynx"));
    //System.out.println(p.validateCompany("dan management", "bostonA6#"));
    //p.addUnits("The Brynx",3, 2,5000.0,1300.0,1001);
    //p.addUnits("The Brynx",2, 1,3500.0,1000.0,1002);
    //System.out.println(p.getParticularBuildingInfo("The Brynx"));
    //p.addTenantToUnit("dan","The Brynx",1002,"bostonA6#","1992-12-09","software engineer","6759675209");
    //p.addTenantToUnit("vivdan","The Brynx",1001,"bostonA6#","1998-12-09","student","0987654321");
    //System.out.println(p.validateTenant("vivdan","bostonA6#"));
    //System.out.println(p.validateTenant("dan","bostonA6#"));
    //p.createCompany("dan realty","bostonA6#","danrealty@gmail.com","2943673940");
    //System.out.println(p.loadCompanyBuildings("dan management"));
    //System.out.println(p.loadCompanyBuildings("dan realty"));
    //System.out.println(p.getAmenities());
    //System.out.println(p.getTenants("The Brynx",1001));
    //System.out.println(p.getLeaseInfo("vivdan"));
    //System.out.println(p.getMaintenancePersonnel("The Brynx"));
    //p.addMaintenancePersonnel("The Brynx","John doe","6723098423");
    //System.out.println(p.getMaintenancePersonnel("The Brynx"));
    //System.out.println(p.isUnitAvailable("The Brynx",1003));
    //System.out.println(p.getMaintenanceRequests("The Brynx",1001));
    //p.createMaintenanceRequest("The Brynx",1001,"Pipes are leakingin the hall");
    //System.out.println(p.getMaintenanceRequests("The Brynx",1001));
    //System.out.println(p.getMaintenanceRequests("The Brynx",1002));
    //System.out.println(p.getAllBuildings());
    //p.createBuilding("dan realty","870 Huntington Ave", "The Longwood","02115",10,50,"Apartment");
    //System.out.println(p.getAllBuildings());
    //System.out.println(p.loadCompanyBuildings("dan realty"));
    //System.out.println(p.validateCompany("dan realty", "bostonA6#"));
    System.out.println(p.getBuildingMetadata("The Longwood"));
    }
}

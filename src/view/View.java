package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.ControllerFeatures;

public interface View {
  void setView(ControllerFeatures c);

  void showLandingMenu();

  void showInputError();

  void goBackMessage();

  void showListOfBuildings(List<String> l1);

  void showBuildingEnterMenu();

  Map<String,String> enterUserPassForm();

  void tenantMenu();

  void companyMenu();


  List<String> createMaintenanceRequestForm();

  void successMessage();

  void showErrorMessage(String message);

  void showMessage(String message);

  List<String> createNewBuildingForm();

  void showMaintenancePersonnel(Map<String, String> maintenancePersonnel);

  void showAmenity(List<String> amenities);
}

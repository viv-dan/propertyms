package view;

import java.util.ArrayList;
import java.util.List;

public interface View {
  void showLandingMenu();

  void showInputError();

  void goBackMessage();

  void showListOfBuildings(List<String> l1);

  void showBuildingEnterMenu();

  List<String> enterUserPassForm();

  void tenantMenu();

  void companyMenu();

  List<String> registerNewCompanyForm();

  ArrayList<String> createMaintenanceRequestForm();

  void successMessage();
}

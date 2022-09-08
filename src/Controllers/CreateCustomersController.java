package Controllers;

import Database.CountryQuery;
import Database.DBCustomers;
import Database.DivisionQuery;
import Models.Country;
import Models.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreateCustomersController implements Initializable {

    @FXML
    private Label AddressLabel;

    @FXML
    private Button Cancel;

    @FXML
    private ComboBox<String> CountryBox;

    @FXML
    private Label CreateCustomerLabel;

    @FXML
    private TextField CustomerAddress;

    @FXML
    private TextField CustomerID;

    @FXML
    private TextField CustomerName;

    @FXML
    private TextField CustomerPhone;

    @FXML
    private TextField CustomerPostalCode;

    @FXML
    private ComboBox<String> DivisionBox;

    @FXML
    private Button Home;

    @FXML
    private Label IdLabel;

    @FXML
    private Label NameLabel;

    @FXML
    private Label PhoneLabel;

    @FXML
    private Label PostalCodeLabel;

    @FXML
    private Button Save;

    @FXML
    void home(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Main Menu!!");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Navigate back to Customers?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() ==  ButtonType.OK)) {
            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/Views/Customers.fxml"));
                stage.setTitle("Customers!!");
                stage.setScene(new Scene(scene));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Load Screen Error.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void Save(ActionEvent event) throws SQLException {
        boolean valid = validateNotEmpty(
                CustomerName.getText(),
                CustomerAddress.getText(),
                CustomerPostalCode.getText(),
                CustomerPhone.getText());

        if (valid) {
            try {
                boolean success = DBCustomers.createCustomer(
                        CustomerName.getText(),
                        CustomerAddress.getText(),
                        CustomerPostalCode.getText(),
                        CustomerPhone.getText(),
                        DivisionBox.getValue(),
                        CountryBox.getValue());

                if (success) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Successfully created new customer");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && (result.get() ==  ButtonType.OK)) {
                        try {
                            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                            Parent scene = FXMLLoader.load(getClass().getResource("/Views/Customers.fxml"));
                            stage.setTitle("Customers!!");
                            stage.setScene(new Scene(scene));
                            stage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Dialog");
                            alert.setContentText("Load Screen Error.");
                            alert.showAndWait();
                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to save new customer");
                    Optional<ButtonType> result = alert.showAndWait();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void setCountryCombo(){
        ObservableList<String> countryList = FXCollections.observableArrayList();

        try {
            ObservableList<Country> countries = CountryQuery.getCountries();;
            if (countries != null) {
                for (Country country: countries) {
                    countryList.add(country.getCountry());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CountryBox.setItems(countryList);
    }

    private void setDivisionCombo(){
        ObservableList<String> divisionList = FXCollections.observableArrayList();

        try {
            ObservableList<Division> divisions = DivisionQuery.getDivisions();;
            if (divisions != null) {
                for (Division division: divisions) {
                    divisionList.add(division.getDivision());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DivisionBox.setItems(divisionList);
    }
    @FXML
    void SelectCountry(ActionEvent event) {
        ObservableList<String> divisionList = FXCollections.observableArrayList();
        try {
            ObservableList<Division> divisions = DivisionQuery.getDivisionsByCountry(CountryBox.getSelectionModel().getSelectedItem());
            if (divisions != null) {
                for (Division division: divisions) {
                    divisionList.add(division.getDivision());
                }
            }
            DivisionBox.setItems(divisionList);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    void SelectDivision(ActionEvent event) {

    }

    private boolean validateNotEmpty(String name, String address, String postalCode, String phone){
        if (name.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Name is required.");
            alert.showAndWait();
            return false;
        }

        if (address.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Address is required.");
            alert.showAndWait();
            return false;
        }

        if (postalCode.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Postal Code is required.");
            alert.showAndWait();
            return false;
        }

        if (phone.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Phone Number is required.");
            alert.showAndWait();
            return false;
        }

        if (DivisionBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Division is required.");
            alert.showAndWait();
            return false;
        }

        if (CountryBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Country is required.");
            alert.showAndWait();
            return false;
        }

        return true;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDivisionCombo();
        setCountryCombo();
    }
}

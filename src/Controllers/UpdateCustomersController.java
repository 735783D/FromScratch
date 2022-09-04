package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateCustomersController implements Initializable {

    @FXML
    private Label AddressLabel;

    @FXML
    private Button Cancel;

    @FXML
    private ComboBox<?> CountryBox;

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
    private ComboBox<?> DivisionBox;

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


    void Save(ActionEvent event){

    }

    @FXML
    void Cancel(ActionEvent event) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

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
import java.util.ResourceBundle;

public class CustomersController implements Initializable {

    @FXML
    private Button CreateCustomer;

    @FXML
    private Button DeleteCustomer;

    @FXML
    private Button MainMenu;

    @FXML
    private Button Search;

    @FXML
    private Button UpdateCustomer;

    @FXML
    private TableColumn<?, ?> CustomerAddress;

    @FXML
    private TableColumn<?, ?> CustomerCountry;

    @FXML
    private TableColumn<?, ?> CustomerFLD;

    @FXML
    private TableColumn<?, ?> CustomerId;

    @FXML
    private TableColumn<?, ?> CustomerName;

    @FXML
    private TableColumn<?, ?> CustomerPhone;

    @FXML
    private TableColumn<?, ?> CustomerPostalCode;

    @FXML
    private Label TitleCustomers;

    @FXML
    private TableView<?> Customers;

    public void BackToMain(ActionEvent event){
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Main Menu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    public void CreateCustomers(ActionEvent event){
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/Views/CreateCustomers" +
                    ".fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Create Customers");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

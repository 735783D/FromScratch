package Controllers;

import Database.DBCustomers;
import Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {

    static ObservableList<Customer> customers;


    @FXML
    private Button CreateCustomer;

    @FXML
    private Button DeleteCustomer;

    @FXML
    private TextField CustSearchText;

    @FXML
    private Button MainMenu;

    @FXML
    private Button CustSearchButton;

    @FXML
    private Button UpdateCustomer;

    @FXML
    private TableColumn<Customer, String > CustomerAddress;

    @FXML
    private TableColumn<Customer, String > CustomerCountry;

    @FXML
    private TableColumn<Customer, String > CustomerFLD;

    @FXML
    private TableColumn<Customer, Integer> CustomerId;

    @FXML
    private TableColumn<Customer, String > CustomerName;

    @FXML
    private TableColumn<Customer, String> CustomerPhone;

    @FXML
    private TableColumn<Customer, String> CustomerPostalCode;

    @FXML
    private Label TitleCustomers;

    @FXML
    private TableView<Customer> Customers;

    public void BackToMain(ActionEvent event){
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

    public void CreateCustomers(ActionEvent event){
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/Views/CreateCustomers" +
                    ".fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Create Customers!!");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    public void UpdateCustomers(ActionEvent event) {
        UpdateCustomersController.receiveSelectedCustomer(Customers.getSelectionModel().getSelectedItem());

        if (Customers.getSelectionModel().getSelectedItem() != null) {
            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/Views/UpdateCustomers.fxml"));
                stage.setScene(new Scene(scene));
                stage.setTitle("Update Customers!!");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Load Screen Error.");
                alert.showAndWait();
            }
        }
    }

    public void DeleteCustomer(ActionEvent event) {
        Customer selectedCustomer = Customers.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("You must select a customer to delete.");
            alert.showAndWait();
        } else if (Customers.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected customer. Do you wish to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                try {
                    boolean deleteSuccessful = DBCustomers.deleteCustomer(Customers.getSelectionModel().getSelectedItem().getCustomerId());

                    if (deleteSuccessful) {
                        customers = DBCustomers.getCustomers();
                        Customers.setItems(customers);
                        Customers.refresh();
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setContentText("Could not delete Customer.");
                        alert.showAndWait();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static ObservableList<Customer> lookupCustomer(String input) {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        for (Customer customer: customers) {
            if (customer.getCustomerName().contains(input)) {
                customerList.add(customer);
            } else if (Integer.toString(customer.getCustomerId()).contains(input)) {
                customerList.add(customer);
            }
        }
        return customerList;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customers = DBCustomers.getCustomers();

            Customers.setItems(customers);
            CustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            CustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            CustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            CustomerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            CustomerPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            CustomerFLD.setCellValueFactory(new PropertyValueFactory<>("division"));
            CustomerCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

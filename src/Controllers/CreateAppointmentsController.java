package Controllers;
import Database.DBContacts;
import Database.DBCustomers;
import Database.DBUsers;
import Models.Contact;
import Models.Customer;
import Models.User;
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
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreateAppointmentsController implements Initializable {

    @FXML
    private Button ButtonApptCancel;

    @FXML
    private Button ButtonApptMainMenu;

    @FXML
    private Button ButtonApptSave;

    @FXML
    private ComboBox<String > ComboContact;

    @FXML
    private ComboBox<Integer> ComboCustomerId;

    @FXML
    private ComboBox<String> ComboEndTime;

    @FXML
    private ComboBox<String> ComboStartTime;

    @FXML
    private ComboBox<String > ComboType;

    @FXML
    private ComboBox<Integer> ComboUserId;

    @FXML
    private Label ContactLabel;

    @FXML
    private DatePicker EndDatePicker;

    @FXML
    private Label LabelApptId;

    @FXML
    private Label LabelApptTitle;

    @FXML
    private Label LabelCustId;

    @FXML
    private Label LabelDesc;

    @FXML
    private Label LabelEndDate;

    @FXML
    private Label LabelEndTime;

    @FXML
    private Label LabelStartDate;

    @FXML
    private Label LabelStartTime;

    @FXML
    private Label LabelType;

    @FXML
    private Label LabelUserId;

    @FXML
    private DatePicker StartDatePicker;

    @FXML
    private TextField TextAppointmentId;

    @FXML
    private TextField TextDescription;

    @FXML
    private TextField TextLocation;

    @FXML
    private TextField TextTitle;

    @FXML
    private Label TitleLabel;

    @FXML
    private Label TitleLocation;

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

    @FXML
    void cancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Navigate back to Appointments?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() ==  ButtonType.OK)) {
            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/Views/Appointments.fxml"));
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

    /** Populates Start Time and End Time Combo Boxes in 15 minute increments
     */
    private void populateTimeComboBoxes() {
        ObservableList<String> time = FXCollections.observableArrayList();
        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = LocalTime.of(23, 0);

        time.add(startTime.toString());
        while (startTime.isBefore(endTime)) {
            startTime = startTime.plusMinutes(15);
            time.add(startTime.toString());
        }

        ComboStartTime.setItems(time);
        ComboEndTime.setItems(time);
    }

    /** Populates Contact Combo Box with Contacts List
     */
    private void populateContactComboBox() {
        ObservableList<String> contactComboList = FXCollections.observableArrayList();

        try {
            ObservableList<Contact> contacts = DBContacts.getContacts();
            if (contacts != null){
                for (Contact contact: contacts) {
                    if (!contactComboList.contains(contact.getContactName())) {
                        contactComboList.add(contact.getContactName());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ComboContact.setItems(contactComboList);
    }

    /** Populates Customer ID Combo Box with Customer ID List
     */
    private void populateCustomerIDComboBox() {
        ObservableList<Integer> customerIDComboList = FXCollections.observableArrayList();

        try {
            ObservableList<Customer> customers = DBCustomers.getCustomers();
            if (customers != null) {
                for (Customer customer: customers) {
                    customerIDComboList.add(customer.getCustomerId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ComboCustomerId.setItems(customerIDComboList);
    }

    /** Populates User ID Combo Box with User ID List
     */
    private void populateUserIDComboBox() {
        ObservableList<Integer> userIDComboList = FXCollections.observableArrayList();

        try {
            ObservableList<User> users = DBUsers.getUsers();
            if (users != null) {
                for (User user: users) {
                    userIDComboList.add(user.getUserId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ComboUserId.setItems(userIDComboList);
    }

    /** Populates Type Combo Box with hardcoded List
     */
    private void populateTypeComboBox() {
        ObservableList<String> typeList = FXCollections.observableArrayList();

        typeList.addAll("Planning Session", "De-Briefing", "Follow-up", "Pre-Briefing", "Open Session");

        ComboType.setItems(typeList);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateTimeComboBoxes();
        populateContactComboBox();
        populateCustomerIDComboBox();
        populateUserIDComboBox();
        populateTypeComboBox();
    }
}

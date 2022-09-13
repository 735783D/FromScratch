package Controllers;

import Database.DBAppointments;
import Database.DBContacts;
import Database.DBCustomers;
import Database.DBUsers;
import Models.Appointment;
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
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class UpdateAppointmentController implements Initializable {

    /**
     * Method to receive selected appointment from Appointment View
     *
     * @param appointment Selected Appointment
     */
    public static void receiveSelectedAppointment(Appointment appointment) {
        selectedAppointment = appointment;
    }


    private ZonedDateTime StartDateTimeConversion;
    private ZonedDateTime EndDateTimeConversion;

    /**
     * Appointment selected from Appointment Controller View
     */
    private static Appointment selectedAppointment;
    @FXML
    private Button ButtonApptCancel;

    @FXML
    private Button ButtonApptMainMenu;

    @FXML
    private Button ButtonApptSave;

    @FXML
    private ComboBox<String> ComboContact;

    @FXML
    private ComboBox<Integer> ComboCustomerId;

    @FXML
    private ComboBox<String> ComboEndTime;

    @FXML
    private ComboBox<String> ComboStartTime;

    @FXML
    private ComboBox<String> ComboType;

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

    /**
     * Converts time to EST.
     *
     * @param time LocalDateTime time to convert
     * @return ZonedDateTime Returns time converted to EST
     */
    private ZonedDateTime convertToEST(LocalDateTime time) {
        return ZonedDateTime.of(time, ZoneId.of("America/New_York"));
    }

    private ZonedDateTime convertToTimeZone(LocalDateTime time, String zoneId) {
        return ZonedDateTime.of(time, ZoneId.of(zoneId));
    }

    @FXML
    void BackToMain(ActionEvent event) {
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

        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/Views/Appointments.fxml"));
                stage.setTitle("Appointments!!");
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

    /**
     * Populates Start Time and End Time Combo Boxes in 15 minute increments
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

    /**
     * Populates Contact Combo Box with Contacts List
     */
    private void populateContactComboBox() {
        ObservableList<String> contactComboList = FXCollections.observableArrayList();

        try {
            ObservableList<Contact> contacts = DBContacts.getContacts();
            if (contacts != null) {
                for (Contact contact : contacts) {
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

    /**
     * Populates Customer ID Combo Box with Customer ID List
     */
    private void populateCustomerIDComboBox() {
        ObservableList<Integer> customerIDComboList = FXCollections.observableArrayList();

        try {
            ObservableList<Customer> customers = DBCustomers.getCustomers();
            if (customers != null) {
                for (Customer customer : customers) {
                    customerIDComboList.add(customer.getCustomerId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ComboCustomerId.setItems(customerIDComboList);
    }

    /**
     * Populates User ID Combo Box with User ID List
     */
    private void populateUserIDComboBox() {
        ObservableList<Integer> userIDComboList = FXCollections.observableArrayList();

        try {
            ObservableList<User> users = DBUsers.getUsers();
            if (users != null) {
                for (User user : users) {
                    userIDComboList.add(user.getUserId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ComboUserId.setItems(userIDComboList);
    }

    /**
     * Populates Type Combo Box with hardcoded List
     */
    private void populateTypeComboBox() {
        ObservableList<String> typeList = FXCollections.observableArrayList();

        typeList.addAll("Planning Session", "De-Briefing", "Follow-up", "Pre-Briefing", "Open Session");

        ComboType.setItems(typeList);
    }

    @FXML
    void Save(ActionEvent event) {
        boolean valid = validateAppointment(
                TextTitle.getText(),
                TextDescription.getText(),
                TextLocation.getText(),
                TextAppointmentId.getText()
        );

        if (valid) {
            try {
                boolean success = DBAppointments.updateAppointment(
                        ComboContact.getSelectionModel().getSelectedItem(),
                        TextTitle.getText(),
                        TextDescription.getText(),
                        TextLocation.getText(),
                        ComboType.getSelectionModel().getSelectedItem(),
                        LocalDateTime.of(StartDatePicker.getValue(), LocalTime.parse(ComboStartTime.getSelectionModel().getSelectedItem())),
                        LocalDateTime.of(EndDatePicker.getValue(), LocalTime.parse(ComboEndTime.getSelectionModel().getSelectedItem())),
                        ComboCustomerId.getSelectionModel().getSelectedItem(),
                        ComboUserId.getSelectionModel().getSelectedItem(),
                        Integer.parseInt(TextAppointmentId.getText()));

                if (success) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Successfully created new appointment");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && (result.get() == ButtonType.OK)) {
                        try {
                            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                            Parent scene = FXMLLoader.load(getClass().getResource("/Views/Appointments.fxml"));
                            stage.setTitle("Appointments!!");
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
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to save new appointment");
                    Optional<ButtonType> result = alert.showAndWait();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateAppointment(String title, String description, String location, String appointmentId) {
        if (ComboContact.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Contact is required.");
            alert.showAndWait();
            return false;
        }

        if (title.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Title is required.");
            alert.showAndWait();
            return false;
        }

        if (description.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Description is required.");
            alert.showAndWait();
            return false;
        }

        if (location.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Location is required.");
            alert.showAndWait();
            return false;
        }

//        if (appointmentId.isEmpty()) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error Dialog");
//            alert.setContentText("Appointment ID is required.");
//            alert.showAndWait();
//            return false;
//        }

        if (ComboType.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Type is required.");
            alert.showAndWait();
            return false;
        }

        if (StartDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Start Date is required.");
            alert.showAndWait();
            return false;
        }

        if (ComboStartTime.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Start Time is required.");
            alert.showAndWait();
            return false;
        }

        if (EndDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("End Date is required.");
            alert.showAndWait();
            return false;
        }

        if (EndDatePicker.getValue().isBefore(StartDatePicker.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("End Date must be after Start Date.");
            alert.showAndWait();
            return false;
        }

        if (ComboEndTime.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("End Time is required.");
            alert.showAndWait();
            return false;
        }

        if (ComboCustomerId.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Customer ID is required.");
            alert.showAndWait();
            return false;
        }

        if (ComboUserId.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("User ID is required.");
            alert.showAndWait();
            return false;
        }

        // additional date validation

        LocalTime startTime = LocalTime.parse(ComboStartTime.getSelectionModel().getSelectedItem());
        LocalTime endTime = LocalTime.parse(ComboEndTime.getSelectionModel().getSelectedItem());

        if (endTime.isBefore(startTime)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointment start time must be before end time.");
            alert.showAndWait();
            return false;
        }
        ;

        LocalDate startDate = StartDatePicker.getValue();
        LocalDate endDate = EndDatePicker.getValue();

        if (!startDate.equals(endDate)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointments must start and end on the same date.");
            alert.showAndWait();
            return false;
        }
        ;

        // Check for overlapping appointments

        LocalDateTime selectedStart = startDate.atTime(startTime);
        LocalDateTime selectedEnd = endDate.atTime(endTime);

        LocalDateTime proposedAppointmentStart;
        LocalDateTime proposedAppointmentEnd;


        try {
            ObservableList<Appointment> appointments = DBAppointments.getAppointmentsByCustomerID(ComboCustomerId.getSelectionModel().getSelectedItem());
            for (Appointment appointment : appointments) {
                proposedAppointmentStart = appointment.getStartDate().atTime(appointment.getStartTime().toLocalTime());
                proposedAppointmentEnd = appointment.getEndDate().atTime(appointment.getEndTime().toLocalTime());

                if (proposedAppointmentStart.isAfter(selectedStart) && proposedAppointmentStart.isBefore(selectedEnd)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Appointments must not overlap with existing customer appointments.");
                    alert.showAndWait();
                    return false;
                } else if (proposedAppointmentEnd.isAfter(selectedStart) && proposedAppointmentEnd.isBefore(selectedEnd)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Appointments must not overlap with existing customer appointments.");
                    alert.showAndWait();
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // check if between business hours
        StartDateTimeConversion = convertToEST(LocalDateTime.of(StartDatePicker.getValue(), LocalTime.parse(ComboStartTime.getSelectionModel().getSelectedItem())));
        EndDateTimeConversion = convertToEST(LocalDateTime.of(EndDatePicker.getValue(), LocalTime.parse(ComboEndTime.getSelectionModel().getSelectedItem())));

        if (StartDateTimeConversion.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointments must be within business hours 8AM - 10PM EST.");
            alert.showAndWait();
            return false;
        }

        if (EndDateTimeConversion.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointments must be within business hours 8AM - 10PM EST.");
            alert.showAndWait();
            return false;
        }

        if (StartDateTimeConversion.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointments must be within business hours 8AM - 10PM EST.");
            alert.showAndWait();
            return false;
        }

        if (EndDateTimeConversion.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointments must be within business hours 8AM - 10PM EST.");
            alert.showAndWait();
            return false;
        }

        return true;
    }

    @FXML
    void PickStartDate(ActionEvent event) {

    }

    @FXML
    void PickEndDate(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateTimeComboBoxes();
        populateContactComboBox();
        populateCustomerIDComboBox();
        populateUserIDComboBox();
        populateTypeComboBox();

        try {
            Appointment appointment = DBAppointments.getAppointmentByAppointmentID(selectedAppointment.getAppointmentId());

            ZonedDateTime zonedStartTime = convertToTimeZone(appointment.getStartDate().atTime(appointment.getStartTime().toLocalTime()), String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));
            ZonedDateTime zonedEndTime = convertToTimeZone(appointment.getEndDate().atTime(appointment.getEndTime().toLocalTime()), String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));

            if (appointment != null) {
                ComboContact.getSelectionModel().select(appointment.getContactName());
                TextTitle.setText(appointment.getTitle());
                TextDescription.setText(appointment.getDescription());
                TextLocation.setText(appointment.getLocation());
                ComboType.getSelectionModel().select(appointment.getType());
                ComboUserId.getSelectionModel().select(Integer.valueOf(appointment.getUserId()));
                TextAppointmentId.setText(String.valueOf(appointment.getAppointmentId()));
                StartDatePicker.setValue(appointment.getStartDate());
                ComboStartTime.getSelectionModel().select(String.valueOf(zonedStartTime.toLocalTime()));
                EndDatePicker.setValue(appointment.getEndDate());
                ComboEndTime.getSelectionModel().select(String.valueOf(zonedEndTime.toLocalTime()));
                ComboCustomerId.getSelectionModel().select(Integer.valueOf(appointment.getCustomerId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

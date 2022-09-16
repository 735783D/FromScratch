package Controllers;

import Database.DBAppointments;
import Models.Appointment;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {
    static ObservableList<Appointment> appointments;
    @FXML
    private RadioButton AllTimesDisplay;

    @FXML
    private TableView<Appointment> Appointments;

    @FXML
    private Label AppointmentsTitle;

    @FXML
    private TextField ApptSearch;

    @FXML
    private Button Cancel;

    @FXML
    private TableColumn<Appointment, Integer> ColumnAppointmentID;

    @FXML
    private TableColumn<Appointment, Integer> ColumnContact;

    @FXML
    private TableColumn<Appointment, Integer> ColumnCustomerID;

    @FXML
    private TableColumn<Appointment, String> ColumnDescription;

    @FXML
    private TableColumn<Appointment, String > ColumnEnd;

    @FXML
    private TableColumn<Appointment, String > ColumnLocation;

    @FXML
    private TableColumn<Appointment, String> ColumnStart;

    @FXML
    private TableColumn<Appointment, String> ColumnTitle;

    @FXML
    private TableColumn<Appointment, String> ColumnType;

    @FXML
    private TableColumn<Appointment, Integer> ColumnUserID;

    @FXML
    private Button CreateAppointment;

    @FXML
    private Button DeleteAppointment;

    @FXML
    private Label Displays;

    @FXML
    private RadioButton MonthDisplay;

    @FXML
    private Button Search;

    @FXML
    private Button UpdateAppointment;

    @FXML
    private RadioButton WeekDisplay;

    @FXML
    private ToggleGroup ToggleView;



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

    public void CreateAppointmentMenu(ActionEvent event){
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/Views/CreateAppointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Appointments!!");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }


    public void UpdateAppointmentMenu(ActionEvent event){
        UpdateAppointmentController.receiveSelectedAppointment(Appointments.getSelectionModel().getSelectedItem());

        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/Views/UpdateAppointment.fxml"));
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

    /** Deletes appointment when clicked.
     * Appointment must be selected prior to clicking Delete Appointment button or it will throw an error dialog.
     * Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent deletes appointment when clicked
     */
    @FXML
    void DeleteAppointment(ActionEvent event) {
        Appointment selectedAppointment = Appointments.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("You must select an appointment to delete.");
            alert.showAndWait();
        } else if (Appointments.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected appointment. Do you wish to continue?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                try {
                    boolean deleteSuccessful = DBAppointments.deleteAppointment(Appointments.getSelectionModel().getSelectedItem().getAppointmentId());

                    if (deleteSuccessful) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Successful Delete");
                        alert.setContentText("Successfully deleted Appointment ID: " + selectedAppointment.getAppointmentId() + " Type: " + selectedAppointment.getType());
                        alert.showAndWait();

                        appointments = DBAppointments.getAppointments();
                        Appointments.setItems(appointments);
                        Appointments.refresh();
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setContentText("Could not delete appointment.");
                        alert.showAndWait();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /** Toggle View - All, Week, or Month.
     * Sets Appointment Table based on Radio Button selected by User.
     * Catches SQL exceptions and prints stacktrace.
     * @param event ActionEvent updates Appointment Table when Radio Button is selected
     */
    @FXML
    void ViewToggle(ActionEvent event) {

        if (AllTimesDisplay.isSelected()) {
            try {
                appointments = DBAppointments.getAppointments();
                Appointments.setItems(appointments);
                Appointments.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ToggleView.getSelectedToggle().equals(MonthDisplay)) {
            try {
                appointments = DBAppointments.getAppointmentsMonth();
                Appointments.setItems(appointments);
                Appointments.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ToggleView.getSelectedToggle().equals(WeekDisplay)) {
            try {
                appointments = DBAppointments.getAppointmentsWeek();
                Appointments.setItems(appointments);
                Appointments.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /** Updates Appointment Table based on search text
     * @param event ActionEvent when search button is clicked
     */
    @FXML
    void SearchAppointments(ActionEvent event) {
        ObservableList<Appointment> updateTable = lookupAppointment(Search.getText());
        Appointments.setItems(updateTable);
    }

    /** Helper function for Search Functionality
     * Gets Appointment List based on Search input
     * @param input String value of search text
     * @return ObservableList List of Appointments
     */
    private static ObservableList<Appointment> lookupAppointment(String input) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        for (Appointment appointment: appointments) {
            if (appointment.getTitle().contains(input)) {
                appointmentList.add(appointment);
            } else if (Integer.toString(appointment.getAppointmentId()).contains(input)) {
                appointmentList.add(appointment);
            }
        }
        return appointmentList;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AllTimesDisplay.setToggleGroup(ToggleView);
        WeekDisplay.setToggleGroup(ToggleView);
        MonthDisplay.setToggleGroup(ToggleView);
        try {
            appointments = DBAppointments.getAppointments();

            Appointments.setItems(appointments);
            ColumnAppointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            ColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            ColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            ColumnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
            ColumnContact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            ColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
            ColumnStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            ColumnEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            ColumnCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            ColumnUserID.setCellValueFactory(new PropertyValueFactory<>("userId"));


        } catch (SQLException e) {
            e.printStackTrace();
        }

        //______________________________________

    }
}

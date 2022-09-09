package Controllers;

import Database.DBAppointments;
import Models.Appointment;
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
    private TableColumn<Appointment, String > ColumnContact;

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


    public void UpdateAppointmentMenu(ActionEvent event){
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
            ColumnContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            ColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
            ColumnStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            ColumnEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            ColumnCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            ColumnUserID.setCellValueFactory(new PropertyValueFactory<>("userId"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

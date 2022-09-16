package Controllers;

import Database.DBAppointments;
import Database.DBUsers;
import Models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

interface LogActivity{
    public String getFileName();
}

public class LoginController implements Initializable {

    LogActivity logActivity = () -> {
        return "login_activity.txt";
    };

    private ResourceBundle resourceBundle;



    @FXML
    private Button ButtonCancel;

    @FXML
    private Button ButtonLogin;

    @FXML
    private Label LabelInfo;

    @FXML
    private Label LabelLocation;

    @FXML
    private Label LabelLocationDisplay;

    @FXML
    private Label LabelPassword;

    @FXML
    private Label LabelTimeZone;

    @FXML
    private Label LabelTimeZoneDisplay;

    @FXML
    private Label LabelTitle;

    @FXML
    private Label LabelUsername;

    @FXML
    private TextField TextPassword;

    @FXML
    private TextField TextUsername;

    /** Helper function to create login_activity.txt file if it doesn't already exist
     *  Catches Exception and prints stacktrace.
     *  Retrieves file name value from Lambda Expression
     */
    private void createFile(){
        try {
            File newfile = new File(logActivity.getFileName());
            if (newfile.createNewFile()) {
                System.out.println("File created:" + newfile.getName());
            } else {
                System.out.println("File already exists. Location: "+ newfile.getPath());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void  loginButton(ActionEvent event) throws SQLException {

        if (TextUsername.getText().isBlank() == false && TextPassword.getText().isBlank() == false) {

        } else {
            LabelInfo.setText("Please enter username and password.");
        }

        String username = TextUsername.getText();
        String password = TextPassword.getText();
        createFile();

        boolean validLogin = DBUsers.checkUsernamePassword(username, password);
        if(validLogin) {
            loginSuccess();

            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.setTitle("Login");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Load Screen Error.");
                alert.showAndWait();
            }

        }  else {

        loginFailure();

        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("errorDialog"));
            alert.setContentText(resourceBundle.getString("WrongUsernameOrPassword"));
            alert.showAndWait();
        }
    }


    }
    private void alertAppointment(){
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTimePlus15 = localDateTime.plusMinutes(15);

        ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();


        try {
            ObservableList<Appointment> appointments = DBAppointments.getAppointments();

            if (appointments != null) {
                for (Appointment appointment: appointments) {
                    if (appointment.getStartTime().isAfter(localDateTime) && appointment.getStartTime().isBefore(localDateTimePlus15)) {
                        upcomingAppointments.add(appointment);

                        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle(resourceBundle.getString("appointmentAlert"));
                            alert.setContentText(
                                    resourceBundle.getString("lessThanFifteenMin") +
                                            "\n" +
                                            resourceBundle.getString("appointmentId") +
                                            " " +
                                            + appointment.getAppointmentId() +
                                            "\n" +
                                            resourceBundle.getString("date") +
                                            " " +
                                            appointment.getStartDate() +
                                            "\n" +
                                            resourceBundle.getString("time") +
                                            " " +
                                            appointment.getStartTime().toLocalTime());
                            alert.setResizable(true);
                            alert.showAndWait();
                        }
                    }
                }

                if (upcomingAppointments.size() < 1) {
                    if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(resourceBundle.getString("appointmentAlert"));
                        alert.setContentText(resourceBundle.getString("noUpcomingAppointments"));
                        alert.setResizable(true);
                        alert.showAndWait();
                    }
                }
            } else {
                if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(resourceBundle.getString("appointmentAlert"));
                    alert.setContentText(resourceBundle.getString("noUpcomingAppointments"));
                    alert.setResizable(true);
                    alert.showAndWait();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Helper function to write user login success activity to the login_activity.txt file
     *  Catches IOException, throws alert, and prints stacktrace.
     *  Retrieves file name value from Lambda Expression
     */
    private void loginSuccess() {

        alertAppointment();

        try {
            FileWriter fileWriter = new FileWriter(logActivity.getFileName(), true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            java.util.Date date = new java.util.Date(System.currentTimeMillis());
            fileWriter.write("Successful Login: Username=" + TextUsername.getText() + " Password=" + TextPassword.getText() + " Timestamp: " + simpleDateFormat.format(date) + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Helper function to write user login failure activity to the login_activity.txt file
     *  Catches IOException, throws alert, and prints stacktrace.
     *  Retrieves file name value from Lambda Expression
     */
    private void loginFailure() {
        try {
            FileWriter fileWriter = new FileWriter(logActivity.getFileName(), true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            java.util.Date date = new Date(System.currentTimeMillis());
            fileWriter.write("Failed Login: Username=" + TextUsername.getText() + " Password=" + TextPassword.getText() + " Timestamp: " + simpleDateFormat.format(date) + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelButton(ActionEvent event){
        Stage stage = (Stage) ButtonCancel.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout!");
        alert.setContentText("Are you sure you want to log out?");
        alert.showAndWait();
        stage.close();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        resourceBundle = ResourceBundle.getBundle("Language/language", Locale.getDefault());

        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")){


            LabelTitle.setText(resourceBundle.getString("title"));
            LabelUsername.setText(resourceBundle.getString("username"));
            LabelPassword.setText(resourceBundle.getString("password"));
            LabelLocation.setText(resourceBundle.getString("location"));
            LabelLocationDisplay.setText(resourceBundle.getString("country"));
            LabelTimeZone.setText(resourceBundle.getString("timezone"));
            LabelTimeZoneDisplay.setText(String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));
            ButtonLogin.setText(resourceBundle.getString("login"));
            ButtonCancel.setText(resourceBundle.getString("cancel"));
        }
    }
}

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

public class CreateAppointmentsController implements Initializable {

    @FXML
    private Button ButtonApptCancel;

    @FXML
    private Button ButtonApptMainMenu;

    @FXML
    private Button ButtonApptSave;

    @FXML
    private ComboBox<?> ComboContact;

    @FXML
    private ComboBox<?> ComboCustomerId;

    @FXML
    private ComboBox<?> ComboEndTime;

    @FXML
    private ComboBox<?> ComboStartTime;

    @FXML
    private ComboBox<?> ComboType;

    @FXML
    private ComboBox<?> ComboUserId;

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

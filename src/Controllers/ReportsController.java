package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML
    private Button BackToMainButton;

    @FXML
    private ComboBox<?> ContactIdCombo;

    @FXML
    private Label ContactIdLabel;

    @FXML
    private Pane ContactIdPane;

    @FXML
    private Button CreateReportButton;

    @FXML
    private ComboBox<?> CustomerIdCombo;

    @FXML
    private Label CustomerIdLabel;

    @FXML
    private Pane CustomerIdPane;

    @FXML
    private Pane MainPane;

    @FXML
    private Label MainPaneLabel;

    @FXML
    private RadioButton MonthSelectionRadioButton;

    @FXML
    private Label ReportTitleLabel;

    @FXML
    private RadioButton TypeSelectionRadioButton;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

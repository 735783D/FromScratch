package Controllers;

import Database.DBUsers;
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

import java.net.URL;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button ButtonCancel;

    @FXML
    private Button ButtonLogin;

    @FXML
    private Label LabelPassword;

    @FXML
    private Label LabelUsername;

    @FXML
    private Label LabelInfo;

    @FXML
    private TextField TextPassword;

    @FXML
    private TextField TextUsername;
    public void  loginButton(ActionEvent event) throws SQLException {

        if (TextUsername.getText().isBlank() == false && TextPassword.getText().isBlank() == false) {
            //validateLogin();
        } else {
            LabelInfo.setText("Please enter username and password.");
        }

        String username = TextUsername.getText();
        String password = TextPassword.getText();

        boolean loginSuccess = DBUsers.checkUsernamePassword(username, password);
        if(loginSuccess) {

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

        }


    }

//    public void validateLogin() {
//        Connection myConn = null;
//        PreparedStatement myStmt = null;
//        ResultSet myRs = null;
//
//
//
//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connectDB= DriverManager.getConnection("jdbc:mysql://localhost:3306/c195","root", "toor");
//
//            Statement statement = connectDB.createStatement();
//
//            String verifyLogin = "select * from user where username='" + txtUsername.getText() + "' and password='" + txtPassword.getText() + "'";
//            ResultSet queryResult = statement.executeQuery(verifyLogin);
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//            e.getCause();
//        }
//
//    }

    public void cancelButton(ActionEvent event){
        Stage stage = (Stage) ButtonCancel.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout!");
        alert.setContentText("Are you sure you want to log out?");
        alert.showAndWait();
        stage.close();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

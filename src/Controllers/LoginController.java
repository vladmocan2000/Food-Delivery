package Controllers;

import Business.DeliveryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoginController {

    ObservableList<String> choiseList = FXCollections.observableArrayList("Client", "Employee", "Admin");

    public Button login;
    public Button signUp;
    public TextField username;
    public TextField password;
    public ChoiceBox<String> chooseUser;
    public DeliveryService deliveryService = new DeliveryService();

    public void initialize() {

        chooseUser.setItems(choiseList);
    }

    private boolean searchAccount(String username, String password, String fileName) throws FileNotFoundException {
        String s;
        File file = new File(fileName + ".txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine())
        {
            s = sc.nextLine();
            String[] split = s.split(" ");
            if(split[0].equals(username) && split[1].equals(password)) {

                sc.close();
                return true;
            }
        }
        sc.close();

        return false;
    }

    public void signUp() {

        openGUI("../presentation/SignUp.fxml", 600, 400);
    }

    public void login() throws FileNotFoundException {

        if(chooseUser.getSelectionModel().getSelectedItem() == choiseList.get(0)) {
            if(searchAccount(this.username.getText(),this.password.getText(), "client")) {

                openGUIClient("../presentation/Client.fxml",1154, 520);
            }
        }

        if(chooseUser.getSelectionModel().getSelectedItem() == choiseList.get(1)) {
            if(searchAccount(this.username.getText(),this.password.getText(), "employee")) {

                openGUIEmployee("../presentation/Employee.fxml",1154, 520);
            }

        }

        if(chooseUser.getSelectionModel().getSelectedItem() == choiseList.get(2)) {
            if(searchAccount(this.username.getText(),this.password.getText(), "administrator")) {

                openGUI("../presentation/Administrator.fxml",1154, 520);
            }
        }
    }

    private void openGUIClient(String name, int x, int y)
    {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
            Parent root = loader.load();
            ClientController clientController = loader.getController();
            clientController.setUsername(username.getText());
            clientController.setDeliveryService(deliveryService);
            Stage stage = new Stage();
            stage.setTitle("Food Delivery");
            stage.setScene(new Scene(root, x, y));
            stage.show();
        }catch(Exception e){
            e.getCause();
            e.printStackTrace();
        }
    }

    private void openGUI(String name, int x, int y)
    {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Food Delivery");
            stage.setScene(new Scene(root, x, y));
            stage.show();
        }catch(Exception e){
            e.getCause();
            e.printStackTrace();
        }
    }

    private void openGUIEmployee(String name, int x, int y)
    {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
            Parent root = loader.load();
            EmployeeController employeeController = loader.getController();
            deliveryService.addObserver(employeeController);
            Stage stage = new Stage();
            stage.setTitle("Food Delivery");
            stage.setScene(new Scene(root, x, y));
            stage.show();
        }catch(Exception e){
            e.getCause();
            e.printStackTrace();
        }
    }
}

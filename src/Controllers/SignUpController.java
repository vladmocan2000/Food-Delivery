package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.Scanner;

public class SignUpController {

    ObservableList<String> choiseList = FXCollections.observableArrayList("Client", "Employee", "Admin");

    public Button signUp;
    public TextField username;
    public TextField password;
    public ChoiceBox<String> chooseUser;

    public void initialize() {

        chooseUser.setItems(choiseList);
    }

    private boolean searchAccount(String username, String fileName) throws FileNotFoundException {

        String s;
        File file = new File(fileName + ".txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine())
        {
            s = sc.nextLine();
            String[] split = s.split(" ");
            if(split[0].equals(username))
                return true;
        }

        return false;
    }

    private void addAccount(String username, String password, String fileName) throws IOException {

        FileWriter fileWriter = new FileWriter(fileName + ".txt", true);
        fileWriter.write("\n" + username + " " + password);
        fileWriter.close();
    }

    public void signUp() throws IOException {

        if(chooseUser.getSelectionModel().getSelectedItem() == choiseList.get(0)) {
            if(!searchAccount(this.username.getText(), "client")) {

                addAccount(this.username.getText(), this.password.getText(), "client");
            }
        }

        if(chooseUser.getSelectionModel().getSelectedItem() == choiseList.get(1)) {
            if(!searchAccount(this.username.getText(), "employee")) {

                addAccount(this.username.getText(), this.password.getText(), "employee");
            }

        }

        if(chooseUser.getSelectionModel().getSelectedItem() == choiseList.get(2)) {
            if(!searchAccount(this.username.getText(), "administrator")) {

                addAccount(this.username.getText(), this.password.getText(), "administrator");
            }
        }
    }

}

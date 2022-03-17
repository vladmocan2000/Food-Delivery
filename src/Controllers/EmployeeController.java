package Controllers;

import Business.DeliveryService;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.util.Observable;
import java.util.Observer;

public class EmployeeController implements Observer {

    public ListView<String> ordersView;
    DeliveryService deliveryService = new DeliveryService();

    @Override
    public void update(Observable o, Object arg) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("New Order");
        alert.setHeaderText("New order was added: " + arg);
        alert.showAndWait();

    }

    public void listOrders() {

        deliveryService.listOrders(ordersView);
    }
    
}

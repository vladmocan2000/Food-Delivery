package sample;

import Business.BaseProduct;
import Business.CompositeProduct;
import Business.MenuItem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../Presentation/Login.fxml"));
        primaryStage.setTitle("Food Delivery");
        primaryStage.setScene(new Scene(root, 532, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        BaseProduct b1 = new BaseProduct("p1", (float) 7.8, 10, 3, 5, 3, 19);
        BaseProduct b2 = new BaseProduct("p2", (float) 7.8, 10,3, 5, 3, 19);
        BaseProduct b3 = new BaseProduct("p3", (float) 7.8, 10,3, 5, 3, 19);

        CompositeProduct c = new CompositeProduct("Felul 1", (float)10);
        CompositeProduct m = new CompositeProduct("Meniul zilei", (float)11);

        c.addMenuItem(b1);
        c.addMenuItem(b2);
        m.addMenuItem(c);
        m.addMenuItem(b3);

        ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
        menu.add(b1);
        menu.add(b2);
        menu.add(b3);
        menu.add(c);
        menu.add(m);

        for(MenuItem menuItem:menu) {

            //System.out.println(menuItem.toString() + " Pret: " + menuItem.getPrice() + " lei " + menuItem.getRating());
        }
    }
}

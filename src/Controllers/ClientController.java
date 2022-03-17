package Controllers;

import Business.DeliveryService;
import Business.MenuItem;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ClientController {

    private String username;

    public ListView<String> productsView;
    public TextField name;
    public TextField price;
    public TextField minRating;
    public Button listProducts;
    public ListView<String> cartView;
    public Button addProduct;
    public Button placeOrder;
    public Label labelPrice;
    DeliveryService deliveryService;

    public void setDeliveryService(DeliveryService deliveryService)
    {
        this.deliveryService = deliveryService;
    }

    public void listProducts() {
        deliveryService.listProductsClient(name, price, minRating, productsView);
        /*
        if(name.getText().equals("") && price.getText().equals("") && minRating.getText().equals("")) {
            deliveryService.products.forEach(a -> productsView.getItems().add(a + " Price: " + a.getPrice() + " Rating: " + a.getRating() + " Calories: " + a.getCalories() +" Protein: " + a.getProtein() + " Fat: " + a.getFat() + " Sodium: " + a.getSodium()));
        }
        else if(name.getText().equals("") && price.getText().equals("")) {

            productsView.getItems().clear();

            assert deliveryService.products != null;
            List<MenuItem> list = deliveryService.products
                    .stream()
                    .filter(c->c.getRating() >= Float.parseFloat(minRating.getText()))
                    .collect(Collectors.toList());
            list.forEach(a->productsView.getItems().add(a + " Price: " + a.getPrice() + " Rating: " + a.getRating() + " Calories: " + a.getCalories() +" Protein: " + a.getProtein() + " Fat: " + a.getFat() + " Sodium: " + a.getSodium()));
        }
        else if(name.getText().equals("") && minRating.getText().equals("")) {

            productsView.getItems().clear();

            assert deliveryService.products != null;
            List<MenuItem> list = deliveryService.products
                    .stream()
                    .filter(c->c.getPrice() <= Integer.parseInt(price.getText()))
                    .collect(Collectors.toList());
            list.forEach(a->productsView.getItems().add(a + " Price: " + a.getPrice() + " Rating: " + a.getRating() + " Calories: " + a.getCalories() +" Protein: " + a.getProtein() + " Fat: " + a.getFat() + " Sodium: " + a.getSodium()));
        }
        else if(price.getText().equals("") && minRating.getText().equals("")) {

            productsView.getItems().clear();

            assert deliveryService.products != null;
            List<MenuItem> list = deliveryService.products
                    .stream()
                    .filter(c->c.getName().toLowerCase().contains(name.getText().toLowerCase()))
                    .collect(Collectors.toList());
            list.forEach(a->productsView.getItems().add(a + " Price: " + a.getPrice() + " Rating: " + a.getRating() + " Calories: " + a.getCalories() +" Protein: " + a.getProtein() + " Fat: " + a.getFat() + " Sodium: " + a.getSodium()));
        }
        else if(name.getText().equals("")) {

            productsView.getItems().clear();

            assert deliveryService.products != null;
            List<MenuItem> list = deliveryService.products
                    .stream()
                    .filter(c->c.getRating() >= Float.parseFloat(minRating.getText()))
                    .filter(c->c.getPrice() <= Integer.parseInt(price.getText()))
                    .collect(Collectors.toList());
            list.forEach(a->productsView.getItems().add(a + " Price: " + a.getPrice() + " Rating: " + a.getRating() + " Calories: " + a.getCalories() +" Protein: " + a.getProtein() + " Fat: " + a.getFat() + " Sodium: " + a.getSodium()));
        }
        else if(price.getText().equals("")) {

            productsView.getItems().clear();

            assert deliveryService.products != null;
            List<MenuItem> list = deliveryService.products
                    .stream()
                    .filter(c->c.getName().toLowerCase().contains(name.getText().toLowerCase()))
                    .filter(c->c.getRating() >= Float.parseFloat(minRating.getText()))
                    .collect(Collectors.toList());
            list.forEach(a->productsView.getItems().add(a + " Price: " + a.getPrice() + " Rating: " + a.getRating() + " Calories: " + a.getCalories() +" Protein: " + a.getProtein() + " Fat: " + a.getFat() + " Sodium: " + a.getSodium()));
        }
        else if(minRating.getText().equals("")) {

            productsView.getItems().clear();

            assert deliveryService.products != null;
            List<MenuItem> list = deliveryService.products
                    .stream()
                    .filter(c->c.getName().toLowerCase().contains(name.getText().toLowerCase()))
                    .filter(c->c.getPrice() <= Integer.parseInt(price.getText()))
                    .collect(Collectors.toList());
            list.forEach(a->productsView.getItems().add(a + " Price: " + a.getPrice() + " Rating: " + a.getRating() + " Calories: " + a.getCalories() +" Protein: " + a.getProtein() + " Fat: " + a.getFat() + " Sodium: " + a.getSodium()));
        }
        else {

            productsView.getItems().clear();

            assert deliveryService.products != null;
            List<MenuItem> list = deliveryService.products
                    .stream()
                    .filter(c->c.getName().toLowerCase().contains(name.getText().toLowerCase()))
                    .filter(c->c.getRating() >= Float.parseFloat(minRating.getText()))
                    .filter(c->c.getPrice() <= Integer.parseInt(price.getText()))
                    .collect(Collectors.toList());
            list.forEach(a->productsView.getItems().add(a + " Price: " + a.getPrice() + " Rating: " + a.getRating() + " Calories: " + a.getCalories() +" Protein: " + a.getProtein() + " Fat: " + a.getFat() + " Sodium: " + a.getSodium()) );
        }

         */
    }

    public void addToCart() {
        String line = productsView.getSelectionModel().getSelectedItem().toString();
        cartView.getItems().add(line);
        int price = Integer.parseInt(line.substring(line.indexOf("Price: ") + 7, line.indexOf(" Rating:"))) + Integer.parseInt(labelPrice.getText());
        labelPrice.setText(String.valueOf(price));
    }

    public void placeOrder() throws IOException {

        deliveryService.placeOrder(username, labelPrice, cartView);

        /*String order = username + "/";

        //get order id
        String s;
        File file = new File("orders.txt");
        Scanner sc = new Scanner(file);
        s = sc.nextLine();
        int orderID = Integer.parseInt(s) + 1;
        sc.close();

        order += orderID + "/";

        //get items
        for(int i=0; i<cartView.getItems().size(); i++) {
            String line = (String)cartView.getItems().get(i);
            String name = line.substring(0, line.indexOf(" Price:"));
            order += name + ",";
        }

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = date.format(format);

        order = order.substring(0,order.length()-1);
        order += "/" + labelPrice.getText();
        order += "/" + formatDateTime + "\n";

        System.out.println(order);

        sc = new Scanner(file);
        StringBuffer buffer = new StringBuffer();
        while (sc.hasNextLine()) {
            buffer.append(sc.nextLine()+System.lineSeparator());
        }
        String fileContents = buffer.toString();
        int noOfDigits = 0;
        int aux = orderID;
        while(aux!=0)
        {
            noOfDigits ++;
            aux/=10;
        }
        fileContents = fileContents.substring(noOfDigits);
        fileContents = orderID + fileContents + order;

        FileWriter fileWriter = new FileWriter("orders.txt");
        fileWriter.write(fileContents);
        fileWriter.close();*/
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

}

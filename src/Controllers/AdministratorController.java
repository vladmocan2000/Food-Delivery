package Controllers;

import Business.CompositeProduct;
import Business.DeliveryService;
import Business.MenuItem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdministratorController {

    public ListView<String> productsView;
    public TextField name;
    public TextField rating;
    public TextField calories;
    public Button add_updateProduct;
    public TextField protein;
    public TextField fat;
    public TextField sodium;
    public TextField price;
    public Button deleteProduct;
    public ListView<String> compositeProductView;
    public TextField compositeProductName;
    public Button addToComposite;
    public Button addCompositeProducts;
    public TextField compositeProductRating;

    String line;
    String oldLine;

    DeliveryService deliveryService = new DeliveryService();

    public void listProducts() {
        productsView.getItems().clear();
        deliveryService.products = deliveryService.importProducts("products.csv");
        deliveryService.products.forEach(a -> productsView.getItems().add(a + " Price: " + a.getPrice() + " Rating: " + a.getRating() + " Calories: " + a.getCalories() + " Protein: " + a.getProtein() + " Fat: " + a.getFat() + " Sodium: " + a.getSodium()));
    }

    public void addToComposite() {
        compositeProductView.getItems().add(line);
    }

    public void addCompositeProduct() throws IOException {

        deliveryService.addCompositeProduct(compositeProductName, compositeProductRating, compositeProductView, productsView);
        /*
        CompositeProduct comp = new CompositeProduct(compositeProductName.getText(), Float.valueOf(compositeProductRating.getText()));

        for(int i=0; i<compositeProductView.getItems().size(); i++) {
            String line = compositeProductView.getItems().get(i);
            String name = line.substring(0, line.indexOf(" Price:"));

            List<MenuItem> list = deliveryService.products
                    .stream()
                    .filter(c->c.getName().equals(name))
                    .collect(Collectors.toList());
            list.forEach(a->a.setName(a.getName().substring(0,a.getName().length()-1)));
            list.forEach(a->comp.addMenuItem(a));
        }

        deliveryService.products.add(comp);
        listProducts();
        compositeProductName.clear();
        compositeProductRating.clear();
        compositeProductView.getItems().clear();

        //Title,Rating,Calories,Protein,Fat,Sodium,Price
        String s = comp.toString() + " ," + comp.getRating() + "," + comp.getCalories()  + "," + comp.getProtein() + "," + comp.getFat() + "," + comp.getSodium() + "," + comp.getPrice() + "\n";
        FileWriter fileWriter = new FileWriter("products.csv", true);
        fileWriter.write(s);
        fileWriter.close();
        */

    }

    public void addOrUpdate() throws IOException {

        deliveryService.addOrUpdate(name,rating,calories,protein,fat, sodium, price, productsView);
        /*
        List<MenuItem> list = deliveryService.products
                .stream()
                .filter(c->c.getName().equals(name.getText()))
                .collect(Collectors.toList());

        String item = name.getText() + "," + rating.getText() + "," + calories.getText() + "," + protein.getText() + "," + fat.getText() + "," + sodium.getText() + "," + price.getText();
        System.out.println(item);
        System.out.println(oldLine);
        if(list.isEmpty()) {
            Files.write(Paths.get("products.csv"),
                    item.getBytes(),
                    StandardOpenOption.APPEND);
        }
        else {
            try (Stream<String> lines = Files.lines(Path.of("products.csv"))) {
                List<String> stringList = lines
                        .map(x-> x.replaceAll(oldLine, item))
                        .collect(Collectors.toList());
                Files.write(Path.of("products.csv"), stringList);
            }
        }
        oldLine = item;
        listProducts();*/
    }


    public void delete() throws IOException {

        deliveryService.delete(name,rating,calories,protein,fat, sodium, price, productsView);
        /*
        File file = new File("products.csv");
        System.out.println(name.getText());
        String sRating;
        if(rating.getText().contains(".0") && rating.getText().length() == 3)
        {
            sRating = rating.getText().substring(0,1);
        }
        else
            sRating = rating.getText();
        String item = name.getText() + "," + sRating + "," + calories.getText() + "," + protein.getText() + "," + fat.getText() + "," + sodium.getText() + "," + price.getText();
        List<String> stringList = Files.lines(file.toPath())
                .filter(x -> !x.equals(item))
                .collect(Collectors.toList());
        System.out.println(item+"\n"+stringList.get(1));
        Files.write(file.toPath(), stringList, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        listProducts();
         */
    }

    public void selectRow() {
        String s = productsView.getSelectionModel().getSelectedItem();
        if(s==null)
            return;
        name.setText(s.substring(0,s.indexOf(" Price: ")));
        price.setText(s.substring(s.indexOf("Price: ")+7,s.indexOf(" Rating:")));
        rating.setText(s.substring(s.indexOf("Rating: ")+8,s.indexOf(" Calories:")));
        calories.setText(s.substring(s.indexOf("Calories: ")+10,s.indexOf(" Protein:")));
        protein.setText(s.substring(s.indexOf("Protein: ")+9,s.indexOf(" Fat:")));
        fat.setText(s.substring(s.indexOf("Fat: ")+5,s.indexOf(" Sodium:")));
        sodium.setText(s.substring(s.indexOf("Sodium: ")+8));

        line = s;
        deliveryService.setLine(line);
        oldLine = name.getText() + "," + rating.getText() + "," + calories.getText() + "," + protein.getText() + "," + fat.getText() + "," + sodium.getText() + "," + price.getText();
        deliveryService.setOldLine(oldLine);
    }

    public void reports() {

        openGUI("../presentation/Reports.fxml",400, 240);
    }

    private void openGUI(String name, int x, int y) {

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
}

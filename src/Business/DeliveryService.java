package Business;

import Controllers.EmployeeController;
import javafx.beans.InvalidationListener;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Observable;

public class DeliveryService extends Observable implements IDeliveryServiceProcessing  {

    public ArrayList<MenuItem> products;
    public List<Order>  orders;

    private String line;
    private String oldLine;

    public String getLine() {
        return line;
    }

    public String getOldLine() {
        return oldLine;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void setOldLine(String oldLine) {
        this.oldLine = oldLine;
    }





    public DeliveryService() {
        this.products = importProducts("products.csv");
    }


    @Override
    public ArrayList<MenuItem> importProducts(String path) {
        if(products!=null)
            products.clear();
        Pattern pattern = Pattern.compile(",");
        try (Stream<String> lines = Files.lines(Path.of("products.csv"))) {
            List<BaseProduct> products = lines.skip(1).map(line -> {
                String[] arr = pattern.split(line);
                return new BaseProduct(
                        arr[0],
                        Float.parseFloat(arr[1]),
                        Integer.parseInt(arr[2]),
                        Integer.parseInt(arr[3]),
                        Integer.parseInt(arr[4]),
                        Integer.parseInt(arr[5]),
                        Integer.parseInt(arr[6]));
            }).filter(filtering(BaseProduct::getName))
                    .collect(Collectors.toList());
            ArrayList<MenuItem> productsList= new ArrayList<MenuItem>();
            for (BaseProduct baseProduct : products) {

                productsList.add(baseProduct);
            }
            return productsList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void listProducts(ListView<String> productsView) {
        productsView.getItems().clear();
        this.products = this.importProducts("products.csv");
        this.products.forEach(a -> productsView.getItems().add(a + " Price: " + a.getPrice() + " Rating: " + a.getRating() + " Calories: " + a.getCalories() + " Protein: " + a.getProtein() + " Fat: " + a.getFat() + " Sodium: " + a.getSodium()));
    }

    @Override
    public void addCompositeProduct(TextField compositeProductName, TextField compositeProductRating, ListView<String> compositeProductView, ListView<String> productsView) throws IOException {

        CompositeProduct comp = new CompositeProduct(compositeProductName.getText(), Float.valueOf(compositeProductRating.getText()));

        for(int i=0; i<compositeProductView.getItems().size(); i++) {
            String line = compositeProductView.getItems().get(i);
            String name = line.substring(0, line.indexOf(" Price:"));

            List<MenuItem> list = this.products
                    .stream()
                    .filter(c->c.getName().equals(name))
                    .collect(Collectors.toList());
            list.forEach(a->a.setName(a.getName().substring(0,a.getName().length()-1)));
            list.forEach(a->comp.addMenuItem(a));
        }

        this.products.add(comp);
        listProducts(productsView);
        compositeProductName.clear();
        compositeProductRating.clear();
        compositeProductView.getItems().clear();

        //Title,Rating,Calories,Protein,Fat,Sodium,Price
        String s = comp.toString() + " ," + comp.getRating() + "," + comp.getCalories()  + "," + comp.getProtein() + "," + comp.getFat() + "," + comp.getSodium() + "," + comp.getPrice() + "\n";
        FileWriter fileWriter = new FileWriter("products.csv", true);
        fileWriter.write(s);
        fileWriter.close();
    }

    @Override
    public void addOrUpdate(TextField name, TextField rating, TextField calories, TextField protein, TextField fat, TextField sodium, TextField price, ListView<String> productsView) throws IOException {

        List<MenuItem> list = this.products
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
        listProducts(productsView);
    }

    @Override
    public void delete(TextField name, TextField rating, TextField calories, TextField protein, TextField fat, TextField sodium, TextField price, ListView<String> productsView) throws IOException {
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
        listProducts(productsView);
    }

    @Override
    public void listProductsClient(TextField name, TextField price, TextField minRating, ListView<String> productsView) {

        if(name.getText().equals("") && price.getText().equals("") && minRating.getText().equals("")) {
            this.products.forEach(a -> productsView.getItems().add(a + " Price: " + a.getPrice() + " Rating: " + a.getRating() + " Calories: " + a.getCalories() +" Protein: " + a.getProtein() + " Fat: " + a.getFat() + " Sodium: " + a.getSodium()));
        }
        else if(name.getText().equals("") && price.getText().equals("")) {

            productsView.getItems().clear();

            assert this.products != null;
            List<MenuItem> list = this.products
                    .stream()
                    .filter(c->c.getRating() >= Float.parseFloat(minRating.getText()))
                    .collect(Collectors.toList());
            list.forEach(a->productsView.getItems().add(a + " Price: " + a.getPrice() + " Rating: " + a.getRating() + " Calories: " + a.getCalories() +" Protein: " + a.getProtein() + " Fat: " + a.getFat() + " Sodium: " + a.getSodium()));
        }
        else if(name.getText().equals("") && minRating.getText().equals("")) {

            productsView.getItems().clear();

            assert this.products != null;
            List<MenuItem> list = this.products
                    .stream()
                    .filter(c->c.getPrice() <= Integer.parseInt(price.getText()))
                    .collect(Collectors.toList());
            list.forEach(a->productsView.getItems().add(a + " Price: " + a.getPrice() + " Rating: " + a.getRating() + " Calories: " + a.getCalories() +" Protein: " + a.getProtein() + " Fat: " + a.getFat() + " Sodium: " + a.getSodium()));
        }
        else if(price.getText().equals("") && minRating.getText().equals("")) {

            productsView.getItems().clear();

            assert this.products != null;
            List<MenuItem> list = this.products
                    .stream()
                    .filter(c->c.getName().toLowerCase().contains(name.getText().toLowerCase()))
                    .collect(Collectors.toList());
            list.forEach(a->productsView.getItems().add(a + " Price: " + a.getPrice() + " Rating: " + a.getRating() + " Calories: " + a.getCalories() +" Protein: " + a.getProtein() + " Fat: " + a.getFat() + " Sodium: " + a.getSodium()));
        }
        else if(name.getText().equals("")) {

            productsView.getItems().clear();

            assert this.products != null;
            List<MenuItem> list = this.products
                    .stream()
                    .filter(c->c.getRating() >= Float.parseFloat(minRating.getText()))
                    .filter(c->c.getPrice() <= Integer.parseInt(price.getText()))
                    .collect(Collectors.toList());
            list.forEach(a->productsView.getItems().add(a + " Price: " + a.getPrice() + " Rating: " + a.getRating() + " Calories: " + a.getCalories() +" Protein: " + a.getProtein() + " Fat: " + a.getFat() + " Sodium: " + a.getSodium()));
        }
        else if(price.getText().equals("")) {

            productsView.getItems().clear();

            assert this.products != null;
            List<MenuItem> list = this.products
                    .stream()
                    .filter(c->c.getName().toLowerCase().contains(name.getText().toLowerCase()))
                    .filter(c->c.getRating() >= Float.parseFloat(minRating.getText()))
                    .collect(Collectors.toList());
            list.forEach(a->productsView.getItems().add(a + " Price: " + a.getPrice() + " Rating: " + a.getRating() + " Calories: " + a.getCalories() +" Protein: " + a.getProtein() + " Fat: " + a.getFat() + " Sodium: " + a.getSodium()));
        }
        else if(minRating.getText().equals("")) {

            productsView.getItems().clear();

            assert this.products != null;
            List<MenuItem> list = this.products
                    .stream()
                    .filter(c->c.getName().toLowerCase().contains(name.getText().toLowerCase()))
                    .filter(c->c.getPrice() <= Integer.parseInt(price.getText()))
                    .collect(Collectors.toList());
            list.forEach(a->productsView.getItems().add(a + " Price: " + a.getPrice() + " Rating: " + a.getRating() + " Calories: " + a.getCalories() +" Protein: " + a.getProtein() + " Fat: " + a.getFat() + " Sodium: " + a.getSodium()));
        }
        else {

            productsView.getItems().clear();

            assert this.products != null;
            List<MenuItem> list = this.products
                    .stream()
                    .filter(c->c.getName().toLowerCase().contains(name.getText().toLowerCase()))
                    .filter(c->c.getRating() >= Float.parseFloat(minRating.getText()))
                    .filter(c->c.getPrice() <= Integer.parseInt(price.getText()))
                    .collect(Collectors.toList());
            list.forEach(a->productsView.getItems().add(a + " Price: " + a.getPrice() + " Rating: " + a.getRating() + " Calories: " + a.getCalories() +" Protein: " + a.getProtein() + " Fat: " + a.getFat() + " Sodium: " + a.getSodium()) );
        }
    }

    @Override
    public void placeOrder(String username, Label labelPrice, ListView<String> cartView) throws IOException {
        String order = username + "/";

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
        fileWriter.close();


        setChanged();
        notifyObservers(order);
        //notifyAll();
    }

    @Override
    public List<Order> importOrders() {

        if(orders!=null)
            orders.clear();
        Pattern pattern = Pattern.compile("/");
        try (Stream<String> lines = Files.lines(Path.of("orders.txt"))) {
            return lines.filter(c->c.contains("/")).map(line -> {
                String[] arr = pattern.split(line);
                return new Order(
                        Integer.parseInt(arr[1]),
                        arr[0],
                        arr[2],
                        arr[4],
                        Integer.parseInt(arr[3]),
                        Integer.parseInt(arr[4].substring(11,13)));
            }).collect(Collectors.toList());

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void generateByTime(TextField startingTimetf, TextField endingTimetf) {
        System.out.println("Report by hour");

        orders = importOrders();
        String startingTime = startingTimetf.getText();
        String endingTime = endingTimetf.getText();

        if(startingTime == null || endingTime == null)
            return;

        int startTime = Integer.parseInt(startingTimetf.getText());
        int endTime = Integer.parseInt(endingTimetf.getText());
        List<Order> list = orders
                .stream()
                .filter(c -> c.getHour() >= startTime && c.getHour() <= endTime)
                .collect(Collectors.toList());
        orders = list;
        orders.forEach(System.out::println);

    }

    @Override
    public void generateByXTimes(TextField minNoOfOrders) {
        System.out.println("Report by X Times");

        orders = importOrders();
        //orders.forEach(a-> System.out.println(a));
        System.out.println("------------------------");
        ArrayList<String> allItems = new ArrayList<>();
        ArrayList<Integer> noOfTimes = new ArrayList<>();
        for (Order x :
                orders) {
            String[] products = x.getProduct().split(",");
            for (String z : products) {

                if(!allItems.contains(z))
                {
                    allItems.add(z);
                    noOfTimes.add(1);
                }
                else
                {
                    noOfTimes.set(allItems.indexOf(z), noOfTimes.get(allItems.indexOf(z)) + 1);
                }
            }

        }
        //allItems.forEach(System.out::println);
        //System.out.println(noOfTimes);
        int i=0;
        for (String x : allItems) {

            if(noOfTimes.get(i) > Integer.parseInt(minNoOfOrders.getText()))
                System.out.println(x);
            i++;
        }

    }

    @Override
    public void generateByDay(TextField date){
        System.out.println("Report by day");

        orders = importOrders();
        List<Order> prod = orders
                .stream()
                .filter(c ->c.getDay().equals(date.getText()))
                .collect(Collectors.toList());

        for (Order x: prod) {
            System.out.println(x);
        }
    }

    @Override
    public void generateByCustomer(TextField minPrice, TextField clientMinOrders){
        System.out.println("Report by customer");
        orders = importOrders();
        List<Order> prod = orders
                .stream()
                .filter(p->p.getPrice() >= Integer.parseInt(minPrice.getText()))
                .collect(Collectors.toList());
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<Integer> noOfOrders = new ArrayList<>();
        for (Order x: prod) {
            if(!usernames.contains(x.getClientUsername()))
            {
                usernames.add(x.getClientUsername());
                noOfOrders.add(1);
            }
            else {
                noOfOrders.set(usernames.indexOf(x.getClientUsername()), noOfOrders.get(usernames.indexOf(x.getClientUsername())) + 1);
            }
        }
        //System.out.println(noOfOrders);
        int i=0;
        for (Integer x : noOfOrders) {
            if(x>Integer.parseInt(clientMinOrders.getText()))
                System.out.println(usernames.get(i));
            i++;
        }
    }

    @Override
    public void listOrders(ListView ordersView) {

        ordersView.getItems().clear();
        orders = this.importOrders();
        orders.forEach(a->ordersView.getItems().add(a));
    }


    public static <T> Predicate<T> filtering(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}

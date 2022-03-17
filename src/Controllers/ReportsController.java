package Controllers;

import Business.DeliveryService;
import Business.Order;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReportsController {

    public Button generate;
    public TextField startingTime;
    public TextField endingTime;
    public TextField minNoOfOrders;
    public TextField minPrice;
    public TextField date;

    DeliveryService deliveryService = new DeliveryService();
    //public List<Order>  orders;
    public TextField clientMinOrders;

    /*public List<Order> importOrders() {

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
    }*/


    public void generateByTime() {

        deliveryService.generateByTime(startingTime, endingTime);

        /*System.out.println("Report by hour");

        orders = importOrders();
        String startingTime = this.startingTime.getText();
        String endingTime = this.endingTime.getText();

        if(startingTime == null || endingTime == null)
            return;

        int startTime = Integer.parseInt(this.startingTime.getText());
        int endTime = Integer.parseInt(this.endingTime.getText());
        List<Order> list = orders
                .stream()
                .filter(c -> c.getHour() >= startTime && c.getHour() <= endTime)
                .collect(Collectors.toList());
        orders = list;
        orders.forEach(System.out::println);*/

    }

    public void generateByXTimes() {

        deliveryService.generateByXTimes(minNoOfOrders);

        /*System.out.println("Report by X Times");

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
        }*/

    }

    public void generateByDay(){

        deliveryService.generateByDay(date);

        /*System.out.println("Report by day");

        orders = importOrders();
        List<Order> prod = orders
                .stream()
                .filter(c ->c.getDay().equals(date.getText()))
                .collect(Collectors.toList());

        for (Order x: prod) {
            System.out.println(x);
        }*/
    }

    public void generateByCustomer(){

       deliveryService.generateByCustomer(minPrice, clientMinOrders);

       /* System.out.println("Report by customer");
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

        */
    }


}

package Business;

import javafx.beans.InvalidationListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Observable;

public class Order extends Observable {

    private int orderId;
    private String clientUsername;
    private String orderDate;
    private int price;
    private int hour;
    private String product;

    public Order(int orderId, String clientUsername, String product, String orderDate, int price, int hour) {

        this.orderId = orderId;
        this.clientUsername = clientUsername;
        this.orderDate = orderDate;
        this.product = product;
        this.price =price;
        this.hour = hour;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getDay(){
        return orderDate.substring(0,2);
    }
    public String toString()
    {
        return ("Order ID: " + orderId + "  Client: " + clientUsername + " Date: " + orderDate);
    }


}

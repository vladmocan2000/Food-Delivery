package Business;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface IDeliveryServiceProcessing {

    public ArrayList<MenuItem> importProducts(String path);
    public void listProducts(ListView<String> productsView);
    public void addCompositeProduct(TextField compositeProductName, TextField compositeProductRating, ListView<String> compositeProductView, ListView<String> productsView) throws IOException;
    public void addOrUpdate(TextField name, TextField rating, TextField calories, TextField protein, TextField fat, TextField sodium, TextField price, ListView<String> productsView) throws IOException;
    public void delete(TextField name, TextField rating, TextField calories, TextField protein, TextField fat, TextField sodium, TextField price, ListView<String> productsView) throws IOException;
    public void listProductsClient(TextField name, TextField price, TextField minRating, ListView<String> productsView);
    public void placeOrder(String username, Label labelPrice, ListView<String> cartView) throws IOException;
    public List<Order> importOrders();
    public void generateByTime(TextField startingTimetf, TextField endingTimetf);
    public void generateByXTimes(TextField minNoOfOrders);
    public void generateByDay(TextField date);
    public void generateByCustomer(TextField minPrice, TextField clientMinOrders);
    public void listOrders(ListView ordersView);

}

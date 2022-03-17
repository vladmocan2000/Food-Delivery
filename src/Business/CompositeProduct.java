package Business;

import java.util.ArrayList;

public class CompositeProduct extends MenuItem{

    private ArrayList<MenuItem> menuItems;

    public CompositeProduct(String name, Float rating) {

        super(name, rating);
        this.menuItems = new ArrayList<>();
    }

    public void addMenuItem(MenuItem menuItem) {

        this.menuItems.add(menuItem);
    }




    @Override
    public String toString() {

        String s = new String();
        s = super.getName() + "(";
        for (MenuItem menuItem : this.menuItems) {

            s += menuItem.toString() + "; ";
        }
        s = s.substring(0, s.length() - 2);
        s += ")";

        return s;
    }

    @Override
    public Integer getPrice() {

        Integer price = 0;
        for (MenuItem menuItem : this.menuItems) {

            price += menuItem.getPrice();
        }

        return price;
    }

    @Override
    public Integer getCalories() {

        Integer calories = 0;
        for (MenuItem menuItem : this.menuItems) {

            calories += menuItem.getCalories();
        }

        return calories;
    }

    @Override
    public Integer getProtein() {

        Integer protein = 0;
        for (MenuItem menuItem : this.menuItems) {

            protein += menuItem.getProtein();
        }

        return protein;
    }

    @Override
    public Integer getFat() {

        Integer fat = 0;
        for (MenuItem menuItem : this.menuItems) {

            fat += menuItem.getFat();
        }

        return fat;
    }

    @Override
    public Integer getSodium() {

        Integer sodium = 0;
        for (MenuItem menuItem : this.menuItems) {

            sodium += menuItem.getSodium();
        }

        return sodium;
    }

}

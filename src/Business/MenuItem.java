package Business;

public abstract class MenuItem {

    protected String name;
    protected Float rating;

    public MenuItem(String name, Float rating) {

        this.name = name;
        this.rating = rating;
    }

    public String getName() { return name; }
    public Float getRating() { return rating; }

    public void setName(String name) { this.name = name; }
    public void setRating(Float rating) { this.rating = rating; }

    public abstract String toString();

    public abstract Integer getPrice();
    public abstract Integer getCalories();
    public abstract Integer getProtein();
    public abstract Integer getFat();
    public abstract Integer getSodium();

}

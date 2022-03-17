package Business;

public class BaseProduct extends MenuItem{

    private Integer calories;
    private Integer protein;
    private Integer fat;
    private Integer sodium;
    private Integer price;

    public BaseProduct(String name, Float rating, Integer calories, Integer protein, Integer fat, Integer sodium, Integer price) {

        super(name, rating);
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
    }



    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public void setProtein(Integer protein) {
        this.protein = protein;
    }

    public void setFat(Integer fat) {
        this.fat = fat;
    }

    public void setSodium(Integer sodium) {
        this.sodium = sodium;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }



    @Override
    public Integer getCalories() { return calories; }

    @Override
    public Integer getProtein() { return protein; }

    @Override
    public Integer getFat() { return fat; }

    @Override
    public Integer getSodium() { return sodium; }

    @Override
    public Integer getPrice() { return price; }



    @Override
    public String toString() { return super.getName(); }

}

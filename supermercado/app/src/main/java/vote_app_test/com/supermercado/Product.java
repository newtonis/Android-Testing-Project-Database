package vote_app_test.com.supermercado;

/**
 * Created by newtonis on 28/01/18.
 */

public class Product{
    String name;
    String price;
    public Product(String name,String price){
        this.name = name;
        this.price = price;
    }
    String GetName(){ return this.name; }
    String GetPrice(){ return this.price; }
}
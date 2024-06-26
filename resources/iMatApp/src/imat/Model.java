package imat;


import javafx.scene.image.Image;
import se.chalmers.cse.dat216.project.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model {

    private static Model instance = null;
    private IMatDataHandler iMatDataHandler;
    private MainViewController mainViewController;



    private void init() {
        iMatDataHandler = IMatDataHandler.getInstance();
    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
            instance.init();
        }
        return instance;
    }


    protected static List<Product> findProducts(java.lang.String s){
        return IMatDataHandler.getInstance().findProducts(s);

    }
    public Product getProduct(int idNbr) {
        return iMatDataHandler.getProduct(idNbr);
    }

    public List<Product> getProducts() {
        return iMatDataHandler.getProducts();
    }

    public Image getImage(Product p, double width, double height){
        return iMatDataHandler.getFXImage(p, width, height);
    }

    public ShoppingCart getShoppingCart() {
        return iMatDataHandler.getShoppingCart();
    }

    public void clearShoppingCart() {

        iMatDataHandler.getShoppingCart().clear();

    }





    public void addToShoppingCart(Product p) {
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
        ShoppingItem item = new ShoppingItem(p);
        Model.getInstance().getShoppingCart().addItem(item);
    }

    public void addToShoppingCartAgain(Product p) {
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
        ShoppingItem item = new ShoppingItem(p);
        Model.getInstance().getShoppingCart().addItem(item,true);
    }



    public void removeFromShoppingCartAgain(double index) {

        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
        ShoppingItem item = shoppingCart.getItems().get((int)index);
        if(item.getAmount() == 1){
            System.out.println("shopping objektet tas bort helt");
            shoppingCart.removeItem((int)index);
        }
        else{
            System.out.println("amountet ändras");
            item.setAmount(item.getAmount() -1);
            System.out.println("" + item.getAmount());
        }
        shoppingCart.fireShoppingCartChanged(item,false);

        //DENNA FUNKAR EJ

        //det går inte att ändra amount på
        /*
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
        ShoppingItem item = null;
        for (int i=0; i<shoppingCart.getItems().size(); i++ ){
            if (shoppingCart.getItems().get(i).equals(p)){
                item = shoppingCart.getItems().get(i);
            }
            //else {
            //    item  = null;
            //}
        }
        System.out.println("" + item.getAmount());
        if (item.getAmount() == 1.0){
            shoppingCart.removeItem(item);
        }
        else {
            System.out.println("jag hamnade här");
            item.setAmount(item.getAmount() -1 );
        }*/
        /*
        ShoppingItem item= new ShoppingItem(p);
        double amount = item.getAmount();
        item.setAmount(amount - 1);*/
        //System.out.println("" + iMatDataHandler.getShoppingCart().getItems());
        //System.out.println("" + item.getAmount());
    }






    public ProductDetail getDetail(Product p) {
        return iMatDataHandler.getDetail(p);
    }

    public List<ProductCategory> getCategories(){
        List<ProductCategory> categoriesList = new ArrayList<>();
        categoriesList.addAll(Arrays.asList(ProductCategory.values()));
        //System.out.println("" + categoriesList.get(0).getDeclaringClass().getSimpleName()); //denna print visar att objekten i listan faktiskt är av typen ProductCategory
        return categoriesList;
    }

}

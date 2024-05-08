package imat;


import javafx.scene.image.Image;
import se.chalmers.cse.dat216.project.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model {

    private static Model instance = null;
    private IMatDataHandler iMatDataHandler;

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

        shoppingCart.addProduct(p);
    }

    public void addToShoppingCartAgain(Product p) {
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();

        ShoppingItem item = new ShoppingItem(p);
        Model.getInstance().getShoppingCart().addItem(item, false);
        item.setAmount(item.getAmount()+1);
        System.out.println(item.getAmount());

        //shoppingCart.addProduct(p);
    }

   public List<ProductCategory> getCategories(){
        List<ProductCategory> categoriesList = new ArrayList<>();
        categoriesList.addAll(Arrays.asList(ProductCategory.values()));
        return categoriesList;
   }



    public void removeFromShoppingCartAgain(int index) {
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
        shoppingCart.removeItem(index);
    }

    public ProductDetail getDetail(Product p) {
        return iMatDataHandler.getDetail(p);
    }
}

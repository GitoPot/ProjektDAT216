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


    private final ArrayList<String> months = new ArrayList<String>(Arrays.asList("1", "2","3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
    private final ArrayList<String> years = new ArrayList<String>(Arrays.asList("24", "25", "26", "27", "28", "29", "30"));



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

    public List<Order> getOrders(){
        return iMatDataHandler.getOrders();
    }
    public List<Product> getProducts(ProductCategory category) {
        return iMatDataHandler.getProducts(category);
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
    public Customer getCustomer() {
        return iMatDataHandler.getCustomer();
    }
    public List<String> getMonths() {
        return months;
    }

    public List<String> getYears() {
        return years;
    }

    public CreditCard getCreditCard() {
        return iMatDataHandler.getCreditCard();
    }

    public void placeOrder() {
        iMatDataHandler.placeOrder();
    }

    public void addToFavorite(Product p){
        iMatDataHandler.addFavorite(p);
    }

    public List<Product> getFavorites(){
        return iMatDataHandler.favorites();
    }

    public void shutDown(){
        iMatDataHandler.shutDown();
    }


    public void removeFromFavorite(Product p){
        iMatDataHandler.removeFavorite(p);
    }

    public void removeFromShoppingCart(Product p){
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
        ShoppingItem item = new ShoppingItem(p);
        // Model.getInstance().getShoppingCart().removeItem(item);
        shoppingCart.removeItem(item);
    }


    public void addToShoppingCart(Product p) {
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
        ShoppingItem item = new ShoppingItem(p);
        //Model.getInstance().getShoppingCart().addItem(item);
        shoppingCart.addItem(item);
    }

    public void addToShoppingCartAgain(Product p) {
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
        ShoppingItem item = new ShoppingItem(p);
       // Model.getInstance().getShoppingCart().addItem(item,true);
        shoppingCart.addItem(item,true);
    }


    public void removeFromShoppingCartAgain(Product p) {
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
        List<ShoppingItem> items = shoppingCart.getItems();
        for (ShoppingItem item : items) {
            if (item.getProduct() == p) {
                if (item.getAmount() - 1 > 0) {
                    item.setAmount(item.getAmount() - 1);
                } else {
                    shoppingCart.removeItem(item);
                }
                shoppingCart.fireShoppingCartChanged(item, false);
            }
        }
    }


    public ProductDetail getDetail(Product p) {
        return iMatDataHandler.getDetail(p);
    }

    public List<ProductCategory> getCategories(){
        List<ProductCategory> categoriesList = new ArrayList<>();
        categoriesList.addAll(Arrays.asList(ProductCategory.values()));
        return categoriesList;
    }

}


package imat;

//import java.awt.event.ActionEvent;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.*;

import javax.swing.*;

public class MainViewController implements Initializable, ShoppingCartListener {

    Boolean UserLoggedInChecker;

   // @FXML
    //Label pathLabel; ( VAD ÄR DETTA?)


    @FXML
    AnchorPane startingPane;
    @FXML
    ImageView escapeHatchLogo;
    @FXML
    AnchorPane smallShoppingCart;

    @FXML
    Circle amountCircle;
    @FXML
    Text shoppingCartAmount;
    @FXML
    Button shoppingCartButton;
    @FXML
    TextField searchTextField;
    @FXML
    FlowPane productsFlowPane;
    @FXML
    AnchorPane accountPane;
    @FXML
    AnchorPane dynamicPane;

    @FXML
    AnchorPane loginPane;

    @FXML
    Label   categoryLabel;

    @FXML
    FlowPane cartFlowPane;
    @FXML
    Label itemAmountLabel;

    @FXML
    Label totalCostLabel;

    @FXML
    TextField emailTextField;
    @FXML
    TextField lastNameTextField;

    @FXML
    TextField firstNameTextField;

    @FXML
    TextField adressTextField;
    @FXML
    TextField postcodeTextField;
    @FXML
    TextField phoneNumberTextField;

    @FXML
    TextField orderEmailTextField;
    @FXML
    TextField orderFirstNameTextField;
    @FXML
    TextField orderLastNameTextField;
    @FXML
    TextField orderAdressTextField;
    @FXML
    TextField orderPostcodeTextField;
    @FXML
    TextField orderPhoneNumberTextField;
    @FXML
    TextField orderCardnumberTextField;
    @FXML
    TextField orderCardCvcTextField; ////
    @FXML
    TextField orderCardholderNameTextField;/////
    @FXML
    ComboBox orderCardMonthComboBox;///
    @FXML
    ComboBox orderCardYearComboBox;////

    @FXML
    FlowPane categoryFlowPane;

    @FXML
    Button favoriteCategoryButton;


    @FXML
    Button profileButton;

    @FXML
    AnchorPane profilePane;
    @FXML
    Button showOrderHistory;

    @FXML
    Button showProfile;
    @FXML
    AnchorPane orderHistoryPane;
    @FXML
    Label profileLabel;
    @FXML
    Button startShoppingButton;

    @FXML
    TextArea order1;
    @FXML
    TextArea order2;
    @FXML
    TextArea order3;
    @FXML
    Label order1Date;
    @FXML
    Label order2Date;
    @FXML
    Label order3Date;
    @FXML
    Button lastOrderButton;
    @FXML
    Button secondOrderButton;
    @FXML
    Button thirdOrderButton;
    @FXML
    Label phoneErrorLabel;

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private final Model model = Model.getInstance();

    private ShoppingCart shoppingCart;

    private Customer customer;

    private CreditCard creditCard;

    public ArrayList<String> categories = new ArrayList<String>(Arrays.asList("Baljväxter","Bröd", "Bär","Citrusfrukter","Varma drycker","Kalla drycker","Exotiska frukter","Fisk","Grönsaksfrukter","Sallad","Kött","Mejeri","Meloner","Basvaror","Nötter och frön","Pasta","Potatis och ris","Rotfrukter","Stenfrukter","Sötsaker","Örter"));


    public void initialize(URL url, ResourceBundle rb) {

        model.getShoppingCart().addShoppingCartListener(this);

        //String iMatDirectory = iMatDataHandler.imatDirectory();

        //pathLabel.setText(iMatDirectory);

        updateProductList(model.getProducts());
        categoryList();
        this.customer = model.getCustomer();
        this.shoppingCart = model.getShoppingCart();

        if(customer != null){
            emailTextField.setText(customer.getEmail());
            firstNameTextField.setText(customer.getFirstName());
            lastNameTextField.setText(customer.getLastName());
            adressTextField.setText(customer.getAddress());
            postcodeTextField.setText(customer.getPostCode());
            phoneNumberTextField.setText(customer.getPhoneNumber());

        }

        // Load the NamePanel and add it to dynamicPane
        // This shows how one can develop a view in a separate
        // FXML-file and then load it into on of the panes in the main interface
        // There is an fxml file NamePanel.fxml and a corresponding class NamePanel.java
        // Simply create a new NamePanel object and add it as a child of dynamicPane
        // The NamePanel holds a reference to the main controller (this class)
        AnchorPane cartPane = new iMatCart(this);
        dynamicPane.getChildren().add(cartPane);

    }


    public void updateProductList(List<Product> products) {
        /*
        Denna funktion är till för att uppdatera vilka produkter som ligger i productsFlowPane.
        */

        //System.out.println("updateProductList " + products.size());
        productsFlowPane.getChildren().clear();

        for (Product product : products) {
            productsFlowPane.getChildren().add(new IMatProduct(product));
        }
    }


    @FXML
    private void startShoppingButton(ActionEvent event){
        updateAccount();
        if(phoneNumberTextField.getText().length() == 10 || phoneNumberTextField.getText().length() == 0 ){
            loginPane.toBack();
        }else{
            phoneErrorLabel.setText("Felaktig inmatning");
        }
    }

    @FXML
    private void backButton(ActionEvent event){
        profilePane.toBack();
        orderHistoryPane.toBack();
    }


//----------------------- search funktioner ------------------------------------------

    @FXML
    private void handleSearchAction(ActionEvent event) {
        /*  Denna funktion är till för när du söker på en produkt. Den kommer hitta matchande produkt(er)
        och uppdatera vilka produkter som visas i productsFlowPane.
        */
        List<Product> matches = Model.findProducts(searchTextField.getText());
        updateProductList(matches);
        System.out.println("# matching products: " + matches.size());
    }
    @FXML
    private void handleSearchActionIcon(MouseEvent event) {
        /*  Denna funktion är till för när du söker på en produkt. Den kommer hitta matchande produkt(er)
        och uppdatera vilka produkter som visas i productsFlowPane.
        */
        List<Product> matches = Model.findProducts(searchTextField.getText());
        updateProductList(matches);
        System.out.println("# matching products: " + matches.size());
    }


//---------------------------------- Översta rektangeln andra funktioner ----------------------------------

    @FXML
    public void startProductList(){
        updateProductList(model.getProducts());
    }
    @FXML
    public void escapeHatchButton(MouseEvent event){
        updateProductList(model.getProducts());
        dynamicPane.toBack();
        orderHistoryPane.toBack();
        loginPane.toBack();
        profilePane.toBack();
        startingPane.toFront();

    }

    @FXML
    public void profileButton(ActionEvent event){
        profilePane.toFront();
    }

    @FXML
    private void showProfileInformation(ActionEvent event){
        loginPane.toFront();
        profileLabel.setText("Användarinformation");
        startShoppingButton.setText("Tillbaka");
    }
//--------------------------------------OrderVy----------------------------------------------------------------------
    @FXML
    private void showOrderHistory(ActionEvent event){
        orderHistoryPane.toFront();
        orderHistory();
    }

    List<ShoppingItem> LastOrders = new ArrayList<>();
    List<ShoppingItem> SecondOrders = new ArrayList<>();
    List<ShoppingItem> ThirdOrders = new ArrayList<>();

    private void orderHistory(){
        LastOrders.clear();
        SecondOrders.clear();
        ThirdOrders.clear();

        List<Integer> ordernumbers = new ArrayList<>();
        for(Order item : model.getOrders()){
            ordernumbers.add(item.getOrderNumber());
        }
        int size = ordernumbers.size();
        for (int i = size - 1; i >= Math.max(0, size - 3); i--) {  //Går igenom de tre senaste elementen i listan bakifrån
            String name = "";
            String date = "";

            for (Order item : model.getOrders()) {
                if (item.getOrderNumber() == ordernumbers.get(i)){
                    date =  date + item.getDate();
                    for (ShoppingItem item1 : item.getItems()) {
                        name += item1.getProduct().getName() + "   " + (int)item1.getAmount() + "st  " + item1.getTotal() + "\n";
                        if(i == size - 1){
                            LastOrders.add(item1);
                        } else if (i == size - 2) {
                            SecondOrders.add(item1);
                        } else if (i == size - 3) {
                            ThirdOrders.add(item1);
                        }
                    }

                    if (i == size - 1) {  //Senaste ordern till första textarean
                        order1.setText(name);
                        order1Date.setText(date);
                    } else if (i == size - 2) { //Näst senaste ordern till första textarean
                        order2.setText(name);
                        order2Date.setText(date);
                    } else if (i == size - 3) { //Tredje senaste ordern till första textarean
                        order3.setText(name);
                        order3Date.setText(date);
                    }

                    break;
                }
            }
        }


    }

    @FXML
    private void buyOrderAgain(MouseEvent event){
        if(lastOrderButton.isPressed()){
            for(ShoppingItem item : LastOrders){
                shoppingCart.addItem(item);
            }
        } else if (secondOrderButton.isPressed()) {
            for(ShoppingItem item : SecondOrders){
                shoppingCart.addItem(item);
            }
        } else if (thirdOrderButton.isPressed()){
            for(ShoppingItem item : ThirdOrders)
                shoppingCart.addItem(item);
        }

        orderHistoryPane.toBack();
        loginPane.toBack();
        dynamicPane.toFront();

    }






//----------------------------------shopping cart funktioner -------------------------------------------------

    @FXML
    private void handleClearCartAction(ActionEvent event) {
        model.clearShoppingCart();
    }
    public void shoppingCartChanged(CartEvent evt) {
        updateSmallShoppingCartPanel();
    }

    public void updateSmallShoppingCartPanel() {
        System.out.println("in updatesmallshoppingcartpanel");
        ShoppingCart shoppingCart = model.getShoppingCart();
        int cartAmountTemp = 0;
        for (int i=0;i<shoppingCart.getItems().size(); i++){
            cartAmountTemp += (int) shoppingCart.getItems().get(i).getAmount();
        }
        if(cartAmountTemp < 1){
            amountCircle.setOpacity(0);
            shoppingCartAmount.setText("");
        }else {
            amountCircle.setOpacity(1);
            shoppingCartAmount.setText("" + cartAmountTemp);
        }
    }

    @FXML
    public void goToCartButton(ActionEvent event){
        dynamicPane.toFront();
    }

    @FXML
    public void closeCartButton() {
        dynamicPane.toBack();
        startingPane.toFront();
    }

    //---------------------------Logga in ---------------------------------------------------------------------

    private void updateAccount(){
        customer.setEmail(emailTextField.getText());
        customer.setFirstName(firstNameTextField.getText());
        customer.setLastName(lastNameTextField.getText());
        customer.setAddress(adressTextField.getText());
        customer.setPostCode(postcodeTextField.getText());
        customer.setPhoneNumber(phoneNumberTextField.getText());
    }


//-------------------------------Kategorier------------------------------------------------------

    private void categoryList(){
        categoryFlowPane.getChildren().clear();
        for(ProductCategory category : ProductCategory.values()){
            categoryFlowPane.getChildren().add(new iMatCategories(category,this));
        }
    }

    @FXML
    private void categoryFavoriteButton(ActionEvent event){
        updateProductList(model.getFavorites());

    }
    @FXML
    private void categoryFavoriteButtonMouse(MouseEvent event){
        updateProductList(model.getFavorites());
    }
    @FXML
    private void categoryUnderline(MouseEvent event){
        categoryLabel.setUnderline(true);
    }

    @FXML
    private void categoryUnderlineExit(MouseEvent event){
        categoryLabel.setUnderline(false);
    }



}
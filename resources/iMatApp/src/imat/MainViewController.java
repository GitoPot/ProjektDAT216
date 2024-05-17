
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
    AnchorPane detailPane;

    @FXML
    Label detailLabel;

    @FXML
    Label originLabel;

    @FXML
    Label brandLabel;

    @FXML
    Label contentsLabel;

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








    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private final Model model = Model.getInstance();

    private ShoppingCart shoppingCart;

    private Customer customer;

    private CreditCard creditCard;

    private IMatProduct iMatProduct;




    public void initialize(URL url, ResourceBundle rb) {

        model.getShoppingCart().addShoppingCartListener(this);

        //String iMatDirectory = iMatDataHandler.imatDirectory();

        //pathLabel.setText(iMatDirectory);

        updateProductList(model.getProducts());
        categoryList();
        this.customer = model.getCustomer();
        this.iMatProduct = iMatProduct;



        // Load the NamePanel and add it to dynamicPane
        // This shows how one can develop a view in a separate
        // FXML-file and then load it into on of the panes in the main interface
        // There is an fxml file NamePanel.fxml and a corresponding class NamePanel.java
        // Simply create a new NamePanel object and add it as a child of dynamicPane
        // The NamePanel holds a reference to the main controller (this class)
        AnchorPane cartPane = new iMatCart(this,iMatProduct);
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
        loginPane.toBack();
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


//---------------------------------- Översta rektangeln andra funktioner ----------------------------------

    @FXML
    public void escapeHatchButton(MouseEvent event){
        updateProductList(model.getProducts());
        dynamicPane.toBack();
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
        startShoppingButton.setText("Spara");
    }
//--------------------------------------OrderVy----------------------------------------------------------------------
    @FXML
    private void showOrderHistory(ActionEvent event){
        orderHistoryPane.toFront();
        orderHistory();
    }

    /*
    private void orderHistory(){
        ArrayList<Integer> ordernumbers = new ArrayList<>();
        for(Order item : model.getOrders()){
            ordernumbers.add(item.getOrderNumber());
            System.out.println(ordernumbers);
            String name = "";
            if(ordernumbers.getLast() == item.getOrderNumber()){
                for(ShoppingItem item1 : item.getItems()){
                    name = name + item1.getProduct().getName() + "   " + item1.getTotal() + "\n";
                }
                order1.setText(name);
            }
            if(ordernumbers.size() > 1){
                if(ordernumbers.get(ordernumbers.size() - 2) == item.getOrderNumber()){
                    for(ShoppingItem item1 : item.getItems()){
                        name = name + item1.getProduct().getName() + "   " + item1.getTotal() + "\n";
                    }
                    order2.setText(name);
                }
            }
            if(ordernumbers.size() > 2){
                if(ordernumbers.get(ordernumbers.size() - 3) == item.getOrderNumber()){
                    for(ShoppingItem item1 : item.getItems()){
                        name = name + item1.getProduct().getName() + "   " + item1.getTotal() + "\n";
                    }
                    order3.setText(name);
                }
            }
        }
    }
     */
    private void orderHistory(){
        List<Integer> ordernumbers = new ArrayList<>();
        for(Order item : model.getOrders()){
            ordernumbers.add(item.getOrderNumber());
        }
        int size = ordernumbers.size();
        for (int i = size - 1; i >= Math.max(0, size - 3); i--) {  //Går igenom de tre senaste elementen i listan bakifrån
            int orderNumber = ordernumbers.get(i);
            String name = "";
            String date = "";

            for (Order item : model.getOrders()) {
                if (item.getOrderNumber() == orderNumber) {
                    date =  date + item.getDate();
                    for (ShoppingItem item1 : item.getItems()) {
                        name += item1.getProduct().getName() + "   " + item1.getTotal() + "\n";
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

//----------------------------------Detaljvy av produkt ----------------------------------------------------------------

    @FXML
    private void showDetailView(ActionEvent event){
       // detailpane.tofront();
        ProductDetail detail = iMatProduct.getDetail();
        detailLabel.setText(detail.getDescription());
        originLabel.setText(detail.getOrigin());
        brandLabel.setText(detail.getBrand());
        contentsLabel.setText(detail.getContents());
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
        shoppingCartAmount.setText("" + cartAmountTemp);

        //shoppingCartAmount.setText("" + shoppingCart.getItems().size());

       // itemAmountLabel.setText("Antal varor: " + shoppingCart.getItems().size());
       // totalCostLabel.setText("Kostnad: " + String.format("%.2f",shoppingCart.getTotal()));

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
        for (ProductCategory category : ProductCategory.values()){
            categoryFlowPane.getChildren().add(new iMatCategories(category, this));
        }
    }

    @FXML
    private void categoryFavoriteButton(ActionEvent event){
        updateProductList(model.getFavorites());
        // iMatProduct.setFavoriteLogo();
    }


}
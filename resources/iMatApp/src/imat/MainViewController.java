
package imat;

//import java.awt.event.ActionEvent;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.*;

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
    TextField adressLabel;
    @FXML
    TextField postcodeLabel;
    @FXML
    TextField phoneNumber;

    @FXML
    FlowPane categoryFlowPane;



    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private final Model model = Model.getInstance();

    private ShoppingCart shoppingCart;

    private Customer customer;






    public void initialize(URL url, ResourceBundle rb) {

        model.getShoppingCart().addShoppingCartListener(this);

        //String iMatDirectory = iMatDataHandler.imatDirectory();

        //pathLabel.setText(iMatDirectory);

        updateProductList(model.getProducts());
        categoryList(model.getCategories());


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
       // updateAccount();
        loginPane.toBack();
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


//------------------------------- översta rektangel account funktioner ---------------------------------------


    @FXML
    private void handleShowAccountAction(ActionEvent event) {
        /*
        Denna funktion gör att account knappen gör det den ska göra.
        */
        openAccountView();
    }


    public void openAccountView() {
        /*
        denna funktion gör så att användar delen visas visas
        */
        accountPane.toFront();
    }


    /*
    private boolean LoggedInChecker (){
        /*
        denna funktion kollar vilken account view som ska visas, logga in sidan
        eller användarens informtion sidan
         *//*
        if (UserLoggedInChecker == null){
            NotLoggedIn();
        }
        else if (UserLoggedInChecker == false){
            NotLoggedIn();
        }
        else if (UserLoggedInChecker == true){
            LoggedIn();
        }
    }
*/
//---------------------------------- Översta rektangeln andra funktioner ----------------------------------

    @FXML
    private void escapeHatchButton(ActionEvent event){
        startingPane.toFront();
    }



//----------------------------------shopping cart funktioner -------------------------------------------------


    @FXML
    private void handleClearCartAction(ActionEvent event) {
        model.clearShoppingCart();
    }
    public void shoppingCartChanged(CartEvent evt) {
        updateSmallShoppingCartPanel();
    }

    private void updateSmallShoppingCartPanel() {
        ShoppingCart shoppingCart = model.getShoppingCart();
        shoppingCartAmount.setText("" + shoppingCart.getItems().size());

       // itemAmountLabel.setText("Antal varor: " + shoppingCart.getItems().size());
       // totalCostLabel.setText("Kostnad: " + String.format("%.2f",shoppingCart.getTotal()));

    }

    @FXML
    private void showSmallShoppingCartButton(ActionEvent event){
        smallShoppingCart.toFront();
    }

    @FXML
    private void goToCartButton(ActionEvent event){
        dynamicPane.toFront();
    }

    //---------------------------Logga in ---------------------------------------------------------------------

    private void updateAccount(){
        if (customer != null) {
            customer.setEmail(emailTextField.getText());
            customer.setFirstName(firstNameTextField.getText());
            customer.setLastName(lastNameTextField.getText());
            customer.setAddress(adressLabel.getText());
            customer.setPostCode(postcodeLabel.getText());
            customer.setPhoneNumber(phoneNumber.getText());
        }
    }

//-------------------------------Kategorier------------------------------------------------------

    private void categoryList(List<ProductCategory> categories){
        categoryFlowPane.getChildren().clear();
        for (ProductCategory category : categories){
            categoryFlowPane.getChildren().add(new iMatCategories(category));

        }
    }

    // TODO
    // fixa kategorier

}
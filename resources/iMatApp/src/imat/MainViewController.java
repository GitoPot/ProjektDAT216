
package imat;

//import java.awt.event.ActionEvent;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.*;

public class MainViewController implements Initializable, ShoppingCartListener {

    Boolean UserLoggedInChecker;

    @FXML
    Label pathLabel;
    @FXML
    TextField searchTextField;
    @FXML
    FlowPane productsFlowPane;
    @FXML
    AnchorPane accountPane;

    @FXML
    Label itemAmountLabel;

    @FXML
    Label totalCostLabel;


    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private final Model model = Model.getInstance();

    private ShoppingCart shoppingCart;




    public void initialize(URL url, ResourceBundle rb) {

        model.getShoppingCart().addShoppingCartListener(this);

        String iMatDirectory = iMatDataHandler.imatDirectory();

        pathLabel.setText(iMatDirectory);

        updateProductList(model.getProducts());

    }



    private void updateProductList(List<Product> products) {
        /*
        Denna funktion är till för att uppdatera vilka produkter som ligger i productsFlowPane.
        */

        //System.out.println("updateProductList " + products.size());
        productsFlowPane.getChildren().clear();

        for (Product product : products) {

            productsFlowPane.getChildren().add(new IMatProduct(product));
        }
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
        updateAccountPanel();
        accountPane.toFront();
    }


    private void updateAccountPanel() {
        /*
        denna funktion är till för att uppdatera användarens information, vi kommer inte vilja
        använda alla dessa dela, vissa delar på andra ställen osv
        */

        /*
        CreditCard card = model.getCreditCard();

        numberTextField.setText(card.getCardNumber());
        nameTextField.setText(card.getHoldersName());

        cardTypeCombo.getSelectionModel().select(card.getCardType());
        monthCombo.getSelectionModel().select(""+card.getValidMonth());
        yearCombo.getSelectionModel().select(""+card.getValidYear());

        cvcField.setText(""+card.getVerificationCode());

        purchasesLabel.setText(model.getNumberOfOrders()+ " tidigare inköp hos iMat");
        */
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

        itemAmountLabel.setText("Antal varor: " + shoppingCart.getItems().size());
        totalCostLabel.setText("Kostnad: " + String.format("%.2f",shoppingCart.getTotal()));

    }









}
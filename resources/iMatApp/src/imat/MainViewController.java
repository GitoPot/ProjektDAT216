
package imat;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.User;

public class MainViewController implements Initializable {

    Boolean UserLoggedInChecker;

    @FXML
    Label pathLabel;
    @FXML
    TextField searchTextField;
    @FXML
    FlowPane productsFlowPane;
    @FXML
    AnchorPane accountPane;

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public void initialize(URL url, ResourceBundle rb) {

        String iMatDirectory = iMatDataHandler.imatDirectory();

        pathLabel.setText(iMatDirectory);

    }

//----------------------- search funktioner ------------------------------------------

/*  Denna funktion är till för när du söker på en produkt. Den kommer hitta matchande produkt(er)
    och uppdatera vilka produkter som visas i productsFlowPane.
 */
    @FXML
    private void handleSearchAction(ActionEvent event) {

        List<Product> matches = Model.findProducts(searchTextField.getText());
        updateProductList(matches);
        System.out.println("# matching products: " + matches.size());

    }

/*
Denna funktion är till för att uppdatera vilka produkter som ligger i productsFlowPane.
 */
    private void updateProductList(List<Product> products) {

        System.out.println("updateProductList " + products.size());
        productsFlowPane.getChildren().clear();

        for (Product product : products) {

            productsFlowPane.getChildren().add(new IMatProduct(product));
        }
    }


//------------------------------- översta rektangel knapp funktioner ---------------------------------------


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

    private boolean LoggedInChecker (){
        /*
        denna funktion kollar vilken account view som ska visas, logga in sidan
        eller användarens informtion sidan
         */
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
}
package imat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class iMatCart extends AnchorPane implements ShoppingCartListener {

    private MainViewController mainViewController;

    @FXML
    private FlowPane cartFlowPane;
    @FXML
    private AnchorPane leveransPane;
    @FXML
    private Label totalAmountLabel;
    @FXML
    private Label totalPriceLabel;

    @FXML private TextField cartAmountTextField;


    private final Model model = Model.getInstance();
    private ShoppingCart shoppingCart = model.getShoppingCart();
    public iMatCart(MainViewController mainViewController){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_cart.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.mainViewController = mainViewController;

        shoppingCart.addShoppingCartListener(this);

        updateView();

    }
    @FXML
    private void goToCheckout(ActionEvent event){
        leveransPane.toFront();
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        updateView();
        updateShoppingCartList(shoppingCart.getItems());
    }


    private void updateShoppingCartList(List<ShoppingItem> products){
        cartFlowPane.getChildren().clear();
        for(ShoppingItem item : shoppingCart.getItems()){
            cartFlowPane.getChildren().add(new iMatShoppingCart(item.getProduct()));
           // int amount = (int) item.getAmount();
            // int x = 0;
           // ShoppingItem lastItem = shoppingCart.getItems().get(x);
            //for (int i=1; i<shoppingCart.getItems().size(); i++){
               // if (shoppingCart.getItems().get(i).getProduct().getProductId() == lastItem.getProduct().getProductId())
            // cartAmountTextField.setText("" + (amount += 1));
        }
    }



    private void updateView() {

        totalAmountLabel.setText("Antal varor: " + shoppingCart.getItems().size());
        totalPriceLabel.setText("Kostnad: " + String.format("%.2f",shoppingCart.getTotal()));

        //System.out.println("updateView");


        // String cartText = "";


        //System.out.println("before " + cartText + shoppingCart.getItems().size());


        // for (ShoppingItem item: shoppingCart.getItems()) {

        //   cartFlowPane.getChildren().add(new iMatShoppingCart(item.getProduct()));

        //  cartText = cartText + item.getProduct().getName() + " " + item.getAmount() + "\n";

        //System.out.println("during " + cartText);
    }
        //cartTextArea.setText(cartText);
}





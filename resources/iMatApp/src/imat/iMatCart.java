package imat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

public class iMatCart extends AnchorPane implements ShoppingCartListener {

    private MainViewController mainViewController;

    @FXML
    private FlowPane cartFlowPane;
    @FXML
    private AnchorPane leveransPane;
    @FXML
    private AnchorPane orderDonePane;
    @FXML
    private Label totalAmountLabel;
    @FXML
    private Label totalPriceLabel;

    @FXML
    private ComboBox orderCardMonthComboBox;
    @FXML
    private ComboBox orderCardYearComboBox;
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
    TextArea orderTextArea;

    private IMatProduct product;
    private Customer customer;

    private final Model model = Model.getInstance();
    private ShoppingCart shoppingCart = model.getShoppingCart();
    public iMatCart(MainViewController mainViewController, IMatProduct product){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_cart.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.mainViewController = mainViewController;
        this.product = product;

        shoppingCart.addShoppingCartListener(this);
        this.customer = model.getCustomer();

        setupCreditCard();

        updateView();

    }


    @FXML
    public void goToCheckout(ActionEvent event){
        updateAccount();
        leveransPane.toFront();
        setCreditCardInformation();

    }
    @FXML
    public void pay(ActionEvent event){
        updateCreditCard();
        showOrder();
        model.placeOrder();
        orderDonePane.toFront();
    }

    @FXML
    public void escapeHatch(MouseEvent event){
        mainViewController.escapeHatchButton(event);
    }
    @FXML
    public void clearShoppingCart(ActionEvent event){
        model.clearShoppingCart();
    }
    @FXML
    public void goBackToShoppingCart(ActionEvent event){ leveransPane.toBack(); }

    @FXML
    public void goBackToStart(ActionEvent event){
        mainViewController.closeCartButton();
        product.changePane();

    }

    @FXML
    public void orderDone(ActionEvent event){
        orderDonePane.toBack();
        leveransPane.toBack();
        goBackToStart(event);
        product.changePane();
    }



    public void setupCreditCard(){
        orderCardYearComboBox.getItems().addAll(model.getYears());
        orderCardMonthComboBox.getItems().addAll(model.getMonths());

    }


    private void setCreditCardInformation(){
        CreditCard card = model.getCreditCard();

        orderCardnumberTextField.setText(card.getCardNumber());
        orderCardholderNameTextField.setText(card.getHoldersName());
        orderCardCvcTextField.setText(""+card.getVerificationCode());


        orderCardMonthComboBox.getSelectionModel().select(""+card.getValidMonth());
        orderCardYearComboBox.getSelectionModel().select(""+card.getValidYear());


    }


    private void updateAccount(){
        if (customer != null){
        orderEmailTextField.setText(customer.getEmail());
        orderFirstNameTextField.setText(customer.getFirstName());
        orderLastNameTextField.setText(customer.getLastName());
        orderAdressTextField.setText(customer.getAddress());
        orderPostcodeTextField.setText(customer.getPostCode());
        orderPhoneNumberTextField.setText(customer.getPhoneNumber());

        }
    }
    private void updateCreditCard() {

        CreditCard card = model.getCreditCard();

        card.setCardNumber(orderCardnumberTextField.getText());
        card.setHoldersName(orderCardholderNameTextField.getText());

        String selectedValue = (String) orderCardMonthComboBox.getSelectionModel().getSelectedItem();
        card.setValidMonth(Integer.parseInt(selectedValue));

        selectedValue = (String) orderCardYearComboBox.getSelectionModel().getSelectedItem();
        card.setValidYear(Integer.parseInt(selectedValue));

        card.setVerificationCode(Integer.parseInt(orderCardCvcTextField.getText()));
    }

    private void showOrder(){

        String cartText = "";
        String totaltText = "";
        for(ShoppingItem item: shoppingCart.getItems()){
            cartText = cartText + item.getProduct().getName() + " " + item.getAmount() + "    " + item.getTotal() + " kr" + "\n";
            totaltText = totaltText + shoppingCart.getTotal() + " kr";

        }
        orderTextArea.setText(cartText + totaltText);

    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        updateView();
        updateShoppingCartList(shoppingCart.getItems());
    }


    private void updateShoppingCartList(List<ShoppingItem> products){

        List<Integer> tempList= new ArrayList<Integer>();
        cartFlowPane.getChildren().clear();
        for(ShoppingItem item : shoppingCart.getItems()){
            iMatShoppingCart sc;
            if (!tempList.contains(item.getProduct().getProductId())){
                sc = new iMatShoppingCart(item.getProduct());
                int amount = (int) item.getAmount();
                sc.textFieldCartAmount.setText(""+amount);
                cartFlowPane.getChildren().add(sc);
                tempList.add(item.getProduct().getProductId());
            }
        }
    }



    private void updateView() {

        //_----------------------DETTA ÄR COPY PASTE FRÅN MAIN VIEW CONTROLLER-----------
         int cartAmountTemp = 0;
                 for (int i=0;i<shoppingCart.getItems().size(); i++){
                     cartAmountTemp += shoppingCart.getItems().get(i).getAmount();
                 }
         //------------------------------------------------------------------------------
        
         totalAmountLabel.setText("Antal varor: " + cartAmountTemp);  // VA FÖRUT totalAmountLabel.setText("Antal varor: " + shoppingCart.get...().size())
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





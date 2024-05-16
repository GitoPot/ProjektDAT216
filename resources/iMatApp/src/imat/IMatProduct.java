package imat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleAction;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class IMatProduct extends AnchorPane {
    @FXML
    private AnchorPane productView;
    @FXML
    private ImageView productImage;
    @FXML
    private ImageView favoriteLogo;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label productPriceLabel;
    @FXML
    private Label productPriceLabel1;
    @FXML
    private Label productNameLabel1;
    @FXML
    private Button buyProductButton;
    @FXML
    private Pane infoPane;
    @FXML
    private Pane changeAmountPane;
    @FXML
    private Button plusButton;

    @FXML
    private TextField cardAmountTextField;

    @FXML
    private Button favoriteButton;
    @FXML
    private Button filledFavoriteButton;


    private Model model = Model.getInstance();

    private Product product;

    ShoppingItem shoppingItem = new ShoppingItem(product);

    int amount = (int) shoppingItem.getAmount();

    private double kImageWidth = 100;
    //ändra detta om du vill ha en annan bild brädd
    private double kImageRatio = 0.75;
    //ändra detta om du vill ha en annan bild höjd


    public IMatProduct(Product product) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_product.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = product;
        productNameLabel.setText(product.getName());
        productPriceLabel.setText(String.format("%.2f", product.getPrice()) + " " + product.getUnit());
        productImage.setImage(model.getImage(product, kImageWidth, kImageWidth * kImageRatio));
        productNameLabel1.setText(product.getName());
        productPriceLabel1.setText(String.format("%.2f", product.getPrice()) + " " + product.getUnit());


        /*
        ProductDetail detail = model.getDetail(product);
        if (detail != null) {
            originLabel.setText(detail.getOrigin());
        }
        */

    }

    @FXML
    public void changePane(){
        changeAmountPane.toBack();
    }

    //------------------------------favorit-knappar------------------------------------------------------------

    @FXML
    private void favoriteButton(ActionEvent event){
        model.addToFavorite(product);
        filledFavoriteButton.toFront();
    }

    @FXML
    private void undoFavoriteButton(ActionEvent event){
        model.removeFromFavorite(product);
        favoriteButton.toFront();
        filledFavoriteButton.toBack();

    }





    //------------------- BORDE INTE DESSA VARA I ANTINGEN MAINVEIWCONTROLLER ELLER IMATCART?-------------------

    @FXML
    private void addToShoppingCart(ActionEvent event) {
        model.addToShoppingCart(product);
        changeAmountPane.toFront();
        cardAmountTextField.setText("" + amount);

    }

    @FXML
    private void addToShoppingCartAgain(ActionEvent event) {
        model.addToShoppingCartAgain(product);
        changeAmountPane.toFront();

        cardAmountTextField.setText("" + (amount += 1));
    }




    @FXML
    private void removeFromShoppingCartAgain(ActionEvent event) {
        //TODO
        // denna funkar ej

        model.removeFromShoppingCart(product);
        if(amount == 1){
            changeAmountPane.toBack();
        }
        else{
            cardAmountTextField.setText("" + (amount -= 1));
        }



        /*
       // if (amount != 0 && !model.getProducts().isEmpty()) {
            //System.out.println("produkten har mer än 1 i amount och listan är inte tom");
            double index = 0;
            for (int i = 0; i < model.getProducts().size(); i++) {
                if (model.getProducts().get(i).equals(product)) {
                    index = (double) i;
                    System.out.println("dom är lika!");
                    break;
                } else {
                    System.out.println("dom är inte lika:(");
                    index = -1;
                }
            }

        if (amount == 1) {
            System.out.println("jag är här");
            changeAmountPane.toBack();
            index = index-1;
        } else {
            cardAmountTextField.setText("" + (amount -= 1));
        }
        model.removeFromShoppingCartAgain(index);
        */
        /*
        if(amount != 0) {
            model.removeFromShoppingCartAgain(product);
            changeAmountPane.toFront();
            cardAmountTextField.setText("" + (amount -= 1));
        }
        else{
            changeAmountPane.toBack();*/
            //}

            //---------------------------------------------------------------------------------

        }
    }

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

public class IMatProduct extends AnchorPane{
    @FXML private AnchorPane productView;
    @FXML private ImageView productImage;
    @FXML private ImageView favoriteLogo;
    @FXML private Label productNameLabel;
    @FXML private Label productPriceLabel;
    @FXML private Label productPriceLabel1;
    @FXML private Label productNameLabel1;
    @FXML private Button buyProductButton;
    @FXML private Pane infoPane;
    @FXML private AnchorPane changeAmountPane;
    @FXML private Button plusButton;

    @FXML private TextField cardAmountTextField;


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
        productImage.setImage(model.getImage(product, kImageWidth, kImageWidth*kImageRatio));
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
    private void addToShoppingCart(ActionEvent event){
        model.addToShoppingCart(product);
        changeAmountPane.toFront();
        cardAmountTextField.setText(""+ amount);
    }

    @FXML
    private void addToShoppingCartAgain(ActionEvent event){
        model.addToShoppingCartAgain(product);
        changeAmountPane.toFront();
        cardAmountTextField.setText(""+ (amount += 1));
    }



    @FXML
    private void removeFromShoppingCartAgain(ActionEvent event){
        int indexToRemove= 0;
        if(amount != 0) {
            for (int i=0; i<model.getProducts().size(); i++){
                if ( product == model.getProducts().get(i)){
                    indexToRemove = i;
                    break;
                }
            }
            model.removeFromShoppingCartAgain(indexToRemove);
            changeAmountPane.toFront();
            cardAmountTextField.setText("" + (amount -= 1));
        }
        else{
            changeAmountPane.toBack();
        }
    }
}

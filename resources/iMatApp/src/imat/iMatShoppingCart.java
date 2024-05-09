package imat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductDetail;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class iMatShoppingCart extends AnchorPane {

    @FXML
    private Label productShoppingCartPrice;
    @FXML
    TextField textFieldCartAmount;
    @FXML
    private ImageView productShoppingCartImage;
    @FXML
    private Label shoppingCartNameLabel;
    @FXML
    Label productDetailLabel;

    @FXML
    TextField detailTextField;

    @FXML Button plusButtonShoppingCart;

    @FXML Button minusButtonShoppingCart;

    private IMatProduct iMatProduct;

    private Model model = Model.getInstance();

    private Product product;

    private double kImageWidth = 100;
    //ändra detta om du vill ha en annan bild brädd
    private double kImageRatio = 0.75;
    //ändra detta om du vill ha en annan bild höjd

    public iMatShoppingCart(Product product) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_shoppingcart.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = product;
        shoppingCartNameLabel.setText(product.getName());
        productShoppingCartPrice.setText(String.format("%.2f", product.getPrice()) + " " + product.getUnit());
        productShoppingCartImage.setImage(model.getImage(product, kImageWidth, kImageWidth * kImageRatio));

        ProductDetail productdetail = model.getDetail(product);
        if (productdetail != null) {
            productDetailLabel.setText("" + productdetail.getDescription());
        }

    }

   // -------------------DETTA ÄR OERHÖRT FULT EFTERSOM DE BARA ÄR COPY PASTE FRÅN IMATPRODUCT----------------

    ShoppingItem shoppingItem = new ShoppingItem(product);

    int amount = (int) shoppingItem.getAmount();

    @FXML
    private void addToShoppingCartAgain(ActionEvent event){
        model.addToShoppingCartAgain(product);
        textFieldCartAmount.setText("" + (amount += 1));
    }

    @FXML
    private void removeFromShoppingCartAgain(ActionEvent event){
        if(amount != 0) {
            //TODO
            // denna funkar ej
            model.removeFromShoppingCartAgain(product);
            textFieldCartAmount.setText("" + (amount -= 1));
        }
        else{
            // TODO
            // remove item somehow
        }
    }

    //------------------------------------------------------------------------------------------------------

}

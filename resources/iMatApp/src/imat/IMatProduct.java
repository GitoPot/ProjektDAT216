package imat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleAction;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductDetail;
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
    @FXML
    AnchorPane detailPane;

    @FXML
    Label detailLabel;

    @FXML
    Label originLabel;

    @FXML
    Label brandLabel;

    @FXML
    Label productDetailNameLabel;

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

    }


    @FXML
    public void changePane() {
        changeAmountPane.toBack();
    }


    //------------------------------favorit-knappar------------------------------------------------------------

    @FXML
    public void setFavoriteLogo() {
        filledFavoriteButton.toFront();
    }

    @FXML
    private void favoriteButton(ActionEvent event) {
        model.addToFavorite(product);
        favoriteButton.toBack();
        filledFavoriteButton.toFront();
    }

    @FXML
    private void undoFavoriteButton(ActionEvent event) {
        model.removeFromFavorite(product);
        favoriteButton.toFront();
        filledFavoriteButton.toBack();
    }

    @FXML
    private void goBackToProduct(ActionEvent event){
        detailPane.toBack();
    }

    @FXML
    private void showDetailView(MouseEvent event){
        detailPane.toFront();
        ProductDetail detail = model.getDetail(product);
        productDetailNameLabel.setText(product.getName());
        detailLabel.setText(detail.getDescription());
        originLabel.setText("Från: " + detail.getOrigin());
        brandLabel.setText("Märke: " +  detail.getBrand());
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
        if (amount == 1) {
            model.removeFromShoppingCart(product);
            changeAmountPane.toBack();
        } else {
            cardAmountTextField.setText("" + (amount -= 1));
            model.removeFromShoppingCart(product);
            for (int i = amount - 1; i > -1; i--) {
                model.addToShoppingCart(product);
            }
        }
    }
}

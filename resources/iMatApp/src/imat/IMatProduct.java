package imat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class IMatProduct extends AnchorPane{
    @FXML private AnchorPane productView;
    @FXML private ImageView productImage;
    @FXML private ImageView favoriteLogo;
    @FXML private Label productNameLabel;
    @FXML private Label productPriceLabel;
    @FXML private Button buyProductButton;
    @FXML private Pane infoPane;



    private Model model = Model.getInstance();

    private Product product;


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

        /*
        ProductDetail detail = model.getDetail(product);
        if (detail != null) {
            originLabel.setText(detail.getOrigin());
        }
        */

    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        model.addToShoppingCart(product);
    }


}

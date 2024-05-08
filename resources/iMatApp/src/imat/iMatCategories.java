package imat;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;


import java.io.IOException;
import java.util.List;




public class iMatCategories extends AnchorPane {

    @FXML
    Label categoryLabel;
    private MainViewController mainViewController;
    private Product product;
    public iMatCategories(ProductCategory category) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_categories.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        categoryLabel.setText("" + category);

    }

    @FXML
    private void categorySearch(ActionEvent event){
        List<Product> matches = Model.findProducts(categoryLabel.getText());
        mainViewController.updateProductList(matches);
    }

}

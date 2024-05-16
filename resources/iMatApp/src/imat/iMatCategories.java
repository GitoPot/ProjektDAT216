package imat;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




public class iMatCategories extends AnchorPane {

    @FXML
    Label categoryLabel;

    private Model model = Model.getInstance();

    private MainViewController mainViewController;
    private Product product;
    private ProductCategory category1;

    public iMatCategories(ProductCategory category, MainViewController mainViewController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_categories.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.category1 = category;
        this.mainViewController = mainViewController;
        categoryLabel.setText("" + category);
        System.out.println(categoryLabel.getText());
    }



    @FXML
    private void categorySearch(ActionEvent event){

        List<Product> matches = model.getProducts(ProductCategory.valueOf(categoryLabel.getText()));
        mainViewController.updateProductList(matches);
    }

}

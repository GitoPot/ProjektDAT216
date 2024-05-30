package imat;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
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

    @FXML
    Label categoryNameLabel;
    @FXML
    Button categoryButton;

    private Model model = Model.getInstance();

    private MainViewController mainViewController;
    private Product product;
    private ProductCategory category1;

    private int count = 0;

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
        switch(category){
            case POD:
                categoryNameLabel.setText(mainViewController.categories.getFirst());
                break;
            case BREAD:
                categoryNameLabel.setText(mainViewController.categories.get(1));
                break;
            case BERRY:
                categoryNameLabel.setText(mainViewController.categories.get(2));
                break;
            case CITRUS_FRUIT:
                categoryNameLabel.setText(mainViewController.categories.get(3));
                break;
            case HOT_DRINKS:
                categoryNameLabel.setText(mainViewController.categories.get(4));
                break;
            case COLD_DRINKS:
                categoryNameLabel.setText(mainViewController.categories.get(5));
                break;
            case EXOTIC_FRUIT:
                categoryNameLabel.setText(mainViewController.categories.get(6));
                break;
            case FISH:
                categoryNameLabel.setText(mainViewController.categories.get(7));
                break;
            case VEGETABLE_FRUIT:
                categoryNameLabel.setText(mainViewController.categories.get(8));
                break;
            case CABBAGE:
                categoryNameLabel.setText(mainViewController.categories.get(9));
                break;
            case MEAT:
                categoryNameLabel.setText(mainViewController.categories.get(10));
                break;
            case DAIRIES:
                categoryNameLabel.setText(mainViewController.categories.get(11));
                break;
            case MELONS:
                categoryNameLabel.setText(mainViewController.categories.get(12));
                break;
            case FLOUR_SUGAR_SALT:
                categoryNameLabel.setText(mainViewController.categories.get(13));
                break;
            case NUTS_AND_SEEDS:
                categoryNameLabel.setText(mainViewController.categories.get(14));
                break;
            case PASTA:
                categoryNameLabel.setText(mainViewController.categories.get(15));;
                break;
            case POTATO_RICE:
                categoryNameLabel.setText(mainViewController.categories.get(16));;
                break;
            case ROOT_VEGETABLE:
                categoryNameLabel.setText(mainViewController.categories.get(17));;
                break;
            case FRUIT:
                categoryNameLabel.setText(mainViewController.categories.get(18));;
                break;
            case SWEET:
                categoryNameLabel.setText(mainViewController.categories.get(19));
                break;
            case HERB:
                categoryNameLabel.setText(mainViewController.categories.getLast());
                break;
        }

    }



    @FXML
    private void categorySearch(ActionEvent event){
        count++;
        switch (count){
            case 1:
                List<Product> matches = model.getProducts(ProductCategory.valueOf(categoryLabel.getText()));
                //switch (ProductCategory.valueOf(categoryLabel;)
                mainViewController.updateProductList(matches);
                break;
            case 2:
                mainViewController.startProductList();
                count = 0;
                break;
        }
    }

    @FXML
    private void categoryLabelSearch(MouseEvent event){
        count++;
        switch (count){
            case 1:
                List<Product> matches = model.getProducts(ProductCategory.valueOf(categoryLabel.getText()));
                mainViewController.updateProductList(matches);
                break;
            case 2:
                mainViewController.startProductList();
                count = 0;
                break;
        }
    }
    @FXML
    private void categoryUnderline(MouseEvent event){
        categoryLabel.setUnderline(true);
        categoryNameLabel.setUnderline(true);
    }

    @FXML
    private void categoryUnderlineExit(MouseEvent event){
        categoryLabel.setUnderline(false);
        categoryNameLabel.setUnderline(false);
    }

}

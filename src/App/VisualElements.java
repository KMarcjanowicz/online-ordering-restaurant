package App;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class VisualElements {

    private FileInputStream inputstream;
    private Image image;
    private ImageView imageView;

    private void ImageButton(String s) throws FileNotFoundException {
        inputstream = new FileInputStream(s);
        image  = new Image(inputstream);
        imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
    }

    public ImageView getImageView(String s) throws FileNotFoundException {
        ImageButton(s);
        return imageView;
    }

}

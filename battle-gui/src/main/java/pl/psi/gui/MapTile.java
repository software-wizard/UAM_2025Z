package pl.psi.gui;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class MapTile extends StackPane
{

    private final Rectangle rect;
    private final Label label;
    private ImageView imageView;

    MapTile( final String aName )
    {
        imageView = new ImageView();
        imageView.setFitWidth( 50 );
        imageView.setFitHeight( 50 );
        rect = new Rectangle( 50, 50 );
        rect.setFill( Color.WHITE );
        rect.setStroke( Color.RED );
        getChildren().add( rect );
        label = new Label( aName );
        getChildren().add( label );
        getChildren().add( imageView );
    }

    void setName( final String aName )
    {
        label.setText( aName );
    }

    void setImage ( final Image aImage )
    {
        imageView.setImage( aImage );
    }

    void setBackground( final Color aColor )
    {
        rect.setFill( aColor );
    }

    void setBackgroundImage(final ImagePattern image) {
        rect.setFill( image );
    }
}

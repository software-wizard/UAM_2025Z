package pl.psi.gui.shop.building;

import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Popup;
import javafx.stage.Stage;
import pl.psi.resource.Resources;

import java.beans.PropertyChangeListener;

public interface EconomyBuildingView extends PropertyChangeListener {

    void showAlert(String title, String header, String content);
    void showPopUp(Stage stage, Popup popup);
    ButtonType showUpgradeConfirmationPopup(
            String buildingName,
            Resources upgradeCost,
            Image buildingImage
    );
}

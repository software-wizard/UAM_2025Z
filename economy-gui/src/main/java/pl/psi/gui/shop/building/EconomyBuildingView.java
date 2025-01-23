package pl.psi.gui.shop.building;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.building.town.Town;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;

import java.util.Map;

public interface EconomyBuildingView {

    void updateResources(Label resourcesLabel, EconomyHero hero);
    void showAlert(String title, String header, String content);
    void showPopUp(Stage stage, Popup popup);
    void updateBuildingStatuses(
            Map<EconomyBuildingStatistic.Type, Map<EconomyBuildingStatistic, VBox>> buildingBoxes,
            Town town,
            EconomyHero buyer
    );
    ButtonType showUpgradeConfirmationPopup(
            String buildingName,
            Resources upgradeCost,
            Image buildingImage
    );
}

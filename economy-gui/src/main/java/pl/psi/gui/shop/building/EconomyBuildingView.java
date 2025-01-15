package pl.psi.gui.shop.building;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.building.town.Town;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;

import java.util.Map;

class EconomyBuildingView {

    void updateResources(Label resourcesLabel, EconomyHero hero) {
        resourcesLabel.setText(hero.getResources().toString());
    }

    void showAlert(String title, String content) {
        Alert alert = createAlert(Alert.AlertType.INFORMATION, title, content, null);
        alert.showAndWait();
    }

    void showPopUp(Stage stage, Popup popup) {
        popup.show(stage);
    }

    void updateBuildingStatuses(Map<EconomyBuildingStatistic.Type, Map<EconomyBuildingStatistic, VBox>> buildingBoxes, Town town, EconomyHero buyer) {
        buildingBoxes.forEach((type, buildings) ->
                buildings.forEach((statistic, buildingBox) -> updateBuildingStatus(buildingBox, statistic, town, buyer))
        );
    }

    ButtonType showUpgradeConfirmationPopup(String buildingName, Resources upgradeCost, Image buildingImage) {
        String title = "Ulepszanie budynku";
        String header = "Czy chcesz ulepszyć budynek " + buildingName + "?";
        String content = "Koszt ulepszenia: " + upgradeCost + " zasobów.";
        Alert alert = createAlert(Alert.AlertType.CONFIRMATION, title, content, buildingImage);
        alert.setHeaderText(header);
        return alert.showAndWait().orElse(null);
    }

    private void updateBuildingStatus(VBox buildingBox, EconomyBuildingStatistic statistic, Town town, EconomyHero buyer) {
        if (town.isBuildingAlreadyBuilt(statistic.name())) {
            setBuildingStyle(buildingBox, "#39d639"); // Zielony dla zbudowanych
        } else if (!statistic.hasEnoughResourcesToBuild(buyer)) {
            setBuildingStyle(buildingBox, "#e14848"); // Czerwony dla niedostępnych
        } else {
            setBuildingStyle(buildingBox, "#e8c50f"); // Żółty dla możliwych do budowy
        }
    }

    private void setBuildingStyle(VBox buildingBox, String color) {
        buildingBox.setStyle(String.format("-fx-border-color: %s; -fx-border-width: 5px; -fx-border-radius: 5px;", color));
    }

    private Alert createAlert(Alert.AlertType type, String title, String content, Image image) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        if (image != null) {
            alert.setGraphic(new ImageView(image));
        }
        return alert;
    }
}

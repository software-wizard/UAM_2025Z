package pl.psi.gui.shop.building;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import pl.psi.building.EconomyBuildingFacade;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.building.model.UpgradableBuilding;
import pl.psi.building.town.Town;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;

import java.beans.PropertyChangeEvent;
import java.util.Map;

@RequiredArgsConstructor
class DefaultEconomyBuildingView implements EconomyBuildingView {

    private final EconomyBuildingFacade economyBuildingFacade;
    private final Map<EconomyBuildingStatistic.Type, Map<EconomyBuildingStatistic, VBox>> buildingBoxes;
    private final Town town;
    private final EconomyHero buyer;
    private final Label resourcesLabel;

    @Override
    public void showAlert(String title, String header, String content) {
        Alert alert = createAlert(Alert.AlertType.INFORMATION, title, header, content, null);
        alert.showAndWait();
    }

    @Override
    public void showPopUp(Stage stage, Popup popup) {
        popup.show(stage);
    }

    @Override
    public ButtonType showUpgradeConfirmationPopup(String buildingName, Resources upgradeCost, Image buildingImage) {
        String title = "Ulepszanie budynku";
        String header = "Czy chcesz ulepszyć budynek " + buildingName + "?";
        String content = "Koszt ulepszenia: " + upgradeCost + " zasobów.";
        Alert alert = createAlert(Alert.AlertType.CONFIRMATION, title, "", content, buildingImage);
        alert.setHeaderText(header);
        return alert.showAndWait().orElse(null);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        updateBuildingStatuses();
        updateResources();
    }

    private void updateBuildingStatus(
            VBox buildingBox,
            EconomyBuildingStatistic statistic,
            Town town,
            EconomyHero buyer
    ) {
        if (!town.isBuildingAlreadyBuilt(statistic.name())) {
            if (!statistic.hasEnoughResourcesToBuild(buyer)) {
                setBuildingStyle(buildingBox, "#e14848"); // Czerwony dla niedostępnych
            } else {
                setBuildingStyle(buildingBox, "#e8c50f"); // Żółty dla możliwych do budowy
            }
            return;
        }
        var building = town.findBuildingByName(statistic.name()).orElseThrow();
        setBuildingStyle(buildingBox, "#39d639"); // Zielony dla zbudowanych
        if (!economyBuildingFacade.isBuildingUpgradable(building)) return;
        var upgradableBuilding = (UpgradableBuilding) building;
        if (upgradableBuilding.isUpgraded()) {
            setBuildingStyle(buildingBox, "#0000ff"); // Niebieski dla ulepszonych
        }
    }

    private void setBuildingStyle(VBox buildingBox, String color) {
        buildingBox.setStyle(String.format("-fx-border-color: %s; -fx-border-width: 5px; -fx-border-radius: 5px;", color));
    }

    private Alert createAlert(Alert.AlertType type, String title, String header, String content, Image image) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setGraphic(new ImageView(image));
        return alert;
    }

    private void updateResources() {
        resourcesLabel.setText(buyer.getResources().toString());
    }

    private void updateBuildingStatuses() {
        buildingBoxes.forEach((type, buildings) ->
                buildings.forEach((statistic, buildingBox) -> updateBuildingStatus(buildingBox, statistic, town, buyer))
        );
    }
}

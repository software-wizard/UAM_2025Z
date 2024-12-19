package pl.psi.gui.shop.building;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import pl.psi.building.EconomyBuildingFacade;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.hero.EconomyHero;
import pl.psi.town.Town;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EconomyBuildingShopController {

    @FXML
    private GridPane buildingGrid;

    @FXML
    private Label resourcesLabel;

    @FXML
    private VBox labelVBox;

    private final EconomyBuildingFacade economyBuildingFacade;
    private final EconomyHero buyer;
    private final Town town;
    private final Stage stage;

    public EconomyBuildingShopController(EconomyBuildingFacade economyBuildingFacade, EconomyHero hero, Town town, Stage stage) {
        this.economyBuildingFacade = economyBuildingFacade;
        this.buyer = hero;
        this.town = town;
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        buildingGrid.setPadding(new Insets(10, 10, 10, 10));
        buildingGrid.setAlignment(Pos.CENTER);
        buildingGrid.setVgap(20);
        buildingGrid.setHgap(5);
        resourcesLabel.setAlignment(Pos.CENTER);
        resourcesLabel.setPadding(new Insets(10, 10, 10, 10));
        labelVBox.setAlignment(Pos.BOTTOM_CENTER);
        labelVBox.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;");
        VBox.setVgrow(labelVBox, Priority.ALWAYS);
        VBox.setMargin(labelVBox, new Insets(110, 0, 0, 0));
        updateResources();
        populateBuildingGrid();
    }

    private void updateResources() {
        resourcesLabel.setText(buyer.getResources().toString());
    }

    private void populateBuildingGrid() {
        buildBuildingsGridPane(EconomyBuildingStatistic.EconomyBuildingType.BUILDING, 1);
        buildBuildingsGridPane(EconomyBuildingStatistic.EconomyBuildingType.DWELLINGS, 2);
    }

    private void zajebiscieWzne(int row, ImageView buildingIcon, EconomyBuildingStatistic economyBuildingStatistic, String buildingName, Popup popup, Map<EconomyBuildingStatistic, VBox> buildingBoxes, VBox buildingBox, int i) {
        buildingIcon.setOnMouseClicked(event -> {
            if (town.isBuildingAlreadyBuilt(town, economyBuildingStatistic)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Budynek już zbudowany");
                alert.setHeaderText(null);
                alert.setContentText("Ten budynek już został zbudowany w tym mieście.");
                alert.showAndWait();
            } else if (!economyBuildingStatistic.hasEnoughResourcesToBuild(buyer)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Brak zasobów");
                alert.setHeaderText(null);
                alert.setContentText("Nie masz wystarczająco zasobów, aby kupić ten budynek.");
                alert.showAndWait();
            } else {
                economyBuildingFacade.buildBuilding(buyer, town, buildingName);
                updateResources();
                popup.show(stage);
                updateBuildingStatuses(buildingBoxes);
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), ae -> popup.hide()));
                timeline.play();
            }
        });
        buildingGrid.add(buildingBox, i, row);
    }


    //============== śmieci z gui ================

    private void buildBuildingsGridPane(EconomyBuildingStatistic.EconomyBuildingType aType, int row) {
        List<EconomyBuildingStatistic> allAvailableDwellingsToBuild = economyBuildingFacade.getAllAvailableBuildingsToBuild(aType, town.getFraction())
                .stream().toList();
        int n = allAvailableDwellingsToBuild.size();
        Map<EconomyBuildingStatistic, VBox> buildingBoxes = new HashMap<>();
        for (int i = 0; i < n; i++) {
            var buildingName = allAvailableDwellingsToBuild.get(i).name();
            var image = new Image("image/" + buildingName + ".gif");
            ImageView buildingIcon = new ImageView(image);
            buildingIcon.setFitWidth(image.getWidth() * 4);
            buildingIcon.setFitHeight(image.getHeight() * 4);
            var buildingNameWithSpaces = buildingName.replaceAll("_", " ");
            Label buildingLabel = new Label(buildingNameWithSpaces);
            VBox buildingBox = new VBox(buildingIcon, buildingLabel);
            buildingBox.setAlignment(Pos.CENTER);
            var economyBuildingStatistic = economyBuildingFacade.findEconomyBuildingStatistic(buildingName, town.getFraction())
                    .orElseThrow();
            buildingBoxes.put(economyBuildingStatistic, buildingBox);
            updateBuildingStatuses(buildingBoxes);
            Popup popup = new Popup();
            Label label = new Label("Pomyślnie zakupiłeś budynek " + buildingNameWithSpaces);
            label.setTranslateY(-750);
            label.setStyle("-fx-background-color: #81fa81; -fx-padding: 10;");
            popup.getContent().add(label);
            zajebiscieWzne(row, buildingIcon, economyBuildingStatistic, buildingName, popup, buildingBoxes, buildingBox, i);
        }
    }

    private void updateBuildingStatuses(Map<EconomyBuildingStatistic, VBox> economyBuildingStatisticMap) {
        economyBuildingStatisticMap.forEach((key, value) -> {
            if (town.isBuildingAlreadyBuilt(town, key)) {
                value.setStyle("-fx-border-color: #39d639; -fx-border-width: 5px; -fx-border-radius: 5px;");
            } else if (!key.hasEnoughResourcesToBuild(buyer)) {
                value.setStyle("-fx-border-color: #e14848; -fx-border-width: 5px; -fx-border-radius: 5px;");
            } else {
                value.setStyle("-fx-border-color: #e8c50f; -fx-border-width: 5px; -fx-border-radius: 5px;");
            }
        });
    }
}

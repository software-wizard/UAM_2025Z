package pl.psi.gui.shop.building;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import pl.psi.building.EconomyBuildingFacade;
import pl.psi.building.EconomyBuildingStatistic;
import pl.psi.hero.EconomyHero;
import pl.psi.town.Town;

import java.util.List;

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

    public EconomyBuildingShopController(EconomyBuildingFacade economyBuildingFacade, EconomyHero hero, Town town) {
        this.economyBuildingFacade = economyBuildingFacade;
        this.buyer = hero;
        this.town = town;
    }

    @FXML
    public void initialize() {
        buildingGrid.setPadding(new Insets(10, 10, 10, 10));
        buildingGrid.setAlignment(Pos.CENTER);
        resourcesLabel.setAlignment(Pos.CENTER);
        resourcesLabel.setPadding(new Insets(10, 10, 10, 10));
        labelVBox.setAlignment(Pos.BOTTOM_CENTER);
        //VBox.setMargin(labelVBox, new Insets(530, 0, 0, 0));
        labelVBox.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;");
        updateResources();
        populateBuildingGrid();
    }

    private void updateResources() {
        resourcesLabel.setText("Zasoby: " + buyer.getResources().values());
    }

    private void populateBuildingGrid() {
        buildBuildingsGridPane(EconomyBuildingStatistic.EconomyBuildingType.BUILDING, 1);
        buildBuildingsGridPane(EconomyBuildingStatistic.EconomyBuildingType.DWELLINGS, 2);
    }

    private void buildBuildingsGridPane(EconomyBuildingStatistic.EconomyBuildingType aType, int row) {
        List<EconomyBuildingStatistic> allAvailableDwellingsToBuild = economyBuildingFacade.getAllAvailableBuildingsToBuild(aType)
                .stream().toList();
        int n = allAvailableDwellingsToBuild.size();
        for (int i = 0; i < n; i++) {
            var buildingName = allAvailableDwellingsToBuild.get(i).name();
            var buildingNameWithoutWhiteSpaces = buildingName.replaceAll(" ", "_");
            var image = new Image("image/" + buildingNameWithoutWhiteSpaces + ".gif");
            ImageView buildingIcon = new ImageView(image);
            buildingIcon.setFitWidth(image.getWidth() * 4);
            buildingIcon.setFitHeight(image.getHeight() * 4);
            Label buildingLabel = new Label(buildingName);
            VBox buildingBox = new VBox(buildingIcon, buildingLabel);
            var economyBuildingStatistic = economyBuildingFacade.findEconomyBuildingStatistic(buildingName)
                    .orElseThrow();
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
                    economyBuildingFacade.buildBuilding(buyer, town, buildingNameWithoutWhiteSpaces);
                    updateResources();
                }
            });
            buildingGrid.add(buildingBox, i, row);
        }
    }
}

package pl.psi.gui.shop.building;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;
import pl.psi.building.EconomyBuildingFacade;
import pl.psi.building.model.EconomyBuilding;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.building.model.UpgradableBuilding;
import pl.psi.building.town.Town;
import pl.psi.gui.EcoController;
import pl.psi.hero.EconomyHero;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class EconomyBuildingShopController {

    @FXML
    private GridPane buildingGrid;

    @FXML
    private VBox labelVBox;

    @FXML
    private Label resourcesLabel;

    private final EconomyBuildingView view;
    private final EconomyBuildingFacade economyBuildingFacade;
    private final EconomyHero buyer;
    private final Town town;
    private final Stage stage;

    @FXML
    void initialize() {
        setupUI();
        view.updateResources(resourcesLabel, buyer);
        populateBuildingGrid();
    }

    private void setupUI() {
        buildingGrid.setPadding(new Insets(10));
        buildingGrid.setAlignment(Pos.CENTER);
        buildingGrid.setVgap(20);
        buildingGrid.setHgap(5);
        buildingGrid.setStyle("-fx-background-color: #403429;");

        resourcesLabel.setAlignment(Pos.CENTER);
        resourcesLabel.setPadding(new Insets(10));
        resourcesLabel.setStyle("-fx-text-fill: black;");

        labelVBox.setAlignment(Pos.BOTTOM_CENTER);
        labelVBox.setStyle("""
            -fx-border-color: black;
            -fx-border-width: 2px;
            -fx-border-radius: 5px;
            -fx-background-color: #403429;
        """);
        VBox.setVgrow(labelVBox, Priority.ALWAYS);
    }

    private void populateBuildingGrid() {
        Map<EconomyBuildingStatistic.Type, Map<EconomyBuildingStatistic, VBox>> buildingBoxes = new HashMap<>();

        buildingBoxes.put(EconomyBuildingStatistic.Type.BUILDING,
                createBuildingGrid(EconomyBuildingStatistic.Type.BUILDING, 1, buildingBoxes));
        buildingBoxes.put(EconomyBuildingStatistic.Type.DWELLINGS,
                createBuildingGrid(EconomyBuildingStatistic.Type.DWELLINGS, 2, buildingBoxes));
    }

    private Map<EconomyBuildingStatistic, VBox> createBuildingGrid(
                EconomyBuildingStatistic.Type buildingType,
                int row,
                Map<EconomyBuildingStatistic.Type, Map<EconomyBuildingStatistic, VBox>> allBuildings
    ) {
        List<EconomyBuildingStatistic> availableBuildings = economyBuildingFacade.getAllAvailableBuildingsToBuild(buildingType, town.getFraction());
        Map<EconomyBuildingStatistic, VBox> buildingBoxes = new HashMap<>();

        for (int i = 0; i < availableBuildings.size(); i++) {
            EconomyBuildingStatistic statistic = availableBuildings.get(i);
            VBox buildingBox = createBuildingBox(statistic);
            buildingBoxes.put(statistic, buildingBox);

            addBuildingClickHandler(row, statistic, buildingBox, allBuildings);
            buildingGrid.add(buildingBox, i, row);
        }

        view.updateBuildingStatuses(Map.of(buildingType, buildingBoxes), town, buyer);
        return buildingBoxes;
    }

    private VBox createBuildingBox(EconomyBuildingStatistic statistic) {
        String buildingName = statistic.name();
        Image image = new Image("image/" + buildingName + ".gif");
        ImageView buildingIcon = new ImageView(image);
        buildingIcon.setFitWidth(image.getWidth() * 3);
        buildingIcon.setFitHeight(image.getHeight() * 3);

        String buildingLabel = buildingName.replace("_", " ");
        Label label = new Label(buildingLabel);
        label.setStyle("-fx-text-fill: black;");

        VBox buildingBox = new VBox(buildingIcon, label);
        buildingBox.setAlignment(Pos.CENTER);
        return buildingBox;
    }

    private void addBuildingClickHandler(int row,
                                         EconomyBuildingStatistic statistic,
                                         VBox buildingBox,
                                         Map<EconomyBuildingStatistic.Type, Map<EconomyBuildingStatistic, VBox>> allBuildings
    ) {
        ImageView buildingIcon = (ImageView) buildingBox.getChildren().get(0);
        Popup popup = createPurchasePopup(statistic.name());

        buildingIcon.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                handlePrimaryClick(statistic, popup, allBuildings);
            } else if (event.getButton() == MouseButton.SECONDARY && row == 2) {
                handleSecondaryClick(statistic, buildingIcon, allBuildings);
            }
        });
    }

    private void handlePrimaryClick(EconomyBuildingStatistic statistic,
                                    Popup popup,
                                    Map<EconomyBuildingStatistic.Type, Map<EconomyBuildingStatistic, VBox>> allBuildings
    ) {
        if (town.isBuildingAlreadyBuilt(statistic.name())) {
            EconomyBuilding building = town.findBuildingByName(statistic.name()).orElseThrow();
            if (economyBuildingFacade.isBuildingUpgradable(building)) {
                openCreatureShop((UpgradableBuilding) building, buyer);
            } else {
                view.showAlert("Budynek już zbudowany", "", "Ten budynek już został zbudowany w tym mieście.");
            }
        } else if (!town.containsBuildings(statistic.prerequisites())) {
            List<String> namesOfRequiredBuildings = statistic.prerequisites()
                    .stream()
                    .map(EconomyBuildingStatistic::name)
                    .toList();
            view.showAlert("Wymagany budynek nie jest zbudowany","",  "Aby zbudować ten budynek potrzebujesz zbudować:" + namesOfRequiredBuildings);
        } else if (!statistic.hasEnoughResourcesToBuild(buyer)) {
            view.showAlert("Brak zasobów", "", "Nie masz wystarczająco zasobów, aby kupić ten budynek.");
        } else {
            economyBuildingFacade.buildBuilding(buyer, town, statistic.name());
            view.updateResources(resourcesLabel, buyer);
            view.showPopUp(stage, popup);
            view.updateBuildingStatuses(allBuildings, town, buyer);

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), ae -> popup.hide()));
            timeline.play();
        }
    }

    private void handleSecondaryClick(EconomyBuildingStatistic statistic, ImageView buildingIcon,
                                      Map<EconomyBuildingStatistic.Type, Map<EconomyBuildingStatistic, VBox>> allBuildings) {
        if (!town.isBuildingAlreadyBuilt(statistic.name())) {
            view.showAlert("Nie zbudowano budynku", "", "Aby ulepszyć budynek należy go wybudować.");
            return;
        }

        Optional<EconomyBuilding> buildingOptional = town.findBuildingByName(statistic.name());
        if (buildingOptional.isEmpty()) {
            throw new IllegalStateException("Building cannot be null!");
        }

        UpgradableBuilding upgradableBuilding;
        if (!economyBuildingFacade.isBuildingUpgradable(buildingOptional.get())) {
            throw new IllegalStateException("Building cannot be null!");
        } else {
            upgradableBuilding = (UpgradableBuilding) buildingOptional.get();
        }

        if (upgradableBuilding.isUpgraded()) {
            view.showAlert("Budynek już ulepszony", "", "Budynek został już ulepszony.");
            return;
        }

        if (!buyer.canAfford(upgradableBuilding.getUpgradeCost())) {
            view.showAlert("Brak zasobów", "", "Nie masz wystarczająco zasobów, aby ulepszyć ten budynek.");
            return;
        }

        ButtonType response = view.showUpgradeConfirmationPopup(statistic.name(), upgradableBuilding.getUpgradeCost(), buildingIcon.getImage());
        if (response == ButtonType.OK) {
            town.upgradeBuilding(buyer, statistic.name());
            view.updateResources(resourcesLabel, buyer);
            view.updateBuildingStatuses(allBuildings, town, buyer);
            view.showAlert("Sukces", "", "Budynek " + statistic.name() + " został ulepszony!");
        }
    }

    private Popup createPurchasePopup(String buildingName) {
        Popup popup = new Popup();
        Label label = new Label("Pomyślnie zakupiłeś budynek " + buildingName.replace("_", " "));
        label.setTranslateY(-450);
        label.setStyle("-fx-background-color: #81fa81; -fx-padding: 10;");
        popup.getContent().add(label);
        return popup;
    }

    private void openCreatureShop(UpgradableBuilding aBuilding, EconomyHero aHero) {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader()
                .getResource("fxml/eco.fxml"));
        var controller = new EcoController(aHero, aBuilding);
        loader.setController(controller);
        final Scene scene;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setX(5);
        stage.setY(5);
        stage.show();
    }
}

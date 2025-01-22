package pl.psi;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;

public class EditorController {

    private final int width = 15;
    private final int height = 10;
    private final EditorEngine engine = new EditorEngine(width, height);

    @FXML
    private GridPane gridMap;
    @FXML
    private Button saveButton;

    @FXML
    public void initialize() {
        engine.initializeMap();
        refreshGui();
        saveButton.setOnAction(event -> saveSpecialTiles());
    }

    private void refreshGui() {
        gridMap.getChildren().clear();
        for (Tile tile : engine.getTiles()) {
            TileView tileView = new TileView(tile);
            gridMap.add(tileView, tileView.getTile().getX(), tileView.getTile().getY());
        }
    }

    private void saveSpecialTiles() {
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String filePath = "battle-map-editor/src/main/resources/specialTiles/specialTiles_" + now.format(formatter) + ".json";
            engine.saveSpecialTilesToFile(filePath);
            System.out.println("Special tiles saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

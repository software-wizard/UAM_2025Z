package pl.psi.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class MainBattleController {
    @FXML
    private GridPane gridMap;
    @FXML
    private Button passButton;

    public MainBattleController() {

    }

    @FXML
    private void initialize() {
        refreshGui();
    }

    private void refreshGui() {
        gridMap.getChildren()
                .clear();

        gridMap.add(new Rectangle(100, 100), 0, 0);
        gridMap.add(new Rectangle(100, 100), 1, 1);
    }
}

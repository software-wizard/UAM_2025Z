package pl.psi;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class TileView extends StackPane {
    @Getter
    final private Tile tile;

    private final Rectangle rect;
    private Label label;

    public TileView(Tile tile) {
        this.tile = tile;
        rect = new Rectangle(60, 60);
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.RED);
        getChildren().add(rect);
        label = new Label("");
        this.setOnMouseClicked(event -> showTileTypeDialog());
    }

    private void showTileTypeDialog() {
        List<TileType> tileTypes = new ArrayList<>();
        tileTypes.add(TileType.OBSTACLE);
        tileTypes.add(TileType.DAMAGE);
        tileTypes.add(TileType.INCREASE_ATTACK_BUFF);

        ChoiceDialog<TileType> dialog = new ChoiceDialog<>(TileType.OBSTACLE, tileTypes);
        dialog.setTitle("Select Tile Type");
        dialog.setHeaderText(null);
        dialog.setContentText("Choose a tile type:");

        dialog.showAndWait().ifPresent(type -> {
            tile.tileType = type;
            if (tile.tileType == TileType.INCREASE_ATTACK_BUFF) {
                showBuffDialog();
            } else if (tile.tileType == TileType.DAMAGE) {
                showDamageDialog();
            }
            updateTileAppearance();
        });
    }

    private void showDamageDialog() {
        TextInputDialog valueDialog = new TextInputDialog();
        valueDialog.setTitle("Damage Tile");
        valueDialog.setHeaderText("Enter Damage Value");
        valueDialog.setContentText("Value:");

        valueDialog.showAndWait().ifPresent(valueStr -> {
            tile.value = Integer.parseInt(valueStr);
            updateTileAppearance();
        });
    }

    private void showBuffDialog() {
        TextInputDialog valueDialog = new TextInputDialog();
        valueDialog.setTitle("Increase Attack Buff");
        valueDialog.setHeaderText(null);
        valueDialog.setContentText("Enter buff value:");

        valueDialog.showAndWait().ifPresent(value -> {
            try {
                int buffValue = Integer.parseInt(value);
                TextInputDialog durationDialog = new TextInputDialog();
                durationDialog.setTitle("Buff Duration");
                durationDialog.setHeaderText(null);
                durationDialog.setContentText("Enter buff duration:");

                durationDialog.showAndWait().ifPresent(dur -> {
                    try {
                        int duration = Integer.parseInt(dur);
                        updateTileAppearance();
                    } catch (NumberFormatException e) {
                        System.out.println(e);
                    }
                });
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
        });
    }

    private void updateTileAppearance() {
        switch (tile.tileType) {
            case OBSTACLE:
                rect.setFill(Color.BLACK);
                break;
            case DAMAGE:
                rect.setFill(Color.ORANGE);
                break;
            case INCREASE_ATTACK_BUFF:
                rect.setFill(Color.BLUE);
                break;
        }
    }
}

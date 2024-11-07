package pl.psi.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.example.*;

import java.util.List;
import java.util.Optional;

import static javafx.scene.paint.Color.*;

public class MainBattleController {
    private final GameEngine gameEngine;
    @FXML
    private GridPane gridMap;
    @FXML
    private Button passButton;

    public MainBattleController() {
        List<Creature> p1 = List.of(Creature.builder().amount(5).maxHp(2).moveRange(3).build(), Creature.builder().amount(3).maxHp(11).moveRange(7).build());
        List<Creature> p2 = List.of(Creature.builder().amount(2).maxHp(112).moveRange(5).build());

        gameEngine = new GameEngine(new Hero(p1), new Hero(p2));

    }

    @FXML
    private void initialize() {
        refreshGui();
        passButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> gameEngine.pass());
    }

    private void refreshGui() {
        gridMap.getChildren().clear();

        for (int x = 0; x < Board.WIDTH; x++) {
            for (int y = 0; y < Board.HEIGHT; y++) {
                final int xx = x;
                final int yy = y;
                MapTile mapTile = new MapTile();
                gridMap.add(mapTile, x, y);

                Optional<Creature> creatureCandidate = gameEngine.get(new Point(x, y));
                creatureCandidate.ifPresent(c -> mapTile.setFill(BLUE));

                creatureCandidate.ifPresent(c -> {
                    if (gameEngine.isCurrentCreature(c)) {
                        mapTile.setFill(GREEN);
                    }
                });
                if (gameEngine.canMove(new Point(x, y))) {
                    mapTile.setFill(GREY);
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            (e) -> {
                                gameEngine.move(new Point(xx, yy));
                                refreshGui();
                            }
                    );
                }
            }
        }
    }
}

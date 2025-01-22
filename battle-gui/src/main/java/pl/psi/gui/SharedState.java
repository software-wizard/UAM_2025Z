package pl.psi.gui;

import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

public class SharedState {
    int INITIAL_SELECTED_SPELL_IDX = -1;

    @Getter
    @Setter
    private int selectedSpellIdx;

    private final Runnable refreshGuiCallback;

    @Getter
    private final GridPane gridPane;

    public SharedState(int aSelectedSpellIdx, Runnable aRefreshGuiCallback, GridPane aGridPane) {
        this.selectedSpellIdx = aSelectedSpellIdx;
        this.refreshGuiCallback = aRefreshGuiCallback;
        this.gridPane = aGridPane;
    }

    public void refreshGui() {
        if (refreshGuiCallback != null) {
            refreshGuiCallback.run();
        }
    }

    public void resetSelectedSpellIdx() {
        this.selectedSpellIdx = INITIAL_SELECTED_SPELL_IDX;
    }
}

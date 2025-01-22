package pl.psi.gui;

import lombok.Getter;
import lombok.Setter;

public class SharedState {
    int INITIAL_SELECTED_SPELL_IDX = -1;
    @Getter
    @Setter
    private int selectedSpellIdx;
    private final Runnable refreshGuiCallback;

    public SharedState(int aSelectedSpellIdx, Runnable aRefreshGuiCallback) {
        this.selectedSpellIdx = aSelectedSpellIdx;
        this.refreshGuiCallback = aRefreshGuiCallback;
    }

    public void refreshGui() {
        if (refreshGuiCallback != null) {
            refreshGuiCallback.run();
        }
    }

    public boolean isSpellSelected(){
        return selectedSpellIdx >= 0;
    }

    public void resetSelectedSpellIdx() {
        this.selectedSpellIdx = INITIAL_SELECTED_SPELL_IDX;
    }
}

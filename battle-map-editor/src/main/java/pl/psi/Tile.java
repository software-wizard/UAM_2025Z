package pl.psi;

import lombok.Getter;

@Getter
public class Tile {

    TileType tileType;
    int value = 0;
    private int duration = 0;
    private int x;
    private int y;

    Tile(TileType aType, int aPosX, int aPosY){
        this.tileType = aType;
        this.x = aPosX;
        this.y = aPosY;
    }

    public boolean isSpecial() {
        return this.tileType != TileType.DEFAULT;
    }
}

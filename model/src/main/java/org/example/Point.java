package org.example;

import lombok.Value;

@Value
public class Point {
    int x;
    int y;

    int distance(Point aSourcePoint) {
        int xDistance = Math.abs((this.x - aSourcePoint.x));
        int yDistance = Math.abs((this.y - aSourcePoint.y));

        return xDistance + yDistance;
    }
}

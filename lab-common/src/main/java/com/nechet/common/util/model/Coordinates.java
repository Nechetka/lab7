package com.nechet.common.util.model;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Serializable {
    private Double x; //Поле не может быть null
    private int y; //Максимальное значение поля: 58

    public void setX(Double x) {
        this.x = x;
    }

    public Double getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates cord = (Coordinates) o;
        return Double.compare(cord.x, this.x) == 0 && this.y==cord.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x: " + x +
                ", y: " + y +
                '}';
    }
}

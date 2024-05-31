package com.nechet.common.util.model;

import java.io.Serializable;
import java.util.Objects;

public class Chapter implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String parentLegion;
    private Integer marineCount; //Поле не может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 1000
    private String world; //Поле не может быть null

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setParentLegion(String parentLegion) {
        this.parentLegion = parentLegion;
    }

    public String getParentLegion() {
        return parentLegion;
    }

    public void setMarineCount(Integer marineCount) {
        this.marineCount = marineCount;
    }

    public Integer getMarineCount() {
        return marineCount;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public String getWorld() {
        return world;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chapter ch = (Chapter) o;
        return Double.compare(ch.marineCount, this.marineCount) == 0 && Objects.equals(ch.name,this.name)
                & Objects.equals(ch.world,this.world) & Objects.equals(ch.parentLegion,this.parentLegion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,parentLegion,marineCount,world);
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "name: " + name +
                ", parentLegion: " + parentLegion +
                ", marineCount: " + marineCount +
                ", world: " + world +
                '}';
    }

}

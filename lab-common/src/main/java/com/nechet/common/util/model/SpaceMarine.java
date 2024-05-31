package com.nechet.common.util.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class SpaceMarine implements Comparable<SpaceMarine>, Serializable {
    public SpaceMarine(){

    }
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double health; //Значение поля должно быть больше 0
    private Boolean loyal; //Поле не может быть null
    private AstartesCategory category; //Поле не может быть null
    private Weapon weaponType; //Поле может быть null
    private Chapter chapter; //Поле не может быть null

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getHealth() {
        return health;
    }

    public void setLoyal(Boolean loyal) {
        this.loyal = loyal;
    }

    public Boolean getLoyal() {
        return loyal;
    }

    public void setCategory(AstartesCategory category) {
        this.category = category;
    }

    public AstartesCategory getCategory() {
        return category;
    }

    public void setWeaponType(Weapon weaponType) {
        this.weaponType = weaponType;
    }

    public Weapon getWeaponType() {
        return weaponType;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpaceMarine marine = (SpaceMarine) o;
        return  Objects.equals(this.name,marine.name) && this.coordinates.equals(marine.coordinates)
                && Double.compare(this.health,marine.health)==0 && this.loyal==marine.loyal
                && this.chapter.equals(marine.chapter) && this.category.equals(marine.category) && this.weaponType.equals(marine.weaponType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, health, loyal, category, weaponType, chapter);
    }

    @Override
    public String toString() {
        return "SpaceMarine{" +
                "id: " + id +
                ", name: " + name  +
                ", " + coordinates +
                ", creationDate: " + creationDate +
                ", health: " + health +
                ", loyal: " + loyal +
                ", AstartesCategory: " + category +
                ", Weapon: " + weaponType +
                ", " + chapter+
                '}';
    }
    @Override
    public int compareTo(SpaceMarine o) {
        return (int) (this.id-o.getId());
    }
}
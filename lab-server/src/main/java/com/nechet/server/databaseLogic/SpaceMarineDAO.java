package com.nechet.server.databaseLogic;

import com.nechet.common.util.model.*;
import com.nechet.server.system.Utils;
import jdk.jshell.execution.Util;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class SpaceMarineDAO extends DatabaseConnection {
    private final String URL = Utils.getUrl();
    private final String USERNAME = Utils.getLogin();
    private final String PASSWORD = Utils.getPassword();

    public SpaceMarineDAO(String url, String login, String password) throws SQLException {
        super(url, login, password);
    }

    public Long saveSpaceMarine(SpaceMarine spaceMarine) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO SpaceMarines (name, coord_id, creation_date, health, loyal, category, weapon_type, chapter_id) " +
                             "VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, spaceMarine.getName());
            preparedStatement.setInt(2, getCoordinateId(spaceMarine.getCoordinates()));
            preparedStatement.setDate(3, new java.sql.Date(spaceMarine.getCreationDate().getTime()));
            preparedStatement.setDouble(4, spaceMarine.getHealth());
            preparedStatement.setBoolean(5, spaceMarine.getLoyal());
            preparedStatement.setString(6, spaceMarine.getCategory().name());
            preparedStatement.setString(7, spaceMarine.getWeaponType().name());
            preparedStatement.setInt(8, getChapterId(spaceMarine.getChapter()));
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getCoordinateId(Coordinates coordinates) {
        System.out.println("X: "+coordinates.getX()+" Y: "+coordinates.getY());
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Coordinates (x, y) VALUES (?,?)")){
            preparedStatement.setDouble(1, coordinates.getX());
            preparedStatement.setInt(2, coordinates.getY());
            preparedStatement.executeUpdate();
            return getLastInsertId(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private int getChapterId(Chapter chapter) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Chapters (name, parent_legion, marine_count, world) VALUES (?,?,?,?)")) {
            preparedStatement.setString(1, chapter.getName());
            preparedStatement.setString(2, chapter.getParentLegion());
            preparedStatement.setInt(3, chapter.getMarineCount());
            preparedStatement.setString(4, chapter.getWorld());
            preparedStatement.executeUpdate();
            return getLastInsertId(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getLastInsertId(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT lastval()")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        }
    }
    public boolean updateId(String login, Long id,SpaceMarine mar) throws SQLException {
        if(deleteSpaceMarineId(login,id)){
            return false;
        }
        long newid = saveSpaceMarine(mar);
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE SpaceMarines SET id=? WHERE id = ?;")) {
            preparedStatement.setLong(1, newid);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public boolean deleteSpaceMarineId(String login, Long id) throws SQLException {
        PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM Usersrelationship WHERE login = ? AND object_id = ?;");
        checkStatement.setString(1, login);
        checkStatement.setLong(2, id);
        ResultSet resultSet = checkStatement.executeQuery();
        if (!resultSet.next()) {
            return false;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM SpaceMarines USING UsersRelationship WHERE SpaceMarines.id = UsersRelationship.object_id AND SpaceMarines.id = ? AND UsersRelationship.login = ?;")) {
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, login);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean deleteSpaceMarine(String login, String queryPart) throws SQLException {
        PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM Usersrelationship WHERE login = ?;");
        checkStatement.setString(1, login);
        ResultSet resultSet = checkStatement.executeQuery();
        if (!resultSet.next()) {
            return false;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM SpaceMarines USING UsersRelationship WHERE SpaceMarines.id = UsersRelationship.object_id AND SpaceMarines."+queryPart+" AND UsersRelationship.login = ?;")) {
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public Set<SpaceMarine> getAllSpaceMarines() {
        Set<SpaceMarine> spaceMarines = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM SpaceMarines");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                SpaceMarine spaceMarine = new SpaceMarine();
                spaceMarine.setId(resultSet.getInt("id"));
                spaceMarine.setName(resultSet.getString("name"));
                Coordinates coordinates = getCoordinates(resultSet.getInt("coord_id"));
                spaceMarine.setCoordinates(coordinates);
                spaceMarine.setCreationDate(new java.util.Date(resultSet.getDate("creation_date").getTime()));
                spaceMarine.setHealth(resultSet.getDouble("health"));
                spaceMarine.setLoyal(resultSet.getBoolean("loyal"));
                spaceMarine.setCategory(AstartesCategory.valueOf(resultSet.getString("category")));
                spaceMarine.setWeaponType(Weapon.valueOf(resultSet.getString("weapon_type")));
                Chapter chapter = getChapter(resultSet.getInt("chapter_id"));
                spaceMarine.setChapter(chapter);
                spaceMarines.add(spaceMarine);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return spaceMarines;
    }

    private Coordinates getCoordinates(int id) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Coordinates WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Coordinates coordinates = new Coordinates();
            coordinates.setX(resultSet.getDouble("x"));
            coordinates.setY(resultSet.getInt("y"));
            return coordinates;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Chapter getChapter(int id) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Chapters WHERE id =?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Chapter chapter = new Chapter();
            chapter.setName(resultSet.getString("name"));
            chapter.setParentLegion(resultSet.getString("parent_legion"));
            chapter.setMarineCount(resultSet.getInt("marine_count"));
            chapter.setWorld(resultSet.getString("world"));
            return chapter;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


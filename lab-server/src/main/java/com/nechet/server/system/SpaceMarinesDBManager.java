package com.nechet.server.system;

import com.nechet.common.util.model.SpaceMarine;
import com.nechet.server.databaseLogic.RelationshipsDAO;
import com.nechet.server.databaseLogic.SpaceMarineDAO;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;

public class SpaceMarinesDBManager implements CollectionReceiver<TreeSet<SpaceMarine>, SpaceMarine>{
    static private SpaceMarinesManager collection;
    private String login;

    public SpaceMarinesDBManager(String login) {
        collection = SpaceMarinesManager.getInstance();
        this.login = login;
    }
    @Override
    public TreeSet<SpaceMarine> getCollection() throws SQLException {
        SpaceMarineDAO marines = new SpaceMarineDAO(Utils.getUrl(),Utils.getLogin(),Utils.getPassword());
        collection.setCollection(new TreeSet<SpaceMarine>(marines.getAllSpaceMarines()));
        return collection.getCollection();
    }

    @Override
    public void setCollection(TreeSet<SpaceMarine> value) throws SQLException {
        SpaceMarineDAO marines = new SpaceMarineDAO(Utils.getUrl(),Utils.getLogin(),Utils.getPassword());
        clearCollection();
        collection.clearCollection();
        for (SpaceMarine val:value){
            marines.saveSpaceMarine(val);
            collection.addElementToCollection(val);
        }
    }

    @Override
    public void addElementToCollection(SpaceMarine value) throws SQLException {
        SpaceMarineDAO marines = new SpaceMarineDAO(Utils.getUrl(),Utils.getLogin(),Utils.getPassword());
        RelationshipsDAO rel = new RelationshipsDAO(Utils.getUrl(),Utils.getLogin(),Utils.getPassword());
        long id = marines.saveSpaceMarine(value);
        value.setId(id);
        rel.addRelationship(id,login);
        collection.addElementToCollection(value);

    }

    @Override
    public void clearCollection() throws SQLException {
        SpaceMarineDAO marines = new SpaceMarineDAO(Utils.getUrl(),Utils.getLogin(),Utils.getPassword());
        marines.deleteSpaceMarine(login,"id > 0");
        collection.setCollection(new TreeSet<>(marines.getAllSpaceMarines()));

    }
    public  boolean update(long id, SpaceMarine marine) throws SQLException {
        SpaceMarineDAO marines = new SpaceMarineDAO(Utils.getUrl(),Utils.getLogin(),Utils.getPassword());
        return marines.updateId(login,id,marine);
    }

    @Override
    public int getSize() {
        return collection.getSize();
    }

    @Override
    public void baseSort() {
        collection.baseSort();
    }

    @Override
    public Date getInitDate() {
        return collection.getInitDate();
    }

    @Override
    public SpaceMarine getMaxElement(Comparator<SpaceMarine> comp) {
        return collection.getMaxElement(comp);
    }

    @Override
    public SpaceMarine getMinElement(Comparator<SpaceMarine> comp) {
        return collection.getMinElement(comp);
    }

    public boolean removeIf(String partSqlRequest) throws SQLException {
        SpaceMarineDAO marines = new SpaceMarineDAO(Utils.getUrl(),Utils.getLogin(),Utils.getPassword());
        if (marines.deleteSpaceMarine(login,partSqlRequest)){
        TreeSet<SpaceMarine> marine = new TreeSet<>(marines.getAllSpaceMarines());
        collection.setCollection(marine);
        return  true;
        } else { return false;}
    }
    public boolean removeId(long id) throws SQLException {
        SpaceMarineDAO marines = new SpaceMarineDAO(Utils.getUrl(),Utils.getLogin(),Utils.getPassword());
        if (marines.deleteSpaceMarineId(login,id)){
        TreeSet<SpaceMarine> marine = new TreeSet<>(marines.getAllSpaceMarines());
        collection.setCollection(marine);
        return  true;
        } else { return false;}
    }

    @Override
    public boolean isEmpty() {
        return collection.isEmpty();
    }
}

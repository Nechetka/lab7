package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.model.SpaceMarine;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.system.CollectionReceiver;
import com.nechet.server.system.SpaceMarinesManager;

import java.sql.SQLException;
import java.util.TreeSet;

public class ShowCommand implements BaseCommand{
    private final String name = "show";
    private String result = "";
    public String getResult(){
        return result;
    }

    @Override
    public void execute(CommandDescription d) {
        CollectionReceiver<TreeSet<SpaceMarine>,SpaceMarine> colMan = SpaceMarinesManager.getInstance();
        if (colMan.isEmpty()){
            result+="В коллекции нет объектов";
        } else {
            try {
                colMan.getCollection().forEach(obj -> result+=obj.toString()+"\n");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String descr() {
        return "Выводит все элементы коллекции в строковом представлении";
    }

    @Override
    public String getName() {
        return this.name;
    }
}

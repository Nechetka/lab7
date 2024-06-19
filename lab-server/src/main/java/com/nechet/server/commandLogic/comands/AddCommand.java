package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.model.SpaceMarine;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.system.CollectionReceiver;
import com.nechet.server.system.SpaceMarinesDBManager;
import com.nechet.server.system.SpaceMarinesManager;
import com.nechet.server.system.Utils;

import java.sql.SQLException;
import java.util.TreeSet;
public class AddCommand implements BaseCommand{
    private final String name = "add";
    private String result = "";

    @Override
    public void execute(CommandDescription descr){
        CollectionReceiver<TreeSet<SpaceMarine>, SpaceMarine> colMan = new SpaceMarinesDBManager(descr.getLogin());
        SpaceMarine marine = descr.getObjectArray().get(0);
        marine.setId(Utils.getNewId());
        try {
            colMan.addElementToCollection(marine);
            result +="Марин успешно добавлен в коллекцию";
        } catch (SQLException e) {
            e.printStackTrace();
            result += "Не удалось добавить элемент в коллекцию";
        }

    }
    public String getResult(){
        return result;
    }

    @Override
    public String descr() {
        return "Добавляет объект в коллекцию";
    }

    @Override
    public String getName() {
        return this.name;
    }
}

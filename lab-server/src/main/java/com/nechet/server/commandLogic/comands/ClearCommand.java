package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.model.SpaceMarine;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.system.CollectionReceiver;
import com.nechet.server.system.SpaceMarinesDBManager;
import com.nechet.server.system.SpaceMarinesManager;

import java.sql.SQLException;
import java.util.TreeSet;

public class ClearCommand implements BaseCommand{
    private final String name = "clear";
    private String result = "";
    public String getResult(){
        return result;
    }

    @Override
    public void execute(CommandDescription descr) {
        CollectionReceiver<TreeSet<SpaceMarine>,SpaceMarine> colMan = new SpaceMarinesDBManager(descr.getLogin());
        try {
            colMan.clearCollection();
        } catch (SQLException e) {
            result+="Ошибка при очищении коллекции в базе данных";
        }
        result+="Коллекция была очищена.";

    }

    @Override
    public String descr() {
        return "Очищает коллекцию";
    }

    @Override
    public String getName() {
        return this.name;
    }

}

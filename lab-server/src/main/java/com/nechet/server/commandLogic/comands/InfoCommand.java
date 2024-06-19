package com.nechet.server.commandLogic.comands;


import com.nechet.common.util.model.SpaceMarine;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.system.CollectionReceiver;
import com.nechet.server.system.SpaceMarinesDBManager;
import com.nechet.server.system.SpaceMarinesManager;

import java.sql.SQLException;
import java.util.TreeSet;


public class InfoCommand implements BaseCommand{
    private final String name = "info";
    private String result = "";
    public String getResult(){
        return result;
    }
    @Override
    public void execute(CommandDescription d) {
        CollectionReceiver<TreeSet<SpaceMarine>,SpaceMarine> colMan = new SpaceMarinesDBManager(d.getLogin());
        try {
            result+="Тип коллекции: "+colMan.getCollection().getClass()+"\n";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        result+="Дата создания: "+colMan.getInitDate().toString()+"\n";
        result+="Количество элементов: "+colMan.getSize()+"\n";
    }

    @Override
    public String descr() {
        return "Показывает информацию о коллекции(тип, дату создания, количество элементов)";
    }

    @Override
    public String getName() {
        return this.name;
    }
}


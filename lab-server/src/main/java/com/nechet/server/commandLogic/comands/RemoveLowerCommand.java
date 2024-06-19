package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.model.SpaceMarine;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.system.CollectionReceiver;
import com.nechet.server.system.SpaceMarinesDBManager;
import com.nechet.server.system.SpaceMarinesManager;
import com.nechet.server.system.Utils;

import java.sql.SQLException;
import java.util.TreeSet;

public class RemoveLowerCommand implements BaseCommand{
    private final String name = "remove_lower";
    private String result = "";
    public String getResult(){
        return result;
    }
    @Override
    public void execute(CommandDescription d ){
        SpaceMarinesDBManager colMan = new SpaceMarinesDBManager(d.getLogin());
        SpaceMarine newMarine = d.getObjectArray().get(0);
        newMarine.setId(Utils.getNewId());
        try {
            colMan.removeIf("health < "+newMarine.getHealth());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        result+=" Удалены все обьекты меньшие заданного";
    }

    @Override
    public String descr() {
        return "Удаляет элементы, меньшие заданного";
    }

    @Override
    public String getName() {
        return this.name;
    }
}

package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.model.SpaceMarine;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.system.CollectionReceiver;
import com.nechet.server.system.SpaceMarinesManager;
import com.nechet.server.system.Utils;

import java.util.TreeSet;

public class RemoveGreaterCommand implements BaseCommand{
    private final String name = "remove_greater";
    private String result = "";
    public String getResult(){
        return result;
    }

    @Override
    public void execute(CommandDescription d){
        CollectionReceiver<TreeSet<SpaceMarine>,SpaceMarine> colMan = SpaceMarinesManager.getInstance();
        SpaceMarine newMarine = d.getObjectArray().get(0);
        newMarine.setId(Utils.getNewId());
        colMan.removeIf(marine -> newMarine.getHealth() < marine.getHealth());
        result+="Удалены все объекты больше полученного";
    }

    @Override
    public String descr() {
        return "Удаляет элементы, большие заданного";
    }

    @Override
    public String getName() {
        return this.name;
    }
}

package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.model.SpaceMarine;
import com.nechet.common.util.model.comparators.MarineDIstanceComparator;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.system.CollectionReceiver;
import com.nechet.server.system.SpaceMarinesDBManager;
import com.nechet.server.system.SpaceMarinesManager;
import com.nechet.server.system.Utils;


import java.sql.SQLException;
import java.util.Comparator;
import java.util.TreeSet;

public class AddIfMaxCommand implements BaseCommand{
    private String result ="";
    private final String name = "add_if_max";

    @Override
    public void execute(CommandDescription descr){
        CollectionReceiver<TreeSet<SpaceMarine>,SpaceMarine> colMan = new SpaceMarinesDBManager(descr.getLogin());
        SpaceMarine newMarine = descr.getObjectArray().get(0);
        newMarine.setId(Utils.getNewId());
        Comparator<SpaceMarine> comp = new MarineDIstanceComparator();
        if(colMan.getMaxElement(comp).getHealth()>= newMarine.getHealth())
        {
            result+="Введенный элемент был не максимальный.";
        }
        else{
            try {
                colMan.addElementToCollection(newMarine);
            } catch (SQLException e) {
                result+="Ошибка добавления объекта в базу";
            }
            result+="Введенный элемент был максимальный и успешно добавлен в коллекцию.";
        }
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public String descr() {
        return "Добавляет введеный элемент, если он максимальный (по значению поля health)";
    }

    @Override
    public String getName() {
        return this.name;
    }
}

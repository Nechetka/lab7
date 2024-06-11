package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.exceptions.WrongValuesOfCommandArgumentException;
import com.nechet.common.util.model.SpaceMarine;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.system.CollectionReceiver;
import com.nechet.server.system.SpaceMarinesManager;
import com.nechet.server.system.UserConsole;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeSet;

public class RemoveByIdCommand implements BaseCommand{
    private final String name = "remove_by_id";
    private String result = "";
    public String getResult(){
        return result;
    }
    @Override
    public void execute(CommandDescription d) throws WrongValuesOfCommandArgumentException {
        CollectionReceiver<TreeSet<SpaceMarine>,SpaceMarine> colMan = SpaceMarinesManager.getInstance();
        ArrayList<String> str = d.getArgs();
        long id;
        try{
            id = Long.parseLong(str.get(1));
            if(!colMan.removeIf(marine -> Objects.equals(marine.getId(), id)))
            {
                result+="Элемента с таким Id коллекции нет";
            }
            else {
                result+="Марин успешно удален";
            }
        } catch (NumberFormatException e) {
           result+="Неправильный ввод Id. Попробуйте заново.";
        }

    }

    @Override
    public String descr() {
        return "Удаляет элемент, Id которого равен указанному.\n" +
                "   Аргумент команды: Id;    Тип: Long";
    }

    @Override
    public String getName() {
        return this.name;
    }
}

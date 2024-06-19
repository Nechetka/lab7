package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.exceptions.WrongValuesOfCommandArgumentException;
import com.nechet.common.util.model.SpaceMarine;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.system.CollectionReceiver;
import com.nechet.server.system.SpaceMarinesDBManager;
import com.nechet.server.system.SpaceMarinesManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeSet;

public class RemoveByIdCommand implements BaseCommand{
    private final String name = "remove_by_id";
    private String result = "";
    public String getResult(){
        return result;
    }
    @Override
    public void execute(CommandDescription d) throws WrongValuesOfCommandArgumentException {
        SpaceMarinesDBManager colMan = new SpaceMarinesDBManager(d.getLogin());
        ArrayList<String> str = d.getArgs();
        long id;
        try{
            id = Long.parseLong(str.get(1));
            if(!colMan.removeId(id))
            {
                result+="Элемента с таким Id коллекции нет или он принадлежит не вам";
            }
            else {
                result+="Марин успешно удален";
            }
        } catch (NumberFormatException e) {
           result+="Неправильный ввод Id. Попробуйте заново.";
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

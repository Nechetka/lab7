package com.nechet.server.commandLogic.comands;


import com.nechet.common.util.model.SpaceMarine;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.system.CollectionReceiver;
import com.nechet.server.system.SpaceMarinesDBManager;
import com.nechet.server.system.SpaceMarinesManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeSet;

public class
UpdateByIdCommand implements BaseCommand {
    private final String name = "update";
    private String result = "";
    public String getResult(){
        return result;
    }
    @Override
    public void execute(CommandDescription d){
        SpaceMarinesDBManager colMan = new SpaceMarinesDBManager(d.getLogin());
        ArrayList<String> str = d.getArgs();
        long id;
        try{
            id = Long.parseLong(str.get(1));
            if(!colMan.removeId(id))
            {
                result+="Элемент с таким Id не ваш";
            }
            else{
                SpaceMarine updateMarine = d.getObjectArray().get(0);
                updateMarine.setId(id);
                colMan.addElementToCollection(updateMarine);
                result+="Элемент был успешно обновлен";
            }
        } catch (NumberFormatException e) {
            result+=("Неправильный ввод значения Id. Попробуйте заново.");
        } catch (SQLException e) {
            result+="Элемента с таким id нет";
        }

    }

    @Override
    public String descr() {
        return "Обновляет элемент, Id которого равен указанному.\n" +
                "   Аргумент команды: Id;    Тип: Long";
    }

    @Override
    public String getName() {
        return this.name;
    }
}

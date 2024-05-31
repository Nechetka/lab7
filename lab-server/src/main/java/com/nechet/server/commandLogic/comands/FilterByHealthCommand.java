package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.model.SpaceMarine;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.system.SpaceMarinesManager;

import java.util.ArrayList;
import java.util.TreeSet;

public class FilterByHealthCommand implements BaseCommand {
    private final String name = "filter_greater_than_weapon_type";

    private String result = "";
    public String getResult(){
        return result;
    }
    @Override
    public void execute(CommandDescription d){
        ArrayList<String> str = d.getArgs();
        TreeSet<SpaceMarine> coll = SpaceMarinesManager.getInstance().getCollection();
        try {
            double health =  Double.parseDouble(str.get(1));
            if (coll.isEmpty()) {
                result+="В коллекции нет объектов";
            } else {
                coll.stream().filter(obj -> health==obj.getHealth()).forEach(obj -> result+=obj.toString()+"\n");
            }
        } catch (NumberFormatException e) {
            result+="Не правильный ввод переменной health. Попробуйте заново.";
        }
    }

    @Override
    public String descr() {
        return "Выводит все элементы коллекции, значение поля health которых равно заданному.\n" +
                "   Аргумент команды: health;    Тип: double";
    }

    @Override
    public String getName() {
        return this.name;
    }
}

package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.model.SpaceMarine;
import com.nechet.common.util.model.Weapon;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.system.SpaceMarinesManager;
import com.nechet.server.system.UserConsole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeSet;

public class FilterGreaterThanWeaponTypeCommand implements BaseCommand{
    private final String name = "filter_greater_than_weapon_type";
    private String result = "";
    public String getResult(){
        return result;
    }

    @Override
    public void execute(CommandDescription d){
        TreeSet<SpaceMarine> coll = SpaceMarinesManager.getInstance().getCollection();
        ArrayList<String> str = d.getArgs();

        try {
            Weapon weapon = Weapon.valueOf(str.get(1));
            if (coll.isEmpty()) {
                result+="В коллекции нет объектов";
            } else {
                coll.stream().filter(obj -> weapon.ordinal() < obj.getWeaponType().ordinal()).forEach(obj -> result+=obj.toString()+"\n");
            }
        } catch (IllegalArgumentException | EnumConstantNotPresentException e) {
            result+="Нет такой переменной. Попробуйте заново.";
        }
    }

    @Override
    public String descr() {
        var weapons = Arrays.toString(Weapon.values());
        return "Выводит все элементы коллекции, значение поля weaponType которых больше заданного\n" +
                "   Возможные аргументы команды: "+weapons+";    Тип: Weapon";
    }

    @Override
    public String getName() {
        return this.name;
    }
}

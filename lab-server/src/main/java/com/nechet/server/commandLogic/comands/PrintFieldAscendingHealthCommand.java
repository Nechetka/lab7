package com.nechet.server.commandLogic.comands;

import com.nechet.common.util.model.SpaceMarine;
import com.nechet.common.util.model.comparators.MarineHealthComparator;
import com.nechet.common.util.requestLogic.CommandDescription;
import com.nechet.server.system.SpaceMarinesManager;
import java.util.Comparator;

public class PrintFieldAscendingHealthCommand implements BaseCommand {
    private final String name = "print_fields_ascending_health";

    private String result = "";
    public String getResult(){
        return result;
    }
    @Override
    public void execute(CommandDescription d) {
        SpaceMarinesManager colMan = SpaceMarinesManager.getInstance();
        Comparator<SpaceMarine> healthComp = new MarineHealthComparator();
        if (colMan.getSize()>0){
        colMan.getCollection().stream().sorted(healthComp).forEach(obj -> result+=obj.getHealth()+" ");
        result+="\n";
        }else{
            result+="В коллекции нет объектов";
        }
    }

    @Override
    public String descr() {
        return "Выводит значение поля Health всех элементов в порядке возрастания";
    }

    @Override
    public String getName() {
        return this.name;
    }
}

package com.nechet.common.util.requestLogic;

import com.nechet.common.util.exceptions.CreateObjectException;
import com.nechet.common.util.exceptions.WrongValuesOfCommandArgumentException;
import com.nechet.common.util.model.SpaceMarine;
import com.nechet.common.util.model.Weapon;

import java.io.Serializable;
import java.util.ArrayList;

import static com.nechet.common.util.requestLogic.RequestArgumentType.NO_ARGS;


public class CommandDescription implements Serializable {
    private final String name;
    private final RequestArgumentType types;
    private ArrayList<String> args = new ArrayList<>();
    private final ArrayList<SpaceMarine> objectArray = new ArrayList<>();


    public CommandDescription(String name, RequestArgumentType types) {
        this.name = name;
        this.types = types;
    }

    public void setAll(String[] args) throws WrongValuesOfCommandArgumentException, CreateObjectException {
        try {
            this.args.add(this.name);
            if (2 < args.length || (args.length==1 && types.compareTo(NO_ARGS)!=0)) {
                throw new WrongValuesOfCommandArgumentException("Неверное количество аргументов команды");
            }
            switch (types) {
                case NO_ARGS:
                    if (args.length != 1) {
                        throw new WrongValuesOfCommandArgumentException("Неверное количество аргументов команды");
                    }
                    break;
                case DOUBLE:
                    double db = Double.parseDouble(args[1]);
                    this.args.add(Double.toString(db));
                    break;
                case LONG:
                    long lon = Long.parseLong(args[1]);
                    this.args.add(Long.toString(lon));
                    break;
                case WEAPON:
                    Weapon weapon = Weapon.valueOf(args[1]);
                    this.args.add(weapon.name());
                    break;
                case STRING:
                    this.args.add(args[1]);
                    break;
                default:
                    throw new WrongValuesOfCommandArgumentException("Неподдерживаемый примитив");
            }

        }catch (IllegalArgumentException e) {
            throw new WrongValuesOfCommandArgumentException("Введен аргумент несоответствующего типа");
        }
    }
    public ArrayList<SpaceMarine> getObjectArray(){
        return this.objectArray;
    }
    public void addObject(SpaceMarine marine){
        this.objectArray.add(marine);
    }
    public String getName(){
        return this.name;
    }
    public ArrayList<String> getArgs(){
        return this.args;
    }
}

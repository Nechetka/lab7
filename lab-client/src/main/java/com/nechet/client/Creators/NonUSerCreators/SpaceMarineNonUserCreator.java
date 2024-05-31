package com.nechet.client.Creators.NonUSerCreators;

import com.nechet.common.util.exceptions.CreateObjectException;
import com.nechet.common.util.model.AstartesCategory;
import com.nechet.common.util.model.Coordinates;
import com.nechet.common.util.model.Creators.BaseObjectUserCreator;
import com.nechet.common.util.model.SpaceMarine;
import com.nechet.common.util.model.Weapon;
import com.nechet.common.util.model.checkers.Checked;
import com.nechet.common.util.model.checkers.MarineHealthChecker;
import com.nechet.common.util.model.checkers.NameChecker;
import com.nechet.client.system.UserConsole;

import java.time.Instant;
import java.util.Date;
import java.util.Scanner;

public class SpaceMarineNonUserCreator implements BaseObjectUserCreator<SpaceMarine> {
    private Date initDate;
    private final Scanner out;
    public SpaceMarineNonUserCreator (Scanner scanner){
        out = scanner;
    }
    @Override
    public SpaceMarine create() throws CreateObjectException {
        try {
            SpaceMarine marine = new SpaceMarine();
            initDate = Date.from(Instant.now());
            marine.setCreationDate(initDate);
            UserConsole console = new UserConsole(out);
            String line = console.read();
            Checked<String> nameCheck = new NameChecker();
            if (nameCheck.check(line)) {
                marine.setName(line);
            } else {
                throw new CreateObjectException("Значение строки должно быть не пустым и не null.");
            }
            BaseObjectUserCreator<Coordinates> cordCreator = new CoordinatesNonUserCreator(out);
            marine.setCoordinates(cordCreator.create());
            Checked<Double> healthCheck = new MarineHealthChecker();
            line = console.read();
            double value = Double.parseDouble(line);
            if (healthCheck.check(value)) {
                marine.setHealth(value);
            } else {
                throw new CreateObjectException("Значение числа должно быть >0.");
            }
            line = console.read();
            boolean loyal = Boolean.getBoolean(line);
            marine.setLoyal(loyal);
            line = console.read();
            AstartesCategory cat = AstartesCategory.valueOf(line);
            marine.setCategory(cat);
            line = console.read();
            Weapon weapon = Weapon.valueOf(line);
            marine.setWeaponType(weapon);
            ChapterNonUserCreator chapterCreator = new ChapterNonUserCreator(out);
            marine.setChapter(chapterCreator.create());
            console.printLine("Обьект SpaceMarine успешно создан!");
            return marine;
        } catch (Exception e) {
            throw new CreateObjectException(e.getMessage());
        }
    }
}

package com.nechet.client.Creators.NonUSerCreators;

import com.nechet.common.util.exceptions.CreateObjectException;
import com.nechet.common.util.model.Coordinates;
import com.nechet.common.util.model.Creators.BaseObjectUserCreator;
import com.nechet.common.util.model.checkers.Checked;
import com.nechet.common.util.model.checkers.CoordinateYChecker;
import com.nechet.client.system.UserConsole;

import java.util.Scanner;

public class CoordinatesNonUserCreator implements BaseObjectUserCreator<Coordinates> {
    private final Scanner out;

    public CoordinatesNonUserCreator(Scanner scanner) {
        out = scanner;
    }

    @Override
    public Coordinates create() throws CreateObjectException {
        Coordinates coordinates = new Coordinates();
        UserConsole console = new UserConsole(out);
        try {
            String line = console.read();
            double x = Double.parseDouble(line);
            coordinates.setX(x);
            Checked<Integer> Ycheck = new CoordinateYChecker();
            line = console.read();
            int y = Integer.parseInt(line);
            if (Ycheck.check(y)) {
                coordinates.setY(y);
            } else {
                throw new CreateObjectException("Значение числа должно быть <58.");
            }
        } catch (Exception e) {
            throw new CreateObjectException(e.getMessage());
        }
        return coordinates;
    }
}
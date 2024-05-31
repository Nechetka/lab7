package com.nechet.common.util.model.comparators;

import com.nechet.common.util.model.SpaceMarine;

import java.util.Comparator;

public class MarineHealthComparator implements Comparator<SpaceMarine> {
    @Override
    public int compare(SpaceMarine o1, SpaceMarine o2) {
        return Double.compare(o1.getHealth(), o2.getHealth());
    }
}

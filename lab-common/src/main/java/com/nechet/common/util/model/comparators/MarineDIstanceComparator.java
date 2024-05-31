package com.nechet.common.util.model.comparators;

import com.nechet.common.util.model.SpaceMarine;

import java.util.Comparator;

public class MarineDIstanceComparator implements Comparator<SpaceMarine> {
    @Override
    public int compare(SpaceMarine o1, SpaceMarine o2) {
        return (o1.getCoordinates().getY()^2+o1.getCoordinates().getX().intValue()^2)-
                (o2.getCoordinates().getY()^2+o2.getCoordinates().getX().intValue()^2);
    }
}
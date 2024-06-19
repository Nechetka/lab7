package com.nechet.server.system;

import com.nechet.common.util.model.SpaceMarine;
import com.nechet.common.util.model.comparators.MarineDIstanceComparator;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;

public class SpaceMarinesManager implements CollectionReceiver<TreeSet<SpaceMarine>, SpaceMarine> {
    static private SpaceMarinesManager collection;
    public static  SpaceMarinesManager getInstance(){
        if (collection == null){
            collection = new SpaceMarinesManager();
        }
        return collection;
    }
    private CopyOnWriteArraySet<SpaceMarine> marines;
    private final Date initDate;

    private SpaceMarinesManager() {
        marines = new CopyOnWriteArraySet<>();
        initDate = Date.from(Instant.now());
    }

    @Override
    public TreeSet<SpaceMarine> getCollection() {
        return new TreeSet<>(marines);
    }

    @Override
    public void setCollection(TreeSet<SpaceMarine> value) {
        this.marines = new CopyOnWriteArraySet<>(value);
    }

    @Override
    public void addElementToCollection(SpaceMarine value) {
        this.marines.add(value);
        baseSort();
    }

    @Override
    public void clearCollection() {
        this.marines.clear();
    }

    @Override
    public void baseSort() {
        CopyOnWriteArraySet<SpaceMarine> sortedMarines = new CopyOnWriteArraySet<>();

        for (Iterator<SpaceMarine> obj = marines.stream().sorted(new MarineDIstanceComparator()).iterator(); obj.hasNext(); ) {
            SpaceMarine sortedItem = obj.next();

            sortedMarines.add(sortedItem);
        }

        this.marines = sortedMarines;
    }


    @Override
    public Date getInitDate() {
        return initDate;
    }

    public int getSize(){
        return marines.size();
    }
    public  SpaceMarine getMinElement(Comparator<SpaceMarine> comparator){
        return getCollection().stream().min(comparator).orElse(null);
    }
    public SpaceMarine getMaxElement(Comparator<SpaceMarine> comparator){
        return getCollection().stream().max(comparator).orElse(null);
    }

    public boolean removeIf(Predicate<? super SpaceMarine> predicate) {
        return marines.removeIf(predicate);
    }

    @Override
    public boolean isEmpty() {
        return marines.isEmpty();
    }
}

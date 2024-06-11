package com.nechet.server.system;


import java.util.AbstractCollection;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Predicate;

public interface CollectionReceiver<T extends AbstractCollection<E>, E> {
    T getCollection();
    void setCollection(T value);
    void addElementToCollection(E value);
    void clearCollection();
    int getSize();
    void baseSort();
    Date getInitDate();
    E getMaxElement(Comparator<E> comp);
    E getMinElement(Comparator<E> comp);
    boolean removeIf (Predicate<? super E> predicate);
    boolean isEmpty();
}
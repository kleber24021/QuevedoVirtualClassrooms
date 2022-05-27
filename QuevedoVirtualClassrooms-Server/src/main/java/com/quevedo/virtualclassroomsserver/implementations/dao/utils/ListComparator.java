package com.quevedo.virtualclassroomsserver.implementations.dao.utils;

import java.util.List;
import java.util.stream.Collectors;

public class ListComparator<T>{
    public List<T> findDifferencesBetweenLists(List<T> reference, List<T> other){
        return reference.stream()
                .filter(element -> !other.contains(element))
                .collect(Collectors.toList());
    }
}

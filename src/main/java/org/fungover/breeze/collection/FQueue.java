package org.fungover.breeze.collection;

import java.util.Collections;
import java.util.List;

public class FQueue<T> {
    private final List<T> front;
    private final List<T> back;

    public FQueue(List<T> front, List<T> back) {
        this.front = front;
        this.back = back;
    }

    public static <T> FQueue<T> empty(){
        return new FQueue<>(Collections.emptyList(),Collections.emptyList());

    }


}

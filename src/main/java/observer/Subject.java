package observer;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by chn on 16/4/27.
 */
public class Subject {

    List<Observer> observers = Lists.newLinkedList();

    public void notifyObservers() {
        if(observers!=null) {
            for (Observer clockObserver : observers) {
                clockObserver.update();
            }
        }
    }

    public Subject registerObserver(Observer o) {
        observers.add(o);
        return this;
    }

//    public void update() {
//
//    }
}

package fsm;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by chn on 16/5/11.
 *
 * A Composite Condition, consist of many joint conditions.
 */
public class Conditions implements Condition {

    List<Condition> conditions = Lists.newLinkedList();

    public Conditions add(Condition c) {
        conditions.add(c);
        return this;
    }

    public boolean test() {
        for(Condition c: conditions) {
            if(!c.test()) {
                return false;
            }
        }
        return true;
    }
}

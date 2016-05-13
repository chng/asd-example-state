package fsm;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by chn on 16/5/11.
 */
public class FSMTable {

    private STATE state;
    private Map<STATE, Map<EVENT, List<Transaction>>> transactions = Maps.newHashMap();

    public FSMTable(STATE init) {
        state = init;
    }

    public STATE getState() {
        return state;
    }

    Transaction newTrans;
    public FSMTable from(STATE from) {
        newTrans = new Transaction();
        newTrans.curState = from;
        return this;
    }
    public FSMTable occur(EVENT event) {
        newTrans.event = event;
        return this;
    }
    public FSMTable when(Condition condition) {
        newTrans.condition = condition;
        return this;
    }
    public FSMTable whenNOT(Condition condition) {
        newTrans.condition = not(condition);
        return this;
    }
    /**
     * 求Condition的反
     */
    public Condition not(final Condition c) {
        return new Condition() {
            public boolean test() {
                if(c==null) {
                    return false;
                }
                return !c.test();
            }
        };
    }

    public FSMTable action(Action a) {
        newTrans.action = a;
        return this;
    }
    public void to(STATE to) {
        newTrans.newState = to;
        save();
    }
    private void save() {
        getTransactions(newTrans.curState, newTrans.event).add(newTrans);
    }
    private List<Transaction> getTransactions(STATE curState, EVENT event) {
        Map fromCurState = transactions.get(curState);
        if(null == fromCurState) {
            fromCurState = Maps.newHashMap();
            fromCurState.put(event, Lists.newLinkedList());
            transactions.put(curState, fromCurState);
        }
        if(null == fromCurState.get(event)) {
            fromCurState.put(event, Lists.newLinkedList());
        }
        return (List<Transaction>) fromCurState.get(event);
    }

    /**
     * do something when event occurs
     * @param event
     */
    public void fire(EVENT event) {
        try {

            // 在state下,发生event时, 在不需要条件或条件为真时, 执行action并跳到newState, 等待下一个事件
            Map<EVENT, List<Transaction>> curState = transactions.get(state);
            if(curState == null) {
                // error log
                System.out.println("STATE "+state+" is not defined");
                return;
            }
            List<Transaction> curEvent = curState.get(event);
            if (curEvent == null) {
                System.out.println("EVENT "+event+" is not defined in STATE "+state);
                return;
            }

            List<Transaction> ts = this.transactions.get(state).get(event);
            if(ts == null) {
                System.out.println("STATE "+state+" EVENT "+event+" has no transaction");
                return;
            }
            for(Transaction t: ts) {
                if(t.condition == null || t.condition.test()) {
                    if(t.action!=null) {
                        t.action.exec();
                    }
                    state = t.newState;
                    break;
                }
            }
            System.out.println("state = " + state);
        }catch (Exception e) {
            e.printStackTrace(); // Action的异常由FSM来处理.
        }
    }


    /**
     *  Transaction
     */
    private class Transaction {
        private STATE curState;
        private EVENT event;
        private Condition condition;
        private Action action;
        private STATE newState;

        @Override
        public boolean equals(Object obj) {
            Transaction t = (Transaction) obj;
            return t.curState == curState
                    && t.event == event;
        }

        @Override
        public int hashCode() {
            return curState.hashCode() ^ event.hashCode();
        }
    }

}

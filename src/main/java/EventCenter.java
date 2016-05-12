import com.google.common.collect.Maps;
import fsm.*;
import observer.Observer;
import observer.Subject;

import java.util.Map;

/**
 * Created by chn on 16/5/11.
 */
public class EventCenter {

    FSMTable fsmTable = new FSMTable(STATE.INIT);

    Map<STATE, Subject> observers = Maps.newHashMap();

    Observer autoRun = new Observer() {
        public void update() {
            fsmTable.fire(EVENT.AUTO);
            observers.get(fsmTable.getState()).notifyObservers();
        }
    };
    private Condition canLog;

    public EventCenter() {
        initializeFSM();
        initializeObservers();
    }

    private void initializeFSM() {
        //初始化fsmTable
        // 正常流程
        fsmTable.from(STATE.INIT).occur(EVENT.RECVED).when(null).action(doResolve).to(STATE.AFTER_RESOLVE);

        fsmTable.from(STATE.AFTER_RESOLVE).occur(EVENT.AUTO).when(canStore).action(doStore).to(STATE.AFTER_STORE);
        fsmTable.from(STATE.AFTER_RESOLVE).occur(EVENT.AUTO).whenNOT(canStore).action(null).to(STATE.SEND);

        fsmTable.from(STATE.AFTER_STORE).occur(EVENT.AUTO).when(null).action(null).to(STATE.SEND);

        fsmTable.from(STATE.SEND).occur(EVENT.AUTO).when(canSend).action(doSend).to(STATE.AFTER_SEND);
        fsmTable.from(STATE.SEND).occur(EVENT.AUTO).when(canLog).action(doLog).to(STATE.LOG);
        fsmTable.from(STATE.SEND).occur(EVENT.AUTO).whenNOT(canLog).action(null).to(STATE.LOG);

        fsmTable.from(STATE.AFTER_SEND).occur(EVENT.AUTO).when(canLog).action(doLog).to(STATE.LOG);
        fsmTable.from(STATE.AFTER_SEND).occur(EVENT.AUTO).whenNOT(canLog).action(null).to(STATE.LOG);

        // ...
    }

    private void initializeObservers() {
        // 收到消息时, 启动状态机, 按照观察者模式扭转
        observers.put(STATE.INIT,
                new Subject().registerObserver(new Observer() {
                    public void update() {
                        fsmTable.fire(EVENT.RECVED);
                        observers.get(STATE.AFTER_RESOLVE).notifyObservers();
                    }
                }));
        observers.put(STATE.AFTER_RESOLVE, new Subject().registerObserver(autoRun));
        observers.put(STATE.AFTER_STORE, new Subject().registerObserver(autoRun));
        observers.put(STATE.SEND, new Subject().registerObserver(autoRun));
        observers.put(STATE.AFTER_SEND, new Subject().registerObserver(autoRun));
        observers.put(STATE.LOG, new Subject());
    }

    public void start() {
        System.out.println("state = " + STATE.INIT);
        observers.get(STATE.INIT).notifyObservers();
    }


    /**
     * 职能方法, 后续应当移动到相应的职能类中
     */
    MessageProcessor messageProcessor = new MessageProcessor(); // new违反DIP原则, 换成Spring注入
    LogWriter logWriter = new LogWriter();

    Action doResolve = new Action() {
        public void exec() {
            messageProcessor.resolve();
        }
    };

    Condition canStore = new Conditions()
            .add(new Condition() {
                public boolean test() {
                    return !Configs.degradeStoreMessage;
                }
            })
            .add(new Condition() {
                public boolean test() {
                    return Message.isCompleted(Context.msg);
                }
            });

    Action doStore = new Action() {
        public void exec() {
            messageProcessor.store();
        }
    };

    Condition canSend = new Conditions()
            .add(new Condition() {
                public boolean test() {
                    return !Configs.degradeSendMessage;
                }
            })
            .add(new Condition() {
                public boolean test() {
                    return Message.isCompleted(Context.msg);
                }
            });

    Action doSend = new Action() {
        public void exec() {
            messageProcessor.send();
        }
    };

    Action doLog = new Action() {
        public void exec() {
            logWriter.write();
        }
    };

}

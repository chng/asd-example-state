import org.junit.Test;

/**
 * Created by chn on 16/5/11.
 */
public class TestEventDriver {

    @Test
    public void test () {

        // 初始化状态迁移表

        // 每次有时间触发时, 调用状态迁移表.
        // 一系列的事件的触发, 会连续调用这个状态迁移表.
        EventDriver eventDriver = new EventDriver();
        // 事件中心根据这一系列的事件的触发过程, 初始化状态迁移表.
        // 根据复杂的业务逻辑, 状态的迁移也是复杂的.
        // 例如, 当某个降级开关打开时, 状态也许在某时直接迁移到LOG或END. 这取决于调用addTransaction添加了何种迁移规则
        // addTransaction(, event.resolved, new fsm.Action(){}, end);

//        fsmTable.event(EVENT.RECVED);
//        // resolve()
//        fsmTable.event(EVENT.NOTIFY_RESOLVED);
//        // store()
//        fsmTable.event(EVENT.NOTIFY_STORED);
//        // send()
//        fsmTable.event(EVENT.NOTIFY_SENDED);
//        // log
//        fsmTable.event(EVENT.NOTIFY_LOGED);
//        // end

        eventDriver.start();

    }
}

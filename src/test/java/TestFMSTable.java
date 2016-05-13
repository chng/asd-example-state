import fsm.EVENT;
import fsm.FSMTable;
import fsm.STATE;
import org.junit.Test;

/**
 * Created by chn on 16/5/11.
 */
public class TestFMSTable {

    @Test
    public void test () {

        // 初始化状态迁移表
        FSMTable fsmTable = new FSMTable(STATE.INIT);
        fsmTable.from(STATE.INIT).occur(EVENT.RECVED).when(null).action(()->System.out.println("resolving")).to(STATE.END);

        System.out.println("state = " + STATE.INIT);
        fsmTable.fire(EVENT.RECVED);
//        // resolve()
//        fsmTable.fire(EVENT.NOTIFY_RESOLVED);
//        // store()
//        fsmTable.fire(EVENT.NOTIFY_STORED);
//        // send()
//        fsmTable.fire(EVENT.NOTIFY_SENDED);
//        // log
//        fsmTable.fire(EVENT.NOTIFY_LOGED);
        // end

    }
}

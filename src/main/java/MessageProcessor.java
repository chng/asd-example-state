import com.alibaba.fastjson.JSON;

/**
 * Created by chn on 16/5/12.
 */
public class MessageProcessor {

    public void resolve() {
        System.out.println("resolving");
        // 模拟成功
        Context.msg = new Message("12345", "hello everyone");
        // 模拟失败
        //Context.msg = null;
    }

    public void store() {
        System.out.println("storing");
        // 模拟成功
        Context.msg.setStored(true);
        // 模拟失败
        //Context.msg.setStored(false);
    }

    public void send() {
        // 模拟发送
        System.out.println("sending");
        Context.msg.setSended(true);
    }
}

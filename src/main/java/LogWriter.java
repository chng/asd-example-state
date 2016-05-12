import com.alibaba.fastjson.JSON;

/**
 * Created by chn on 16/5/12.
 */
public class LogWriter {
    public void write() {
        // 模拟写日志
        System.out.println("log: "+ JSON.toJSONString(Context.msg));
    }
}

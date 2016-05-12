/**
 * Created by chn on 16/5/11.
 */
public class Message {
    String body;
    String recvId;

    boolean stored;
    boolean sended;

    public static boolean isCompleted(Message m) {
        return m != null
                && m.recvId != null && !m.recvId.isEmpty()
                && m.body != null && !m.body.isEmpty();
    }

    public Message(String recvId, String body) {
        this.recvId = recvId;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public String getRecvId() {
        return recvId;
    }

    public boolean isSended() {
        return sended;
    }

    public void setSended(boolean sended) {
        this.sended = sended;
    }

    public boolean isStored() {
        return stored;
    }

    public void setStored(boolean stored) {
        this.stored = stored;
    }
}


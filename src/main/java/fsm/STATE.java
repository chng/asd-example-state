package fsm;

public enum STATE {
    INIT,
    AFTER_RESOLVE,
    RESOLVED,
    AFTER_STORE,
    STORED,
    SENDED,
    LOG,
    NOTIFY_LOGED,
    END, SEND, AFTER_SEND,
}

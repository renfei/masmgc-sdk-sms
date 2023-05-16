package net.renfei.mascloud.util;

import org.apache.commons.lang3.StringUtils;

/**
 * com.ejtone.mars.kernel.core.fault.Err
 *
 * @author renfei
 */
public class Err {
    public static final Err IllegalMsgfmt = new Err("IllegalMsgfmt");
    public static final Err InternalError = new Err("InternalError");
    public static final Err SystemBusy = new Err("SystemBusy");
    public static final Err IllegalState = new Err("IllegalState");
    public static final Err InvalidMessage = new Err("InvalidMessage");
    public static final Err InvalidUsrOrPwd = new Err("InvalidUsrOrPwd");
    public static final Err IllegalIpAddress = new Err("IllegalIpAddress");
    public static final Err RequestTimeout = new Err("RequestTimeout");
    public static final Err TransportError = new Err("TransportError");
    public static final Err QueueOverLimit = new Err("QueueOverLimit");
    public static final Err FlowOverLimit = new Err("FlowOverLimit");
    public static final Err IllegalProtocol = new Err("IllegalProtocol");
    public static final Err IllegalUseTemplate = new Err("IllegalUseTemplate");
    public static final Err OverDailyMax = new Err("OverDailyMax");
    public static final Err NotLoadedSign = new Err("NotLoadedSign");
    public static final Err FastChNotFound = new Err("FastChNotFound");
    public static final Err NotFindMimeData = new Err("NotFindMimeData");
    public static final Err InvalidMmsContent = new Err("InvalidMmsContent");
    public static final Err RequestTooOften = new Err("RequestTooOften");
    private String reason;
    private String msg;

    public Err(String reason) {
        this(reason, (String) null);
    }

    public Err(String reason, String msg) {
        this.reason = reason;
        this.msg = msg;
    }

    public String getReason() {
        return this.reason;
    }

    public String getMsg() {
        return this.msg;
    }

    public Err newInstance(String msg) {
        return new Err(this.reason, msg);
    }

    public Fault makeFault() {
        return new Fault(this);
    }

    public Fault makeFault(String message) {
        return new Fault(this.newInstance(message));
    }

    public Fault makeFault(Throwable cause) {
        return StringUtils.isBlank(this.msg) && !StringUtils.isBlank(cause.getMessage()) ? new Fault(this.newInstance(cause.getMessage()), cause) : new Fault(this, cause);
    }

    public boolean becauseOf(String... reasons) {
        String[] var2 = reasons;
        int var3 = reasons.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String c = var2[var4];
            if (this.reason.equals(c)) {
                return true;
            }
        }

        return false;
    }

    public boolean becauseOf(Err... reasons) {
        Err[] var2 = reasons;
        int var3 = reasons.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Err c = var2[var4];
            if (this.reason.equals(c.getReason())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Err{" +
                "reason='" + reason + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}

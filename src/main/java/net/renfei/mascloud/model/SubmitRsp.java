package net.renfei.mascloud.model;

/**
 * @author renfei
 */
public class SubmitRsp {
    private boolean success;
    private String rspcod;
    private String msgId;

    public SubmitRsp(boolean success, String rspcod) {
        this.success = success;
        this.rspcod = rspcod;
    }

    public SubmitRsp(boolean success, String rspcod, String msgId) {
        this.success = success;
        this.rspcod = rspcod;
        this.msgId = msgId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRspcod() {
        return rspcod;
    }

    public void setRspcod(String rspcod) {
        this.rspcod = rspcod;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @Override
    public String toString() {
        return "SubmitRsp{" +
                "success=" + success +
                ", rspcod='" + rspcod + '\'' +
                ", msgId='" + msgId + '\'' +
                '}';
    }
}

package net.renfei.mascloud.model;

/**
 * @author renfei
 */
public class MoModel {
    private String mobile;
    private String smsContent;
    private String sendTime;
    private String addSerial;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getAddSerial() {
        return addSerial;
    }

    public void setAddSerial(String addSerial) {
        this.addSerial = addSerial;
    }
}

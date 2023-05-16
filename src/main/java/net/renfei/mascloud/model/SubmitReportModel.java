package net.renfei.mascloud.model;

/**
 * @author renfei
 */
public class SubmitReportModel {
    private String reportStatus;
    private String[] mobiles;
    private String submitDate;
    private String receiveDate;
    private String errorCode;
    private String msgGroup;

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String[] getMobiles() {
        return mobiles;
    }

    public void setMobiles(String[] mobiles) {
        this.mobiles = mobiles;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsgGroup() {
        return msgGroup;
    }

    public void setMsgGroup(String msgGroup) {
        this.msgGroup = msgGroup;
    }
}

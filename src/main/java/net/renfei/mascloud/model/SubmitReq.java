package net.renfei.mascloud.model;

import java.util.List;

/**
 * @author renfei
 */
public class SubmitReq {
    private String apId;
    private String apReqId;
    private List<String> mobiles;
    private String content;
    private String srcId;
    private String serviceId;
    private boolean regReport;
    private String templateId;
    private String ecName;
    private String mac;

    public String getApId() {
        return apId;
    }

    public void setApId(String apId) {
        this.apId = apId;
    }

    public String getApReqId() {
        return apReqId;
    }

    public void setApReqId(String apReqId) {
        this.apReqId = apReqId;
    }

    public List<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public boolean isRegReport() {
        return regReport;
    }

    public void setRegReport(boolean regReport) {
        this.regReport = regReport;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getEcName() {
        return ecName;
    }

    public void setEcName(String ecName) {
        this.ecName = ecName;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}

package net.renfei.mascloud.model;

/**
 * @author renfei
 */
public class LoginReq {
    private String apId;
    private String secretKey;
    private String ecName;
    private String version = "2.0.0";

    public String getApId() {
        return apId;
    }

    public void setApId(String apId) {
        this.apId = apId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getEcName() {
        return ecName;
    }

    public void setEcName(String ecName) {
        this.ecName = ecName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "LoginReq{" +
                "apId='" + apId + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", ecName='" + ecName + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}

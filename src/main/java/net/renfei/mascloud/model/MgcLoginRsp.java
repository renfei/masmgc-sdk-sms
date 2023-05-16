package net.renfei.mascloud.model;

/**
 * @author renfei
 */
public class MgcLoginRsp {
    private boolean success;
    private String rspcod;
    private String sysflag;
    private String url;

    public MgcLoginRsp() {
    }

    public MgcLoginRsp(boolean success, String rspcod) {
        this.success = success;
        this.rspcod = rspcod;
    }

    public MgcLoginRsp(boolean success, String sysflag, String url) {
        this.success = success;
        this.sysflag = sysflag;
        this.url = url;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRspcod() {
        return this.rspcod;
    }

    public void setRspcod(String rspcod) {
        this.rspcod = rspcod;
    }

    public String getSysflag() {
        return this.sysflag;
    }

    public void setSysflag(String sysflag) {
        this.sysflag = sysflag;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "MgcLoginRsp{" +
                "success=" + success +
                ", rspcod='" + rspcod + '\'' +
                ", sysflag='" + sysflag + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

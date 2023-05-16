package net.renfei.mascloud.sdkclient;

import net.renfei.mascloud.model.*;
import net.renfei.mascloud.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author renfei
 */
public class Client {
    private final AtomicBoolean logined;
    private String sendUrl;
    int num;
    private String apId;
    private String apSecretKey;
    private String ecName;
    private int poolSize;
    private int maxJobs;
    private final ThreadPoolExecutor nThreadPool;

    private Client() {
        this.logined = new AtomicBoolean(false);
        this.num = 0;
        this.poolSize = 16;
        this.maxJobs = 8192;
        this.nThreadPool = new ThreadPoolExecutor(this.poolSize, this.poolSize, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue(this.maxJobs), new NamedThreadFactory("thread-sendDsms"));
    }

    public static Client getInstance() {
        return Client.InstanceHolder.instance;
    }

    public String getSendUrl() {
        return this.sendUrl;
    }

    public void setSendUrl(String sendUrl) {
        this.sendUrl = sendUrl;
    }

    public boolean login(String url, String userAccount, String password, String ecname) {
        synchronized (this) {
            if (this.logined.get()) {
                System.err.println("user lgoined already");
                return true;
            }
        }

        LoginReq req = new LoginReq();
        req.setApId(userAccount);
        req.setSecretKey("");
        req.setEcName(ecname);

        try {
            MgcLoginRsp rsp = this.doLogin(url, req);
            if (rsp.isSuccess()) {
                this.sendUrl = rsp.getUrl();
                this.apId = userAccount;
                this.apSecretKey = password;
                this.ecName = ecname;
                this.logined.set(true);
            } else {
                System.out.println("登录失败：" + JsonUtil.toJsonString(rsp));
            }
        } catch (Exception var7) {
            System.out.println("登录异常:" + var7);
            var7.printStackTrace();
        }

        return this.logined.get();
    }

    private MgcLoginRsp doLogin(String baseUrl, LoginReq req) throws Exception {
        String url = MixUtil.mergeUrl(baseUrl, "/sms/login");
        CloseableHttpResponse resp = null;

        MgcLoginRsp var8;
        try {
            String reqText = JsonUtil.toJsonString(req);
            String encode = Base64.encodeBase64String(reqText.getBytes(StandardCharsets.UTF_8));
            System.out.println("loginreq\t" + reqText);
            resp = HttpUtil.post(url, encode, ContentType.APPLICATION_JSON);
            if (resp.getStatusLine().getStatusCode() != 200) {
                System.err.println("login to " + url + " exception, code = " + resp.getStatusLine().getStatusCode());
                throw new RuntimeException("login exception");
            }

            String respText = HttpUtil.readHttpResponse(resp);
            if (StringUtils.isBlank(respText)) {
                System.err.println("not found loginrsp data");
                throw new RuntimeException("login exception: not found loginrsp data");
            }

            System.out.println("loginrsp\t" + respText);
            var8 = JsonUtil.fromJsonString(respText, MgcLoginRsp.class);
        } catch (Exception var12) {
            System.err.println("login to " + url + " exception, error = " + var12);
            throw var12;
        } finally {
            HttpUtil.safeClose(resp);
        }

        return var8;
    }

    /**
     * 短信发送
     *
     * @param mobiles     手机号码数组，允许群发信息，该字符串数组中的每个字符串代表一个手机号码。群发短信单批最大号码数组为每批5000条。
     * @param smsContent  发送短信内容
     * @param addSerial   扩展码，根据向移动公司申请的通道填写，如果申请的精确匹配通道，则填写空字符串("")，否则添加移动公司允许的扩展码
     * @param smsPriority 短信优先级，取值1-5，填其余值，系统默认选择1, 1最低，5最高
     * @param sign        网关签名编码，必填，签名编码在中国移动集团开通帐号后分配，可以在云MAS网页端管理子系统-SMS接口管理功能中下载。
     * @param msgGroup    发送数据批次号，32位世界上唯一编码，由字母和数字组成。用户可以采用自定义的数据批次产生算法，标定每次下发的数据的批号。 如果不填写该参数，SDK为满足发送服务器的管理需要，会自动生成一个批次号，但是客户端取状态报告时无法分辨短信的状态报告批次。 建议填写
     * @param isMo        是否需要上行，True代表需要；false代表不需要。目前云MAS平台默认推送上行
     * @return
     */
    public int sendDSMS(String[] mobiles, String smsContent, String addSerial, int smsPriority, String sign, String msgGroup, boolean isMo) {
        if (!this.logined.get()) {
            return 105;
        } else if (mobiles == null) {
            return 102;
        } else if (mobiles.length == 0) {
            return 103;
        } else if (mobiles.length > 5000) {
            return 110;
        } else {
            int var10 = mobiles.length;

            Set<String> mbSet = new HashSet<>(Arrays.asList(mobiles).subList(0, var10));

            if (mbSet.size() != mobiles.length) {
                return 109;
            } else if (StringUtils.isBlank(smsContent)) {
                return 101;
            } else if (StringUtils.isBlank(sign)) {
                return 106;
            } else {
                SubmitReq submit = new SubmitReq();
                submit.setApId(this.apId);
                submit.setApReqId(msgGroup != null ? msgGroup : MixUtil.uuid());
                if (StringUtils.isBlank(addSerial)) {
                    addSerial = "";
                }

                if (StringUtils.isBlank(this.apSecretKey)) {
                    this.apSecretKey = "";
                }

                submit.setMac(MD5Util.md5Encode(this.apId + this.apSecretKey + this.ecName + smsContent + sign + addSerial));
                submit.setContent(smsContent);
                submit.setMobiles(new ArrayList(mbSet));
                submit.setSrcId(addSerial);
                submit.setServiceId(sign);
                submit.setRegReport(isMo);
                submit.setEcName(this.ecName);
                Runnable task = null;

                try {
                    new SubmitTask(submit, mobiles, this.sendUrl);
                    this.nThreadPool.execute(new SubmitTask(submit, mobiles, this.sendUrl));
                    return 1;
                } catch (RejectedExecutionException var13) {
                    ((Runnable) task).run();
                    return 112;
                }
            }
        }
    }

    /**
     * 发送模板短信
     *
     * @param mobiles     手机号码数组，允许群发信息，该字符串数组中的每个字符串代表一个手机号码。群发短信单批最大号码数组为每批5000条。
     * @param tempID      模版ID，模板由用户在中国移动集团提供的客户业务平台上，由客户自己增加短信模版的信息。
     * @param params      模版参数，字符串数组。 模板采用模板和参数合成的方式产生短信， 短信内容由发送服务器自动拼接。模板参数必须和模板中定义的动态填写的参数的个数一致。
     * @param addSerial   扩展码，根据向移动公司申请的通道填写，如果申请的精确匹配通道，则填写空字符串("")，否则添加移动公司允许的扩展码
     * @param smsPriority 短信优先级，取值1-5，填其余值，系统默认选择1 优先级1为最低，5为最高
     * @param sign        网关签名编码，必填，签名编码由企业在中国移动集团开通帐号分配
     * @param msgGroup    发送数据批次号，32位世界上唯一编码，由字母和数字组成。用户可以采用自定义的数据批次产生算法，标定每次下发的数据的批号。 如果不填写该参数，SDK为满足发送服务器的管理需要，会自动生成一个批次号，但是客户端取状态报告时无法分辨短信的状态报告批次。 建议填写
     * @return
     */
    public int sendTSMS(String[] mobiles, String tempID, String[] params, String addSerial, int smsPriority, String sign, String msgGroup) {
        if (!this.logined.get()) {
            return 105;
        } else if (mobiles == null) {
            return 102;
        } else if (mobiles.length == 0) {
            return 103;
        } else if (mobiles.length > 5000) {
            return 110;
        } else {
            int var10 = mobiles.length;

            Set<String> mbSet = new HashSet<>(Arrays.asList(mobiles).subList(0, var10));

            if (mbSet.size() != mobiles.length) {
                return 109;
            } else if (params != null && params.length != 0) {
                if (StringUtils.isBlank(sign)) {
                    return 106;
                } else if (StringUtils.isBlank(tempID)) {
                    return 111;
                } else {
                    SubmitReq submit = new SubmitReq();
                    submit.setApId(this.apId);
                    submit.setApReqId(msgGroup != null ? msgGroup : MixUtil.uuid());
                    submit.setMac(MD5Util.md5Encode(this.apId + this.apSecretKey + this.ecName + tempID + sign + addSerial));
                    submit.setContent(JsonUtil.toJsonString(params));
                    submit.setMobiles(new ArrayList(mbSet));
                    submit.setSrcId(addSerial);
                    submit.setServiceId(sign);
                    submit.setRegReport(true);
                    submit.setTemplateId(tempID);
                    submit.setEcName(this.ecName);
                    Runnable task = null;

                    try {
                        new SubmitTask(submit, mobiles, this.sendUrl);
                        this.nThreadPool.execute(new SubmitTask(submit, mobiles, this.sendUrl));
                        return 1;
                    } catch (RejectedExecutionException var13) {
                        task.run();
                        return 112;
                    }
                }
            } else {
                return 101;
            }
        }
    }

    private SubmitRsp doSubmit(String baseUrl, SubmitReq req) throws Exception {
        String url = MixUtil.mergeUrl(baseUrl, "/sms/submit");
        CloseableHttpResponse resp = null;

        SubmitRsp var8;
        try {
            String reqText = JsonUtil.toJsonString(req);
            System.out.println("submitreq\t" + reqText);
            String encode = Base64.encodeBase64String(reqText.getBytes(StandardCharsets.UTF_8));
            resp = HttpUtil.post(url, encode, ContentType.APPLICATION_JSON);
            if (resp.getStatusLine().getStatusCode() != 200) {
                System.err.println("submit to " + url + " exception, code = " + resp.getStatusLine().getStatusCode());
                throw new RuntimeException("submit exception");
            }

            String respText = HttpUtil.readHttpResponse(resp);
            if (StringUtils.isBlank(respText)) {
                System.err.println("not found submitrsp data");
                throw new RuntimeException("submit exception:not found submitrsp data");
            }

            System.out.println("submitrsp\t" + respText);
            var8 = (SubmitRsp) JsonUtil.fromJsonString(respText, SubmitRsp.class);
        } catch (Exception var12) {
            System.err.println("submit to " + url + " exception, error = " + var12.getMessage());
            throw var12;
        } finally {
            HttpUtil.safeClose(resp);
        }

        return var8;
    }

    private String parseSubmitStatus(String rspcod) {
        if (McMgwError.InvalidUsrOrPwd.becauseOf(new String[]{rspcod})) {
            return McRptStatus.INVALID_USR_OR_PWD.getStatus();
        } else if (McMgwError.InvalidMessage.becauseOf(new String[]{rspcod})) {
            return McRptStatus.INVALID_MESSAGE.getStatus();
        } else if (McMgwError.NO_SIGN_ID.becauseOf(new String[]{rspcod})) {
            return McRptStatus.NO_SIGN_ID.getStatus();
        } else if (McMgwError.TOO_MANY_MOBILES.becauseOf(new String[]{rspcod})) {
            return McRptStatus.TOO_MANY_MOBILES.getStatus();
        } else {
            return McMgwError.ILLEGAL_SIGN_ID.becauseOf(new String[]{rspcod}) ? McRptStatus.ILLEGAL_SIGN_ID.getStatus() : "CM:9999";
        }
    }

    public final class SubmitTask implements Runnable {
        private final SubmitReq submit;
        private final String[] mobiles;
        private final String sendUrl;

        public SubmitTask(SubmitReq submit, String[] mobiles, String sendUrl) {
            this.submit = submit;
            this.mobiles = mobiles;
            this.sendUrl = sendUrl;
        }

        public void run() {
            SubmitReportModel model = null;
            SubmitReportModel model2 = null;

            try {
                SubmitRsp rsp = Client.this.doSubmit(this.sendUrl, this.submit);
                model = new SubmitReportModel();
                model.setMobiles(this.mobiles);
                model.setMsgGroup(this.submit.getApReqId());
                model.setReportStatus(rsp.isSuccess() ? "CM:0000" : Client.this.parseSubmitStatus(rsp.getRspcod()));
                model.setSubmitDate(DateTimeUtil.getDate());
                model.setReceiveDate(DateTimeUtil.getDate());
                model.setErrorCode(rsp.getRspcod());
                if (rsp.isSuccess()) {
                    model2 = new SubmitReportModel();
                    model2.setMobiles(this.mobiles);
                    model2.setMsgGroup(this.submit.getApReqId());
                    model2.setReportStatus("CM:3000");
                    model2.setSubmitDate(DateTimeUtil.getDate());
                    model2.setReceiveDate(DateTimeUtil.getDate());
                    model2.setErrorCode(rsp.getRspcod());
                }
            } catch (Exception var4) {
                model = new SubmitReportModel();
                model.setMobiles(this.mobiles);
                model.setMsgGroup(this.submit.getApReqId());
                model.setReportStatus("发送错误");
                model.setSubmitDate(DateTimeUtil.getDate());
                model.setReceiveDate(DateTimeUtil.getDate());
                model.setErrorCode("CM:9999");
            }

        }
    }

    private static final class InstanceHolder {
        public static final Client instance = new Client();

        private InstanceHolder() {
        }
    }
}

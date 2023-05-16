package net.renfei.mascloud.util;

/**
 * @author renfei
 */
public class McMgwError extends Err {
    public static final Err ILLEGAL_AP = new Err("IllegalAp");
    public static final Err ROUTE_NOT_FOUND = new Err("RouteNotFound");
    public static final Err ERR_BLACK_LIST = new Err("ContainBlackList");
    public static final Err ERR_SENSITIVE_WORD = new Err("ContainSensitiveWord");
    public static final Err ERR_LMS_DISABLED = new Err("LMSDisabled");
    public static final Err ILLEGAL_LMS = new Err("IllegalLMS");
    public static final Err EXCEED_MAX_CONTENT_LENGTH = new Err("ExceedMaxContentLength");
    public static final Err NO_SIGN_ID = new Err("NoSignId");
    public static final Err ILLEGAL_SIGN_ID = new Err("IllegalSignId");
    public static final Err CONFLICT_SIGN_ID = new Err("ConflictSignId");
    public static final Err ILLEGAL_SPID = new Err("IllegalSpId");
    public static final Err NO_MOBILE = new Err("NoMobile");
    public static final Err DUP_MOBILE = new Err("DupMobile");
    public static final Err INVALID_MOBILE = new Err("InvalidMobile");
    public static final Err TOO_MANY_MOBILES = new Err("TooManyMobiles");
    public static final Err NO_OT_SIGN = new Err("NoOtSign");
    public static final Err PARAMS_NOT_MATCH = new Err("ParamsAndTemplateDoesNotMatch");
    public static final Err ILLEGAL_TEMPLATE_ID = new Err("IllegalTemplateId");
    public static final Err ERR_MOULD_DAILY_MAX_ZER0 = new Err("dailyMaxNumZero");
    public static final Err ERR_GLOBAL_BLACK_LIST = new Err("ErrGlobalBlackList");
    public static final Err ERR_EC_BLACK_LIST = new Err("ErrEcBlackList");
    public static final Err ERR_MMS_EXECEED_MAX_SIZE = new Err("MmsAttachmentExceedMaxSize");
    public static final Err MESSAGE_EXPIRED = new Err("MessageExpired");
    public static final Err ERR_LABS_SENSITIVE_WORD = new Err("ContainLabsSensitiveWord");
    public static final Err PREPAY_BALANCE_ZERO = new Err("PrepayBalanceZero");
    public static final Err PREPAY_OPER_ERR = new Err("PrepayOperErr");
    public static final Err ERR_SENDER_NOT_STARTED = new Err("SenderNotStarted");
    public static final Err ERR_MMS_CONTENT_NULL = new Err("MmsContentIsNull");

    public McMgwError(String reason) {
        super(reason);
    }
}

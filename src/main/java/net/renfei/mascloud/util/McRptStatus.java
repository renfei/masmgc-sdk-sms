package net.renfei.mascloud.util;

/**
 * @author renfei
 */
public enum McRptStatus {
    ILLEGAL_SIGN_ID("CM:1004", new String[]{McMgwError.ILLEGAL_SIGN_ID.getReason()}),
    ILLEGAL_TEMPLATE_ID("CM:1005", new String[]{McMgwError.ILLEGAL_TEMPLATE_ID.getReason()}),
    ERR_MOULD_DAILY_MAX_ZER0("CM:4010", new String[]{McMgwError.ERR_MOULD_DAILY_MAX_ZER0.getReason()}),
    PARAMS_NOT_MATCH("CM:1006", new String[]{McMgwError.PARAMS_NOT_MATCH.getReason()}),
    ERR_SENSITIVE_WORD("CM:8001", new String[]{McMgwError.ERR_SENSITIVE_WORD.getReason()}),
    ERR_GLOBAL_BLACK_LIST("CM:8003", new String[]{McMgwError.ERR_GLOBAL_BLACK_LIST.getReason()}),
    ERR_EC_BLACK_LIST("CM:8004", new String[]{McMgwError.ERR_EC_BLACK_LIST.getReason()}),
    NO_SIGN_ID("CM:3007", new String[]{McMgwError.NO_SIGN_ID.getReason()}),
    ILLEGAL_AP("CM:4001", new String[]{McMgwError.ILLEGAL_AP.getReason()}),
    LMS_DISABLED("CM:4002", new String[]{McMgwError.ERR_LMS_DISABLED.getReason()}),
    ILLEGAL_LMS("CM:4003", new String[]{McMgwError.ILLEGAL_LMS.getReason()}),
    INVALID_MESSAGE("CM:4004", new String[]{McMgwError.InvalidMessage.getReason()}),
    ROUTE_NOT_FOUND("CM:4005", new String[]{McMgwError.ROUTE_NOT_FOUND.getReason()}),
    NO_OT_SIGN("CM:4006", new String[]{McMgwError.NO_OT_SIGN.getReason()}),
    INVALID_USR_OR_PWD("CM:4007", new String[]{McMgwError.InvalidUsrOrPwd.getReason()}),
    TOO_MANY_MOBILES("CM:4008", new String[]{McMgwError.TOO_MANY_MOBILES.getReason()}),
    CONFLICT_SIGN_ID("CM:4009", new String[]{McMgwError.CONFLICT_SIGN_ID.getReason()}),
    ERR_LABS_SENSITIVE_WORD("CM:8002", new String[]{McMgwError.ERR_LABS_SENSITIVE_WORD.getReason()}),
    PREPAY_BALANCE_ZERO("CM:8005", new String[]{McMgwError.PREPAY_BALANCE_ZERO.getReason()}),
    ERR_SENDER_NOT_STARTED("CM:8006", new String[]{McMgwError.ERR_SENDER_NOT_STARTED.getReason()}),
    PREPAY_OPER_ERR("CM:8007", new String[]{McMgwError.PREPAY_OPER_ERR.getReason()}),
    REQUEST_TIMEOUT("GW:9999", new String[]{McMgwError.RequestTimeout.getReason()});

    public static final String INTERNAL_ERROR = "CM:9999";
    private String status;
    private String[] reasons;

    private McRptStatus(String status, String... reasons) {
        this.status = status;
        this.reasons = reasons;
    }

    public String getStatus() {
        return this.status;
    }

    public String[] getReasons() {
        return this.reasons;
    }

    public static String getStatus(String reason) {
        McRptStatus[] values = values();
        McRptStatus[] var2 = values;
        int var3 = values.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            McRptStatus value = var2[var4];
            String[] reasons = value.getReasons();
            String[] var7 = reasons;
            int var8 = reasons.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                String r = var7[var9];
                if (r.equals(reason)) {
                    return value.getStatus();
                }
            }
        }

        return "CM:9999";
    }
}

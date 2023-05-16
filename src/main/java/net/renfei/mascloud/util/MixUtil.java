package net.renfei.mascloud.util;

import org.apache.commons.lang3.StringUtils;

import java.io.Closeable;
import java.io.File;
import java.lang.reflect.Field;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;

/**
 * @author renfei
 */
public class MixUtil {
    public static final String __LINE_SEPARATOR = System.getProperty("line.separator", "\n");
    public static final String INET_ADDR_ANY = "0.0.0.0";

    public static int roundUpToPowerOf2(int number) {
        return number > 1 ? Integer.highestOneBit(number - 1 << 1) : 1;
    }

    public static String mergeUrl(String baseUrl, String addUrl) {
        baseUrl = StringUtils.stripEnd(baseUrl == null ? "" : baseUrl, "/");
        addUrl = StringUtils.stripStart(addUrl == null ? "" : addUrl, "/");
        return baseUrl + '/' + addUrl;
    }

    public static String fileSuffix(String filename) {
        if (StringUtils.isBlank(filename)) {
            return "";
        } else {
            String[] s = filename.split("\\.");
            return s.length <= 1 ? "" : s[s.length - 1];
        }
    }

    public static String parentPath(String url) {
        if (!StringUtils.isBlank(url) && !url.equals("/")) {
            url = StringUtils.stripEnd(url, "/");
            int i = url.lastIndexOf("/");
            return i != -1 ? url.substring(0, i) + "/" : "/";
        } else {
            return "/";
        }
    }

    public static String replaceUnderlineAndfirstToUpper(String str) {
        if (str != null && str.length() != 0) {
            String[] arr = str.split("_");
            StringBuilder sb = new StringBuilder(str.length());

            for (int i = 0; i < arr.length; ++i) {
                if (arr[i] != null && arr[i].length() != 0) {
                    sb.append(Character.toTitleCase(arr[i].charAt(0))).append(arr[i].substring(1));
                }
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException var3) {
        }

    }

    public static void warnTimeout(long currVal) {
        warnTimeout(currVal, 5000L);
    }

    public static void warnTimeout(long currVal, long warnVal) {
        if (currVal < warnVal) {
            System.out.println("timeout value " + currVal + " < " + warnVal + "ms, make sure it's your want");
        }

    }

    public static void printStackTrace() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        StackTraceElement[] var2 = elements;
        int var3 = elements.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            StackTraceElement element = var2[var4];
            System.out.println(element.toString());
        }

    }

    public static String getStackTrace() {
        StringBuilder sb = new StringBuilder(1024);
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        StackTraceElement[] var2 = elements;
        int var3 = elements.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            StackTraceElement element = var2[var4];
            sb.append(element.toString()).append("\n");
        }

        return sb.toString();
    }

    public static void safeClose(Closeable o) {
        if (o != null) {
            try {
                o.close();
            } catch (Throwable var2) {
                var2.printStackTrace();
            }

        }
    }

    public static final boolean mkdirs(String dir) {
        File f = new File(dir);
        return f.mkdirs();
    }

    public static String listToString(List<?> list) {
        return JsonUtil.toJsonString(list);
    }

    public static String arrayToString(Object[] array) {
        if (array != null && array.length != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");

            for (int i = 0; i < array.length; ++i) {
                if (i != 0) {
                    sb.append(",");
                }

                sb.append(array[i]);
            }

            sb.append("]");
            return sb.toString();
        } else {
            return "[]";
        }
    }

    public static boolean objectEquals(Object obj1, Object obj2) {
        return obj1 == null ? obj2 == null : obj1.equals(obj2);
    }

    public static <T> List<List<T>> comp(List<T> list1, List<T> list2) {
        List<List<T>> result = new ArrayList(2);
        if (list1 != null && list1.size() != 0) {
            if (list2 != null && list2.size() != 0) {
                List<T> listAdd = new ArrayList();
                List<T> listRemoved = new ArrayList(list1);
                Iterator var5 = list2.iterator();

                while (var5.hasNext()) {
                    T t = (T) var5.next();
                    if (!listRemoved.remove(t)) {
                        listAdd.add(t);
                    }
                }

                result.add(listAdd);
                result.add(listRemoved);
                return result;
            } else {
                result.add(new ArrayList());
                result.add(new ArrayList(list1));
                return result;
            }
        } else {
            result.add(new ArrayList(list2));
            result.add(new ArrayList());
            return result;
        }
    }

    public static boolean beanEquals(Object obj1, Object obj2) {
        if ((null != obj1 || null == obj2) && (null == obj1 || null != obj2)) {
            if (null == obj1 && null == obj2) {
                return true;
            } else if (obj1.equals(obj2)) {
                return true;
            } else {
                Field[] fieldArr1 = obj1.getClass().getDeclaredFields();
                Field[] fieldArr2 = obj2.getClass().getDeclaredFields();
                if (fieldArr1.length != fieldArr2.length) {
                    return false;
                } else {
                    for (int i = 0; i < fieldArr1.length; ++i) {
                        Field field1 = fieldArr1[i];
                        Field field2 = fieldArr2[i];
                        field1.setAccessible(true);
                        field2.setAccessible(true);
                        String typeName1 = field1.getType().getName();
                        String typeName2 = field2.getType().getName();
                        if (!StringUtils.equals(typeName1, typeName2)) {
                            return false;
                        }

                        Object tmpObj1;
                        Object tmpObj2;
                        try {
                            tmpObj1 = field1.get(obj1);
                            tmpObj2 = field2.get(obj2);
                        } catch (Exception var12) {
                            return false;
                        }

                        if (null == tmpObj1 && null != tmpObj2 || null != tmpObj1 && null == tmpObj2) {
                            return false;
                        }

                        if (null != tmpObj1 || null != tmpObj2) {
                            if (String.class.getName().equals(typeName1)) {
                                if (!((String) tmpObj1).equals((String) tmpObj2)) {
                                    return false;
                                }
                            } else if (Boolean.TYPE.getName().equals(typeName1)) {
                                if (!((Boolean) tmpObj1).equals((Boolean) tmpObj2)) {
                                    return false;
                                }
                            } else if (Double.TYPE.getName().equals(typeName1)) {
                                if (!((Double) tmpObj1).equals((Double) tmpObj2)) {
                                    return false;
                                }
                            } else if (Float.TYPE.getName().equals(typeName1)) {
                                if (!((Float) tmpObj1).equals((Float) tmpObj2)) {
                                    return false;
                                }
                            } else if (Integer.TYPE.getName().equals(typeName1)) {
                                if (!((Integer) tmpObj1).equals((Integer) tmpObj2)) {
                                    return false;
                                }
                            } else if (Long.TYPE.getName().equals(typeName1)) {
                                if (!((Long) tmpObj1).equals((Long) tmpObj2)) {
                                    return false;
                                }
                            } else if (Character.TYPE.getName().equals(typeName1)) {
                                if (!((Character) tmpObj1).equals((Character) tmpObj2)) {
                                    return false;
                                }
                            } else if (!tmpObj1.toString().equals(tmpObj2.toString())) {
                                return false;
                            }
                        }
                    }

                    return true;
                }
            }
        } else {
            return false;
        }
    }

    public static String getLocalAddress() {
        Enumeration<NetworkInterface> netInterfaces = null;

        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();

            while (true) {
                NetworkInterface ni;
                do {
                    if (!netInterfaces.hasMoreElements()) {
                        return "00:00:00:00:00:00";
                    }

                    ni = (NetworkInterface) netInterfaces.nextElement();
                } while (!ni.getName().equals("en0") && !ni.getName().equals("eth0"));

                Enumeration<InetAddress> ips = ni.getInetAddresses();

                while (ips.hasMoreElements()) {
                    InetAddress i = (InetAddress) ips.nextElement();
                    if (!(i instanceof Inet6Address)) {
                        return i.getHostAddress();
                    }
                }
            }
        } catch (SocketException var4) {
            return "00:00:00:00:00:00";
        }
    }

    public static String getMac() {
        Enumeration<NetworkInterface> netInterfaces = null;

        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();

            byte[] macs;
            do {
                NetworkInterface ni;
                do {
                    if (!netInterfaces.hasMoreElements()) {
                        return "00:00:00:00:00:00";
                    }

                    ni = (NetworkInterface) netInterfaces.nextElement();
                } while (!ni.getName().equals("en0") && !ni.getName().equals("eth0"));

                macs = ni.getHardwareAddress();
            } while (macs == null);

            return String.format("%02x:%02x:%02x:%02x:%02x:%02x", macs[0] & 255, macs[1] & 255, macs[2] & 255, macs[3] & 255, macs[4] & 255, macs[5] & 255);
        } catch (SocketException var3) {
            return "00:00:00:00:00:00";
        }
    }

    public static void getAllMacAdress() {
        Enumeration<NetworkInterface> netInterfaces = null;

        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();

            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                System.out.println("DisplayName: " + ni.getDisplayName());
                System.out.println("Name: " + ni.getName());
                Enumeration<InetAddress> ips = ni.getInetAddresses();

                while (ips.hasMoreElements()) {
                    InetAddress i = (InetAddress) ips.nextElement();
                    if (i instanceof Inet6Address) {
                        System.out.println("IPv6: " + i.getHostAddress());
                    } else {
                        System.out.println("IPv4: " + i.getHostAddress());
                    }
                }

                byte[] macs = ni.getHardwareAddress();
                if (macs != null) {
                    String mac = String.format("%02x:%02x:%02x:%02x:%02x:%02x", macs[0] & 255, macs[1] & 255, macs[2] & 255, macs[3] & 255, macs[4] & 255, macs[5] & 255);
                    System.out.println("MAC:" + mac);
                }
            }
        } catch (SocketException var5) {
            var5.printStackTrace();
        }

    }

    public static String getObjectIdentity(Object o) {
        return o.getClass().getSimpleName() + "@" + Integer.toHexString(System.identityHashCode(o));
    }

    public static void checkTimeout(long currVal) {
        checkTimeout(currVal, 5000L);
    }

    public static void checkTimeout(long currVal, long warnVal) {
        if (currVal < warnVal) {
            System.out.println("timeout value " + currVal + " < " + warnVal + "ms,make sure it's your want");
        }

    }

    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();
        return digits(mostSigBits >> 32, 8) + digits(mostSigBits >> 16, 4) + digits(mostSigBits, 4) + digits(leastSigBits >> 48, 4) + digits(leastSigBits, 12);
    }

    private static String digits(long val, int digits) {
        long hi = 1L << digits * 4;
        return Long.toHexString(hi | val & hi - 1L).substring(1);
    }

    public static String nullToEmpty(String s) {
        return s == null ? "" : s;
    }

    public static long ipToLong(String strIp) {
        long[] ip = new long[4];
        int position1 = strIp.indexOf(".");
        int position2 = strIp.indexOf(".", position1 + 1);
        int position3 = strIp.indexOf(".", position2 + 1);
        ip[0] = Long.parseLong(strIp.substring(0, position1));
        ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strIp.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    public static String longToIP(long longIp) {
        StringBuffer sb = new StringBuffer("");
        sb.append(String.valueOf(longIp >>> 24));
        sb.append(".");
        sb.append(String.valueOf((longIp & 16777215L) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((longIp & 65535L) >>> 8));
        sb.append(".");
        sb.append(String.valueOf(longIp & 255L));
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getLocalAddress());
        System.out.println(getMac());
        getAllMacAdress();
    }
}

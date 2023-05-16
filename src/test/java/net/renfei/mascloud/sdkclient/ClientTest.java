package net.renfei.mascloud.sdkclient;

import org.junit.jupiter.api.Test;

/**
 * @author renfei
 */
public class ClientTest {
    //@Test
    public void sendDSMS() {
        Client client = Client.getInstance();
        // 登录地址需另外提供
        boolean isLoggedin = client.login("http://112.35.4.197:15000", "username", "password", "任霏博客");
        if (isLoggedin) {
            System.out.println("Login successed");
        } else {
            System.out.println("Login failed");
            return;
        }

        // 普通短信
        int rt = client.sendDSMS(new String[]{"15211111111"}, "单元测试短信", "", 1, "sign", null, false);
        System.out.println(rt);
    }

    //@Test
    public void sendTSMS() {
        Client client = Client.getInstance();
        // 登录地址需另外提供
        boolean isLoggedin = client.login("http://112.35.4.197:15000", "username", "password", "任霏博客");
        if (isLoggedin) {
            System.out.println("Login successed");
        } else {
            System.out.println("Login failed");
            return;
        }
        int rtm = client.sendTSMS(new String[]{"15211111111"}, "模板ID", new String[]{"参数一", "参数二"}, "123", 0, "签名ID", null);
        System.out.println(rtm);
    }
}

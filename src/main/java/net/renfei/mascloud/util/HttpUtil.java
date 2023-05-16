package net.renfei.mascloud.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author renfei
 */
public class HttpUtil {

    public static CloseableHttpResponse post(String url, String dat, ContentType type) throws Exception {
        HttpPost post = new HttpPost(url);
        HttpEntity entity = EntityBuilder.create().setText(dat).setContentType(type).build();
        post.setEntity(entity);
        CloseableHttpClient client = HttpClientHolder.getInstance().getClient();
        CloseableHttpResponse resp = client.execute(post);
        return resp;
    }

    public static String readHttpResponse(HttpResponse response) throws IOException {
        HttpEntity resEntity = response.getEntity();
        if (resEntity == null) {
            return null;
        } else {
            String var5;
            try {
                Charset charset = ContentType.getOrDefault(resEntity).getCharset();
                String contentCharset = charset == null ? HTTP.DEF_CONTENT_CHARSET.name() : charset.name();
                byte[] data = EntityUtils.toByteArray(resEntity);
                var5 = new String(data, contentCharset);
            } finally {
                EntityUtils.consume(resEntity);
            }

            return var5;
        }
    }

    public static void safeClose(CloseableHttpResponse o) {
        if (o != null) {
            if (o.getEntity() != null) {
                try {
                    EntityUtils.consume(o.getEntity());
                } catch (IOException var3) {
                    var3.printStackTrace();
                }
            }

            try {
                o.close();
            } catch (Throwable var2) {
                var2.printStackTrace();
            }

        }
    }
}

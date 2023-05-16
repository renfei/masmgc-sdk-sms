package net.renfei.mascloud.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * com.ejtone.mars.transport.http.client.HttpClientHolder
 *
 * @author renfei
 */
public class HttpClientHolder {
    private int maxTotal;
    private int maxPerRoute;
    private int connTimeout;
    private int requestTimeout;
    private int keepaliveTiemout;
    private CloseableHttpClient client;

    public static HttpClientHolder getInstance() {
        return HttpClientHolder.InstanceHolder.instance;
    }

    private HttpClientHolder() {
        this.maxTotal = 2000;
        this.maxPerRoute = 200;
        this.connTimeout = 5000;
        this.requestTimeout = 5000;
        this.keepaliveTiemout = 1000;
        try {
            afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CloseableHttpClient getClient() {
        return this.client;
    }

    public int getMaxTotal() {
        return this.maxTotal;
    }

    public HttpClientHolder setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
        return this;
    }

    public int getMaxPerRoute() {
        return this.maxPerRoute;
    }

    public HttpClientHolder setMaxPerRoute(int maxPerRoute) {
        this.maxPerRoute = maxPerRoute;
        return this;
    }

    public int getConnTimeout() {
        return this.connTimeout;
    }

    public HttpClientHolder setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
        return this;
    }

    public int getRequestTimeout() {
        return this.requestTimeout;
    }

    public HttpClientHolder setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
        return this;
    }

    public int getKeepaliveTiemout() {
        return this.keepaliveTiemout;
    }

    public HttpClientHolder setKeepaliveTiemout(int keepaliveTiemout) {
        this.keepaliveTiemout = keepaliveTiemout;
        return this;
    }

    public void afterPropertiesSet() throws Exception {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(this.requestTimeout).setConnectTimeout(this.connTimeout).build();
        HttpClientBuilder builder = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig);
        builder.evictExpiredConnections().evictIdleConnections((long) this.keepaliveTiemout, TimeUnit.MILLISECONDS);
        builder.setMaxConnTotal(this.maxTotal).setMaxConnPerRoute(this.maxPerRoute);
        builder.setSSLSocketFactory(createSSLConnSocketFactory());
        this.client = builder.build();
    }

    private static SSLConnectionSocketFactory createSSLConnSocketFactory() throws GeneralSecurityException {
        SSLContext sslContext = (new SSLContextBuilder()).loadTrustMaterial((KeyStore) null, new TrustStrategy() {
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
        return new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
    }

    private static final class InstanceHolder {
        public static final HttpClientHolder instance = new HttpClientHolder();

        private InstanceHolder() {
        }
    }
}

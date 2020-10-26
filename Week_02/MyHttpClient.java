import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLHandshakeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kaithy.xu
 * @date 2020-10-26 21:19
 */
public class MyHttpClient {

    private static final PoolingHttpClientConnectionManager connectManager ;

    static {

        connectManager = new PoolingHttpClientConnectionManager();

        connectManager.setMaxTotal(400);

        connectManager.setDefaultMaxPerRoute(20);

    }

    public static CloseableHttpClient getHttpClient() {

        RequestConfig config = RequestConfig.custom()
            //连接超时
            .setConnectionRequestTimeout(15000)
            //请求超时
            .setConnectTimeout(15000)
            //响应超时
            .setSocketTimeout(15000)
            .build();

        HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException e, int count, HttpContext httpContext) {
                if(3 <= count) {
                    return false;
                }

                if(e instanceof NoHttpResponseException || e instanceof SSLHandshakeException || e instanceof InterruptedIOException) {
                    return true;
                }
                return false;
            }
        };


        return HttpClients.custom().setDefaultRequestConfig(config)
            .setRetryHandler(retryHandler)
            .setConnectionManager(connectManager)
            .build();


    }


    public static void get(String url, Map<String, Object> params) throws Exception {

        CloseableHttpClient client = getHttpClient();

        URIBuilder builder = new URIBuilder(url);


        if(null != params && params.size() > 0) {
            List<NameValuePair> requestParams = new ArrayList<>();
            for (String key: params.keySet()) {
                requestParams.add(new BasicNameValuePair(key, JSONObject.toJSONString(params.get(key))));
            }

            builder.setParameters(requestParams);
        }

        HttpGet get = new HttpGet(builder.build());

        CloseableHttpResponse response = client.execute(get);

        printResponse(response);
        
    }

    public static void post(String url, Map<String, Object> params) throws Exception {
        CloseableHttpClient client = getHttpClient();

        HttpPost post = new HttpPost(url);

        if(null != params && params.size() > 0) {

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            for (String key: params.keySet()) {
                builder.addTextBody(key, JSONObject.toJSONString(params.get(key)),ContentType.APPLICATION_JSON);
            }

            post.setEntity(builder.build());
        }

        CloseableHttpResponse response = client.execute(post);
        
        printResponse(response);

    }
    
    private static void printResponse(CloseableHttpResponse response) {
        //获取返回的内容
        HttpEntity entity = response.getEntity();
        BufferedReader reader;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"),8*1024);
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            System.out.println(stringBuilder.toString());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

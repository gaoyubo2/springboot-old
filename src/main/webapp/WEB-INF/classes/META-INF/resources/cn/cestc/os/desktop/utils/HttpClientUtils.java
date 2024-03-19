package cn.cestc.os.desktop.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description: http客户端工具类
 *
 * @author xubo
 * 2015年6月11日 上午11:44:16
 */
@Slf4j
public class HttpClientUtils
{

    //java.net.URL;
    public static String UrlSend(String url, Map<String, String> map)
    {
        URL neturl;
        String inputLine = "";
        String urlsuffix = "";
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            urlsuffix += "&" + entry.getKey() + "=" + entry.getValue();
        }
        String urlsuffixtemp = urlsuffix.substring(1, urlsuffix.length());
        System.out.println(url + "?" + urlsuffixtemp);
        try
        {
            neturl = new URL(url + "?" + urlsuffixtemp);
            BufferedReader in = new BufferedReader(new InputStreamReader(neturl.openStream()));
            inputLine = in.readLine();
            in.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return inputLine;
    }

    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public static String httpPost(String url, Map<String, String> map, HttpServletRequest req)
    {
        String result = "";
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();

        for (Map.Entry<String, String> entry : map.entrySet())
        {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        UrlEncodedFormEntity uefEntity;
        try
        {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            //由于session统一的问题，这里需要将cookies放入请求头中
            Cookie[] cookies = req.getCookies();
            String cookiesstr = "";
            if (cookies != null)
            {
                for (Cookie c : cookies)
                {
                    cookiesstr += c.getName() + "=" + c.getValue() + ";";
                }
            }
            if (cookies.length > 0)
            {
                httppost.addHeader("Cookie", cookiesstr.substring(0, cookiesstr.length() - 1));
            }
            log.info("executing request : {}", httppost.getURI());
            // System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try
            {
                HttpEntity entity = response.getEntity();
                if (entity != null)
                {
                    result = EntityUtils.toString(entity, "UTF-8");
                }
            } finally
            {
                response.close();
            }
        } catch (ClientProtocolException e)
        {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            // 关闭连接,释放资源
            try
            {
                httpclient.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 发送 get请求
     */
    public static String httpGet(String url, Map<String, String> map)
    {
        String result = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try
        {
            String urlsuffix = "";
            for (Map.Entry<String, String> entry : map.entrySet())
            {
                urlsuffix += "&" + entry.getKey() + "=" + entry.getValue();
            }
            String urlsuffixtemp = urlsuffix.substring(1, urlsuffix.length());
            // 创建httpget.
            HttpGet httpget = new HttpGet(url + "?" + urlsuffixtemp);

            log.info("executing request : {}", httpget.getURI());
            // System.out.println("executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try
            {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null)
                {
                    result = EntityUtils.toString(entity);
                }
            } finally
            {
                response.close();
            }
        } catch (ClientProtocolException e)
        {
            e.printStackTrace();
        } catch (ParseException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            // 关闭连接,释放资源
            try
            {
                httpclient.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static void main(String[] args) throws Exception
    {
//		Map<String, String> map=new HashMap<String, String>();
//		map.put("username", "jupiter");
//		System.out.println(httpPost("http://172.18.1.68:6080/authority/usercenter/function/search/first.do",map));
    }

}

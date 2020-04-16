package Data_Format;

import java.io.*;
import java.util.ArrayList;

import Tool.Tools;
import com.sun.beans.editors.ColorEditor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//解析具体信息
public class WebCrawler {

    public String Crawler(String code, int type) {
        Tool.Tools tool = new Tools();
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        //2.创建get请求，相当于在浏览器地址栏输入 网址
        HttpGet request;
        if (type == 0)
            request = new HttpGet("http://search.huochepiao.com/chaxun/resultc.asp?txtCheci=" + code + "&cc.x=0&cc.y=0");
        else if (type == 1)
            request = new HttpGet("http://search.huochepiao.com/oldcheci/" + code);
        else
            request = new HttpGet("http://search.huochepiao.com/checiold/" + code);
        //设置请求头，将爬虫伪装成浏览器
        request.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
//        HttpHost proxy = new HttpHost("60.13.42.232", 9999);
//        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
//        request.setConfig(config);

        StringBuilder source = new StringBuilder();
        try {
            //3.执行get请求，相当于在输入地址栏后敲回车键
            response = httpClient.execute(request);

            //4.判断响应状态为200，进行处理
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                String html = EntityUtils.toString(httpEntity, "GBK");

                Document document = Jsoup.parse(html);
                Element table;
                if (type == 2)
                    table = document.select("table").get(5);
                else
                    table = document.select("table").get(6);

                ArrayList<String> train_code = new ArrayList<String>();
                ArrayList<String> station_sequence = new ArrayList<String>();
                ArrayList<String> station_name = new ArrayList<String>();
                ArrayList<String> arrive_time = new ArrayList<String>();
                ArrayList<String> depart_time = new ArrayList<String>();
                ArrayList<String> stay_time = new ArrayList<String>();
                ArrayList<String> runtime = new ArrayList<String>();
                ArrayList<String> days = new ArrayList<String>();
                ArrayList<String> mileage = new ArrayList<String>();
                ArrayList<String> hard_price = new ArrayList<String>();
                ArrayList<String> soft_price = new ArrayList<String>();
                ArrayList<String> business_price = new ArrayList<String>();
                ArrayList<String> hard_sleep_price = new ArrayList<String>();
                ArrayList<String> soft_sleep_price = new ArrayList<String>();
                ArrayList<String> first_second_price = new ArrayList<String>();

                boolean[] isHaveThis = new boolean[15];
                int[] rowList = new int[15];

                Elements rows = table.select("tr");
                {
                    Element row = rows.get(0);
                    Elements cols = row.select("td");
                    judgeIsHaveThisInformation(cols, isHaveThis, rowList);
                }


                for (int i = 1; i < rows.size(); i++) {
                    Element row = rows.get(i);
                    Elements cols = row.select("td");
                    if (isHaveThis[0])
                        train_code.add(cols.get(rowList[0]).text());
                    if (isHaveThis[1])
                        station_sequence.add(cols.get(rowList[1]).text());
                    if (isHaveThis[2])
                        station_name.add(cols.get(rowList[2]).text());
                    if (isHaveThis[3])
                        arrive_time.add(cols.get(rowList[3]).text());
                    if (isHaveThis[4])
                        depart_time.add(cols.get(rowList[4]).text());
                    if (isHaveThis[5])
                        stay_time.add(cols.get(rowList[5]).text());
                    if (isHaveThis[6])
                        runtime.add(cols.get(rowList[6]).text());
                    if (isHaveThis[7])
                        days.add(cols.get(rowList[7]).text());
                    if (isHaveThis[8])
                        mileage.add(cols.get(rowList[8]).text());
                    if (isHaveThis[9])
                        hard_price.add(cols.get(rowList[9]).text());
                    if (isHaveThis[10])
                        soft_price.add(cols.get(rowList[10]).text());
                    if (isHaveThis[11])
                        business_price.add(cols.get(rowList[11]).text());
                    if (isHaveThis[12])
                        hard_sleep_price.add(cols.get(rowList[12]).text());
                    if (isHaveThis[13])
                        soft_sleep_price.add(cols.get(rowList[13]).text());
                    if (isHaveThis[14])
                        first_second_price.add(cols.get(rowList[14]).text());
                }
                if (train_code.size() != 0)
                    source.append("train_code,station_sequence,station_name,arrive_time,depart_time,stay_time,runtime,days,mileage,hard_price,soft_price,business_price,hard_sleep_price,soft_sleep_price,first_second_price").append(tool.getLineSeparator());
                for (int i= 0; i < train_code.size(); i++)
                {
                    if (isHaveThis[0])
                        source.append(train_code.get(i)).append(",");
                    else
                        source.append("-,");
                    if (isHaveThis[1])
                        source.append(station_sequence.get(i)).append(",");
                    else
                        source.append("-,");
                    if (isHaveThis[2])
                        source.append(station_name.get(i)).append(",");
                    else
                        source.append("-,");
                    if (isHaveThis[3])
                        source.append(arrive_time.get(i)).append(",");
                    else
                        source.append("-,");
                    if (isHaveThis[4])
                        source.append(depart_time.get(i)).append(",");
                    else
                        source.append("-,");
                    if (isHaveThis[5])
                        source.append(stay_time.get(i)).append(",");
                    else
                        source.append("-,");
                    if (isHaveThis[6])
                        source.append(runtime.get(i)).append(",");
                    else
                        source.append("-,");
                    if (isHaveThis[7])
                        source.append(days.get(i)).append(",");
                    else
                        source.append("-,");
                    if (isHaveThis[8])
                        source.append(mileage.get(i)).append(",");
                    else
                        source.append("-,");
                    if (isHaveThis[9])
                        source.append(hard_price.get(i)).append(",");
                    else
                        source.append("0,");
                    if (isHaveThis[10])
                        source.append(soft_price.get(i)).append(",");
                    else
                        source.append("0,");
                    if (isHaveThis[11])
                        source.append(business_price.get(i)).append(",");
                    else
                        source.append("0,");
                    if (isHaveThis[12])
                        source.append(hard_sleep_price.get(i)).append(",");
                    else
                        source.append("0/0/0,");
                    if (isHaveThis[13])
                        source.append(soft_sleep_price.get(i)).append(",");
                    else
                        source.append("0/0,");
                    if (isHaveThis[14])
                        source.append(first_second_price.get(i));
                    else
                        source.append("0/0");
                    if (i != train_code.size())
                        source.append(tool.getLineSeparator());
                }
            } else {
                //如果返回状态不是200，比如404（页面不存在）等，根据情况做处理，这里略
                System.out.println("返回状态不是200");
                System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6.关闭
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);

            if (source.length() == 0)
            {
                if (type == 0)
                    return Crawler(code, 1);
                if (type == 1)
                    return Crawler(code, 2);
                else
                    return "no";
            }else
            {
                return source.toString();
            }
        }
    }

    public void judgeIsHaveThisInformation(Elements cols, boolean[] isHaveThis, int[] rowList)
    {
        String[] allHeader = {"车次", "站次", "站名", "到达时间", "开车时间", "停留时间", "运行时间", "天数", "里程", "硬座", "软座", "商务", "硬卧", "软卧", "一等"};
        for(int i = 0; i < cols.size(); i++)
        {
            for (int j =0; j < allHeader.length; j++)
            {
                if(cols.get(i).text().indexOf(allHeader[j]) != -1)
                {
                    isHaveThis[j] = true;
                    rowList[j] = i;
                    continue;
                }
            }
        }
    }
}
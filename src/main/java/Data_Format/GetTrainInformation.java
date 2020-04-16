package Data_Format;

import Tool.Tools;

import java.io.*;
import java.util.ArrayList;

//爬虫得到所有车站信息，存放到data文件夹中
public class GetTrainInformation
{
    public static void main(String[] args) throws Exception
    {
        Tool.Tools tool = new Tools();
        WebCrawler webCrawler = new WebCrawler();
        String outPutPath = "src" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Data" + tool.getSeparator() + "train_information" + tool.getSeparator();
        FileInputStream fis = new FileInputStream("src" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Data" + tool.getSeparator() + "train_information.csv");
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader bufferReader = new BufferedReader(isr);
        bufferReader.readLine();
        String codeInput = bufferReader.readLine();
        while (!codeInput.isEmpty())
        {
            String[] temp = codeInput.split(",");
            String content = webCrawler.Crawler(temp[0], 0);
            if (content.equals("no"))
                System.out.println(temp[0]);
            else
            {
                FileOutputStream newFis = new FileOutputStream(outPutPath + temp[0] + ".csv");
                OutputStreamWriter osr = new OutputStreamWriter(newFis, "UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(osr);
                bufferedWriter.write(content);
                bufferedWriter.close();
            }
            codeInput = bufferReader.readLine();
        }
//        System.out.println(webCrawler.Crawler("D2359", 2));
    }
}

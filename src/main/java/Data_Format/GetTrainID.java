package Data_Format;

import Tool.Tools;

import java.io.*;
//解析train_list文件，得到所有的车次编号，存到train_information文件中
public class GetTrainID
{
    public static void main(String[] args) throws Exception
    {
        Tool.Tools tool = new Tools();
        FileInputStream fis = new FileInputStream("src" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "train_list.js");
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        FileOutputStream newFis = new FileOutputStream("src" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "train_information.csv");
        OutputStreamWriter osr = new OutputStreamWriter(newFis, "UTF-8");
        BufferedReader bufferReader = new BufferedReader(isr);
        BufferedWriter bufferedWriter = new BufferedWriter(osr);
        StringBuilder source = new StringBuilder(bufferReader.readLine());
        String[] sourceBreak = source.toString().split("},\\{");
        bufferedWriter.write("station_train_code,departure_destination_station,train_number" + tool.getLineSeparator());
        for (int i = 0; i < sourceBreak.length; i++)
        {
            int left = sourceBreak[i].indexOf("station_train_code");
            left += 21;
            int right = sourceBreak[i].indexOf("(");
            bufferedWriter.write(sourceBreak[i].substring(left, right) + ",");
            left = right + 1;
            right = sourceBreak[i].indexOf(")");
            bufferedWriter.write(sourceBreak[i].substring(left, right) + ",");
            left = sourceBreak[i].indexOf("train_no");
            left += 12;
            right = sourceBreak[i].indexOf("\"", left);
            bufferedWriter.write(sourceBreak[i].substring(left, right));
            if(i != sourceBreak.length-1)
                bufferedWriter.write(tool.getLineSeparator());
        }
        bufferedWriter.close();
        System.out.println();
    }
}

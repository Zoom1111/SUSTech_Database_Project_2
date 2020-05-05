package Data_Format;

import Tool.Tools;

import java.io.*;
import java.util.*;
import net.sourceforge.pinyin4j.PinyinHelper;

public class GetAllStation
{
    public static Tool.Tools tool = new Tools();
    public static void main(String[] args)
    {
        File[] fileList = getFile("src" + tool.getSeparator() + "main" + tool.getSeparator() + "java" + tool.getSeparator() +
                "Data_Format" + tool.getSeparator() + "Data" + tool.getSeparator() + "train_information");

        String stationList[] =  getStationList(fileList);

        String[][] station_Info = getStationInfo(stationList);

        writeToTable(station_Info, "src" + tool.getSeparator() + "main" + tool.getSeparator() + "java" + tool.getSeparator() +
                "Data_Format" + tool.getSeparator() + "Table" + tool.getSeparator() + "station.csv");
        System.out.println();
    }

    public static File[] getFile(String path)
    {
        File file = new File(path);
        File[] fileListTemp = file.listFiles();
        List<File> wjList = new ArrayList<File>();//新建一个文件集合
        for (int i = 0; i < fileListTemp.length; i++) {
            if (fileListTemp[i].isFile()) {//判断是否为文件
                wjList.add(fileListTemp[i]);
            }
        }
        return wjList.toArray(new File[wjList.size()]);
    }

    public static String[] getStationList(File[] fileList)
    {
        String path = "src" + tool.getSeparator() + "main" + tool.getSeparator() +
                "java" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Data" + tool.getSeparator() + "train_information" + tool.getSeparator();
        Set<String> stationList = new HashSet<String>();
        try {
            for (int i = 0 ; i < fileList.length; i++)
            {
                FileInputStream fis = new FileInputStream(path + fileList[i].getName());
                InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                BufferedReader bufferReader = new BufferedReader(isr);
                bufferReader.readLine();
                String temp = bufferReader.readLine();
                String[] rowList;
                while (temp != null)
                {
                    rowList = temp.split(",");
                    stationList.add(rowList[2]);
                    temp = bufferReader.readLine();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return stationList.toArray(new String[stationList.size()]);
        }
    }

    public static String[][] getStationInfo(String[] stationList)
    {
        int id = 1;
        String[][] stationInfo = new String[stationList.length + 1][4];
        stationInfo[0] = new String[]{"station_id", "station_name", "station_pinyin_headchar", "station_pinyin"};
        for (int i = 1 ; i < stationList.length + 1 ; i++)
        {
            stationInfo[i][0] = "" + id++;
            stationInfo[i][1] = stationList[i - 1];
            stationInfo[i][2] = getPinYinHeadChar(stationList[i - 1]);
            stationInfo[i][3] = getPinYin(stationList[i - 1]);
        }
        return stationInfo;
    }

    public static void writeToTable(String[][] station_info, String path)
    {
        try {
            FileOutputStream newFis = new FileOutputStream(path);
            OutputStreamWriter osr = new OutputStreamWriter(newFis, "UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter(osr);
            for (int i =0; i < station_info.length; i++)
            {
                for (int j = 0; j < station_info[0].length; j++)
                {
                    if (j != station_info[0].length - 1)
                        bufferedWriter.write(station_info[i][j] + ",");
                    else
                        bufferedWriter.write(station_info[i][j]);
                }
                if (i != station_info.length - 1)
                    bufferedWriter.write(tool.getLineSeparator());
            }
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static String getPinYinHeadChar(String str) {
        StringBuilder convert = new StringBuilder();
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].charAt(0));
            } else {
                convert.append(word);
            }
        }
        return convert.toString().toLowerCase();
    }
    public static String getPinYin(String str) {
        StringBuilder convert = new StringBuilder();
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].subSequence(0, pinyinArray[0].length() - 1));
            } else {
                convert.append(word);
            }
        }
        return convert.toString().toLowerCase();
    }
}

package Data_Format;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Format_Table
{
    public static Tool.Tools tool = new Tool.Tools();
    public static void main(String[] args)
    {
        formatTable_train_station();
    }

    static class Train
    {
        int train_id = 0;
        String train_num = "";
        String train_name = "";
        String train_type = "";
        int train_depart_station = 0;
        int train_arrive_station = 0;
        LocalDate train_depart_date;
        LocalDate train_arrive_date;
        LocalTime train_depart_time;
        LocalTime train_arrive_time;
    }

    public static void formatTable_train_station()
    {
        try {
            ArrayList<String> train_name_arraylist = new ArrayList<>();

            FileInputStream fis = new FileInputStream("src" + tool.getSeparator() + "main" + tool.getSeparator()
                    + "java" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Data" + tool.getSeparator() + "train_information.csv");
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader bufferReader = new BufferedReader(isr);
            bufferReader.readLine();
            String train_name = bufferReader.readLine();
            String[] train_name_rowList;
            while (train_name != null)
            {
                train_name_rowList = train_name.split(",");
                train_name_arraylist.add(train_name_rowList[0]);
                train_name = bufferReader.readLine();
            }

            HashMap<String, Integer> station_map = new HashMap<>();
            FileInputStream fis2 = new FileInputStream("src" + tool.getSeparator() + "main" + tool.getSeparator()
                    + "java" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Table" + tool.getSeparator() + "station.csv");
            InputStreamReader isr2 = new InputStreamReader(fis2, StandardCharsets.UTF_8);
            BufferedReader bufferReader2 = new BufferedReader(isr2);
            bufferReader2.readLine();
            String station = bufferReader2.readLine();
            String[] station_rowList;
            while (station != null)
            {
                station_rowList = station.split(",");
                station_map.put(station_rowList[1], Integer.parseInt(station_rowList[0]));
                station = bufferReader2.readLine();
            }

            ArrayList<Train> train_list = new ArrayList<>();
            for (String s : train_name_arraylist) {
                ArrayList<String[]> current_train_information = new ArrayList<>();

                File file = new File(("src" + tool.getSeparator() + "main" + tool.getSeparator()
                        + "java" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Data" + tool.getSeparator() + "train_information" + tool.getSeparator()
                        + s + ".csv"));
                FileInputStream fis3;
                if (file.isFile())
                    fis3 = new FileInputStream(file);
                else
                    continue;
                InputStreamReader isr3 = new InputStreamReader(fis3, StandardCharsets.UTF_8);
                BufferedReader bufferReader3 = new BufferedReader(isr3);
                bufferReader3.readLine();
                String part_station = bufferReader3.readLine();
                while (part_station != null) {
                    String[] part_station_rowList = part_station.split(",");
                    current_train_information.add(part_station_rowList);
                    part_station = bufferReader3.readLine();
                }
                String[] part_station_rowList = current_train_information.get(0);
                Train currentTrain = new Train();

                if (part_station_rowList[0].contains("/"))
                    currentTrain.train_num = s;
                else
                    currentTrain.train_num = part_station_rowList[0];

                if (part_station_rowList[0].equals("G2") || part_station_rowList[0].equals("G3") || part_station_rowList[0].equals("G10") ||
                        part_station_rowList[0].equals("G11") || part_station_rowList[0].equals("G118") || part_station_rowList[0].equals("G149") ||
                        part_station_rowList[0].equals("G7") || part_station_rowList[0].equals("G14"))
                    currentTrain.train_name = "复兴号";
                else
                    currentTrain.train_name = "和谐号";

                if (part_station_rowList[0].substring(0, 1).equals("G"))
                    currentTrain.train_type = "高速列车";
                else if (part_station_rowList[0].substring(0, 1).toUpperCase().equals("D"))
                    currentTrain.train_type = "动车";
                else if (part_station_rowList[0].substring(0, 1).toUpperCase().equals("C"))
                    currentTrain.train_type = "城际动车";
                else if (part_station_rowList[0].substring(0, 1).toUpperCase().equals("Z"))
                    currentTrain.train_type = "特快直达列车";
                else if (part_station_rowList[0].substring(0, 1).toUpperCase().equals("S"))
                    currentTrain.train_type = "旅客快车";
                else if (part_station_rowList[0].substring(0, 1).toUpperCase().equals("P"))
                    currentTrain.train_type = "普通快车";
                else if (part_station_rowList[0].substring(0, 1).toUpperCase().equals("Y"))
                    currentTrain.train_type = "旅游列车";
                else if (part_station_rowList[0].substring(0, 1).toUpperCase().equals("T") || part_station_rowList[0].substring(0, 1).toUpperCase().equals("Q"))
                    currentTrain.train_type = "特快列车";
                else if (part_station_rowList[0].substring(0, 1).toUpperCase().equals("K"))
                    currentTrain.train_type = "快速列车";
                else if (Integer.parseInt(part_station_rowList[0].substring(0, 1)) >= 1 || Integer.parseInt(part_station_rowList[0].substring(0, 1)) <= 5)
                    currentTrain.train_type = "普快列车";
                else if (Integer.parseInt(part_station_rowList[0].substring(0, 1)) >= 6 || Integer.parseInt(part_station_rowList[0].substring(0, 1)) <= 7)
                    currentTrain.train_type = "普客列车";
                else if (Integer.parseInt(part_station_rowList[0].substring(0, 1)) == 8)
                    currentTrain.train_type = "通勤列车";
                else
                    System.out.println(part_station_rowList[0]);

                part_station_rowList = current_train_information.get(0);
                currentTrain.train_depart_station = station_map.get(part_station_rowList[2]);
                currentTrain.train_depart_time = LocalTime.of(Integer.parseInt(part_station_rowList[4].substring(0, 2)), Integer.parseInt(part_station_rowList[4].substring(3, 5)));
                part_station_rowList = current_train_information.get(current_train_information.size() - 1);
                currentTrain.train_arrive_station = station_map.get(part_station_rowList[2]);

                currentTrain.train_depart_date = LocalDate.of(2020, 5, 7);
                LocalDate currentDate = LocalDate.of(2020, 5, 7);
                if (part_station_rowList[7].equals("-"))
                    continue;
                int passDays = Integer.parseInt(part_station_rowList[7]);
                currentTrain.train_arrive_date = currentDate.plusDays(passDays - 1);
                if (part_station_rowList[3].substring(0, 2).equals("始发"))
                    continue;
                currentTrain.train_arrive_time = LocalTime.of(Integer.parseInt(part_station_rowList[3].substring(0, 2)), Integer.parseInt(part_station_rowList[3].substring(3, 5)));
                train_list.add(currentTrain);
            }

            FileOutputStream newFis = new FileOutputStream("src" + tool.getSeparator() + "main" + tool.getSeparator()
                    + "java" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Table" + tool.getSeparator()
                    + "train.csv");
            OutputStreamWriter osr = new OutputStreamWriter(newFis, "UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter(osr);

            StringBuilder content = new StringBuilder();
            content.append("train_id,train_num,train_name,train_type,train_depart_station,train_arrive_station,train_depart_date,train_arrive_date,train_depart_time,train_arrive_time").append(tool.getLineSeparator());

            int current_id = 0;
            for(int t = 0; t < 7; t++) {
                HashSet<String> train_num_list = new HashSet<>();
                for (int i = 0; i < train_list.size(); i++) {
                    current_id++;
                    Train current = train_list.get(i);
                    if (train_num_list.contains(current.train_num))
                        System.out.println(current.train_num);
                    else
                        train_num_list.add(current.train_num);
                    content.append(current_id).append(",");
                    content.append(current.train_num).append(",");
                    content.append(current.train_name).append(",");
                    content.append(current.train_type).append(",");
                    content.append(current.train_depart_station).append(",");
                    content.append(current.train_arrive_station).append(",");
                    content.append(current.train_depart_date.plusDays(t)).append(",");
                    content.append(current.train_arrive_date.plusDays(t)).append(",");
                    content.append(current.train_depart_time).append(",");
                    if (i == train_list.size() - 1 && t == 6)
                        content.append(current.train_arrive_time);
                    else
                        content.append(current.train_arrive_time).append(tool.getLineSeparator());
                }
            }
            bufferedWriter.write(content.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

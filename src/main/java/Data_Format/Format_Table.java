package Data_Format;

import java.io.*;
import java.lang.reflect.Array;
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
        formatTable_train("2020-05-10", 1);
        formatTable_interval(118, 118, 28, 22, 22, 22, 18, 18, 54, 88, 28);
//        formatTable_seat(118, 118, 28, 22, 22, 22, 18, 18, 54, 88, 28);
        formatTable_ticket();
        formatTable_order();
        formatTable_user();
        formatTable_rate(10, 0.15, 0.3, 1.95, 0.4, 0.3, 0.23, 0.585
        ,0.45, 1, 0.02);
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

    public static void formatTable_train(String beginDay, int passDay)
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

                currentTrain.train_depart_date = LocalDate.parse(beginDay);
                LocalDate currentDate = LocalDate.parse(beginDay);
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
            OutputStreamWriter osr = new OutputStreamWriter(newFis, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(osr);

            FileOutputStream newFis2 = new FileOutputStream("src" + tool.getSeparator() + "main" + tool.getSeparator()
                    + "java" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Table" + tool.getSeparator()
                    + "train_station.csv");
            OutputStreamWriter osr2 = new OutputStreamWriter(newFis2, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter2 = new BufferedWriter(osr2);

            StringBuilder content = new StringBuilder();
            content.append("train_id,train_num,train_name,train_type,train_depart_station,train_arrive_station,train_depart_date,train_arrive_date,train_depart_time,train_arrive_time").append(tool.getLineSeparator());

            StringBuilder content2 = new StringBuilder();
            content2.append("train_id,station_id,each_depart_date,each_arrive_date,each_depart_time,each_arrive_time,distance,sequence").append(tool.getLineSeparator());

            int current_id = 0;
            for(int t = 0; t < passDay; t++) {
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
                    if (i == train_list.size() - 1 && t == passDay - 1)
                        content.append(current.train_arrive_time);
                    else
                        content.append(current.train_arrive_time).append(tool.getLineSeparator());


                    FileInputStream fis4 = new FileInputStream("src" + tool.getSeparator() + "main" + tool.getSeparator()
                            + "java" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Data" + tool.getSeparator() + "train_information" + tool.getSeparator()
                            + current.train_num + ".csv");
                    InputStreamReader isr4 = new InputStreamReader(fis4, StandardCharsets.UTF_8);
                    BufferedReader bufferReader4 = new BufferedReader(isr4);
                    bufferReader4.readLine();
                    String train_information = bufferReader4.readLine();
                    ArrayList<Integer> mileage_list = new ArrayList<>();
                    while (train_information != null)
                    {
                        String[] temp = train_information.split(",");

                        if(temp[8].equals(""))
                        {
                            mileage_list.add(-1);
                        }else
                            mileage_list.add(Integer.parseInt(temp[8]));
                        train_information = bufferReader4.readLine();
                    }
                    int minIndex = 0, maxIndex = 0;
                    boolean flag1 = true, flag2;
                    for(int m = 0; m < mileage_list.size(); m++)
                    {
                        if(mileage_list.get(m) == -1)
                        {
                            flag1 = false;
                            flag2 = true;
                        }else
                        {
                            flag2 = false;
                        }
                        if(flag1)
                            minIndex = m;
                        if(!flag1 && flag2)
                            maxIndex = m;
                    }

                    minIndex++;
                    int interval  = maxIndex - minIndex + 2;
                    int average = 1;
                    //////////////////////////
                    if(maxIndex >= minIndex){
                        if(maxIndex == mileage_list.size() - 1)
                            average = 50;
                        else
                            average = (mileage_list.get(maxIndex + 1) - mileage_list.get(minIndex - 1)) / (interval);
                    }

                    for(int m = minIndex; m <= maxIndex; m++)
                    {
                        mileage_list.set(m, mileage_list.get(m - 1) + average);
                    }

                    FileInputStream fis5 = new FileInputStream("src" + tool.getSeparator() + "main" + tool.getSeparator()
                            + "java" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Data" + tool.getSeparator() + "train_information" + tool.getSeparator()
                            + current.train_num + ".csv");
                    InputStreamReader isr5 = new InputStreamReader(fis5, StandardCharsets.UTF_8);
                    BufferedReader bufferReader5 = new BufferedReader(isr5);
                    bufferReader5.readLine();
                    train_information = bufferReader5.readLine();

                    int Index = -1;
                    while(train_information != null)
                    {
                        Index++;
                        String[] train_information_rowList = train_information.split(",");
                        content2.append(current_id).append(",");
                        content2.append(station_map.get(train_information_rowList[2])).append(",");
                        content2.append(current.train_depart_date.plusDays(t).plusDays(Integer.parseInt(train_information_rowList[7]) - 1)).append(",");
                        content2.append(current.train_arrive_date.plusDays(t).plusDays(Integer.parseInt(train_information_rowList[7]) - 1)).append(",");
                        if(train_information_rowList[3].substring(0,2).equals("始发"))
                            content2.append(",");
                        else
                            content2.append(LocalTime.of(Integer.parseInt(train_information_rowList[3].substring(0,2)), Integer.parseInt(train_information_rowList[3].substring(3,5)))).append(",");

                        if(train_information_rowList[4].substring(0,2).equals("终点"))
                            content2.append(",");
                        else
                            content2.append(LocalTime.of(Integer.parseInt(train_information_rowList[4].substring(0,2)), Integer.parseInt(train_information_rowList[4].substring(3,5)))).append(",");

                        //公里数是null的时候，，，，，，，，，，
//                        if(train_information_rowList[8].equals(""))
//                        {
//                            System.out.println(current.train_num);
//                            train_information = bufferReader5.readLine();
//                            continue;
//                        }

                        content2.append(mileage_list.get(Index)).append(",");
//                        content2.append(Integer.parseInt(train_information_rowList[8])).append(",");

//                        if(t == passDay - 1 && train_information_rowList[4].substring(0,2).equals("终点"))
//                            content2.append(Integer.parseInt(train_information_rowList[1]));
//                        else
                            content2.append(Integer.parseInt(train_information_rowList[1])).append(tool.getLineSeparator());

                        train_information = bufferReader5.readLine();
                    }
                }
            }
            bufferedWriter.write(content.toString());
            bufferedWriter.close();
            bufferedWriter2.write(content2.toString());
            bufferedWriter2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void formatTable_interval(int rest_hard,int rest_soft,int rest_business,int rest_hard_sleep_down,
                                           int rest_hard_sleep_middle,int rest_hard_sleep_up,int rest_soft_sleep_down,
                                           int rest_soft_sleep_up,int rest_first,int rest_second,int rest_super)
    {
        try {
            HashMap<String, Integer> station_map = new HashMap<>();
            {
            FileInputStream fis = new FileInputStream("src" + tool.getSeparator() + "main" + tool.getSeparator()
                    + "java" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Table" + tool.getSeparator() + "station.csv");
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader bufferReader = new BufferedReader(isr);
            bufferReader.readLine();
            String station_information = bufferReader.readLine();
            String[] station_rowList;
            while (station_information != null)
            {
                station_rowList = station_information.split(",");
                if(station_map.containsKey(station_rowList[1]))
                    System.out.println(station_rowList[1]);
                else
                    station_map.put(station_rowList[1], Integer.parseInt(station_rowList[0]));

                station_information = bufferReader.readLine();
            }}

            HashMap<Integer, String> train_map = new HashMap<>();
            {
            FileInputStream fis2 = new FileInputStream("src" + tool.getSeparator() + "main" + tool.getSeparator()
                    + "java" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Table" + tool.getSeparator() + "train.csv");
            InputStreamReader isr2 = new InputStreamReader(fis2, StandardCharsets.UTF_8);
            BufferedReader bufferReader2 = new BufferedReader(isr2);
            bufferReader2.readLine();
            String train_information = bufferReader2.readLine();
            String[] train_rowList;
            while (train_information != null)
            {
                train_rowList = train_information.split(",");
                if(train_map.containsKey(Integer.parseInt(train_rowList[0])))
                    System.out.println(train_rowList[1]);
                else
                    train_map.put(Integer.parseInt(train_rowList[0]), (train_rowList[1]));

                train_information = bufferReader2.readLine();
            }}

            long interval_id = 0;
            int max_train_id = train_map.size();
            int now_train_id = 0;

            StringBuilder content = new StringBuilder();
            content.append("interval_id,train_id,station_depart,station_arrive,rest_hard,rest_soft,rest_business,rest_hard_sleep_down," +
                    "rest_hard_sleep_middle,rest_hard_sleep_up,rest_soft_sleep_down,rest_soft_sleep_up,rest_first,rest_second,rest_super").append(tool.getLineSeparator());

            for(int i = 0; i < max_train_id; i++)
            {
                now_train_id++;
                String current_train_num = train_map.get(now_train_id);

                FileInputStream fis3 = new FileInputStream("src" + tool.getSeparator() + "main" + tool.getSeparator()
                        + "java" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Data" + tool.getSeparator()
                        + "train_information" + tool.getSeparator() + current_train_num + ".csv");
                InputStreamReader isr3 = new InputStreamReader(fis3, StandardCharsets.UTF_8);
                BufferedReader bufferReader3 = new BufferedReader(isr3);
                bufferReader3.readLine();
                String train_information = bufferReader3.readLine();
                String[] train_rowList;
                ArrayList<Integer> station_list = new ArrayList<>();
                while(train_information != null)
                {
                    train_rowList = train_information.split(",");
                    station_list.add(station_map.get(train_rowList[2]));
                    train_information = bufferReader3.readLine();
                }
//                if(station_list.get(0) == station_list.get(station_list.size() - 1))
//                    System.out.println(current_train_id);

                String train_type = current_train_num.substring(0, 1);
                for(int m = 0; m < station_list.size() - 1; m++)
                {
                    interval_id++;
                    content.append(interval_id).append(",");
                    content.append(now_train_id).append(",");
                    content.append(station_list.get(m)).append(",");
                    content.append(station_list.get(m + 1)).append(",");
                    if (train_type.equals("C") || train_type.equals("D") || train_type.equals("G"))
                    {
                        content.append("-1").append(",");
                        content.append("-1").append(",");
                        content.append(rest_business).append(",");
                        content.append("-1").append(",");
                        content.append("-1").append(",");
                        content.append("-1").append(",");
                        content.append("-1").append(",");
                        content.append("-1").append(",");
                        content.append(rest_first).append(",");
                        content.append(rest_second * 7).append(",");
                        content.append(rest_super).append(tool.getLineSeparator());
                    }else
                    {
                        content.append(rest_hard * 12).append(",");
                        content.append(rest_soft).append(",");
                        content.append("-1").append(",");
                        content.append(rest_hard_sleep_down * 5).append(",");
                        content.append(rest_hard_sleep_middle * 5).append(",");
                        content.append(rest_hard_sleep_up * 5).append(",");
                        content.append(rest_soft_sleep_down).append(",");
                        content.append(rest_soft_sleep_up).append(",");
                        content.append("-1").append(",");
                        content.append("-1").append(",");
                        content.append("-1").append(tool.getLineSeparator());
                    }
                }
            }

            FileOutputStream newFis = new FileOutputStream("src" + tool.getSeparator() + "main" + tool.getSeparator()
                    + "java" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Table" + tool.getSeparator()
                    + "interval.csv");
            OutputStreamWriter osr = new OutputStreamWriter(newFis, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(osr);

            bufferedWriter.write(content.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void formatTable_seat(int count_hard,int count_soft,int count_business,int count_hard_sleep_down,
                                        int count_hard_sleep_middle,int count_hard_sleep_up,int count_soft_sleep_down,
                                        int count_soft_sleep_up,int count_first,int count_second,int count_super)
    {
        try {
            FileInputStream fis = new FileInputStream("src" + tool.getSeparator() + "main" + tool.getSeparator()
                    + "java" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Table" + tool.getSeparator()
                   + "interval.csv");
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader bufferReader = new BufferedReader(isr);
            bufferReader.readLine();
            String interval_information = bufferReader.readLine();
            String[] interval_rowList;
            long seat_id = 0;

            FileOutputStream newFis = new FileOutputStream("src" + tool.getSeparator() + "main" + tool.getSeparator()
                    + "java" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Table" + tool.getSeparator()
                    + "seat.csv");
            OutputStreamWriter osr = new OutputStreamWriter(newFis, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(osr);

            StringBuilder content = new StringBuilder();
            content.append("seat_id,interval_id,ticket_id,seat_carriage,seat_type,seat_number,seat_status").append(tool.getLineSeparator());
            while(interval_information != null)
            {
                interval_rowList = interval_information.split(",");
                int size = Integer.parseInt(interval_rowList[4]);
                int number = 0;
                int carriage = 0;
                if(size != -1)
                    carriage++;
                for(int i = 0; i < size; i++)
                {
                    seat_id++;
                    number++;
                    if(number > count_hard)
                    {
                        carriage++;
                        number = 1;
                    }
                    content.append(seat_id).append(",");
                    content.append(interval_rowList[0]).append(",");
                    content.append(",");
                    content.append(carriage).append(",");
                    content.append("1").append(",");
                    content.append(number).append(",");
                    content.append("Y").append(tool.getLineSeparator());
                }

                size = Integer.parseInt(interval_rowList[5]);
                number = 0;
                if(size != -1)
                    carriage++;
                for(int i = 0; i < size; i++)
                {
                    seat_id++;
                    number++;
                    if(number > count_soft)
                    {
                        carriage++;
                        number = 1;
                    }
                    content.append(seat_id).append(",");
                    content.append(interval_rowList[0]).append(",");
                    content.append(",");
                    content.append(carriage).append(",");
                    content.append("2").append(",");
                    content.append(number).append(",");
                    content.append("Y").append(tool.getLineSeparator());
                }

                size = Integer.parseInt(interval_rowList[6]);
                number = 0;
                if(size != -1)
                    carriage++;
                for(int i = 0; i < size; i++)
                {
                    seat_id++;
                    number++;
                    if(number > count_business)
                    {
                        carriage++;
                        number = 1;
                    }
                    content.append(seat_id).append(",");
                    content.append(interval_rowList[0]).append(",");
                    content.append(",");
                    content.append(carriage).append(",");
                    content.append("3").append(",");
                    content.append(number).append(",");
                    content.append("Y").append(tool.getLineSeparator());
                }

                size = Integer.parseInt(interval_rowList[7]);
                number = 0;
                if(size != -1)
                    carriage++;
                for(int i = 0; i < size; i++)
                {
                    seat_id++;
                    number++;
                    if(number > count_hard_sleep_down)
                    {
                        carriage++;
                        number = 1;
                    }
                    content.append(seat_id).append(",");
                    content.append(interval_rowList[0]).append(",");
                    content.append(",");
                    content.append(carriage).append(",");
                    content.append("4").append(",");
                    content.append(number).append(",");
                    content.append("Y").append(tool.getLineSeparator());
                }

                size = Integer.parseInt(interval_rowList[8]);
                number = 0;
                if(size != -1)
                    carriage++;
                for(int i = 0; i < size; i++)
                {
                    seat_id++;
                    number++;
                    if(number > count_hard_sleep_middle)
                    {
                        carriage++;
                        number = 1;
                    }
                    content.append(seat_id).append(",");
                    content.append(interval_rowList[0]).append(",");
                    content.append(",");
                    content.append(carriage).append(",");
                    content.append("5").append(",");
                    content.append(number).append(",");
                    content.append("Y").append(tool.getLineSeparator());
                }

                size = Integer.parseInt(interval_rowList[9]);
                number = 0;
                if(size != -1)
                    carriage++;
                for(int i = 0; i < size; i++)
                {
                    seat_id++;
                    number++;
                    if(number > count_hard_sleep_up)
                    {
                        carriage++;
                        number = 1;
                    }
                    content.append(seat_id).append(",");
                    content.append(interval_rowList[0]).append(",");
                    content.append(",");
                    content.append(carriage).append(",");
                    content.append("6").append(",");
                    content.append(number).append(",");
                    content.append("Y").append(tool.getLineSeparator());
                }

                size = Integer.parseInt(interval_rowList[10]);
                number = 0;
                if(size != -1)
                    carriage++;
                for(int i = 0; i < size; i++)
                {
                    seat_id++;
                    number++;
                    if(number > count_soft_sleep_down)
                    {
                        carriage++;
                        number = 1;
                    }
                    content.append(seat_id).append(",");
                    content.append(interval_rowList[0]).append(",");
                    content.append(",");
                    content.append(carriage).append(",");
                    content.append("7").append(",");
                    content.append(number).append(",");
                    content.append("Y").append(tool.getLineSeparator());
                }

                size = Integer.parseInt(interval_rowList[11]);
                number = 0;
                if(size != -1)
                    carriage++;
                for(int i = 0; i < size; i++)
                {
                    seat_id++;
                    number++;
                    if(number > count_soft_sleep_up)
                    {
                        carriage++;
                        number = 1;
                    }
                    content.append(seat_id).append(",");
                    content.append(interval_rowList[0]).append(",");
                    content.append(",");
                    content.append(carriage).append(",");
                    content.append("8").append(",");
                    content.append(number).append(",");
                    content.append("Y").append(tool.getLineSeparator());
                }

                size = Integer.parseInt(interval_rowList[12]);
                number = 0;
                if(size != -1)
                    carriage++;
                for(int i = 0; i < size; i++)
                {
                    seat_id++;
                    number++;
                    if(number > count_first)
                    {
                        carriage++;
                        number = 1;
                    }
                    content.append(seat_id).append(",");
                    content.append(interval_rowList[0]).append(",");
                    content.append(",");
                    content.append(carriage).append(",");
                    content.append("9").append(",");
                    content.append(number).append(",");
                    content.append("Y").append(tool.getLineSeparator());
                }

                size = Integer.parseInt(interval_rowList[13]);
                number = 0;
                if(size != -1)
                    carriage++;
                for(int i = 0; i < size; i++)
                {
                    seat_id++;
                    number++;
                    if(number > count_second)
                    {
                        carriage++;
                        number = 1;
                    }
                    content.append(seat_id).append(",");
                    content.append(interval_rowList[0]).append(",");
                    content.append(",");
                    content.append(carriage).append(",");
                    content.append("10").append(",");
                    content.append(number).append(",");
                    content.append("Y").append(tool.getLineSeparator());
                }

                size = Integer.parseInt(interval_rowList[14]);
                number = 0;
                if(size != -1)
                    carriage++;
                for(int i = 0; i < size; i++)
                {
                    seat_id++;
                    number++;
                    if(number > count_super)
                    {
                        carriage++;
                        number = 1;
                    }
                    content.append(seat_id).append(",");
                    content.append(interval_rowList[0]).append(",");
                    content.append(",");
                    content.append(carriage).append(",");
                    content.append("11").append(",");
                    content.append(number).append(",");
                    content.append("Y").append(tool.getLineSeparator());
                }

                bufferedWriter.write(content.toString());
                content = new StringBuilder();
                interval_information = bufferReader.readLine();
            }

            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void formatTable_ticket()
    {
        try {
            FileOutputStream newFis = new FileOutputStream("src" + tool.getSeparator() + "main" + tool.getSeparator()
                    + "java" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Table" + tool.getSeparator()
                    + "seat.csv");
            OutputStreamWriter osr = new OutputStreamWriter(newFis, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(osr);

            bufferedWriter.write("ticket_id,order_id,train_id,ticker_price,ticket_entrance,depart_station_id,arrive_station_id,ticket_status" + tool.getLineSeparator());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void formatTable_order()
    {
        try {
            FileOutputStream newFis = new FileOutputStream("src" + tool.getSeparator() + "main" + tool.getSeparator()
                    + "java" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Table" + tool.getSeparator()
                    + "order.csv");
            OutputStreamWriter osr = new OutputStreamWriter(newFis, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(osr);

            bufferedWriter.write("order_id,user_id,create_date,create_time,order_status,order_price" + tool.getLineSeparator());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void formatTable_user()
    {
        try {
            FileOutputStream newFis = new FileOutputStream("src" + tool.getSeparator() + "main" + tool.getSeparator()
                    + "java" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Table" + tool.getSeparator()
                    + "user.csv");
            OutputStreamWriter osr = new OutputStreamWriter(newFis, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(osr);

            bufferedWriter.write("user_id,username,phone_number,card_id" + tool.getLineSeparator());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void formatTable_rate(double start_mileage, double rate_hard, double rate_soft, double rate_business, double rate_hard_sleep_down,
                                        double rate_hard_middle, double rate_hard_up, double rate_soft_sleep_down
            , double rate_soft_sleep_up, double rate_float, double rate_insurance)
    {
        try {
            FileOutputStream newFis = new FileOutputStream("src" + tool.getSeparator() + "main" + tool.getSeparator()
                    + "java" + tool.getSeparator() + "Data_Format" + tool.getSeparator() + "Table" + tool.getSeparator()
                    + "rate.csv");
            OutputStreamWriter osr = new OutputStreamWriter(newFis, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(osr);

            String content = "start_mileage,rate_hard,rate_soft,rate_business,rate_hard_sleep_down,rate_hard_middle,rate_hard_up,rate_soft_sleep_down" +
                    ",rate_soft_sleep_up,rate_float,rate_insurance" +
                    tool.getLineSeparator() +
                    start_mileage + "," + rate_hard + "," + rate_soft + "," + rate_business + "," + rate_hard_sleep_down
                    + "," + rate_hard_middle + "," + rate_hard_up + "," + rate_soft_sleep_down
                    + "," + rate_soft_sleep_up + "," + rate_float + "," + rate_insurance +
                    tool.getLineSeparator();
            bufferedWriter.write(content);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

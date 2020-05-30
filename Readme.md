# <center>SUSTech_Database_Project_2</center>

Project Site: https://github.com/Zoom1111/SUSTech_Database_Project_2</br>
Developer: [Zhong Wanli](https://github.com/zoom1111) 11811526<br/>Developer: [Tong Yijie](https://github.com/nanfang-wuyu) 11811512



### 1. Hypothesis

1. Each train has only one train code.
2. In normal conditions, the train schedule is the same every day.
3. Train information updates to 2020-04-11.
4. If C,D,G is the header of train number, the train is high-speed railway. Otherwise it is simple train.
5. The seat numer is not supported because of the database efficiency.
6. Random generation of entrance because we can't acquire the data.
7. The high-speed railway have 1 first seat carriage, 7 second seat carriage and 1 business seat carriage. The simple train have 6 hard seat carriage, 1 soft seat carriage, 5 hard sleep seat carriage and 1 soft sleep seat carriage.
8. If the carriage are of the same type, they have the same count of seats. Hard seat: 118; Soft seat: 118; Business seat: 28; Hard sleep seat: 66; soft sleep seat: 36; First seat: 54; Second seat: 88; Super seat: 28.
9. The price of each ticket equals $(max(start\ mileage,\ real\  mileage)\ \times\ seat\ rate\ )\ \times\ (float\ rate\ +\ insurance\ rate)$
10. Students always discount total price.

### 2. Database

##### 	Data Acquisition

1. Get the real train code from 12306 official [train_list.js](<https://kyfw.12306.cn/otn/resources/js/query/train_list.js>) file, then parse it to write all train code in train_information.csv file.
2. Write a web crawler using `jsoup` and `httpclient` to crawl the information about `train_code`, `train_sequence`, `station_name`, `arrive_time`, `depart_time`, `stay_time`, `days`, `mileage`, `hard_price`, `soft_price`, `business_price`, `hard_sleep_price(up/middle/down)`, `soft_sleep_price(up/down)`, `first_second_price(up/down)` from website [火车票网](<http://www.huochepiao.com/>) by api `http://search.huochepiao.com/chaxun/resultc.asp?txtCheci= + train_code + &cc.x=0&cc.y=0`, because the information in [12306](<https://www.12306.cn/index/>) are enciphered. Some trains have been suspended, we have to obtain the data from other api `http://search.huochepiao.com/oldcheci/ + train_code` and `http://search.huochepiao.com/checiold/ + train_code` . Finally parse them we get 10341 csv files about trains with their information.
##### 	Data Processing
1. Design all the table to satisfy the demand of 12306.
 ![](https://codimd.s3.shivering-isles.com/demo/uploads/upload_750fc16213481983a3fab94366305e75.png)
2. Wash the data because there are many invalid data.
3. Format all tables initially with the data.
4. Push the database to the ECS server.

### 3. Code Design
##### 	Front-End Design
1. Try to learn how to develop a Wechat mini-program.


##### 	Front-End Design
1. Use Mybatis Plus framwork to simplify the code.
(杰鸽你自己写)





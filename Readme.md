# <center>SUSTech_Database_Project_2</center>

Developer: [Zhong Wanli](https://github.com/zoom1111) 11811526<br/>
Developer: [Tong Yijie](https://github.com/nanfang-wuyu) 11811512<br/>



### 1. Hypothesis

1. Each train has only one train code.
2. In normal conditions, the train schedule is the same every day.

### 2. Database

##### 	Data Acquisition

1. Get the real train code from 12306 official [train_list.js](<https://kyfw.12306.cn/otn/resources/js/query/train_list.js>) file, then parse it to write all train code in train_information.csv file.
2. Write a web crawler using `jsoup` and `httpclient` to crawl the information about `train_code`, `train_sequence`, `station_name`, `arrive_time`, `depart_time`, `stay_time`, `days`, `mileage`, `hard_price`, `soft_price`, `business_price`, `hard_sleep_price(up/middle/down)`, `soft_sleep_price(up/down)`, `first_second_price(up/down)` from website [火车票网](<http://www.huochepiao.com/>) by api `http://search.huochepiao.com/chaxun/resultc.asp?txtCheci=" + train_code + "&cc.x=0&cc.y=0`, because the information in [12306](<https://www.12306.cn/index/>) are enciphered. Some trains have been suspended, we have to obtain the data from other api `http://search.huochepiao.com/oldcheci/ + train_code` and `http://search.huochepiao.com/checiold/ + train_code` . Finally parse them we get 10341 csv files about trains with their information.

test fot github work...
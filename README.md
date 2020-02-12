# 红岩网校寒假Android方向第一次考核 2019213788-张桂源

## 一.APP简要介绍
### 1.功能
1.1.可以查看全国各地的天气情况，空气污染指数和生活指数.

1.2.不同的天气会有所对应的背景图.

1.3.可以同时管理多个城市，同时查看多个城市的天气情况.

1.4.可以添加，删除城市和设置默认城市

1.5.在无网络情况下，将加载上一次有网络时缓存的数据.

### 2.使用步骤 (gif图可能要加载很久,太慢啦...)
2.1.首次进入app时，默认城市为重庆，用户可以在随后的操作中设置自己的默认城市.

![main](https://github.com/zzz6332/redrockAndroidExam/blob/master/gif/main.gif)

2.2.在主活动通过menu进入管理城市的活动，在此活动中可以通过menu进行添加，删除城市和设置默认城市.

具体gif如下👇

添加城市

![add](https://github.com/zzz6332/redrockAndroidExam/blob/master/gif/add_city.gif)

删除城市

![delete](https://github.com/zzz6332/redrockAndroidExam/blob/master/gif/delete_city.gif)

设置默认城市

![defalt](https://github.com/zzz6332/redrockAndroidExam/blob/master/gif/default_city.gif)

PS：在没有添加城市的情况下进入删除城市和设置默认城市的活动时，是下面这样的👇

![no_add](https://github.com/zzz6332/redrockAndroidExam/blob/master/gif/no_add_city.gif)

2.3.添加城市了之后，可以在管理城市的界面点击任一城市，就可以跳转到点击的城市的具体天气情况界面(主活动).

![adapter_to_main](https://github.com/zzz6332/redrockAndroidExam/blob/master/gif/manager_to_main.gif)

2.4.如果添加了错误的城市，会显示添加错误

![add_wrong](https://github.com/zzz6332/redrockAndroidExam/blob/master/gif/add_wrong_city.gif)

2.5.在没有网络时，会加载上一次有网络时的数据(只有主活动才有...)，并且添加城市也会失败,但可以删除和设置默认城市.

👇这个是没有网络时的主活动 (图中数据更新时间是5点，但当前的时间是5点半).

![no_Internet_main](https://github.com/zzz6332/redrockAndroidExam/blob/master/gif/no_Internet_main.gif)

👇这个是没有网络时添加城市会失败

![no_Internet_add](https://github.com/zzz6332/redrockAndroidExam/blob/master/gif/no_Internet_add.gif)

写一个app，公开俺自己银行卡的收支动账信息，给爹妈查账用。

因为是小工程，所以没什么编码规范，代码文件写得非常放肆，不分activity, event, util啥包

主要功能是选择年月，向自己的服务器发送请求，获取响应数据，解析一条条的收支记录，转化为列表，显示。对于出错情况，会将异常信息字符串作为日志写入本地文件中。

涉及到的，可作为demo的知识点有：ListView自定义单项视图，okhttp请求与响应，json解析，文件写出，EventBus使用

采用的数据库是MongoDB，但是没有直接使用mongo驱动的原因在于，mongo java driver在进行身份验证时，需要的一个包在ART上不存在。这个问题从2015年至今未解决，大概率以后也不会解决，故在服务端采用http进行数据库查询代理
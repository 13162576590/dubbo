# dubbo
dubbo-serve

注意点

1.edu-service-user报错java.lang.ClassNotFoundException: org.springframework.web.context.ContextLoaderListener
右击工程，选中Build Path -> Deployment Assembly -> Add -> Java Build Path Entries -> Maven Dependencies

2.spring-mybatis.xml基于Druid数据库链接池的数据源配置,解密密码必须要配置的项需注释

3.在CentOS/RHEL 7上开启端口

#即时打开，这里也可以是一个端口范围，如1000-2000/tcp

firewall-cmd --add-port=2181/tcp

firewall-cmd --add-port=2888/tcp

firewall-cmd --add-port=3888/tcp

#写入配置文件

firewall-cmd --permanent --add-port=2181/tcp

firewall-cmd --permanent --add-port=2888/tcp

firewall-cmd --permanent --add-port=3888/tcp


#重启防火墙
firewall-cmd --reload 

firewall-cmd --zone=public --add-port=2181/tcp --permanent

firewall-cmd --zone=public --add-port=2888/tcp --permanent

firewall-cmd --zone=public --add-port=3888/tcp --permanent

firewall-cmd --reload 

查看已经开放的端口：

firewall-cmd --list-ports

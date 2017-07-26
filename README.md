# dubbo
dubbo-serve

端口规划：

linux1:

httpd服务启动

service httpd start 搜索启动

service httpd restart 重新启动

service httpd stop 停止服务

svn 9000  admin/admin

sonarqube 9090  admin/admin   /root/sonarqube-5.6.6/bin/linux-x86-64/sonar.sh start

nexus 8081  admin/admin123   /root/nexus/nexus-2.11.2-03/bin 这很奇怪，把防火墙关闭，页面居然访问不了，打开防火墙，把8081端口开放居然可以访问了

fastDFS 22122

linux2:

zookeeper 2881

dubbo-admin 8080 guest/guest

dubbo-monitor/ 8020 guest/guest 防火墙未k

activemq 8161、61616   admin/admin

redis 6379 

fastDFS 22122

linux3:

hudson 8082  admin/admin(装在性能较好机器)

服务启动：
service httpd start

jdk版本：1.7

消费者：只有在访问页面的情况下才注册到zookeeper注册中心

svn授权

# cd /svn
# chown -R apache.apache wusc_edu
# chmod -R 777 wusc_edu

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

查询防火墙状态：firewall-cmd --state

关闭防火墙：systemctl stop firewalld.service

开启防火墙： systemctl start firewalld.service

4.mysql远程授权问题

Mac用brew安装mysql，解决远程连接授权问题

设置远程访问：

(1)、mysql -u root -p  ip:192.168.1.110 123

(2)、grant all privileges on *.* to 'root'@'%' identified by 'root' with grant option;

flush privileges;

(3)、打开/usr/local/Cellar/mysql/5.7.16/下的homebrew.mxcl.mysql.plist文件，去掉

--bind-address=127.0.0.1

(4)、brew services stop mysql

brew services start mysql

5.安装svn遇到困难

每次启动httpd报以下错误：
Job for httpd.service failed because the control process exited with error code. See "systemctl status httpd.service" and "journalctl -xe" for details.

错误原因：

修改/etc/httpd/conf.d/subversion.conf(没有则新建)，內容为
忘记加LoadModule dav_svn_module     modules/mod_dav_svn.so
LoadModule authz_svn_module   modules/mod_authz_svn.so
这两行

subversion.conf完整内容

#必须
LoadModule dav_svn_module     modules/mod_dav_svn.so    
#必须
LoadModule authz_svn_module   modules/mod_authz_svn.so
<Location /repo>
	DAV svn
	SVNListParentPath on
	SVNParentPath /svn
	AuthType Basic
	Satisfy Any
	AuthName "Subversion repositories"
    AuthUserFile /svn/passwd.http       
    AuthzSVNAccessFile /svn/authz 
	Require valid-user
</Location>
RedirectMatch ^(/svn)$ $1/

4.sonarqube集成出现问题

eclipse:clean install sonar:sonar

[WARNING] The requested profile "pom.xml" could not be activated because it does not exist.
[ERROR] Failed to execute goal org.sonarsource.scanner.maven:sonar-maven-plugin:3.3.0.603:sonar (default-cli) on project edu-common: SCM provider autodetection failed. Both git and svn claim to support this project. Please use sonar.scm.provider to define SCM of your project. -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoExecutionException

解决方案：登录sonarqube管理，找到SCM，设置为true

[ERROR] SonarQube server [http://localhost:9090/sonarqube] can not be reached
解决方案：登录sonarqube管理，找到SCM，设置为true
System Configurations -> Sonar  
Server URL	http://192.168.1.110:9090/sonarqube


WrapperSimpleApp: Unable to locate the class org.sonar.application.App: java.lang.UnsupportedClassVersionError: org/sonar/application/App : Unsupported major.minor version 52.0

sonarqube-5.6.6请使用jdk1.8，此处针对Linux
cd sonarqube-5.6.6/conf 
vi wrapper.conf 
linux: 
wrapper.java.command=/opt/jdk1.8.0_11/bin/java 

5.fastDFS分布式文件系统

 启动 Tracker:
# /etc/init.d/fdfs_trackerd start

关闭 Tracker:
# /etc/init.d/fdfs_trackerd stop

启动 Storage:
# /etc/init.d/fdfs_storaged start

关闭 Storage:
# /etc/init.d/fdfs_storaged stop

6.redis
./redis-trib create --replicas 1  192.168.1.114:7111 192.168.1.110:7112 192.168.1.111:7113 192.168.1.114:7114 192.168.1.110:7115 192.168.1.111:7116 

7.keepalived启动失败问题
[root@centos-linux-7 keepalived]# service keepalived start 
Starting keepalived (via systemctl):  Job for keepalived.service failed because the control process exited with error code. See "systemctl status keepalived.service" and "journalctl -xe" for details.
                                                           [FAILED]

解决方案
[root@centos-linux-7 init.d]# cd /usr/sbin/
[root@centos-linux-7 sbin]# rm -f keepalived 
[root@centos-linux-7 sbin]# cp /usr/local/keepalived/sbin/keepalived  /usr/sbin/
[root@centos-linux-7 sbin]# service keepalived start 
Starting keepalived (via systemctl):                       [  OK  ]
[root@centos-linux-7 sbin]# 

8.tracker  114  111
http://192.168.1.114:8888/group1/M00/00/00/wKgBblkq-tmAbIQYAAVFOL7FJU4.tar.gz
http://192.168.1.111:8888/group1/M00/00/00/wKgBblkq-tmAbIQYAAVFOL7FJU4.tar.gz

http://192.168.1.111:8888/group1/M00/00/00/wKgBclkq_AmAAKKGAAVFOL7FJU4.tar.gz

http://192.168.1.111:8888/group2/M00/00/00/wKgBb1krjwGAc3erAAVFOL7FJU4.tar.gz

http://192.168.1.111:8888/group2/M00/00/00/wKgBb1krkPeAIucQAAVFOL7FJU4.tar.gz

/etc/init.d/fdfs_storaged restart

http://192.168.1.111:8888/group2/M00/00/00/wKgBb1kr0C-AfKQNAAVFOL7FJU4.tar.gz

http://192.168.1.110:8888/group2/M00/00/00/wKgBb1kr0C-AfKQNAAVFOL7FJU4.tar.gz

8.1 tracker_nginx
/usr/local/tracker_nginx/sbin/ngnix

http://192.168.1.114:8000/group2/M00/00/00/wKgBb1kr0C-AfKQNAAVFOL7FJU4.tar.gz
http://192.168.1.111:8000/group2/M00/00/00/wKgBb1kr0C-AfKQNAAVFOL7FJU4.tar.gz

8.2 virtual_ngnix

./configure --prefix=/usr/local/virtual_ngnix

netstat –apn | grep 88

9. web集群
bank-receive-tomcat     7081
boss-tomcat             7082   7072  7092
gateway-tomcat          7083
notify-receive-tomcat   7084
portal-tomcat           7085
shop-tomcat             7086
trade-tomcat            7087
dubbo-admin-tomcat      7088
jenkins-admin-tomcat    7089


    ## web-boss
    upstream web-boss {
         server 192.168.1.114:7082 weight=1 max_fails=2 fail_timeout=30s;
         server 192.168.1.101:7082 weight=1 max_fails=2 fail_timeout=30s;
         server 192.168.1.110:7082 weight=1 max_fails=2 fail_timeout=30s;
    }

	## web-boss Cluster
	location /pay-web-boss {
	    root   html;
	    index  index.html index.htm;
	    proxy_pass  http://web-boss/pay-web-boss;
	    proxy_set_header Host  $http_host;
	    proxy_set_header Cookie $http_cookie;
	    proxy_set_header X-Real-IP $remote_addr;
	    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
	    proxy_set_header X-Forwarded-Proto $scheme;
	    client_max_body_size  100m;
	}

10.添加服务开机启动

a、进入/etc/init.d目录命令:cd /etc/init.d

b、拷贝nexus命令:cp /usr/local/nexus/nexus-2.11.1-01/bin/nexus ./nexus 、3、赋权命令:chmod 755 /etc/init.d/nexus

c、添加服务命令:chkconfig --add nexus

d、设置开机启动命令:chkconfig --levels 345 nexus on 

e、然后我们进行编辑/etc/init.d下的nexu文件命令:vim /etc/init.d/nexus
修改如下内容:
RUN_AS_USER=root NEXUS_HOME="/usr/local/nexus/nexus-2.11.1-01" PIDDIR="${NEXUS_HOME}"

f、进行编辑nexus安装目录下的wrapper.conf文件命令:
vim /usr/local/nexus/nexus-2.11.1-01/bin/jsw/conf/wrapper.conf 注意修改jdk文件路径:wrapper.java.command=/usr/local/jdk1.7/bin/java

g、最后我们执行:service nexus start (restart、stop) 

h、Reboot重启服务，开机时我们发现nexus服务自动已启动!

11.GitLab安装配置

a. 安装依赖软件

yum -y install policycoreutils openssh-server openssh-clients postfix

b.设置postfix开机自启，并启动，postfix支持gitlab发信功能

systemctl enable postfix && systemctl start postfix

c.下载gitlab安装包，然后安装

centos 7系统的下载地址:https://mirrors.tuna.tsinghua.edu.cn/gitlab-ce/yum/el7

wget https://mirrors.tuna.tsinghua.edu.cn/gitlab-ce/yum/el7/gitlab-ce-8.0.0-ce.0.el7.x86_64.rpm

rpm -i gitlab-ce-8.0.0-ce.0.el7.x86_64.rpm

d.修改gitlab配置文件指定服务器ip和自定义端口：

vim  /etc/gitlab/gitlab.rb

修改external_url: http://192.168.1.110:8082/  

e.重置并启动GitLab

执行：

gitlab-ctl reconfigure

gitlab-ctl restart

f.访问 GitLab页面

初始账户: root 密码: 5iveL!fe

第一次登录修改密码

g.设置gitlab发信功能，需要注意一点：

发信系统用的默认的postfix，smtp是默认开启的，两个都启用了，两个都不会工作。

我这里设置关闭smtp，开启postfix

关闭smtp方法：vim /etc/gitlab/gitlab.rb

找到#gitlab_rails['smtp_enable'] = true 改为 gitlab_rails['smtp_enable'] = false

修改后执行gitlab-ctl reconfigure

另一种是关闭postfix，设置开启smtp，相关教程请参考官网https://doc.gitlab.cc/omnibus/settings/smtp.html

测试是否可以邮件通知：

登录并添加一个用户，我这里使用qq邮箱添加一个用户

参考原链接： http://www.cnblogs.com/wenwei-blog/p/5861450.html

安装过程中错误如下：
   Error executing action `create` on resource 'user[GitLab user and group]'
   报错图片见GitLab_01

user['username'] = "git"；user['group'] = "git"修改为user['username'] = "gitlab"；user['group'] = "gitlab"再次gitlab-ctl reconfigure不报错了，然后执行gitlab-ctl restart








# 应用环境配置

---

## 配置项

* [x] 1. Tomcat 8.0安装
* [x] 2. JKS证书签发与Tomcat SSL配置
* [x] 3. MySQL
* [x] 4. 防火墙端口开放设置

---

## 配置流程

### Tomcat 8.0安装

**主要参考**：[Linux(Centos)之安装tomcat并且部署Java Web项目](https://www.cnblogs.com/hanyinglong/p/5024643.html)
#### 操作日志
* June 25th, 16:51
  * 运行`tomcat/bin/startup.sh`后，系统提示`Tomcat started.`，但从Chrome访问`http://139.196.162.127:8080/`显示响应时间过长；
    * 修改`/etc/sysconfig/iptables`，添加行`-A INPUT -m state --state NEW -m tcp -p tcp --dport 8080 -j ACCEPT`，尝试重启防火墙，仍然无效；
    
    * 使用`systemctl stop firewalld.service`停用防火墙，无效；
    
    * 使用`netstat -ntpl`，
      ```
      Proto Recv-Q Send-Q Local Address           Foreign Address         State       PID/Program name    
      tcp        0      0 0.0.0.0:8009            0.0.0.0:*               LISTEN      4334/java           
      tcp        0      0 0.0.0.0:8080            0.0.0.0:*               LISTEN      4334/java 
      ```
    
    * 使用`curl http://localhost:8080`时获得提示`Connected`，TCP握手已成功，但未接收到返回的数据包；
    
    * 尝试使用`./bin/shutdown.sh`重启Tomcat，报错
    
      ```
      [root@izuf60e8oyd5hz4naotzagz conf]# ../bin/shutdown.sh
      Using CATALINA_BASE:   /usr/local/tomcat
      Using CATALINA_HOME:   /usr/local/tomcat
      Using CATALINA_TMPDIR: /usr/local/tomcat/temp
      Using JRE_HOME:        /usr
      Using CLASSPATH:       /usr/local/tomcat/bin/bootstrap.jar:/usr/local/tomcat/bin/tomcat-juli.jar
      Jun 26, 2019 2:13:10 PM org.apache.catalina.startup.Catalina stopServer
      SEVERE: Could not contact [localhost:[8005]]. Tomcat may not be running.
      Jun 26, 2019 2:13:10 PM org.apache.catalina.startup.Catalina stopServer
      SEVERE: Catalina.stop: 
      java.net.ConnectException: Connection refused (Connection refused)
      	at java.net.PlainSocketImpl.socketConnect(Native Method)
      	at java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:350)
      	at java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:206)
      	at java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:188)
      	at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:392)
      	at java.net.Socket.connect(Socket.java:589)
      	at java.net.Socket.connect(Socket.java:538)
      	at java.net.Socket.<init>(Socket.java:434)
      	at java.net.Socket.<init>(Socket.java:211)
      	at org.apache.catalina.startup.Catalina.stopServer(Catalina.java:504)
      	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
      	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
      	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
      	at java.lang.reflect.Method.invoke(Method.java:498)
      	at org.apache.catalina.startup.Bootstrap.stopServer(Bootstrap.java:406)
      	at org.apache.catalina.startup.Bootstrap.main(Bootstrap.java:498)
      
      ```
    
      注意到`SERVER: Could not contact [localhost:[8005]]. Tomcat may not be running`，结合`netstat -ntpl`的返回情况，`8005`端口并未启用，则判断Tomcat有可能未正常启动；
  
* June 26th, 14:07

  * 在虚拟机上安装OpenJDK 1.8和相同版本的Tomcat，顺利完成，使用浏览器访问`localhost:8080`正常显示界面；

    * 使用`netstat -ntpl`命令可以看到

      ```
      Proto Recv-Q Send-Q Local Address           Foreign Address         State       PID/Program name    
tcp6       0      0 127.0.0.1:8005          :::*                    LISTEN      9954/java           
tcp6       0      0 :::8009                 :::*                    LISTEN      9954/java           
tcp6       0      0 :::8080                 :::*                    LISTEN      9954/java    
      ```

      结合前文在云服务器上`8005`端口未启用，使用`shutdown.sh`终止Tomcat失败的情况，可以更加确信云服务器上的Tomcat未能正常启动；
      
    * 尝试重装云服务器上的JDK，再试安装；
  
* June 26th, 15:15

  * 使用`firewall-cmd --zone=public --add-port=8080/tcp --permanent`开启8080端口后，`curl -v http://localhost:8080`成功，但Windows端`curl -v http://139.196.162.127:8080/`失败：

    ```
    *   Trying 139.196.162.127...
    * TCP_NODELAY set
    * connect to 139.196.162.127 port 8080 failed: Timed out
    * Failed to connect to 139.196.162.127 port 8080: Timed out
    * Closing connection 0
    curl: (7) Failed to connect to 139.196.162.127 port 8080: Timed out
    ```
  
* 目前使用`netstat -ntl`发现Tomcat已成功启用`8005`端口；
  
* June 26th, 15:28

  * 修改`/etc/sysconfig/iptables`，修改如下：
  
    ```iptable
    :INPUT ACCEPT [0:0] 										    # NEW ADDED
    :FORWARD ACCEPT [0:0]											# NEW ADDED
    :OUTPUT ACCEPT [0:0]											# NEW ADDED
    # Added by Jingo Lan for InfoSec Lesson, Tomcat
    -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT 		# NEW ADDED 
    -A INPUT -p icmp -j ACCEPT 										# NEW ADDED 
    -A INPUT -i lo -j ACCEPT										# NEW ADDED
    -A INPUT -m state --state NEW -m tcp -p tcp --dport 8080 -j ACCEPT 
    -A INPUT -j REJECT --reject-with icmp-host-prohibited 			# NEW ADDED
    -A FORWARD -j REJECT --reject-with icmp-host-prohibited 		# NEW ADDED
    COMMIT
    ```
  
    使用`service iptables reatart`后，从远端访问`http://139.196.162.127:8080/`成功；
    
  * 然鹅实际上经过验证后发现，其实本次对`iptables`的修改并无必要；保留`-A INPUT -m state --state NEW -m tcp -p tcp --dport 8080 -j ACCEPT`即可，其余维持原状；
  
#### 总结

* Tomcat安装目录：`/usr/local/tomcat`
* 目前来看，真正起到作用的是命令`firewall-cmd --zone=public --add-port=8080/tcp --permanent`和对`iptables`的修改（添加行：`-A INPUT -m state --state NEW -m tcp -p tcp --dport 8080 -j ACCEPT`）；
* 启动Tomcat时的注意事项，建议在运行命令`/usr/local/tomcat/bin/startup.sh`前，开启另一终端，执行`watch -n 1 netstat -ntpl`，监视端口`8005`的启用情况，因为直到`8005`被Tomcat顺利启用，Tomcat才算开启完成；

### JKS证书签发与Tomcat SSL配置

**主要参考**：[tomcat https配置方法(免费证书)](https://www.jianshu.com/p/a55590f486a2)

#### 操作日志

* June 27th, 13:17

  * 使用命令`keytool -genkey -v -alias infosec -keyalg RSA -validity 3650 -keystore /usr/local/pki/infosec.keystore`以生成JSK证书，证书细节内容如下：

    ```
    Enter keystore password: infosec 
    Re-enter new password: infosec
    What is your first and last name?
      [Unknown]:  InfoSec
    What is the name of your organizational unit?
      [Unknown]:  InfoSec
    What is the name of your organization?
      [Unknown]:  InfoSec
    What is the name of your City or Locality?
      [Unknown]:  InfoSec
    What is the name of your State or Province?
      [Unknown]:  InfoSec
    What is the two-letter country code for this unit?
      [Unknown]:  IS
    Is CN=InfoSec, OU=InfoSec, O=InfoSec, L=InfoSec, ST=InfoSec, C="IS" correct?
      [no]:  yes
    
    ```

  * 更改`tomcat/conf/server.conf`文件，添加内容：

    ```
        <Connector port="8443" protocol="org.apache.coyote.http11.Http11NioProtocol"
                   maxThreads="150" SSLEnabled="true" scheme="https" secure="true">
            <SSLHostConfig>
                <Certificate certificateKeystoreFile="/usr/local/pki/infosec.keystore"
                             keystorePass="infosec"
                             sslProtocols="TLS"
                             type="RSA" />
            </SSLHostConfig>
        </Connector>
    ```

  * 结果就是连不上；

* June 27th, 15:19

  * 忘了改`iptables`和阿里云安全策略；

  * 更改`tomcat/conf/server.conf`文件，上述内容修改为：

    ```
    <Connector port="443" protocol="HTTP/1.1"
                   maxThreads="150" SSLEnabled="true" scheme="https" 
                   secure="true"
                   KeystoreFile="/usr/local/pki/infosec.keystore"
                   keystorePass="infosec"
                   clientAuth="false"
                   sslProtocols="TLS"
                   type="RSA" />
    ```

  * `/etc/sysconfig/iptable`，添加：

    ```
    -A INPUT -m state --state NEW -m tcp -p tcp --dport 443 -j ACCEPT
    ```

  * `firewall-cmd --zone=public --add-port=443/tcp --permanent`配置防火墙；

  * `https://139.196.162.127/`访问成功；

#### 总结

* 对`tomcat/conf/server.conf`文件修改：

  * | 属性         | 描述                                                         |
    | ------------ | :----------------------------------------------------------- |
    | clientAuth   | 如果设为true，表示Tomcat要求所有的SSL客户出示安全证书，对SSL客户进行身份验证（双向验证） |
    | keystoreFile | 指定keystore文件的存放位置，可以指定绝对路径，也可以指定相对于<CATALINA_HOME>（Tomcat安装目录）环境变量 的相对路径。如果此项没有设定，默认情况下，Tomcat将从当前操作系统用户的用户目录下读取名为“.keystore”的文件。 |
    | keystorePass | 指定keystore的密码，如果此项没有设定，在默认情况下，Tomcat将使用“changeit”作为默认密码。 |
    | sslProtocol  | 指定套接字（Socket）使用的加密/解密协议，默认值为TLS，用户不应该修改这个默认值。 |
    | ciphers      | 指定套接字可用的用于加密的密码清单，多个密码间以逗号（,）分隔。如果此项没有设定，在默认情况下，套接字可以使用任意一个可用的密码。 |
* **记得改`iptables`和防火墙，以及阿里云的安全策略**


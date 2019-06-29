# InfoSec课设报告

## 服务器安全设计部分

### 1. 部署结构拓扑图

![拓扑部署图](D:\OneDrive\Documents\University Lessons\InfoSec\LessonDesign\部署拓扑图.png)

### 2. 设计思路

1. 主要需求：
   1. 解决因业务服务器的开发端口在外网环境下暴露而带来的安全隐患；
   2. 解决开发端口对在外网环境下的已授权开发人员的可见性；
2. 主要工具与技术：
   1. 阿里云ECS组网机制；
   2. 阿里云ECS安全组策略机制；
   3. Linux iptables服务；
   4. 由OpenVPN提供的VPN支持；
   5. 由EasyRSA提供的授权与认证服务；
3. 功能概述：
   1. 生产环境下，阿里云ECS安全组策略和Business Server iptables开放8080端口，向外网提供业务服务；开发端口，如MySQL的3306等关键安全端口在Business Server iptables中予以开放，在ECS安全组策略中不予开放，实现其对外网的不可见；
   2. 开发环境下，ECS组网内的OpenVPN Server提供VPN Server服务，ECS安全组策略开放提供VPN服务所需端口(UDP 1194)；处于外网环境下的已授权开发人员和Business Server作为Client端加入该VPN，已授权开发人员即可通过Business Server在VPN内的虚拟IP直接对其进行访问；

### 3. 部署与配置

1. 端口控制

      | PORT | ECS组策略 | VPN Server | Business Server |
      | ---- | --------- | ---------- | --------------- |
      | 8080 | ◉         | ⚪          | ◉               |
      | 22   | ◉         | ◉          | ◉               |
      | 1194 | ◉         | ◉          | ◉               |
| 3306 | ⚪         | ⚪          | ◉               |
      
2. OpenVPN Server

      1. 配置iptables，如下：

            ```
            -A INPUT -i eth0 -m state --state NEW -p udp --dport 1194 -j ACCEPT
            -A INPUT -i tun+ -j ACCEPT
            -A FORWARD -i tun+ -j ACCEPT
            -A FORWARD -i tun+ -o eth0 -m state --state RELATED,ESTABLISHED -j ACCEPT
            -A FORWARD -i eth0 -o tun+ -m state --state RELATED,ESTABLISHED -j ACCEPT
            -t nat -A POSTROUTING -s 10.8.0.0/24 -o eth0 -j MASQUERADE
            -A OUTPUT -o tun+ -j ACCEPT
            
            -A INPUT -m state --state NEW -m tcp -p tcp --dport 22 -j ACCEPT
            ```

      2. 安装OpenVPN，并使用EasyRSA搭建PKI系统，具体操作流程参考实验4；

      3. 使用PKI生成VPN Server端证书与密钥；

      4. 使用PKI为Business Server生成VPN Client端证书与密钥；

      5. 使用PKI为开发人员生成VPN Client端证书与密钥；

      6. 配置OpenVPN Server，开启服务；

3. Business Server

      1. 正常部署与配置业务应用；

      2. 配置iptables，如下：

         ```
         -A INPUT -i eth0 -m state --state NEW -p udp --dport 1194 -j ACCEPT
         -A INPUT -i tun+ -j ACCEPT
         -A FORWARD -i tun+ -j ACCEPT
         -A FORWARD -i tun+ -o eth0 -m state --state RELATED,ESTABLISHED -j ACCEPT
         -A FORWARD -i eth0 -o tun+ -m state --state RELATED,ESTABLISHED -j ACCEPT
         -t nat -A POSTROUTING -s 10.8.0.0/24 -o eth0 -j MASQUERADE
         -A OUTPUT -o tun+ -j ACCEPT
         
         -A INPUT -m state --state NEW -m tcp -p tcp --dport 22 -j ACCEPT
         -A INPUT -m state --state NEW -m tcp -p tcp --dport 3306 -j ACCEPT
         -A INPUT -m state --state NEW -m tcp -p tcp --dport 8080 -j ACCEPT
         ```

      3. 安装OpenVPN，导入由Server 1中的PKI的证书与其签发的Client端证书与密钥，启动OpenVPN Client服务，加入VPN；

4. ECS安全组策略：

      1. 按上述表格，新建并配置ECS安全组策略；
         ![ESC安全组策略](D:\OneDrive\Documents\University Lessons\InfoSec\LessonDesign\ESC安全组策略.png)
      2. 将OpenVPN Server和Business Server加入此ECS安全组策略；

5. 外网下的开发人员用机：

      1. 安装OpenVPN，导入由Server 1中的PKI的证书与其签发的Client端证书与密钥，启动OpenVPN Client服务，加入VPN；




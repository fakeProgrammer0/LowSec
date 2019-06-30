# Mysql 安全配置

## 1.账号安全

### 1.1.创建开发账号

为了避免从互联网访问 MySQL 数据库，确保只有特定主机才拥有访问特权。

创建 `infosec-dev` 账号，允许特定ip网络内的远程连接。

```mysql
CREATE USER 'infosec-dev'@'%' IDENTIFIED BY 'xxxxxxxx';
```

只授予 `infosec` 数据库的 `user` 表格的 `SELECT` 权限，保持最小权限原则。

```mysql
GRANT SELECT ON `infosec`.`user` TO 'infosec-dev';
FLUSH PRIVILEGES;
```

查看该账户拥有的权限：

```mysql
SHOW GRANTS FOR 'infosec-dev';
```

<img src="../assets/mysql-infosec-dev-grants.png">

### 1.2.创建生产环境账号

创建 `infosec-prod` 账号，只允许localhost访问，该账号提供给同一台服务器上的infosec应用访问数据库。

```mysql
CREATE USER 'infosec-prod'@'localhost' IDENTIFIED BY 'xxxxxxxx';
```

只授予 `infosec` 数据库的 `user` 表格的 `SELECT` 权限，保持最小权限原则。

```mysql
GRANT SELECT ON `infosec`.`user` TO 'infosec-prod'@'localhost';
FLUSH PRIVILEGES;
```

查看该账户拥有的权限：

```mysql
SHOW GRANTS FOR 'infosec-prod'@'localhost';
```

<img src="../assets/mysql-infosec-prod-grants.png">

### 1.3.移除匿名账户和废弃账户

有些MySQL数据库的匿名用户的口令为空。因而，任何人都可以连接到这些数据库。可以用下面的命令进行检查：

~~~sql
select * from mysql.user where user="";
~~~
















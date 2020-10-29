- SHOW DATABASES;
- SHOW CRATE DATABASE MYSQL;
- CREATE DATABASE TEST1;
- CREATE DATABASE IF NOT EXISTS DB2;
- CREATE DATABASE DB3 CHARACTER SET GBK;
- ALTER DATABASE DB3 CHARACTER SET UTF8;

## 修改

- ALTER DATABASE DB3 CHARACTER SET UTF8;

### 删除

- DROP DATABASE DB3;

- DROP DATABASE IF EXISTS DB3；

### 使用

USE DATABASE;

  

### 操作表

- SHOW TABLES;

- 查询表结构：DESC 表名

- 创建表：CREATE  TABLE 表名（列名：对应类型）；

  - CREATE TABLE STUDENT(

    ID INT,

    NAME VARCHAR(32),

    AGE INT,

    SCORE DOUBLE(4,1),

    BORNDATE DATE,

    INSERTTIME TIMESTAMP

    )

  - CREATE TABLE STU LIKE STUDENT

- 删除表：DROP TABLE DB2;

  - DROP TABLE IF EXISTS DB2;

- 复制表：CREATE TABLE 表名 LIKE 被复制的表名;

- 修改表：

  - 修改表名
    - alter table 表名 RENAME TO 新的表名
  - 修改表的字符集
    - alter table 表名 character set 字符集名称；
  - 添加一列
    - alter table 表名 add 列名 数据类型
  - 修改列的名称和类型
    - alter table 表名 change 列名 新的列名 类型；
    - alter table 表名 modify 列名 新的类型；
  - 删除列
    - alter table 表名 drop 列名 ；

  
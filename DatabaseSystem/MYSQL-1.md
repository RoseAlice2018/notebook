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




### DML

#### 添加数据

- 语法：
  - insert into 表名 （列名1，列名2，......,列名n) values(值1,值2,)
    - 列名可以省略,默认给所有字段添加
    - 除数字类型外,需添加引号

#### 删除数据

- 语法
  - delete from 表名 [ where 条件]
    - 如果不加条件,会删除所有记录
  - TRUNCATE TABLE 表名 ;
    - 删除表,然后建立一个同样的空表

#### 修改数据

- 语法
  - update 表名 set  列名1 = 值1 ,列名2 = 值2,.....[where 条件]



### DQL

#### 查询表中记录

- select * from 表名

#### 排序查询

- 语法

  - order by 排序字段1 排序方式1 排序字段2 方式字段2

#### 分组查询

- 语法
  - group by 分组字段；
  - 注意：
    - 分组之后的查询字段：分组字段/聚合函数
  - 限定条件
    - WHERE和Having区别
      - where 在分组之前进行限定 如果不满足条件则不参与分组
      - having 在分组之后进行限定 如果不满足结果则不会被查询
      - where 后不可以跟聚合函数 ， having可以进行聚合函数的判断

#### 分页查询



-  语法
  - limit 开始的索引 每页查询的条数



#### 基础查询

- 语法
  - select 字段列表
  - from 表名列表
  - where 条件列表
  - group by 分组字段
  - having 分组之后的条件限定
  - order by 排序
  - limit 分页限定
- 查询多个字段
  - select address ，ID from student；
- 去除重复
  - select distinct address from student；
- 计算列
  - select NAME，english，math+English FROM student；
  - select NAME，english，math+ifnull（English，0） FROM student；
- 别名
  - select NAME，english，math+ifnull（English，0）AS sum FROM student；

#### 条件查询

- 运算符
  - < , > , < = , > = , = , < >
  - BETWEEN .... AND
  - IN(set)
  - LIKE
  - IS NULL
  - and
  - or
  - not

#### 约束

- 主键约束：primary key
  - 含义：非空且唯一
  - 一张表只能有一个字段为主键
  - 主键就是表中记录的唯一标识
- 非空约束： not null
  - 1. 创建表时加非空约束
    2. alter 改表
- 唯一约束：  unique
  - 值不可重复
- 外键约束： foreign key






## SQL

## 3.1SQL查询语言概述

SQL是数据库查询语言，有以下几个部分。

- DDL（数据定义语言）。定义关系模式，删除关系，修改关系模式，每个属性的取值类型、每个关系维护的索引集合、每个关系的安全性和权限信息、每个关系在磁盘上的物理存储结构。
- DML（数据操纵语言）。从数据库中查询信息，在数据库中插入元组、删除元组、修改元组的部分。
- 完整性：sql DDL包括定义完整性约束的命令，破坏完整性的更新是不被允许的。
- 视图定义： DDL包括定义视图的命令
- 事务控制： 包括定义事务的开始和结束的命令
- 嵌入式SQL和动态SQL：sql如何嵌入到通用编程语言。
- 授权：sql DDL包括定义对关系和视图的访问权限的命令。

### 3.2SQL的数据定义

#### 3.2.1 基本类型

- char(n) 固定长度字符串。空格补位
- varchar(n) 可变长度字符串。不补位
- int
- smallint
- numeric(p,d)定点数：该数有p位数字，其中d位在小数点右边
- real,double precision :浮点数和双精度浮点数
- float(n)精度至少为n位的浮点数

char和char比较时，长度不同追加空格 。char和varchar比较可能返true、false。建议使用varchar而不是char来解决问题。

#### 3.2.2 基本模式定义

完整性约束：

- primary key(A1,A2...An)

- foreigh key(A1,A2...An) references:

- not null：该属性不允许为空值

  ```
   CREATE TABLE instructor
   (
   	ID varchar(5),
   	name varchar(20) not null,
   	dept_name varchar(20),
   	salary numeric(8,2),
   	primary key(ID),
   	foreign key(dept_name) references department
   );
  ```

- sql允许为属性指定默认值

  ```
  
  create table b(
      course_id varchar(20),
      tot_cred numeric(3,0) default 0,
       foreign key(course_id) references d
  );
  ```

- insert。可以在查询结果的基础上加入元组。在执行插入前执行完select语句很重要。没有被赋值的属性为空。

  ```
  insert into instrutor values(10211,"wang","math",121212.20,1234);
  ```

- delete,删除关系中所有元祖。(不删除表)

  ```
  delete from instructor
  ```

  

- drop删除表

  ```
  drop table instructor
  ```

- alter 在表中增加(ADD)、删除列(Drop)。新属性的值为null.

  ```
  alter table r add A,D;(A name,D domain)
  alter table r drop A;
  ```

### 3.3 sql查询的基本结构



#### 3.3.1 单关系查询

- select Ａ　ｆｒｏｍ　Ｂ
- 一般SQL不去重,distinct 显示去重，all显示不去重
- 支持+，-，*，/。逻辑词and,or,not。比较运算符<,>,<=,>=,<>.

#### 3.3.2 多关系查询

- from语句定义了一个在该语句中所列出关系的笛卡尔集。where中的谓词限制笛卡尔集的集合。在属性前加前缀区分属性来自哪个关系。sql会通过只产生满足where子句的笛卡尔元素优化执行。

- 逻辑顺序  from--where--group by--having--order by--select  所有运算都是表运算，输入输出都是一个表。

  ```
  select name,course_id
  from instructor,teachers
  where instructor.ID=teaches.ID
  ```



#### 3.3.3 自然连接

-  只取在共同属性上取值相同的元组对。列出的属性顺序：共同的--第一个--第二个
- join...using需要一个属性列表，允许用户来确定哪些列相等

### 3.4 附加基本运算

- 更名运算 as.可以出现在select、from中
- 字符串运算。用双引号代表单引号。大小写敏感。串连||、upper(s)、lower(s)、trim(s).  like/not like模式匹配。百分号：匹配任意字串。_匹配任意一个字符.  允许转义字符，直接在特殊字符前面，表示特殊字符为普通字符，escape定义转义字符
  - SQL使用一对单引号来标识字符串,如果单引号是字符串的组成部分,就用两个单引号字符表示.
  - 字符串相等运算,大小写敏感.(有些数据库不敏感).
  - ||串联
  - upper(s) 转成大写
  - lower(s)转成小写
  - trim(s) 去掉字符串后面的空格
  - like/not like实现模式匹配
    - % :匹配任意子串
    - _:匹配任意一个字符
    - escape和'\' 定义转义字符: like 'ab\%cd%' escape '\'匹配所有以"ab%cd"开头的字符串
    - 
- order by. 默认升序  desc降序   asc升序 
- [not]between....and闭区间
- (a,b,...c)n维元组，可以按字典序比较运算



### 3.5 集合运算

均自动去重(加上all不去重).

- union 并运算

  ```
  (
  select course_id
  from section
  where semester = 'Fall' and year = 2009
  )
  union
  (
  select course_id
  from section
  where semester = 'Spring' and year = 2010
  )
  ```

- intersect 交运算

  ```
  (
  select course_id
  from section
  where semester = 'Fall' and year = 2009
  )
  intersect
  (
  select course_id
  from section
  where semester = 'Spring' and year = 2010
  )
  ```

  

- except 差运算

  ```
  (
  select course_id
  from section
  where semester = 'Fall' and year = 2009
  )
  except
  (
  select course_id
  from section
  where semester = 'Spring' and year = 2010
  )
  ```

#### 3.6空值

- 算术表达式的任一输入为空，结果为空。
- SQL设计控制任何比较运算的结果为unknown（既不是is null,也不是is not null),这创建了除true和false外的第三个逻辑值。
- 如果子句谓词对一个元组计算出false或unknown，则该院组不能加入结果集中。
- 用is null和not is null判断是否是null值。用is unknown和not is unknown判断是否是unknow值
- 当一个查询使用select distinct 语句时，重复元组将被去除。为了达到这个目的，当比较两个元组对应的属性值时，如果这两个元组都是非空且值相等，或者都是为空，那么它们是相同的。所以诸如{（A,null),(A,null)}这样的两个元组拷贝被认为是相同的，即使在某些属性上存在空值。使用distinct子句会保留这样的相同元组的一份拷贝。

### 3.7 聚集函数

- avg

- min

- max

- count

- sum

- sum和avg的输入必须是数字集

- 聚集函数以以下规则处理空值：除了count(*)外所有的聚集函数都忽略输入集合的空值。规定空集的count为0，其它所有聚集函数在输入为空集的情况下返回一个空值。

- Group By:group by子句给出的一个或多个属性是用来构造分组的,在group by子句的所有属性上取值相同的元组被分到一个组里.

  ```
  select dept_name, avg(salary) as avg_salary
  from instructor
  group by dept_name;
  ```

- 任何出现在select子句中，但没有被聚集的属性必须出现在group by中

- having : 

  - having子句中的谓词只有在形成分组后才起作用
  - 任何出现在having子句中，但没有被聚集的属性必须出现在group by中

- 使用关键字distinct可以删除重复元素，不允许在count(*)时使用distinct.

- group by子句中给出一个或多个属性是用来构造分组的。



### 3.8 嵌套子查询

- 子查询在where子句中，通常对成员资格、集合的比较以及集合的基数进行检查。

- 集合成员资格：in /not in 测试是否是集合中的成员。也可用于枚举集合，在SQL测试任意关系的成员资格也是可以的。

  ```
  mysql>
  mysql> select distinct course_id
      -> from section
      -> where semester='Fall' and year='2009'
      -> and course_id in(select distinct course_id
      ->                  from section
      ->                  where semester='Spring' and year=2010);
   
  mysql> select distinct name
      -> from instructor
      -> where name not in('Wang','Wen');
   
  mysql>
  mysql> select count(distinct ID)
      -> from takes
      -> where (course_id,sec_id,semester,year)in(select course_id,sec_id,semester,year
      ->                                          from teaches
      ->                                          where teaches.ID=10101);
  ```

- 集合的比较

  - 至少比某一个要大/小/相等用some表示。<>some不等价not in,=some等价in

  - 至少比所有要大/小/相等用all表示。<>all等价not in,=some不等价in

    ```
    mysql> select name
        -> from instructor
        -> where salary>some(select salary
        ->                  from instructor
        ->                  where dept_name='Biology')
        -> ;
     
    mysql> select dept_name
        -> from instructor
        -> group by dept_name
        -> having avg(salary)>all(
        ->             select avg(salary)
        ->             from instructor
        ->             group by dept_name);
    ```

- 空关系测试  exists 参数的子查询非空时返回true/not exists

- 来自外层循环的一个相关名称可以用在where的子查询中。使用了外层查询相关名称的查询为相关子查询。如果一个相关名称在外层和子查询中定义，则子查询中定义有效。

- 关系A包含关系B  not exists(B except A)

  ```
  select name
  from student
  where not exists(
      (
      select course_id
      from course
      where dept_name='Biology'
      )
      except
      (
      select T.course_id
      from takes as T
      where S.ID=T.ID
      )
  );
  ```

- 重复元组存在性测试。sql提供一个布尔函数，用于测试在一个子查询的结果中是否存在重复元组。如果作为参数的子查询结果中没有重复的元组、unique结构将返回true值。在空集上计算出真值。还有not unique.对一个unique测试结果为假的定义是，当且仅当在关系中存在这两个元组t1和t2,且t1=t2.由于在t1或t2的某个域为空时，判断t1=t2为假，所以尽管一个元组有多个副本，只要该元组有一个属性为空unique测试可能为真。

  ```
  select T.course_id
  from course as T
  where unique(select R.course_id
               from section as R
               where T.course_id=R.course_id and R.year=2009);
  ```

- from语句中的子查询。任何select-from-where表达式返回的结果都是关系，因而可以被插入到另一个select-from-where中任何关系可以出现的位置。

- 可以用as子句给子查询的结果关系起个名字。

- from子句嵌套的子查询中不能使用来自from子句其它关系的相关变量。加入lateral作为前缀，可以访问from子句中在它前面的表或子查询中的属性。

- with子句提供定义临时关系的方法。

- SQL允许子查询出现在单个值的表达式可以出现的任何地方，只要子查询只返回单个属性的单个元组；这样的子查询称为标量子查询。select、where、having子句都可以使用标量子查询。

  ```
  select dept_name
  from (select dept_name,avg(salary) as avg_salary
        from instructor
        group by dept_name)
        as dept_avg(dept_name,avg_salary)
  where avg_salary>42000;
   
  select dept_name
  from (select dept_name,avg(salary) as avg_salary
        from instructor
        group by dept_name)
  where avg_salary>42000;
   
  select name
  from instructor I1,lateral (select avg(salary) as avg_salary
                              from instructor I2
                              where I2.dept_name=I1.dept_name);
   
  with dept_total(dept_name,value) as
  (select sept_name,sum(salary)
   from instructor
   group by dept_name),
   
  dept_total_avg(value) as
  (select avg(value)
   from dept_total)
   
  select dept_name
  from dept_total,dept_total_avg
  where dept_total.val>=dept_total_avg.value;
  ```










# Linux C++

## GCC Command Option

|              Command Option              |                         Discription                          |
| :--------------------------------------: | :----------------------------------------------------------: |
|                    -E                    |                预处理指定的源文件，不进行编译                |
|                    -S                    |                 编译指定的源文件，不进行汇编                 |
|                    -C                    |             编译汇编指定的源文件，但是不进行链接             |
| -o  [file1] [file2] / [file1] -o [file2] |               将文件file2编译成可执行文件file1               |
|               -I directory               |                指定include包含文件的搜索目录                 |
|                    -g                    |        在编译时，生成调试信息，该程序可以被调试器调试        |
|                    -D                    |                   在程序编译时，指定一个宏                   |
|                    -w                    |                      不生成任何警告信息                      |
|                  -Wall                   |                       生成所有警告信息                       |
|                   -On                    | n（0~3）。编译器优化选项的4个级别，-o0表示没有优化，-o1为缺省值，-o3优化级别最高 |
|                    -l                    |                  在程序编译时，使用指定的库                  |
|                    -L                    |                   指定编译时，搜索库的路径                   |
|               - fpic/FPIC                |                       生成位置无关代码                       |
|                 -shared                  |              生成共享目标文件，通常简历共享库时              |
|                   -std                   |                      指定C标准 -std=c99                      |

## static library 

- 命名规则
  - Linux
    - lib：前缀（固定）
    - xxx：库名
    - .a：后缀（固定）
  - Windows：libxxx.lib
- 静态库制作
  - gcc 得到目标文件
  - 将.o文件打包
    - ar rcs libxxx.a xxx.o xxx.o
    - r - 将文件插入备存文件中
    - c  -建立备存文件
    - s - 索引
- 静态库的使用
  - gcc main.c -o app -I ./include/(头文件)  -l name -L ./lib(静态库)

## Dynamic Library

- 命名规则
  - Linux ： libxxx.so
    - lib : 前缀（固定）
    - xxx: 库名
    - .so : 后缀(固定)
    - 在Linux下是一个可执行文件
  - windows：libxxx.dll
- 动态库的制作
  - gcc 得到 .o文件，得到和位置无关代码
    - gcc -c -fpic/-FPIC a.c b.c
  - gcc 得到动态库
    - gcc -shared a.o b.o -o libxxx.so

## Makefile

- 文件命名

  - makefile / Makefile

- Makefile规则

  - 一个Makefile文件中可以有一个或多个规则
    - 目标  ... : 依赖 ...
      - 命令（shell命令）
    - 目标： 最终要生成的文件（伪目标除外）
    - 依赖：生成目标所需要的文件或者目标
    - 命令： 通过执行命令对依赖操作生成目标 （命令前必须Tab缩进）

- 工作原理

  - 命令在执行之前，需要先检查规则中的依赖是否存在
    - 如果存在，执行命令
    - 如果不存在，向下检查其他的规则，检查有没有一个规则是用来生成这个依赖的。如果找到了，则执行该规则中的命令。
  - 检测更新，在执行规则中的命令时，会比较目标和依赖文件的时间。
    - 如果依赖的时间比目标的时间晚，需要重新生成目标
    - 如果依赖的时间比目标的时间早，目标不需要更新，对应规则中的命令不需要执行。

- 变量

  - 自定义变量
    - 变量名 = 变量值 var = test
  - 预定义变量
    - AR ： 归档维护程序的名称，默认值ar
    - CC ： C 编译器的名称，默认值为cc
    - CXX： C++编译器的名称，默认值为C++
    - $@  :  完整的目标名称
    - $<   :   第一个依赖文件名称
    - $^    :  所有的依赖文件
  - 获取变量的值
    - $ （变量名 ）

  ```
  app : main.c a.c b.c
  	gcc -c main.c a.c b.c
  app : main.c a.c b.c
  	$ (cc) -c $^ -o $@
  ```

- 模式匹配

  - %.o : %.c
    - % : 通配符  ，匹配一个字符串
    - 两个%匹配的是同一个字符串

- 函数 $(wildcard PATTERN...)

  - 功能： 获取指定目录下指定类型的文件列表
  - 参数： PATTERN指的是某个或多个目录下的对应的某种类型的文件，如果有多个目录，一般使用空格间隔。
  - 返回：得到的若干个文件的文件列表，文件名之间使用空格间隔。

  ```
  eg:
  	$(wildcard *.c ./sub/*.c)( 当前目录下的*c和sub子目录下的*c)
  	return value a.c b.c c.c d.c e.c f.c
  ```


## GDB

- 启动和退出
  - gdb 可执行程序
  - quit 
- 给程序设置参数/获取设置参数
  - set args 10 20
  - show args
- GDB 使用帮助
  - help
- 查看当前文件代码
  - list/l （从默认位置显示）
  - list/l 行号 （从指定的行显示）
  - list/l  函数名 （从指定的函数显示）
- 查看非当前文件代码
  - list/l 文件名 ： 行号
  - list/l   文件名 ：函数名                                             
- 设置显示的行数
  - show list/listsize
  - set list/listsize 行数
- 设置断点
  - b/break 行号
  - b/break 函数名
  - b/break  文件名：行号
  - b/break   文件名：函数
- 查看断点
  - i/info  b/break
- 删除断点
  - d/del/delete 断点编号
- 设置断点无效
  - ena / enable 断点编号
- 设置条件断点 （一般用在循环的位置）
  - b/break 10 if i == 5
- 运行GDB程序
  - start （程序停在第一行）
  - run （遇到断点才停）
- 继续运行，到下一个断点停止
  - c/continue
- 向下执行一行代码
  - n/next
- 变量操作
  - p/print 变量名 （打印变量值）
  - ptype 变量名 （打印变量类型）
- 向下单步调试（遇到函数进入函数体）
  - s/step
  - finish （跳出函数体）
- 自动变量操作
  - display num（自动打印指定变量的值）
  - i/info display
  - undisplay 编号
- 其他操作
  - set var 变量名 = 变量值
  - until 跳出循环

## File I/O

 ### Open

- int open(const char* pathname,int flags);
- int open(const char* pathname,int flags,mode_t mode);
  - flags ： 对文件操作权限
    - 必选项： O_RDONLY,O_WRONLY,O_RDWR这三个之间是互斥的
    - 可选项： O_CREAT文件不存在，创建新文件
  - mode： 八进制的树，表示创建出的新的文件的操作权限，比如： 0775
    - 最终权限：mode & ~umask
- int close(int fd);
- ssize_t read(int fd,void* buf,size_t count);
- ssize_t write(int fd,const void* buf,size_t count);
- off_t lseek(int fd,off_t offset,int whence);
  - offset : 偏移量
  - whence:
    - SEEK_SET 
      - 设置文件指针的偏移量
    - SEEK_CUR
      - 设置偏移量：当前位置 + 第二个参数offset的值
    - SEEK_END
      - 设置偏移量：文件大小 + 第二个参数offset的值
  - 移动文件指针到头文件
    - lseek（fd，0，SEEK_SET)
  - 获取当前指针位置
    - lseek（fd，0，SEEK_CUR)
  - 获取文件长度
    - lseek（fd，0，SEEK_END)
  - 拓展文件长度
    - lseek(fd,100,SEEK_END); 在文件末尾添加100个字节
- int stat(const char* pathname,struct stat* statbuf);
- int lstat(const char* pathname,struct stat* statbuf);
- int access(const char* pathname,int mode);
- int chmod(const char* filename,int mode);
- int chown(const char* path,uid_t owner,gid_t  group);
- int truncate(const char* path,off_t length);
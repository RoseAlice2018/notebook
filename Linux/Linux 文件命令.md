## Linux 文件命令

**Chapter 5 ：文件目录初探**
**1.pwd：**
	print working directory
**2.cd filename**
	change directory 
eg:
	

```cpp
$cd source
$cd 
//返回主目录
$cd .
//进入当前目录
$cd ..
//返回上层目录
$cd ~
//返回用户主目录
$cd ~username
//进入用户的主目录中，前提拥有相关权限
```
**3.mkdir make directory**
eg:
```cpp
$mkdir demos
$mkdir source bin output

```
//一次建立多层目录
eg:

```cpp
$mkdir -p xx/yy/zz
$cd xx
```
**4. rmdir remove directory(must be empty)**
eg:

```cpp
$mkdir memos

```
**5.ls list**
**option:**

```cpp
-a 
-C
-F
-l
-m
-R
-s
```
**6.隐藏文件（dot开头的文件）**
$ls -a
**7.cat**
显示一个或多个文件的内容
cat filename filename
eg:

```cpp
$cat myfirst
$cat myfirst yourfirst
```
**8 rm filename**
option:
-i 删除文件前，要求确认
-r 删除指定目录以及目录下所有的文件和子目录
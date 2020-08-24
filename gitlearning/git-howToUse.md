## git 使用

### 创建版本库
	1.创建空目录
	2.git init 将这个目录变成Git可以管理的仓库。
	3.git add xxx.cpp 
	4.git commit -m "explanation"

```c++
$mkdir test
$cd test
$git init
$git add readme.txt
$git commit -m "write a readme"
```

### 添加远程库
1. 在GitHub建立一个同名repository

2. ```
   $ git remote add origin git@github.com:username/repositoryname.git
   $ git push -u origin master (first time)
   $ git push origin master (the latest change)
   ```



### 从远程库克隆
1. 远程库建立repository

2. ```
   $git clone git@github.com:username/repositoryname.git
   ```

   
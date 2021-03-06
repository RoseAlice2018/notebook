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
### 版本回退
1.git log (命令显示最近到最远的提交日志)
	--pretty=oneline(缩减信息,如果输出信息太多)
2.基础知识
	2.1HEAD表示当前版本
	2.2 HEAD^上一个版本
	2.3 HEAD^^上上一个版本
	2.4 HEAD~100 往前第100版本
3.回退到上一个版本.

```
$git reset --hard HEAD^
$git reset --hard commit_id
```
4. ```
   git reflog
   查看自己的每一次命令记录
   ```

5 git status
 	查看当前状态
6. git diff HEAD -- readme.txt
	查看当前工作区和版本库的最新版本的区别
### 撤销修改
1.

```
$ git checkout -- filename
```
两种情况：
第一种是file自修改后还没有add到暂存区，此时把文件在工作区的修改工作全部撤销。
第二种是file已经修改添加到暂存区，撤销回退到添加暂存区后的状态。
总之，就是让这个文件回到最近一次git commit 或 git add的状态。
2.

```
 $git reset HEAD file
```
将暂存区的修改撤销掉，重新放回工作区。

### 删除文件
本地：

```
$rm filename
```

git：

```
$git rm filename
%git commit -m ""
```

误删：（在本地错误删除某些文件，而此文件仍然在版本库里有）

```
$git checkout -- filename 
```

### 创建和合并分支
1. 创建并切换分支
	
2. ```
    $git checkout -b dev
    Switched to a new branch 'dev'
    git checkout命令加上-b参数表示创建并切换，相当于以下两条命令：
    ```
  ```

  ```

  相当于以下命令

  ```
  $git branch dev
  $git checkout dev
  Swicthed to branch 'dev'
  ```

  

  ```
$git branch
查看当前分支
git branch 命令会列出所有分支，当前分支前面会有一个*号
  ```

```
将dev分支合并到当前分支（$git checkout master 回到主分支）
$git merge dev
```

```
$git branch -d dev
deleted branch dev
```

#### Switch

```
$ git switch -c dev
创建并切换到新的dev分支
$ git switch master
```

#### summary

```
查看分支：git branch
创建分支：git branch name
切换分支：git checkout name / git switch name
创建加切换分支：git checkout -b name / git switch -c name
合并某分支到当前分支： git merge name
删除分支： git branch -d name
```


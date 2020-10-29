**1.启动Vi 编辑器：**
  Method1： vi return
  	   

 - ：w myfile[Return] 将缓冲区内容保存到myfile中
 - ： wq myfile [Return] ....+离开vi
 Method2：vi myfile [Return]
 		：w yourfile   将缓冲区内容保存到yourflle中，当前文件仍然为myfile

 **2.使用Vi启动选项**
 	
 - -**R**   Read only
 - vi -R filename  /   view filename
 - **-c**   将指定vi命令作为命令行的一部分执行
 - vi -c command filename
 - eg: vi -c /most myfirst

**3.编辑多个文件**

 - **vi filename1 filename2** ......
 - eg: vi file1 file2 file3
 - **:n** 启动下一个文件
 - **ar**   查看要同时编辑的文件名
 - **：e** filename  切换到新文件
 - **r**  filename 将指定文件副本插入到当前文件光标的下一行
 -**[a,b] w filename** 将当前文件的a-b行 写到另一个文件中
 - eg : 1,2 w temp

 **4.重排文本**


 - d 删除指定位置文本，保存到缓存区
 - y 复制指定文本到缓冲区
 - P 将指定缓冲区内容放到当前光标位置之上
 - p  之下
 - c  删除文本并进 inputmode
 命令组合P
 - dd+p  删除当前行，将被删除行放到当前光标之下
 - dd+P
 - yy+p  复制当前行，将复制行放入当前光标之下
 - yy+P


**5.vi操作符的域**

 - $  光标位置开始到当前行尾
 - 0   行首
 - e/w   光标位置开始到当前词尾
 - b    词首
 - d$ 
 - d0
 - c+ 域 先将指定文本清除，进入文本编辑模式
 - 


6.在vi中使用寄存器（缓冲区

-” np /nP"  粘贴数字寄存器内容

  

 

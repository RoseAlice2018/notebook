## 7.2 捕获异常
要想捕获一个异常，必须设置try/catch语句块。最简单的try语句块如下所示：
	try
	{
		code
		more code
		more code
	}
	catch (ExceptionType e)
	{
		handler for this type
	}

-  应当捕获那些知道如何处理的异常，而将那些不知道如何处理的异常继续进行传递。
-  如果想要传递一个异常，就必须在方法的首部添加一个throws说明符，以便告知调用者这个方法可能会抛出异常。

### 7.2.4 finally子句
 无论在try语句块中是否遇到异常，finally语句中的语句都会被执行。
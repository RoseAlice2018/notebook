###  7.1.1 异常分类
- JAVA异常派生于Throwable类的一个实例。
- Throwable下一层类分为两个：Error类和Exception类
- Error类用于描述JAVA运行时系统的内部错误和资源耗尽错误，应用程序不应抛出这种错误。
- Exception又有两个分支：RuntimeException和IOException
-  ** 由于程序本身错误导致的异常属于RuntimeException **
*派生于RuntimeException的异常包含下面几种情况 *
1.错误的类型转换
2.数组访问越界
3.访问null指针
** 不是派生于RuntimeException的异常**
1.试图在文件末尾后部读取数据
2.试图打开一个不存在的文件
3.试图根据给定的字符串查找class对象，而这个字符串表示的类根本不在。
-受查声明和非受查声明
1.派生于Error和RuntimeException类的异常称之为unchecked异常
2.其他异常称之为checked异常

### 7.1.2 声明受查异常
1. 方法应该在其声明其可能会抛出的异常。
	public FileInputStream(String name) throws FileNotFoundException
2. 如果有多个异常，那么就必须在方法的首部列出所有的异常，每个异常之间用‘，’隔开。

** 总之，一段程序应该尽可能的声明所有可能抛出的受查异常，而非受查异常要么是不可控制（Error），要么就应该避免发生（Runtime Exception）。**
如果子类中覆盖了超类的一个方法 声明的受查异常不能超过超类的声明受查异常。也就是说，子类的异常不能比超类更通用，如果超类没有声明异常，子类也不能声明。 

### 7.1.3 如何抛出异常
#### 对于一个已经存在的异常类
- 1）找到一个适合的异常类
- 2）创建这个类的一个对象
- 3）将对象抛出

### 7.1.4 创建异常类
定义一个派生于 Exception的子类即可。
	class FileFormException extends IOException
	{
	
	}






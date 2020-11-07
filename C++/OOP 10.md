## OOP 10
#### 名字空间的使用
- 引用名字空间中的成员有两种方式
- 方式一是在成员名前加上名字空间：
	<名字空间名>::<成员名>
- 方式二是利用预编译指示符using：
	using namespace <名字空间名>; 
### 友元
- 根据类的封装性，一般将数据成员声明为私有成员，外部不能直接访问，只能通过类的公有成员函数对私有成员进行访问。有时，需要频繁地调用成员函数来访问私有成员，这就存在一定的系统开销
- C++从高效的角度出发，提供友元机制，使被声明为友元的全局函数或者其他类可以直接访问当前类中的私有成员，又不改变其私有成员的访问权限
- 友元可以是一个全局函数，也可以是一个类的成员函数，还可以是一个类。如果友元是函数，则称为友元函数。如果友元是一个类，则称为友元类。友元类的所有成员函数都是友元函数，可以访问类的任何成员
友元声明以关键字friend开始，只能出现在被访问类的定义中。具体格式如下 
	friend <返回类型> <函数名>(<形参表>);
	friend class <类名>; 

```
class A;  class B { public: void BFunc(A& a); };
class C { public: void CFuncs( ); };
class A {
	friend const A operator+ (const A& lhs, const A& rhs);
	friend void  B::BFunc(A& a);
	friend class C;
	class D;
	friend class D;
	class D { //… };
public:
private:  int mValue;
};
const A operator+ (const A& lhs, const A& rhs);

```

### 嵌套类
- 嵌套类一般定义在外围类的类体内。嵌套类的成员函数可以其类体内定义，也可以在其类体外定义，并且要用外围类进行限定

- 嵌套类也可在外围类外或另一个头文件中定义，并在外围类中前向声明这个嵌套类即可。当然，在外围类外定义这个嵌套类时，应该使用外围类进行限定

  ```
  class List {
  public:   // ...
  private: // 嵌套类是私有的
     class ListItem {  
     public:  ListItem( int val = 0 );
                   ListItem *next;
                   int value;
     }; // 嵌套类成员都是公有的
     ListItem *list;
     ListItem *at_end;
  };
  // 用外围类名限定修饰嵌套类名
  List::ListItem::ListItem( int val ) {
      value = val;
      next = 0;
  }
  
  ```

#### 嵌套类的注意
-嵌套类是其外围类的一个类型成员，同数据成员和成员函数一样，遵循外围类的访问权限控制规则
- 嵌套类是一个独立的类，它的成员不属于外围类，外围类的成员也不属于该嵌套类。外围类对嵌套类成员的访问没有特权，嵌套类对外围类成员的访问也没有特权
- 嵌套类可以直接引用外围类的公有静态成员、类型名和枚举成员(后两个私有的也可)。类型名是一个typedef名字，枚举类型名，或是一个类名

```
在嵌套类的定义被看到之前，只能声明嵌套类的指针和引用
	class List {
	public:
	// ...
	private:
	    // 这个声明是必需的
	    class ListItem;
	    ListItem *list;
	    ListItem at_end; //错误: 未定义嵌套类ListItem
	};

```
#### 显式类型转换
- 显式(强制)类型转换操作符除了上面的static_cast之外还包括dynamic_cast，const_cast，reinterpret_cast，()等
- dynamic_cast一般被用来执行从基类指针到派生类指针的安全转换，被称为安全的向下类型转换。它是在运行时刻执行的，转换失败则返回0。
	manager *pm = dynamic_cast< manager* >( pe );
- const_cast将转换掉表达式的常量性，例如：
	extern char *string_copy( char* );
	const char *pc_str;
	char *pc = string_copy( const_cast< char* >( pc_str ));
- reinterpret_cast通常对于操作数的位模式执行一个比较低层次的重新解释，例如：
	complex<double> *pcom;
	char *pc = reinterpret_cast< char* >( pcom );
- 旧式强制类型转换，例如：
	char *pc = (char*) pcom; //C风格
	int ival = (int) 3.14159;	//C风格
	int addr_value = int( &ival );　//C++风格

#### 转换函数
- 转换函数(conversion function)又称类型强制转换成员函数，它是类中的一个非静态成员函数，能把一个类对象转换成其它的指定类型
转换函数的声明格式如下
	class <类型说明符1>
	{
	public:
		operator <类型说明符2>();
		…
	}
	
- ```
  class Rational{
  public:
     Rational(int d, int n):den(d),num(n)
     {    }
     operator double(); //类型转换函数
  private:	int den, num;
  };
  Rational::operator double()
  {
      return double(den)/double(num);
  }
  int main()
  {
      Rational r(5, 8);
      double d = 4.7;
      d+=r;
      cout<<d<<endl;
      return 0;
  }
  
  ```

 #### 转换函数的注意
 - 转换函数是用户定义的成员函数，它是非静态的
- 转换函数名是类型转换的目标类型，不必再指定返回类型
- 转换函数用于本类型对象转换为其它类型数据，不必带参数
- 转换函数不能声明为友元函数
- 不能定义到void的转换，也不允许转换成数组或者函数类型
- 由于不改变数据成员的值，转换函数通常声明为const
- 转换函数在派生类继承
- 最好只定义一个到内置类型的转换函数，避免二义性问题
#### 构造函数作为转换函数
- 在一个类中，只带一个参数的构造函数，都对应一个隐式转换，可以把构造函数参数类型的数据转换为该类的对象

- ```
  class A {
  public:
     A() { m=0; }
     A(double i) { m=i; }
     void print() { cout<<m<<endl; }
  private:
     double m;
  };
  int main()
  {
     A a(5);
     a=10; //a与10是不同的数据类型
     a.print();
     return 0；
  }
  
  ```


- 编译器隐式地用单参构造函数，将参数类型的值转换成构造函数类类型的值，这可能不是我们所希望的。为防止使用该构造函数进行隐式类型转换，我们可以把它声明为显式的
	class A {
	public: A() { m=0; }
		         explicit A(double i) { m=i; }//不会被用来执行隐式转换
	}
- 该构造函数仍然可以被用来执行类型转换，只要程序以强制转换的形式显式地要求转换即可
	double d=10.0; a=static_cast<A>(d);

#### 流
- C++将数据从一个位置到另一个位置的传递抽象为流
- C++基于流的概念处理数据的输入输出，因此也称之为输入输出流，即I/O流
- I/O流具有两个基本的行为特征，一是从流中获取数据的操作称为提取操作，二是向流中添加数据的操作称为插入操作

#### I/O 流类
- C++标准库提供了一整套I/O流类簇，按功能分为3类：
- 标准I/O流类(istream、ostream、iostream)，处理内存与标准输入输出设备(键盘、屏幕)之间数据的传递
- 文件I/O流类(ifstream、ofstream、fstream)，处理内存与外部磁盘文件之间数据的传递
- 字符串I/O流类(istringstream、istrstream、ostringstream、ostrstream、stringstream、strstream)，处理内存变量与表示字符串流的字符数组之间数据的传递
#### 标准I/O流
-标准I/O流预定义了4个流类对象：cin、cout、cerr、clog
cin用来处理标准输入，即键盘输入；cout用来处理标准输出，即屏幕输出。它们被定义在iostream头文件中
- “<<”是预定义的插入运算符，作用在流类对象cout上，实现默认格式的屏幕输出，例如：cout<<E1<<E2<<…<<Em; 其中，E1、E2、…、Em为均为表达式
- “>>”是预定义的提取运算符，作用在流类对象cin上，实现默认格式的键盘输入，例如：cin>>V1>>V2>>…>>Vn; 其中，V1、V2、…、Vn都是变量
#### 标准I/O流的格式控制
- C++提供了两种格式化输入/输出方式：
	1.用ios类成员函数进行格式化输入输出
	2.用专门的操作符函数进行格式化输入输出
- ios类成员函数主要是通过对状态标志、输出宽度、填充字符以及输出精度的操作来完成输入/输出格式化
专门的操作符函数不属于任何类成员，定义在iomanip头文件中
#### 文件I/O
- 文件一般指的是磁盘文件，它是存储在磁盘上的相关数据集合。每个文件都有确定的名字
- 文件按存储格式分二类：一种为字符格式文件，简称字符文件(ASCII码文件或文本文件)，另一种为内部格式文件，简称字节文件(二进制文件)
- 字符文件中，每一个字节单元的内容存放一个字符的ASCII码，能够直接被显示和打印
- 字节文件中，是把内存中的数据按其在内存中的存储形式原样输出到磁盘文件存放
- ASCII码文件占用字节多，把内存中的数据写入ASCII码文件或从ASCII码文件读数据存放在内存中，需要转换
- 二进制文件占用字节少，把内存中的数据写入二进制文件或从二进制文件读数据存放在内存中，不需要转换
	例：10000是整型数据，用二进制表示占两或四个字节，如用ASCII码表示，则占五个字节
- 对于字符信息，在字符文件和字节文件中保存的信息是相同的。对于数值信息，在字符文件和字节文件中保存的信息是不同的
- 对于每个打开的文件，都存在着一个文件指针，初始指向一个隐含的位置，该位置由具体打开方式决定
- 对文件的读写都是从当前文件指针所指的位置开始，在读写过程中，文件指针顺序后移
每个文件都有一个结束标志。当文件指针移到文件的结束标志处时，表示文件结束
- C++提供了使用流对象调用eof()成员函数来测试文件当前状态是否“文件结束”。如果eof()的值非0，表示文件结束，否则表示文件没有结束

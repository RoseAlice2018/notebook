### OOP14 异常和异常处理
#### 什么是异常
- 能够正常运行的程序可能存在很多潜在隐患。程序运行时可以检测到的一些非正常情况称为异常（Exception）。如除数为0，数组越界访问，内存空间不够，输入输出不正常（文件找不到、输入数据类型错等）等等
- 异常是程序错误一种形式
- 程序中的错误按性质可分为：
	语法错误(关键字拼写错、标识符未定义、语句不完整等)
	逻辑错误(算法设计有误导致得不到期望结果)
	异常(出现是不可避免的，必须加以控制和处理)
#### 异常的抛出，检测，和捕捉
- 异常抛出、检测和捕获的语法定义如下：
- 异常抛出：
	throw 表达式；
- 异常的检测和捕获由try-catch结构实现：
	try
	{被检测可能会发生异常的语句}
	catch(异常的类型)
	{异常处理的语句}
```
异常的抛出、检测和捕获的示例
int main() {
	float a,b,c;	 double x1,x2;
	cout<<"请输入一元二次方程的系数abc:";   cin>>a>>b>>c;
	try {  if(0 == a) throw a;  //并无意义，代表float型即可
		       if((b*b-4*a*c)<0) throw 1.0;
	          x1=(-b+sqrt(b*b-4*a*c))/2*b;   x2=(-b-sqrt(b*b-4*a*c))/2*b;
		       cout<<"方程的实根是: x1="<<x1<<"  x2="<<x2<<endl;
	}
	catch(float)      {  cout<<"系数b不能为0，方程无解!"<<endl; }
	catch(double)  {  cout<<"开方为负值，方程无解!"<<endl; }
	return 0;
}
函数嵌套情况下的异常处理
typedef struct zero {string s;}ZERO; //除数为零异常类型
typedef struct negative{string s;}NEGATIVE; //开方值为负异常类型
double fun2(int a, int b, int c) { //抛出异常的函数
	ZERO s1; s1.s="除数为零"; NEGATIVE s2; s2.s="开方值为负数";
	if(0 == a) throw s1;
	if((b*b-4*a*c)<0) throw s2;
	return sqrt(b*b-4*a*c);
}
double fun1(int a, int b, int c) { //捕获异常并重新抛出异常
	try { return (-b+fun2(a,b,c))/(2*a); }
	catch(ZERO) { throw; //重新抛出异常 }
}
int main() {
	float a, b, c;  double x1, x2;
	cout<<"请输入一元二次方程的系数abc:";   
	cin>>a>>b>>c;
	try{ cout<<"方程的一个实根是: x1="<<fun1(a,b,c)<<endl; }
	catch(ZERO S1) //捕获除数为零异常,并处理异常
	{ cout<<S1.s<<"，方程无解!"<<endl; }
	catch(NEGATIVE S2)// 捕获开方值为负数异常,并处理异常
	{ cout<<S2.s<<"，方程无解!"<<endl; }
	cout<<"程序结束!"<<endl;
	return 0;
}

```
### 异常接口声明
- 在使用可能抛出异常的函数时，可能需要知道该函数抛出的异常类型，C++专门提供了异常接口声明，也称为异常规范
- 异常接口声明格式如下：
	<返回类型> <函数名>(<形参表>) throw (<异常类型表>)
	其中<异常类型表>包括该函数中所有可以抛出的异常类型，异常类型之间用逗号分开，为空表示函数不会抛出任何异常。异常接口声明是函数接口的一部分，是函数使用者和函数之间的协议
```
异常处理中析构函数的调用
class intArray {  
public:
	intArray(int n) { p = new int[ n ];  cout<<"构造intArray" << endl;}
    ~intArray() { delete[] p;  cout << "析构intArray" << endl; }
private: int *p;
};
void Fun() {
	intArray ia(10);
	cout<< "在Fun()中抛掷整型异常" << endl;
	throw 1;
}
int main() {  
	try {
		cout << "在主函数的try块中调用函数Fun()。"<< endl;
		Fun();
	}
	catch( int ) {
		cout << "在主函数catch子句捕获到整型异常," ;
		cout << "并进行异常处理。"<< endl;
	}
	cout << "程序结束!" << endl;
	return 0;
}

```
#### 自定义异常类
```
class Excp { //声明异常基类 
public:   static void print( string msg ) { cout<<msg<<endl; }
};
class pushOnFull : public Excp { //声明栈满异常类
public:  pushOnFull( int i ) : tempvalue( i ) { }
             int getTempValue() { return tempvalue; }
private: int tempvalue; //存放异常发生时没有压栈的值
};
class popOnEmpty : public Excp { };//声明栈空异常类 
class newError : public Excp { }; //声明动态分配内存异常类 
class otherError : public Excp { };//声明其它异常类 
class Stack { 
public:  Stack( int capacity=10 ) throw (newError);//建立一空栈
             ~Stack(){delete[] elements;};//析构函数，释放数组资源
             void push( int value ) throw (pushOnFull);//压栈
             void pop(int &top_value) throw (popOnEmpty);//出栈
             bool isfull(){return top < maxsize? false : true;};
             bool isempty(){return top ? false : true;};
             int size(){ return top; }
             void display();
private: int top, *elements,  maxsize;
}; 
Stack::Stack( int capacity):top(0), maxsize(capacity) { 
	elements = new int[capacity];    //动态创建栈数组
	if(0==elements) throw newError();//如果分配失败抛出异常
}
void Stack::pop( int &top_value ) {
	if ( isempty() ) throw popOnEmpty();//栈空，则抛出异常
	top_value = elements[ --top ];
}
void Stack::push( int value ) {
	if ( isfull() ) throw pushOnFull(value);//栈满抛出异常
	elements[top++ ] = value;
}
void Stack::display() {
	if ( !size() ) { cout << "栈中已没有元素！\n"; return; }
	cout << "(栈中有 " << size() << "个元素)( bottom: ";
	for ( int i = 0; i < top; ++i )
		cout << elements[i] << " ";
	cout << " :top )\n";
}
int main() {
	int i,n,count;
	try {
		cout << "请输入需要栈的元素个数：";   cin >>n;
		Stack myStack(n);
标准库中的异常的根类
namespace std { //exception 类在命名空间std 中
class exception {
public:
	exception() throw();//缺省构造函数
	exception( const exception & ) throw();//拷贝构造函数
	exception& operator=( const exception& ) throw();
	virtual ~exception() throw();//析构函数
	// what()返回一个字符串，用于异常的文本描述
	virtual const char* what() const throw();
};
}
标准库中的异常的使用
#include<stdexcept>//标准异常的头文件
#include<string>
#include<iostream>
using namespace std;
class Array {
public: Array(const char *source, int sz) { /* 略 */ }
	char &operator[]( int i ) const {
		if ( i < 0||i >= size ) { string es ="越界"; throw out_of_range( es ); }
		return ca[i];
	}
private:  int size;  char *ca;
};
标准库中的异常的使用
int main() {
	try { char ca[] = { 'A','B','C','D','E','F'};
		Array CA( ca, sizeof(ca)/sizeof(char) );
		CA[5]='X';//正常访问字符数组类
		CA[6]='G';//越界访问数组类将引发异常
		return 0;
	}
	catch ( const out_of_range &excp ) {
		cout<<excp.what()<<endl;//获取异常信息
		return -1;
	}
}

```
异常处理的注意
- 使用标准异常和异常接口声明
- 不要将异常处理当成程序控制结构来使用，可能会带来程序的混乱和执行的低效率
- 尽量避免在构造和析构函数中抛出异常
- try catch语句必须相邻，不允许在中间插入其它
- 使用catch(…)捕获通用异常
- 类型匹配必须严格地类型相同；throw匹配不上任何catch时abort，如throw ‘H’ catch(int)
- catch(基类)总放在catch(派生类)后面 

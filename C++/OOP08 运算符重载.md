### OOP08 运算符重载
#### 为什么运算符重载？
- C++提供了运算符重载机制，赋予运算符多重含义，使一个运算符同时可以作用于内置数据类型和用户自定义数据类型

#### 运算符重载的限制
- 运算符重载可以有两种形式，一种是重载为类的成员函数，另一种是重载为全局函数

- 重载为类的成员函数语法格式为
	<返回类型> [<类名>::]operator<运算符>(<形参表>)
	{       <函数体>	    }
	operator是进行运算符重载的关键字，后跟一个需要被重载的运算符；<形参表>是该运算符涉及的操作数，运算符重载为成员函数时最多有一个形参
	
- ```
	class Complex
	{  
	    double real, imag;
	public:
	    Complex() { real = 0; imag = 0; }
	    Complex(double r, double i) { real = r; imag = i; }
	    Complex operator+(const Complex& x);
	};
	Complex Complex::operator+(const Complex& x)
	{ 
	    Complex temp;
	    temp.real = real+x.real;
	    temp.imag = imag+x.imag;
	    return temp;
	}
	
	```
#### 重载的形式
- 重载为全局函数语法格式为
	<返回类型> operator<运算符>(<形参表>)
	{       <函数体>	    }
	运算符重载为全局函数，函数最多可以有两个参数；如果重载双目运算符，则第一个参数代表左操作数，第二个参数代表右操作数
	
- ```
  class Complex
  { 
  public:
      double real, imag;
      Complex() { real = 0; imag = 0; }
      Complex(double r, double i) { real = r; imag = i; }
  };
  Complex operator+(const Complex& c1, const Complex& c2)
  { 
      Complex temp;
      temp.real = c1.real+c2.real;
      temp.imag = c1.imag+c2.imag;
      return temp;
  }
  
  ```

#### 重载条件
- 至少要有一个操作数的类型是自定义类型
并不是所有的运算符都能重载，下面给出可重载和不可重载的运算符，同时不可使用新的运算符
- 可重载的操作符：
    +  -  *  /  %  ^  &  |  ~  !  =  <  >  +=  -=  *=  /=  %=^=  &=  |=  <<  >>  >>=  <<=  ==  !=  <=  >=  &&  ||  ++  --  ,  ->*  ->  ()  []  new  delete  new[]  delete[]等
- 不可重载的操作符：
    ::  .  .*  ?:  sizeof  typeid等
- 重载的功能应当与原有功能类似，不能改变原运算符的操作数个数
- 重载之后运算符的优先级和结合性都不会改变，并且要保持原运算符的语法结构。函数形参和返回类型都可以重新声明

#### 二元运算符重载
- 所有可重载的二元运算符中，+=、－=、/=、*=、^=、&=、|=、%=、>>=、<<=建议重载为类的成员函数，其它建议重载为全局函数

- ```
  class MyString
  {
  public:
      MyString(const char * = 0);	       //构造函数
      MyString(const MyString &);     //拷贝构造函数
      ~MyString();                                //析构函数
      unsigned int length() const;         //求字符串长度
      char * c_str() const;                      //取得该字符串      
  private:
      unsigned int len;                           //字符串所占空间大小
      char *elems;                                 //字符串中的字符元素集合
  };
  bool operator==(const MyString& str1,const MyString& str2)
  {	
  	return strcmp(str1.c_str(), str2.c_str())==0;
  }
  bool operator==(const char *str1, const MyString &str2)
  {	
      return strcmp(str1, str2.c_str())==0;
  }
  bool operator==( const MyString &str1, const char *str2 )
  {	
      return strcmp(str1.c_str(), str2)==0;
  }
  
  ```

#### 一元运算符重载
- 所有可重载的一元运算符，都建议重载为成员函数，其中=、()、[]、->、->*这几个运算符，必须重载为成员函数

- ```
  class MyString {
  public:
      MyString(const char * = 0);	       //构造函数
      MyString(const MyString &);     //拷贝构造函数
      ~MyString();                                //析构函数
      unsigned int length() const;         //求字符串长度
      char * c_str() const;                      //取得该字符串
      MyString& operator=(const MyString &);      //重载运算符=
  	MyString& operator=(const char *);
  	char & operator[](unsigned int);                      //重载运算符[]
  private:
      unsigned int len;                           //字符串所占空间大小
      char *elems;                                 //字符串中的字符元素集合
  };
  MyString& MyString::operator=(const MyString &obj)
  {
  	if( this != &obj)   //避免向自身赋值
  	{
  		len = obj.len;
  		delete[] elems;
  		elems = new char[len+1];
  		strcpy(elems, obj.elems);
  	}
  	return *this;
  }
  MyString& MyString::operator=(const char *pstr)
  {
  	if (! pstr) { // pstr是个空指针
  		len = 0;
  		delete[] elems;
  		elems = 0;
  	} else {
  		len = strlen(pstr);
  		delete[] elems;
  		elems = new char[len+1];
  		strcpy(elems,pstr);
  	}
  	return *this;
  }
  //返回第i个位置的字符
  char& MyString::operator[](unsigned int i) 
  {
  	if(i<=0 || i>len)  //判断下标是否出界
  	{
  		cout<<"Index out of boundary"<<endl;
  		exit(1);
  	}
  	return elems[i];
  }
  class Time                  //时钟类
  {
  public: 
  	Time(int NewH=0,int NewM=0,int NewS=0);
  	void ShowTime(); 
  	void operator++(); //前缀运算符重载函数的声明
  	                               //后缀运算符重载函数，加int参数以示区分
  	void operator++(int); 
  private:
  	int Hour;
      int Minute;
      int Second;
  }; 
  void Time::operator++()
  {					 //前缀单目运算符重载函数的实现
  	Second++;
  	if (Second>=60)
  	{
  		Second=Second-60;
  		Minute++;
  		if (Minute>=60)
  		{
  		    Minute=Minute-60;
  		    Hour++;
  		    Hour=Hour%24;
  		}
  	}
  }
  void Time::operator++(int)
  {					 //后缀单目运算符重载函数的实现
  	Second++;
  	if (Second>=60)
  	{
  		Second=Second-60;
  		Minute++;
  		if (Minute>=60)
  		{
  		    Minute=Minute-60;
  		    Hour++;
  		    Hour=Hour%24;
  		}
  	}
  }
  
  
  ```

  

## C++ 基础学习

[toc]

### pointer-like classes (智能指针)

#### 为什么使用智能指针？

#### auto_ptr and unique_ptr

#### shared_ptr

#### weak_ptr

### 模板(template)

#### class template

```
template <typename T>
class complex
{
	complex (T r = 0, T i = 0)
	: re (r) ,im (i)
	{ }
	complex& operator += (const complex&);
	T real() const { return re ;}
	T imag() const { return im ;}
	private:
		T re,im;
};
```

- 当我们设计一个类时,如果对于某一个成员变量或者返回类型,并不进行特定的类型指定的话,可以采用如上模式进行设计.

 #### function template

```
template <class T >
inline
const T& min(const T& a,const T& b)
{
	return b<a ? b : a;
}
```

- 当我们设计一个函数时,同上,不必指明类型.
- 编译器会进行实参推导(argument deduction)

#### member template

```
template <class T1,class T2>
struct pair
{
	typedef T1 first_type;
	typedef T2 second_type;
	
	T1 first;
	T2 second;
	
	pair()
	: first(T1()),second(T2()){}
	pair(const T1& a, const T2& b)
	:first(a) , second(b){}
	// member template
	template<class U1,class U2>
	pair(const pair<U1,U2>& p)
	: first(p.first),second(p.second){}
}
```

- 在T1,T2确定的情况下,U1和U2仍然提供不确定性

```
//使用情况如下
class Base1{};
class Derived1 : public Base1 {};

class Base2{};
class Derived2: public Base2 { };


//

pair<Derived1,Derived2> p;
pair<Base1,Base2> p2(p);
```

#### 模板特化

- 模板类的设计初衷是为了泛化,能够使得一次设计能够适用于多种类型.但是有些时候,对于一些特殊类型,我们需要单独为其设计,而并被套用模板类就行,这就是模板的特化.

  ```
  template <class Key>
  struct hash { };
  
  template<>
  struct hash<char>{
  	size_t operator() (char x) const { return x};
  };
  ```

  

#### partial specialization 模板局部(偏)特化

- 个数的变化(由多个模板参数减少)

  ```
  template<typename T,typename Alloc=......>
  class vector
  {
  ...
  };
  
  
  //
  templat<typename Alloc = ......>
  class vector<bool, Alloc>
  {
  	...
  }
  ```

  

- 范围的缩小

  ```
  template <typename T>
  class C
  {
  	...
  };
  //由全类型 缩小为 指针类型
  template <typename T>
  class C<T*>
  {
  	...
  };
  ```

#### template template parameter 模板模板参数

```
template<typename T, template <typename T> class Container>
//template<typename T> class Container作为一个模板参数，且其本身是模板
class XCls
{
	private:
		Container<T> c;
		
	public:
		.... 
};
```

#### variadic templates 模板不定的模板参数

```
void print()
{
}

template<typename T,typename... Type>
void print(const T& firstArg,const Type&... args)
{
	cout<<firstArg<<endl;
	print(args...);
}
```

### auto 自动类型推断

```
list<string> c;
...
list<string>::iterator ite;
ite = find(c.begin(),c.end(),target);


auto ite = find(c.begin,c.end(),target);
```

### ranged-base-for 

```
for ( decl : coll )
{
	statement;
}

for (int i : { 2, 3 ,5 , 9})
	cout<< i << endl;

vector<double> vec;
...
for (auto elem : vec)
	cout << elem << endl;

```

### reference

```
int x = 0 ;
int* p = &x; // 指针
int& r = x ; // 引用

r = x2 ;
int& r2 = r ;
```

- 引用，形象一点比喻就是漂亮的指针。如上，指针p指向x，也就是指针变量p内存的地址是变量x的地址，如果我们想要得到x的值，就要使用解引用符号* ，也就是*p来获得。而引用r，从表面上看来就代表变量x（本质上仍然是用指针实现），我们所有对x的操作和对r的操作是一样的，更像是x的别名。
- 引用从表面上来看，其大小与所引用的变量相同，地址与所引用的变量相同。
- 指针大小一般为4字节，其地址是指针变量自身的地址。

### vptr和vtbl

```
class A
{
public:
	virtual void vfunc1();
	virtual void vfunc2();
	//类A声明了两个虚函数
			void func1();
			void func2();
	private:
		int m_data1,m_data2;
};


class B : public A
{
	public:
		virtual void vfunc1();
		//此时Class B 重写了父类的vfunc1函数	虚函数表中自然就指向该类的需要虚函数实现
	private:
		int m_data3;
};


class C : public B
{
	public:
	virtual void vfunc1();
	//同上，此时该类的虚指针指向的是该类实现的虚函数
	private:
		int m_data1,m_data4;
};
```

- 每一个包含虚函数的类，都会在内存中自动的设置一个虚指针（编译器自动处理），该虚指针指向函数的虚表，虚表中按顺序存有该类的虚函数的调用地址。
- 

###  this

```
CDoucument::OnFileOpen()
{
	......
	Serialize();
	......
}
//在类的设计中 如果一些需要特化的操作，比如文件打开时，对于具体文件类型的不同打开操作会有不同的方式
virtual Serialize();

class CMyDoc: public CDoucment
{
	virtual Serialize(){...}
	//继承实现Serilalize方法
};

main()
{
	CMyDoc myDoc;
	......
	myDoc.OnFileOpen();
	//实际上是OnFileOpen（&myDoc)，this指针是编译器默认实现的首参数，不占用空间，这时传进去的实际上是父类型，所以满足了动态绑定的条件，实现了动态绑定。
}
```

### Dynamic Binding

```
B b;
A a = (A)b;
a.vfunc1();
// 对象调用 执行A的函数
A* pa =  new B;
pa->vfunc1();
//动态绑定
pa = &b;
pa->vfunc1();
//动态绑定
```

- 指针调用
- 向上转型
- 虚函数
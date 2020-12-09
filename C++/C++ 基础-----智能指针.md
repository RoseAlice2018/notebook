## C++ 基础-----智能指针

[toc]

### 为什么使用智能指针？

- C++ 对于内存的分配是通过new和delete一组相对应的操作来实现的，但是实际编程中（尤其在大的项目），容易出现问题，如漏掉delete，或者多次delete，出现内存泄漏等问题。这时候需要一种更智能的方式来替我们管理内存，最好不需要手动释放，而是由系统替我们自动释放。普通指针无法实现这种效果，而智能指针就是为了解决这一问题而设计的。
- 智能指针本质上是pointer-like-class，而非pointer。所以由智能指针生成的对象，在生命周期结束后，会自动调用析构函数来释放掉所占用的内存，而不再需要程序员手动操作。
- 但智能指针的实现也有不同的方式，以下就将介绍出现在C++标准库中的四种智能指针（其中auto_ptr是C98标准，以及被C11废弃）

### 智能指针的实现类模板

```
template <class T> class SmartPointer
{
	private:
		T* _ptr;
	public:
		SmartPointer(T* p):_ptr(p) //ConStructor
		{
		
		}
		T& operator *() //重载'*'操作符
		{
			return *_ptr;
		}
		T* operator -> ()//重载“->”操作符
		{
			return _ptr;
		}
		~SmartPointer()
		{
			delete _ptr;
		}
}
```



### auto_ptr and unique_ptr

- 这里我们先介绍一下所有权的概念。通俗一点讲，我们将对象想象成一个房间里的宝藏，而所有权就是打开这个房间的钥匙(有且只有一个，auto和unique都是独占权），一个指针只有有了这个钥匙才能打开这个门，取到宝藏。而也只有拥有所有权的那个类对象结束生命周期时，才会调用析构函数来释放这个对象，而非多次释放。

- auto_ptr 的安全风险

  ```
  #include <iostream>
  #include <string>
  #include <memory>
  using namespace std;
  int main()
  {
  	auto_ptr <string> films[5]={
  	auto_ptr <string> (new string("Fowl Balls")),
  	auto_ptr <string> (new string("Duck Walls")),
  	auto_ptr <string> (new string("Turkey Runs")),
  	auto_ptr <string> (new string("Goose Eggs")),
  	auto_ptr <string> (new string("Chicken Runs"))
  	}
  	auto_ptr <string> pwin;
  	pwin = films[2]; //将所有权从films[2]转让给pwin
  					// 此时films[2]不再引用该字符串从而变成空指针
  	for(int i=0;i<5;++i)
  	{
  		cout<<*films[i]<<endl;
  	}
  	cout<<"The winner is"<<*pwin<<endl;
  	return 0;
  }
  ```

- 上述程序在运行时会发生崩溃，因为films[2]已经将所有权移交出去，变成了空指针，这样访问空指针当然会出问题。但是使用unique_ptr则不会发生这样的问题，因为unique_ptr禁止了拷贝语义，所以在编译期就会报错。

- 虽然无法通过拷贝语义，但是unique同样提供了控制权的转移操作，如下。

  ```
  unique_ptr <string> upt1 = std::move(upt);
  if(upt.get()!=nullptr)
  {
  	
  }
  ```

  **注意：在通过这样的转移之后，不能通过unique来访问和控制资源了，否则也会出现auto的情况，只能在使用前通过get（）判断是否为空。**

- **unique虽然不支持拷贝语义，但是在unique_ptr是一个临时右值的时候允许拷贝语义**。

  ```
  unique_ptr <string> demo(const char* s)
  {
  	unique_ptr <string> temp(new string(s));
  	return temp;
  }
  unique_ptr <string> ps;
  ps = demo("Uniquely special");
  ```

- 如上，demo返回的是一个临时的对象，然后ps接管临时对象，而返回时，临时对象被销毁（因为已经过了作用域），也就是说这个对象没有机会再被使用，也就不会造成程序的故障。所以，事实上，编译器在这种情况下允许拷贝赋值。

#### unique相对于auto的扩展

1. unique_ptr 可以放在容器里，而auto_ptr不能作为容器元素。

2. 管理动态数组，unique有着unique_ptr<X[]>重载版本，销毁动态对象时调用delete[]

   ```
   unique_ptr <int[]> p(new int[3]{1,2,3});
   p[0] = 0 ;
   ```

   

3. 自定义资源删除操作（delete）。unique_ptr 默认的资源删除操作是delete/delete[],但如有需要，也可以进行自定义。

   ```
   void end_connection(connection *p){disconnect(*p);}//资源清理函数
   
   unique_ptr <connection,decltype(end_connection)*> p(&c,end_connection);
   ```

   

### shared_ptr

- 引用计数型智能指针。在引用计数的机制上，提供了可以共享所有权的智能指针。

  **如何模拟实现shared_ptr**

  - 本来想写个注释的，但是仔细看看，代码写的一清二楚，就不多讲了。以下代码并非用模板类写成。
  
  1. 基础对象类
  
     ```
     class point
     {
     	
     }
     ```
  
     
  
  2. 辅助类
  
     ```
     class RefPtr
     {
     	private:
     		friend class SmartPtr;
     		RefPtr(Point* ptr):p(ptr),count(1){}
     		~RefPtr(){delete p;}
     		
     		int count;
     		Point* p;
     }
     ```
  
     
  
  3. 智能指针实现
  
     ```
     class SmartPtr
     {
     	public:
     		SmartPtr(Point* ptr):rp(new RefPtr(ptr)){}
     		SmartPtr(const SmartPtr& sp):rp(sp.rp){++rp->count;}//拷贝构造
     		
     	//重载赋值运算符
     		SmartPtr& operator = (const SmartPtr& rhs)
     		{
     			++ rhs.rp->count;
     			if(--rp->count == 0)
     			{
     				delete rp;
     			}
     			rp = rhs.rp;
     			return *this;
     		}
     	//重载->运算符
     		Point* operator -> ()
     		{
     			return rp->p;
     		}
     	//重载*操作符
     	Point& operator *()
     	{
     		return *(rp->p);
     	}
     	
     	~SmartPtr()
     	{
     		if(--rp->count == 0)
     			delet rp;
     		else
     			;
     	}
     	private:
     		RefPtr *rp;
     }
     ```
  
     

### weak_ptr

```
#include <iostream>
#include <memory>
class Woman;
class Man
{
	private:
		//std::weak_ptr <Woman> _wife;
		std::shared_ptr <Woman> _wife;
	public:
		void setWife(std::shared_ptr <Woman> woman)
		{
			_wife = woman;
		}
		void doSomething()
		{
			if(_wife.lock())
			{
			
			}
		}
		~Man()
		{
			std::cout<<"kill man\n";
		}
}
class Woman
{
	private:
		//std::weak_ptr <Man> _husband;
		std::shared_ptr <Man> _husband;
	public:
		void setHusband(std::shared_ptr <Man> man)
		{
			_husband = man;
		}
		~Woman()
		{
			std::cout<<"Kill woman\n";
		}
};
int main(int argc,char** argv)
{
	std::shared_ptr <Man> m(new Man());
	std::shared_ptr <Woman> w(new Woman());
	if(m && w)
	{
		m -> setWife(w);
		w -> setHusband(m);
	}
	return 0;
}
```

- 在Man类内部引用一个Woman，Woman类内部也引用了一个Man。当一个man和一个woman是夫妻时，他们就直接存在相互引用问题。Man内部有一个wife变量，那么它结束时，wife是在man调用delete之后释放的，同理husband也是在woman调用delete之后释放掉的，如果woman和man对象互相调用，则存在循环问题，两者都释放不掉。
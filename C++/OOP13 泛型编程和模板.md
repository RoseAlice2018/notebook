## OOP13 泛型编程和模板
### 什么是泛型编程
- 所谓泛型编程就是以独立于任何特定类型的方式编写代码，即编写的代码可以用来操纵各种类型的数据
	例如，编写一个函数，该函数能对各种数据类型的数组（基本类型数组或对象数组）进行倒序；编写一个类，该类能以链表的形式存储各种相同类型的数据。那么，编写这样一个函数或类就是泛型编程
### 什么是模板
- C++中，模板是泛型编程的基础
- 模板是创建类或函数的蓝图或公式
- 模板分为两种类型
	-函数模板（Function Template）
	-类模板（Class Template）
- 模板并非一个实实在在的类或函数，仅仅是一个类或函数的描述，是参数化的函数和类

### 函数模板和模板函数
#### 函数模板
- 形参类型、返回类型或函数体中使用的类型是通用类型的函数称为函数模板，它定义了一组函数
- 函数模板的定义格式 
	template <类型参数表>
	<返回类型> <函数名>(<形参表>)
	{
         <函数体>　
	}
- 函数模板是对一组函数的描述，它以任意类型T为形参类型及函数返回类型
- 函数模板不是一个实实在在的函数，编译系统并不产生任何执行代码
- 当编译系统在程序中发现有与函数模板中相匹配的函数调用时，便生成一个重载函数，该重载函数的函数体与函数模板的函数体相同
```
函数模板的示例
template <class T>
void Reverse(T* begin, T* end)    //对任意类型数组进行倒序
{
	for(; begin != end && begin != --end; ++begin)
  	{  
        T temp;
        temp=*begin;
        *begin=*end;
        *end=temp;
    }
}
模板函数的示例
template <typename T> //加法函数模板
T Add(T x, T y) {  return x+y;  }
int main() 
{
    int x=10, y=10;
    cout<<Add(x,y)<<endl;//相当于调用函数int Add(int,int)
    double x1=10.10,y1=10.10;
    cout<<Add(x1,y1)<<endl;//相当于调用函数double Add(double,double)
    return 0;
}

```
### 什么是类模板
- 数据成员类型、成员函数的形参类型、返回类型或函数体中使用的类型为通用类型的类称为类模板，它定义了一组类
- 类模板的定义格式 
	template <类型参数表>
	class <类模板名>
	{
         <类成员>　
	}
### 类模板的说明
- 类模板是对一组类的描述，类中的某些数据成员、某些成员函数的形参或返回值，能取任意类型
- 类模板不是一个实实在在的类，编译系统并不产生任何执行代码
- 当编译系统在程序中发现有与类模板相匹配的类对象定义时，便生成一个模板类，该类的定义与类模板的定义完全相同

### 类模板的成员函数的定义
- 成员函数可以在类模板中定义，也可以在类模板外定义，此时成员函数的定义格式如下：
	template <类型参数表> 
    <返回类型> <类模板名><类型名表>::<函数名>(<形参表>)
	{
    	    <函数体>
	}
- 注意：类模板的所有成员函数一定是函数模板在类模板外定义成员函数时，每一个函数前都
要加上template <类型参数表> 
```
类模板的示例
template <class T> class Compare {
public:   Compare(T a,T b){ x=a;y=b; }
              T max();
private:  T x,y;
};
template <class T>  T Compare<T>::max() { return (x>y)? x:y; } 
int main(){
	Compare <int> cmp(4,7);
	cout << cmp.max();
	return 0;
}

```
### 类模板的派生
- 类模板可以作为基类派生出新的类模板，即可以派生类模板
定义派生类模板时应注意：
	1、在派生类模板前加上“Template <类型参数表>”；
	2、在指定所继承的基类时传入类型名表；
	3、其余的与一般派生类定义相似

```
派生类模板的示例
template <class T> class A { 
public:   void showA(T a){ cout << a << endl; }
};
template <class X, class Y> class B: public A<Y> {
public:   void showB(X b){ cout << b << endl; }
};
int main(){
	B <char, int>  bb;
	bb.showA(10);    bb.showB(‘A’);
	return 0;
}

```

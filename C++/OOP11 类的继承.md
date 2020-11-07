## OOP11 类的继承
### 派生和继承
- 派生是指由已有类创建新类的过程。新类称为派生类或子类，已有类称作基类、父类或超类
- 继承是指子类保留父类的成员的特性
- 继承意味着“自动地拥有”，即子类中不必重新定义已在父类中定义过的属性和操作，而是自动地、隐含地拥有其父类的属性和行为
- 继承具有传递性，派生类也可以作基类
- 继承体现泛化关系，实现了代码复用
### 派生类的定义
- 派生类的声明：class <派生类名>;

- 派生类的定义格式如下
	class <派生类名>:<继承方式> <基类名>
	{
         <派生类新定义成员>
	};
	
- 基类必须是已定义的。继承方式有公有、保护和私有继承三种，分别用public、protected和private来表示，默认情况下为私有继承(struct默认为公有继承)

- ```
  class Base {
  private:
  	int b_number;
  public:
  	Base( ){}
  	Base(int i):b_number (i) { }
  	int get_number( ) 
      {
          return b_number;
      }
  	void print( )
      {
          cout << b_number << endl;
      }       
  };
  class Derived : public Base {
  private:
  	int d_number;
  public:
  	Derived( int i, int j ) 
         : Base(i), d_number(j) { };       
  	void print( )
      {
  		cout << get_number( ) 
                 << " ";       
  		cout << d_number 
                 << endl;
  	}
  };
  ```

### 派生类成员的构成
- 派生类的生成经历了3个步骤：吸收基类成员、改造基类成员、添加派生类新成员
- 吸收基类成员就是继承基类的成员，但不继承基类的构造函数、析构函数、拷贝构造函数和赋值函数
- 改造基类成员包括两方面，一是通过派生类定义时的继承方式来控制改变基类成员在派生类中的访问控制权限；二是定义同名成员屏蔽基类成员(但是该基类成员仍然存在）
- 添加新成员就是派生类中定义新的成员，扩展新功能；另外，还要定义自己的构造和析构函数

### 基类成员在派生类中的访问权限
#### 公有继承
- 当类的继承方式为公有继承时，在派生类中，基类的公有成员和保护成员被继承后分别成为派生类的公有成员和保护成员，派生类新定义的成员函数可以直接访问他们，但是不能直接访问基类的私有成员。在类外，派生类的对象可以访问继承下来的基类公有成员

- ```
  //rectangle.h
  class Point
  {
  public:
     void InitP(float xx=0, float yy=0)   
     {    X=xx;  Y=yy;   }
     void Move(float xOff, float yOff)  
     {    X+=xOff;  Y+=yOff;  }
     float GetX(){return X;}
     float GetY(){return Y;}
  private:
     float X,Y;
  };
  class Rectangle: public Point
  {
  public:
     void InitR(float x, float y,
                      float w,float h)
     { InitP(x,y); W=w;H=h; }
     float GetH() {return H;}
     float GetW() {return W;}
  private:
     float W,H;
  };
  
  ```

#### 保护继承
- 当类的继承方式为保护继承时，在派生类中，基类的公有成员和保护成员被继承后全部成为派生类的保护成员，派生类新定义的成员函数可以直接访问他们，但是不能直接访问基类的私有成员。在类外，派生类的对象不能访问继承下来的所有基类成员

- 保护成员具有两面性，对外不可见，对派生类可见

- 在面向对象程序设计中，若要对基类的某些函数功能进行扩充和改造，就可以采用保护继承的方式，并在派生类中定义同名成员函数来实现

  ```
  //rectangle.h
  class Point
  {
  public:
     void InitP(float xx=0, float yy=0)   
     {    X=xx;  Y=yy;   }
     void Move(float xOff, float yOff)  
     {    X+=xOff;  Y+=yOff;  }
     float GetX(){return X;}
     float GetY(){return Y;}
  private:
     float X,Y;
  };
  class Rectangle: protected Point
  {
  public:
     void InitR(float x, float y,
                      float w,float h)
     { InitP(x,y); W=w;H=h; }
     void Move(float xOff, float yOff)
     { Point::Move(xOff,yOff); }
     float GetX() {return Point::GetX();}
     float GetY() {return Point::GetY();}
     float GetH() {return H;}
     float GetW() {return W;}
  private:
     float W,H;
  };
  
  ```

### 私有继承
- 当类的继承方式为私有继承时，在派生类中，基类的公有成员和保护成员被继承后全部成为派生类的私有成员，派生类新定义的成员函数可以直接访问他们，但是不能直接访问基类的私有成员。在类外，派生类的对象不能访问继承下来的所有基类成员

### 接口继承和实现继承
- 从上面可以看出：

- 当类的继承方式为公有继承时，派生类继承基类的接口，具有与基类相同的接口，通常称为接口继承

- 当类的继承方式为保护或私有继承时，派生类继承基类的部分并未成为其接口的一部分，即派生类不继承基类的接口，而是在实现中使用从基类继承过来的成员，因此通常称为实现继承
  实现继承下，个别从基类继承过来的成员可以在派生类中用using声明恢复其原来的访问控制权限

  ```
  class Base
  {
  public:
     size_t size() const
     {   return n;   }
  protected:
     size_t n;
  };
  class Derived : private Base
  {
  public:
     using Base::size;
  protected:
     using Base::n;
     // ...
  };
  
  ```

- 在派生类Derived中增加using声明，使size成员能够在类外访问，使n能够被从Derived派生的类访问
	注意：在派生类中不能对基类的私有成员使用using声明

#### 关于基类私有成员的注意
- 无论是公有继承、保护继承还是私有继承，基类的私有成员在派生类中都是不能被直接访问的。C++如此处理主要是考虑到数据封装，因为，既然某个成员被声明为private类型，就说明开发人员是不希望它被外部直接访问到的，无论是本类的对象还是派生类的对象

### 派生类的构造函数
- 由于派生类的对象自动拥有基类的所有数据成员，所以，在定义派生类的构造函数时除了对派生类的数据成员进行初始化外，还必须初始化基类的数据成员。如果派生类中有对象成员时，还要初始化对象成员

- 派生类构造函数的定义格式如下
	[<派生类名>::]<派生类名>(<形参表>)
           [:<基类名>(<形参表1>),<对象成员名>(<形参表2>)]
	{
	    <派生类数据成员的初始化>
	};
	
	```
	//rectangle.h
	class Point
	{
	public:
	   void Point(float xx=0, float yy=0)   
	   {    X=xx;  Y=yy;   }
	   void Move(float xOff, float yOff)  
	   {    X+=xOff;  Y+=yOff;  }
	   float GetX(){return X;}
	   float GetY(){return Y;}
	private:
	   float X,Y;
	};
	class Rectangle: public Point
	{
	public:
	   Rectangle(float x, float y, float w,        
	                    float h)
	   :Point(x, y) { W=w; H=h; }
	   void Move(float xOff, float yOff)
	   { Point::Move(xOff,yOff); }
	   float GetX() {return Point::GetX();}
	   float GetY() {return Point::GetY();}
	   float GetH() {return H;}
	   float GetW() {return W;}
	private:
	   float W,H;
	};
	
	```
	
### 派生类构造函数的注意
- 对从基类继承过来的成员的初始化，派生类构造函数最好不要采用函数体内直接赋值的方式，而是通过初始化列表调用直接基类的适当的构造函数来间接初始化，绝不允许在初始化列表对其直接初始化或调用基类其他成员函数初始化
- 当基类中没有显式定义构造函数时，派生类可以不必显式定义构造函数，或者显式定义构造函数，但初始化列表可以省略对基类构造函数的调用
- 当基类中显式定义构造函数时，派生类也应该显式定义构造函数，并通过初始化列表调用直接基类的适当的构造函数来初始化从基类继承过来的成员

### 派生类的构造顺序：
- 派生类构造函数执行顺序为：
	① 调用基类构造函数，初始化从基类继承过来的数据成员
    ② 如果派生类新增对象成员，则按照新增对象成员的声明顺序依次调用其所属类的构造函数，初始化新增对象成员
    ③ 执行派生类构造函数的函数体，初始化派生类新增的其它数据成员

### 派生类的析构函数
- 派生类析构函数的定义格式如下
	~<派生类名>( )
	{    <函数体>    }
派生类的析构函数会自动调用基类的析构函数
派生类析构函数执行顺序为：
	① 执行派生类析构函数的函数体
	② 按声明顺序的逆序依次析构各新增对象成员
	③ 调用基类的析构函数

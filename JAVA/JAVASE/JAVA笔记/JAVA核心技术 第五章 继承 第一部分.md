## 5.1 类，超类，子类

- 通常在扩展超类，定义子类的时候，仅仅需要指出子类和超类的不同之处。
### 5.1.2 覆盖方法
- super关键字，调用父类的方法
- 子类可以增加域，增加方法和覆盖超类的方法，但是不能删除继承的任何域和方法
### 5.1.3 子类构造器

```
public Manager(String name,double salary,int year,int month,int day)
{
	super(name,salary,year,month,day);
	bonus=0;
}
```

- **super调用构造器的语句必须是子类构造器的第一条语句。**
- 如果子类的构造器没有显示的调用超类的构造器，则将自动地调用超类默认（没有参数）的构造器。如果超类没有不带参数的构造器，并且在子类的构造器又没有显示的调用超类的其他构造器，则Java编译器报错。
1. 一个对象变量可以指示多种实际类型的现象被称之为**多态（polymorphism)** 。在运行时能够自动的选择调用哪个方法的现象称之为**动态绑定（dynamic binding)**.
2.** Java不支持多继承**
### 5.1.5 多态
- **“is - a”法则**

### 5.1.6 理解方法调用

1. 编译器查看对象的声明类型和方法名。
2. 接下来，编译器查看调用方法时提供的参数类型。找到最匹配的调用，否则报错。（这个过程称之为**重载解析（overloading resolution）**。
3. 如果是private，static，final方法或者构造器，那么编译器能够准确地知道应该调用哪个方法，我们将这种方式称之为**静态绑定。**

** 在覆盖一个方法时，子类方法不能低于超类方法的可见性。特别是，如果超类的方法是public，子类的方法一定也要声明为public。**

### 5.1.7 阻止继承：final类和方法
 - **不允许扩展的类称之为final类**
 - **final方法不允许被覆盖**
 - 域也可以声明为final，对于final域来说，构造对象后就不允许修改他们的值了。
 -  如果一个类声明为final，只有其中的方法自动成为final，而不包括域。

 ### 5.1.8 强制类型转换
 - 只能在继承层次内进行类型转换。
 - 在超类转换成子类之前，应使用instanceof 进行检查。


 ### 5.1.9  抽象类
 - 为了提高程序的清晰度，包含了一个或多个抽象方法的类本身必须被声明为抽象的。

 - 除了抽象方法外，抽象类还可以包含具体数据和具体方法。

 - 类即使不包含抽象方法，也可以声明为抽象类。

 - 抽象类不可以被实例化。

 - 可以定义一个抽象类的对象变量，但是它只能引用非抽象子类的对象。

   ```
   Person p=new student();
   ```

   


## 5.2 object：所有类的超类

### 5.2.1 equals 方法
- 判断两个对象是否具有相同的引用。（可以override）
- 在子类中定义equals方法时。首先调用超类的euqals。如果检测失败，对象不可能相等。如果超类中的域都相等。就需要比较子类中的实例域。

Java语言规范对equals方法具有以下特性要求：
1.自反性
2.对称性
3.传递性
4.一致性

### 5.2.3 hashCode方法
- 详见第九章
### 5.2.4 toString方法
- 只要对象与一个字符串通过操作符“+”连接起来，JAVA编译就会自动的调用toString方法，以便于获得这个对象的字符串描述。
	Class getClass();//返回包含对象信息的类对象。
	String getName(); //返回这个类的名字
	Class getSuperclass();以Class对象返回这个类的超类信息

## 5.3 泛型数组列表

	array.ensureCapacity(100);已经清楚的知道或估计出
	array.trimToSize(); 将数组列表的存储容量削减到当前尺寸。





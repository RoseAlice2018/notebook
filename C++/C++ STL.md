## C++ STL

[toc]



### 容器（Containers）

结构分类

#### Sequence Containers

##### Array

##### Vector

- Vector源码解析

  ```
  template <class T,class Alloc = alloc >
  class vector{
  	//定义
  	typedef T value_type;
  	typedef value_type* iterator;
  	typedef value_type& reference;
  	typedef size_t size_type;
  	
  	//成员
  	iterator start;  //开始结点
  	iterator finish; //结束结点后一个结点
  	iterator end_of_storage; //最大结点
  	
  	//函数
  	iterator begin() {return start;}
  	iterator end() {return finish;}
  	size_type size() const { end()- begin()};
  	size_type capacity() const() {end_of_storage() - begin();}
  	bool empty() const { return begin() == end();}
  	reference operator[](size_type n){return *(begin()+n);}
  	reference front() { return *begin();}
  	reference back() {return *(end()-1);}
  };
  ```

- vector内存扩充

  ```
  vectorpush_back(const T& x)
  {
  	if(finish ! =  end_of_storage)
  	{
  		construct(finish,x);
  		++finish;
  	}
  	else 
  		insert_aux(end(),x);
  }
  template<class T,class Alloc>
  void vector<T,Alloc>::insert_aux(iterator position,const T& x)
  {
  	if(finish != end_of_storage)
  	{
  		construct(finish,*(finish-1));
  		++finish;
  		
  		//
  		T x_copy = x;
  		copy_backward(position,finish-2,finish-1);
  		*position = x_copy;
  	}
  	else{ //已无备用空间
  		const size_type old_size = size();
  		const size_type len = old_size ? 0 2*old_size : 1;
  		// old_size 为0则是1 ， 不为0则是之前2倍
  		
  		//重新申请内存
  		iterator new_start = data_allocator::allocate(len);
  		iterator new_finish = new_start;
  		
  		//内存拷贝
  		try{
  			new_finish = uninitialized_copy(start,position,new_start);
  			//将原vector内容拷贝到新vector内容
  			++new_finish//调整finish位置
  		//以下代码供其他函数使用,insert，不一定插入在最后，也需拷贝当前位置后面元素
  			new_finish = uninitialized_copy(position, finish, new_finish);	//拷贝当前后面元素
  		} catch(...){
  			destroy(new_start, new_finish);
  			data_allocator::deallocate(new_start, len);
  			throw;
  		}
  
  		//释放原内存
  		destroy(begin(), end());
  		deallocate();
  
  		//调整迭代器，指向新vector
  		start = new_start;
  		finish = new_finish;
  		end_of_storage = new_start + len;
  	}
  }
  ```

  

- vector迭代器iterator

  ```
  template<class T, class Alloc=alloc>
  class vector{
  	typedef T value_type;
  	typedef value_type* iterator; //迭代器为指针T*
  };
  ```

  

##### Deque

##### List



#### Associative Containers

##### rb_tree

- rb_tree(red-black tree)是一个自平衡的二叉搜索树，具体实现原理和插入删除等操作这里不再细说。总之，有利于插入和搜索操作，并且会保持高度平很，不会让任意一个结点过深。

- rb_tree 提供“遍历”操作，以及迭代器iterators，按照正常规则（++ite）遍历，就可以得到有序的结果。（实际上进行了中序遍历）

- 理论上，我们不应该也不能通过iterator来修改其指向的值，因为rbtree有着严谨的排列规则，一旦元素值发生变动，整个树会进行重新排列。（但是从编程层面，这件事并没有进行阻绝。）如此设计的原因，是因为rbtree设计目的是用作set和map的底层，而map元素允许data被修改，只有元素的key才是不可改变的。

- rb_tree 提供两种插入操作：insert_unique() 和insert_equal().前者表示结点的key一定在整个tree中独一无二，否则安插失败；后者表示结点是可以重复的。

- rb_tree 的实现

  ```
  template <class Key
  		class Value,
  		class KeyOfValue,
  		class compare,
  		class Alloc = alloc >
  class rb_tree{
  	protected:
  		typedef _rb_tree_node<Value> rb_tree_node;
  		...
  	public:
  		typedef rb_tree_node* link_type;
  		...
  	protected:
  	    //RB-tree 表现性质
  	    size_type node_count;
  	    link_type header;
  	    Compare key_compare;
  	    ...
  };
  ```

  

##### Set/Multiset

- Set本身更像是一个适配器，因为其所有功能都由rbt_ree来实现

- Set因为没有数据域，所以将其数据域标记位key，来组成value

- compare 是比较函数，可以通过重载‘<'来实现对于自定义类的大小比较

  ```
  template <class Key,
  		  class Compare = less<Key>,
  		  class Alloc = alloc >
  class set{
   public:
   	//typedefs:
   	typedef Key key_type;
   	typedef Key value_type;
   	typedef Compare key_compare;
   	typedef Compare value_compare;
   private:
   	typedef rb_tree<key_type,value_type,identity<value_type>,key_compare,Alloc>
   	rep_type;
   public:
     typedef typename rep_type::const_iterator iterator;
     ...
  }
  
  template <class Arg,class Result>
  struct unary_function{
  	typedef Arg argument_type;
  	typedef Result result_type;
  }
  template <class T>
  struct identity:public unary_function<T,T>
  {
  	const T& operator()(const T& x) const { return x;}
  };
  ```

- 样例

  ```
  set<int> iset;
  
  set<int,
  	less<int>,
  	alloc
  	>iset;
  
  template<int,
  		 int,
  		 identity<int>,
  		 less<int>,
  		 alloc>
  class rb_tree;
  
  ```

  

##### Map/Multimap

- map 以rb_tree 为底层结构，因此元素会自动排序（以key为排序依据）。

- 无法修改key值（因为内部将key值设置为const），但可以修改对应的data值。

- map的key值是独一无二的，因此其insert（）使用的是rb_tree的insert_unique().

- multimap的key值可以重复，因此其insert（）用的是rb_tree的insert_equal().

  ```
  template <class Key,
  		 class T,
  		 class Compare =  less<Key>,
  		 class Alloc = alloc >
  class map{
  	public:
  		typedef Key key_type;
  		typedef T data_type;
  		typedef T mapped_type;
  		typedef pair<const Key,T> value_type;
  		typedef Compare key_compare;
  	private: 
  		typedef rb_tree<key_type,value_type,select1st<value_type>,key_compare,Alloc> rep_type;
  	public:
  		typedef typename rep_type::iterator iterator;
  		...
  }
  ```

  

#### Unordered Containers

##### Unordered Set/Multiset

##### Unordered Map/Multimap

### 分配器（Allocators）

### 算法（Algorithms）

### 迭代器（Iterators）

### 适配器（Adapters）

### 仿函数（Functors）


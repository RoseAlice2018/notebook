## C++ STL

[toc]



### 容器（Containers）

结构分类

#### Sequence Containers

##### Array

- Array就是C语言中数组的包装类，正因为有了这层包装，Array才能享受标准库给予6大部件的便利；

- Array必须初始化给定空间大小，期间不允许改变大小，也是连续空间，只要是连续空间其迭代器就可以用自然指针来询问。

  ```
  template<typename _Tp,std::size_t _Nm>
  struct array
  {
  	typedef _Tp value_type;
  	typedef value _type* pointer;
  	typedef value_type&  reference;
  	typedef	value_type*	iterator;
  	typedef std::size_t	size_type;
  	
  	//Support for zero-sized arrays mandatory
  	typedef _GLIBCXX_STD_C::_array_traits<_Tp,_Nm>_AT_Type;
  	typename _AT_Type::_Type _M_elems;
  	
  	//
  	...
  }
  ```

  

##### Vector

- Vector是自动增长的数组，事实上标准库中并没有一个容器可以实现原地扩充，所谓原地扩充都是假象（ ，它只是在内存的另一个地方找到一个大的空间，再将源数据拷贝赋值过去。

- Vector大小为3个指针大小。

  	iterator start;  //开始结点
    	iterator finish; //结束结点后一个结点
    	iterator end_of_storage; //最大结点

- 我们使用push_back放元素时它会检查当前是不是有足够空间，在我们一开始创建Vector时，它的大小就是0，所以第一次内存扩充为1，在这个之后如果需要扩充那就扩充到2*现在容器size的大小，之后调用分配器扩充内存，再把原来内容拷贝到新的地方，接着把新的元素安插到末尾，还要进行删掉之前地方的元素释放空间

- 由于每次扩充都会进行内存的分配，复制，析构释放，会有很大的系统开销。

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
##### Forward_list

- 单向链表

##### Deque

- 实现

  ```
  template<class T,class Alloc = alloc,size_t BufSiz = 0>
  class deque{
  	public:
  		typedef T value_type;
  		typedef _deque_iterator<T,T&,T*,BufSiz> iterator;
  	protected:
  		iterator start;
  		iterator finish;
  		map_pointer map;
  		size_type map_size;
  	public:
  		iterator begin() {return start;}
  		iterator end()  {return finish;}
  		size_type size() const {return finish-start;}
  }
  ```

  

- Deque（双向队列）也号称连续空间（其实是给使用者一个善意的谎言，只是为了好用），其实它使用分段拼接起来的（分段连续），各个分段间是用Vector来管理的，Vector的每个元素就是一个指针，每个指针指向一个分段，每一个分段就是一个缓冲区buffer，首位安插元素时，当缓冲区满了需要扩充时，就重新分配一个缓冲区然后串在Vector里面；

- Deque的迭代器有4个指针，其中node表示在控制中心的位置（也就是在Vector中的位置），first表示node所指的buffer的头，last表示node所指的buffer的尾，first和last是边界的标兵，它们时不会变的，cur表示迭代器当前指向的哪个元素；

- 分段连续的实现，当迭代器前进后者后退时，都要判断是不是走到了当前buffer的末端或者头部，都必须有能力跳到下个buffer缓冲区，如果到达边界就必须依靠node指针回到控制中心（Vector）再跳到下个buffer

- 每个缓冲区大小：512字节除以放入数据的字节大小，比如放int，缓冲区大小=512/sizeof(int)

- **迭代器**

  - Deque的迭代器sizeof是16，一个Deque包含两个迭代器，一个指针一个size_type,所以Deque的sizeof为16+16+4+4=40个字节

  ```
  template<class T,class Ref,class Ptr,size_t Buffer>
  struct _deque_iterator{
  	typedef random_access_iterator_tag iterator_catagory;
  	...
  	
  	T* cur;
  	T* first;
  	T* last;
  	map_pointer node;
  	
  }
  ```

- 由于Deque是可以两端进行扩充的，插入元素又会引入元素移动问题，进而带来拷贝构造的开销，所以在插入时首先进行判断插入位置距离首位哪边比较短，移动距离较短的一边，最大化的减少开销。



##### queue

- 先进先出队列，其实内部实现就是用deque，只是把不用的功能封掉，所以queue自己不做事它只是把事交给deque来做，所以我们不把queue称之为容器，把它称为容器适配器（把别人改装一下用）。

##### stack

- 先进后出（栈），其实内部实现也是用deque，只是把不用的功能封掉，所以queue自己不做事它只是把事交给deque来做，所以我们不把stack称之为容器，把它称为容器适配器（把别人改装一下用）。

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

- 实例

  ```
  map<int,string> imap;
  map<int,
  	string,
  	less<int>,
  	alloc>imap;
  template<int,
           pair<const int,string>,
           select1st<pair<const int,string>>,
           less<int>,
           alloc
           >
     class rb_tree;
  ```

- 测试

  ```
  void test_multimap(long& value)
  {
  	cout<<"\n test_multimap().........\n";
  	multimap<long,string> c;
  	char buf[10];
  	clock_t timeStart = clock();
  	for(long i=0;i< value ;++i)
  	{
  		try{
  			snprintf(buf,10,"%d",rand());
  			//multimap can't use [] as insertion
  			c.insert(pair<long,string>(i,buf));
  		}
  		catch(exception& p)
  		{
  			cout<<"i="<<i<<" "<<p.what()<<endl;
  			abort();
  		}
  	}
  	cout<<"milli-seconds:"<<(clock()-timestart)<<endl;
  	cout<<"multimap.size()="<<c.size()<<endl;
  	cout<<"multimap.max_size()="<<c.max_size()<<endl;
  	
  	long target = get_a_target_long();
  		timeStart = clock();
  	auto pItem = c.find(target);
  		cout<<"c.find(),milli-seconds: "<<(clock()-timeStart)<<endl;
  	if(pItem != c.end())
  		cout<<"found, value = "<<(*pItem).second << endl;
  	else
  		cout<<"not found!"<<endl;
  }
  ```

- map可以使用操作符[]。如果map中存在这个key，则返回这个key关联的data，如果不存在则会在适当的位置创建一个pair（key，默认值的data）

  ```
  mapped_type& operator[](const key_type& _k)
  {
      //
  }
  ```

- 中括号[]和insert哪个效率高 

  - insert效率高于中括号，因为中括号其实先进行了二分查找之后，再调用insert插入元素。

#### Unordered Containers

##### Hashtable

-  hashtable相比红黑树来讲，算法要相对简单，但是占用内存要大于红黑树。具体算法不多赘述。

- separate chaining分离链表法：把需要碰撞处理的元素直接让它像链表一样串起来。

  - 为了防止其中某一个链表过长，就需要在合适时间打散所有元素重新排列，我们把初始的hashtable空间叫做篮子（bucket），当我们放入的元素个数大于篮子个数时（GNUC初始篮子个数为53），就要扩充篮子个数，扩充数量为当前篮子个数2倍附近的一个质数（比如：53扩充到97），然后将所有元素重新从头开始放入新篮子。

  ```
  template <class Value,
  		  class Key,
  		  class HashFcn,/* hashfcn as a function to transfer an object to a hashcode */
  		  class ExtractKey,
  		  class EqualKey,
  		  class Alloc = alloc >
  class hashtable{
  	public:
  		typedef HashFcn hasher;
  		typedef EqualKey key_equal;
  		typedef size_t	size_type;
  	private：
  		hasher hash;
  		key_equal euqals;
  		ExtractKey get_key;
		
  		typedef _hashtable_node<Value> node;
  		
  		vector<node*,Alloc>buckets;
  		size_type num_elements;
  	public:
  		size_type bucket_count() const { return buckets.size();}
  	... 
  }		  
  //iterator
  
  template<class Value,class Key,
  		class HashFcn,class ExtractKey,
  		class EqualKey,class Alloc>
  struct _hashtable_iterator{
  	...
  	node* cur;
  	hashtable* ht;
  };
  ```
  
  

##### Unordered Set/Multiset

- unordered 由C++11引入，由hash过渡而来。

##### Unordered Map/Multimap

### 分配器（Allocators）

- 分配器是容器的幕后英雄，负责内存的使用，但是不建议用户直接使用它，因为没有需要用它，它是专门为容器服务的，了解分配器可以更好的掌握容器的操作效率，方便我们挑选合适的容器；
- 分配器操作内存的实质还是调用C Runtime Library的malloc和free（可以说c++的new以及任何其他上层操作内存的方式最终一层层调用都是回归到malloc和free这两个函数上）,这两个函数再根据不同平台再去调用各自的System API。
- 所以每次申请的内存后系统分配给我们的远远大于我们实际申请的空间，因为系统要给这块内存附加一定的标识（也可叫做cooking或者额外开销），以方便释放操作，因为附加的部分是固定的，也就说我们申请的内存越大，附加部分占用的比重就越小。
- 用分配器而不直接用C RunTime Library提供的malloc函数，其本质上是减少内存的额外开销，也就是说分配器会预先开好一定的空间，尽量减少malloc的使用次数，它不是需要多少开多少，也就是我们常说的内存池设计。

### 算法（Algorithms）

### 迭代器（Iterators）

### 适配器（Adapters）

### 仿函数（Functors）


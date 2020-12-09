## C++ STL

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

##### Set/Multiset

##### Map/Multimap

#### Unordered Containers

##### Unordered Set/Multiset

##### Unordered Map/Multimap

### 分配器（Allocators）

### 算法（Algorithms）

### 迭代器（Iterators）

### 适配器（Adapters）

### 仿函数（Functors）


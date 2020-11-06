## Java SE 多线程

### 什么是线程

 - 在一个单独的线程中执行一个任务的简单过程

    - 将任务代码移到实现了Runnable接口的类

       - ```
         public interface Runnable
         {
         	void run();
         }
         class MyRunnable implements Runnable
         {
         	public void run()
         	{
         		task code
         	}
         }
         ```

   - 创建一个类对象

     - ```
       Runnable r =  new Thread(r);
       ```

   - 由Runnable创建一个Thread对象

     - ```
       Thread t =new Thread(r);
       ```

   - 启动线程

     - ```
       t.start();
       ```

   **另一种方法**：

   ```
   class MyThread extends Thread
   {
   	public void run()
   	{
   		task code
   	}
   }
   ```

   ​		然后，构造一个子类的对象，并调用start方法。不过，这种方法已经不推荐。应该从运行机制上减少需要并行运行的数量。如果有很多任务，要为每个任务创建一个独立的线程所付出的代价太大了。

   **警告**：

   ​	不要调用Thread类或Runnable对象的run方法。

   #### 线程的start和run方法

   - **start（）方法**来启动线程，真正实现了多线程运行，这时无需等待run方法体代码执行完毕而直接继续执行下面的代码： 通过调用Thread类的start()方法来启动一个线程，这时此线程是处于就绪状态，并没有运行。然后通过此Thread类调用方法run()来完成其运行操作的，这里方法run()称为线程体，它包含了要执行的这个线程的内容，Run方法运行结束，此线程终止，而CPU再运行其它线程。

     - ```
       public class TestThread implements Runnable{
       	public void run(){
       		for(int i=1;i<=3;i++){
       			System.out.println(Thread.currentThread().getName()+"---"+i);
       		}
       	}
       	public static void main(String[] args) {
       		for(int i=0;i<2;i++){
       			TestThread testThread=new TestThread();
       			Thread thread=new Thread(testThread);
       			System.out.println("[线程"+(i+1)+"]正在启动！");
       			thread.start();
       		}
       	}
       }
       
       ```

     - 控制台输出：

     - ```
       [线程1]正在启动！
       [线程2]正在启动！
       Thread-0---1
       Thread-0---2
       Thread-0---3
       Thread-1---1
       Thread-1---2
       Thread-1---3
       
       ```

   - **run（）方法**当作普通方法的方式调用，程序还是要顺序执行，还是要等待run方法体执行完毕后才可继续执行下面的代码： 而如果直接用run方法，这只是调用一个方法而已，程序中依然只有主线程–这一个线程，其程序执行路径还是只有一条，这样就没有达到写线程的目的。

     - ```
       public class TestThread implements Runnable{
       	public void run(){
       		for(int i=1;i<=3;i++){
       			System.out.println(Thread.currentThread().getName()+"---"+i);
       		}
       	}
       	public static void main(String[] args) {
       		for(int i=0;i<2;i++){
       			TestThread testThread=new TestThread();
       			Thread thread=new Thread(testThread);
       			System.out.println("[线程"+(i+1)+"]正在启动！");
       			thread.run();
       		}
       	}
       }
       
       ```

     - 控制台输出：

     - ```
       [线程1]正在启动！
       main---1
       main---2
       main---3
       [线程2]正在启动！
       main---1
       main---2
       main---3
       ```

       

### 中断线程



### 线程状态

- 线程有以下6种状态：
  - New（创建）
  - Runnable（可运行）
  - Blocked（阻塞）
  - Waiting（等待）
  - Time waiting（计时等待）
  - Terminated（被终止）

### 线程属性

	#### 线程优先级

- **warning**：不要将程序构建为功能的正确性依赖于优先级。
- 在Java语言程序设计中，每一个线程会有一个默认的优先级---继承其父线程的优先级。
  - 也可以用setPriority来修改线程的优先级。优先级设置在Min（1）和Max（10）之间。
- 线程调度会优先选择优先级高的线程。
- 线程的优先级依赖于操作系统。比如：windows只提供7个优先级。Java的优先级会映射到系统提供的优先级上。

#### 守护线程

 - 调用

    - ```
      t.setDaemon(true)
      ```

   可以将线程转换位守护线程（daemon thread）。守护线程的唯一用途就是为其他线程提供服务。

#### 线程组和处理未捕获异常的处理器

- **线程组**是一个可以统一管理的线程集合。（基本弃用）
- 线程的run方法不能抛出任何被检测的异常，但是，不被检测的异常会导致线程终止。在这种情况下，线程就死亡了。
- 但是，不需要任何catch子句来处理的可以被传播的异常。相反，就在线程死亡之前，异常被传递到一个用于未捕获异常的处理器。

### 同步

### 阻塞队列




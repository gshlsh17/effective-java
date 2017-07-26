# effective-java
Effective Java 第二版(Joshua Bloch)，学习代码，笔记

# 项目内容
主要展示了effective java书中根据建议如何写出更好的代码。
每一章对应一个包，如第五章对应chapter5这个包，有些简单的建议直接
写在一个类中，类名为Item+数字，负载的建议写在对应的Item+数字包下。
如包下的Item23对应书中的第二十三条建议。

**方法前缀有bad的表示不好的写法，前缀为good表示好的写法**


# 78条建议
## 第八章 通用程序设计
### 45：将局部变量的作用域最小化

## 第九章 异常
### 57：只针对异常的情况才是用异常
异常是为了在异常情况下使用而设计的，不要将异常用于普通的控制流。同时设计良好的API也不应该强迫它的客户端为了正常的控制流而使用异常。
### 58：对可恢复的情况使用受检异常，对编程错误使用运行时异常
Java三种可抛出结构：*受检的异常*，*运行时异常*，*错误*

使用异常的主要原则：**如果期望调用者能够适当地恢复，对于这种情况就应该使用受检的异常**。
一条简单的规则：对于可恢复的情况，使用受检的异常；对于程序错误，使用运行时异常。
我们也可以在异常类中定义方法，这些方法可以为捕获异常的代码提供额外的信息。
### 59：避免不必要地使用受检的异常
### 60：优先使用标准的异常
### 61：抛出与抽象相对应的异常
更高层的实现应该捕获底层的异常，同时抛出可以按照高层抽象进行解释的异常。如下所示
```
// Exeception Translation
try {
    // use lower-level abstraction to do our bidding
} catch (LowerLevelException e){
    throw new HigherLevelException(...);
}
```
JDK中的AbstractSequentialList类的get方法就使用了异常转移
```
    public E get(int index) {
        try {
            return listIterator(index).next();
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index); // 异常转译
        }
    }
```
### 62：每个方法抛出的异常都要有文档
对于方法抛出的异常可以使用throws进行标记
### 63：在细节消息中包含能捕获失败的信息
异常的细节消息能够帮助我们快速的定为问题从而解决问题
### 64：努力使失败保持原子性
### 65：不要忽略异常
```
// catch块中什么也不做，忽略了异常，这种方式极其不好
try{
    ...
} catch(SomeExeception e){

}
```

## 第十章：并发
### 68:executor和task优先于线程
进行多线程编程的过程中，尽量使用ExecutorService进行线程的执行。JDK中Executor框架将线程的工作和执行分离，
这样我们在平时的编程中只需要关注如何定义具体的任务逻辑，任务的执行交给ExecutorService执行，当然我们也要考虑
线程池的具体配置
### 69：并发工具先于wait和notify
在平常的编程中，我们应该优先选用java并发包为我们提供的工具，而不是使用原始的wait和notify操作，
因为wait和notify操作很难正确使用。
java.util.concurrent包中的工具可以分为三类
1. Executor Framework，这主要是ExecutorService等类，用于线程池以及调度作业等。
2. Concurrent Collection(并发集合)，用于在并发情况下代替Collection集合。如ConcurrentHashMap，CopyOnWriteArrayList等
3. Synchronizer(同步器)，用于控制同步访问，如ReentrantLock、CountDownLatch、Semophore等
如果使用wait方法，则必须在while循环内部使用；一般情况下，应该优先使用notifyAll而不是notify
### 70：线程安全性的文档化
在我们编写多线程程序时，应该在文档中声明类或者方法的线程安全性。
如果我们将锁放再对象上，有可能会发生拒绝服务攻击，即持有此类或此类对象的线程超时持有或者永不释放，那么其他线程就获取不到此类或此类对象，造成访问失败。我们可以在类内部提供私有锁来避免这种攻击，代码如下
```
// 内部私有锁，用final修饰的好处是可以避免修改lock内容，从而更安全的进行并发访问
private final Object lock = new Object();
// foo持有的是类中的私有锁lock
public void foo(){
    synchronized(lock){
        ...
    }
}
```
### 71：慎用延迟初始化
一般情况下，不需要延迟初始化，如果为了性能，采用了延迟初始化，要考虑线程安全。
对于实例域，采用双重检查模式；对于静态域，使用lazy initialization holder class idiom。对于可以接受重复初始化的实例域
，也可以考虑使用单重检查模式。代码在chapter10/item71包下。
lazy initialization holder class idiom

### 72：不要依赖线程调度器
应用程序的正确性不能依赖线程调度器，并且不要依赖Thread.yield或者线程优先级。

### 73：避免使用线程组
线程组没有提供太多有用的功能，并且许多功能还是有缺陷的。应该使用线程池executor来替代。




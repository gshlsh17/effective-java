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
在使用局部变量时，尽量是在第一次使用的地方进行声明，并且如果可以进行初始化，就在声明处进行初始化。
### 46：for-each循环优先于传统的for循环
for-each在简洁性和预防Bug方面有着传统for循环无法比拟的优势，并且没有性能损失。但是在下面三种情况下不能使用for-each
1. 过滤-当遍历集合的同时要删除集合中的部分元素，需要使用迭代器，调用迭代器的remove方法
2. 转换-遍历集合或者数组时，需要替换其中部分元素的值，那么需要迭代器或者元素索引来定为元素从而替换元素的值
3. 并行迭代-当要同时遍历多个集合时，并且多个集合的遍历是同步增加的，那么需要iterator或者元素的索引
### 47：了解和使用类库
平时编程过程中要尽量使用Java的类库。对于java.lang、java.util、以及java.io中的内容要掌握，其他类库可以根据需要学习
### 48：如果需要精确的答案，请避免使用float和double
对于任何需要精确计算答案的程序，都不要使用float和double。使用BigDecimal，int，long实现。如果数值范围没有超过9位十进制数字，使用int；如果不超过18位使用long，超过18位必须使用BigDecimal。同时BigDecimal能够选择舍入方式，但是BigDecimal性能会有损失。
### 49：基本类型优先于装箱基本类型
一般选择基本类型，除非必须要使用装箱基本类型
### 50：如果其他类型更合适，则尽量避免使用字符串
注意在平时的编程中，尽量选择合适的类型表示数据，不要将所有数据都用字符串表示，不要滥用字符串。

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




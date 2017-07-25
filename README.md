# effective-java
Effective Java 第二版(Joshua Bloch)，学习代码，笔记

# 项目内容
主要展示了effective java书中根据建议如何写出更好的代码。
每一章对应一个包，如第五章对应chapter5这个包，有些简单的建议直接
写在一个类中，类名为Item+数字，负载的建议写在对应的Item+数字包下。
如包下的Item23对应书中的第二十三条建议。

**方法前缀有bad的表示不好的写法，前缀为good表示好的写法**


# 78条建议

## 并发
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



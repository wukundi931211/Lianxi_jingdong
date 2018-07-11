package com.example.tianlong.lianxi_jingdong.network;

/**
 * @file FileName
 *  网络请求单例
 */

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.tianlong.lianxi_jingdong.App;

/**
 * getInstance的使用:
 * 在主函数开始时调用，返回一个实例化对象，此对象是static的，在内存中保留着它的引用，即内存中有一块区域专门用来存放静态方法和变量，
 * 可以直接使用，调用多次返回同一个对象。
 * getInstance 和 new的区别；
 *  大部分类都可以用new，
 *     new就是通过生产一个新的实例对象，或者在栈上声明一个对象，每部分的调用
 *  1.  都是用的一个新的对
 * 2. getInstance在单例模式(保证一个类仅有一个实例，并提供一个访问它的全局访问点)的类中常见，用来生成唯一的实例，getInstance往往是static的。
 * @return
 */
public class VolleySingleton {
    /**
     * 1. 定义一个实例
     *
     * volatile就可以说是java虚拟机提供的最轻量级的同步机制
     * 被volatile修饰的变量能够保证每个线程能够获取该变量的最新值，
     * 从而避免出现数据脏读的现象  如同上了一把锁
     */
    public static volatile VolleySingleton volleySingleton;

    //2. 定义队列
    private static RequestQueue queue;

    //3. 定义上下文
    private static Context context;

    //4. 构造方法
    public VolleySingleton(Context context) {
        this.context = context;
        //5.实例队列
        queue = getQueue();
    }



    /**   7.
     * 双重检测机制（懒汉模式）
     *   1.synchroized 这个方法是为锁住，synchronized锁住的是括号里的对象,而不是代码。
     *   在Java中，synchronized关键字是用来控制线程同步的，就是在多线程的环境下，
     *   控制synchronized代码段不被多个线程同时执行。
     *   synchronized既可以加在一段代码上，也可以加在方法上。
     *   对于非static的synchronized方法，锁的就是对象本身也就是this。
     *
     *  2.当synchronized锁住一个对象后，别的线程如果也想拿到这个对象的锁，
     *  就必须等待这个线程执行完成释放锁，才能再次给对象加锁，这样才达到线程同步的目的。
     *  即使两个不同的代码段，都要锁同一个对象，那么这两个代码段也不能在多线程环境下同时运行。
     *
     *  3.所以我们在用synchronized关键字的时候，能缩小代码段的范围就尽量缩小，
     *    能在代码段上加同步就不要再整个方法上加同步。这叫减小锁的粒度，使代码更大程度的并发。
     *    原因是基于以上的思想，锁的代码段太长了，别的线程是不是要等很久
     *
     *  4.synchronized(VolleySingleton.class)实现了全局锁的效果。
     *     最后说说static synchronized方法，static方法可以直接类名加方法名调用，
     *     方法中无法使用this，所以它锁的不是this，而是类的Class对象，
     *     所以，static synchronized方法也相当于全局锁，相当于锁住了代码段
     */
    public static VolleySingleton getInstance1(){
        if (volleySingleton == null){
            synchronized (VolleySingleton.class){
                if (volleySingleton == null){
                    volleySingleton = new VolleySingleton(App.context);
                }
            }
        }
        return volleySingleton;
    }
    /**
     *   8. 静态内部类
     */
    private static class SingletonInstance{
        //此类加载好时 第二次就加载时不用二次加载
        //INSTANCE  实例
        private static final VolleySingleton INSTANCE = new VolleySingleton(App.context);
    }

    /**
     *  9. 静态内部类实现返回单列
     * @return
     */
    public static  VolleySingleton getInstance2(){
        return SingletonInstance.INSTANCE;
    }

    /**
     * 10. 添加请求到队列
     * @param request
     * @param <T>
     */
    public <T> void  addToRequestQueue(Request<T> request){
        queue.add(request);
    }


    /**
     * 5.获得队列的方法
     * @return
     */
    public RequestQueue getQueue() {
        //若果队列是空
        if (queue==null){
            //通过volley创建新的队列
            queue = Volley.newRequestQueue(context);
        }
        return queue;
    }

    /**
     * 11.取消请求
     * @param tag
     */
    public void cancelReq(String tag){
        if (queue!=null){
            queue.cancelAll(tag);
        }
    }
}

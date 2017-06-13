# 面试问题

> 心有猛虎，细嗅蔷薇。

### 多线程(未完待续)

1. #### 进程和线程的区别

   - **进程**：每个进程都有独立的代码和数据空间，进程间的切换会有较大的开销，一个进程包含1--n个线程。
   - **线程**：同一类线程共享代码和数据空间，每个线程有独立的运行栈和程序计数器(PC)，线程切换开销小。
   - **线程和进程**一样分为五个阶段：创建、就绪、运行、阻塞、终止。
   - 多进程是指[操作系统](http://lib.csdn.net/base/operatingsystem)能同时运行多个任务（程序）。
   - 多线程是指在同一程序中有多个顺序流在执行

2. #### 并行与并发：

   并行：多个cpu实例或者多台机器同时执行一段处理逻辑，是真正的同时。

   并发：通过cpu调度算法，让用户看上去同时执行，实际上从cpu操作层面不是真正的同时。并发往往在场景中有公用的资源。

### TCP/IP(待看)

[面试 -- 网络 TCP/IP](https://link.juejin.im/?target=https%3A%2F%2Fgold.xitu.io%2Fpost%2F586cfcf8da2f600055ce8a8d)

### Http协议

[面试 -- 网络 HTTP](https://juejin.im/post/5872309261ff4b005c4580d4)

1. #### 了解 Web 及网络基础

   - 发送端在层与层间传输数据时，没经过一层都会被加上首部信息，接收端每经过一层都会删除一条首部

2. #### 简单的 HTTP 协议

   - 客户端像服务器发起请求时会生成一段请求报文，请求报文是由请求方法，URL，协议版本，可选的请求首部字段和内容实体构成。
   - 接收到请求的服务器，会将请求内容的处理结构以响应的形式返回。响应报文基本上由协议版本，状态码，用以解释状态的原因短语，可选的响应首部字段以及实体主体构成。
   - HTTP 是不保存状态的协议和 Cookie 的简单介绍
   - HTTP 协议对于发送的请求和响应不做持久化处理。这时候引入了 Cookie 技术用于状态管理。Cookie 对用与登录的状态管理，没有 Cookie 这个技术的话，因为 HTTP 不保存状态，每次打开新网页都必须再次登录。
   - Cookie 会根据响应报文中的 Set-Cookie 字段来通知客户端自动保存 Cookie。下次请求时会自动发送 Cookie，服务器会比对数据得到状态结果。

3. #### Post 和 Get 的区别

   - Get 请求能缓存，Post 不能
   - Post 相对 Get 安全一点点，因为Get 请求都包含在 URL 里，且会被浏览器保存历史纪录，Post 不会，但是在抓包的情况下都是一样的。
   - Post 可以通过 request body来传输比 Get 更多的数据，Get 没有这个技术
   - URL有长度限制，会影响 Get 请求，但是这个长度限制是浏览器规定的，不是 RFC 规定的
   - Post 支持更多的编码类型且不对数据类型限制时间分发机制

4. #### 常见状态码

   **2XX 成功**

   - 200 OK，表示从客户端发来的请求在服务器端被正确处理
   - 204 No content，表示请求成功，但响应报文不含实体的主体部分
   - 206 Partial Content，进行范围请求

   **3XX 重定向**

   - 301 moved permanently，永久性重定向，表示资源已被分配了新的 URL
   - 302 found，临时性重定向，表示资源临时被分配了新的 URL
   - 303 see other，表示资源存在着另一个 URL，应使用 GET 方法丁香获取资源
   - 304 not modified，表示服务器允许访问资源，但因发生请求未满足条件的情况
   - 307 temporary redirect，临时重定向，和302含义相同

   **4XX 客户端错误**

   - 400 bad request，请求报文存在语法错误
   - 401 unauthorized，表示发送的请求需要有通过 HTTP 认证的认证信息
   - 403 forbidden，表示对请求资源的访问被服务器拒绝
   - 404 not found，表示在服务器上没有找到请求的资源

   **5XX 服务器错误**

   - 500 internal sever error，表示服务器端在执行请求时发生了错误
   - 503 service unavailable，表明服务器暂时处于超负载或正在停机维护，无法处理请求

5. #### HTTP 首部

   1. **通用首部**

   指请求报文和响应报文都可以使用的字段

   - Cache-Control
     - no-cache 指客户端不缓存过期资源
     - no-store 指不进行缓存
     - max-age 指缓存资源的缓存时间比指定的值小，那么客户端就接受缓存资源，且缓存服务器不对资源有效性进行再次确认
   - Connection 指控制不再转发给代理的首部字段（Hop-by-hop），管理持久连接
     - close 指服务器像明确断开连接
     - Keep-Alive 指保存持久连接，HTTP/1.1前默认连接是非持久性的，如需要保存持久连接，需要增加此字段
   - Upgrade 可以用来指定一个完全不同的通信协议，对于这个字段，服务器可以返回101状态码

   2. **请求首部字段** 

   - Accept 指用户代理能够处理的媒体类型及媒体类型的相对优先级
   - Accept-Encoding 指用来告知服务器用户代理支持的内容编码及内容编码的优先级顺序
   - Authorization 指用来告知服务器，用户代理的认证信息
   - Host 当一个 IP 下存在多个域名时，帮助服务器知道要请求的具体主机
   - User-Agent 会讲创建请求的浏览器和用户代理名称等信息传达给服务器

6. #### HTTPS

   - HTTPS 是 HTTP 建立在 SSL/TLS 安全协议上的。
   - 在 Android 中，客户端本地会存放着 CA 证书，在HTTPS 请求时，会首先像服务器索要公钥，获得公钥后会使用本地 CA 证书验证公钥的正确性，然后通过正确的公钥加密信息发送给服务器，服务器会使用私钥解密信息。
   - HTTPS 相对于 HTTP 性能上差点，因为多了 SSL/TLS 的几次握手和加密解密的运算处理，但是加密解密的运算处理已经可以通过特有的硬件来加速处理。

### Bitmap 高效加载

1. **BitmapFactory**类提供四种方法：decodeFile、decodeResource、decodeStream和decodeByteArray；其中decodeFile和decodeResource间接的调用了decodeStream方法；这四个方法最终在Android底层实现。

2. 如何高效的加载Bitmap？核心思想：按需加载；很多时候ImageView并没有原始图片那么大，所以没必要加载原始大小的图片。采用**BitmapFactory.Options**来加载所需尺寸的图片。 通过BitmapFactory.Options来缩放图片，主要是用到了它的inSampleSize参数，即采样率。 inSampleSize应该为2的指数，如果不是系统会向下取整并选择一个最接近2的指数来代替；缩放比例为1/（inSampleSize的二次方）。

3. **Bitmap内存占用**：拿一张1024*1024像素的图片来说，假定采用ARGB8888格式存储，那么它占用的内存为1024*1024*4，即4MB。

4. **缩放图片（压缩）**：

   ~~~java
   public static Bitmap decodeBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //1. 将BitmapFactory.Options的inJustDecodeBounds参数设置为true并加载图片。
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        //2. 根据采样率的规则并结合目标View的所需大小计算出采样率inSampleSize。
        options.inSampleSize = calcuateInSampleSize(options, reqWidth, reqHeight);

        //3. 将BitmapFactory.Options的inJustDecodeBounds参数设置为false，然后重新加载图片。
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
     //获取采样率
    private static int calcuateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
   // 显示图片
   Bitmap bitmap = DecodeBitmap.decodeBitmapFromResource(getResources(), R.mipmap.haimei2, 400, 400);
   imageView.setImageBitmap(bitmap);
   ~~~

   当inJustDecodeBounds参数为true时，BitmapFactory只会解析图片的原始宽/高信息，并不会真正的加载图片。需要注意这时候BitmapFactory获取的图片宽/高信息和图片的位置与程序运行的设备有关。

### Bitmap 缓存策略

1. 如何减少流量消耗？**缓存**。当程序第一次从网络上加载图片后，将其缓存在存储设备中，下次使用这张图片的时候就不用再从网络从获取了。一般情况会把图片存一份到内存中，一份到存储设备中，如果内存中没找到就去存储设备中找，还没有找到就从网络上下载。
2. 目前常用的缓存算法是**LRU**，是近期最少使用算法，当缓存满时，优先淘汰那些近期最少使用的缓存对象。采用LRU算法的缓存有两种：LRUCache（内存缓存）和DiskLruCache（存储缓存）。
3. **LruCache**是Android3.1所提供的一个缓存类，通过support-v4兼容包可以兼容到早期的Android版本。LruCache是一个泛型类，是线程安全的，内部采用LinkedHashMap以强引用的方式存储外界缓存对象，并提供get和put方法来完成缓存的获取和添加操作，当缓存满时，LruCache会移除较早的使用的缓存对象。LruCache初始化时需重写**sizeOf**方法，用于计算缓存对象的大小。**强引用**：直接的对象引用**软引用**：当一个对象只有软引用存在的时候，系统内存不足的时此对象会被GC回收。**弱引用** : 当一个对象只有弱引用存在的时候，此对象随时会被GC回收。
4. **DiskLruCache**用于实现磁盘缓存，DiskLruCache 得到了 Android 官方文档推荐，但它不属于 Android SDK 的一部分[源码在这里](https://android.googlesource.com/platform/libcore/+/android-4.1.1_r1/luni/src/main/java/libcore/io/DiskLruCache.java)
5. 自己实现一个 ImageLoader，包含
   1. 图片压缩功能
   2. 内存缓存和磁盘缓存
   3. 同步加载和异步加载的接口设计
6. 优化列表卡顿现象
   1. 不要在 getView 中执行耗时操作，不要在 getView 中直接加载图片。
   2. 控制异步任务的执行频率：如果用户刻意频繁上下滑动，getView 方法会不停调用，从而产生大量的异步任务。可以考虑在列表滑动停止加载图片；给 ListView 或者 GridView 设置 setOnScrollListener 并在 OnScrollListener 的onScrollStateChanged 方法中判断列表是否处于滑动状态，如果是的话就停止加载图片。
   3. 大部分情况下，可以使用硬件加速解决莫名卡顿问题，通过设置android:hardwareAccelerated="true"即可为 Activity 开启硬件加速。

### 性能优化

1. #### 布局优化

   1. 布局优化的思想就是尽量减少布局文件的层级，这样绘制界面时工作量就少了，那么程序的性能自然就高了。
      删除无用的控件和层级，其次就是有选择的使用性能较低的ViewGroup，如果布局中既可以使用Linearlayout也可以使用RelativeLayout，那就是用LinearLayout，因为RelativeLayout功能比较复杂，它的布局过程需要花费更多的CPU时间。有时候通过LinearLayou无法实现产品效果，需要通过嵌套来完成，这种情况还是推荐使用RelativeLayout，因为ViewGroup的嵌套相当于增加了布局的层级，同样降低程序性能。

   2. 另一种手段是采用<include>标签、<merge>标签和ViewStub。

      - **<include>标签**
        <include>标签用于布局重用，可以将一个指定的布局文件加载到当前布局文件中。<include>只支持android:layout_开头的属性，当然android:id这个属性是个特例；如果指定了android:layout_*这种属性，那么要求android:layout_width和android:layout_height必须存在，否则android:layout_*属性无法生效。如果<include>指定了id属性，同时被包含的布局文件的根元素也指定了id属性，会以<include>指定的这个id属性为准。

      - **<merge>标签**
        <merge>标签一般和<include>标签一起使用从而减少布局的层级。如果当前布局是一个竖直方向的LinearLayout，这个时候被包含的布局文件也采用竖直的LinearLayout，那么显然被包含的布局文件中的这个LinearLayout是多余的，通过<merge>标签就可以去掉多余的那一层LinearLayout。

      - **ViewStub**
        ViewStub意义在于按需加载所需的布局文件，因为实际开发中，有很多布局文件在正常情况下是不会现实的，比如网络异常的界面，这个时候就没必要在整个界面初始化的时候将其加载进来，在需要使用的时候再加载会更好。在需要加载ViewStub布局时：

        ```java
        ((ViewStub)findViewById(R.id.stub_import)).setVisibility(View.VISIBLE);
        //或者
        View importPanel = ((ViewStub)findViewById(R.id.stub_import)).inflate();
        ```

        当ViewStub通过setVisibility或者inflate方法加载后，ViewStub就会被它内部的布局替换掉，ViewStub也就不再是整个布局结构的一部分了。

   3. 优化工具：

      1. Hierarchy Viewer ，是 Android SDK 自带的一款可视化调试工具，用来检查 Layout **嵌套**和**绘制**时间。
      2. Android Lint 是 Android SDK Tools 中引入的代码检查工具，

2. #### 绘制优化

   View的 onDraw 方法要避免执行大量的操作；

   1. onDraw 中不要创建大量的局部对象，因为 onDraw 方法会被频繁调用，这样就会在一瞬间产生大量的临时对象，不仅会占用过多内存还会导致系统频繁 GC，降低程序执行效率。

   2. onDraw 也不要做耗时的任务，也不能执行成千上万的循环操作，尽管每次循环都很轻量级，但大量循环依然十分抢占 CPU 的时间片，这会造成 View 的绘制过程不流畅。根据 Google 官方给出的标准，View 绘制保持在60fps是最佳的，这也就要求每帧的绘制时间不超过16ms(1000/60)；所以要尽量降低 onDraw 方法的复杂度。

   3. 查看工具：

      开发者选项 -> Show GPU Overdraw，打开后会有不同颜色的区域表示不同的过度绘制次数。

3. #### 内存泄漏优化

   内存泄露是最容易犯的错误之一，内存泄露优化主要分两个方面；
   一方面是开发过程中避免写出有内存泄露的代码，另一方面是通过一些分析工具如LeakCanary或MAT来找出潜在的内存泄露继而解决。

   1. 静态变量导致的内存泄露
      比如Activity内，一静态Conext引用了当前Activity，所以当前Activity无法释放。或者一静态变量，内部持有了当前Activity，Activity在需要释放的时候依然无法释放。

   2. 单例模式导致的内存泄露
      比如单例模式持有了Activity，而且也没用解注册的操作。因为单例模式的生命周期和Application保存一致，生命周期比Activity要长，这样一来就导致Activity对象无法及时被释放。

   3. 属性动画导致的内存泄露
      属性动画中有一类无限循环的动画，如果在Activity播放了此类动画并且没有在onDestroy中去停止动画，那么动画会一直播放下去，并且这个时候Activity的View会被动画持有，而View又持有了Activity，最终导致Activity无法释放。解决办法是在 Activity 的 onDrstroy中调用animator.cancel()来停止动画。

   4. Handler 临时性内存泄漏

      **原因**：Message 发出之后存储在 MessageQueue 中，页面关闭时有些 Message 可能没有被立即处理到。在Message中存在一个 target ， 它是 Handler 的一个引用，Message 在 Queue 中存在的时间过长，就会导致 Handler 无法被回收。如果 Handler 是非静态的，就会导致 Activity 或者 Service 不被回收。

      **解决**：

      - 使用静态 handler 内部类，然后对 handler 持有的引用（this）采取软引用。

      - 在 Activity 关闭的时候，移除消息。

        ~~~java
        mHandler.removeCallbacksAndMessages(null);
        ~~~

4. #### 线程优化

   线程优化的思想是采用线程池，避免程序存在大量的Thread。

5. #### 省电优化

   1. 屏幕：深色比浅色省电。
   2. 网络：
      - WiFi 比移动数据网络省电。尽量在 WiFi 下传输数据。
      - WiFi 下增大每个包的大小。
      - 移动数据下做到批量执行网络请求，避免频繁的间隔网络请求。
      - JSON 比 XML 效率高。
      - 压缩数据格式。
   3. CPU：
      - CPU 频率高费电，并不是利用率高费电。
      - 减少浮点运算。
      - 避免 wakelock（换型手机） 使用不当。
      - 使用 job scheduler。不紧急的任务，耗电量大的任务，放到充电或者有 WiFi 的地方再执行。

6. #### 响应速度优化和ANR日志分析

   响应速度优化的核心思想就是避免在主线程中去做耗时操作，将耗时操作放在其他线程当中去执行。Activity如果5秒无法响应屏幕触摸事件或者键盘输入事件就会触发ANR，而BroadcastReceiver如果10秒还未执行完操作也会出现ANR。
   当一个进程发生ANR以后系统会在/data/anr的目录下创建一个文件traces.txt，通过分析该文件就能定位出ANR的原因。

7. #### ListView优化和Bitmap优化

   ListView/GridView优化：采用ViewHolder避免在getView中执行耗时操作；其次通过列表的滑动状态来控制任务的执行频率，比如快速滑动时不是和开启大量异步任务；最后可以尝试开启硬件加速使得ListView的滑动更加流畅。
   Bitmap优化：主要是想是根据需要对图片进行采样显示。

8. #### 一些性能优化的小建议

   - 避免创建过多的对象，尤其在循环、onDraw这类方法中，谨慎创建对象；
   - 不要过多的使用枚举，枚举占用的内存空间比整形大。
   - 常量使用static final来修饰；
   - 使用一些Android特有的数据结构，比如SparseArray和Pair等，他们都具有更好的性能；
   - 适当的使用软引用和弱引用；
   - 采用内存缓存和磁盘缓存；
   - 尽量采用静态内部类，这样可以避免非静态内部类隐式持有外部类所导致的内存泄露问题。

### 屏幕适配

[屏幕适配的前世今生](http://blog.csdn.net/lin_t_s/article/details/55271002)

**百分比布局**

- PercentRelativeLayout
- PercentFrameLayout

图片适配用.9图

代码适配

### Crash捕获

[Android如何捕获应用的crash信息](http://blog.csdn.net/fishle123/article/details/50823358)

### Https

[Android Https相关完全解析 当OkHttp遇到Https](http://blog.csdn.net/lmj623565791/article/details/48129405)

### Context 使用

[Android Context 上下文 你必须知道的一切](http://blog.csdn.net/lmj623565791/article/details/40481055)

### 单例模式、观察者模式、工厂模式项目中使用情况

### IPC 机制： AIDL、ContentProvider、Messenger 和 Socket的使用 

1. #### IPC基础概念介绍
   1. ##### Serializable接口

      1. Serializable是Java所提供的一个序列化接口；
      2. serialVersionUID是用来辅助序列化和反序列化过程的，原则上序列化后的数据中的serialVersionUID要和当前类的serialVersionUID相同才能正常的序列化。
      3. 静态成员变量属于类不属于对象，所以不会参加序列化过程；其次用transient关键字标明的成员变量也不参加序列化过程。
      4. 重写如下两个方法可以重写系统默认的序列化和反序列化过程

   ~~~java
   private void writeObject(java.io.ObjectOutputStream out)throws IOException{
   }
   private void readObject(java.io.ObjectInputStream out)throws IOException,ClassNotFoundException{
   }
   ~~~

   1. ##### Parcelable接口

      1. Android中特有的序列化方式，效率相对Serializable更高，占用内存相对也更少，但使用起来稍微麻烦点。

      ```java
      public class Book implements Parcelable {
       public static final Creator<Book> CREATOR = new Creator<Book>() {
           @Override
           public Book createFromParcel(Parcel in) {
               return new Book(in);
           }
           @Override
           public Book[] newArray(int size) {
               return new Book[size];
           }
       };
       public int code;
       public String name;
       public Book(int code, String name) {
           this.code = code;
           this.name = name;
       }
       protected Book(Parcel in) {
           code = in.readInt();
           name = in.readString();
       }
       public int describeContents() {
           return 0;
       }
       @Override
       public void writeToParcel(Parcel dest, int flags) {
           dest.writeInt(code);
           dest.writeString(name);
       }
      }
      ```

      1. 序列化功能由**writeToParcel**方法来完成，最终通过Parcel中的一系列write方法完成的。反序列化功能由CREATEOR来完成，其内部标明了如何创建序列号对象和诉诸，并通过Parcel的一系列read方法来完成反序列化过程。内容描述功能由describeContents方法来完成，几乎所有情况都返回0，只有当前对象存在文件描述符时，才返回1。

2. #### Android的IPC方式

   1. 使用**Bundle**：由于Binder实现了Parcelable接口，所以可以方便的在不同进程中传输；Activity、Service和Receiver都支持在Intent中传递Bundle数据。
   2. 使用**文件共享**：两个进程通过读/写一个文件来交换数据；适合对数据同步要求性不高的场景；并要避免并发写这种场景或者处理好线程同步问题。SharedPreferences是个特例，虽然也是文件的一种，但系统在内存中有一份SharedPreferences文件的缓存，因此在多线程模式下，系统对他的读/写就变得不可靠，高并发读写SharedPreferences有一定几率会丢失数据，因此不建议在多进程通讯时采用SharedPreferences。
   3. 使用**Messenger**：Messenger是轻量级的IPC方案，底层实现是AIDL，他对AIDL进行了封装，Messenger 服务端是以串行的方式来处理客户端的请求的，不存在并发执行的情形。
   4. 使用**AIDL**：**服务端**首先创建一个Service用来监听客户端的连接请求，然后创建一个AIDL文件，将暴露给客户端的接口在AIDL文件中声明，最后在Service中实现这个AIDL接口即可。**客户端**首先绑定服务端的Service，绑定成功后，将服务端返回的Binder对象转化成AIDL接口所属的类型，调用相对应的AIDL中的方法。
      1. AIDL支持的数据类型：
         基本数据类型；
         String、CharSequence；
         List 只支持ArrayList,里面的元素必须都能被AIDL所支持；
         Map 只支持HashMap，里面的元素（key和value）必须都能被AIDL所支持；Parcelable 所有实现了Parcelable接口的对象；
         AIDL 所有AIDL接口本身也可以在AIDL文件中使用。
      2. 自定义的Parcelable对象和AIDL对象必须显示的import进来（即使在同一个包）。
      3. 除了基本数据类型，需要用inout表示输入输出型参数。
      4. 为了方便AIDL开发，建议把所有和AIDL相关的类和文件都放在同一个包中，好处在于，当客户端是另一个应用的时候，我们可以直接把整个包复制到客户端工程中去。
      5. RemoteCallbackList是系统专门提供用于删除跨进程listener的接口，RemoteCallbackList是泛型，支持管理任意的AIDL接口，因为所有AIDL接口都继承自android.os.IInterface接口。
      6. 需注意AIDL客户端发起RPC过程的时候，客户端的线程会挂起，如果是UI线程发起的RPC过程，如果服务端处理事件过长，就会导致ANR。
   5. 使用**ContentProvider**
      ContentProvider是Android专门用于不同应用之间进行数据共享的方式，天生适合跨进程通讯，底层同样采用Binder实现。
      ContentProvider主要以表格的形式来组织数据，可以包含多个表；ContentProvider支持普通文件，甚至可以采用内存中得一个对象来进行数据存储。
      通过ContentProvider的notifyChange方法来通知外界当前ContentProvider中的数据已经发生改变。
   6. 使用**Socket**
      Socket也被称为“套接字”，是网络通讯中得概念，分为流式套接字和用户数据报套接字两种，分别对应网络的传输控制层中得TCP和UDP协议。


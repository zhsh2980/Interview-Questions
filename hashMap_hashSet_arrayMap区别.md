### HashSet

* hashSet实现了Set接口,他不允许集合中出现重复元素

  * 将对象存储在HashSet之前需要确保重写了hashCode()跟equals()方法,这样才能比较对象的值是否相等,确保对象中没有存储相同的对象,如果不重写上述两个方法,将使用Object的默认实现

  * add添加方法,成功返回true,重复元素返回false

    

* 源码分析

  * 底层其实使用hashMap存储数据(初始化值同HashMap)可以存放null键

    ```
    /**
     * 默认的无参构造器，构造一个空的HashSet。
     *
     * 实际底层会初始化一个空的HashMap，并使用默认初始容量为16和加载因子0.75。
     */
    public HashSet() {
        map = new HashMap<E,Object>();
    }
    ```

  * add方法添加元素

    ```
    /**
    
     * @param e 将添加到此set中的元素。
     * @return 如果此set尚未包含指定元素，则返回true。
     */
    public boolean add(E e) {
        return map.put(e, PRESENT)==null;
    }
    ```

    分析: 是将整个对象作为一个key储存到HashMap中,根据重写的hashCode跟equals方法确认是否已经存储有e值,如果相当则默认值都为PRESENT,所以新加不会更改key而且保证了唯一性,又因为HashMap源码中添加元素存在返回旧值oldValue != null 返回false,如果不存在HashMap源码中新加返回null,即HashSet的add方法返回true,表示添加成功了,

  * contains跟remove方法

    ```
    
    public boolean contains(Object o) {
        return map.containsKey(o);
        }
        
    public boolean remove(Object o) {
        return map.remove(o)==PRESENT;
        }
    ```

    

### HashMap

1. 数据结构为:HashMap实际上是一个“链表散列”的数据结构，即数组和链表的结合体;

* 根据使用场景设置初始容量及负载因子

* hashMap实现了Map接口,使用键值对进行映射,map中不允许出现重复的键(key)
* Map接口分为两类:TreeMap跟HashMap
  * TreeMap保存了对象的排列次序,hashMap不能
  * HashMap可以有空的键值对(null-null),是非线程安全的,但是可以调用collections的静态方法synchronizeMap()实现
  * HashMap中使用键对象来计算hashcode值  
  * HashMap比较快，因为是使用唯一的键来获取对象 

2. 源码解析:

   ```
   public V put(K key, V value) {
           //其允许存放null的key和null的value，当其key为null时，调用putForNullKey方法，放入到table[0]的这个位置
           if (key == null)
               return putForNullKey(value);
           //通过调用hash方法对key进行哈希，得到哈希之后的数值。该方法实现可以通过看源码，其目的是为了尽可能的让键值对可以分不到不同的桶中
           int hash = hash(key);
           //根据上一步骤中求出的hash得到在数组中是索引i
           int i = indexFor(hash, table.length);
           //如果i处的Entry不为null，则通过其next指针不断遍历e元素的下一个元素。
           for (Entry<K,V> e = table[i]; e != null; e = e.next) {
               Object k;
               //基本数据类型,或者String相同的key即替换键值对中的值,并返回旧值
               if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                   V oldValue = e.value; 
                   e.value = value;
                   e.recordAccess(this);
                   return oldValue;//如果有相等的key值,替换返回旧值
               }
           }
   		//新加元素key-value
           modCount++;
           addEntry(hash, key, value, i);
           return null; //新加入元素: key 以前是不存在，那么返回 null
   }
   ```

    1. 通过源码我们可以得出: 

       	1. 当我们往HashMap中put元素的时候,先根据key的hashCode重新计算hash值,根据hash值得到元素在数组中的下标,

       	2. 如果数组该位置已经存放有其他元素,则在这个位置上以链表的形式存放,新加入的放在链头,最后加入的放在链尾,如果链表中有相应的key则替换value值为最新的并返回旧值;

       	3. 如果该位置上没有其他元素,就直接将该位置放在此数组中的该位置上; 

       	4. 我们希望HashMap里面的元素位置尽量的分布均匀写,使得每个位置上的元素只有一个,这样当使用hash算法得到这个位置的时候,马上就可以知道对应位置的元素就是我们要的,不用再遍历链表,这样就大大优化了查询效率;

       	5. 计算hash值的方法hash(int h),理论上是对数组长度取模运算 % ,但是消耗比较大,源代码调用为:

           ```
           /**
                * Returns index for hash code h.
                */
           static int indexFor(int h, int length) {  
               return h & (length-1); //HashMap底层数组长度总是2的n次方,每次扩容为2倍
           }
           
           //Map数组初始化取值:
           //当 length 总是 2 的 n 次方时，h& (length-1)运算等价于对 length 取模，也就是 h%length，但是 & 比 % 具有更高的效率。
           // Find a power of 2 >= initialCapacity
           int capacity = 1;
               while (capacity < initialCapacity)  
                   capacity <<= 1; //每次增加2倍,所以总长度一定为2的次方
           ```

           6. 为何hash码初始化为2的次方数探讨:

              ![](https://weiai1314-1258100421.cos.ap-beijing.myqcloud.com/hash.png?q-sign-algorithm=sha1&q-ak=AKIDFLAtiwo9kpqaGIXrli1BBbG1m8URA861&q-sign-time=1542618093;1542619893&q-key-time=1542618093;1542619893&q-header-list=&q-url-param-list=&q-signature=b1dee6f2f709b1d2eaa5d71ba88cce8a382f2e46&x-cos-security-token=f1e3010438cba4f871b09d01c6c63ff4a614b6ff10001)

              分析:

              1. 当它们和 15-1（1110）“与”的时候，产生了相同的结果，也就是说它们会定位到数组中的同一个位置上去，这就产生了碰撞，8 和 9 会被放到数组中的同一个位置上形成链表，那么查询的时候就需要遍历这个链 表，得到8或者9，这样就降低了查询的效率。同时，我们也可以发现，当数组长度为 15 的时候，hash 值会与 15-1（1110）进行“与”，那么最后一位永远是 0，而 0001，0011，0101，1001，1011，0111，1101 这几个位置永远都不能存放元素了，空间浪费相当大，更糟的是这种情况中，数组可以使用的位置比数组长度小了很多，这意味着进一步增加了碰撞的几率，减慢了查询的效率！

              2. 而当数组长度为16时，即为2的n次方时，2n-1 得到的二进制数的每个位上的值都为 1，这使得在低位上&时，得到的和原 hash 的低位相同，加之 hash(int h)方法对 key 的 hashCode 的进一步优化，加入了高位计算，就使得只有相同的 hash 值的两个值才会被放到数组中的同一个位置上形成链表。 

              3. 当数组长度为 2 的 n 次幂的时候，不同的 key 算得得 index 相同的几率较小，那么数据在数组上分布就比较均匀，也就是说碰撞的几率小，相对的，查询的时候就不用遍历某个位置上的链表，这样查询效率也就较高了 

                 

           * 总结: 根据上面 put 方法的源代码可以看出，当程序试图将一个key-value对放入HashMap中时，程序首先根据该 key 的 hashCode() 返回值决定该 Entry 的存储位置：如果两个 Entry 的 key 的 hashCode() 返回值相同，那它们的存储位置相同。如果这两个 Entry 的 key 通过 equals 比较返回 true，新添加 Entry 的 value 将覆盖集合中原有 Entry 的 value，但key不会覆盖。如果这两个 Entry 的 key 通过 equals 比较返回 false，新添加的 Entry 将与集合中原有 Entry 形成 Entry 链，而且新添加的 Entry 位于 Entry 链的头部——具体说明继续看 addEntry() 方法的说明。 

             

   #### 归纳HashMap

     1. **简单地说，HashMap 在底层将 key-value 当成一个整体进行处理，这个整体就是一个 Entry 对象。HashMap 底层采用一个 Entry[] 数组来保存所有的 key-value 对，当需要存储一个 Entry 对象时，会根据 hash 算法来决定其在数组中的存储位置，在根据 equals 方法决定其在该数组位置上的链表中的存储位置；当需要取出一个Entry 时，也会根据 hash 算法找到其在数组中的存储位置，再根据 equals 方法从该位置上的链表中取出该Entry。** 
     2. 初始化HashMap默认值为16, 初始化时可以指定initial capacity，若不是2的次方，HashMap将选取第一个大于initial capacity 的2n次方值作为其初始长度 ;

     3. 初始化负载因子为0.75,如果超过16 * 0.75 = 12个数据就会将数组扩容为2倍 长度为32 , 并后重新计算每个元素在数组中的位置，而这是一个非常消耗性能的操作,如果我们已经预知 HashMap 中元素的个数，那么预设元素的个数能够有效的提高 HashMap 的性能。 如果负载印在越大,对空间利用越充分,但是会降低查询效率,如果负载因子越小,则散列表过于稀疏,对空间造成浪费(时间<-> 空间转换: 0.75最佳)

     4. 多线程中的检测机制:Fail-Fast,通过标记modCount(使用volatile修饰,保证线程间修改的可见性) 域 修改次数,在迭代初始化时候赋值,以后每次next的时候都会判断是否相等

        ```
        HashIterator() {
            expectedModCount = modCount;
            if (size > 0) { // advance to first entry
            Entry[] t = table;
            while (index < t.length && (next = t[index++]) == null)  
                ;
            }
        }
        final Entry<K,V> nextEntry() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        ```

     5. 建议使用concurrent HashMap在多线程中

         ```
         　Map map = new HashMap();
         　　Iterator iter = map.entrySet().iterator();
         　　while (iter.hasNext()) {
         　　Map.Entry entry = (Map.Entry) iter.next();
         　　Object key = entry.getKey();
         　　Object val = entry.getValue();
         　　}
         ```


### Hashtable(默认容量 11 ,加载因子0.75)

 * 概述

   和HashMap一样儿,Hashtable也是一个散列表,它存储的内容是键值对,implements Map<>

   

* 成员变量的含义:table, count, threshold, loadFactor, modCount 

  * table是一个Entry[]数组类型,Entry就是一个单向链表,而key-value 键值对就是存储在entry中
  * count是Hashtable的大小,是保存键值对的数量
  * threshold为判断是否需要扩容 = 容量 * 加载因子
  * loadFactor 加载因子
  * modCount用来实现fail-fast同步机制

* put方法

  1. 判断value 是否为null,为空抛出异常;
  2. 计算key的hash值获得key在table数组的位置index,如果table[index]元素不为空,进行迭代,如果遇到相同的key,则直接替换,并返回旧的value
  3. 否则将其插入到table[index]位置上

  ```
  public synchronized V put(K key, V value) {
          // Make sure the value is not null确保value不为null
          if (value == null) {
              throw new NullPointerException();
          }
  
          // Makes sure the key is not already in the hashtable.
          //确保key不在hashtable中
          //首先，通过hash方法计算key的哈希值，并计算得出index值，确定其在table[]中的位置
          //其次，迭代index索引位置的链表，如果该位置处的链表存在相同的key，则替换value，返回旧的value
          Entry tab[] = table;
          int hash = hash(key);
          int index = (hash & 0x7FFFFFFF) % tab.length;
          for (Entry<K,V> e = tab[index] ; e != null ; e = e.next) {
              if ((e.hash == hash) && e.key.equals(key)) {
                  V old = e.value;
                  e.value = value;
                  return old;
              }
          }
  
          modCount++;
          if (count >= threshold) {
              // Rehash the table if the threshold is exceeded
              //如果超过阀值，就进行rehash操作
              rehash();
  
              tab = table;
              hash = hash(key);
              index = (hash & 0x7FFFFFFF) % tab.length;
          }
  
          // Creates the new entry.
          //将值插入，返回的为null
          Entry<K,V> e = tab[index];
          // 创建新的Entry节点，并将新的Entry插入Hashtable的index位置，并设置e为新的Entry的下一个元素
          tab[index] = new Entry<>(hash, key, value, e);
          count++;
          return null;
      }
  ```

  

#### Hashtable与HashMap的比较

 1. HashMap 是非线程安全的，HashTable 是线程安全的；HashTable 内部的方法基本都经过`synchronized` 修饰。（如果你要保证线程安全的话就使用 ConcurrentHashMap 吧！）； 

	2. 因为线程安全的问题，HashMap 要比 HashTable 效率高一点。另外，HashTable 基本被淘汰，不要在代码中使用它 ;

	3. **对Null key 和Null value的支持：** HashMap 中，null 可以作为键，这样的键只有一个，可以有一个或多个键所对应的值为 null。。但是在 HashTable 中 put 进的键值只要有一个 null，直接抛出 NullPointerException。

	4. **初始容量大小和每次扩充容量大小的不同 ：** ①创建时如果不指定容量初始值，Hashtable 默认的初始大小为11，之后每次扩充，容量变为原来的2n+1。HashMap 默认的初始化大小为16。之后每次扩充，容量变为原来的2倍。②创建时如果给定了容量初始值，那么 Hashtable 会直接使用你给定的大小，而 HashMap 会将其扩充为2的幂次方大小。也就是说 HashMap 总是使用2的幂作为哈希表的大小。

	5. **底层数据结构：** JDK1.8 以后的 HashMap 在解决哈希冲突时有了较大的变化，当链表长度大于阈值（默认为8）时，将链表转化为红黑树，以减少搜索时间。Hashtable 没有这样的机制。

    

#### ConcurrentHashMap和Hashtable的区别

 1. ConcurrentHashMap:JDK1.8 采用的数据结构跟HashMap1.8的结构一样，数组+链表/红黑二叉树。Hashtable 和 JDK1.8 之前的 HashMap 的底层数据结构类似都是采用 **数组+链表** 的形式，数组是 HashMap 的主体，链表则是主要为了解决哈希冲突而存在的； 

	2. 实现线程安全的方式: 

    	1. concurrenthashMap使用分段锁对整个桶数组就行分割分段,每一把锁只锁容器其中一部分数据，多线程访问容器里不同数据段的数据，就不会存在锁竞争，提高并发访问率 ,到了JDK1.8并发控制使用sunchronized和CAS来操作
    	2. Hashtable使用同一把锁,效率非常低下,当一个线程访问同步方法时,其他线程可能进入阻塞或轮询状态,一个线程put,其他线程既不能put也不能get,效率太低下基本舍弃不用;

	3. jdk 1.7的实现

    ![JDK 1.7](https://weiai1314-1258100421.cos.ap-beijing.myqcloud.com/concurrentHashMap1.png?q-sign-algorithm=sha1&q-ak=AKIDAQPLk4SNjmC7x92GLOY3EPoKjuBZSJKH&q-sign-time=1543026259;1543028059&q-key-time=1543026259;1543028059&q-header-list=&q-url-param-list=&q-signature=f1b09f745965c58605dc735174d23c40cdcec654&x-cos-security-token=7f94a8b2389b61e4e9cc71448c5c3ad41949c9e810001)

    * **ConcurrentHashMap 是由 Segment 数组结构和 HahEntry 数组结构组成**。

      Segment 实现了 ReentrantLock,所以 Segment 是一种可重入锁，扮演锁的角色。HashEntry 用于存储键值对数据。

      ```
      static class Segment<K,V> extends ReentrantLock implements Serializable {
      }
      ```

      一个 ConcurrentHashMap 里包含一个 Segment 数组。Segment 的结构和HashMap类似，是一种数组和链表结构，一个 Segment 包含一个 HashEntry 数组，每个 HashEntry 是一个链表结构的元素，每个 Segment 守护着一个HashEntry数组里的元素，当对 HashEntry 数组的数据进行修改时，必须首先获得对应的 Segment的锁。

	4. JDK 1.8的实现(（TreeBin: 红黑二叉树节点  Node: 链表节点)

    ![](https://weiai1314-1258100421.cos.ap-beijing.myqcloud.com/concurrentHashMap2.png?q-sign-algorithm=sha1&q-ak=AKID33XK8L2DPlEEwqXS5083x3at5rOIdom6&q-sign-time=1543026453;1543028253&q-key-time=1543026453;1543028253&q-header-list=&q-url-param-list=&q-signature=79c279def6f72504c65d373c98663a89b47d519a&x-cos-security-token=19153f9cd09e1bdc0c0b1ae62b66322c7cdd31b910001)

    * ConcurrentHashMap取消了Segment分段锁，采用CAS和synchronized来保证并发安全。数据结构跟HashMap1.8的结构类似，数组+链表/红黑二叉树。

      synchronized只锁定当前链表或红黑二叉树的首节点，这样只要hash不冲突，就不会产生并发，效率又提升N倍。

    

### LinkedHashMap

1. 简介

   * 实现有序的HashMap,存储跟取值对应顺序 是HashMap的一个子类

   * LinkedHashMap维护着一个运行于所有条目的双重链接列表,此链接列表定义了迭代顺序,可以是插入或者访问顺序;(非同步性,如果一个线程修改该映射关系,则它必须要保持外部同步)

   * 根据链表中元素的顺序可以分为：按插入顺序的链表，和按访问顺序(调用 get 方法)的链表。默认是按插入顺序排序，如果指定按访问顺序排序，那么调用get方法后，会将这次访问的元素移至链表尾部，不断访问可以形成按访问顺序排序的链表。 

     ```
     //按照插入顺序排序,默认设置
     Map<String, String> map = new LinkedHashMap<String, String>();
     //按照get顺序排序,初始化第三个参数为true即可
     //accessOrder: 如果为true，则按照访问顺序；如果为false，则按照插入顺序。
     Map<String, String> map = new LinkedHashMap<String, String>(16,0.75f,true);
     ```

   

2. 实现原理:(使用hash表及双向链表来保存所有元素)

   1. 成员变量:跟HashMap不同的是重新定义了Entry,HashMap是单链表,保存以一个指定元素,LinkedHashMap还保存了其上一个元素before和下一个元素afer的引用,使用双链表形式

      * 存储结构同HashMap

      ![](https://weiai1314-1258100421.cos.ap-beijing.myqcloud.com/linkedHashMap_tab.png?q-sign-algorithm=sha1&q-ak=AKIDnBNfM1sMd7g509kx6S1LRejRau2X8Uuw&q-sign-time=1543050548;1543052348&q-key-time=1543050548;1543052348&q-header-list=&q-url-param-list=&q-signature=fff410f99eaad39c2667532a59f8a5b9425988f0&x-cos-security-token=8c118693d116e558ffae6f91ffa70893609b8e9f10001)

      * 使用head节点维持了一个双链表结构指向

        ![](https://weiai1314-1258100421.cos.ap-beijing.myqcloud.com/LinkedHashmap2.jpg?q-sign-algorithm=sha1&q-ak=AKIDJ6LX20c5n86WuHbOSstyUMB2PVIXfBt4&q-sign-time=1543050678;1543052478&q-key-time=1543050678;1543052478&q-header-list=&q-url-param-list=&q-signature=9a9e67df7ffca81a1c2ffc5ea470dabb9c0bf37c&x-cos-security-token=88dfd3f9223d4f9d02c975cd4faa24be5fd41e0a10001)

      * 数据结构图

        ![](https://weiai1314-1258100421.cos.ap-beijing.myqcloud.com/linkedHashMap3.png?q-sign-algorithm=sha1&q-ak=AKID96OFw9guYoXiJ54g8K34aKt9Ej96B2WW&q-sign-time=1543050735;1543052535&q-key-time=1543050735;1543052535&q-header-list=&q-url-param-list=&q-signature=5600c21a626a216661da6d23a217fe65cc4714e7&x-cos-security-token=705844daaa03a25e30cd70e8826e2dcc940212c010001)

      * HashMap于linkedHashMap中entry比较

        ![](https://weiai1314-1258100421.cos.ap-beijing.myqcloud.com/linkedHashMap4.png?q-sign-algorithm=sha1&q-ak=AKIDDZtAIUx7rvHvYA8zu1c389wWvDlLWIRL&q-sign-time=1543050940;1543052740&q-key-time=1543050940;1543052740&q-header-list=&q-url-param-list=&q-signature=81cf4d5b92f065dc32aee55f72d03d3b4eb14fe7&x-cos-security-token=e27c8d03c4b1752cd62711aa00c2dba847d2fcfc10001)

   2. 存储put,重写了put方法

      ```
      void recordAccess(HashMap<K,V> m) {
          LinkedHashMap<K,V> lm = (LinkedHashMap<K,V>)m;
          if (lm.accessOrder) {
              lm.modCount++;
              remove();
              addBefore(lm.header);
              }
      }
      
      void addEntry(int hash, K key, V value, int bucketIndex) {
          // 调用create方法，将新元素以双向链表的的形式加入到映射中。
          createEntry(hash, key, value, bucketIndex);
      
          // 删除最近最少使用元素的策略定义
          Entry<K,V> eldest = header.after;
          if (removeEldestEntry(eldest)) {
              removeEntryForKey(eldest.key);
          } else {
              if (size >= threshold)
                  resize(2 * table.length);
          }
      }
      
      void createEntry(int hash, K key, V value, int bucketIndex) {
          HashMap.Entry<K,V> old = table[bucketIndex];
          Entry<K,V> e = new Entry<K,V>(hash, key, value, old);
          table[bucketIndex] = e;
          // 调用元素的addBrefore方法，将元素加入到哈希、双向链接列表。  
          e.addBefore(header);
          size++;
      }
      
      private void addBefore(Entry<K,V> existingEntry) {
          after  = existingEntry;
          before = existingEntry.before;
          before.after = this;
          after.before = this;
      }
      ```

   3. get方法,通过设置的accessOrder确认是按访问还是存储顺序:

      * 重写了父类 HashMap 的 get 方法，实际在调用父类 getEntry() 方法取得查找的元素后，再判断当排序模式 accessOrder 为 true 时，记录访问顺序，将最新访问的元素添加到双向链表的表头，并从原来的位置删除。由于的链表的增加、删除操作是常量级的，故并不会带来性能的损失 

      ```
      public V get(Object key) {
          // 调用父类HashMap的getEntry()方法，取得要查找的元素。
          Entry<K,V> e = (Entry<K,V>)getEntry(key);
          if (e == null)
              return null;
          // 记录访问顺序。
          e.recordAccess(this);
          return e.value;
      }
      
      void recordAccess(HashMap<K,V> m) {
          LinkedHashMap<K,V> lm = (LinkedHashMap<K,V>)m;
          // 如果定义了LinkedHashMap的迭代顺序为访问顺序，
          // 则删除以前位置上的元素，并将最新访问的元素添加到链表表头。  
          if (lm.accessOrder) {
              lm.modCount++;
              remove();
              addBefore(lm.header);
          }
      }
      
      /**
      * Removes this entry from the linked list.
      */
      private void remove() {
          before.after = after;
          after.before = before;
      }
      
      /**clear链表，设置header为初始状态*/
      public void clear() {
       super.clear();
       header.before = header.after = header;
      }
      ```

   4. 排序模式

      * LinkedHashMap 定义了排序模式 accessOrder，该属性为 boolean 型变量，对于访问顺序，为 true；对于插入顺序，则为 false。一般情况下，不必指定排序模式，其迭代顺序即为默认为插入顺序。 
      * 如果你想构造一个 LinkedHashMap，并打算按从近期访问最少到近期访问最多的顺序（即访问顺序）来保存元素 ,设置accessorder为true ; 
      * 该哈希映射的迭代顺序就是最后访问其条目的顺序，这种映射很适合构建 LRU 缓存。 

   5. 区别:其实 LinkedHashMap 几乎和 HashMap 一样：从技术上来说，不同的是它定义了一个 Entry<K,V> header，这个 header 不是放在 Table 里，它是额外独立出来的。LinkedHashMap 通过继承 hashMap 中的 Entry<K,V>,并添加两个属性 Entry<K,V> before,after,和 header 结合起来组成一个双向链表，来实现按插入顺序或访问顺序排序。 

### LinkedHashSet 

 * LinkedHashSet 维护着一个运行于所有条目的双重链接列表。此链接列表定义了迭代顺序，该迭代顺序可为插入顺序或是访问顺序 

* 原理

  1. 对于 LinkedHashSet 而言，它继承与 HashSet、又基于 LinkedHashMap 来实现
  2. LinkedHashSet 底层使用 LinkedHashMap 来保存所有元素，它继承与 HashSet，其所有的方法操作上又与 HashSet 相同，因此 LinkedHashSet 的实现上非常简单，只提供了四个构造方法，并通过传递一个标识参数，调用父类的构造器，底层构造一个 LinkedHashMap 来实现，在相关操作上与父类 HashSet 的操作相同，直接调用父类 HashSet 的方法即可

  ```
  public class LinkedHashSet<E>
      extends HashSet<E>
      implements Set<E>, Cloneable, java.io.Serializable {
  
      private static final long serialVersionUID = -2851667679971038690L;
  
      /**
       * 构造一个带有指定初始容量和加载因子的新空链接哈希set。
       *
       * 底层会调用父类的构造方法，构造一个有指定初始容量和加载因子的LinkedHashMap实例。
       * @param initialCapacity 初始容量。
       * @param loadFactor 加载因子。
       */
      public LinkedHashSet(int initialCapacity, float loadFactor) {
          super(initialCapacity, loadFactor, true);
      }
  
      /**
       * 构造一个带指定初始容量和默认加载因子0.75的新空链接哈希set。
       *
       * 底层会调用父类的构造方法，构造一个带指定初始容量和默认加载因子0.75的LinkedHashMap实例。
       * @param initialCapacity 初始容量。
       */
      public LinkedHashSet(int initialCapacity) {
          super(initialCapacity, .75f, true);
      }
  
      /**
       * 构造一个带默认初始容量16和加载因子0.75的新空链接哈希set。
       *
       * 底层会调用父类的构造方法，构造一个带默认初始容量16和加载因子0.75的LinkedHashMap实例。
       */
      public LinkedHashSet() {
          super(16, .75f, true);
      }
  
      /**
       * 构造一个与指定collection中的元素相同的新链接哈希set。
       *
       * 底层会调用父类的构造方法，构造一个足以包含指定collection
       * 中所有元素的初始容量和加载因子为0.75的LinkedHashMap实例。
       * @param c 其中的元素将存放在此set中的collection。
       */
      public LinkedHashSet(Collection<? extends E> c) {
          super(Math.max(2*c.size(), 11), .75f, true);
          addAll(c);
      }
  }
  
  父类的构造函数中创建linkedHashMap
  /**
       * 以指定的initialCapacity和loadFactor构造一个新的空链接哈希集合。
       * 此构造函数为包访问权限，不对外公开，实际只是是对LinkedHashSet的支持。
       *
       * 实际底层会以指定的参数构造一个空LinkedHashMap实例来实现。
       * @param initialCapacity 初始容量。
       * @param loadFactor 加载因子。
       * @param dummy 标记。
       */
  HashSet(int initialCapacity, float loadFactor, boolean dummy) {
      map = new LinkedHashMap<E,Object>(initialCapacity, loadFactor);
  }
  ```

* 总结

  1. LinkedHashSet 是 Set 的一个具体实现，其维护着一个运行于所有条目的双重链接列表。此链接列表定义了迭代顺序，该迭代顺序可为插入顺序或是访问顺序。
  2. LinkedHashSet 继承与 HashSet，并且其内部是通过 LinkedHashMap 来实现的。有点类似于我们之前说的LinkedHashMap 其内部是基于 Hashmap 实现一样
  3. 如果我们需要迭代的顺序为插入顺序或者访问顺序，那么 LinkedHashSet 是需要你首先考虑的。
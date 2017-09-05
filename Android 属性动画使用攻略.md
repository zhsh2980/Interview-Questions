# Android动画：这里有一份很详细的 属性动画 使用攻略

![img](https://user-gold-cdn.xitu.io/2017/9/5/92177ed7f84ec2f57a234b63ef5c8dc6?imageView2/0/w/1280/h/960)

------

# 前言

- 动画的使用 是 `Android` 开发中常用的知识
- 本文将详细介绍 `Android` 动画中 **属性动画**的原理 & 使用

![动画类型](https://user-gold-cdn.xitu.io/2017/9/5/0071b3e3127665d89409528a08462331?imageView2/0/w/1280/h/960)动画类型

------

# 目录

![目录](https://user-gold-cdn.xitu.io/2017/9/5/3e22723090f4c14f5243b6cf0e1f2cea?imageView2/0/w/1280/h/960)目录

------

# 1. 属性动画出现的原因

- 属性动画（`Property Animation`）是在 `Android 3.0`（`API 11`）后才提供的一种全新动画模式
- 那么为什么要提供属性动画（`Property Animation`）？

### 1.1 背景

实现动画效果在`Android`开发中非常常见，因此`Android`系统一开始就提供了两种实现动画的方式：

- 逐帧动画（`Frame Animation`）
- 补间动画（ `Tweened animation` ）

### 1.2 问题

逐帧动画 & 补间动画存在一定的缺点：

##### a. 作用对象局限：View

即补间动画 只能够作用在视图`View`上，即只可以对一个`Button`、`TextView`、甚至是`LinearLayout`、或者其它继承自`View`的组件进行动画操作，但无法对非`View`的对象进行动画操作

> 1. 有些情况下的动画效果只是视图的某个属性 & 对象而不是整个视图；
> 2. 如，现需要实现视图的颜色动态变化，那么就需要操作视图的颜色属性从而实现动画效果，而不是针对整个视图进行动画操作

##### b. 没有改变View的属性，只是改变视觉效果

- 补间动画只是改变了`View`的视觉效果，而不会真正去改变`View`的属性。
- 如，将屏幕左上角的按钮 通过补间动画 移动到屏幕的右下角
- 点击当前按钮位置（屏幕右下角）是没有效果的，因为实际上按钮还是停留在屏幕左上角，补间动画只是将这个按钮绘制到屏幕右下角，改变了视觉效果而已。

##### c. 动画效果单一

- 补间动画只能实现平移、旋转、缩放 & 透明度这些简单的动画需求

- 一旦遇到相对复杂的动画效果，即超出了上述4种动画效果，那么补间动画则无法实现。

  > 即在功能 & 可扩展性有较大局限性

### 1.3 问题

- 为了解决补间动画的缺陷，在 `Android 3.0`（API 11）开始，系统提供了一种全新的动画模式：属性动画（`Property Animation`）
- 下面，我将全面介绍属性动画（`Property Animation`）

------

# 2. 简介

- 作用对象：任意

   

  ```
  Java
  ```

   

  对象

  > 不再局限于 视图View对象

- 实现的动画效果：可自定义各种动画效果

  > 不再局限于4种基本变换：平移、旋转、缩放 & 透明度

------

# 3. 特点

- 作用对象进行了扩展：不只是View对象，甚至没对象也可以
- 动画效果：不只是4种基本变换，还有其他动画效果
- 作用领域：API11后引入的

------

# 4. 工作原理

- 在一定时间间隔内，通过不断对值进行改变，并不断将该值赋给对象的属性，从而实现该对象在该属性上的动画效果

  > 可以是任意对象的任意属性

- 具体的工作原理逻辑如下：

![工作原理](https://user-gold-cdn.xitu.io/2017/9/5/dca1abc86d53ac908f4c22b452b74cc0?imageView2/0/w/1280/h/960)工作原理

- 从上述工作原理可以看出属性动画有两个非常重要的类：`ValueAnimator` 类 & `ObjectAnimator` 类
- 其实属性动画的使用基本都是依靠这两个类
- 所以，在下面介绍属性动画的具体使用时，我会着重介绍这两个类。

------

# 5. 具体使用

### 5.1 ValueAnimator类

- 定义：属性动画机制中 最核心的一个类
- 实现动画的原理：**通过不断控制 值 的变化，再不断 手动 赋给对象的属性，从而实现动画效果**。如图下：

![工作原理](https://user-gold-cdn.xitu.io/2017/9/5/5f2341bb746cfbe6674b8ea34d812681?imageView2/0/w/1280/h/960)工作原理

从上面原理可以看出：`ValueAnimator`类中有3个重要方法：

1. `ValueAnimator.ofInt（int values）`
2. `ValueAnimator.ofFloat（float values）`
3. `ValueAnimator.ofObject（int values）`

下面我将逐一介绍。

### 5.1.1 ValueAnimator.ofInt（int values）

- 作用：将初始值

   

  以整型数值的形式

   

  过渡到结束值

  > 即估值器是整型估值器 - `IntEvaluator`

- 工作原理：

![工作原理](https://user-gold-cdn.xitu.io/2017/9/5/5045052ed18e2e14d74e58fe14f0759c?imageView2/0/w/1280/h/960)工作原理

- 具体使用：

  > 特别说明：
  >
  > 1. 因为ValueAnimator本质只是一种值的操作机制，所以下面的介绍先是展示如何改变一个值的过程（下面的实例主要讲解：如何将一个值从0平滑地过渡到3）
  > 2. 至于如何实现动画，是需要开发者手动将这些 值 赋给 对象的属性值。关于这部分在下节会进行说明。

操作值的方式 分为 `XML` 设置 / `Java` 代码设置
**设置方式1：Java代码设置**

> 实际开发中，建议使用Java代码实现属性动画：因为很多时候属性的起始值是无法提前确定的（无法使用XML设置），这就需要在`Java`代码里动态获取。

```
// 步骤1：设置动画属性的初始值 & 结束值
ValueAnimator anim = ValueAnimator.ofInt(0, 3);
        // ofInt（）作用有两个
        // 1. 创建动画实例
        // 2. 将传入的多个Int参数进行平滑过渡:此处传入0和1,表示将值从0平滑过渡到1
        // 如果传入了3个Int参数 a,b,c ,则是先从a平滑过渡到b,再从b平滑过渡到C，以此类推
        // ValueAnimator.ofInt()内置了整型估值器,直接采用默认的.不需要设置，即默认设置了如何从初始值 过渡到 结束值
        // 关于自定义插值器我将在下节进行讲解
        // 下面看看ofInt()的源码分析 ->>关注1

// 步骤2：设置动画的播放各种属性
        anim.setDuration(500);
        // 设置动画运行的时长

        anim.setStartDelay(500);
        // 设置动画延迟播放时间

        anim.setRepeatCount(0);
        // 设置动画重复播放次数 = 重放次数+1
        // 动画播放次数 = infinite时,动画无限重复

        anim.setRepeatMode(ValueAnimator.RESTART);
        // 设置重复播放动画模式
        // ValueAnimator.RESTART(默认):正序重放
        // ValueAnimator.REVERSE:倒序回放

// 步骤3：将改变的值手动赋值给对象的属性值：通过动画的更新监听器
        // 设置 值的更新监听器
        // 即：值每次改变、变化一次,该方法就会被调用一次
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int currentValue = (Integer) animation.getAnimatedValue();
                // 获得改变后的值

                System.out.println(currentValue);
                // 输出改变后的值

        // 步骤4：将改变后的值赋给对象的属性值，下面会详细说明
                View.setproperty（currentValue）；

       // 步骤5：刷新视图，即重新绘制，从而实现动画效果
                View.requestLayout();


            }
        });

        anim.start();
        // 启动动画
    }

// 关注1：ofInt（）源码分析
    public static ValueAnimator ofInt(int... values) {
        // 允许传入一个或多个Int参数
        // 1. 输入一个的情况（如a）：从0过渡到a；
        // 2. 输入多个的情况（如a，b，c）：先从a平滑过渡到b，再从b平滑过渡到C

        ValueAnimator anim = new ValueAnimator();
        // 创建动画对象
        anim.setIntValues(values);
        // 将传入的值赋值给动画对象
        return anim;
    }
```

### 效果图

值 从初始值 过度到 结束值 的过程如下：

![效果图](https://user-gold-cdn.xitu.io/2017/9/5/5e972ee52b09fa44e8e9c19ed3772af1?imageView2/0/w/1280/h/960)效果图

**设置方法2：在XML 代码中设置 **
具备重用性，即将通用的动画写到XML里，可在各个界面中去重用它

- 步骤1：在路径

   

  ```
  res/animator
  ```

  的文件夹里创建相应的动画

   

  ```
  .xml
  ```

  文件

  > 此处设置为res/animator/set_animation.xml

- 步骤2：设置动画参数

*set_animation.xml*

```
// ValueAnimator采用<animator>  标签
<animator xmlns:android="http://schemas.android.com/apk/res/android"  
    android:valueFrom="0"   // 初始值
    android:valueTo="100"  // 结束值
    android:valueType="intType" // 变化值类型 ：floatType & intType

    android:duration="3000" // 动画持续时间（ms），必须设置，动画才有效果
    android:startOffset ="1000" // 动画延迟开始时间（ms）
    android:fillBefore = “true” // 动画播放完后，视图是否会停留在动画开始的状态，默认为true
    android:fillAfter = “false” // 动画播放完后，视图是否会停留在动画结束的状态，优先于fillBefore值，默认为false
    android:fillEnabled= “true” // 是否应用fillBefore值，对fillAfter值无影响，默认为true
    android:repeatMode= “restart” // 选择重复播放动画模式，restart代表正序重放，reverse代表倒序回放，默认为restart|
    android:repeatCount = “0” // 重放次数（所以动画的播放次数=重放次数+1），为infinite时无限重复
    android:interpolator = @[package:]anim/interpolator_resource // 插值器，即影响动画的播放速度,下面会详细讲

/>
```

- 步骤3：在Java代码中启动动画

```
Animator animator = AnimatorInflater.loadAnimator(context, R.animator.set_animation);  
// 载入XML动画

animator.setTarget(view);  
// 设置动画对象

animator.start();  
// 启动动画
```

# 效果图

效果同第一种方式是一样的。

### 实例说明

- 下面，我将结合 **手动赋值给对象属性** 这一步骤，从而实现一个完整的动画效果
- 实现的动画效果：按钮的宽度从 `150px` 放大到 `500px`

```
Button mButton = (Button) findViewById(R.id.Button);
        // 创建动画作用对象：此处以Button为例

// 步骤1：设置属性数值的初始值 & 结束值
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mButton.getLayoutParams().width, 500);
        // 初始值 = 当前按钮的宽度，此处在xml文件中设置为150
        // 结束值 = 500
        // ValueAnimator.ofInt()内置了整型估值器,直接采用默认的.不需要设置
        // 即默认设置了如何从初始值150 过渡到 结束值500

// 步骤2：设置动画的播放各种属性
        valueAnimator.setDuration(2000);
        // 设置动画运行时长:1s

// 步骤3：将属性数值手动赋值给对象的属性:此处是将 值 赋给 按钮的宽度
        // 设置更新监听器：即数值每次变化更新都会调用该方法
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                int currentValue = (Integer) animator.getAnimatedValue();
                // 获得每次变化后的属性值
                System.out.println(currentValue);
                // 输出每次变化后的属性值进行查看

                mButton.getLayoutParams().width = currentValue;
                // 每次值变化时，将值手动赋值给对象的属性
                // 即将每次变化后的值 赋 给按钮的宽度，这样就实现了按钮宽度属性的动态变化

// 步骤4：刷新视图，即重新绘制，从而实现动画效果
                mButton.requestLayout();

            }
        });

        valueAnimator.start();
        // 启动动画

    }
```

#### 效果图

![效果图](https://user-gold-cdn.xitu.io/2017/9/5/9c6a57041203c9e6ab7dd96c08c2b8e4?imageView2/0/w/1280/h/960)效果图

### 5.1.2 ValueAnimator.oFloat（float values）

- 作用：将初始值 以浮点型数值的形式 过渡到结束值
- 工作原理：

![工作原理](https://user-gold-cdn.xitu.io/2017/9/5/6f54aa7ae1fe3abdfcc6feaed129bbaf?imageView2/0/w/1280/h/960)工作原理

- 具体使用：分为 XML 设置 / Java 代码设置

**设置方法1：在 Java 代码中设置**

```
ValueAnimator anim = ValueAnimator.ofFloat(0, 3);  
// 其他使用类似ValueAnimator.ofInt（int values），此处不作过多描述
```

**设置方法2：在XML 代码中设置**

- 步骤1：在路径

   

  ```
  res/animator
  ```

  的文件夹里创建相应的动画

  ```
  .xml
  ```

  文件

  > 此处设置为res/animator/set_animation.xml

- 步骤2：设置动画参数

*set_animation.xml*

```
// ValueAnimator采用<animator>  标签
<animator xmlns:android="http://schemas.android.com/apk/res/android"  
    // 设置属性同上
    android:valueFrom="0"  
    android:valueTo="100"  
    android:valueType="intType"/>
```

- 步骤3：在Java代码中启动动画

```
Animator animator = AnimatorInflater.loadAnimator(context, R.animator.set_animation);  
// 载入XML动画

animator.setTarget(view);  
// 设置动画对象

animator.start();  
// 启动动画
```

#### 效果图

![效果图](https://user-gold-cdn.xitu.io/2017/9/5/2806b14e8a50d82aecdc65ebd1b04130?imageView2/0/w/1280/h/960)效果图

从上面可以看出，`ValueAnimator.ofInt（）`与`ValueAnimator.oFloat（）`仅仅只是在估值器上的区别：（即如何从初始值 过渡 到结束值）

- `ValueAnimator.oFloat（）`采用默认的浮点型估值器 (`FloatEvaluator`)
- `ValueAnimator.ofInt（）`采用默认的整型估值器（`IntEvaluator`）

在使用上完全没有区别，此处对`ValueAnimator.oFloat（）`的使用就不作过多描述。

------

### 5.1.3 ValueAnimator.ofObject（）

- 作用：**将初始值 以对象的形式 过渡到结束值**

  > 即通过操作 对象 实现动画效果

- 工作原理：

![工作原理](https://user-gold-cdn.xitu.io/2017/9/5/967ece0f289e4110a396b64b7275fbcc?imageView2/0/w/1280/h/960)工作原理

- 具体使用：

```
// 创建初始动画时的对象  & 结束动画时的对象
myObject object1 = new myObject();  
myObject object2 = new myObject();  

ValueAnimator anim = ValueAnimator.ofObject(new myObjectEvaluator(), object1, object2);  
// 创建动画对象 & 设置参数
// 参数说明
// 参数1：自定义的估值器对象（TypeEvaluator 类型参数） - 下面会详细介绍
// 参数2：初始动画的对象
// 参数3：结束动画的对象
anim.setDuration(5000);  
anim.start();
```

在继续讲解`ValueAnimator.ofObject（）`的使用前，我先讲一下估值器（`TypeEvaluator`）

#### 估值器（TypeEvaluator） 介绍

- 作用：设置动画

   

  如何从初始值 过渡到 结束值 的逻辑

  > 1. 插值器（`Interpolator`）决定 值 的变化模式（匀速、加速blabla）
  > 2. 估值器（`TypeEvaluator`）决定 值 的具体变化数值

从5.1.2节可看到：

- `ValueAnimator.ofFloat（）`实现了 **将初始值 以浮点型的形式 过渡到结束值 **的逻辑，那么这个过渡逻辑具体是怎么样的呢？
- 其实是系统内置了一个 `FloatEvaluator`估值器，内部实现了初始值与结束值 以浮点型的过渡逻辑
- 我们来看一下 `FloatEvaluator`的代码实现：

```
public class FloatEvaluator implements TypeEvaluator {  
// FloatEvaluator实现了TypeEvaluator接口

// 重写evaluate()
    public Object evaluate(float fraction, Object startValue, Object endValue) {  
// 参数说明
// fraction：表示动画完成度（根据它来计算当前动画的值）
// startValue、endValue：动画的初始值和结束值
        float startFloat = ((Number) startValue).floatValue();  

        return startFloat + fraction * (((Number) endValue).floatValue() - startFloat);  
        // 初始值 过渡 到结束值 的算法是：
        // 1. 用结束值减去初始值，算出它们之间的差值
        // 2. 用上述差值乘以fraction系数
        // 3. 再加上初始值，就得到当前动画的值
    }  
}
```

- ```
  ValueAnimator.ofInt（）
  ```

   

  &

   

  ```
  ValueAnimator.ofFloat（）
  ```

  都具备系统内置的估值器，即

  ```
  FloatEvaluator
  ```

   

  &

   

  ```
  IntEvaluator
  ```

  > 即系统已经默认实现了 **如何从初始值 过渡到 结束值 的逻辑**

- 但对于`ValueAnimator.ofObject（）`，从上面的工作原理可以看出并没有系统默认实现，因为对对象的动画操作复杂 & 多样，系统无法知道如何从初始对象过度到结束对象

- 因此，对于`ValueAnimator.ofObject（）`，我们需自定义估值器（`TypeEvaluator`）来告知系统如何进行从 初始对象 过渡到 结束对象的逻辑

- 自定义实现的逻辑如下

```
// 实现TypeEvaluator接口
public class ObjectEvaluator implements TypeEvaluator{  

// 复写evaluate（）
// 在evaluate（）里写入对象动画过渡的逻辑
    @Override  
    public Object evaluate(float fraction, Object startValue, Object endValue) {  
        // 参数说明
        // fraction：表示动画完成度（根据它来计算当前动画的值）
        // startValue、endValue：动画的初始值和结束值

        ... // 写入对象动画过渡的逻辑

        return value;  
        // 返回对象动画过渡的逻辑计算后的值
    }
```

### 实例说明

- 下面我将用实例说明 该如何自定义`TypeEvaluator`接口并通过`ValueAnimator.ofObject（）`实现动画效果

- 实现的动画效果：一个圆从一个点 移动到 另外一个点

  ![效果图](https://user-gold-cdn.xitu.io/2017/9/5/555d28146aac811af07f67d1d5438afb?imageView2/0/w/1280/h/960)效果图

  ​

- 工程目录文件如下：

  ![工程目录](https://user-gold-cdn.xitu.io/2017/9/5/f66e5cfcfa92593520d2318ee5786627?imageView2/0/w/1280/h/960)工程目录

  ​

### 步骤1：定义对象类

- 因为`ValueAnimator.ofObject（）`是面向对象操作的，所以需要自定义对象类。

- 本例需要操作的对象是 **圆的点坐标**
  *Point.java*

  ```
  public class Point {

    // 设置两个变量用于记录坐标的位置
    private float x;
    private float y;

    // 构造方法用于设置坐标
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // get方法用于获取坐标
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
  }
  ```

## 步骤2：根据需求实现TypeEvaluator接口

- 实现`TypeEvaluator`接口的目的是自定义如何 从初始点坐标 过渡 到结束点坐标；
- 本例实现的是一个从左上角到右下角的坐标过渡逻辑。
  ![效果图](https://user-gold-cdn.xitu.io/2017/9/5/555d28146aac811af07f67d1d5438afb?imageView2/0/w/1280/h/960)效果图

*PointEvaluator.java*

```
// 实现TypeEvaluator接口
public class PointEvaluator implements TypeEvaluator {

    // 复写evaluate（）
    // 在evaluate（）里写入对象动画过渡的逻辑
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {

        // 将动画初始值startValue 和 动画结束值endValue 强制类型转换成Point对象
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;

        // 根据fraction来计算当前动画的x和y的值
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());

        // 将计算后的坐标封装到一个新的Point对象中并返回
        Point point = new Point(x, y);
        return point;
    }

}
```

- 上面步骤是根据需求自定义`TypeEvaluator`的实现
- 下面将讲解如何通过对 `Point` 对象进行动画操作，从而实现整个自定义View的动画效果。

### 步骤3：将属性动画作用到自定义View当中

*MyView.java*

```
/**
 * Created by Carson_Ho on 17/4/18.
 */
public class MyView extends View {
    // 设置需要用到的变量
    public static final float RADIUS = 70f;// 圆的半径 = 70
    private Point currentPoint;// 当前点坐标
    private Paint mPaint;// 绘图画笔


    // 构造方法(初始化画笔)
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    // 复写onDraw()从而实现绘制逻辑
    // 绘制逻辑:先在初始点画圆,通过监听当前坐标值(currentPoint)的变化,每次变化都调用onDraw()重新绘制圆,从而实现圆的平移动画效果
    @Override
    protected void onDraw(Canvas canvas) {
        // 如果当前点坐标为空(即第一次)
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            // 创建一个点对象(坐标是(70,70))

            // 在该点画一个圆:圆心 = (70,70),半径 = 70
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x, y, RADIUS, mPaint);


 // (重点关注)将属性动画作用到View中
            // 步骤1:创建初始动画时的对象点  & 结束动画时的对象点
            Point startPoint = new Point(RADIUS, RADIUS);// 初始点为圆心(70,70)
            Point endPoint = new Point(700, 1000);// 结束点为(700,1000)

            // 步骤2:创建动画对象 & 设置初始值 和 结束值
            ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
            // 参数说明
            // 参数1：TypeEvaluator 类型参数 - 使用自定义的PointEvaluator(实现了TypeEvaluator接口)
            // 参数2：初始动画的对象点
            // 参数3：结束动画的对象点

            // 步骤3：设置动画参数
            anim.setDuration(5000);
            // 设置动画时长

// 步骤3：通过 值 的更新监听器，将改变的对象手动赋值给当前对象
// 此处是将 改变后的坐标值对象 赋给 当前的坐标值对象
            // 设置 值的更新监听器
            // 即每当坐标值（Point对象）更新一次,该方法就会被调用一次
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentPoint = (Point) animation.getAnimatedValue();
                    // 将每次变化后的坐标值（估值器PointEvaluator中evaluate（）返回的Piont对象值）到当前坐标值对象（currentPoint）
                    // 从而更新当前坐标值（currentPoint）

// 步骤4：每次赋值后就重新绘制，从而实现动画效果
                    invalidate();
                    // 调用invalidate()后,就会刷新View,即才能看到重新绘制的界面,即onDraw()会被重新调用一次
                    // 所以坐标值每改变一次,就会调用onDraw()一次
                }
            });

            anim.start();
            // 启动动画


        } else {
            // 如果坐标值不为0,则画圆
            // 所以坐标值每改变一次,就会调用onDraw()一次,就会画一次圆,从而实现动画效果

            // 在该点画一个圆:圆心 = (30,30),半径 = 30
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x, y, RADIUS, mPaint);
        }
    }


}
```

### 步骤4：在布局文件加入自定义View空间

*activity_main.xml*

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="scut.carson_ho.valueanimator_ofobject.MainActivity">

    <scut.carson_ho.valueanimator_ofobject.MyView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
         />
</RelativeLayout>
```

### 步骤5：在主代码文件设置显示视图

*MainActivity.java*

```
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
```

## 效果图

![效果图](https://user-gold-cdn.xitu.io/2017/9/5/555d28146aac811af07f67d1d5438afb?imageView2/0/w/1280/h/960)效果图

### 源码地址

Carson_Ho的Github地址：[github.com/Carson-Ho/P…](https://link.juejin.im/?target=https%3A%2F%2Fgithub.com%2FCarson-Ho%2FPropertyAnimator_ofObject)

### 特别注意

从上面可以看出，其实`ValueAnimator.ofObject（）`的本质还是操作 **值 **，只是是采用将 多个值 封装到一个对象里的方式 同时对多个值一起操作而已

> 就像上面的例子，本质还是操作坐标中的x，y两个值，只是将其封装到Point对象里，方便同时操作x，y两个值而已

------

- 至此，关于属性动画中最核心的 `ValueAnimator`类已经讲解完毕
- 下面我将继续讲解另外一个重要的类：`ObjectAnimator`类

------

# 5.2 ObjectAnimator类

### 5.2.1 实现动画的原理

直接对对象的属性值进行改变操作，从而实现动画效果

> 1. 如直接改变 `View`的 `alpha` 属性 从而实现透明度的动画效果
> 2. 继承自`ValueAnimator`类，即底层的动画实现机制是基于`ValueAnimator`类

- 本质原理： 通过不断控制 值 的变化，再不断 **自动** 赋给对象的属性，从而实现动画效果。如下图：

![工作原理](https://user-gold-cdn.xitu.io/2017/9/5/9c6ba4d7ab48bba6697860e4e24e5f02?imageView2/0/w/1280/h/960)工作原理

从上面的工作原理可以看出：`ObjectAnimator`与 `ValueAnimator`类的区别：

- `ValueAnimator` 类是先改变值，然后 **手动赋值** 给对象的属性从而实现动画；是 **间接** 对对象属性进行操作；

- ```
  ObjectAnimator
  ```

   

  类是先改变值，然后

   

  自动赋值

   

  给对象的属性从而实现动画；是

   

  直接

  对对象属性进行操作；

  > 至于是如何自动赋值给对象的属性，下面会详细说明

### 5.2.2 具体使用

由于是继承了ValueAnimator类，所以使用的方法十分类似：`XML` 设置 / `Java`设置

**设置方式1：Java 设置**

```
ObjectAnimator animator = ObjectAnimator.ofFloat(Object object, String property, float ....values);  

// ofFloat()作用有两个
// 1. 创建动画实例
// 2. 参数设置：参数说明如下
// Object object：需要操作的对象
// String property：需要操作的对象的属性
// float ....values：动画初始值 & 结束值（不固定长度）
// 若是两个参数a,b，则动画效果则是从属性的a值到b值
// 若是三个参数a,b,c，则则动画效果则是从属性的a值到b值再到c值
// 以此类推
// 至于如何从初始值 过渡到 结束值，同样是由估值器决定，此处ObjectAnimator.ofFloat（）是有系统内置的浮点型估值器FloatEvaluator，同ValueAnimator讲解

anim.setDuration(500);
        // 设置动画运行的时长

        anim.setStartDelay(500);
        // 设置动画延迟播放时间

        anim.setRepeatCount(0);
        // 设置动画重复播放次数 = 重放次数+1
        // 动画播放次数 = infinite时,动画无限重复

        anim.setRepeatMode(ValueAnimator.RESTART);
        // 设置重复播放动画模式
        // ValueAnimator.RESTART(默认):正序重放
        // ValueAnimator.REVERSE:倒序回放

animator.start();  
// 启动动画
```

**设置方法2：在XML 代码中设置**

- 步骤1：在路径

   

  ```
  res/animator
  ```

   

  的文件夹里创建动画效果

  ```
  .xml
  ```

  文件

  > 此处设置为`res/animator/set_animation.xml`

- 步骤2：设置动画参数

*set_animation.xml*

```
// ObjectAnimator 采用<animator>  标签
<objectAnimator xmlns:android="http://schemas.android.com/apk/res/android"  
    android:valueFrom="1"   // 初始值
    android:valueTo="0"  // 结束值
    android:valueType="floatType"  // 变化值类型 ：floatType & intType
    android:propertyName="alpha" // 对象变化的属性名称

/>
```

在Java代码中启动动画

```
Animator animator = AnimatorInflater.loadAnimator(context, R.animator.view_animation);  
// 载入XML动画

animator.setTarget(view);  
// 设置动画对象

animator.start();  
// 启动动画
```

- 使用实例
  此处先展示四种基本变换：平移、旋转、缩放 & 透明度

### a. 透明度

```
mButton = (Button) findViewById(R.id.Button);
        // 创建动画作用对象：此处以Button为例

        ObjectAnimator animator = ObjectAnimator.ofFloat(mButton, "alpha", 1f, 0f, 1f);
        // 表示的是:
        // 动画作用对象是mButton
        // 动画作用的对象的属性是透明度alpha
        // 动画效果是:常规 - 全透明 - 常规
        animator.setDuration(5000);
        animator.start();
```

![属性动画 - 透明度.gif](https://user-gold-cdn.xitu.io/2017/9/5/7ccb716038f73bbdf0e54c66bd5c5d96?imageView2/0/w/1280/h/960)属性动画 - 透明度.gif

### b. 旋转

```
mButton = (Button) findViewById(R.id.Button);
        // 创建动画作用对象：此处以Button为例

  ObjectAnimator animator = ObjectAnimator.ofFloat(mButton, "rotation", 0f, 360f);

        // 表示的是:
        // 动画作用对象是mButton
        // 动画作用的对象的属性是旋转alpha
        // 动画效果是:0 - 360
        animator.setDuration(5000);
        animator.start();
```

![属性动画- 旋转.gif](https://user-gold-cdn.xitu.io/2017/9/5/0ce693ce34a36d5fa58a7a2028f3c0dc?imageView2/0/w/1280/h/960)属性动画- 旋转.gif

### c. 平移

```
mButton = (Button) findViewById(R.id.Button);
        // 创建动画作用对象：此处以Button为例

  float curTranslationX = mButton.getTranslationX();
        // 获得当前按钮的位置
        ObjectAnimator animator = ObjectAnimator.ofFloat(mButton, "translationX", curTranslationX, 300,curTranslationX);


        // 表示的是:
        // 动画作用对象是mButton
        // 动画作用的对象的属性是X轴平移（在Y轴上平移同理，采用属性"translationY"
        // 动画效果是:从当前位置平移到 x=1500 再平移到初始位置
        animator.setDuration(5000);
        animator.start();
```

![属性动画 - X轴平移.gif](https://user-gold-cdn.xitu.io/2017/9/5/a57080279a07a43d9b79f7cfbcf24d00?imageView2/0/w/1280/h/960)属性动画 - X轴平移.gif

### d. 缩放

```
mButton = (Button) findViewById(R.id.Button);
        // 创建动画作用对象：此处以Button为例

  ObjectAnimator animator = ObjectAnimator.ofFloat(mButton, "scaleX", 1f, 3f, 1f);
        // 表示的是:
        // 动画作用对象是mButton
        // 动画作用的对象的属性是X轴缩放
        // 动画效果是:放大到3倍,再缩小到初始大小
        animator.setDuration(5000);
        animator.start();
```

![属性动画 - 缩放.gif](https://user-gold-cdn.xitu.io/2017/9/5/90b7e5d2310bbc4c564e03d30a8c3632?imageView2/0/w/1280/h/960)属性动画 - 缩放.gif

------

- 在上面的讲解，我们使用了属性动画最基本的四种动画效果：透明度、平移、旋转 & 缩放

  > 即在`ObjectAnimator.ofFloat`（）的第二个参数`String property`传入`alpha`、`rotation`、`translationX` 和 `scaleY` 等blabla

| 属性           | 作用             | 数值类型  |
| ------------ | -------------- | ----- |
| Alpha        | 控制View的透明度     | float |
| TranslationX | 控制X方向的位移       | float |
| TranslationY | 控制Y方向的位移       | float |
| ScaleX       | 控制X方向的缩放倍数     | float |
| ScaleY       | 控制Y方向的缩放倍数     | float |
| Rotation     | 控制以屏幕方向为轴的旋转度数 | float |
| RotationX    | 控制以X轴为轴的旋转度数   | float |
| RotationY    | 控制以Y轴为轴的旋转度数   | float |

**问题：那么ofFloat()的第二个参数还能传入什么属性值呢？**
答案：**任意属性值**。因为：

- `ObjectAnimator` 类 对 对象属性值 进行改变从而实现动画效果的本质是：通过不断控制 值 的变化，再不断 自动 赋给对象的属性，从而实现动画效果

![工作原理](https://user-gold-cdn.xitu.io/2017/9/5/cb9f1129ddb108065f24f407bdd6e670?imageView2/0/w/1280/h/960)工作原理

- 而 **自动赋给对象的属性**的本质是调用该对象属性的set（） & get（）方法进行赋值
- 所以，`ObjectAnimator.ofFloat(Object object, String property, float ....values)`的第二个参数传入值的作用是：让`ObjectAnimator`类根据传入的属性名 去寻找 该对象对应属性名的 `set（） & get（）`方法，从而进行对象属性值的赋值，如上面的例子：

```
ObjectAnimator animator = ObjectAnimator.ofFloat(mButton, "rotation", 0f, 360f);
// 其实Button对象中并没有rotation这个属性值
// ObjectAnimator并不是直接对我们传入的属性名进行操作
// 而是根据传入的属性值"rotation" 去寻找对象对应属性名对应的get和set方法，从而通过set（） &  get（）对属性进行赋值

// 因为Button对象中有rotation属性所对应的get & set方法
// 所以传入的rotation属性是有效的
// 所以才能对rotation这个属性进行操作赋值
public void setRotation(float value);  
public float getRotation();  

// 实际上，这两个方法是由View对象提供的，所以任何继承自View的对象都具备这个属性
```

至于是如何进行自动赋值的，我们直接来看源码分析：

```
// 使用方法
ObjectAnimator animator = ObjectAnimator.ofFloat(Object object, String property, float ....values);  
anim.setDuration(500);
animator.start();  
// 启动动画，源码分析就直接从start()开始

<--  start()  -->
@Override  
public void start() {  
    AnimationHandler handler = sAnimationHandler.get();  

    if (handler != null) {  
        // 判断等待动画（Pending）中是否有和当前动画相同的动画，如果有就把相同的动画给取消掉 
        numAnims = handler.mPendingAnimations.size();  
        for (int i = numAnims - 1; i >= 0; i--) {  
            if (handler.mPendingAnimations.get(i) instanceof ObjectAnimator) {  
                ObjectAnimator anim = (ObjectAnimator) handler.mPendingAnimations.get(i);  
                if (anim.mAutoCancel && hasSameTargetAndProperties(anim)) {  
                    anim.cancel();  
                }  
            }  
        }  
      // 判断延迟动画（Delay）中是否有和当前动画相同的动画，如果有就把相同的动画给取消掉 
        numAnims = handler.mDelayedAnims.size();  
        for (int i = numAnims - 1; i >= 0; i--) {  
            if (handler.mDelayedAnims.get(i) instanceof ObjectAnimator) {  
                ObjectAnimator anim = (ObjectAnimator) handler.mDelayedAnims.get(i);  
                if (anim.mAutoCancel && hasSameTargetAndProperties(anim)) {  
                    anim.cancel();  
                }  
            }  
        }  
    }  

    super.start();  
   // 调用父类的start()
   // 因为ObjectAnimator类继承ValueAnimator类，所以调用的是ValueAnimator的star（）
   // 经过层层调用，最终会调用到 自动赋值给对象属性值的方法
   // 下面就直接看该部分的方法
}  



<-- 自动赋值给对象属性值的逻辑方法 ->>

// 步骤1：初始化动画值
private void setupValue(Object target, Keyframe kf) {  
    if (mProperty != null) {  
        kf.setValue(mProperty.get(target));  
        // 初始化时，如果属性的初始值没有提供，则调用属性的get（）进行取值
    }  
        kf.setValue(mGetter.invoke(target));   
    }  
}  

// 步骤2：更新动画值
// 当动画下一帧来时（即动画更新的时候），setAnimatedValue（）都会被调用
void setAnimatedValue(Object target) {  
    if (mProperty != null) {  
        mProperty.set(target, getAnimatedValue());  
        // 内部调用对象该属性的set（）方法，从而从而将新的属性值设置给对象属性
    }  

}
```

自动赋值的逻辑：

1. 初始化时，如果属性的初始值没有提供，则调用属性的 `get（）`进行取值；
2. 当 值 变化时，用对象该属性的 `set（）`方法，从而从而将新的属性值设置给对象属性。

所以：

- `ObjectAnimator` 类针对的是**任意对象 & 任意属性值**，并不是单单针对于View对象
- 如果需要采用`ObjectAnimator` 类实现动画效果，那么需要操作的对象就必须有该属性的`set（） & get（）`


- 同理，针对上述另外的三种基本动画效果，`View` 也存在着`setRotation()`、`getRotation()`、`setTranslationX()`、`getTranslationX()`、`setScaleY()`、`getScaleY()`等`set（）` & `get（）` 。

------

### 5.2.3 通过自定义对象属性实现动画效果

对于属性动画，其拓展性在于：不局限于系统限定的动画，可以自定义动画，即自定义对象的属性，并通过操作自定义的属性从而实现动画。

那么，该如何自定义属性呢？本质上，就是：

- 为对象设置需要操作属性的set（） & get（）方法

- 通过实现TypeEvaluator类从而定义属性变化的逻辑

  > 类似于ValueAnimator的过程

下面，我将用一个实例来说明如何通过自定义属性实现动画效果

- 实现的动画效果：一个圆的颜色渐变

  ![属性动画 - 颜色变化](https://user-gold-cdn.xitu.io/2017/9/5/edc96a02b91cb1ad853f56a070295a06?imageView2/0/w/1280/h/960)属性动画 - 颜色变化

  ​

- 自定义属性的逻辑如下：（需要自定义属性为圆的背景颜色）

![自定义属性的逻辑](https://user-gold-cdn.xitu.io/2017/9/5/9ffdf71e4cf208853c3c085090d0fd14?imageView2/0/w/1280/h/960)自定义属性的逻辑

# 步骤1：设置对象类属性的set（） & get（）方法

设置对象类属性的`set（）` & `get（）`有两种方法：

1. 通过继承原始类，直接给类加上该属性的 `get（）`& `set（）`，从而实现给对象加上该属性的 `get（）`& `set（）`
2. 通过包装原始动画对象，间接给对象加上该属性的 `get（）`&
   `set（）`。即 **用一个类来包装原始对象**

此处主要使用第一种方式进行展示。

> 关于第二种方式的使用，会在下一节进行详细介绍。

*MyView2.java*

```
public class MyView2 extends View {
    // 设置需要用到的变量
    public static final float RADIUS = 100f;// 圆的半径 = 100
    private Paint mPaint;// 绘图画笔

    private String color;
    // 设置背景颜色属性

    // 设置背景颜色的get() & set()方法
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        // 将画笔的颜色设置成方法参数传入的颜色
        invalidate();
        // 调用了invalidate()方法,即画笔颜色每次改变都会刷新视图，然后调用onDraw()方法重新绘制圆
        // 而因为每次调用onDraw()方法时画笔的颜色都会改变,所以圆的颜色也会改变
    }


    // 构造方法(初始化画笔)
    public MyView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    // 复写onDraw()从而实现绘制逻辑
    // 绘制逻辑:先在初始点画圆,通过监听当前坐标值(currentPoint)的变化,每次变化都调用onDraw()重新绘制圆,从而实现圆的平移动画效果
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(500, 500, RADIUS, mPaint);
    }
}
```

# 步骤2：在布局文件加入自定义View控件

*activity_main.xml*

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="scut.carson_ho.valueanimator_ofobject.MainActivity">

    <scut.carson_ho.valueanimator_ofobject.MyView2
        android:id="@+id/MyView2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
         />
</RelativeLayout>
```

# 步骤3：根据需求实现TypeEvaluator接口

此处实现估值器的本质是：实现 颜色过渡的逻辑。

*ColorEvaluator.java*

```
public class ColorEvaluator implements TypeEvaluator {
    // 实现TypeEvaluator接口

    private int mCurrentRed;

    private int mCurrentGreen ;

    private int mCurrentBlue ;

    // 复写evaluate（）
    // 在evaluate（）里写入对象动画过渡的逻辑:此处是写颜色过渡的逻辑
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {

        // 获取到颜色的初始值和结束值
        String startColor = (String) startValue;
        String endColor = (String) endValue;

        // 通过字符串截取的方式将初始化颜色分为RGB三个部分，并将RGB的值转换成十进制数字
        // 那么每个颜色的取值范围就是0-255
        int startRed = Integer.parseInt(startColor.substring(1, 3), 16);
        int startGreen = Integer.parseInt(startColor.substring(3, 5), 16);
        int startBlue = Integer.parseInt(startColor.substring(5, 7), 16);

        int endRed = Integer.parseInt(endColor.substring(1, 3), 16);
        int endGreen = Integer.parseInt(endColor.substring(3, 5), 16);
        int endBlue = Integer.parseInt(endColor.substring(5, 7), 16);

        // 将初始化颜色的值定义为当前需要操作的颜色值
            mCurrentRed = startRed;
            mCurrentGreen = startGreen;
            mCurrentBlue = startBlue;


        // 计算初始颜色和结束颜色之间的差值
        // 该差值决定着颜色变化的快慢:初始颜色值和结束颜色值很相近，那么颜色变化就会比较缓慢;否则,变化则很快
        // 具体如何根据差值来决定颜色变化快慢的逻辑写在getCurrentColor()里.
        int redDiff = Math.abs(startRed - endRed);
        int greenDiff = Math.abs(startGreen - endGreen);
        int blueDiff = Math.abs(startBlue - endBlue);
        int colorDiff = redDiff + greenDiff + blueDiff;
        if (mCurrentRed != endRed) {
            mCurrentRed = getCurrentColor(startRed, endRed, colorDiff, 0,
                    fraction);
                    // getCurrentColor()决定如何根据差值来决定颜色变化的快慢 ->>关注1
        } else if (mCurrentGreen != endGreen) {
            mCurrentGreen = getCurrentColor(startGreen, endGreen, colorDiff,
                    redDiff, fraction);
        } else if (mCurrentBlue != endBlue) {
            mCurrentBlue = getCurrentColor(startBlue, endBlue, colorDiff,
                    redDiff + greenDiff, fraction);
        }
        // 将计算出的当前颜色的值组装返回
        String currentColor = "#" + getHexString(mCurrentRed)
                + getHexString(mCurrentGreen) + getHexString(mCurrentBlue);

        // 由于我们计算出的颜色是十进制数字，所以需要转换成十六进制字符串:调用getHexString()->>关注2
        // 最终将RGB颜色拼装起来,并作为最终的结果返回
        return currentColor;
    }


    // 关注1:getCurrentColor()
    // 具体是根据fraction值来计算当前的颜色。

    private int getCurrentColor(int startColor, int endColor, int colorDiff,
                                int offset, float fraction) {
        int currentColor;
        if (startColor > endColor) {
            currentColor = (int) (startColor - (fraction * colorDiff - offset));
            if (currentColor < endColor) {
                currentColor = endColor;
            }
        } else {
            currentColor = (int) (startColor + (fraction * colorDiff - offset));
            if (currentColor > endColor) {
                currentColor = endColor;
            }
        }
        return currentColor;
    }

    // 关注2:将10进制颜色值转换成16进制。
    private String getHexString(int value) {
        String hexString = Integer.toHexString(value);
        if (hexString.length() == 1) {
            hexString = "0" + hexString;
        }
        return hexString;
    }

}
```

# 步骤4：调用ObjectAnimator.ofObject（）方法

*MainActivity.java*

```
public class MainActivity extends AppCompatActivity {

    MyView2 myView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myView2 = (MyView2) findViewById(R.id.MyView2);
        ObjectAnimator anim = ObjectAnimator.ofObject(myView2, "color", new ColorEvaluator(),
                "#0000FF", "#FF0000");
        // 设置自定义View对象、背景颜色属性值 & 颜色估值器
        // 本质逻辑：
        // 步骤1：根据颜色估值器不断 改变 值 
        // 步骤2：调用set（）设置背景颜色的属性值（实际上是通过画笔进行颜色设置）
        // 步骤3：调用invalidate()刷新视图，即调用onDraw（）重新绘制，从而实现动画效果

        anim.setDuration(8000);
        anim.start();
    }
}
```

### 效果图

![属性动画 - 颜色变化](https://user-gold-cdn.xitu.io/2017/9/5/edc96a02b91cb1ad853f56a070295a06?imageView2/0/w/1280/h/960)属性动画 - 颜色变化

# 源码地址

[Carson_Ho的Github地址](https://link.juejin.im/?target=https%3A%2F%2Fgithub.com%2FCarson-Ho%2FPropertyAnimator_ofObject)

------

#### 5.2.4 特别注意：如何手动设置对象类属性的 set（） & get（）

### a. 背景

- `ObjectAnimator` 类 自动赋给对象的属性 的本质是调用该对象属性的set（） & get（）方法进行赋值
- 所以，`ObjectAnimator.ofFloat(Object object, String property, float ....values)`的第二个参数传入值的作用是：让`ObjectAnimator`类根据传入的属性名 去寻找 该对象对应属性名的 `set（） & get（）`方法，从而进行对象属性值的赋值

从上面的原理可知，如果想让对象的属性a的动画生效，属性a需要同时满足下面两个条件：

1. 对象必须要提供属性a的set（）方法

   > a. 如果没传递初始值，那么需要提供get（）方法，因为系统要去拿属性a的初始值
   > b. 若该条件不满足，程序直接Crash

2. 对象提供的 属性a的set（）方法 对 属性a的改变 必须通过某种方法反映出来

   > a. 如带来ui上的变化
   > b. 若这条不满足，动画无效，但不会Crash）

上述条件，一般第二条都会满足，主要是在第一条

> 1. 比如说：由于`View`的`setWidth（）`并不是设置`View`的宽度，而是设置`View`的最大宽度和最小宽度的；所以通过`setWidth（）`无法改变控件的宽度；所以对`View`视图的`width`做属性动画没有效果
> 2. 具体请看下面Button按钮的例子

```
       Button  mButton = (Button) findViewById(R.id.Button);
        // 创建动画作用对象：此处以Button为例
        // 此Button的宽高设置具体为具体宽度200px

               ObjectAnimator.ofInt(mButton, "width", 500).setDuration(5000).start();
                 // 设置动画的对象
```

### 效果图

![效果图：不会有动画效果](https://user-gold-cdn.xitu.io/2017/9/5/ef3d913018c1cd7e7e7fe6e438f0a52b?imageView2/0/w/1280/h/960)效果图：不会有动画效果

为什么没有动画效果呢？我们来看`View` 的 `setWidth`方法

```
public void setWidth(int pixels) {  
    mMaxWidth = mMinWidth = pixels;  
    mMaxWidthMode = mMinWidthMode = PIXELS;  
    // 因为setWidth（）并不是设置View的宽度，而是设置Button的最大宽度和最小宽度的
    // 所以通过setWidth（）无法改变控件的宽度
   // 所以对width属性做属性动画没有效果

    requestLayout();  
    invalidate();  
}  


@ViewDebug.ExportedProperty(category = "layout")  
public final int getWidth() {  
    return mRight - mLeft;  
    // getWidth的确是获取View的宽度
}
```

### b. 问题

那么，针对上述对象属性的set（）不是设置属性 或 根本没有`set（） / get （）`的情况应该如何处理？

### c. 解决方案

**手动设置对象类属性的set（） & get（）**。共有两种方法：

1. 通过继承原始类，直接给类加上该属性的 `get（）`& `set（）`，从而实现给对象加上该属性的 `get（）`& `set（）`
2. 通过包装原始动画对象，间接给对象加上该属性的 `get（）`&
   `set（）`。即 **用一个类来包装原始对象**

对于第一种方法，在上面的例子已经说明；下面主要讲解第二种方法：通过包装原始动画对象，间接给对象加上该属性的`get（）& set（）`

> 本质上是采用了设计模式中的装饰模式，即通过包装类从而扩展对象的功能

还是采用上述 `Button` 按钮的例子

```
public class MainActivity extends AppCompatActivity {
    Button mButton;
    ViewWrapper wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.Button);
        // 创建动画作用对象：此处以Button为例

        wrapper = new ViewWrapper(mButton);
        // 创建包装类,并传入动画作用的对象

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectAnimator.ofInt(wrapper, "width", 500).setDuration(3000).start();
                // 设置动画的对象是包装类的对象
            }
        });

    }
    // 提供ViewWrapper类,用于包装View对象
    // 本例:包装Button对象
    private static class ViewWrapper {
        private View mTarget;

        // 构造方法:传入需要包装的对象
        public ViewWrapper(View target) {
            mTarget = target;
        }

        // 为宽度设置get（） & set（）
        public int getWidth() {
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width) {
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }

    }

}
```

## 效果图

![效果图 - 动画有效](https://user-gold-cdn.xitu.io/2017/9/5/d0f81b9e14a0d0cdfd60b33736ab2675?imageView2/0/w/1280/h/960)效果图 - 动画有效

------

# 5.4 总结

- 对比`ValueAnimator`类 & `ObjectAnimator` 类，其实二者都属于属性动画，本质上都是一致的：先改变值，然后 赋值 给对象的属性从而实现动画效果。

- 但二者的区别在于：
  `ValueAnimator` 类是先改变值，然后 **手动赋值** 给对象的属性从而实现动画；是 **间接** 对对象属性进行操作；

  > `ValueAnimator` 类本质上是一种 改变 值 的操作机制

  `ObjectAnimator`类是先改变值，然后 **自动赋值** 给对象的属性从而实现动画；是 **直接** 对对象属性进行操作；

  > 可以理解为：`ObjectAnimator`更加智能、自动化程度更高

------

# 6. 额外的使用方法

### 6.1 组合动画（AnimatorSet 类）

- 单一动画实现的效果相当有限，更多的使用场景是同时使用多种动画效果，即组合动画
- 实现 组合动画 的功能：`AnimatorSet`类
- 具体使用：

```
AnimatorSet.play(Animator anim)   ：播放当前动画
AnimatorSet.after(long delay)   ：将现有动画延迟x毫秒后执行
AnimatorSet.with(Animator anim)   ：将现有动画和传入的动画同时执行
AnimatorSet.after(Animator anim)   ：将现有动画插入到传入的动画之后执行
AnimatorSet.before(Animator anim) ：  将现有动画插入到传入的动画之前执行
```

- 实例

  ​

  主要动画是平移，平移过程中伴随旋转动画，平移完后进行透明度变化

  > 实现方式有 `XML`设置 / `Java`代码设置

**设置方式1：Java代码设置**

```
// 步骤1：设置需要组合的动画效果
ObjectAnimator translation = ObjectAnimator.ofFloat(mButton, "translationX", curTranslationX, 300,curTranslationX);  
// 平移动画
ObjectAnimator rotate = ObjectAnimator.ofFloat(mButton, "rotation", 0f, 360f);  
// 旋转动画
ObjectAnimator alpha = ObjectAnimator.ofFloat(mButton, "alpha", 1f, 0f, 1f);  
// 透明度动画

// 步骤2：创建组合动画的对象
AnimatorSet animSet = new AnimatorSet();  

// 步骤3：根据需求组合动画
animSet.play(translation).with(rotate).before(alpha);  
animSet.setDuration(5000);  

// 步骤4：启动动画
animSet.start();
```

### 效果图

![组合动画.gif](https://user-gold-cdn.xitu.io/2017/9/5/e9a20fb0a0ba66e2aad498f8d4fb387e?imageView2/0/w/1280/h/960)组合动画.gif

**设置方式2：XML设置**

- 步骤1：在

   

  ```
  res/animator
  ```

  的文件夹里创建动画

  ```
  .xml
  ```

  文件

  > 此处为 `res/animator/set_animation.xml`

- 步骤2：设置动画效果

*set_animation.xml*

```
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:ordering="sequentially" >
    // 表示Set集合内的动画按顺序进行
    // ordering的属性值:sequentially & together
    // sequentially:表示set中的动画，按照先后顺序逐步进行（a 完成之后进行 b ）
    // together:表示set中的动画，在同一时间同时进行,为默认值

    <set android:ordering="together" >
        // 下面的动画同时进行
        <objectAnimator
            android:duration="2000"
            android:propertyName="translationX"
            android:valueFrom="0"
            android:valueTo="300"
            android:valueType="floatType" >
        </objectAnimator>

        <objectAnimator
            android:duration="3000"
            android:propertyName="rotation"
            android:valueFrom="0"
            android:valueTo="360"
            android:valueType="floatType" >
        </objectAnimator>
    </set>

        <set android:ordering="sequentially" >
            // 下面的动画按序进行
            <objectAnimator
                android:duration="1500"
                android:propertyName="alpha"
                android:valueFrom="1"
                android:valueTo="0"
                android:valueType="floatType" >
            </objectAnimator>
            <objectAnimator
                android:duration="1500"
                android:propertyName="alpha"
                android:valueFrom="0"
                android:valueTo="1"
                android:valueType="floatType" >
            </objectAnimator>
        </set>

</set>
```

在Java代码中启动动画

```
mButton = (Button) findViewById(R.id.Button);
        // 创建动画作用对象：此处以Button为例

        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.set_animation);
// 创建组合动画对象  &  加载XML动画
        animator.setTarget(mButton);
        // 设置动画作用对象
        animator.start();
        // 启动动画
```

### 效果图

同第一种方式相同。

------

### 6.2 ViewPropertyAnimator用法

- 从上面可以看出，属性动画的本质是对值操作

- 但

  ```
  Java
  ```

  是面向对象的，所以

   

  ```
  Google
  ```

   

  团队添加面向对象操作的属性动画使用 -

   

  ```
  ViewPropertyAnimator
  ```

  类

  > 可认为是属性动画的一种简写方式

- 具体使用

```
// 使用解析
        View.animate().xxx().xxx();
        // ViewPropertyAnimator的功能建立在animate()上
        // 调用animate()方法返回值是一个ViewPropertyAnimator对象,之后的调用的所有方法都是通过该实例完成
        // 调用该实例的各种方法来实现动画效果
        // ViewPropertyAnimator所有接口方法都使用连缀语法来设计，每个方法的返回值都是它自身的实例
        // 因此调用完一个方法后可直接连缀调用另一方法,即可通过一行代码就完成所有动画效果

// 以下是例子
        mButton = (Button) findViewById(R.id.Button);
        // 创建动画作用对象：此处以Button为例

        mButton.animate().alpha(0f);
        // 单个动画设置:将按钮变成透明状态 
        mButton.animate().alpha(0f).setDuration(5000).setInterpolator(new BounceInterpolator());
        // 单个动画效果设置 & 参数设置 
        mButton.animate().alpha(0f).x(500).y(500);
        // 组合动画:将按钮变成透明状态再移动到(500,500)处

        // 特别注意:
        // 动画自动启动,无需调用start()方法.因为新的接口中使用了隐式启动动画的功能，只要我们将动画定义完成后，动画就会自动启动
        // 该机制对于组合动画也同样有效，只要不断地连缀新的方法，那么动画就不会立刻执行，等到所有在ViewPropertyAnimator上设置的方法都执行完毕后，动画就会自动启动
        // 如果不想使用这一默认机制，也可以显式地调用start()方法来启动动画
```

------

### 6.3 监听动画

- `Animation`类通过监听动画开始 / 结束 / 重复 / 取消时刻来进行一系列操作，如跳转页面等等
- 通过在`Java`代码里`addListener（）`设置

```
      Animation.addListener(new AnimatorListener() {
          @Override
          public void onAnimationStart(Animation animation) {
              //动画开始时执行
          }

           @Override
          public void onAnimationRepeat(Animation animation) {
              //动画重复时执行
          }

         @Override
          public void onAnimationCancel()(Animation animation) {
              //动画取消时执行
          }

          @Override
          public void onAnimationEnd(Animation animation) {
              //动画结束时执行
          }
      });

// 特别注意：每次监听必须4个方法都重写。
```

- 因`Animator`类、`AnimatorSet`类、`ValueAnimator`、`ObjectAnimator`类存在以下继承关系

![各类继承关系](https://user-gold-cdn.xitu.io/2017/9/5/3f3536eb0d95fa284f8a1b54e140f353?imageView2/0/w/1280/h/960)各类继承关系

- 所以`AnimatorSet`类、`ValueAnimator`、`ObjectAnimator`都可以使用`addListener()`监听器进行动画监听

### 动画适配器AnimatorListenerAdapter

- 背景：有些时候我们并不需要监听动画的所有时刻
- 问题：但`addListener(new AnimatorListener())`监听器是必须重写4个时刻方法，这使得接口方法重写太累赘
- 解决方案：采用动画适配器（`AnimatorListenerAdapter`），解决
  **实现接口繁琐** 的问题

```
anim.addListener(new AnimatorListenerAdapter() {  
// 向addListener()方法中传入适配器对象AnimatorListenerAdapter()
// 由于AnimatorListenerAdapter中已经实现好每个接口
// 所以这里不实现全部方法也不会报错
    @Override  
    public void onAnimationStart(Animator animation) {  
    // 如想只想监听动画开始时刻，就只需要单独重写该方法就可以
    }  
});
```

至此，`Android` 动画中的`属性动画`的所有知识点都讲解完毕。

------

# 7. 总结

- 属性动画的本质原理：通过不断对值进行改变，并不断将该值赋给对象的属性，从而实现该对象在该属性上的动画效果；具体工作原理逻辑如下：
  ![工作原理](https://user-gold-cdn.xitu.io/2017/9/5/dca1abc86d53ac908f4c22b452b74cc0?imageView2/0/w/1280/h/960)工作原理
- 属性动画的使用主要有以下类，具体如下：

![主要使用类](https://user-gold-cdn.xitu.io/2017/9/5/1b11f0bdcdfca6089bfba78dd328711e?imageView2/0/w/1280/h/960)主要使用类

------

- 接下来，我我将继续对**Android 动画**进行分析，有兴趣的可以继续关注[Carson_Ho的安卓开发笔记](https://link.juejin.im/?target=https%3A%2F%2Fjuejin.im%2Fuser%2F58d4d9781b69e6006ba65edc)

  # 请点赞！因为你们的赞同/鼓励是我写作的最大动力！

- 关于`Android`动画的系列文章

  1. 动画的使用，请参考文章：
     [Android 属性动画：这是一篇很详细的 属性动画 总结&攻略](https://link.juejin.im/?target=http%3A%2F%2Fwww.jianshu.com%2Fp%2F2412d00a0ce4)
     [Android 动画：手把手教你使用 补间动画](https://link.juejin.im/?target=http%3A%2F%2Fwww.jianshu.com%2Fp%2F733532041f46)
     [Android 逐帧动画：关于 逐帧动画 的使用都在这里了！](https://link.juejin.im/?target=http%3A%2F%2Fwww.jianshu.com%2Fp%2F225fe1feba60)
     [Android 动画：你真的会使用插值器与估值器吗？（含详细实例教学）](https://link.juejin.im/?target=http%3A%2F%2Fwww.jianshu.com%2Fp%2F2f19fe1e3ca1)
  2. 自定义View的原理，请参考文章：
     [（1）自定义View基础 - 最易懂的自定义View原理系列](https://link.juejin.im/?target=http%3A%2F%2Fwww.jianshu.com%2Fp%2F146e5cec4863)
     [（2）自定义View Measure过程 - 最易懂的自定义View原理系列](https://link.juejin.im/?target=http%3A%2F%2Fwww.jianshu.com%2Fp%2F1dab927b2f36)
     [（3）自定义View Layout过程 - 最易懂的自定义View原理系列](https://link.juejin.im/?target=http%3A%2F%2Fwww.jianshu.com%2Fp%2F158736a2549d)
     [（4）自定义View Draw过程- 最易懂的自定义View原理系列](https://link.juejin.im/?target=http%3A%2F%2Fwww.jianshu.com%2Fp%2F95afeb7c8335)
  3. 自定义View的应用，请参考文章：
     [手把手教你写一个完整的自定义View](https://link.juejin.im/?target=http%3A%2F%2Fwww.jianshu.com%2Fp%2Fe9d8420b1b9c)
     [Path类的最全面详解 - 自定义View应用系列](https://link.juejin.im/?target=http%3A%2F%2Fwww.jianshu.com%2Fp%2F2c19abde958c)
     [Canvas类的最全面详解 - 自定义View应用系列](https://link.juejin.im/?target=http%3A%2F%2Fwww.jianshu.com%2Fp%2F762b490403c3)
     [为什么你的自定义View wrap_content不起作用？](https://link.juejin.im/?target=http%3A%2F%2Fwww.jianshu.com%2Fp%2Fca118d704b5e)
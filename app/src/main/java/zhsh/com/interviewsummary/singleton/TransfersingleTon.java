package zhsh.com.interviewsummary.singleton;

/**
 * 遇到new, getstatic, putstatic, invokestatic这4条字节码指令时，如果类没有进行过初始化，由需要先触发其初始化。
 * 生成这4条指令的最常见的Java代码场景是：使用new关键字实例化对象，
 * 读取或设置一个类的静态字段（被fina修饰的静态字段除外，其已在编译期把值放入了常量池中），以及调用一个类的静态方法。
 * created by shi on 2018/11/9/009
 */
public class TransfersingleTon {

    //直接懒汉式,调用TransfersingleTon.testStr,或者静态的方法->都会初始化instance对象 ,
    // 注意:被fina修饰的静态字段除外，其已在编译期把值放入了常量池中
   private TransfersingleTon() {
        super();
        System.out.println("我被创建了....");
    }

    public static String testStr  = "我是调用"; //如果加上final修饰,直接加载到常量池中,不会初始化类的

     private static TransfersingleTon instance = new TransfersingleTon();

    public static TransfersingleTon getInstance(){
        return instance ;
    }

    public static String getName(){
        return "nidaye";
    }

    /**
     * 内部类使用只有调用getInstall方法才会创建类的实例,调用testStr常量不会创建对象的
     * 静态内部类只有当被外部类调用到的时候才会初始化。
     这里也是指在运行时，也就是说不在于你在编辑器中有没有写调用的代码，而是你写的这段调用代码运行时是否会被真正执行到。
     在只使用了外部类，但是没有使用内部类的情况下，内部类里面的东西不会被初始化。
     */
//  static class TransferSingle{
//
//      private static TransfersingleTon instance = new TransfersingleTon();
//
//  }
//
//  public static TransfersingleTon getInstall(){
//      return TransferSingle.instance;
//  }

}

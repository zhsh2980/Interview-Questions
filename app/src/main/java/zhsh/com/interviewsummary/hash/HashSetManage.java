package zhsh.com.interviewsummary.hash;

import java.util.HashSet;
import java.util.Set;

/**
 * created by shi on 2018/11/7/007
 */
public class HashSetManage {

    public static void setHashSet(){

        Set set = new HashSet();
        Person person1 = new Person("小强", 27);
        Person person2 = new Person("善哥", 26);
        Person person3 = new Person("凯哥", 28);

        set.add(person1) ;
        set.add(person2) ;
        set.add(person3) ;

        int per = person3.hashCode();

        person3.setAge(25);
        boolean remove = set.remove(person3);
        System.out.println("per:" + per + "size():" + set.size() + "remove:" + remove);
        int after = person3.hashCode();
        set.add(person3);

       Object obj =  person3;

        System.out.println("per:" + per + "after:" +after + "obj:" +  obj.hashCode() + "size():" + set.size());




    }


}

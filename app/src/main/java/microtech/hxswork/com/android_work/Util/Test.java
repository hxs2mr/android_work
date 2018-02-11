package microtech.hxswork.com.android_work.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by microtech on 2017/11/1.
 */

public class Test {
    public static void  main(String[] args){
        List list = new ArrayList();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        System.err.println(list.size());
        list.remove(3);
        System.err.println("---"+list);
        for (int i= 0;i<list.size();i++){
            System.err.println("--"+list.get(i));
        }
    }
}

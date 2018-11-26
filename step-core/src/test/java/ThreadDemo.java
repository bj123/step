import ch.qos.logback.classic.pattern.ThrowableHandlingConverter;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.ArrayList;

/**
 * Created by yangjian9 on 2018/11/22.
 */
public class ThreadDemo extends Thread{

    ArrayList<ThreadDemo> arrayList = new ArrayList<ThreadDemo>();

    public static void main(String args[]){
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        System.out.println("Pid is: " + pid);



        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new ThreadDemo().start();

            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            System.out.println(memoryMXBean.getHeapMemoryUsage());
            System.out.println(memoryMXBean.getNonHeapMemoryUsage());

        }
    }



    public void run(){
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        arrayList.add(new ThreadDemo());
    }

}







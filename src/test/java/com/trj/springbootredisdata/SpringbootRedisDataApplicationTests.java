package com.trj.springbootredisdata;

import com.trj.springbootredisdata.thread.MyTestThread;
import com.trj.springbootredisdata.utils.RedisUtil;
import com.trj.springbootredisdata.utils.TypeConversion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRedisDataApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void set() {
        for (int i = 0; i < 100; i++) {
            redisUtil.setRightList("right_list", i);
        }
    }

    @Test
    public void get() {
        System.out.println(redisUtil.getString("data"));
    }

    @Test
    public void range() {
        redisUtil.setLeftList("a", 2);
        for(int i = 1; i <= 3; i++ ) {
            new Thread(new Runnable() {
                @Override
                public synchronized void run() {
                    redisUtil.setLeftList("a", 1);
                }
            }).start();
        }
    }

    @Test
    public void size() {
        System.out.println(redisUtil.size("left_list"));
    }

    @Test
    public void trim() {
        Long size = redisUtil.size("left_list");
        redisUtil.trim("left_list", 20, size);
    }

    @Test
    public void leftPushAll() {
        //添加数组
        String[] str = {"4","5", "6"};
        System.out.println(redisUtil.leftPushAll("array", str));

        Collection<Object> collection = new ArrayList<>();
        collection.add("1");
        collection.add("2");
        collection.add("3");
        System.out.println(redisUtil.leftPushAll("list", collection));
    }

    @Test
    public void rightPushAll() {
        //添加数组
        String[] str = {"1","2", "3"};
        System.out.println(redisUtil.rightPushAll("array", str));

        Collection<Object> collection = new ArrayList<>();
        collection.add("1");
        collection.add("2");
        collection.add("3");
        System.out.println(redisUtil.rightPushAll("list", collection));
    }

    @Test
    public void leftPop() {
        for (int j = 0; j < 200; j++) {
            for(int i = 0; i < 500; i++) {
                Object object = redisUtil.rightPop("right_list");
                System.out.println(object);
            }
            try {
                Thread.sleep(1000);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void rightPop() {
        System.out.println(redisUtil.rightPop("array"));
    }

    @Test
    public void thread() {
        MyTestThread myTestThread = new MyTestThread(redisUtil);
        myTestThread.start();
    }
}

package com.trj.springbootredisdata.thread;

import com.trj.springbootredisdata.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @BelongsProject: springboot-redis-data
 * @BelongsPackage: com.trj.springbootredisdata.thread
 * @Author: 谭荣杰
 * @CreateTime: 2018-12-05 09:30
 * @Description:
 */
public class MyTestThread extends Thread {

    private RedisUtil redisUtil;

    public MyTestThread(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public void run() {
        for (int j = 0; j < 200; j++) {
            try {
                Object object = redisUtil.rightPop("right_list");
                System.out.println(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

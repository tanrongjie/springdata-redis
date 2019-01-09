package com.trj.springbootredisdata.utils;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @BelongsProject: springboot-redis-data
 * @BelongsPackage: com.trj.springbootredisdata.utils
 * @Author: 谭荣杰
 * @CreateTime: 2018-11-30 10:25
 * @Description:
 */
@Component
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    public RedisUtil(RedisTemplate redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private ValueOperations<String, String> getOpsForValue() {
        return stringRedisTemplate.opsForValue();
    }

    private ListOperations<String, Object> getOpsForList() {
        return redisTemplate.opsForList();
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public boolean setString(String key, String value) {
        if (null == key || "".equals(key)) {
            return false;
        }
        getOpsForValue().set(key, value);
        return true;
    }

    /**
     * 获取key的字符串值
     *
     * @param key
     * @return
     */
    public String getString(Object key) {
        if (null == key || "".equals(key)) {
            return "";
        }
        return getOpsForValue().get(key);
    }


    //===================================================================List操作=============================================================//

    /**
     * 获取list中一行数据,向左获取,从首向尾读取,并删除改行
     * @param key
     * @return
     */
    public Object leftPop(String key) {
        if (null == key || "".equals(key)) {
            return null;
        }
        return getOpsForList().leftPop(key);
    }

    /**
     * 获取list中一行数据,向右获取,从尾向首读取,并删除改行
     * @param key
     * @return
     */
    public Object rightPop(String key) {
        if (null == key || "".equals(key)) {
            return null;
        }
        return getOpsForList().rightPop(key);
    }

    /**
     * 添加list数据,入栈,先进后出
     *
     * @param key
     * @param value
     * @return
     */
    public Long setLeftList(String key, Object value) {
        if (null == key || "".equals(key)) {
            return 0L;
        }
        return getOpsForList().leftPush(key, value);
    }

    /**
     * 添加list数据,队列,先进先出
     *
     * @param key
     * @param value
     * @return
     */
    public Long setRightList(String key, Object value) {
        if (null == key || "".equals(key)) {
            return 0L;
        }
        return getOpsForList().rightPush(key, value);
    }

    /**
     * 返回存储在键中的列表的指定元素。偏移开始和停止是基于零的索引，其中start是列表的第一个元素（列表的头部），end是下一个元素
     * 返回数据将包含star跟end元素,例如返回20条数据: 0,19
     *
     * @param key   键值
     * @param start 开始位置
     * @param end   结束位置
     */
    public List<Object> range(String key, Integer start, Integer end) {
        if (null == key || "".equals(key) || null == start || null == end) {
            return null;
        }
        return getOpsForList().range(key, start, end);
    }

    /**
     * 返回集合长度
     *
     * @param k
     * @return
     */
    public Long size(String key) {
        if (null == key || "".equals(key)) {
            return 0L;
        }
        return getOpsForList().size(key);
    }

    /**
     * 删除集合指定区间数据之外的数据,例如:有50条数据,删除前20条,start跟end的值则为20,50
     *
     * @param key
     * @param start
     * @param end
     */
    public void trim(String key, Integer start, Long end) {
        if (null == key || "".equals(key)) {
            return;
        }
        getOpsForList().trim(key, start, end);
    }

    /**
     * 添加数组,数组添加顺序向左加入,栈形式先进后出
     *
     * @param key
     * @param values
     * @return
     */
    public Long leftPushAll(String key, Object[] values) {
        if (null == key || "".equals(key)) {
            return 0L;
        }
        return getOpsForList().leftPushAll(key, values);
    }

    /**
     * 添加一个集合,集合顺序向左加入,栈形式先进后出
     *
     * @param key
     * @param values
     * @return
     */
    public Long leftPushAll(String key, Collection<Object> values) {
        if (null == key || "".equals(key)) {
            return 0L;
        }
        return getOpsForList().leftPushAll(key, values);
    }

    /**
     * 添加数组,数组添加顺序向右加入,栈形式先进后出
     *
     * @param key
     * @param values
     * @return
     */
    public Long rightPushAll(String key, Object[] values) {
        if (null == key || "".equals(key)) {
            return 0L;
        }
        return getOpsForList().rightPushAll(key, values);
    }

    /**
     * 添加一个集合,集合顺序向右加入,栈形式先进后出
     *
     * @param key
     * @param values
     * @return
     */
    public Long rightPushAll(String key, Collection<Object> values) {
        if (null == key || "".equals(key)) {
            return 0L;
        }
        return getOpsForList().rightPushAll(key, values);
    }


}

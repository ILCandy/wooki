//package com.wooki.system.base;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.data.redis.connection.RedisConnection;
//import org.springframework.data.redis.core.RedisCallback;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author zxk175
// */
//@Component
//public class RedisTemplateUtils {
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    /**
//     * 清空Redis数据
//     *
//     * @return
//     */
//    public Object flushRedis() {
//        return redisTemplate.execute(new RedisCallback() {
//            @Override
//            public String doInRedis(RedisConnection connection) throws DataAccessException {
//                connection.flushAll();
//                return "OK";
//            }
//        });
//    }
//
//    /**
//     * 检查是否连接成功
//     *
//     * @return
//     */
//    public Boolean ping() {
//        String execute = (String) redisTemplate.execute(new RedisCallback() {
//            @Override
//            public String doInRedis(RedisConnection connection) throws DataAccessException {
//                return connection.ping();
//            }
//        });
//        return "pong".equalsIgnoreCase(execute);
//    }
//
//    /**
//     * 查看redis里有多少数据
//     *
//     * @return
//     */
////    public String dbSize() {
////        Long execute = (Long) redisTemplate.execute(new RedisCallback() {
////            @Override
////            public Object doInRedis(RedisConnection connection) throws DataAccessException {
////                return connection.dbSize();
////            }
////        });
////        return FileUtils.transFormFileSize(execute);
////    }
//
//    /**
//     * 写入缓存
//     *
//     * @param key
//     * @param value
//     * @param expire 过期时间 单位：秒
//     */
//    public void set(final String key, final String value, final Long expire) {
//        stringRedisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
//    }
//    public void set(final String key, final String value) {
//        stringRedisTemplate.opsForValue().set(key, value);
//    }
//
//    /**
//     * 写入缓存
//     *
//     * @param key
//     * @param value
//     * @param expire 过期时间 单位：秒
//     */
//    public void setObject(final String key, final Object value, final Long expire) {
//        //System.out.println("set OBject :"+value);
//        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
//    }
//    public void setObject(final String key, final Object value) {
//        //System.out.println("set Object :"+value);
//        redisTemplate.opsForValue().set(key, value);
//    }
//
//    /**
//     * 读取缓存
//     *
//     * @param key
//     * @return
//     */
//    public String get(final String key) {
//        return stringRedisTemplate.boundValueOps(key).get();
//    }
//
//
//    /**
//     * 读取缓存
//     *
//     * @param key
//     * @return
//     */
//    public Object getObject(final String key){
//        //System.out.println("get Object :"+redisTemplate.boundValueOps(key).get());
////        return (T)JSON.parseObject(redisTemplate.boundValueOps(key).get(),clazz);
//        return redisTemplate.boundValueOps(key).get();
//    }
//
//    public <T> T getObject(final String key,Class<T> clazz){
//        return (T)redisTemplate.boundValueOps(key).get();
//    }
//
//    /**
//     * 删除，根据key精确匹配
//     *
//     * @param key
//     */
//    public void del(final String... key) {
//        stringRedisTemplate.delete(Arrays.asList(key));
//    }
//
//    /**
//     * 批量删除，根据key模糊匹配
//     *
//     * @param pattern
//     */
//    public void delByPattern(final String... pattern) {
//        for (String kp : pattern) {
//            stringRedisTemplate.delete(stringRedisTemplate.keys(kp + "*"));
//        }
//    }
//
//    /**
//     * 递增操作
//     *
//     * @param key
//     * @param val
//     * @return
//     */
//    public Long increment(final String key, final Long val) throws Exception {
//        return stringRedisTemplate.boundValueOps(key).increment(val);
//    }
//
//    /**
//     * 递减操作
//     *
//     * @param key
//     * @param val
//     * @return
//     */
//    public Long decrement(final String key, final Long val) throws Exception {
//        return stringRedisTemplate.boundValueOps(key).increment(-val);
//    }
//
//    /**
//     * 设置key过期时间
//     *
//     * @param key
//     * @param time
//     * @param timeUnit
//     * @return
//     */
//    public Boolean expire(final String key, final Long time, final TimeUnit timeUnit) {
//        return stringRedisTemplate.expire(key, time, timeUnit);
//    }
//
//    /**
//     * 根据key获取过期时间并换算成指定单位
//     *
//     * @param key
//     * @return
//     */
//    public String getExpireBySeconds(final String key, final TimeUnit timeUnit) {
//        return String.valueOf(stringRedisTemplate.getExpire(key, timeUnit));
//    }
//
//    /**
//     * key是否存在
//     *
//     * @param key
//     * @return
//     */
//    public boolean exists(final String key) {
//        return stringRedisTemplate.hasKey(key);
//    }
//
//    /**
//     * 存list ，默认从左开始
//     * @param key
//     * @param list
//     */
//    public void setList(String key,List list){
//        redisTemplate.opsForList().leftPush(key,list);
//    }
//
//    /**
//     * 获取list
//     * @param key
//     * @return
//     */
//    public List getList(String key){
//        return (List)redisTemplate.opsForList().leftPop(key);
//    }
//    public Double getDoubleValue(String key){
//        return Double.valueOf(stringRedisTemplate.boundValueOps(key).get());
//    }
//    public Integer getIntegerValue(String key){
//        return Integer.valueOf(stringRedisTemplate.boundValueOps(key).get());
//    }
//}

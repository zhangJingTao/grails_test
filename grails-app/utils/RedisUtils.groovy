//import org.codehaus.groovy.grails.commons.ConfigurationHolder;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * Created by zhang on 2016/2/24.
// */
//class RedisUtils {
//    def grailsApplication
//    private static JedisPool pool = null;
//
//    /**
//     * 构建redis连接池
//     *
//     * @param ip
//     * @param port
//     * @return JedisPool
//     */
//    public static JedisPool getPool() {
//        if (pool == null) {
//            JedisPoolConfig config = new JedisPoolConfig();
//            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
//            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
//            config.setMaxIdle(Integer.valueOf(grailsApplication.config.jedis.pool.maxidle));
//
//            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
//            config.setMaxWaitMillis(1000 * 100);
//            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
//            config.setTestOnBorrow(true);
//            pool = new JedisPool(config,grailsApplication.config.jedis.ip.toString(),
//                    Integer.valueOf(config,grailsApplication.config.jedis.port.toString()),
//                    Integer.valueOf(config,grailsApplication.config.jedis.pool.timeout.toString()));
//        }
//        return pool;
//    }
//
//    /**
//     * 返还到连接池
//     *
//     * @param pool
//     * @param redis
//     */
//    public static void returnResource(JedisPool pool, Jedis redis) {
//        if (redis != null) {
//            pool.returnResource(redis);
//        }
//    }
//
//    /**
//     * 获取数据
//     *
//     * @param key
//     * @return
//     */
//    public static String get(String key){
//        String value = null;
//
//        JedisPool pool = null;
//        Jedis jedis = null;
//        try {
//            pool = getPool();
//            jedis = pool.getResource();
//            value = jedis.get(key);
//        } catch (Exception e) {
//            //释放redis对象
//            pool.returnBrokenResource(jedis);
//            e.printStackTrace();
//        } finally {
//            //返还到连接池
//            returnResource(pool, jedis);
//        }
//
//        return value;
//    }
//
//    public static Boolean set(String key,String value,Long timeOut){
//
//    }
//}

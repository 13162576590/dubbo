import org.junit.Test;

import wusc.edu.pay.common.utils.cache.redis.RedisUtils;

public class TestClass {

    public static void main(String[] args) {
        RedisUtils ru = new RedisUtils();

//        System.out.println(RedisUtils.get("\\xac\\xed\\x00\\x05t\\x00\\x1cBANK_CHANNEL_ICBC_NET_B2B_GY"));
        System.out.println(ru.get("test2"));

    }


    @Test
    public void test() {
        RedisUtils ru = new RedisUtils();
        System.out.println(RedisUtils.getJedisSentinelPool());
//      System.out.println(RedisUtils.get("\\xac\\xed\\x00\\x05t\\x00\\x1cBANK_CHANNEL_ICBC_NET_B2B_GY"));
        RedisUtils.save("test2", "test2");
        System.out.println(ru.get("test2"));
    }


}

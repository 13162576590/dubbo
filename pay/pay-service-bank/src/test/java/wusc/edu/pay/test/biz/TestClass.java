package wusc.edu.pay.test.biz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wusc.edu.pay.common.utils.cache.redis.RedisUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-context.xml"})
public class TestClass {

//    @Autowired
//    private RedisUtils ru;

//    public static void main(String[] args) {
//        RedisUtils ru = new RedisUtils();
//
////        System.out.println(RedisUtils.get("\\xac\\xed\\x00\\x05t\\x00\\x1cBANK_CHANNEL_ICBC_NET_B2B_GY"));
//        System.out.println(ru.get("test1"));
//
//    }

    @SuppressWarnings("static-access")
    @Test
    public void test() {
        RedisUtils ru = new RedisUtils();

//      System.out.println(RedisUtils.get("\\xac\\xed\\x00\\x05t\\x00\\x1cBANK_CHANNEL_ICBC_NET_B2B_GY"));
//        ru.save("test2", "test2");
        System.out.println("==========" + ru.get("test2"));
    }


}

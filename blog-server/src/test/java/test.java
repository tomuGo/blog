import com.zhongkouwei.blog.server.BlogApplication;
import com.zhongkouwei.blog.server.util.RedisLock;
import com.zhongkouwei.blog.server.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BlogApplication.class)
public class test {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedisLock redisLock;

    @Autowired
    Jedis jedis;


    @Test
    public void test1(){
        int q=redisUtil.generateFloorId("qwe");
        System.out.println(q);
        int w=redisUtil.generateFloorId("qwe");
        System.out.println(w);
    }

    @Test
    public void test2(){
        //jedis.del("123");
        String uuid=redisLock.lock("qwe");
        System.out.println(uuid);
        /*Boolean b=redisLock.unlock("qwe",uuid);
        System.out.println(b);*/
    }

    @Test
    public void test3(){
        jedis.set("123","123");
    }
}

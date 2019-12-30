package per.xck.rabbitmq;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class RabbitmqApplicationTests {


    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
    //message需要自己构造，定义消息体内容和消息头
//    rabbitTemplate.send(exchange,routeKey,message);

//        object默认当成消息体，只需要传入要发送的对象，自动序列化发送给rabbitmq；
//    rabbitTemplate.convertAndSend(exchange,routeKey,object);
        Map<String,Object> map = new HashMap<>();
        map.put("msg","helloWorld");
        map.put("data", Arrays.asList("hei",123,true));
        //对象默认序列化以后发送出去
        rabbitTemplate.convertAndSend("exchange.direct","kazemi.news",map);
    }

    // 接受数据，如何将数据自动转为json发送出去
    @Test
    public void receive(){
        Object o = rabbitTemplate.receiveAndConvert("kazemi.news");
        System.out.println(o.getClass());
        System.out.println(o);
    }

    @Test
    public void sendMsg(){
        Map<String,Object> map = new HashMap<>();
        map.put("msg","excalibur!");
        map.put("data", Arrays.asList("merlin",123,true));
        rabbitTemplate.convertAndSend("exchange.fanout",map);
    }

}

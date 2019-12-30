# Sprint 整合 Rabbitmq 

自动配置:
    
    1、RabbitAutoConfiguration
    
    2、自动配置了连接工厂ConnectionFactory
    
    3、RabbitProperties 封装了 RabbitMQ的配置
    
    4、RabbitTemplate：给RabbitMQ发送和接受消息
    
    5、AmqpAdmin：RabbitMQ系统管理组件

    6、@EnableRabbit + @RabbitListener 监听消息队列的内容
```properties
# application.properties

spring.rabbitmq.host=49.233.173.72
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
#spring.rabbitmq.port=5672
#spring.rabbitmq.virtual-host= /

```

```java

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

}
```
由于默认是使用jdk的converter序列化 我们更希望使用json方式
```java

@Configuration
public class MyAMQPConfig {

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
```
广播
```
@Test
public void sendMsg(){
    Map<String,Object> map = new HashMap<>();
    map.put("msg","excalibur!");
    map.put("data", Arrays.asList("merlin",123,true));
    rabbitTemplate.convertAndSend("exchange.fanout",map);
}
```

实际开发中经常需要监听队列

需要使用@EnableRabbit与@RabbitListener


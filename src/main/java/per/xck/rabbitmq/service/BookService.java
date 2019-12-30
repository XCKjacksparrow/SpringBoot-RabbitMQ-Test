package per.xck.rabbitmq.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import per.xck.rabbitmq.bean.Book;

@Service
public class BookService {

    @RabbitListener(queues = "")
    public void receive(Book book){
        System.out.println("收到消息: " + book);
    }
}

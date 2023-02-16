package personal.vahid.schedulerengine.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import personal.vahid.schedulerengine.model.config.RabbitConfig;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RabbitConfiguration {

    public static final String SCHEDULER_API_EXCHANGE = "scheduler-api";
    public static final String SYM_SCHEDULER_API_QUEUE = "sym-scheduler-api-queue";
    public static final String RW_SCHEDULER_API_QUEUE = "rw-scheduler-api-queue";

    @Primary
    @Bean
    public ConnectionFactory connectionFactory(AppConfiguration appConfiguration){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        try{
            RabbitConfig config = appConfiguration.getRabbit();
            connectionFactory.setAddresses(config.getAddresses());
            connectionFactory.setUsername(config.getUsername());
            connectionFactory.setPassword(config.getPassword());
            connectionFactory.setVirtualHost(config.getVirtualHost());
        }catch (Exception e){
            log.error("Error in loading rabbit configuration." + e.getMessage());
        }
        return connectionFactory;
    }

    @Primary
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory){
        return new RabbitTemplate(factory);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(SCHEDULER_API_EXCHANGE, true, false);
    }

    @Bean("symbolic-queue")
    public Queue symQueue(){
        return new Queue(SYM_SCHEDULER_API_QUEUE, true);
    }

    @Bean("real-world-queue")
    public Queue rwQueue(){
        return new Queue(RW_SCHEDULER_API_QUEUE, true);
    }

    @Bean("symbolic-binding")
    public Binding symBinding(DirectExchange exchange, @Qualifier("symbolic-queue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("sym");
    }


    @Bean("real-world-binding")
    public Binding rwBinding(DirectExchange exchange, @Qualifier("real-world-queue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("rw");
    }
}

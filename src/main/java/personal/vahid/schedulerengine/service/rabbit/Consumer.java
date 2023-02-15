package personal.vahid.schedulerengine.service.rabbit;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import personal.vahid.schedulerengine.configuration.RabbitConfiguration;
import personal.vahid.schedulerengine.model.dto.RabbitRealWorldConsumerDTO;
import personal.vahid.schedulerengine.model.dto.RabbitSymbolicConsumerDTO;
import personal.vahid.schedulerengine.service.SchedulerService;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class Consumer {

    SchedulerService schedulerService;
    FanoutExchange fanoutExchange;
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitConfiguration.SCHEDULER_API_QUEUE, concurrency = "1")
    public void symbolicConsumer(RabbitSymbolicConsumerDTO consumerDTO){
        if(consumerDTO == null){
            log.error("Invalid message on queue. NULL. queue: {}", RabbitConfiguration.SCHEDULER_API_QUEUE);
        }
        long optimalProfit = schedulerService.schedule(consumerDTO.jobs());
        rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", optimalProfit);
    }

    @RabbitListener(queues = RabbitConfiguration.SCHEDULER_API_QUEUE, concurrency = "1")
    public void realWorldConsumer(RabbitRealWorldConsumerDTO consumerDTO){
        if(consumerDTO == null){
            log.error("Invalid message on queue. NULL. queue: {}", RabbitConfiguration.SCHEDULER_API_QUEUE);
        }
        long optimalProfit = schedulerService.schedule(consumerDTO.jobs());
        rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", optimalProfit);
    }
}

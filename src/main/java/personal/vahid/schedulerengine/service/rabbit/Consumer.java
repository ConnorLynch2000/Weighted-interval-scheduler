package personal.vahid.schedulerengine.service.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
    ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitConfiguration.SYM_SCHEDULER_API_QUEUE, concurrency = "1")
    public Long symbolicConsumer(byte[] consumerDTO){
        if(consumerDTO == null){
            log.error("Invalid message on queue. NULL. queue: {}", RabbitConfiguration.SYM_SCHEDULER_API_QUEUE);
        }
        RabbitSymbolicConsumerDTO schedulerDto;
        try {
            schedulerDto = objectMapper.readValue(consumerDTO, RabbitSymbolicConsumerDTO.class);
        }catch (Exception e){
            log.error("Cant map the dto.");
            return null;
        }
        Long optimalProfit = schedulerService.schedule(schedulerDto.jobs());
        return optimalProfit;
    }


    @RabbitListener(queues = RabbitConfiguration.RW_SCHEDULER_API_QUEUE, concurrency = "1")
    public Long realWorldConsumer(byte[] consumerDTO){
        if(consumerDTO == null){
            log.error("Invalid message on queue. NULL. queue: {}", RabbitConfiguration.SYM_SCHEDULER_API_QUEUE);
        }
        RabbitRealWorldConsumerDTO schedulerDto;
        try {
            schedulerDto = objectMapper.readValue(consumerDTO, RabbitRealWorldConsumerDTO.class);
        }catch (Exception e){
            log.error("Cant map the dto.");
            return null;
        }
        Long optimalProfit = schedulerService.schedule(schedulerDto.jobs());
        return optimalProfit;
    }
}

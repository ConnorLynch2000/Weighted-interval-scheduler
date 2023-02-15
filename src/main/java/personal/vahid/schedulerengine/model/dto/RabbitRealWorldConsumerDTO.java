package personal.vahid.schedulerengine.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import personal.vahid.schedulerengine.model.RealWorldJob;

import java.util.List;

@Builder
public record RabbitRealWorldConsumerDTO(@JsonProperty("jobs")List<RealWorldJob> jobs) {
}

package personal.vahid.schedulerengine.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import personal.vahid.schedulerengine.model.SymbolicJob;

import java.util.List;

@Builder
public record RabbitSymbolicConsumerDTO(@JsonProperty("jobs")List<SymbolicJob> jobs) {
}

package personal.vahid.schedulerengine.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
public record RealWorldJob(LocalDateTime start,
                           LocalDateTime end,
                           long profit) {}

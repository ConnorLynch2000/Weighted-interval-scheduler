package personal.vahid.schedulerengine.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@Builder
public record SymbolicJob(int start, int end, int profit) {}

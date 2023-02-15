package personal.vahid.schedulerengine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
public class RealWorldJob implements Job<LocalDateTime>{
    LocalDateTime start;
    LocalDateTime end;
    long profit;

    @Override
    public LocalDateTime getEnd() {
        return this.end;
    }

    @Override
    public LocalDateTime getStart() {
        return this.start;
    }

    @Override
    public long getProfit(){
        return this.profit;
    }

}

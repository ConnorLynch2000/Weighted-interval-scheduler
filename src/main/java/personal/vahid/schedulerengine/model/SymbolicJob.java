package personal.vahid.schedulerengine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class SymbolicJob implements Job<Integer>{
    int start;
    int end;
    long profit;

    @Override
    public Integer getEnd() {
        return this.end;
    }

    @Override
    public Integer getStart() {
        return this.start;
    }

    @Override
    public long getProfit(){
        return this.profit;
    }
}

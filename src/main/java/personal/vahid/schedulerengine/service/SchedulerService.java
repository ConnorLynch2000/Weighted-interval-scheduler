package personal.vahid.schedulerengine.service;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import personal.vahid.schedulerengine.model.SymbolicJob;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class SchedulerService {

    @PostConstruct
    public void init(){
        List<SymbolicJob> jobs = new ArrayList<>(List.of(
                new SymbolicJob(1, 2, 50),
                new SymbolicJob(3, 5, 20),
                new SymbolicJob(6, 19, 100),
                new SymbolicJob(2, 100, 200)
        ));

        log.info("Optimal profit is: {}", symbolicSchedule(jobs));
    }

    private int SymbolicBinarySearch(List<SymbolicJob> jobs, int index){
        int lo = 0, hi = index - 1;

        while(lo <= hi){
            int mid = (lo + hi) / 2;
            if (jobs.get(mid).end() <= jobs.get(index).end())
            {
                if (jobs.get(mid + 1).end() <= jobs.get(index).end())
                    lo = mid + 1;
                else
                    return mid;
            }
            else
                hi = mid - 1;
        }
        return -1;
    }

    public int symbolicSchedule(List<SymbolicJob> jobs){
        Comparator<SymbolicJob> jobComparator = new Comparator<SymbolicJob>() {
            @Override
            public int compare(SymbolicJob o1, SymbolicJob o2) {
                return o1.end() < o2.end() ? -1 : o1.end() == o2.end() ? 0 : -1;
            }
        };

        jobs.sort(jobComparator);

        int n = jobs.size();
        int[] table = new int[n];
        table[0] = jobs.get(0).profit();

        for(int i = 1; i<n ; i++){

            int inclProfit = jobs.get(i).profit();
            int l = SymbolicBinarySearch(jobs, i);
            if(l != -1){
                inclProfit += table[i];
            }

            table[i] = Math.max(inclProfit, table[i - 1]);
        }
        return table[n - 1];
    }
}

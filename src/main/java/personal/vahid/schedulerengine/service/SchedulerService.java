package personal.vahid.schedulerengine.service;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import personal.vahid.schedulerengine.model.Job;
import personal.vahid.schedulerengine.model.RealWorldJob;
import personal.vahid.schedulerengine.model.SymbolicJob;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
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

        log.info("Optimal profit is: {}", schedule(jobs));
        LocalDateTime now = LocalDateTime.now();
        List<RealWorldJob> jobs1 = new ArrayList<>(List.of(
                new RealWorldJob(now.plusDays(1), now.plusDays(2), 50),
                new RealWorldJob(now.plusDays(3), now.plusDays(5), 20),
                new RealWorldJob(now.plusDays(6), now.plusDays(19), 100),
                new RealWorldJob(now.plusDays(2), now.plusDays(100), 200)
        ));
        log.info("Optimal profit is: {}", schedule(jobs1));
    }

    private int binarySearch(List<? extends Job> jobs, int index){
        int lo = 0, hi = index - 1;

        while(lo <= hi){
            int mid = (lo + hi) / 2;
            if(jobs.get(mid).getEnd().compareTo(jobs.get(index).getStart()) <= 0){
                if (jobs.get(mid + 1).getEnd().compareTo(jobs.get(index).getStart()) <= 0){
                    lo = mid + 1;
                }else {
                    return mid;
                }
            }else {
                hi = mid - 1;
            }
        }
        return -1;
    }
    public long schedule(List<? extends Job> jobs){
        Comparator<Job> jobComparator = Comparator.comparing(Job::getEnd);

        jobs.sort(jobComparator);

        int n = jobs.size();
        long[] table = new long[n];
        table[0] = jobs.get(0).getProfit();

        for(int i = 1; i<n ; i++){
            long inclProfit = jobs.get(i).getProfit();
            int l = binarySearch(jobs, i);
            if(l != -1){
                inclProfit += table[l];
            }
            table[i] = Math.max(inclProfit, table[i - 1]);
        }
        return table[n - 1];
    }

}

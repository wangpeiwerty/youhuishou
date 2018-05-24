package com.eleven.mvp.base.domain;


import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;


public class JobSchedule{

    private static JobSchedule jobSchedule;
    private Scheduler scheduler;
    private Scheduler schedulerHook;

    public static JobSchedule getInstance() {
        if (jobSchedule == null){
            jobSchedule = new JobSchedule();
        }
        return jobSchedule;
    }
    private JobSchedule() {
        scheduler = Schedulers.from(new JobExecutor());
    }
    public Scheduler getScheduler() {
        return schedulerHook == null?scheduler:schedulerHook;
    }
    public void registerSchedulerHook(Scheduler scheduler) {
        schedulerHook = scheduler;
    }
    public void resetSchedulerHook(){
        schedulerHook = null;
    }
}

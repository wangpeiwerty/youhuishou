package com.eleven.mvp.base.domain;
import com.blankj.utilcode.util.StringUtils;
import com.lzy.okgo.OkGo;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;



public abstract class UseCase<Q extends UseCase.RequestValues> {

    private Scheduler executorThread;
    private Scheduler uiThread;
    private CompositeDisposable disposables = new CompositeDisposable();

    public UseCase() {
        this(JobSchedule.getInstance().getScheduler(), AndroidSchedulers.mainThread());
    }

    public UseCase(Scheduler executorThread, Scheduler uiThread) {
        this.executorThread = executorThread;
        this.uiThread = uiThread;
    }

    public void execute(Consumer onNext, Consumer<? super Throwable> onError , Q rv) {
        disposables.add(this.buildUseCaseObservable(rv)
                .subscribeOn(executorThread)
                .observeOn(uiThread)
                .subscribe(onNext,onError));
    }

    public void unSubscribe(){
        if(disposables!=null){
            disposables.clear();
        }
    }

    public void unSubscribe(String tag) {
        if(StringUtils.isEmpty(tag)){
            OkGo.getInstance().cancelAll();
        }else{
            OkGo.getInstance().cancelTag(tag);
        }
       unSubscribe();
    }



    protected abstract Observable buildUseCaseObservable(Q rv);

    public interface RequestValues {
        boolean checkInput();
        int getErrorStringRes();
    }

    public interface RequestPageValue{
        void setPage(int page);
        void setLimit(int limit);
        int getPage();
        int getLimit();
    }

}

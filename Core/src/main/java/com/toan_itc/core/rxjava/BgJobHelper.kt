package com.toan_itc.core.rxjava

import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


object BgJobHelper {

    interface JobLifecycleEvents {
        fun setUiBeforeBgJobStarts()
        fun doJobInBackground(jobSignals: JobSignals)
        fun setUiOnJobComplete()
        fun setUiOnJobError()
        fun setCommonUiAfterJobEnds()
    }

    interface JobLifecycleSingleEvent {
        fun doJobInBackground()
    }

    interface JobSignals {
        fun onJobComplete()
        fun onJobError()
    }

    fun runBackgroundJob(compositeDisposable: CompositeDisposable, jobLifecycleEvents: JobLifecycleEvents) {

        jobLifecycleEvents.setUiBeforeBgJobStarts()

        compositeDisposable.add(
                Observable.create<Any> { emitter ->
                    jobLifecycleEvents.doJobInBackground(object : JobSignals {
                        override fun onJobComplete() {
                            emitter.onComplete()
                        }

                        override fun onJobError() {
                            emitter.onError(Throwable())
                        }
                    })
                }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<Any>() {
                            override fun onComplete() {
                                jobLifecycleEvents.setCommonUiAfterJobEnds()
                                jobLifecycleEvents.setUiOnJobComplete()
                            }

                            override fun onNext(o: Any) {}

                            override fun onError(e: Throwable) {
                                jobLifecycleEvents.setCommonUiAfterJobEnds()
                                jobLifecycleEvents.setUiOnJobError()
                            }

                        }) as Disposable
        )
    }

    fun runBackgroundJob(compositeDisposable: CompositeDisposable, jobLifecycleSingleEvent: JobLifecycleSingleEvent) {
        compositeDisposable.add(
                Observable.create(ObservableOnSubscribe<Any> { jobLifecycleSingleEvent.doJobInBackground() })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<Any>() {
                            override fun onComplete() {}

                            override fun onNext(o: Any) {}

                            override fun onError(e: Throwable) {}
                        }) as Disposable
        )

    }
}

/* USE
//RUN UI
BgJobHelper.runBackgroundJob(compositeDisposable, new BgJobHelper.JobLifecycleEvents() {

    @Override
    public void setUiBeforeBgJobStarts() {
        // UI tasks to execute before the background job starts
    }

    @Override
    public void doJobInBackground(BgJobHelper.JobSignals jobSignals) {
        // do your job here. It will be executed in a background thread
        signals.onComplete(); // use this to notify that your task has completed successfully
        signals.onError(); // use this to notify that an error has occured
    }

    @Override
    public void setUiOnJobComplete() {
        // UI tasks to execute after the task has completed successfully
    }

    @Override
    public void setUiOnJobError() {
        // UI tasks to execute after an error has occured
    }

    @Override
    public void setCommonUiAfterJobEnds() {
        // this will execute before onComplete() / onError()
        // you can put here common UI tasks which are needed to be performed, after the job is done or after an error has occured
        // (like hiding the progress bar)
    }
});

//RUN BACKGROUND
BgJobHelper.runBackgroundJob(compositeDisposable, new BgJobHelper.JobLifecycleSingleEvent() {
    @Override
    public void doJobInBackground() {
        // do your job here. It will be executed in a background thread
    }
});
 */
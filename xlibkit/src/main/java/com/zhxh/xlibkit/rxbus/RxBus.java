package com.zhxh.xlibkit.rxbus;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * 参考EventBus用RxJava实现RxBus
 * version 1.6加上了删除消息的函数
 * version 1.7加上了发送延迟消息的函数
 * version 1.8推荐延迟消息只用sticky模式
 */
public final class RxBus {

    private final FlowableProcessor<Object> mBus;

    private static final String EMPTY_TAG = "";

    private final Consumer<Throwable> mOnError = new Consumer<Throwable>() {
        @Override
        public void accept(Throwable throwable) {
            Utils.logE(throwable.toString());
        }
    };

    private RxBus() {
        mBus = PublishProcessor.create().toSerialized();
    }

    public static RxBus getDefault() {
        return Holder.BUS;
    }

    public void post(final Object event) {
        post(EMPTY_TAG, event, false);
    }

    public void post(final String tag, final Object event) {
        post(tag, event, false);
    }

    public void postSticky(final Object event) {
        post(EMPTY_TAG, event, true);
    }

    public void postSticky(final String tag, final Object event) {
        post(tag, event, true);
    }

    private void post(final String tag, final Object event,
                      final boolean isSticky) {
        Utils.requireNonNull(event, tag);

        TagMsg msgEvent = new TagMsg(tag, event);
        if (isSticky) {
            CacheUtils.getInstance().addStickyEvent(msgEvent);
        }
        mBus.onNext(msgEvent);
    }

    private void postDelay(final String tag, final Object event,
                           final boolean isSticky, long millisecond) {
        Utils.requireNonNull(event, tag);
        Disposable[] disposable = new Disposable[1];

        Observable.just(millisecond).delay(millisecond, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable[0] = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        TagMsg msgEvent = new TagMsg(tag, event);
                        if (isSticky) {
                            CacheUtils.getInstance().addStickyEvent(msgEvent);
                        }
                        mBus.onNext(msgEvent);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        if (disposable.length >= 1) {
                            disposable[0].dispose();
                        }
                    }
                });

    }

    //以后版本将不再保留非sticky消息的延迟，延迟的消息发送建议用postStickyDelay
    @Deprecated
    public void postDelay(final String tag, final Object event,
                          long millisecond) {
        postDelay(tag, event, false, millisecond);
    }

    public void postStickyDelay(final String tag, final Object event,
                                long millisecond) {
        postDelay(tag, event, true, millisecond);
    }


    //version 1.6加上了删除消息的函数
    public <T> boolean removeStickyEvent(final String tag, final Class<T> eventType) {
        Utils.requireNonNull(eventType, tag);
        return CacheUtils.getInstance().removeStickyEvent(tag, eventType);
    }

    //version 1.6加上了删除消息的函数
    public <T> boolean removeStickyEvent(final Class<T> eventType) {
        return removeStickyEvent(EMPTY_TAG, eventType);
    }

    //version 1.6加上了删除消息的函数
    public void removeStickyEvent() {
        CacheUtils.getInstance().removeAllStickyEvents();
    }

    public <T> void subscribe(final Object subscriber,
                              final Callback<T> callback) {
        subscribe(subscriber, EMPTY_TAG, false, null, callback);
    }

    public <T> void subscribe(final Object subscriber,
                              final String tag,
                              final Callback<T> callback) {
        subscribe(subscriber, tag, false, null, callback);
    }

    public <T> void subscribe(final Object subscriber,
                              final Scheduler scheduler,
                              final Callback<T> callback) {
        subscribe(subscriber, EMPTY_TAG, false, scheduler, callback);
    }

    public <T> void subscribe(final Object subscriber,
                              final String tag,
                              final Scheduler scheduler,
                              final Callback<T> callback) {
        subscribe(subscriber, tag, false, scheduler, callback);
    }

    public <T> void subscribeSticky(final Object subscriber,
                                    final Callback<T> callback) {
        subscribe(subscriber, EMPTY_TAG, true, null, callback);
    }

    public <T> void subscribeSticky(final Object subscriber,
                                    final String tag,
                                    final Callback<T> callback) {
        subscribe(subscriber, tag, true, null, callback);
    }

    public <T> void subscribeSticky(final Object subscriber,
                                    final Scheduler scheduler,
                                    final Callback<T> callback) {
        subscribe(subscriber, EMPTY_TAG, true, scheduler, callback);
    }

    public <T> void subscribeSticky(final Object subscriber,
                                    final String tag,
                                    final Scheduler scheduler,
                                    final Callback<T> callback) {
        subscribe(subscriber, tag, true, scheduler, callback);
    }

    private <T> void subscribe(final Object subscriber,
                               final String tag,
                               final boolean isSticky,
                               final Scheduler scheduler,
                               final Callback<T> callback) {
        Utils.requireNonNull(subscriber, tag, callback);

        final Class<T> typeClass = Utils.getTypeClassFromCallback(callback);

        final Consumer<T> onNext = new Consumer<T>() {
            @Override
            public void accept(T t) {
                callback.onEvent(t);
            }
        };

        if (isSticky) {
            final TagMsg stickyEvent = CacheUtils.getInstance().findStickyEvent(typeClass, tag);
            if (stickyEvent != null) {
                Flowable<T> stickyFlowable = Flowable.create(new FlowableOnSubscribe<T>() {
                    @Override
                    public void subscribe(FlowableEmitter<T> emitter) {
                        emitter.onNext(typeClass.cast(stickyEvent.mEvent));
                    }
                }, BackpressureStrategy.LATEST);
                if (scheduler != null) {
                    stickyFlowable = stickyFlowable.observeOn(scheduler);
                }
                Disposable stickyDisposable = FlowableUtils.subscribe(stickyFlowable, onNext, mOnError);
                CacheUtils.getInstance().addDisposable(subscriber, stickyDisposable);
            } else {
                Utils.logW("sticky event is empty.");
            }
        }
        Disposable disposable = FlowableUtils.subscribe(
                toFlowable(typeClass, tag, scheduler), onNext, mOnError
        );
        CacheUtils.getInstance().addDisposable(subscriber, disposable);
    }

    private <T> Flowable<T> toFlowable(final Class<T> eventType,
                                       final String tag,
                                       final Scheduler scheduler) {
        Flowable<T> flowable = mBus.ofType(TagMsg.class)
                .filter(new Predicate<TagMsg>() {
                    @Override
                    public boolean test(TagMsg tagMessage) {
                        return tagMessage.isSameType(tag, eventType);
                    }
                })
                .map(new Function<TagMsg, Object>() {
                    @Override
                    public Object apply(TagMsg tagMessage) {
                        return tagMessage.mEvent;
                    }
                })
                .cast(eventType);
        if (scheduler != null) {
            return flowable.observeOn(scheduler);
        }
        return flowable;
    }

    public void unregister(final Object subscriber) {
        CacheUtils.getInstance().removeDisposables(subscriber);
    }

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }

    public interface Callback<T> {
        void onEvent(T t);
    }
}
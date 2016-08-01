package com.demos.rx;

import java.util.Arrays;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * Created by Mr_Wrong on 16/3/30.
 */
public class RxTest {
    static int index = 0;

    public static void main(String[] args) {


        Observable observable = Observable.from(Arrays.asList(1, 2, 3));
        Observable observable1 = Observable.from(Arrays.asList(4, "5", 6));


        Observable.from(Arrays.asList(1, 2, 3))
                .flatMap(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer integer) {
                        return index == 0 ? Observable.<Integer>error(new NullPointerException("这个是0的异常")) : Observable.just(integer);
                    }
                }).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        if (throwable instanceof IllegalArgumentException || throwable instanceof NullPointerException) {
                            return Observable.just(null).doOnNext(new Action1<Object>() {
                                @Override
                                public void call(Object o) {
                                    index = 10;
                                }
                            });
                        }
                        return Observable.just(throwable);
                    }
                });
            }
        })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
//                        print(integer);
//                        print(index);
                    }
                });


        //-----PublishSubject-----会发射subscribe之后的所有
        PublishSubject<Object> subject = PublishSubject.create();
        subject.subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                print("1--" + o);
            }
        });
        subject.onNext("one");
        subject.onNext("two");
        subject.subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                print("2--" + o);
            }
        });
        subject.onNext("three");
        subject.onCompleted();

        //----BehaviorSubject-----observer会接收到上面的一个  也就是最近的  还有下面的所有的
        //也就是BehaviorSubject会发射最近的一个 还有subscribe之后的所有
        // observer will receive all events.
//        BehaviorSubject<Object> subject = BehaviorSubject.create("default");
//        subject.subscribe(observer);
//        subject.onNext("one");
//        subject.onNext("two");
//        subject.onNext("three");
//
//        // observer will receive the "one", "two" and "three" events, but not "zero"
//        BehaviorSubject<Object> subject = BehaviorSubject.create("default");
//        subject.onNext("zero");
//        subject.onNext("one");
//        subject.subscribe(observer);
//        subject.onNext("two");
//        subject.onNext("three");
//
//        // observer will receive only onCompleted
//        BehaviorSubject<Object> subject = BehaviorSubject.create("default");
//        subject.onNext("zero");
//        subject.onNext("one");
//        subject.onCompleted();
//        subject.subscribe(observer);
//
//        // observer will receive only onError
//        BehaviorSubject<Object> subject = BehaviorSubject.create("default");
//        subject.onNext("zero");
//        subject.onNext("one");
//        subject.onError(new RuntimeException("error"));
//        subject.subscribe(observer);


    }

    private static void print(Object o) {
        System.out.println(o.toString());
    }
}

package com.hangox.zuinews.data;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/16
 * Time 下午9:14
 */

public class ObservableFactory {
    public static <T> Observable<T>  create(List<T> items){
        return Observable.create(e -> {
            for (T item : items) {
                e.onNext(item);
            }
            e.onComplete();
        });
    }
}

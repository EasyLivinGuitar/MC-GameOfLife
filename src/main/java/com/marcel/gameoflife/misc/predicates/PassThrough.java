package com.marcel.gameoflife.misc.predicates;

import com.google.common.base.Predicate;

import javax.annotation.Nullable;

/**
 * Created by kipu5728 on 6/29/16.
 */
public class PassThrough<T> implements Predicate<T> {
    @Override
    public boolean apply(@Nullable T input) {
        return true;
    }
}

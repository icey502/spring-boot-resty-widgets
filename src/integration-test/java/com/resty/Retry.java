package com.resty;

import lombok.AllArgsConstructor;

import java.util.concurrent.Callable;

/**
 * Created by damianhagge on 5/12/17.
 */
@AllArgsConstructor
public class Retry<T> {

    int numAttempts;
    long sleepMillis;

    public T call(Callable<T> function) throws Error, Exception {
        for (int i = 0; i < numAttempts; i++) {
            try {
                return function.call();
            } catch (Exception | Error e) {
                if (i == numAttempts - 1) {
                    throw e;
                }
                Thread.sleep(sleepMillis);
            }
        }

        return null;
    }
}

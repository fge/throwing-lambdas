package com.github.fge.lambdas.runnable;

import com.github.fge.lambdas.ThrownByLambdaException;

@FunctionalInterface
public interface ThrowingRunnable
    extends Runnable
{
    void doRun()
        throws Throwable;

    @Override
    default void run()
    {
        try {
            doRun();
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new ThrownByLambdaException(throwable);
        }
    }
}

package com.github.fge.lambdas.runnable;

import com.github.fge.lambdas.Chainer;

public class RunnableChainer
    extends Chainer<Runnable, ThrowingRunnable, RunnableChainer>
    implements ThrowingRunnable
{
    public RunnableChainer(final ThrowingRunnable throwing)
    {
        super(throwing);
    }

    @Override
    public void doRun()
        throws Throwable
    {
        throwing.doRun();
    }

    @Override
    public RunnableChainer orTryWith(final ThrowingRunnable other)
    {
        final ThrowingRunnable runnable = () -> {
            try {
                throwing.doRun();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                other.doRun();
            }
        };

        return new RunnableChainer(runnable);
    }

    @Override
    public <E extends RuntimeException> ThrowingRunnable orThrow(
        final Class<E> exclass)
    {
        return () -> {
            try {
                throwing.doRun();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw rethrow(exclass, throwable);
            }
        };
    }

    @Override
    public Runnable fallbackTo(final Runnable fallback)
    {
        return () -> {
            try {
                throwing.doRun();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                fallback.run();
            }
        };
    }

    @Override
    public Runnable sneakyThrow()
    {
        return () -> {
            try {
                throwing.doRun();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw doSneakyThrow(throwable);
            }
        };
    }

    public Runnable orDoNothing()
    {
        return () -> {
            try {
                throwing.doRun();
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable ignored) {
                // nothing
            }
        };
    }
}

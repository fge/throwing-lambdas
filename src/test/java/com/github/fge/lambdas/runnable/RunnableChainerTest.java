package com.github.fge.lambdas.runnable;

import com.github.fge.lambdas.ChainerTest;
import com.github.fge.lambdas.Throwing;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public final class RunnableChainerTest
    extends ChainerTest<Runnable, ThrowingRunnable, RunnableChainer, Type1>
{
    private final AtomicReference<Type1> sentinel = new AtomicReference<>();

    private final Type1 novalue = Type1.mock();

    public RunnableChainerTest()
    {
        super(Type1.mock(), Type1.mock());
    }

    @BeforeMethod
    public void initSentinel()
    {
        sentinel.set(novalue);
    }

    @Override
    protected Runnable getFallback()
    {
        return mock(Runnable.class);
    }

    @Override
    protected ThrowingRunnable getThrowing()
    {
        return mock(ThrowingRunnable.class);
    }

    @Override
    protected RunnableChainer getChain(final ThrowingRunnable throwing)
    {
        return Throwing.runnable(throwing);
    }

    @Override
    protected Callable<Type1> toCallable(final Runnable chain)
    {
        return () -> {
            chain.run();
            return sentinel.get();
        };
    }

    @Override
    protected void configureFull(final ThrowingRunnable throwing)
        throws Throwable
    {
        doAnswer(invocation -> { sentinel.set(ret1); return null; })
            .doThrow(checked)
            .doThrow(unchecked)
            .doThrow(error)
            .when(throwing).doRun();
    }

    @Override
    protected void configureAlternate(final ThrowingRunnable throwing)
        throws Throwable
    {
        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(throwing).doRun();
    }

    @Override
    protected void configureFallback(final Runnable fallback)
    {
        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(fallback).run();
    }

    @Test
    public void orDoNothingTest()
        throws Throwable
    {
        final ThrowingRunnable throwing = getThrowing();

        configureFull(throwing);

        final Runnable chain = getChain(throwing).orDoNothing();

        final Callable<Type1> callable = toCallable(chain);

        assertThat(callable.call()).isSameAs(ret1);

        assertThat(callable.call()).isSameAs(ret1);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

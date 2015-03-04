package com.github.fge.lambdas.consumer;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public final class IntConsumerChainTest
    extends ChainTest<IntConsumer, ThrowingIntConsumer, IntConsumerChain, Type1>
{
    private final int value = 42;

    private final Type1 noValue = Type1.mock();

    private final AtomicReference<Type1> sentinel = new AtomicReference<>();

    public IntConsumerChainTest()
    {
        super(Type1.mock(), Type1.mock());
    }

    @BeforeMethod
    public void initSentinel()
    {
        sentinel.set(noValue);
    }

    @Override
    protected IntConsumer getFallback()
    {
        return mock(IntConsumer.class);
    }

    @Override
    protected ThrowingIntConsumer getThrowing()
    {
        return mock(ThrowingIntConsumer.class);
    }

    @Override
    protected IntConsumerChain getChain(
        final ThrowingIntConsumer throwing)
    {
        return new IntConsumerChain(throwing);
    }

    @Override
    protected Callable<Type1> toCallable(final IntConsumer chain)
    {
        return () -> { chain.accept(value); return sentinel.get(); };
    }

    @Override
    protected void configureFull(final ThrowingIntConsumer throwing)
        throws Throwable
    {
        doAnswer(ignored -> { sentinel.set(ret1); return null; })
            .doThrow(checked)
            .doThrow(unchecked)
            .doThrow(error)
            .when(throwing).doAccept(value);
    }

    @Override
    protected void configureAlternate(final ThrowingIntConsumer throwing)
        throws Throwable
    {
        doAnswer(ignored -> { sentinel.set(ret2); return null; })
            .when(throwing).doAccept(value);
    }

    @Override
    protected void configureFallback(final IntConsumer fallback)
    {
        doAnswer(ignored -> { sentinel.set(ret2); return null; })
            .when(fallback).accept(value);
    }

    @Test
    public void orDoNothingTest()
        throws Throwable
    {
        final ThrowingIntConsumer throwing = getThrowing();
        configureFull(throwing);

        final IntConsumer chain = getChain(throwing).orDoNothing();

        final Callable<Type1> callable = toCallable(chain);

        assertThat(callable.call()).isSameAs(ret1);

        assertThat(callable.call()).isSameAs(ret1);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

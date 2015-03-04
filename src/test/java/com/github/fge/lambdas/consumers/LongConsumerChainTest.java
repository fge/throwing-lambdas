package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.LongConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public final class LongConsumerChainTest
    extends ChainTest<LongConsumer, ThrowingLongConsumer, LongConsumerChain, Type1>
{
    private final long value = 42L;

    private final Type1 noValue = Type1.mock();

    private final AtomicReference<Type1> sentinel = new AtomicReference<>();

    public LongConsumerChainTest()
    {
        super(Type1.mock(), Type1.mock());
    }

    @BeforeMethod
    public void initSentinel()
    {
        sentinel.set(noValue);
    }

    @Override
    protected LongConsumer getFallback()
    {
        return mock(LongConsumer.class);
    }

    @Override
    protected ThrowingLongConsumer getThrowing()
    {
        return mock(ThrowingLongConsumer.class);
    }

    @Override
    protected LongConsumerChain getChain(
        final ThrowingLongConsumer throwing)
    {
        return Throwing.longConsumer(throwing);
    }

    @Override
    protected Callable<Type1> toCallable(final LongConsumer chain)
    {
        return () -> { chain.accept(value); return sentinel.get(); };
    }

    @Override
    protected void configureFull(final ThrowingLongConsumer throwing)
        throws Throwable
    {
        doAnswer(ignored -> { sentinel.set(ret1); return null; })
            .doThrow(checked)
            .doThrow(unchecked)
            .doThrow(error)
            .when(throwing).doAccept(value);
    }

    @Override
    protected void configureAlternate(final ThrowingLongConsumer throwing)
        throws Throwable
    {
        doAnswer(ignored -> { sentinel.set(ret2); return null; })
            .when(throwing).doAccept(value);
    }

    @Override
    protected void configureFallback(final LongConsumer fallback)
    {
        doAnswer(ignored -> { sentinel.set(ret2); return null; })
            .when(fallback).accept(value);
    }

    @Test
    public void orDoNothingTest()
        throws Throwable
    {
        final ThrowingLongConsumer throwing = getThrowing();
        configureFull(throwing);

        final LongConsumer chain = getChain(throwing).orDoNothing();

        final Callable<Type1> callable = toCallable(chain);

        assertThat(callable.call()).isSameAs(ret1);

        assertThat(callable.call()).isSameAs(ret1);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

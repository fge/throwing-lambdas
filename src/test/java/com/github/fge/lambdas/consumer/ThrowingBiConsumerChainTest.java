package com.github.fge.lambdas.consumer;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import com.github.fge.lambdas.helpers.Type3;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public final class ThrowingBiConsumerChainTest
    extends ChainTest<BiConsumer<Type1, Type2>, ThrowingBiConsumer<Type1, Type2>, ThrowingBiConsumerChain<Type1, Type2>, Type3>
{
    private final Type1 t = Type1.mock();
    private final Type2 u = Type2.mock();

    private final Type3 noValue = Type3.mock();

    private final AtomicReference<Type3> sentinel = new AtomicReference<>();

    public ThrowingBiConsumerChainTest()
    {
        super(Type3.mock(), Type3.mock());
    }

    @BeforeMethod
    public void initSentinel()
    {
        sentinel.set(noValue);
    }

    @Override
    protected BiConsumer<Type1, Type2> getFallback()
    {
        return mock(BiConsumer.class);
    }

    @Override
    protected ThrowingBiConsumer<Type1, Type2> getThrowing()
    {
        return mock(ThrowingBiConsumer.class);
    }

    @Override
    protected ThrowingBiConsumerChain<Type1, Type2> getChain(
        final ThrowingBiConsumer<Type1, Type2> throwing)
    {
        return new ThrowingBiConsumerChain<>(throwing);
    }

    @Override
    protected Callable<Type3> toCallable(final BiConsumer<Type1, Type2> chain)
    {
        return () -> { chain.accept(t, u); return sentinel.get(); };
    }

    @Override
    protected void configureFull(
        final ThrowingBiConsumer<Type1, Type2> throwing)
        throws Throwable
    {
        doAnswer(invocation -> { sentinel.set(ret1); return null; })
            .doThrow(checked)
            .doThrow(unchecked)
            .doThrow(error)
            .when(throwing).doAccept(t, u);
    }

    @Override
    protected void configureAlternate(
        final ThrowingBiConsumer<Type1, Type2> throwing)
        throws Throwable
    {
        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(throwing).doAccept(t, u);
    }

    @Override
    protected void configureFallback(final BiConsumer<Type1, Type2> fallback)
    {
        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(fallback).accept(t, u);
    }

    @Test
    public void orDoNothingTest()
        throws Throwable
    {
        final ThrowingBiConsumer<Type1, Type2> throwing = getThrowing();
        configureFull(throwing);

        final BiConsumer<Type1, Type2> chain = getChain(throwing).orDoNothing();

        final Callable<Type3> callable = toCallable(chain);

        assertThat(callable.call()).isSameAs(ret1);

        assertThat(callable.call()).isSameAs(ret1);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

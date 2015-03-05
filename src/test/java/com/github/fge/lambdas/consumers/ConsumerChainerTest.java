package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ChainerTest;
import com.github.fge.lambdas.Throwing;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public final class ConsumerChainerTest
    extends ChainerTest<Consumer<Type1>, ThrowingConsumer<Type1>, ConsumerChainer<Type1>, Type2>
{
    private final Type1 t = Type1.mock();

    private final Type2 noValue = Type2.mock();

    private final AtomicReference<Type2> sentinel = new AtomicReference<>();

    public ConsumerChainerTest()
    {
        super(Type2.mock(), Type2.mock());
    }

    @BeforeMethod
    public void initSentinel()
    {
        sentinel.set(noValue);
    }

    @Override
    protected Consumer<Type1> getFallback()
    {
        return mock(Consumer.class);
    }

    @Override
    protected ThrowingConsumer<Type1> getThrowing()
    {
        return mock(ThrowingConsumer.class);
    }

    @Override
    protected ConsumerChainer<Type1> getChain(
        final ThrowingConsumer<Type1> throwing)
    {
        return Throwing.consumer(throwing);
    }

    @Override
    protected Callable<Type2> toCallable(final Consumer<Type1> chain)
    {
        return () -> { chain.accept(t); return sentinel.get(); };
    }

    @Override
    protected void configureFull(final ThrowingConsumer<Type1> throwing)
        throws Throwable
    {
        doAnswer(invocation -> { sentinel.set(ret1); return null; })
            .doThrow(checked)
            .doThrow(unchecked)
            .doThrow(error)
            .when(throwing).doAccept(t);
    }

    @Override
    protected void configureAlternate(final ThrowingConsumer<Type1> throwing)
        throws Throwable
    {
        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(throwing).doAccept(t);
    }

    @Override
    protected void configureFallback(final Consumer<Type1> fallback)
    {
        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(fallback).accept(t);
    }

    @Test
    public void orDoNothingTest()
        throws Throwable
    {
        final ThrowingConsumer<Type1> throwing = getThrowing();
        configureFull(throwing);

        final Consumer<Type1> chain = getChain(throwing).orDoNothing();

        final Callable<Type2> callable = toCallable(chain);

        assertThat(callable.call()).isSameAs(ret1);

        assertThat(callable.call()).isSameAs(ret1);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

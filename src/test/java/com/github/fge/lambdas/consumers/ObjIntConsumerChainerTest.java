package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ChainerTest;
import com.github.fge.lambdas.Throwing;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.ObjIntConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public final class ObjIntConsumerChainerTest
    extends ChainerTest<ObjIntConsumer<Type1>, ThrowingObjIntConsumer<Type1>, ObjIntConsumerChainer<Type1>, Type2>
{
    private final Type1 t = Type1.mock();
    private final int value = 42;

    private final Type2 noValue = Type2.mock();

    private final AtomicReference<Type2> sentinel = new AtomicReference<>();

    public ObjIntConsumerChainerTest()
    {
        super(Type2.mock(), Type2.mock());
    }

    @BeforeMethod
    public void initSentinel()
    {
        sentinel.set(noValue);
    }

    @Override
    protected ObjIntConsumer<Type1> getFallback()
    {
        return mock(ObjIntConsumer.class);
    }

    @Override
    protected ThrowingObjIntConsumer<Type1> getThrowing()
    {
        return mock(ThrowingObjIntConsumer.class);
    }

    @Override
    protected ObjIntConsumerChainer<Type1> getChain(
        final ThrowingObjIntConsumer<Type1> throwing)
    {
        return Throwing.objIntConsumer(throwing);
    }

    @Override
    protected Callable<Type2> toCallable(final ObjIntConsumer<Type1> chain)
    {
        return () -> { chain.accept(t, value); return sentinel.get(); };
    }

    @Override
    protected void configureFull(
        final ThrowingObjIntConsumer<Type1> throwing)
        throws Throwable
    {
        doAnswer(ignored -> { sentinel.set(ret1); return null; })
            .doThrow(checked)
            .doThrow(unchecked)
            .doThrow(error)
            .when(throwing).doAccept(t, value);
    }

    @Override
    protected void configureAlternate(
        final ThrowingObjIntConsumer<Type1> throwing)
        throws Throwable
    {
        doAnswer(ignored -> { sentinel.set(ret2); return null; })
            .when(throwing).doAccept(t, value);
    }

    @Override
    protected void configureFallback(final ObjIntConsumer<Type1> fallback)
    {
        doAnswer(ignored -> { sentinel.set(ret2); return null; })
            .when(fallback).accept(t, value);
    }

    @Test
    public void orDoNothingTest()
        throws Throwable
    {
        final ThrowingObjIntConsumer<Type1> throwing = getThrowing();
        configureFull(throwing);

        final ObjIntConsumer<Type1> chain = getChain(throwing).orDoNothing();

        final Callable<Type2> callable = toCallable(chain);

        assertThat(callable.call()).isSameAs(ret1);

        assertThat(callable.call()).isSameAs(ret1);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

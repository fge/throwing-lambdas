package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.ObjDoubleConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public final class ObjDoubleConsumerChainTest
    extends ChainTest<ObjDoubleConsumer<Type1>, ThrowingObjDoubleConsumer<Type1>, ObjDoubleConsumerChain<Type1>, Type2>
{
    private final Type1 t = Type1.mock();
    private final double value = 42.0;

    private final Type2 noValue = Type2.mock();

    private final AtomicReference<Type2> sentinel = new AtomicReference<>();

    public ObjDoubleConsumerChainTest()
    {
        super(Type2.mock(), Type2.mock());
    }

    @BeforeMethod
    public void initSentinel()
    {
        sentinel.set(noValue);
    }

    @Override
    protected ObjDoubleConsumer<Type1> getFallback()
    {
        return mock(ObjDoubleConsumer.class);
    }

    @Override
    protected ThrowingObjDoubleConsumer<Type1> getThrowing()
    {
        return mock(ThrowingObjDoubleConsumer.class);
    }

    @Override
    protected ObjDoubleConsumerChain<Type1> getChain(
        final ThrowingObjDoubleConsumer<Type1> throwing)
    {
        return Throwing.objDoubleConsumer(throwing);
    }

    @Override
    protected Callable<Type2> toCallable(final ObjDoubleConsumer<Type1> chain)
    {
        return () -> { chain.accept(t, value); return sentinel.get(); };
    }

    @Override
    protected void configureFull(
        final ThrowingObjDoubleConsumer<Type1> throwing)
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
        final ThrowingObjDoubleConsumer<Type1> throwing)
        throws Throwable
    {
        doAnswer(ignored -> { sentinel.set(ret2); return null; })
            .when(throwing).doAccept(t, value);
    }

    @Override
    protected void configureFallback(final ObjDoubleConsumer<Type1> fallback)
    {
        doAnswer(ignored -> { sentinel.set(ret2); return null; })
            .when(fallback).accept(t, value);
    }

    @Test
    public void orDoNothingTest()
        throws Throwable
    {
        final ThrowingObjDoubleConsumer<Type1> throwing = getThrowing();
        configureFull(throwing);

        final ObjDoubleConsumer<Type1> chain = getChain(throwing).orDoNothing();

        final Callable<Type2> callable = toCallable(chain);

        assertThat(callable.call()).isSameAs(ret1);

        assertThat(callable.call()).isSameAs(ret1);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

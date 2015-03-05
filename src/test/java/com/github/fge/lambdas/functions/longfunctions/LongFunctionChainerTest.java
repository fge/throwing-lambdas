package com.github.fge.lambdas.functions.longfunctions;

import com.github.fge.lambdas.ChainerTest;
import com.github.fge.lambdas.Throwing;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.LongFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class LongFunctionChainerTest
    extends ChainerTest<LongFunction<Type1>, ThrowingLongFunction<Type1>, LongFunctionChainer<Type1>, Type1>
{
    private final long value = 42L;

    public LongFunctionChainerTest()
    {
        super(Type1.mock(), Type1.mock());
    }

    @Override
    protected LongFunction<Type1> getFallback()
    {
        return mock(LongFunction.class);
    }

    @Override
    protected ThrowingLongFunction<Type1> getThrowing()
    {
        return mock(ThrowingLongFunction.class);
    }

    @Override
    protected LongFunctionChainer<Type1> getChain(
        final ThrowingLongFunction<Type1> throwing)
    {
        return Throwing.longFunction(throwing);
    }

    @Override
    protected Callable<Type1> toCallable(final LongFunction<Type1> chain)
    {
        return () -> chain.apply(value);
    }

    @Override
    protected void configureFull(final ThrowingLongFunction<Type1> throwing)
        throws Throwable
    {
        when(throwing.doApply(value))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(
        final ThrowingLongFunction<Type1> throwing)
        throws Throwable
    {
        when(throwing.doApply(value))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final LongFunction<Type1> fallback)
    {
        when(fallback.apply(value))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingLongFunction<Type1> throwing = getThrowing();
        configureFull(throwing);

        final LongFunction<Type1> chain = getChain(throwing).orReturn(ret2);

        final Callable<Type1> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

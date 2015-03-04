package com.github.fge.lambdas.function;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.ToDoubleFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class ToDoubleFunctionChainTest
    extends ChainTest<ToDoubleFunction<Type1>, ThrowingToDoubleFunction<Type1>, ToDoubleFunctionChain<Type1>, Double>
{
    private final Type1 value = Type1.mock();

    public ToDoubleFunctionChainTest()
    {
        super(42.0, 24.0);
    }

    @Override
    protected ToDoubleFunction<Type1> getFallback()
    {
        return mock(ToDoubleFunction.class);
    }

    @Override
    protected ThrowingToDoubleFunction<Type1> getThrowing()
    {
        return mock(ThrowingToDoubleFunction.class);
    }

    @Override
    protected ToDoubleFunctionChain<Type1> getChain(
        final ThrowingToDoubleFunction<Type1> throwing)
    {
        return Throwing.toDoubleFunction(throwing);
    }

    @Override
    protected Callable<Double> toCallable(final ToDoubleFunction<Type1> chain)
    {
        return () -> chain.applyAsDouble(value);
    }

    @Override
    protected void configureFull(final ThrowingToDoubleFunction<Type1> throwing)
        throws Throwable
    {
        when(throwing.doApplyAsDouble(value))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(
        final ThrowingToDoubleFunction<Type1> throwing)
        throws Throwable
    {
        when(throwing.doApplyAsDouble(value))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final ToDoubleFunction<Type1> fallback)
    {
        when(fallback.applyAsDouble(value))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingToDoubleFunction<Type1> throwing = getThrowing();
        configureFull(throwing);

        final ToDoubleFunction<Type1> chain = getChain(throwing).orReturn(ret2);

        final Callable<Double> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

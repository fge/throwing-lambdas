package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ChainerTest;
import com.github.fge.lambdas.Throwing;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class UnaryOperatorChainerTest
    extends ChainerTest<UnaryOperator<Type1>, ThrowingUnaryOperator<Type1>, UnaryOperatorChainer<Type1>, Type1>
{
    private final Type1 t = Type1.mock();

    public UnaryOperatorChainerTest()
    {
        super(Type1.mock(), Type1.mock());
    }

    @Override
    protected UnaryOperator<Type1> getFallback()
    {
        return mock(UnaryOperator.class);
    }

    @Override
    protected ThrowingUnaryOperator<Type1> getThrowing()
    {
        return mock(ThrowingUnaryOperator.class);
    }

    @Override
    protected UnaryOperatorChainer<Type1> getChain(
        final ThrowingUnaryOperator<Type1> throwing)
    {
        return Throwing.unaryOperator(throwing);
    }

    @Override
    protected Callable<Type1> toCallable(final UnaryOperator<Type1> chain)
    {
        return () -> chain.apply(t);
    }

    @Override
    protected void configureFull(final ThrowingUnaryOperator<Type1> throwing)
        throws Throwable
    {
        when(throwing.doApply(t))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(
        final ThrowingUnaryOperator<Type1> throwing)
        throws Throwable
    {
        when(throwing.doApply(t))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final UnaryOperator<Type1> fallback)
    {
        when(fallback.apply(t))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingUnaryOperator<Type1> throwing = getThrowing();
        configureFull(throwing);

        final UnaryOperator<Type1> chain = getChain(throwing).orReturn(ret2);

        final Callable<Type1> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Test
    public void orReturnSelfTest()
        throws Throwable
    {
        final ThrowingUnaryOperator<Type1> throwing = getThrowing();
        configureFull(throwing);

        final UnaryOperator<Type1> chain = getChain(throwing).orReturnSelf();

        final Callable<Type1> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(t);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

package com.github.fge.lambdas.function.operators;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.Throwing;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.BinaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class BinaryOperatorChainTest
    extends ChainTest<BinaryOperator<Type1>, ThrowingBinaryOperator<Type1>, BinaryOperatorChain<Type1>, Type1>
{
    private final Type1 t = Type1.mock();
    private final Type1 u = Type1.mock();

    public BinaryOperatorChainTest()
    {
        super(Type1.mock(), Type1.mock());
    }

    @Override
    protected BinaryOperator<Type1> getFallback()
    {
        return mock(BinaryOperator.class);
    }

    @Override
    protected ThrowingBinaryOperator<Type1> getThrowing()
    {
        return mock(ThrowingBinaryOperator.class);
    }

    @Override
    protected BinaryOperatorChain<Type1> getChain(
        final ThrowingBinaryOperator<Type1> throwing)
    {
        return Throwing.binaryOperator(throwing);
    }

    @Override
    protected Callable<Type1> toCallable(final BinaryOperator<Type1> chain)
    {
        return () -> chain.apply(t, u);
    }

    @Override
    protected void configureFull(final ThrowingBinaryOperator<Type1> throwing)
        throws Throwable
    {
        when(throwing.doApply(t, u))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(
        final ThrowingBinaryOperator<Type1> throwing)
        throws Throwable
    {
        when(throwing.doApply(t, u))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final BinaryOperator<Type1> fallback)
    {
        when(fallback.apply(t, u))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingBinaryOperator<Type1> throwing = getThrowing();
        configureFull(throwing);

        final BinaryOperator<Type1> chain = getChain(throwing).orReturn(ret2);

        final Callable<Type1> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Test
    public void orReturnLeftTest()
        throws Throwable
    {
        final ThrowingBinaryOperator<Type1> throwing = getThrowing();
        configureFull(throwing);

        final BinaryOperator<Type1> chain = getChain(throwing).orReturnLeft();

        final Callable<Type1> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(t);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Test
    public void orReturnRightTest()
        throws Throwable
    {
        final ThrowingBinaryOperator<Type1> throwing = getThrowing();
        configureFull(throwing);

        final BinaryOperator<Type1> chain = getChain(throwing).orReturnRight();

        final Callable<Type1> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(u);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

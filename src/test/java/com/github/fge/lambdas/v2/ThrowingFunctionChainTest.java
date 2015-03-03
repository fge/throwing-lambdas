package com.github.fge.lambdas.v2;

import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class ThrowingFunctionChainTest
    extends ChainTest<Function<Type1, Type2>, ThrowingFunction<Type1, Type2>,
            ThrowingFunctionChain<Type1, Type2>, Type2>
{
    private final Type1 t = Type1.mock();


    public ThrowingFunctionChainTest()
        throws NoSuchMethodException, IllegalAccessException
    {
        super(Type2.mock(), Type2.mock());
    }

    @Override
    protected Function<Type1, Type2> getFallback()
    {
        return mock(Function.class);
    }

    @Override
    protected ThrowingFunction<Type1, Type2> getThrowing()
    {
        return mock(ThrowingFunction.class);
    }

    @Override
    protected ThrowingFunctionChain<Type1, Type2> getChain(
        final ThrowingFunction<Type1, Type2> throwing)
    {
        return new ThrowingFunctionChain<>(throwing);
    }

    @Override
    protected Callable<Type2> toCallable(
        final Function<Type1, Type2> chainer)
    {
        return () -> chainer.apply(t);
    }

    @Override
    protected void configureFull(final ThrowingFunction<Type1, Type2> throwing)
        throws Throwable
    {
        when(throwing.doApply(t)).thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(
        final ThrowingFunction<Type1, Type2> throwing)
        throws Throwable
    {
        when(throwing.doApply(t)).thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final Function<Type1, Type2> fallback)
    {
        when(fallback.apply(t)).thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingFunction<Type1, Type2> throwing = getThrowing();
        configureFull(throwing);

        final Function<Type1, Type2> chain = getChain(throwing)
            .orReturn(ret2);

        final Callable<Type2> callable = toCallable(chain);

        assertThat(callable.call()).isSameAs(ret1);

        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

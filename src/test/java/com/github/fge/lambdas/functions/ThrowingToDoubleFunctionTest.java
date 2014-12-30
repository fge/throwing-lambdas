package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;

import java.util.concurrent.Callable;
import java.util.function.ToDoubleFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause" })
public final class ThrowingToDoubleFunctionTest
    extends ThrowingInterfaceBaseTest<ThrowingToDoubleFunction<Type1>, ToDoubleFunction<Type1>, Double>
{
    private final Type1 arg = Type1.mock();
    private final double ret1 = 0.5;
    private final double ret2 = 0.25;

    @Override
    protected ThrowingToDoubleFunction<Type1> getAlternate()
    {
        return SpiedThrowingToDoubleFunction.newSpy();
    }

    @Override
    protected ThrowingToDoubleFunction<Type1> getPreparedInstance()
        throws Throwable
    {
        final ThrowingToDoubleFunction<Type1> spy = getAlternate();

        when(spy.doApplyAsDouble(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected ToDoubleFunction<Type1> getFallbackInstance()
    {
        //noinspection unchecked
        return mock(ToDoubleFunction.class);
    }

    @Override
    protected Runnable runnableFrom(final ToDoubleFunction<Type1> instance)
    {
        return () -> instance.applyAsDouble(arg);
    }

    @Override
    protected Callable<Double> callableFrom(
        final ToDoubleFunction<Type1> instance)
    {
        return () -> instance.applyAsDouble(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingToDoubleFunction<Type1> instance = getPreparedInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final ToDoubleFunction<Type1> instance
            = getPreparedInstance().orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingToDoubleFunction<Type1> first = getPreparedInstance();
        final ThrowingToDoubleFunction<Type1> second = getAlternate();
        when(second.applyAsDouble(arg)).thenReturn(ret2);

        final ToDoubleFunction<Type1> instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingToDoubleFunction<Type1> first = getPreparedInstance();
        final ToDoubleFunction<Type1> second = getFallbackInstance();
        when(second.applyAsDouble(arg)).thenReturn(ret2);

        final ToDoubleFunction<Type1> instance = first.fallbackTo(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final ThrowingToDoubleFunction<Type1> first = getPreparedInstance();

        final ToDoubleFunction<Type1> instance = first.orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Double> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

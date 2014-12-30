package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;

import java.util.concurrent.Callable;
import java.util.function.DoubleFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "OverlyBroadThrowsClause" })
public final class ThrowingDoubleFunctionTest
    extends ThrowingInterfaceBaseTest<ThrowingDoubleFunction<Type1>, DoubleFunction<Type1>, Type1>
{
    private final double arg = 0.25;

    public ThrowingDoubleFunctionTest()
    {
        super(Type1.mock(), Type1.mock());
    }


    @Override
    protected ThrowingDoubleFunction<Type1> getAlternate()
        throws Throwable
    {
        final ThrowingDoubleFunction<Type1> spy =
            SpiedThrowingDoubleFunction.newSpy();

        when(spy.doApply(arg)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingDoubleFunction<Type1> getTestInstance()
        throws Throwable
    {
        final ThrowingDoubleFunction<Type1> spy
            = SpiedThrowingDoubleFunction.newSpy();

        when(spy.doApply(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected DoubleFunction<Type1> getFallback()
    {
        final DoubleFunction<Type1> mock = mock(DoubleFunction.class);

        when(mock.apply(arg)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final DoubleFunction<Type1> instance)
    {
        return () -> instance.apply(arg);
    }

    @Override
    protected Callable<Type1> asCallable(final DoubleFunction<Type1> instance)
    {
        return () -> instance.apply(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingDoubleFunction<Type1> instance = getTestInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final ThrowingDoubleFunction<Type1> first = getTestInstance();

        final DoubleFunction<Type1> instance = first.orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingDoubleFunction<Type1> first = getTestInstance();
        final ThrowingDoubleFunction<Type1> second = getAlternate();

        final DoubleFunction<Type1> instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingDoubleFunction<Type1> first = getTestInstance();
        final DoubleFunction<Type1> second = getFallback();

        final DoubleFunction<Type1> instance = first.fallbackTo(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final ThrowingDoubleFunction<Type1> first = getTestInstance();

        final DoubleFunction<Type1> instance = first.orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

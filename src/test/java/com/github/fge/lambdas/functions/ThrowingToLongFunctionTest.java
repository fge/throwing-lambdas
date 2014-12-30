package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;

import java.util.concurrent.Callable;
import java.util.function.ToLongFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause" })
public final class ThrowingToLongFunctionTest
    extends ThrowingInterfaceBaseTest<ThrowingToLongFunction<Type1>, ToLongFunction<Type1>, Long>
{
    private final Type1 arg = Type1.mock();

    public ThrowingToLongFunctionTest()
    {
        super(42L, 25L);
    }

    @Override
    protected ThrowingToLongFunction<Type1> getAlternate()
        throws Throwable
    {
        final ThrowingToLongFunction<Type1> spy =
            SpiedThrowingToLongFunction.newSpy();

        when(spy.doApplyAsLong(arg)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingToLongFunction<Type1> getTestInstance()
        throws Throwable
    {
        final ThrowingToLongFunction<Type1> spy
            = SpiedThrowingToLongFunction.newSpy();

        when(spy.doApplyAsLong(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected ToLongFunction<Type1> getFallback()
    {
        @SuppressWarnings("unchecked")
        final ToLongFunction<Type1> mock = mock(ToLongFunction.class);

        when(mock.applyAsLong(arg)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final ToLongFunction<Type1> instance)
    {
        return () -> instance.applyAsLong(arg);
    }

    @Override
    protected Callable<Long> asCallable(
        final ToLongFunction<Type1> instance)
    {
        return () -> instance.applyAsLong(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingToLongFunction<Type1> instance = getTestInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final ToLongFunction<Type1> instance
            = getTestInstance().orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingToLongFunction<Type1> first = getTestInstance();
        final ThrowingToLongFunction<Type1> second = getAlternate();

        final ToLongFunction<Type1> instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingToLongFunction<Type1> first = getTestInstance();
        final ToLongFunction<Type1> second = getFallback();

        final ToLongFunction<Type1> instance = first.fallbackTo(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final ThrowingToLongFunction<Type1> first = getTestInstance();

        final ToLongFunction<Type1> instance = first.orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

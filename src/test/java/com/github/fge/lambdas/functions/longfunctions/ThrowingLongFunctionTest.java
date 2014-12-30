package com.github.fge.lambdas.functions.longfunctions;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;

import java.util.concurrent.Callable;
import java.util.function.LongFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "OverlyBroadThrowsClause" })
public final class ThrowingLongFunctionTest
    extends ThrowingInterfaceBaseTest<ThrowingLongFunction<Type1>, LongFunction<Type1>, Type1>
{
    private final long arg = 2898L;
    private final Type1 ret1 = Type1.mock();
    private final Type1 ret2 = Type1.mock();

    @Override
    protected ThrowingLongFunction<Type1> getAlternate()
    {
        return SpiedThrowingLongFunction.newSpy();
    }

    @Override
    protected ThrowingLongFunction<Type1> getPreparedInstance()
        throws Throwable
    {
        final ThrowingLongFunction<Type1> spy = getAlternate();

        when(spy.doApply(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected LongFunction<Type1> getFallbackInstance()
    {
        //noinspection unchecked
        return mock(LongFunction.class);
    }

    @Override
    protected Runnable runnableFrom(final LongFunction<Type1> instance)
    {
        return () -> instance.apply(arg);
    }

    @Override
    protected Callable<Type1> callableFrom(final LongFunction<Type1> instance)
    {
        return () -> instance.apply(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingLongFunction<Type1> instance = getPreparedInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final ThrowingLongFunction<Type1> first = getPreparedInstance();

        final LongFunction<Type1> instance = first.orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingLongFunction<Type1> first = getPreparedInstance();
        final ThrowingLongFunction<Type1> second = getAlternate();
        when(second.doApply(arg)).thenReturn(ret2);

        final LongFunction<Type1> instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingLongFunction<Type1> first = getPreparedInstance();
        final LongFunction<Type1> second = getFallbackInstance();
        when(second.apply(arg)).thenReturn(ret2);

        final LongFunction<Type1> instance = first.fallbackTo(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final ThrowingLongFunction<Type1> first = getPreparedInstance();

        final LongFunction<Type1> instance = first.orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

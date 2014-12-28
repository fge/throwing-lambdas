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
    private final long ret1 = 42L;
    private final long ret2 = 25L;

    @Override
    protected ThrowingToLongFunction<Type1> getBaseInstance()
    {
        return SpiedThrowingToLongFunction.newSpy();
    }

    @Override
    protected ThrowingToLongFunction<Type1> getPreparedInstance()
        throws Throwable
    {
        final ThrowingToLongFunction<Type1> spy = getBaseInstance();

        when(spy.doApplyAsLong(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected ToLongFunction<Type1> getNonThrowingInstance()
    {
        //noinspection unchecked
        return mock(ToLongFunction.class);
    }

    @Override
    protected Runnable runnableFrom(final ToLongFunction<Type1> instance)
    {
        return () -> instance.applyAsLong(arg);
    }

    @Override
    protected Callable<Long> callableFrom(
        final ToLongFunction<Type1> instance)
    {
        return () -> instance.applyAsLong(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingToLongFunction<Type1> instance = getPreparedInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

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
            = getPreparedInstance().orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingToLongFunction<Type1> first = getPreparedInstance();
        final ThrowingToLongFunction<Type1> second = getBaseInstance();
        when(second.applyAsLong(arg)).thenReturn(ret2);

        final ToLongFunction<Type1> instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOr()
        throws Throwable
    {
        final ThrowingToLongFunction<Type1> first = getPreparedInstance();
        final ToLongFunction<Type1> second = getNonThrowingInstance();
        when(second.applyAsLong(arg)).thenReturn(ret2);

        final ToLongFunction<Type1> instance = first.or(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final ThrowingToLongFunction<Type1> first = getPreparedInstance();

        final ToLongFunction<Type1> instance = first.orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Long> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

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
    private final Type1 ret1 = Type1.mock();
    private final Type1 ret2 = Type1.mock();

    @Override
    protected ThrowingDoubleFunction<Type1> getBaseInstance()
    {
        return SpiedThrowingDoubleFunction.newSpy();
    }

    @Override
    protected ThrowingDoubleFunction<Type1> getPreparedInstance()
        throws Throwable
    {
        final ThrowingDoubleFunction<Type1> spy = getBaseInstance();

        when(spy.doApply(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected DoubleFunction<Type1> getNonThrowingInstance()
    {
        //noinspection unchecked
        return mock(DoubleFunction.class);
    }

    @Override
    protected Runnable runnableFrom(final DoubleFunction<Type1> instance)
    {
        return () -> instance.apply(arg);
    }

    @Override
    protected Callable<Type1> callableFrom(final DoubleFunction<Type1> instance)
    {
        return () -> instance.apply(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingDoubleFunction<Type1> instance = getPreparedInstance();

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
        final ThrowingDoubleFunction<Type1> first = getPreparedInstance();

        final DoubleFunction<Type1> instance = first.orThrow(MyException.class);

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
        final ThrowingDoubleFunction<Type1> first = getPreparedInstance();
        final ThrowingDoubleFunction<Type1> second = getBaseInstance();
        when(second.doApply(arg)).thenReturn(ret2);

        final DoubleFunction<Type1> instance = first.orTryWith(second);

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
        final ThrowingDoubleFunction<Type1> first = getPreparedInstance();
        final DoubleFunction<Type1> second = getNonThrowingInstance();
        when(second.apply(arg)).thenReturn(ret2);

        final DoubleFunction<Type1> instance = first.fallbackTo(second);

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
        final ThrowingDoubleFunction<Type1> first = getPreparedInstance();

        final DoubleFunction<Type1> instance = first.orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;

import java.util.concurrent.Callable;
import java.util.function.ToIntFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause" })
public final class ThrowingToIntFunctionTest
    extends ThrowingInterfaceBaseTest<ThrowingToIntFunction<Type1>, ToIntFunction<Type1>, Integer>
{
    private final Type1 arg = Type1.mock();
    private final int ret1 = 42;
    private final int ret2 = 25;

    @Override
    protected ThrowingToIntFunction<Type1> getBaseInstance()
    {
        return SpiedThrowingToIntFunction.newSpy();
    }

    @Override
    protected ThrowingToIntFunction<Type1> getPreparedInstance()
        throws Throwable
    {
        final ThrowingToIntFunction<Type1> spy = getBaseInstance();

        when(spy.doApplyAsInt(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected ToIntFunction<Type1> getNonThrowingInstance()
    {
        //noinspection unchecked
        return mock(ToIntFunction.class);
    }

    @Override
    protected Runnable runnableFrom(final ToIntFunction<Type1> instance)
    {
        return () -> instance.applyAsInt(arg);
    }

    @Override
    protected Callable<Integer> callableFrom(
        final ToIntFunction<Type1> instance)
    {
        return () -> instance.applyAsInt(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingToIntFunction<Type1> instance = getPreparedInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final ToIntFunction<Type1> instance
            = getPreparedInstance().orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingToIntFunction<Type1> first = getPreparedInstance();
        final ThrowingToIntFunction<Type1> second = getBaseInstance();
        when(second.applyAsInt(arg)).thenReturn(ret2);

        final ToIntFunction<Type1> instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOr()
        throws Throwable
    {
        final ThrowingToIntFunction<Type1> first = getPreparedInstance();
        final ToIntFunction<Type1> second = getNonThrowingInstance();
        when(second.applyAsInt(arg)).thenReturn(ret2);

        final ToIntFunction<Type1> instance = first.or(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final ThrowingToIntFunction<Type1> first = getPreparedInstance();

        final ToIntFunction<Type1> instance = first.orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

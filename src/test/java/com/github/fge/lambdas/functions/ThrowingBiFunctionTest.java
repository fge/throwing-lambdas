package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import com.github.fge.lambdas.helpers.Type3;

import java.util.concurrent.Callable;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "OverlyBroadThrowsClause" })
public final class ThrowingBiFunctionTest
    extends ThrowingInterfaceBaseTest<ThrowingBiFunction<Type1, Type2, Type3>, BiFunction<Type1, Type2, Type3>, Type3>
{
    private final Type1 arg1 = Type1.mock();
    private final Type2 arg2 = Type2.mock();
    private final Type3 ret1 = Type3.mock();
    private final Type3 ret2 = Type3.mock();

    @Override
    protected ThrowingBiFunction<Type1, Type2, Type3> getBaseInstance()
    {
        return SpiedThrowingBiFunction.newSpy();
    }

    @Override
    protected ThrowingBiFunction<Type1, Type2, Type3> getPreparedInstance()
        throws Throwable
    {
        final ThrowingBiFunction<Type1, Type2, Type3> spy = getBaseInstance();

        when(spy.doApply(arg1, arg2)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected BiFunction<Type1, Type2, Type3> getNonThrowingInstance()
    {
        //noinspection unchecked
        return mock(BiFunction.class);
    }

    @Override
    protected Runnable runnableFrom(
        final BiFunction<Type1, Type2, Type3> instance)
    {
        return () -> instance.apply(arg1, arg2);
    }

    @Override
    protected Callable<Type3> callableFrom(
        final BiFunction<Type1, Type2, Type3> instance)
    {
        return () -> instance.apply(arg1, arg2);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingBiFunction<Type1, Type2, Type3> instance
            = getPreparedInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type3> callable = callableFrom(instance);

        assertThat(callable.call()).isSameAs(ret1);

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final BiFunction<Type1, Type2, Type3> instance
            = getPreparedInstance().orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type3> callable = callableFrom(instance);

        assertThat(callable.call()).isSameAs(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingBiFunction<Type1, Type2, Type3> first
            = getPreparedInstance();
        final ThrowingBiFunction<Type1, Type2, Type3> second
            = getBaseInstance();
        when(second.doApply(arg1, arg2)).thenReturn(ret2);

        final BiFunction<Type1, Type2, Type3> instance
            = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type3> callable = callableFrom(instance);

        assertThat(callable.call()).isSameAs(ret1);

        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOr()
        throws Throwable
    {
        final ThrowingBiFunction<Type1, Type2, Type3> first
            = getPreparedInstance();
        final BiFunction<Type1, Type2, Type3> second = getNonThrowingInstance();
        when(second.apply(arg1, arg2)).thenReturn(ret2);

        final BiFunction<Type1, Type2, Type3> instance = first.or(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type3> callable = callableFrom(instance);

        assertThat(callable.call()).isSameAs(ret1);
        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final ThrowingBiFunction<Type1, Type2, Type3> first
            = getPreparedInstance();

        final BiFunction<Type1, Type2, Type3> instance = first.orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type3> callable = callableFrom(instance);

        assertThat(callable.call()).isSameAs(ret1);
        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

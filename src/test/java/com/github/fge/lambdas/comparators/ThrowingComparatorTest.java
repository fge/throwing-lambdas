package com.github.fge.lambdas.comparators;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;

import java.util.Comparator;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "AutoBoxing", "ProhibitedExceptionDeclared",
    "OverlyBroadThrowsClause" })
public final class ThrowingComparatorTest
    extends ThrowingInterfaceBaseTest<ThrowingComparator<Type1>, Comparator<Type1>, Integer>
{
    private final Type1 arg1 = Type1.mock();
    private final Type1 arg2 = Type1.mock();

    private final int ret1 = 42;
    private final int ret2 = 421;

    @Override
    protected ThrowingComparator<Type1> getBaseInstance()
    {
        return SpiedThrowingComparator.newSpy();
    }

    @Override
    protected ThrowingComparator<Type1> getPreparedInstance()
        throws Throwable
    {
        final ThrowingComparator<Type1> spy = getBaseInstance();

        when(spy.doCompare(arg1, arg2)).thenReturn(ret1)
            .thenThrow(checked).thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected Comparator<Type1> getNonThrowingInstance()
    {
        //noinspection unchecked
        return mock(Comparator.class);
    }

    @Override
    protected Runnable runnableFrom(final Comparator<Type1> instance)
    {
        return () -> instance.compare(arg1, arg2);
    }

    @Override
    protected Callable<Integer> callableFrom(final Comparator<Type1> instance)
    {
        return () -> instance.compare(arg1, arg2);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final Comparator<Type1> instance = getPreparedInstance();

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
        final Comparator<Type1> instance
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
        final ThrowingComparator<Type1> first = getPreparedInstance();

        final ThrowingComparator<Type1> second = getBaseInstance();
        when(second.doCompare(arg1, arg2)).thenReturn(ret2);

        final Comparator<Type1> instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingComparator<Type1> first = getPreparedInstance();

        final Comparator<Type1> second = getNonThrowingInstance();
        when(second.compare(arg1, arg2)).thenReturn(ret2);

        final Comparator<Type1> instance = first.fallbackTo(second);

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
        final ThrowingComparator<Type1> first = getPreparedInstance();

        final Comparator<Type1> instance = first.orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

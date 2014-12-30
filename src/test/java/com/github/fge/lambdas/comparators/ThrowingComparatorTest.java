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

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingComparatorTest
    extends ThrowingInterfaceBaseTest<ThrowingComparator<Type1>, Comparator<Type1>, Integer>
{
    private final Type1 arg1 = Type1.mock();
    private final Type1 arg2 = Type1.mock();

    public ThrowingComparatorTest()
    {
        super(42, 421);
    }

    @Override
    protected ThrowingComparator<Type1> getAlternate()
        throws Throwable
    {
        final ThrowingComparator<Type1> spy =
            SpiedThrowingComparator.newSpy();

        when(spy.doCompare(arg1, arg2)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingComparator<Type1> getTestInstance()
        throws Throwable
    {
        final ThrowingComparator<Type1> spy
            = SpiedThrowingComparator.newSpy();

        when(spy.doCompare(arg1, arg2)).thenReturn(ret1)
            .thenThrow(checked).thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected Comparator<Type1> getFallback()
    {
        @SuppressWarnings("unchecked")
        final Comparator<Type1> mock = mock(Comparator.class);

        when(mock.compare(arg1, arg2)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Callable<Integer> asCallable(final Comparator<Type1> instance)
    {
        return () -> instance.compare(arg1, arg2);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final Comparator<Type1> instance = getTestInstance();

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, ThrownByLambdaException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final Comparator<Type1> instance
            = getTestInstance().orThrow(MyException.class);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, MyException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingComparator<Type1> first = getTestInstance();
        final ThrowingComparator<Type1> second = getAlternate();

        final Comparator<Type1> instance = first.orTryWith(second);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingComparator<Type1> first = getTestInstance();

        final Comparator<Type1> second = getFallback();

        final Comparator<Type1> instance = first.fallbackTo(second);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final ThrowingComparator<Type1> first = getTestInstance();

        final Comparator<Type1> instance = first.orReturn(ret2);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

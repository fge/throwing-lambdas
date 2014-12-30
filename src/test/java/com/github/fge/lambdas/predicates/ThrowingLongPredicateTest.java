package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.LongPredicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingLongPredicateTest
    extends ThrowingInterfaceBaseTest<ThrowingLongPredicate, LongPredicate, Boolean>
{
    private final long arg = 0x287981723987L;

    public ThrowingLongPredicateTest()
    {
        super(true, false);
    }

    @Override
    protected ThrowingLongPredicate getAlternate()
        throws Throwable
    {
        final ThrowingLongPredicate spy =
            SpiedThrowingLongPredicate.newSpy();

        when(spy.doTest(arg)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingLongPredicate getTestInstance()
        throws Throwable
    {
        final ThrowingLongPredicate spy
            = SpiedThrowingLongPredicate.newSpy();

        when(spy.doTest(arg)).thenReturn(true).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected LongPredicate getFallback()
    {
        final LongPredicate mock = mock(LongPredicate.class);

        when(mock.test(arg)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Callable<Boolean> asCallable(final LongPredicate instance)
    {
        return () -> instance.test(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingLongPredicate instance = getTestInstance();

        final Callable<Boolean> callable = asCallable(instance);

        assertThat(callable.call()).isTrue();

        verifyCheckedRethrow(callable, ThrownByLambdaException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final LongPredicate instance
            = getTestInstance().orThrow(MyException.class);

        final Callable<Boolean> callable = asCallable(instance);

        assertThat(callable.call()).isTrue();

        verifyCheckedRethrow(callable, MyException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingLongPredicate first = getTestInstance();
        final ThrowingLongPredicate second = getAlternate();

        final LongPredicate instance = first.orTryWith(second);

        final Callable<Boolean> callable = asCallable(instance);

        assertThat(callable.call()).isTrue();
        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingLongPredicate first = getTestInstance();
        final LongPredicate second = getFallback();

        final LongPredicate instance = first.fallbackTo(second);

        final Callable<Boolean> callable = asCallable(instance);

        assertThat(callable.call()).isTrue();
        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final LongPredicate instance = getTestInstance().orReturn(false);

        final Callable<Boolean> callable = asCallable(instance);

        assertThat(callable.call()).isTrue();
        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

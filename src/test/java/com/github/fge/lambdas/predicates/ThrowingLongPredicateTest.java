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

    @Override
    protected ThrowingLongPredicate getAlternate()
    {
        return SpiedThrowingLongPredicate.newSpy();
    }

    @Override
    protected ThrowingLongPredicate getPreparedInstance()
        throws Throwable
    {
        final ThrowingLongPredicate spy = getAlternate();

        when(spy.doTest(arg)).thenReturn(true).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected LongPredicate getFallbackInstance()
    {
        return mock(ThrowingLongPredicate.class);
    }

    @Override
    protected Runnable runnableFrom(final LongPredicate instance)
    {
        return () -> instance.test(arg);
    }

    @Override
    protected Callable<Boolean> callableFrom(final LongPredicate instance)
    {
        return () -> instance.test(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingLongPredicate instance = getPreparedInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Boolean> callable = callableFrom(instance);

        assertThat(callable.call()).isTrue();

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final LongPredicate instance
            = getPreparedInstance().orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Boolean> callable = callableFrom(instance);

        assertThat(callable.call()).isTrue();

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingLongPredicate first = getPreparedInstance();
        final ThrowingLongPredicate second = getAlternate();
        // That's the default, but...
        when(second.doTest(arg)).thenReturn(false);

        final LongPredicate instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Boolean> callable = callableFrom(instance);

        assertThat(callable.call()).isTrue();
        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingLongPredicate first = getPreparedInstance();
        final LongPredicate second = getFallbackInstance();
        // That's the default, but...
        when(second.test(arg)).thenReturn(false);

        final LongPredicate instance = first.fallbackTo(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Boolean> callable = callableFrom(instance);

        assertThat(callable.call()).isTrue();
        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final LongPredicate instance = getPreparedInstance().orReturn(false);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Boolean> callable = callableFrom(instance);

        assertThat(callable.call()).isTrue();
        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

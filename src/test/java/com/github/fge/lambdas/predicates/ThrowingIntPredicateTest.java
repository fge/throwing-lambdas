package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.IntPredicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingIntPredicateTest
    extends ThrowingInterfaceBaseTest<ThrowingIntPredicate, IntPredicate, Boolean>
{
    private final int arg = 16;

    public ThrowingIntPredicateTest()
    {
        super(true, false);
    }

    @Override
    protected ThrowingIntPredicate getAlternate()
        throws Throwable
    {
        final ThrowingIntPredicate spy =
            SpiedThrowingIntPredicate.newSpy();

        when(spy.doTest(arg)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingIntPredicate getPreparedInstance()
        throws Throwable
    {
        final ThrowingIntPredicate spy
            = SpiedThrowingIntPredicate.newSpy();

        when(spy.doTest(arg)).thenReturn(true).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected IntPredicate getFallbackInstance()
    {
        final IntPredicate mock = mock(IntPredicate.class);

        when(mock.test(arg)).thenReturn(false);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final IntPredicate instance)
    {
        return () -> instance.test(arg);
    }

    @Override
    protected Callable<Boolean> callableFrom(final IntPredicate instance)
    {
        return () -> instance.test(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingIntPredicate instance = getPreparedInstance();

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
        final IntPredicate instance
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
        final ThrowingIntPredicate first = getPreparedInstance();
        final ThrowingIntPredicate second = getAlternate();

        final IntPredicate instance = first.orTryWith(second);

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
        final ThrowingIntPredicate first = getPreparedInstance();
        final IntPredicate second = getFallbackInstance();

        final IntPredicate instance = first.fallbackTo(second);

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
        final IntPredicate instance = getPreparedInstance().orReturn(false);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Boolean> callable = callableFrom(instance);

        assertThat(callable.call()).isTrue();
        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

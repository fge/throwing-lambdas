package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.DoublePredicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingDoublePredicateTest
    extends ThrowingInterfaceBaseTest<ThrowingDoublePredicate, DoublePredicate, Boolean>
{
    private final double arg = 16.25;

    public ThrowingDoublePredicateTest()
    {
        super(true, false);
    }

    @Override
    protected ThrowingDoublePredicate getAlternate()
        throws Throwable
    {
        final ThrowingDoublePredicate spy =
            SpiedThrowingDoublePredicate.newSpy();

        when(spy.doTest(arg)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingDoublePredicate getTestInstance()
        throws Throwable
    {
        final ThrowingDoublePredicate spy
            = SpiedThrowingDoublePredicate.newSpy();

        when(spy.doTest(arg)).thenReturn(true).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected DoublePredicate getFallback()
    {
        final DoublePredicate mock = mock(DoublePredicate.class);

        when(mock.test(arg)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Callable<Boolean> asCallable(final DoublePredicate instance)
    {
        return () -> instance.test(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingDoublePredicate instance = getTestInstance();

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
        final DoublePredicate instance
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
        final ThrowingDoublePredicate first = getTestInstance();
        final ThrowingDoublePredicate second = getAlternate();

        final DoublePredicate instance = first.orTryWith(second);

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
        final ThrowingDoublePredicate first = getTestInstance();
        final DoublePredicate second = getFallback();

        final DoublePredicate instance = first.fallbackTo(second);

        final Callable<Boolean> callable = asCallable(instance);

        assertThat(callable.call()).isTrue();
        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final DoublePredicate instance = getTestInstance().orReturn(false);

        final Callable<Boolean> callable = asCallable(instance);

        assertThat(callable.call()).isTrue();
        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

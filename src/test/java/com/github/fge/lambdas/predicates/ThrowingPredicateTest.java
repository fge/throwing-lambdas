package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;

import java.util.concurrent.Callable;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"AutoBoxing", "ProhibitedExceptionDeclared",
    "OverlyBroadThrowsClause"})
public final class ThrowingPredicateTest
    extends ThrowingInterfaceBaseTest<ThrowingPredicate<Type1>, Predicate<Type1>, Boolean>
{
    private final Type1 arg = Type1.mock();

    public ThrowingPredicateTest()
    {
        super(true, false);
    }

    @Override
    protected ThrowingPredicate<Type1> getAlternate()
        throws Throwable
    {
        final ThrowingPredicate<Type1> spy =
            SpiedThrowingPredicate.newSpy();

        when(spy.doTest(arg)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingPredicate<Type1> getTestInstance()
        throws Throwable
    {
        final ThrowingPredicate<Type1> spy
            = SpiedThrowingPredicate.newSpy();

        when(spy.doTest(arg)).thenReturn(true).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected Predicate<Type1> getFallback()
    {
        @SuppressWarnings("unchecked")
        final Predicate<Type1> mock = mock(Predicate.class);

        when(mock.test(arg)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final Predicate<Type1> instance)
    {
        return () -> instance.test(arg);
    }

    @Override
    protected Callable<Boolean> asCallable(final Predicate<Type1> instance)
    {
        return () -> instance.test(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingPredicate<Type1> instance = getTestInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Boolean> callable = asCallable(instance);

        assertThat(callable.call()).isTrue();

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final Predicate<Type1> instance
            = getTestInstance().orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Boolean> callable = asCallable(instance);

        assertThat(callable.call()).isTrue();

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);

    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingPredicate<Type1> first = getTestInstance();
        final ThrowingPredicate<Type1> second = getAlternate();

        final Predicate<Type1> instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Boolean> callable = asCallable(instance);

        assertThat(callable.call()).isTrue();
        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingPredicate<Type1> first = getTestInstance();
        final Predicate<Type1> second = getFallback();

        final Predicate<Type1> instance = first.fallbackTo(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Boolean> callable = asCallable(instance);

        assertThat(callable.call()).isTrue();
        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final Predicate<Type1> instance = getTestInstance().orReturn(false);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Boolean> callable = asCallable(instance);

        assertThat(callable.call()).isTrue();
        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

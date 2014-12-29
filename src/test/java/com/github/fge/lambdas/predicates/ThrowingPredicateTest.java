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

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing", "unchecked",
    "OverlyBroadThrowsClause"})
public final class ThrowingPredicateTest
    extends ThrowingInterfaceBaseTest<ThrowingPredicate<Type1>, Predicate<Type1>, Boolean>
{
    private final Type1 arg = Type1.mock();

    @Override
    protected ThrowingPredicate<Type1> getBaseInstance()
    {
        return SpiedThrowingPredicate.newSpy();
    }

    @Override
    protected ThrowingPredicate<Type1> getPreparedInstance()
        throws Throwable
    {
        final ThrowingPredicate<Type1> spy = getBaseInstance();

        when(spy.doTest(arg)).thenReturn(true).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected Predicate<Type1> getNonThrowingInstance()
    {
        return mock(Predicate.class);
    }

    @Override
    protected Runnable runnableFrom(final Predicate<Type1> instance)
    {
        return () -> instance.test(arg);
    }

    @Override
    protected Callable<Boolean> callableFrom(final Predicate<Type1> instance)
    {
        return () -> instance.test(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingPredicate<Type1> instance = getPreparedInstance();

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
        final Predicate<Type1> instance
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
        final ThrowingPredicate<Type1> first = getPreparedInstance();
        final ThrowingPredicate<Type1> second = getBaseInstance();
        // This is by default, but...
        when(second.doTest(arg)).thenReturn(false);

        final Predicate<Type1> instance = first.orTryWith(second);

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
        final ThrowingPredicate<Type1> first = getPreparedInstance();
        final Predicate<Type1> second = getNonThrowingInstance();
        when(second.test(arg)).thenReturn(false);

        final Predicate<Type1> instance = first.fallbackTo(second);

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
        final Predicate<Type1> instance = getPreparedInstance().orReturn(false);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Boolean> callable = callableFrom(instance);

        assertThat(callable.call()).isTrue();
        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

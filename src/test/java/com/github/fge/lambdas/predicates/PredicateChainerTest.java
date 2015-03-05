package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ChainerTest;
import com.github.fge.lambdas.Throwing;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class PredicateChainerTest
    extends ChainerTest<Predicate<Type1>, ThrowingPredicate<Type1>, PredicateChainer<Type1>, Boolean>
{
    private final Type1 t = Type1.mock();

    public PredicateChainerTest()
    {
        super(true, false);
    }

    @Override
    protected Predicate<Type1> getFallback()
    {
        return mock(Predicate.class);
    }

    @Override
    protected ThrowingPredicate<Type1> getThrowing()
    {
        return mock(ThrowingPredicate.class);
    }

    @Override
    protected PredicateChainer<Type1> getChain(
        final ThrowingPredicate<Type1> throwing)
    {
        return Throwing.predicate(throwing);
    }

    @Override
    protected Callable<Boolean> toCallable(final Predicate<Type1> chain)
    {
        return () -> chain.test(t);
    }

    @Override
    protected void configureFull(final ThrowingPredicate<Type1> throwing)
        throws Throwable
    {
        when(throwing.doTest(t))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(final ThrowingPredicate<Type1> throwing)
        throws Throwable
    {
        when(throwing.doTest(t))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final Predicate<Type1> fallback)
    {
        when(fallback.test(t))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTrueTest()
        throws Throwable
    {
        final ThrowingPredicate<Type1> throwing = getThrowing();
        configureFull(throwing);

        final Predicate<Type1> chain = getChain(throwing).orReturnTrue();

        final Callable<Boolean> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isTrue();

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Test
    public void orReturnFalseTest()
        throws Throwable
    {
        final ThrowingPredicate<Type1> throwing = getThrowing();
        configureFull(throwing);

        final Predicate<Type1> chain = getChain(throwing).orReturnFalse();

        final Callable<Boolean> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

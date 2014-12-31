package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ThrowingInterfaceTest;
import com.github.fge.lambdas.helpers.Type1;

import java.util.concurrent.Callable;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"AutoBoxing", "ProhibitedExceptionDeclared",
    "OverlyBroadThrowsClause"})
public final class ThrowingPredicateTest
    extends ThrowingInterfaceTest<ThrowingPredicate<Type1>, ThrowingPredicate<Type1>, Predicate<Type1>, Boolean>
{
    private final Type1 t = Type1.mock();

    public ThrowingPredicateTest()
    {
        super(SpiedThrowingPredicate::newSpy, () -> mock(Predicate.class), true,
            false);
    }

    @Override
    protected void setupFull(final ThrowingPredicate<Type1> instance)
        throws Throwable
    {
        when(instance.doTest(t)).thenReturn(true).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingPredicate<Type1> instance)
        throws Throwable
    {
        when(instance.doTest(t)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final Predicate<Type1> instance)
    {
        when(instance.test(t)).thenReturn(ret2);
    }

    @Override
    protected Callable<Boolean> asCallable(final Predicate<Type1> instance)
    {
        return () -> instance.test(t);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final Predicate<Type1> instance = getFullInstance().orReturn(false);

        final Callable<Boolean> callable = asCallable(instance);

        assertThat(callable.call()).isTrue();
        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

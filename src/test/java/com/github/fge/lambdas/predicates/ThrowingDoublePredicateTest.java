package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ThrowingInterfaceTest;

import java.util.concurrent.Callable;
import java.util.function.DoublePredicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingDoublePredicateTest
    extends ThrowingInterfaceTest<ThrowingDoublePredicate, ThrowingDoublePredicate, DoublePredicate, Boolean>
{
    private final double value = 16.25;

    public ThrowingDoublePredicateTest()
    {
        super(SpiedThrowingDoublePredicate::newSpy,
            () -> mock(DoublePredicate.class), true, false);
    }

    @Override
    protected void setupFull(final ThrowingDoublePredicate instance)
        throws Throwable
    {
        when(instance.doTest(value)).thenReturn(true).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingDoublePredicate instance)
        throws Throwable
    {
        when(instance.doTest(value)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final DoublePredicate instance)
    {
        when(instance.test(value)).thenReturn(ret2);
    }

    @Override
    protected Callable<Boolean> asCallable(final DoublePredicate instance)
    {
        return () -> instance.test(value);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final DoublePredicate instance = getFullInstance().orReturn(false);

        final Callable<Boolean> callable = asCallable(instance);

        assertThat(callable.call()).isTrue();
        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

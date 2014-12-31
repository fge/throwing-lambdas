package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ThrowingInterfaceTest;

import java.util.concurrent.Callable;
import java.util.function.IntPredicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingIntPredicateTest
    extends ThrowingInterfaceTest<ThrowingIntPredicate, ThrowingIntPredicate, IntPredicate, Boolean>
{
    private final int value = 16;

    public ThrowingIntPredicateTest()
    {
        super(SpiedThrowingIntPredicate::newSpy, () -> mock(IntPredicate.class),
            true, false);
    }

    @Override
    protected void setupFull(final ThrowingIntPredicate instance)
        throws Throwable
    {
        when(instance.doTest(value)).thenReturn(true).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingIntPredicate instance)
        throws Throwable
    {
        when(instance.doTest(value)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final IntPredicate instance)
    {
        when(instance.test(value)).thenReturn(false);
    }

    @Override
    protected Callable<Boolean> asCallable(final IntPredicate instance)
    {
        return () -> instance.test(value);
    }


    public void testChainedWithOrReturn()
        throws Throwable
    {
        final IntPredicate instance = getFullInstance().orReturn(false);

        final Callable<Boolean> callable = asCallable(instance);

        assertThat(callable.call()).isTrue();
        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

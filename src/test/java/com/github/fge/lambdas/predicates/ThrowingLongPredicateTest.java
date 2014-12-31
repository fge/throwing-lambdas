package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.ThrowingInterfaceTest;

import java.util.concurrent.Callable;
import java.util.function.LongPredicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "AutoBoxing",
    "OverlyBroadThrowsClause"})
public final class ThrowingLongPredicateTest
    extends ThrowingInterfaceTest<ThrowingLongPredicate, ThrowingLongPredicate, LongPredicate, Boolean>
{
    private final long value = 0x287981723987L;

    public ThrowingLongPredicateTest()
    {
        super(SpiedThrowingLongPredicate::newSpy,
            () -> mock(LongPredicate.class), true, false);
    }

    @Override
    protected void setupFull(final ThrowingLongPredicate instance)
        throws Throwable
    {
        when(instance.doTest(value)).thenReturn(true).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingLongPredicate instance)
        throws Throwable
    {
        when(instance.doTest(value)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final LongPredicate instance)
    {
        when(instance.test(value)).thenReturn(ret2);
    }

    @Override
    protected Callable<Boolean> asCallable(final LongPredicate instance)
    {
        return () -> instance.test(value);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final LongPredicate instance = getFullInstance().orReturn(false);

        final Callable<Boolean> callable = asCallable(instance);

        assertThat(callable.call()).isTrue();
        assertThat(callable.call()).isFalse();

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

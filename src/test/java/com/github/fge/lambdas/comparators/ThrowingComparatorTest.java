package com.github.fge.lambdas.comparators;

import com.github.fge.lambdas.ThrowingInterfaceTest;
import com.github.fge.lambdas.helpers.Type1;

import java.util.Comparator;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"AutoBoxing", "ProhibitedExceptionDeclared",
    "OverlyBroadThrowsClause"})
public final class ThrowingComparatorTest
    extends ThrowingInterfaceTest<ThrowingComparator<Type1>, ThrowingComparator<Type1>, Comparator<Type1>, Integer>
{
    private final Type1 o1 = Type1.mock();
    private final Type1 o2 = Type1.mock();

    public ThrowingComparatorTest()
    {
        super(SpiedThrowingComparator::newSpy,
            () -> mock(Comparator.class), 42, 24);
    }

    @Override
    protected void setupFull(final ThrowingComparator<Type1> instance)
        throws Throwable
    {
        when(instance.doCompare(o1, o2)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingComparator<Type1> instance)
        throws Throwable
    {
        when(instance.doCompare(o1, o2)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final Comparator<Type1> instance)
    {
        when(instance.compare(o1, o2)).thenReturn(ret2);
    }

    @Override
    protected Callable<Integer> asCallable(final Comparator<Type1> instance)
    {
        return () -> instance.compare(o1, o2);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final Comparator<Type1> instance = getFullInstance().orReturn(ret2);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

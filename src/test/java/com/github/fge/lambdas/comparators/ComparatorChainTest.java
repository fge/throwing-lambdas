package com.github.fge.lambdas.comparators;

import com.github.fge.lambdas.ChainTest;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class ComparatorChainTest
    extends ChainTest<Comparator<Type1>, ThrowingComparator<Type1>, ComparatorChain<Type1>, Integer>
{
    private final Type1 o1 = Type1.mock();
    private final Type1 o2 = Type1.mock();

    public ComparatorChainTest()
    {
        super(42, 24);
    }

    @Override
    protected Comparator<Type1> getFallback()
    {
        return mock(Comparator.class);
    }

    @Override
    protected ThrowingComparator<Type1> getThrowing()
    {
        return mock(ThrowingComparator.class);
    }

    @Override
    protected ComparatorChain<Type1> getChain(
        final ThrowingComparator<Type1> throwing)
    {
        return new ComparatorChain<>(throwing);
    }

    @Override
    protected Callable<Integer> toCallable(final Comparator<Type1> chain)
    {
        return () -> chain.compare(o1, o2);
    }

    @Override
    protected void configureFull(final ThrowingComparator<Type1> throwing)
        throws Throwable
    {
        when(throwing.doCompare(o1, o2))
            .thenReturn(ret1)
            .thenThrow(checked)
            .thenThrow(unchecked)
            .thenThrow(error);
    }

    @Override
    protected void configureAlternate(final ThrowingComparator<Type1> throwing)
        throws Throwable
    {
        when(throwing.doCompare(o1, o2))
            .thenReturn(ret2);
    }

    @Override
    protected void configureFallback(final Comparator<Type1> fallback)
    {
        when(fallback.compare(o1, o2))
            .thenReturn(ret2);
    }

    @Test
    public void orReturnTest()
        throws Throwable
    {
        final ThrowingComparator<Type1> throwing = getThrowing();
        configureFull(throwing);

        final Comparator<Type1> comparator = getChain(throwing).orReturn(ret2);

        final Callable<Integer> callable = toCallable(comparator);

        assertThat(callable.call()).isSameAs(ret1);

        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

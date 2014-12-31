package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowingInterfaceTest;
import com.github.fge.lambdas.helpers.Type1;

import java.util.concurrent.Callable;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "OverlyBroadThrowsClause"})
public final class ThrowingUnaryOperatorTest
    extends ThrowingInterfaceTest<ThrowingUnaryOperator<Type1>, ThrowingUnaryOperator<Type1>, UnaryOperator<Type1>, Type1>
{
    private final Type1 t = Type1.mock();

    public ThrowingUnaryOperatorTest()
    {
        super(SpiedThrowingUnaryOperator::newSpy,
            () -> mock(UnaryOperator.class), Type1.mock(), Type1.mock());
    }

    @Override
    protected void setupFull(final ThrowingUnaryOperator<Type1> instance)
        throws Throwable
    {
        when(instance.doApply(t)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingUnaryOperator<Type1> instance)
        throws Throwable
    {
        when(instance.doApply(t)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final UnaryOperator<Type1> instance)
    {
        when(instance.apply(t)).thenReturn(ret2);
    }

    @Override
    protected Callable<Type1> asCallable(final UnaryOperator<Type1> instance)
    {
        return () -> instance.apply(t);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final UnaryOperator<Type1> instance = getFullInstance().orReturn(ret2);

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnSelf()
        throws Throwable
    {
        final UnaryOperator<Type1> instance = getFullInstance().orReturnSelf();

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(t);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

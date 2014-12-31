package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowingInterfaceTest;
import com.github.fge.lambdas.helpers.Type1;

import java.util.concurrent.Callable;
import java.util.function.BinaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "OverlyBroadThrowsClause"})
public final class ThrowingBinaryOperatorTest
    extends ThrowingInterfaceTest<ThrowingBinaryOperator<Type1>, ThrowingBinaryOperator<Type1>, BinaryOperator<Type1>, Type1>
{
    private final Type1 t = Type1.mock();
    private final Type1 u = Type1.mock();

    public ThrowingBinaryOperatorTest()
    {
        super(SpiedThrowingBinaryOperator::newSpy,
            () -> mock(BinaryOperator.class), Type1.mock(), Type1.mock());
    }

    @Override
    protected void setupFull(final ThrowingBinaryOperator<Type1> instance)
        throws Throwable
    {
        when(instance.doApply(t, u)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);
    }

    @Override
    protected void setupAlternate(final ThrowingBinaryOperator<Type1> instance)
        throws Throwable
    {
        when(instance.doApply(t, u)).thenReturn(ret2);
    }

    @Override
    protected void setupFallback(final BinaryOperator<Type1> instance)
    {
        when(instance.apply(t, u)).thenReturn(ret2);
    }

    @Override
    protected Callable<Type1> asCallable(final BinaryOperator<Type1> instance)
    {
        return () -> instance.apply(t, u);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final BinaryOperator<Type1> instance = getFullInstance().orReturn(ret2);

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnLeft()
        throws Throwable
    {
        final BinaryOperator<Type1> instance = getFullInstance().orReturnLeft();

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(t);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnRight()
        throws Throwable
    {
        final BinaryOperator<Type1> instance
            = getFullInstance().orReturnRight();

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(u);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

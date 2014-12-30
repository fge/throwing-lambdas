package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;

import java.util.concurrent.Callable;
import java.util.function.BinaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "OverlyBroadThrowsClause"})
public final class ThrowingBinaryOperatorTest
    extends ThrowingInterfaceBaseTest<ThrowingBinaryOperator<Type1>, BinaryOperator<Type1>, Type1>
{
    private final Type1 left = Type1.mock();
    private final Type1 right = Type1.mock();

    public ThrowingBinaryOperatorTest()
    {
        super(Type1.mock(), Type1.mock());
    }

    @Override
    protected ThrowingBinaryOperator<Type1> getAlternate()
        throws Throwable
    {
        final ThrowingBinaryOperator<Type1> spy =
            SpiedThrowingBinaryOperator.newSpy();

        when(spy.doApply(left, right)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingBinaryOperator<Type1> getTestInstance()
        throws Throwable
    {
        final ThrowingBinaryOperator<Type1> spy
            = SpiedThrowingBinaryOperator.newSpy();

        when(spy.doApply(left, right)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected BinaryOperator<Type1> getFallback()
    {
        @SuppressWarnings("unchecked")
        final BinaryOperator<Type1> mock = mock(BinaryOperator.class);

        when(mock.apply(left, right)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Callable<Type1> asCallable(final BinaryOperator<Type1> instance)
    {
        return () -> instance.apply(left, right);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingBinaryOperator<Type1> instance = getTestInstance();

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, ThrownByLambdaException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final BinaryOperator<Type1> instance
            = getTestInstance().orThrow(MyException.class);

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, MyException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingBinaryOperator<Type1> first = getTestInstance();
        final ThrowingBinaryOperator<Type1> second = getAlternate();

        final BinaryOperator<Type1> instance = first.orTryWith(second);

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingBinaryOperator<Type1> first = getTestInstance();
        final BinaryOperator<Type1> second = getFallback();

        final BinaryOperator<Type1> instance = first.fallbackTo(second);

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final BinaryOperator<Type1> instance
            = getTestInstance().orReturn(ret2);

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnLeft()
        throws Throwable
    {
        final BinaryOperator<Type1> instance
            = getTestInstance().orReturnLeft();

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(left);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnRight()
        throws Throwable
    {
        final BinaryOperator<Type1> instance
            = getTestInstance().orReturnRight();

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(right);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

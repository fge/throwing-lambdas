package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;

import java.util.concurrent.Callable;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "OverlyBroadThrowsClause"})
public final class ThrowingUnaryOperatorTest
    extends ThrowingInterfaceBaseTest<ThrowingUnaryOperator<Type1>, UnaryOperator<Type1>, Type1>
{
    private final Type1 arg = Type1.mock();

    public ThrowingUnaryOperatorTest()
    {
        super(Type1.mock(), Type1.mock());
    }

    @Override
    protected ThrowingUnaryOperator<Type1> getAlternate()
        throws Throwable
    {
        final ThrowingUnaryOperator<Type1> spy =
            SpiedThrowingUnaryOperator.newSpy();

        when(spy.doApply(arg)).thenReturn(ret2);

        return spy;
    }

    @Override
    protected ThrowingUnaryOperator<Type1> getTestInstance()
        throws Throwable
    {
        final ThrowingUnaryOperator<Type1> spy
            = SpiedThrowingUnaryOperator.newSpy();

        when(spy.doApply(arg)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected UnaryOperator<Type1> getFallback()
    {
        @SuppressWarnings("unchecked")
        final UnaryOperator<Type1> mock = mock(UnaryOperator.class);

        when(mock.apply(arg)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Callable<Type1> asCallable(final UnaryOperator<Type1> instance)
    {
        return () -> instance.apply(arg);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingUnaryOperator<Type1> instance = getTestInstance();

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
        final UnaryOperator<Type1> instance
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
        final ThrowingUnaryOperator<Type1> first = getTestInstance();
        final ThrowingUnaryOperator<Type1> second = getAlternate();

        final UnaryOperator<Type1> instance = first.orTryWith(second);

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
        final ThrowingUnaryOperator<Type1> first = getTestInstance();
        final UnaryOperator<Type1> second = getFallback();

        final UnaryOperator<Type1> instance = first.fallbackTo(second);

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final UnaryOperator<Type1> instance
            = getTestInstance().orReturn(ret2);

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturnSelf()
        throws Throwable
    {
        final UnaryOperator<Type1> instance
            = getTestInstance().orReturnSelf();

        final Callable<Type1> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(arg);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

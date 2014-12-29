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
    private final Type1 ret1 = Type1.mock();
    private final Type1 ret2 = Type1.mock();

    @Override
    protected ThrowingBinaryOperator<Type1> getBaseInstance()
    {
        return SpiedThrowingBinaryOperator.newSpy();
    }

    @Override
    protected ThrowingBinaryOperator<Type1> getPreparedInstance()
        throws Throwable
    {
        final ThrowingBinaryOperator<Type1> spy = getBaseInstance();

        when(spy.doApply(left, right)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected BinaryOperator<Type1> getNonThrowingInstance()
    {
        //noinspection unchecked
        return mock(BinaryOperator.class);
    }

    @Override
    protected Runnable runnableFrom(final BinaryOperator<Type1> instance)
    {
        return () -> instance.apply(left, right);
    }

    @Override
    protected Callable<Type1> callableFrom(final BinaryOperator<Type1> instance)
    {
        return () -> instance.apply(left, right);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingBinaryOperator<Type1> instance = getPreparedInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final BinaryOperator<Type1> instance
            = getPreparedInstance().orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingBinaryOperator<Type1> first = getPreparedInstance();
        final ThrowingBinaryOperator<Type1> second = getBaseInstance();
        when(second.apply(left, right)).thenReturn(ret2);

        final BinaryOperator<Type1> instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingBinaryOperator<Type1> first = getPreparedInstance();
        final BinaryOperator<Type1> second = getNonThrowingInstance();
        when(second.apply(left, right)).thenReturn(ret2);

        final BinaryOperator<Type1> instance = first.fallbackTo(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final BinaryOperator<Type1> instance
            = getPreparedInstance().orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturnLeft()
        throws Throwable
    {
        final BinaryOperator<Type1> instance
            = getPreparedInstance().orReturnLeft();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(left);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturnRight()
        throws Throwable
    {
        final BinaryOperator<Type1> instance
            = getPreparedInstance().orReturnRight();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(right);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

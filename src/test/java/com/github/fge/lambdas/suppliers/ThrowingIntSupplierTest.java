package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;

import java.util.concurrent.Callable;
import java.util.function.IntSupplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SuppressWarnings({"AutoBoxing", "OverlyBroadThrowsClause",
    "ProhibitedExceptionDeclared"})
public final class ThrowingIntSupplierTest
        extends ThrowingInterfaceBaseTest<ThrowingIntSupplier, IntSupplier, Integer>
{

    private final int ret1 = 42; // Arbitrarily random, also The Answer.
    private final int ret2 = 24; // Opposite of The Answer.

    @Override
    protected ThrowingIntSupplier getBaseInstance()
    {
        return SpiedThrowingIntSupplier.newSpy();
    }

    @Override
    protected ThrowingIntSupplier getPreparedInstance()
        throws Throwable
    {
        final ThrowingIntSupplier spy = getBaseInstance();

        when(spy.doGetAsInt()).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected IntSupplier getNonThrowingInstance()
    {
        return mock(IntSupplier.class);
    }

    @Override
    protected Runnable runnableFrom(final IntSupplier instance)
    {
        return instance::getAsInt;
    }

    @Override
    protected Callable<Integer> callableFrom(final IntSupplier instance)
    {
        return instance::getAsInt;
    }

    @Override
    public void testUnchained()
            throws Throwable
    {
        final ThrowingIntSupplier instance = getPreparedInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
            throws Throwable
    {
        final IntSupplier instance
                = getPreparedInstance().orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
            throws Throwable
    {
        final ThrowingIntSupplier first = getPreparedInstance();
        final ThrowingIntSupplier second = getBaseInstance();
        when(second.doGetAsInt()).thenReturn(ret2);

        final IntSupplier instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOr()
            throws Throwable
    {
        final ThrowingIntSupplier first = getPreparedInstance();
        final IntSupplier second = getNonThrowingInstance();
        when(second.getAsInt()).thenReturn(ret2);

        final IntSupplier instance = first.or(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
            throws Throwable
    {
        final ThrowingIntSupplier first = getPreparedInstance();

        final IntSupplier instance = first.orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Integer> callable = callableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

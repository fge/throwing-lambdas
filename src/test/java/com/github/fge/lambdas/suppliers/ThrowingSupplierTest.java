package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ProhibitedExceptionDeclared", "unchecked",
    "OverlyBroadThrowsClause"})
public final class ThrowingSupplierTest
    extends ThrowingInterfaceBaseTest<ThrowingSupplier<Type1>, Supplier<Type1>, Type1>
{
    private final Type1 ret1 = Type1.mock();
    private final Type1 ret2 = Type1.mock();

    @Override
    protected ThrowingSupplier<Type1> getAlternate()
    {
        return SpiedThrowingSupplier.newSpy();
    }

    @Override
    protected ThrowingSupplier<Type1> getPreparedInstance()
        throws Throwable
    {
        final ThrowingSupplier<Type1> spy = getAlternate();

        when(spy.doGet()).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected Supplier<Type1> getFallbackInstance()
    {
        return mock(Supplier.class);
    }

    @Override
    protected Runnable runnableFrom(final Supplier<Type1> instance)
    {
        return instance::get;
    }

    @Override
    protected Callable<Type1> callableFrom(final Supplier<Type1> instance)
    {
        return instance::get;
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingSupplier<Type1> instance = getPreparedInstance();

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = callableFrom(instance);

        assertThat(callable.call()).isSameAs(ret1);

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final Supplier<Type1> instance
            = getPreparedInstance().orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = callableFrom(instance);

        assertThat(callable.call()).isSameAs(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingSupplier<Type1> first = getPreparedInstance();
        final ThrowingSupplier<Type1> second = getAlternate();
        when(second.doGet()).thenReturn(ret2);

        final Supplier<Type1> instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = callableFrom(instance);

        assertThat(callable.call()).isSameAs(ret1);

        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingSupplier<Type1> first = getPreparedInstance();
        final Supplier<Type1> second = getFallbackInstance();
        when(second.get()).thenReturn(ret2);

        final Supplier<Type1> instance = first.fallbackTo(second);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = callableFrom(instance);

        assertThat(callable.call()).isSameAs(ret1);
        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final ThrowingSupplier<Type1> first = getPreparedInstance();

        final Supplier<Type1> instance = first.orReturn(ret2);

        final Runnable runnable = runnableFrom(instance);
        final Callable<Type1> callable = callableFrom(instance);

        assertThat(callable.call()).isSameAs(ret1);
        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

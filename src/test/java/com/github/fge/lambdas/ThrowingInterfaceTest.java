package com.github.fge.lambdas;

import com.github.fge.lambdas.helpers.MyException;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"ProhibitedExceptionDeclared", "OverlyBroadThrowsClause"})
@Test
public abstract class ThrowingInterfaceTest<E extends N, T extends ThrowingFunctionalInterface<E, N>, N, R>
{
    protected final Exception checked = new Exception();
    protected final RuntimeException unchecked = new RuntimeException();
    protected final Error error = new Error();

    protected final Supplier<T> supplier;
    protected final Supplier<N> fallbackSupplier;
    protected final R ret1;
    protected final R ret2;

    protected ThrowingInterfaceTest(final Supplier<T> supplier,
        final Supplier<N> fallbackSupplier, final R ret1, final R ret2)
    {
        this.supplier = supplier;
        this.fallbackSupplier = fallbackSupplier;
        this.ret1 = ret1;
        this.ret2 = ret2;
    }

    protected abstract void setupFull(T instance)
        throws Throwable;

    protected abstract void setupAlternate(T instance)
        throws Throwable;

    protected abstract void setupFallback(N instance);

    protected abstract Callable<R> asCallable(N instance);

    protected final T getFullInstance()
        throws Throwable
    {
        final T instance = supplier.get();

        setupFull(instance);

        return instance;
    }

    private T getAlternateInstance()
        throws Throwable
    {
        final T instance = supplier.get();

        setupAlternate(instance);

        return instance;
    }

    private N getFallbackInstance()
    {
        final N instance = fallbackSupplier.get();

        setupFallback(instance);

        return instance;
    }

    public final void testUnchained()
        throws Throwable
    {
        @SuppressWarnings("unchecked")
        final N instance = (N) getFullInstance();

        final Callable<R> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, ThrownByLambdaException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public final void testChainedWithOrThrow()
        throws Throwable
    {
        final N instance = getFullInstance().orThrow(MyException.class);

        final Callable<R> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, MyException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public final void testChainedWithOrTryWith()
        throws Throwable
    {
        final T first = getFullInstance();
        @SuppressWarnings("unchecked")
        final E second = (E) getAlternateInstance();

        final N instance = first.orTryWith(second);

        final Callable<R> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public final void testChainedWithFallbackTo()
        throws Throwable
    {
        final T first = getFullInstance();
        final N second = getFallbackInstance();

        final N instance = first.fallbackTo(second);

        final Callable<R> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @SuppressWarnings("OverlyBroadCatchBlock")
    protected final void verifyCheckedRethrow(final Callable<R> callable,
        final Class<? extends Throwable> exceptionClass)
        throws Exception
    {
        try {
            callable.call();
            shouldHaveThrown(exceptionClass);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(exceptionClass);
            assertThat(e.getCause()).isSameAs(checked);
        }
    }

    @SuppressWarnings("OverlyBroadCatchBlock")
    protected final void verifyUncheckedThrow(final Callable<R> callable)
        throws Exception
    {
        try {
            callable.call();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(unchecked);
        }
    }

    @SuppressWarnings("OverlyBroadCatchBlock")
    protected final void verifyErrorThrow(final Callable<R> callable)
        throws Exception
    {
        try {
            callable.call();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(error);
        }
    }
}

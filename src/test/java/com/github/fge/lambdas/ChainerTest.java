package com.github.fge.lambdas;

import com.github.fge.lambdas.helpers.MyException;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;

import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static org.assertj.core.api.Assertions.assertThat;

@Test
public abstract class ChainerTest<N, T extends N, C extends Chainer<N, T, C>, R>
{
    protected final Exception checked = new Exception();
    protected final RuntimeException unchecked = new RuntimeException();
    protected final Error error = new Error();

    protected final R ret1;
    protected final R ret2;

    public ChainerTest(final R ret1, final R ret2)
    {
        this.ret1 = ret1;
        this.ret2 = ret2;
    }

    protected abstract N getFallback();

    protected abstract T getThrowing();

    protected abstract C getChain(T throwing);

    protected abstract Callable<R> toCallable(N chain);

    protected abstract void configureFull(T throwing)
        throws Throwable;

    protected abstract void configureAlternate(T throwing)
        throws Throwable;

    protected abstract void configureFallback(N fallback);

    @Test
    public final void baseChainTest()
        throws Throwable
    {
        final T throwing = getThrowing();
        final C chain = getChain(throwing);
        configureFull(throwing);

        final Callable<R> callable = toCallable((N) chain);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, ThrownByLambdaException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Test
    public final void orTryWithTest()
        throws Throwable
    {
        final T throwing = getThrowing();
        configureFull(throwing);
        final T alternate = getThrowing();
        configureAlternate(alternate);

        final C chain = getChain(throwing).orTryWith(alternate);

        final Callable<R> callable = toCallable((N) chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Test
    public final void fallbackToTest()
        throws Throwable
    {
        final T throwing = getThrowing();
        configureFull(throwing);
        final N fallback = getFallback();
        configureFallback(fallback);

        final N chain = getChain(throwing).fallbackTo(fallback);

        final Callable<R> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Test
    public final void orThrowTest()
        throws Throwable
    {
        final T throwing = getThrowing();
        configureFull(throwing);

        final T chain = getChain(throwing).orThrow(MyException.class);

        final Callable<R> callable = toCallable(chain);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, MyException.class);

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

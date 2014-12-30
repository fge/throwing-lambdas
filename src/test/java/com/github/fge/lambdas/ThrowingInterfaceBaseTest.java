package com.github.fge.lambdas;

import org.testng.annotations.Test;

import java.util.concurrent.Callable;

import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @param <T> type of the Throwing interface
 * @param <N> type of the non Throwing interface
 * @param <R> type of the result from the interface
 */
@SuppressWarnings("ProhibitedExceptionDeclared")
@Test
public abstract class ThrowingInterfaceBaseTest<T extends N, N, R>
{
    protected final Exception checked = new Exception();
    protected final RuntimeException unchecked = new RuntimeException();
    protected final Error error = new Error();

    protected final R ret1;
    protected final R ret2;

    protected ThrowingInterfaceBaseTest(final R ret1, final R ret2)
    {
        this.ret1 = ret1;
        this.ret2 = ret2;
    }

    /**
     * Return an alternate mock for use with {@link
     * ThrowingFunctionalInterface#orTryWith(Object)}
     *
     * @return a mock
     */
    protected abstract T getAlternate()
        throws Throwable;

    /**
     * Return a fully prepared mock
     *
     * <p>This mock should be a Mockito spy() and chain:</p>
     *
     * <ul>
     *     <li>returning a result, if any;</li>
     *     <li>throwing a checked exception;</li>
     *     <li>throwing an unchecked exception;</li>
     *     <li>throwing an error.</li>
     * </ul>
     *
     * @return a mock
     */
    protected abstract T getTestInstance()
        throws Throwable;

    /**
     * Return a mock of the non throwing version of the interface for use in
     * {@link ThrowingFunctionalInterface#fallbackTo(Object)}
     *
     * <p>A mock of the interface itself is enough. No need to stub (it will
     * only be verify()ed).</p>
     *
     * @return a mock
     */
    protected abstract N getFallback();

    /**
     * Return a runnable from running an instance
     *
     * <p>Used to check for exceptions</p>
     *
     * @param instance the instance
     * @return a runnable
     */
    protected abstract Runnable runnableFrom(N instance);

    /**
     * Return a callable from running an instance
     *
     * <p>Used to check results.</p>
     *
     * @param instance the instance
     * @return a callable
     */
    protected abstract Callable<R> asCallable(N instance);

    /**
     * Test a fully prepared, unchained instance
     */
    @Test
    public abstract void testUnchained()
        throws Throwable;

    /**
     * Test a fully prepared instance chained with .orThrow()
     */
    @Test
    public abstract void testChainedWithOrThrow()
        throws Throwable;

    /**
     * Test a fully prepared instance chained with .orTryWith()
     */
    @Test
    public abstract void testChainedWithOrTryWith()
        throws Throwable;

    /**
     * Test a fully prepared instance chained with .or()
     */
    @Test
    public abstract void testChainedWithFallbackTo()
        throws Throwable;

    /*
     * Here, add tests with all of the interface's specific chaining methods
     * (.orReturn(), etc etc)
     */

    /*
     * Exception checking methods
     */
    protected final void verifyCheckedRethrow(final Runnable runnable,
        final Class<? extends Throwable> exceptionClass)
    {
        try {
            runnable.run();
            shouldHaveThrown(exceptionClass);
        } catch (Throwable e) {
            assertThat(e).isExactlyInstanceOf(exceptionClass);
            assertThat(e.getCause()).isSameAs(checked);
        }
    }

    protected final void verifyUncheckedThrow(final Runnable runnable)
    {
        try {
            runnable.run();
            shouldHaveThrown(RuntimeException.class);
        } catch (Throwable e) {
            assertThat(e).isSameAs(unchecked);
        }
    }

    protected final void verifyErrorThrow(final Runnable runnable)
    {
        try {
            runnable.run();
            shouldHaveThrown(Error.class);
        } catch (Throwable e) {
            assertThat(e).isSameAs(error);
        }
    }
}

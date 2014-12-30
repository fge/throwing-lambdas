package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.LongConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@SuppressWarnings({"ProhibitedExceptionDeclared", "OverlyBroadThrowsClause",
    "AutoBoxing"})
public final class ThrowingLongConsumerTest
    extends ThrowingInterfaceBaseTest<ThrowingLongConsumer, LongConsumer, Integer>
{
    private final long arg = 42L;

    private final AtomicInteger sentinel = new AtomicInteger(0);

    public ThrowingLongConsumerTest()
    {
        super(42, 24);
    }

    @BeforeMethod
    public void resetSentinel()
    {
        sentinel.set(0);
    }

    @Override
    protected ThrowingLongConsumer getAlternate()
        throws Throwable
    {
        final ThrowingLongConsumer spy = SpiedThrowingLongConsumer.newSpy();

        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(spy).doAccept(arg);

        return spy;
    }

    @Override
    protected ThrowingLongConsumer getPreparedInstance()
        throws Throwable
    {
        final ThrowingLongConsumer spy = SpiedThrowingLongConsumer.newSpy();

        doAnswer(invocation -> { sentinel.set(ret1); return null; })
            .doThrow(checked).doThrow(unchecked).doThrow(error)
            .when(spy).doAccept(arg);

        return spy;
    }

    @Override
    protected LongConsumer getFallbackInstance()
    {
        final LongConsumer mock = mock(LongConsumer.class);

        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(mock).accept(arg);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final LongConsumer instance)
    {
        return () -> instance.accept(arg);
    }

    @Override
    protected Callable<Integer> callableFrom(final LongConsumer instance)
    {
        return () -> { instance.accept(arg); return sentinel.get(); };
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingLongConsumer instance = getPreparedInstance();

        final Callable<Integer> callable = callableFrom(instance);
        final Runnable runnable = runnableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final ThrowingLongConsumer spy = getPreparedInstance();

        final LongConsumer instance = spy.orThrow(MyException.class);

        final Callable<Integer> callable = callableFrom(instance);
        final Runnable runnable = runnableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingLongConsumer first = getPreparedInstance();
        final ThrowingLongConsumer second = getAlternate();

        final LongConsumer instance = first.orTryWith(second);

        final Callable<Integer> callable = callableFrom(instance);
        final Runnable runnable = runnableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingLongConsumer first = getPreparedInstance();
        final LongConsumer second = getFallbackInstance();

        final LongConsumer instance = first.fallbackTo(second);

        final Callable<Integer> callable = callableFrom(instance);
        final Runnable runnable = runnableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithDoNothing()
        throws Throwable
    {
        final ThrowingLongConsumer first = getPreparedInstance();

        final LongConsumer instance = first.orDoNothing();

        final Callable<Integer> callable = callableFrom(instance);
        final Runnable runnable = runnableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret1);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

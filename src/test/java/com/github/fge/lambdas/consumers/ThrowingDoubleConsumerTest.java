package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.DoubleConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@SuppressWarnings({"ProhibitedExceptionDeclared", "OverlyBroadThrowsClause",
    "AutoBoxing"})
public final class ThrowingDoubleConsumerTest
    extends ThrowingInterfaceBaseTest<ThrowingDoubleConsumer, DoubleConsumer, Integer>
{
    private final double arg = 0.125;

    private final AtomicInteger sentinel = new AtomicInteger(0);

    public ThrowingDoubleConsumerTest()
    {
        super(42, 24);
    }

    @BeforeMethod
    public void resetSentinel()
    {
        sentinel.set(0);
    }

    @Override
    protected ThrowingDoubleConsumer getAlternate()
        throws Throwable
    {
        final ThrowingDoubleConsumer spy
            =  SpiedThrowingDoubleConsumer.newSpy();

        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(spy).doAccept(arg);

        return spy;
    }

    @Override
    protected ThrowingDoubleConsumer getPreparedInstance()
        throws Throwable
    {
        final ThrowingDoubleConsumer spy
            = SpiedThrowingDoubleConsumer.newSpy();

        doAnswer(invocation -> { sentinel.set(ret1); return null; })
            .doThrow(checked).doThrow(unchecked).doThrow(error)
            .when(spy).doAccept(arg);

        return spy;
    }

    @Override
    protected DoubleConsumer getFallbackInstance()
    {
        final DoubleConsumer mock = mock(DoubleConsumer.class);

        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(mock).accept(arg);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final DoubleConsumer instance)
    {
        return () -> instance.accept(arg);
    }

    @Override
    protected Callable<Integer> callableFrom(final DoubleConsumer instance)
    {
        return () -> { instance.accept(arg); return sentinel.get(); };
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingDoubleConsumer instance = getPreparedInstance();

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
        final ThrowingDoubleConsumer spy = getPreparedInstance();

        final DoubleConsumer instance = spy.orThrow(MyException.class);

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
        final ThrowingDoubleConsumer first = getPreparedInstance();
        final ThrowingDoubleConsumer second = getAlternate();

        final DoubleConsumer instance = first.orTryWith(second);

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
        final ThrowingDoubleConsumer first = getPreparedInstance();
        final DoubleConsumer second = getFallbackInstance();

        final DoubleConsumer instance = first.fallbackTo(second);

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
        final ThrowingDoubleConsumer first = getPreparedInstance();

        final DoubleConsumer instance = first.orDoNothing();

        final Callable<Integer> callable = callableFrom(instance);
        final Runnable runnable = runnableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret1);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

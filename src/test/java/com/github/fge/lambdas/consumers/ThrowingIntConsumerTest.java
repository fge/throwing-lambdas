package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@SuppressWarnings({"ProhibitedExceptionDeclared", "OverlyBroadThrowsClause",
    "AutoBoxing"})
public final class ThrowingIntConsumerTest
    extends ThrowingInterfaceBaseTest<ThrowingIntConsumer, IntConsumer, Integer>
{
    private final int arg = 42;

    private final AtomicInteger sentinel = new AtomicInteger(0);

    public ThrowingIntConsumerTest()
    {
        super(42, 24);
    }

    @BeforeMethod
    public void resetSentinel()
    {
        sentinel.set(0);
    }

    @Override
    protected ThrowingIntConsumer getAlternate()
        throws Throwable
    {
        final ThrowingIntConsumer spy = SpiedThrowingIntConsumer.newSpy();

        doAnswer(invocation -> { sentinel.set(ret2); return  null; })
            .when(spy).doAccept(arg);

        return spy;
    }

    @Override
    protected ThrowingIntConsumer getTestInstance()
        throws Throwable
    {
        final ThrowingIntConsumer spy = SpiedThrowingIntConsumer.newSpy();

        doAnswer(invocation -> { sentinel.set(ret1); return null; })
            .doThrow(checked).doThrow(unchecked).doThrow(error)
            .when(spy).doAccept(arg);

        return spy;
    }

    @Override
    protected IntConsumer getFallback()
    {
        final IntConsumer mock = mock(IntConsumer.class);

        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(mock).accept(arg);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final IntConsumer instance)
    {
        return () -> instance.accept(arg);
    }

    @Override
    protected Callable<Integer> asCallable(final IntConsumer instance)
    {
        return () -> { instance.accept(arg); return sentinel.get(); };
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingIntConsumer instance = getTestInstance();

        final Callable<Integer> callable = asCallable(instance);
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
        final ThrowingIntConsumer spy = getTestInstance();

        final IntConsumer instance = spy.orThrow(MyException.class);

        final Callable<Integer> callable = asCallable(instance);
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
        final ThrowingIntConsumer first = getTestInstance();
        final ThrowingIntConsumer second = getAlternate();

        final IntConsumer instance = first.orTryWith(second);

        final Callable<Integer> callable = asCallable(instance);
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
        final ThrowingIntConsumer first = getTestInstance();
        final IntConsumer second = getFallback();

        final IntConsumer instance = first.fallbackTo(second);

        final Callable<Integer> callable = asCallable(instance);
        final Runnable runnable = runnableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithDoNothing()
        throws Throwable
    {
        final ThrowingIntConsumer first = getTestInstance();

        final IntConsumer instance = first.orDoNothing();

        final Callable<Integer> callable = asCallable(instance);
        final Runnable runnable = runnableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret1);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

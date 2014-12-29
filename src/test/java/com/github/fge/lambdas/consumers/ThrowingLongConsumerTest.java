package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import org.mockito.InOrder;

import java.util.concurrent.Callable;
import java.util.function.LongConsumer;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SuppressWarnings("ProhibitedExceptionDeclared")
public final class ThrowingLongConsumerTest
    extends ThrowingInterfaceBaseTest<ThrowingLongConsumer, LongConsumer, Void>
{
    private final long arg = 42;

    @Override
    protected ThrowingLongConsumer getBaseInstance()
    {
        return SpiedThrowingLongConsumer.newSpy();
    }

    @Override
    protected ThrowingLongConsumer getPreparedInstance()
        throws Throwable
    {
        final ThrowingLongConsumer spy = getBaseInstance();

        doNothing().doThrow(checked).doThrow(unchecked).doThrow(error)
            .when(spy).doAccept(arg);

        return spy;
    }

    @Override
    protected LongConsumer getNonThrowingInstance()
    {
        return mock(LongConsumer.class);
    }

    @Override
    protected Runnable runnableFrom(final LongConsumer instance)
    {
        return () -> instance.accept(arg);
    }

    @Override
    protected Callable<Void> callableFrom(final LongConsumer instance)
    {
        return () -> {
            instance.accept(arg);
            return null;
        };
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingLongConsumer instance = getPreparedInstance();

        final Runnable runnable = runnableFrom(instance);

        runnable.run();

        verify(instance).doAccept(arg);

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

        final Runnable runnable = runnableFrom(instance);

        runnable.run();

        verify(spy).doAccept(arg);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingLongConsumer first = getPreparedInstance();
        final ThrowingLongConsumer second = getBaseInstance();

        final LongConsumer instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);

        final InOrder inOrder = inOrder(first, second);

        runnable.run();
        runnable.run();

        inOrder.verify(first, times(2)).doAccept(arg);
        inOrder.verify(second).doAccept(arg);
        inOrder.verifyNoMoreInteractions();

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingLongConsumer first = getPreparedInstance();
        final LongConsumer second = getNonThrowingInstance();

        final LongConsumer instance = first.fallbackTo(second);

        final Runnable runnable = runnableFrom(instance);

        final InOrder inOrder = inOrder(first, second);

        runnable.run();
        runnable.run();

        inOrder.verify(first, times(2)).doAccept(arg);
        inOrder.verify(second).accept(arg);
        inOrder.verifyNoMoreInteractions();

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithDoNothing()
        throws Throwable
    {
        final ThrowingLongConsumer first = getPreparedInstance();

        final LongConsumer instance = first.orDoNothing();

        final Runnable runnable = runnableFrom(instance);

        runnable.run();
        runnable.run();

        verify(first).orDoNothing();
        verify(first, times(2)).doAccept(arg);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import org.mockito.InOrder;

import java.util.concurrent.Callable;
import java.util.function.IntConsumer;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SuppressWarnings("ProhibitedExceptionDeclared")
public final class ThrowingIntConsumerTest
    extends ThrowingInterfaceBaseTest<ThrowingIntConsumer, IntConsumer, Void>
{
    private final int arg = 42;

    @Override
    protected ThrowingIntConsumer getBaseInstance()
    {
        return SpiedThrowingIntConsumer.newSpy();
    }

    @Override
    protected ThrowingIntConsumer getPreparedInstance()
        throws Throwable
    {
        final ThrowingIntConsumer spy = getBaseInstance();

        doNothing().doThrow(checked).doThrow(unchecked).doThrow(error)
            .when(spy).doAccept(arg);

        return spy;
    }

    @Override
    protected IntConsumer getNonThrowingInstance()
    {
        return mock(IntConsumer.class);
    }

    @Override
    protected Runnable runnableFrom(final IntConsumer instance)
    {
        return () -> instance.accept(arg);
    }

    @Override
    protected Callable<Void> callableFrom(final IntConsumer instance)
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
        final ThrowingIntConsumer instance = getPreparedInstance();

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
        final ThrowingIntConsumer spy = getPreparedInstance();

        final IntConsumer instance = spy.orThrow(MyException.class);

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
        final ThrowingIntConsumer first = getPreparedInstance();
        final ThrowingIntConsumer second = getBaseInstance();

        final IntConsumer instance = first.orTryWith(second);

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
    public void testChainedWithOr()
        throws Throwable
    {
        final ThrowingIntConsumer first = getPreparedInstance();
        final IntConsumer second = getNonThrowingInstance();

        final IntConsumer instance = first.or(second);

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
        final ThrowingIntConsumer first = getPreparedInstance();

        final IntConsumer instance = first.orDoNothing();

        final Runnable runnable = runnableFrom(instance);

        runnable.run();
        runnable.run();

        verify(first).orDoNothing();
        verify(first, times(2)).doAccept(arg);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

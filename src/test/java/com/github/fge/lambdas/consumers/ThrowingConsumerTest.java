package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;
import org.mockito.InOrder;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SuppressWarnings("ProhibitedExceptionDeclared")
public final class ThrowingConsumerTest
    extends ThrowingInterfaceBaseTest<ThrowingConsumer<Type1>, Consumer<Type1>, Void>
{
    private final Type1 arg = Type1.mock();

    @Override
    protected ThrowingConsumer<Type1> getBaseInstance()
    {
        return SpiedThrowingConsumer.newSpy();
    }

    @Override
    protected ThrowingConsumer<Type1> getPreparedInstance()
        throws Throwable
    {
        final ThrowingConsumer<Type1> spy = getBaseInstance();

        doNothing().doThrow(checked).doThrow(unchecked).doThrow(error)
            .when(spy).doAccept(arg);

        return spy;
    }

    @Override
    protected Consumer<Type1> getNonThrowingInstance()
    {
        //noinspection unchecked
        return mock(Consumer.class);
    }

    @Override
    protected Runnable runnableFrom(final Consumer<Type1> instance)
    {
        return () -> instance.accept(arg);
    }

    @Override
    protected Callable<Void> callableFrom(final Consumer<Type1> instance)
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
        final ThrowingConsumer<Type1> instance = getPreparedInstance();

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
        final ThrowingConsumer<Type1> spy = getPreparedInstance();

        final Consumer<Type1> instance = spy.orThrow(MyException.class);

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
        final ThrowingConsumer<Type1> first = getPreparedInstance();
        final ThrowingConsumer<Type1> second = getBaseInstance();

        final Consumer<Type1> instance = first.orTryWith(second);

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
        final ThrowingConsumer<Type1> first = getPreparedInstance();
        final Consumer<Type1> second = getNonThrowingInstance();

        final Consumer<Type1> instance = first.fallbackTo(second);

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
        final ThrowingConsumer<Type1> first = getPreparedInstance();

        final Consumer<Type1> instance = first.orDoNothing();

        final Runnable runnable = runnableFrom(instance);

        runnable.run();
        runnable.run();

        // We must do that...
        verify(first).orDoNothing();
        verify(first, times(2)).doAccept(arg);
        verifyNoMoreInteractions(first);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

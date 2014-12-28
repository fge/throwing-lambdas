package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import org.mockito.InOrder;

import java.util.concurrent.Callable;
import java.util.function.BiConsumer;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SuppressWarnings("ProhibitedExceptionDeclared")
public final class ThrowingBiConsumerTest
    extends ThrowingInterfaceBaseTest<ThrowingBiConsumer<Type1, Type2>, BiConsumer<Type1, Type2>, Void>
{
    private final Type1 arg1 = Type1.mock();
    private final Type2 arg2 = Type2.mock();

    @Override
    protected ThrowingBiConsumer<Type1, Type2> getBaseInstance()
    {
        return SpiedThrowingBiConsumer.newSpy();
    }

    @Override
    protected ThrowingBiConsumer<Type1, Type2> getPreparedInstance()
        throws Throwable
    {
        final ThrowingBiConsumer<Type1, Type2> spy = getBaseInstance();

        doNothing().doThrow(checked).doThrow(unchecked).doThrow(error)
            .when(spy).doAccept(arg1, arg2);

        return spy;
    }

    @Override
    protected BiConsumer<Type1, Type2> getNonThrowingInstance()
    {
        return mock(BiConsumer.class);
    }

    @Override
    protected Runnable runnableFrom(final BiConsumer<Type1, Type2> instance)
    {
        return () -> instance.accept(arg1, arg2);
    }

    @Override
    protected Callable<Void> callableFrom(
        final BiConsumer<Type1, Type2> instance)
    {
        return () -> {
            instance.accept(arg1, arg2);
            return null;
        };
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingBiConsumer<Type1, Type2> instance = getPreparedInstance();

        final Runnable runnable = runnableFrom(instance);

        runnable.run();

        verify(instance).doAccept(arg1, arg2);

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final ThrowingBiConsumer<Type1, Type2> spy = getPreparedInstance();

        final BiConsumer<Type1, Type2> instance
            = spy.orThrow(MyException.class);

        final Runnable runnable = runnableFrom(instance);

        runnable.run();

        verify(spy).doAccept(arg1, arg2);

        verifyCheckedRethrow(runnable, MyException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingBiConsumer<Type1, Type2> first = getPreparedInstance();
        final ThrowingBiConsumer<Type1, Type2> second = getBaseInstance();

        final BiConsumer<Type1, Type2> instance = first.orTryWith(second);

        final Runnable runnable = runnableFrom(instance);

        final InOrder inOrder = inOrder(first, second);

        runnable.run();
        runnable.run();

        inOrder.verify(first, times(2)).doAccept(arg1, arg2);
        inOrder.verify(second).doAccept(arg1, arg2);
        inOrder.verifyNoMoreInteractions();

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOr()
        throws Throwable
    {
        final ThrowingBiConsumer<Type1, Type2> first = getPreparedInstance();
        final BiConsumer<Type1, Type2> second = getNonThrowingInstance();

        final BiConsumer<Type1, Type2> instance = first.or(second);

        final Runnable runnable = runnableFrom(instance);

        final InOrder inOrder = inOrder(first, second);

        runnable.run();
        runnable.run();

        inOrder.verify(first, times(2)).doAccept(arg1, arg2);
        inOrder.verify(second).accept(arg1, arg2);
        inOrder.verifyNoMoreInteractions();

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    public void testChainedWithDoNothing()
        throws Throwable
    {
        final ThrowingBiConsumer<Type1, Type2> first = getPreparedInstance();

        final BiConsumer<Type1, Type2> instance = first.orDoNothing();

        final Runnable runnable = runnableFrom(instance);

        runnable.run();
        runnable.run();

        // We must do that...
        verify(first).orDoNothing();
        verify(first, times(2)).doAccept(arg1, arg2);
        verifyNoMoreInteractions(first);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

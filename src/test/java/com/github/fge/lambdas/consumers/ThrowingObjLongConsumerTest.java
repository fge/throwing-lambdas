package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;
import org.mockito.InOrder;

import java.util.concurrent.Callable;
import java.util.function.ObjLongConsumer;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SuppressWarnings("ProhibitedExceptionDeclared")
public final class ThrowingObjLongConsumerTest
    extends ThrowingInterfaceBaseTest<ThrowingObjLongConsumer<Type1>, ObjLongConsumer<Type1>, Void>
{
    private final Type1 arg1 = Type1.mock();
    private final long arg2 = 42L;

    @Override
    protected ThrowingObjLongConsumer<Type1> getBaseInstance()
    {
        return SpiedThrowingObjLongConsumer.newSpy();
    }

    @Override
    protected ThrowingObjLongConsumer<Type1> getPreparedInstance()
        throws Throwable
    {
        final ThrowingObjLongConsumer<Type1> spy = getBaseInstance();

        doNothing().doThrow(checked).doThrow(unchecked).doThrow(error)
            .when(spy).doAccept(arg1, arg2);

        return spy;
    }

    @Override
    protected ObjLongConsumer<Type1> getNonThrowingInstance()
    {
        //noinspection unchecked
        return mock(ObjLongConsumer.class);
    }

    @Override
    protected Runnable runnableFrom(final ObjLongConsumer<Type1> instance)
    {
        return () -> instance.accept(arg1, arg2);
    }

    @Override
    protected Callable<Void> callableFrom(
        final ObjLongConsumer<Type1> instance)
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
        final ThrowingObjLongConsumer<Type1> spy = getPreparedInstance();

        final Runnable runnable = runnableFrom(spy);

        runnable.run();

        verify(spy).doAccept(arg1, arg2);

        verifyCheckedRethrow(runnable, ThrownByLambdaException.class);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final ThrowingObjLongConsumer<Type1> spy = getPreparedInstance();

        final Runnable runnable = runnableFrom(spy.orThrow(MyException.class));

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
        final ThrowingObjLongConsumer<Type1> first = getPreparedInstance();
        final ThrowingObjLongConsumer<Type1> second = getBaseInstance();

        final ObjLongConsumer<Type1> instance = first.orTryWith(second);

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
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingObjLongConsumer<Type1> first = getPreparedInstance();
        final ObjLongConsumer<Type1> second = getNonThrowingInstance();

        final ObjLongConsumer<Type1> instance = first.fallbackTo(second);

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
        final ThrowingObjLongConsumer<Type1> first = getPreparedInstance();

        final ObjLongConsumer<Type1> instance = first.orDoNothing();

        final Runnable runnable = runnableFrom(instance);

        runnable.run();
        runnable.run();

        verify(first).orDoNothing();
        verify(first, times(2)).doAccept(arg1, arg2);
        verifyNoMoreInteractions(first);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

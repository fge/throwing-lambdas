package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ObjDoubleConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SuppressWarnings({"ProhibitedExceptionDeclared", "OverlyBroadThrowsClause",
    "AutoBoxing"})
public final class ThrowingObjDoubleConsumerTest
    extends ThrowingInterfaceBaseTest<ThrowingObjDoubleConsumer<Type1>, ObjDoubleConsumer<Type1>, Integer>
{
    private final Type1 arg1 = Type1.mock();
    private final double arg2 = 0.125;

    private final AtomicInteger sentinel = new AtomicInteger(0);

    public ThrowingObjDoubleConsumerTest()
    {
        super(42, 24);
    }

    @BeforeMethod
    public void resetSentinel()
    {
        sentinel.set(0);
    }

    @Override
    protected ThrowingObjDoubleConsumer<Type1> getAlternate()
        throws Throwable
    {
        final ThrowingObjDoubleConsumer<Type1> spy
            = SpiedThrowingObjDoubleConsumer.newSpy();

        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(spy).doAccept(arg1, arg2);

        return spy;
    }

    @Override
    protected ThrowingObjDoubleConsumer<Type1> getTestInstance()
        throws Throwable
    {
        final ThrowingObjDoubleConsumer<Type1> spy
            = SpiedThrowingObjDoubleConsumer.newSpy();

        doAnswer(invocation -> { sentinel.set(ret1); return null; })
            .doThrow(checked).doThrow(unchecked).doThrow(error)
            .when(spy).doAccept(arg1, arg2);

        return spy;
    }

    @Override
    protected ObjDoubleConsumer<Type1> getFallback()
    {
        @SuppressWarnings("unchecked")
        final ObjDoubleConsumer<Type1> mock = mock(ObjDoubleConsumer.class);

        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(mock).accept(arg1, arg2);

        return mock;
    }

    @Override
    protected Callable<Integer> asCallable(
        final ObjDoubleConsumer<Type1> instance)
    {
        return () -> { instance.accept(arg1, arg2); return sentinel.get(); };
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingObjDoubleConsumer<Type1> instance = getTestInstance();

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, ThrownByLambdaException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final ThrowingObjDoubleConsumer<Type1> spy = getTestInstance();

        final ObjDoubleConsumer<Type1> instance
            = spy.orThrow(MyException.class);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);

        verifyCheckedRethrow(callable, MyException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingObjDoubleConsumer<Type1> first = getTestInstance();
        final ThrowingObjDoubleConsumer<Type1> second = getAlternate();

        final ObjDoubleConsumer<Type1> instance = first.orTryWith(second);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingObjDoubleConsumer<Type1> first = getTestInstance();
        final ObjDoubleConsumer<Type1> second = getFallback();

        final ObjDoubleConsumer<Type1> instance = first.fallbackTo(second);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithDoNothing()
        throws Throwable
    {
        final ThrowingObjDoubleConsumer<Type1> first = getTestInstance();

        final ObjDoubleConsumer<Type1> instance = first.orDoNothing();

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret1);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ObjLongConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SuppressWarnings({"ProhibitedExceptionDeclared", "OverlyBroadThrowsClause",
    "AutoBoxing"})
public final class ThrowingObjLongConsumerTest
    extends ThrowingInterfaceBaseTest<ThrowingObjLongConsumer<Type1>,
    ObjLongConsumer<Type1>, Integer>
{
    private final Type1 arg1 = Type1.mock();
    private final long arg2 = 42L;

    private final AtomicInteger sentinel = new AtomicInteger(0);

    public ThrowingObjLongConsumerTest()
    {
        super(42, 24);
    }

    @BeforeMethod
    public void resetSentinel()
    {
        sentinel.set(0);
    }

    @Override
    protected ThrowingObjLongConsumer<Type1> getAlternate()
        throws Throwable
    {
        final ThrowingObjLongConsumer<Type1> spy
            = SpiedThrowingObjLongConsumer.newSpy();

        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(spy).doAccept(arg1, arg2);

        return spy;
    }

    @Override
    protected ThrowingObjLongConsumer<Type1> getTestInstance()
        throws Throwable
    {
        final ThrowingObjLongConsumer<Type1> spy
            = SpiedThrowingObjLongConsumer.newSpy();

        doAnswer(invocation -> { sentinel.set(ret1); return null; })
            .doThrow(checked).doThrow(unchecked).doThrow(error)
            .when(spy).doAccept(arg1, arg2);

        return spy;
    }

    @Override
    protected ObjLongConsumer<Type1> getFallback()
    {
        @SuppressWarnings("unchecked")
        final ObjLongConsumer<Type1> mock = mock(ObjLongConsumer.class);

        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(mock).accept(arg1, arg2);

        return mock;
    }

    @Override
    protected Callable<Integer> asCallable(
        final ObjLongConsumer<Type1> instance)
    {
        return () -> { instance.accept(arg1, arg2); return sentinel.get(); };
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingObjLongConsumer<Type1> instance = getTestInstance();

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
        final ThrowingObjLongConsumer<Type1> spy = getTestInstance();

        final ObjLongConsumer<Type1> instance
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
        final ThrowingObjLongConsumer<Type1> first = getTestInstance();
        final ThrowingObjLongConsumer<Type1> second = getAlternate();

        final ObjLongConsumer<Type1> instance = first.orTryWith(second);

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
        final ThrowingObjLongConsumer<Type1> first = getTestInstance();
        final ObjLongConsumer<Type1> second = getFallback();

        final ObjLongConsumer<Type1> instance = first.fallbackTo(second);

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithDoNothing()
        throws Throwable
    {
        final ThrowingObjLongConsumer<Type1> first = getTestInstance();

        final ObjLongConsumer<Type1> instance = first.orDoNothing();

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret1);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

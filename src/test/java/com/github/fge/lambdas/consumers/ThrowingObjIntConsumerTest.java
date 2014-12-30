package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ObjIntConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SuppressWarnings({"ProhibitedExceptionDeclared", "OverlyBroadThrowsClause",
    "AutoBoxing"})
public final class ThrowingObjIntConsumerTest
    extends ThrowingInterfaceBaseTest<ThrowingObjIntConsumer<Type1>, ObjIntConsumer<Type1>, Integer>
{
    private final Type1 arg1 = Type1.mock();
    private final int arg2 = 042;

    private final AtomicInteger sentinel = new AtomicInteger(0);

    public ThrowingObjIntConsumerTest()
    {
        super(42, 24);
    }

    @BeforeMethod
    public void resetSentinel()
    {
        sentinel.set(0);
    }

    @Override
    protected ThrowingObjIntConsumer<Type1> getAlternate()
        throws Throwable
    {
        final ThrowingObjIntConsumer<Type1> spy
            = SpiedThrowingObjIntConsumer.newSpy();

        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(spy).doAccept(arg1, arg2);

        return spy;
    }

    @Override
    protected ThrowingObjIntConsumer<Type1> getPreparedInstance()
        throws Throwable
    {
        final ThrowingObjIntConsumer<Type1> spy
            = SpiedThrowingObjIntConsumer.newSpy();

        doAnswer(invocation -> { sentinel.set(ret1); return null; })
            .doThrow(checked).doThrow(unchecked).doThrow(error)
            .when(spy).doAccept(arg1, arg2);

        return spy;
    }

    @Override
    protected ObjIntConsumer<Type1> getFallbackInstance()
    {
        @SuppressWarnings("unchecked")
        final ObjIntConsumer<Type1> mock = mock(ObjIntConsumer.class);

        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(mock).accept(arg1, arg2);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final ObjIntConsumer<Type1> instance)
    {
        return () -> instance.accept(arg1, arg2);
    }

    @Override
    protected Callable<Integer> callableFrom(
        final ObjIntConsumer<Type1> instance)
    {
        return () -> { instance.accept(arg1, arg2); return sentinel.get(); };
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingObjIntConsumer<Type1> instance = getPreparedInstance();

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
        final ThrowingObjIntConsumer<Type1> spy = getPreparedInstance();

        final ObjIntConsumer<Type1> instance
            = spy.orThrow(MyException.class);

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
        final ThrowingObjIntConsumer<Type1> first = getPreparedInstance();
        final ThrowingObjIntConsumer<Type1> second = getAlternate();

        final ObjIntConsumer<Type1> instance = first.orTryWith(second);

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
        final ThrowingObjIntConsumer<Type1> first = getPreparedInstance();
        final ObjIntConsumer<Type1> second = getFallbackInstance();

        final ObjIntConsumer<Type1> instance = first.fallbackTo(second);

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
        final ThrowingObjIntConsumer<Type1> first = getPreparedInstance();

        final ObjIntConsumer<Type1> instance = first.orDoNothing();

        final Callable<Integer> callable = callableFrom(instance);
        final Runnable runnable = runnableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret1);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@SuppressWarnings({"ProhibitedExceptionDeclared", "OverlyBroadThrowsClause"})
public final class ThrowingBiConsumerTest
    extends ThrowingInterfaceBaseTest<ThrowingBiConsumer<Type1, Type2>,
    BiConsumer<Type1, Type2>, Integer>
{
    private final Type1 arg1 = Type1.mock();
    private final Type2 arg2 = Type2.mock();

    private final AtomicInteger sentinel = new AtomicInteger(0);

    private final int ret1 = 42;
    private final int ret2 = 24;

    @BeforeMethod
    public void resetSentinel()
    {
        sentinel.set(0);
    }

    @Override
    protected ThrowingBiConsumer<Type1, Type2> getBaseInstance()
        throws Throwable
    {
        final ThrowingBiConsumer<Type1, Type2> spy
            = SpiedThrowingBiConsumer.newSpy();

        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(spy).doAccept(arg1, arg2);

        return spy;
    }

    @Override
    protected ThrowingBiConsumer<Type1, Type2> getPreparedInstance()
        throws Throwable
    {
        final ThrowingBiConsumer<Type1, Type2> spy
            = SpiedThrowingBiConsumer.newSpy();

        doAnswer(invocation -> { sentinel.set(ret1); return null; })
            .doThrow(checked).doThrow(unchecked).doThrow(error)
            .when(spy).doAccept(arg1, arg2);

        return spy;
    }

    @Override
    protected BiConsumer<Type1, Type2> getNonThrowingInstance()
    {
        @SuppressWarnings("unchecked")
        final BiConsumer<Type1, Type2> mock = mock(BiConsumer.class);

        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(mock).accept(arg1, arg2);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final BiConsumer<Type1, Type2> instance)
    {
        return () -> instance.accept(arg1, arg2);
    }

    @Override
    protected Callable<Integer> callableFrom(
        final BiConsumer<Type1, Type2> instance)
    {
        return () -> {
            instance.accept(arg1, arg2);
            return sentinel.get();
        };
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingBiConsumer<Type1, Type2> instance = getPreparedInstance();

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
        final ThrowingBiConsumer<Type1, Type2> spy = getPreparedInstance();

        final BiConsumer<Type1, Type2> instance
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
        final ThrowingBiConsumer<Type1, Type2> first = getPreparedInstance();
        final ThrowingBiConsumer<Type1, Type2> second = getBaseInstance();

        final BiConsumer<Type1, Type2> instance = first.orTryWith(second);

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
        final ThrowingBiConsumer<Type1, Type2> first = getPreparedInstance();
        final BiConsumer<Type1, Type2> second = getNonThrowingInstance();

        final BiConsumer<Type1, Type2> instance = first.fallbackTo(second);

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
        final ThrowingBiConsumer<Type1, Type2> first = getPreparedInstance();

        final BiConsumer<Type1, Type2> instance = first.orDoNothing();

        final Callable<Integer> callable = callableFrom(instance);
        final Runnable runnable = runnableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret1);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

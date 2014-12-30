package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@SuppressWarnings({"ProhibitedExceptionDeclared", "OverlyBroadThrowsClause",
    "AutoBoxing"})
public final class ThrowingConsumerTest
    extends ThrowingInterfaceBaseTest<ThrowingConsumer<Type1>, Consumer<Type1>, Integer>
{
    private final Type1 arg = Type1.mock();

    private final AtomicInteger sentinel = new AtomicInteger(0);

    public ThrowingConsumerTest()
    {
        super(42, 24);
    }

    @BeforeMethod
    public void resetSentinel()
    {
        sentinel.set(0);
    }

    @Override
    protected ThrowingConsumer<Type1> getAlternate()
        throws Throwable
    {
        final ThrowingConsumer<Type1> spy = SpiedThrowingConsumer.newSpy();

        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(spy).doAccept(arg);

        return spy;
    }

    @Override
    protected ThrowingConsumer<Type1> getTestInstance()
        throws Throwable
    {
        final ThrowingConsumer<Type1> spy = SpiedThrowingConsumer.newSpy();

        doAnswer(invocation -> { sentinel.set(ret1); return null; })
            .doThrow(checked).doThrow(unchecked).doThrow(error)
            .when(spy).doAccept(arg);

        return spy;
    }

    @Override
    protected Consumer<Type1> getFallback()
    {
        @SuppressWarnings("unchecked")
        final Consumer<Type1> mock = mock(Consumer.class);

        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(mock).accept(arg);

        return mock;
    }

    @Override
    protected Runnable runnableFrom(final Consumer<Type1> instance)
    {
        return () -> instance.accept(arg);
    }

    @Override
    protected Callable<Integer> asCallable(final Consumer<Type1> instance)
    {
        return () -> { instance.accept(arg); return sentinel.get(); };
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingConsumer<Type1> instance = getTestInstance();

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
        final ThrowingConsumer<Type1> spy = getTestInstance();

        final Consumer<Type1> instance = spy.orThrow(MyException.class);

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
        final ThrowingConsumer<Type1> first = getTestInstance();
        final ThrowingConsumer<Type1> second = getAlternate();

        final Consumer<Type1> instance = first.orTryWith(second);

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
        final ThrowingConsumer<Type1> first = getTestInstance();
        final Consumer<Type1> second = getFallback();

        final Consumer<Type1> instance = first.fallbackTo(second);

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
        final ThrowingConsumer<Type1> first = getTestInstance();

        final Consumer<Type1> instance = first.orDoNothing();

        final Callable<Integer> callable = asCallable(instance);
        final Runnable runnable = runnableFrom(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret1);

        verifyUncheckedThrow(runnable);

        verifyErrorThrow(runnable);
    }
}

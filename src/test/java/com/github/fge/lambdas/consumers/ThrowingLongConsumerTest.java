package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowingInterfaceTest;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.LongConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@SuppressWarnings({"ProhibitedExceptionDeclared", "OverlyBroadThrowsClause",
    "AutoBoxing"})
public final class ThrowingLongConsumerTest
    extends ThrowingInterfaceTest<ThrowingLongConsumer, ThrowingLongConsumer, LongConsumer, Integer>
{
    private final long value = 42L;

    private final AtomicInteger sentinel = new AtomicInteger(0);

    public ThrowingLongConsumerTest()
    {
        super(SpiedThrowingLongConsumer::newSpy, () -> mock(LongConsumer.class),
            42, 24);
    }

    @BeforeMethod
    public void resetSentinel()
    {
        sentinel.set(0);
    }

    @Override
    protected void setupFull(final ThrowingLongConsumer instance)
        throws Throwable
    {
        doAnswer(invocation -> { sentinel.set(ret1); return null; })
            .doThrow(checked).doThrow(unchecked).doThrow(error)
            .when(instance).doAccept(value);
    }

    @Override
    protected void setupAlternate(final ThrowingLongConsumer instance)
        throws Throwable
    {
        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(instance).doAccept(value);
    }

    @Override
    protected void setupFallback(final LongConsumer instance)
    {
        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(instance).accept(value);
    }

    @Override
    protected Callable<Integer> asCallable(final LongConsumer instance)
    {
        return () -> { instance.accept(value); return sentinel.get(); };
    }

    public void testChainedWithDoNothing()
        throws Throwable
    {
        final LongConsumer instance = getFullInstance().orDoNothing();

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret1);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

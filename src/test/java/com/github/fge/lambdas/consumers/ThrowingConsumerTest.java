package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowingInterfaceTest;
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
    extends ThrowingInterfaceTest<ThrowingConsumer<Type1>, ThrowingConsumer<Type1>, Consumer<Type1>, Integer>
{
    private final Type1 arg = Type1.mock();

    private final AtomicInteger sentinel = new AtomicInteger(0);

    public ThrowingConsumerTest()
    {
        super(SpiedThrowingConsumer::newSpy, () -> mock(Consumer.class),
            42, 24);
    }

    @BeforeMethod
    public void resetSentinel()
    {
        sentinel.set(0);
    }

    @Override
    protected void setupFull(final ThrowingConsumer<Type1> instance)
        throws Throwable
    {
        doAnswer(invocation -> { sentinel.set(ret1); return null; })
            .doThrow(checked).doThrow(unchecked).doThrow(error)
            .when(instance).doAccept(arg);
    }

    @Override
    protected void setupAlternate(final ThrowingConsumer<Type1> instance)
        throws Throwable
    {
        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(instance).doAccept(arg);
    }

    @Override
    protected void setupFallback(final Consumer<Type1> instance)
    {
        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(instance).accept(arg);
    }

    @Override
    protected Callable<Integer> asCallable(final Consumer<Type1> instance)
    {
        return () -> { instance.accept(arg); return sentinel.get(); };
    }

    public void testChainedWithDoNothing()
        throws Throwable
    {
        final Consumer<Type1> instance = getFullInstance().orDoNothing();

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret1);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

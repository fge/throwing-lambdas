package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowingInterfaceTest;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@SuppressWarnings({"AutoBoxing", "ProhibitedExceptionDeclared",
    "OverlyBroadThrowsClause"})
public final class ThrowingBiConsumerTest
    extends ThrowingInterfaceTest<ThrowingBiConsumer<Type1, Type2>, ThrowingBiConsumer<Type1, Type2>, BiConsumer<Type1, Type2>, Integer>
{
    private final Type1 t = Type1.mock();
    private final Type2 u = Type2.mock();

    private final AtomicInteger sentinel = new AtomicInteger(0);

    public ThrowingBiConsumerTest()
    {
        super(SpiedThrowingBiConsumer::newSpy, () -> mock(BiConsumer.class),
            42, 24);
    }

    @BeforeMethod
    public void resetSentinel()
    {
        sentinel.set(0);
    }

    @Override
    protected void setupFull(final ThrowingBiConsumer<Type1, Type2> instance)
        throws Throwable
    {
        doAnswer(invocation -> { sentinel.set(ret1); return null; })
            .doThrow(checked).doThrow(unchecked).doThrow(error)
            .when(instance).doAccept(t, u);
    }

    @Override
    protected void setupAlternate(
        final ThrowingBiConsumer<Type1, Type2> instance)
        throws Throwable
    {
        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(instance).doAccept(t, u);
    }

    @Override
    protected void setupFallback(final BiConsumer<Type1, Type2> instance)
    {
        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(instance).accept(t, u);
    }

    @Override
    protected Callable<Integer> asCallable(
        final BiConsumer<Type1, Type2> instance)
    {
        return () -> { instance.accept(t, u); return sentinel.get(); };
    }

    public void testChainedWithDoNothing()
        throws Throwable
    {
        final BiConsumer<Type1, Type2> instance
            = getFullInstance().orDoNothing();

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret1);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

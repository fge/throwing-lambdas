package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.ThrowingInterfaceTest;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ObjIntConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@SuppressWarnings({"ProhibitedExceptionDeclared", "OverlyBroadThrowsClause",
    "AutoBoxing"})
public final class ThrowingObjIntConsumerTest
    extends ThrowingInterfaceTest<ThrowingObjIntConsumer<Type1>, ThrowingObjIntConsumer<Type1>, ObjIntConsumer<Type1>, Integer>
{
    private final Type1 t = Type1.mock();
    private final int value = 142;

    private final AtomicInteger sentinel = new AtomicInteger(0);

    public ThrowingObjIntConsumerTest()
    {
        super(SpiedThrowingObjIntConsumer::newSpy,
            () -> mock(ObjIntConsumer.class), 42, 24);
    }

    @BeforeMethod
    public void resetSentinel()
    {
        sentinel.set(0);
    }

    @Override
    protected void setupFull(final ThrowingObjIntConsumer<Type1> instance)
        throws Throwable
    {
        doAnswer(invocation -> { sentinel.set(ret1); return null; })
            .doThrow(checked).doThrow(unchecked).doThrow(error)
            .when(instance).doAccept(t, value);
    }

    @Override
    protected void setupAlternate(final ThrowingObjIntConsumer<Type1> instance)
        throws Throwable
    {
        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(instance).doAccept(t, value);
    }

    @Override
    protected void setupFallback(final ObjIntConsumer<Type1> instance)
    {
        doAnswer(invocation -> { sentinel.set(ret2); return null; })
            .when(instance).accept(t, value);
    }

    @Override
    protected Callable<Integer> asCallable(
        final ObjIntConsumer<Type1> instance)
    {
        return () -> { instance.accept(t, value); return sentinel.get(); };
    }

    public void testChainedWithDoNothing()
        throws Throwable
    {
        final ObjIntConsumer<Type1> instance = getFullInstance().orDoNothing();

        final Callable<Integer> callable = asCallable(instance);

        assertThat(callable.call()).isEqualTo(ret1);
        assertThat(callable.call()).isEqualTo(ret1);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrowingInterfaceBaseTest;
import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import com.github.fge.lambdas.helpers.Type3;

import java.util.concurrent.Callable;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "OverlyBroadThrowsClause" })
public final class ThrowingBiFunctionTest
    extends ThrowingInterfaceBaseTest<ThrowingBiFunction<Type1, Type2, Type3>, BiFunction<Type1, Type2, Type3>, Type3>
{
    private final Type1 arg1 = Type1.mock();
    private final Type2 arg2 = Type2.mock();

    public ThrowingBiFunctionTest()
    {
        super(Type3.mock(), Type3.mock());
    }

    @Override
    protected ThrowingBiFunction<Type1, Type2, Type3> getAlternate()
        throws Throwable
    {
        final ThrowingBiFunction<Type1, Type2, Type3>
            spy = SpiedThrowingBiFunction.newSpy();

        when(spy.doApply(arg1, arg2)).thenReturn(ret2);
        return spy;
    }

    @Override
    protected ThrowingBiFunction<Type1, Type2, Type3> getTestInstance()
        throws Throwable
    {
        final ThrowingBiFunction<Type1, Type2, Type3> spy
            = SpiedThrowingBiFunction.newSpy();

        when(spy.doApply(arg1, arg2)).thenReturn(ret1).thenThrow(checked)
            .thenThrow(unchecked).thenThrow(error);

        return spy;
    }

    @Override
    protected BiFunction<Type1, Type2, Type3> getFallback()
    {
        @SuppressWarnings("unchecked")
        final BiFunction<Type1, Type2, Type3> mock = mock(BiFunction.class);

        when(mock.apply(arg1, arg2)).thenReturn(ret2);

        return mock;
    }

    @Override
    protected Callable<Type3> asCallable(
        final BiFunction<Type1, Type2, Type3> instance)
    {
        return () -> instance.apply(arg1, arg2);
    }

    @Override
    public void testUnchained()
        throws Throwable
    {
        final ThrowingBiFunction<Type1, Type2, Type3> instance
            = getTestInstance();

        final Callable<Type3> callable = asCallable(instance);

        assertThat(callable.call()).isSameAs(ret1);

        verifyCheckedRethrow(callable, ThrownByLambdaException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrThrow()
        throws Throwable
    {
        final BiFunction<Type1, Type2, Type3> instance
            = getTestInstance().orThrow(MyException.class);

        final Callable<Type3> callable = asCallable(instance);

        assertThat(callable.call()).isSameAs(ret1);

        verifyCheckedRethrow(callable, MyException.class);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithOrTryWith()
        throws Throwable
    {
        final ThrowingBiFunction<Type1, Type2, Type3> first
            = getTestInstance();
        final ThrowingBiFunction<Type1, Type2, Type3> second
            = getAlternate();

        final BiFunction<Type1, Type2, Type3> instance
            = first.orTryWith(second);

        final Callable<Type3> callable = asCallable(instance);

        assertThat(callable.call()).isSameAs(ret1);

        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    @Override
    public void testChainedWithFallbackTo()
        throws Throwable
    {
        final ThrowingBiFunction<Type1, Type2, Type3> first
            = getTestInstance();
        final BiFunction<Type1, Type2, Type3> second = getFallback();

        final BiFunction<Type1, Type2, Type3> instance = first.fallbackTo(
            second);

        final Callable<Type3> callable = asCallable(instance);

        assertThat(callable.call()).isSameAs(ret1);
        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }

    public void testChainedWithOrReturn()
        throws Throwable
    {
        final ThrowingBiFunction<Type1, Type2, Type3> first
            = getTestInstance();

        final BiFunction<Type1, Type2, Type3> instance = first.orReturn(ret2);

        final Callable<Type3> callable = asCallable(instance);

        assertThat(callable.call()).isSameAs(ret1);
        assertThat(callable.call()).isSameAs(ret2);

        verifyUncheckedThrow(callable);

        verifyErrorThrow(callable);
    }
}

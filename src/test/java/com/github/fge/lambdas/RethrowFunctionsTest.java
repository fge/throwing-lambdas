package com.github.fge.lambdas;

import com.github.fge.lambdas.functions.ThrowingFunction;
import com.github.fge.lambdas.functions.ThrowingIntFunction;
import com.github.fge.lambdas.functions.ThrowingLongFunction;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.github.fge.lambdas.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.Rethrow.rethrow;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "unchecked" })
public final class RethrowFunctionsTest
{
    @Test
    public void regularExceptionFromFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingFunction<Type1, Type2> f = mock(ThrowingFunction.class);

        final Exception ex = new IOException("meh");

        when(f.apply(any(Type1.class))).thenThrow(ex);

        try {
            /*
             * Note that WE MUST include a terminal operation function!
             * Otherwise the stream is not consumed. Meh.
             */
            Stream.of(mock(Type1.class)).map(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(ex);
        }
    }

    @Test
    public void runtimeExceptionFromFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingFunction<Type1, Type2> f = mock(ThrowingFunction.class);

        final Exception ex = new RuntimeException("meh");

        when(f.apply(any(Type1.class))).thenThrow(ex);

        try {
            Stream.of(mock(Type1.class)).map(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(ex);
        }
    }

    @Test
    public void errorFromFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingFunction<Type1, Type2> f = mock(ThrowingFunction.class);

        final Error ex = new Error("meh");

        when(f.apply(any(Type1.class))).thenThrow(ex);

        try {
            Stream.of(mock(Type1.class)).map(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Throwable e) {
            assertThat(e).isSameAs(ex);
        }
    }

    @Test
    public void regularExceptionFromIntFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingIntFunction<Type1> f = mock(ThrowingIntFunction.class);

        final Exception ex = new IOException("meh");

        when(f.apply(anyInt())).thenThrow(ex);

        try {
            IntStream.of(0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(ex);
        }
    }

    @Test
    public void runtimeExceptionFromIntFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingIntFunction<Type1> f = mock(ThrowingIntFunction.class);

        final Exception ex = new RuntimeException("meh");

        when(f.apply(anyInt())).thenThrow(ex);

        try {
            IntStream.of(0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(ex);
        }
    }

    @Test
    public void errorFromIntFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingIntFunction<Type1> f = mock(ThrowingIntFunction.class);

        final Error ex = new Error("meh");

        when(f.apply(anyInt())).thenThrow(ex);

        try {
            IntStream.of(0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Throwable e) {
            assertThat(e).isSameAs(ex);
        }
    }

    @Test
    public void regularExceptionFromLongFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingLongFunction<Type1> f = mock(ThrowingLongFunction.class);

        final Exception ex = new IOException("meh");

        when(f.apply(anyLong())).thenThrow(ex);

        try {
            LongStream.of(0L).mapToObj(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(ex);
        }
    }

    @Test
    public void runtimeExceptionFromLongFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingLongFunction<Type1> f = mock(ThrowingLongFunction.class);

        final Exception ex = new RuntimeException("meh");

        when(f.apply(anyLong())).thenThrow(ex);

        try {
            LongStream.of(0L).mapToObj(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(ex);
        }
    }

    @Test
    public void errorFromLongFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingLongFunction<Type1> f = mock(ThrowingLongFunction.class);

        final Error ex = new Error("meh");

        when(f.apply(anyLong())).thenThrow(ex);

        try {
            LongStream.of(0L).mapToObj(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Throwable e) {
            assertThat(e).isSameAs(ex);
        }
    }
}
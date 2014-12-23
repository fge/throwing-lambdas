package com.github.fge.lambdas;

import com.github.fge.lambdas.function.ThrowingDoubleFunction;
import com.github.fge.lambdas.function.ThrowingDoubleToIntFunction;
import com.github.fge.lambdas.function.ThrowingDoubleToLongFunction;
import com.github.fge.lambdas.function.ThrowingFunction;
import com.github.fge.lambdas.function.ThrowingIntFunction;
import com.github.fge.lambdas.function.ThrowingIntToDoubleFunction;
import com.github.fge.lambdas.function.ThrowingIntToLongFunction;
import com.github.fge.lambdas.function.ThrowingLongFunction;
import com.github.fge.lambdas.function.ThrowingLongToDoubleFunction;
import com.github.fge.lambdas.function.ThrowingLongToIntFunction;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.github.fge.lambdas.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.Rethrow.rethrow;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "ProhibitedExceptionDeclared", "unchecked", "AutoBoxing" })
public final class RethrowFunctionsTest
{
    /*
     * Function
     */
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

    /*
     * Int*Function
     */
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
    public void regularExceptionFromIntToLongFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingIntToLongFunction f
            = mock(ThrowingIntToLongFunction.class);

        final Exception ex = new IOException("meh");

        when(f.apply(anyInt())).thenThrow(ex);

        try {
            IntStream.of(0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(ex);
        }
    }

    @Test
    public void runtimeExceptionFromIntToLongFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingIntToLongFunction f
            = mock(ThrowingIntToLongFunction.class);

        final Exception ex = new RuntimeException("meh");

        when(f.apply(anyInt())).thenThrow(ex);

        try {
            IntStream.of(0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(ex);
        }
    }

    @Test
    public void errorFromIntToLongFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingIntToLongFunction f
            = mock(ThrowingIntToLongFunction.class);

        final Error ex = new Error("meh");

        when(f.apply(anyInt())).thenThrow(ex);

        try {
            IntStream.of(0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Throwable e) {
            assertThat(e).isSameAs(ex);
        }
    }

    @Test
    public void regularExceptionFromIntToDoubleFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingIntToDoubleFunction f
            = mock(ThrowingIntToDoubleFunction.class);

        final Exception ex = new IOException("meh");

        when(f.apply(anyInt())).thenThrow(ex);

        try {
            IntStream.of(0).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(ex);
        }
    }

    @Test
    public void runtimeExceptionFromIntToDoubleFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingIntToDoubleFunction f
            = mock(ThrowingIntToDoubleFunction.class);

        final Exception ex = new RuntimeException("meh");

        when(f.apply(anyInt())).thenThrow(ex);

        try {
            IntStream.of(0).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(ex);
        }
    }

    @Test
    public void errorFromIntToDoubleFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingIntToDoubleFunction f
            = mock(ThrowingIntToDoubleFunction.class);

        final Error ex = new Error("meh");

        when(f.apply(anyInt())).thenThrow(ex);

        try {
            IntStream.of(0).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Throwable e) {
            assertThat(e).isSameAs(ex);
        }
    }

    /*
     * Long*Function
     */
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

    @Test
    public void regularExceptionFromLongToIntFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingLongToIntFunction f
            = mock(ThrowingLongToIntFunction.class);

        final Exception ex = new IOException("meh");

        when(f.apply(anyLong())).thenThrow(ex);

        try {
            LongStream.of(0L).mapToInt(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(ex);
        }
    }

    @Test
    public void runtimeExceptionFromLongToIntFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingLongToIntFunction f
            = mock(ThrowingLongToIntFunction.class);

        final Exception ex = new RuntimeException("meh");

        when(f.apply(anyLong())).thenThrow(ex);

        try {
            LongStream.of(0L).mapToInt(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(ex);
        }
    }

    @Test
    public void errorFromLongToIntFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingLongToIntFunction f
            = mock(ThrowingLongToIntFunction.class);

        final Error ex = new Error("meh");

        when(f.apply(anyLong())).thenThrow(ex);

        try {
            LongStream.of(0L).mapToInt(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Throwable e) {
            assertThat(e).isSameAs(ex);
        }
    }

    @Test
    public void regularExceptionFromLongToDoubleFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingLongToDoubleFunction f
            = mock(ThrowingLongToDoubleFunction.class);

        final Exception ex = new IOException("meh");

        when(f.apply(anyLong())).thenThrow(ex);

        try {
            LongStream.of(0L).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(ex);
        }
    }

    @Test
    public void runtimeExceptionFromLongToDoubleFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingLongToDoubleFunction f
            = mock(ThrowingLongToDoubleFunction.class);

        final Exception ex = new RuntimeException("meh");

        when(f.apply(anyLong())).thenThrow(ex);

        try {
            LongStream.of(0L).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(ex);
        }
    }

    @Test
    public void errorFromLongToDoubleFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingLongToDoubleFunction f
            = mock(ThrowingLongToDoubleFunction.class);

        final Error ex = new Error("meh");

        when(f.apply(anyLong())).thenThrow(ex);

        try {
            LongStream.of(0L).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Throwable e) {
            assertThat(e).isSameAs(ex);
        }
    }

    /*
     * Double*Function
     */
    @Test
    public void regularExceptionFromDoubleFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingDoubleFunction<Type1> f
            = mock(ThrowingDoubleFunction.class);

        final Exception ex = new IOException("meh");

        when(f.apply(anyDouble())).thenThrow(ex);

        try {
            DoubleStream.of(0.0d).mapToObj(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(ex);
        }
    }

    @Test
    public void runtimeExceptionFromDoubleFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingDoubleFunction<Type1> f
            = mock(ThrowingDoubleFunction.class);

        final Exception ex = new RuntimeException("meh");

        when(f.apply(anyDouble())).thenThrow(ex);

        try {
            DoubleStream.of(0.0d).mapToObj(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(ex);
        }
    }

    @Test
    public void errorFromDoubleFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingDoubleFunction<Type1> f
            = mock(ThrowingDoubleFunction.class);

        final Error ex = new Error("meh");

        when(f.apply(anyDouble())).thenThrow(ex);

        try {
            DoubleStream.of(0.0d).mapToObj(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Throwable e) {
            assertThat(e).isSameAs(ex);
        }
    }

    @Test
    public void regularExceptionFromDoubleToIntFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingDoubleToIntFunction f
            = mock(ThrowingDoubleToIntFunction.class);

        final Exception ex = new IOException("meh");

        when(f.apply(anyDouble())).thenThrow(ex);

        try {
            DoubleStream.of(0.0d).mapToInt(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(ex);
        }
    }

    @Test
    public void runtimeExceptionFromDoubleToIntFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingDoubleToIntFunction f
            = mock(ThrowingDoubleToIntFunction.class);

        final Exception ex = new RuntimeException("meh");

        when(f.apply(anyDouble())).thenThrow(ex);

        try {
            DoubleStream.of(0.0d).mapToInt(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(ex);
        }
    }

    @Test
    public void errorFromDoubleToIntFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingDoubleToIntFunction f
            = mock(ThrowingDoubleToIntFunction.class);

        final Error ex = new Error("meh");

        when(f.apply(anyDouble())).thenThrow(ex);

        try {
            DoubleStream.of(0.0d).mapToInt(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Throwable e) {
            assertThat(e).isSameAs(ex);
        }
    }

    @Test
    public void regularExceptionFromDoubleToLongFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingDoubleToLongFunction f
            = mock(ThrowingDoubleToLongFunction.class);

        final Exception ex = new IOException("meh");

        when(f.apply(anyDouble())).thenThrow(ex);

        try {
            DoubleStream.of(0.0d).mapToLong(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(ex);
        }
    }

    @Test
    public void runtimeExceptionFromDoubleToLongFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingDoubleToLongFunction f
            = mock(ThrowingDoubleToLongFunction.class);

        final Exception ex = new RuntimeException("meh");

        when(f.apply(anyDouble())).thenThrow(ex);

        try {
            DoubleStream.of(0.0d).mapToLong(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(ex);
        }
    }

    @Test
    public void errorFromDoubleToLongFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingDoubleToLongFunction f
            = mock(ThrowingDoubleToLongFunction.class);

        final Error ex = new Error("meh");

        when(f.apply(anyDouble())).thenThrow(ex);

        try {
            DoubleStream.of(0.0d).mapToLong(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Throwable e) {
            assertThat(e).isSameAs(ex);
        }
    }
}
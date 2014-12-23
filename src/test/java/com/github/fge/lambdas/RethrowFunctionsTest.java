package com.github.fge.lambdas;

import com.github.fge.lambdas.functions.ThrowingDoubleFunction;
import com.github.fge.lambdas.functions.ThrowingDoubleToIntFunction;
import com.github.fge.lambdas.functions.ThrowingDoubleToLongFunction;
import com.github.fge.lambdas.functions.ThrowingFunction;
import com.github.fge.lambdas.functions.ThrowingIntFunction;
import com.github.fge.lambdas.functions.ThrowingIntToDoubleFunction;
import com.github.fge.lambdas.functions.ThrowingIntToLongFunction;
import com.github.fge.lambdas.functions.ThrowingLongFunction;
import com.github.fge.lambdas.functions.ThrowingLongToDoubleFunction;
import com.github.fge.lambdas.functions.ThrowingLongToIntFunction;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import org.testng.annotations.Test;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.github.fge.lambdas.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.Rethrow.rethrow;
import static com.github.fge.lambdas.helpers.Throwables.ERROR;
import static com.github.fge.lambdas.helpers.Throwables.REGULAR_EXCEPTION;
import static com.github.fge.lambdas.helpers.Throwables.RUNTIME_EXCEPTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({
    "ProhibitedExceptionDeclared",
    "unchecked",
    "AutoBoxing",
    "ErrorNotRethrown"
})
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

        when(f.apply(any(Type1.class))).thenThrow(REGULAR_EXCEPTION);

        try {
            /*
             * Note that WE MUST include a terminal operation function!
             * Otherwise the stream is not consumed. Meh.
             */
            Stream.of(mock(Type1.class)).map(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(REGULAR_EXCEPTION);
        }
    }

    @Test
    public void runtimeExceptionFromFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingFunction<Type1, Type2> f = mock(ThrowingFunction.class);

        when(f.apply(any(Type1.class))).thenThrow(RUNTIME_EXCEPTION);

        try {
            Stream.of(mock(Type1.class)).map(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(RUNTIME_EXCEPTION);
        }
    }

    @Test
    public void errorFromFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingFunction<Type1, Type2> f = mock(ThrowingFunction.class);

        when(f.apply(any(Type1.class))).thenThrow(ERROR);

        try {
            Stream.of(mock(Type1.class)).map(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
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

        when(f.apply(anyInt())).thenThrow(REGULAR_EXCEPTION);

        try {
            IntStream.of(0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(REGULAR_EXCEPTION);
        }
    }

    @Test
    public void runtimeExceptionFromIntFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingIntFunction<Type1> f = mock(ThrowingIntFunction.class);

        when(f.apply(anyInt())).thenThrow(RUNTIME_EXCEPTION);

        try {
            IntStream.of(0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(RUNTIME_EXCEPTION);
        }
    }

    @Test
    public void errorFromIntFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingIntFunction<Type1> f = mock(ThrowingIntFunction.class);

        when(f.apply(anyInt())).thenThrow(ERROR);

        try {
            IntStream.of(0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void regularExceptionFromIntToLongFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingIntToLongFunction f
            = mock(ThrowingIntToLongFunction.class);

        when(f.apply(anyInt())).thenThrow(REGULAR_EXCEPTION);

        try {
            IntStream.of(0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(REGULAR_EXCEPTION);
        }
    }

    @Test
    public void runtimeExceptionFromIntToLongFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingIntToLongFunction f
            = mock(ThrowingIntToLongFunction.class);

        when(f.apply(anyInt())).thenThrow(RUNTIME_EXCEPTION);

        try {
            IntStream.of(0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(RUNTIME_EXCEPTION);
        }
    }

    @Test
    public void errorFromIntToLongFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingIntToLongFunction f
            = mock(ThrowingIntToLongFunction.class);

        when(f.apply(anyInt())).thenThrow(ERROR);

        try {
            IntStream.of(0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void regularExceptionFromIntToDoubleFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingIntToDoubleFunction f
            = mock(ThrowingIntToDoubleFunction.class);

        when(f.apply(anyInt())).thenThrow(REGULAR_EXCEPTION);

        try {
            IntStream.of(0).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(REGULAR_EXCEPTION);
        }
    }

    @Test
    public void runtimeExceptionFromIntToDoubleFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingIntToDoubleFunction f
            = mock(ThrowingIntToDoubleFunction.class);

        when(f.apply(anyInt())).thenThrow(RUNTIME_EXCEPTION);

        try {
            IntStream.of(0).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(RUNTIME_EXCEPTION);
        }
    }

    @Test
    public void errorFromIntToDoubleFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingIntToDoubleFunction f
            = mock(ThrowingIntToDoubleFunction.class);

        when(f.apply(anyInt())).thenThrow(ERROR);

        try {
            IntStream.of(0).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
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

        when(f.apply(anyLong())).thenThrow(REGULAR_EXCEPTION);

        try {
            LongStream.of(0L).mapToObj(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(REGULAR_EXCEPTION);
        }
    }

    @Test
    public void runtimeExceptionFromLongFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingLongFunction<Type1> f = mock(ThrowingLongFunction.class);

        when(f.apply(anyLong())).thenThrow(RUNTIME_EXCEPTION);

        try {
            LongStream.of(0L).mapToObj(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(RUNTIME_EXCEPTION);
        }
    }

    @Test
    public void errorFromLongFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingLongFunction<Type1> f = mock(ThrowingLongFunction.class);

        when(f.apply(anyLong())).thenThrow(ERROR);

        try {
            LongStream.of(0L).mapToObj(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void regularExceptionFromLongToIntFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingLongToIntFunction f
            = mock(ThrowingLongToIntFunction.class);

        when(f.apply(anyLong())).thenThrow(REGULAR_EXCEPTION);

        try {
            LongStream.of(0L).mapToInt(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(REGULAR_EXCEPTION);
        }
    }

    @Test
    public void runtimeExceptionFromLongToIntFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingLongToIntFunction f
            = mock(ThrowingLongToIntFunction.class);

        when(f.apply(anyLong())).thenThrow(RUNTIME_EXCEPTION);

        try {
            LongStream.of(0L).mapToInt(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(RUNTIME_EXCEPTION);
        }
    }

    @Test
    public void errorFromLongToIntFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingLongToIntFunction f
            = mock(ThrowingLongToIntFunction.class);

        when(f.apply(anyLong())).thenThrow(ERROR);

        try {
            LongStream.of(0L).mapToInt(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void regularExceptionFromLongToDoubleFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingLongToDoubleFunction f
            = mock(ThrowingLongToDoubleFunction.class);

        when(f.apply(anyLong())).thenThrow(REGULAR_EXCEPTION);

        try {
            LongStream.of(0L).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(REGULAR_EXCEPTION);
        }
    }

    @Test
    public void runtimeExceptionFromLongToDoubleFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingLongToDoubleFunction f
            = mock(ThrowingLongToDoubleFunction.class);

        when(f.apply(anyLong())).thenThrow(RUNTIME_EXCEPTION);

        try {
            LongStream.of(0L).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(RUNTIME_EXCEPTION);
        }
    }

    @Test
    public void errorFromLongToDoubleFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingLongToDoubleFunction f
            = mock(ThrowingLongToDoubleFunction.class);

        when(f.apply(anyLong())).thenThrow(ERROR);

        try {
            LongStream.of(0L).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
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

        when(f.apply(anyDouble())).thenThrow(REGULAR_EXCEPTION);

        try {
            DoubleStream.of(0.0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(REGULAR_EXCEPTION);
        }
    }

    @Test
    public void runtimeExceptionFromDoubleFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingDoubleFunction<Type1> f
            = mock(ThrowingDoubleFunction.class);

        when(f.apply(anyDouble())).thenThrow(RUNTIME_EXCEPTION);

        try {
            DoubleStream.of(0.0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(RUNTIME_EXCEPTION);
        }
    }

    @Test
    public void errorFromDoubleFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingDoubleFunction<Type1> f
            = mock(ThrowingDoubleFunction.class);

        when(f.apply(anyDouble())).thenThrow(ERROR);

        try {
            DoubleStream.of(0.0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void regularExceptionFromDoubleToIntFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingDoubleToIntFunction f
            = mock(ThrowingDoubleToIntFunction.class);

        when(f.apply(anyDouble())).thenThrow(REGULAR_EXCEPTION);

        try {
            DoubleStream.of(0.0).mapToInt(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(REGULAR_EXCEPTION);
        }
    }

    @Test
    public void runtimeExceptionFromDoubleToIntFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingDoubleToIntFunction f
            = mock(ThrowingDoubleToIntFunction.class);

        when(f.apply(anyDouble())).thenThrow(RUNTIME_EXCEPTION);

        try {
            DoubleStream.of(0.0).mapToInt(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(RUNTIME_EXCEPTION);
        }
    }

    @Test
    public void errorFromDoubleToIntFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingDoubleToIntFunction f
            = mock(ThrowingDoubleToIntFunction.class);

        when(f.apply(anyDouble())).thenThrow(ERROR);

        try {
            DoubleStream.of(0.0).mapToInt(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void regularExceptionFromDoubleToLongFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingDoubleToLongFunction f
            = mock(ThrowingDoubleToLongFunction.class);

        when(f.apply(anyDouble())).thenThrow(REGULAR_EXCEPTION);

        try {
            DoubleStream.of(0.0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(REGULAR_EXCEPTION);
        }
    }

    @Test
    public void runtimeExceptionFromDoubleToLongFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingDoubleToLongFunction f
            = mock(ThrowingDoubleToLongFunction.class);

        when(f.apply(anyDouble())).thenThrow(RUNTIME_EXCEPTION);

        try {
            DoubleStream.of(0.0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(RUNTIME_EXCEPTION);
        }
    }

    @Test
    public void errorFromDoubleToLongFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingDoubleToLongFunction f
            = mock(ThrowingDoubleToLongFunction.class);

        when(f.apply(anyDouble())).thenThrow(ERROR);

        try {
            DoubleStream.of(0.0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}
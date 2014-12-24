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
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static com.github.fge.lambdas.helpers.Throwables.UNCHECKED;
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
    public void checkedExceptionFromFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingFunction<Type1, Type2> f = mock(ThrowingFunction.class);

        when(f.apply(any(Type1.class))).thenThrow(CHECKED);

        try {
            /*
             * Note that WE MUST include a terminal operation function!
             * Otherwise the stream is not consumed. Meh.
             */
            Stream.of(mock(Type1.class)).map(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionFromFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingFunction<Type1, Type2> f = mock(ThrowingFunction.class);

        when(f.apply(any(Type1.class))).thenThrow(UNCHECKED);

        try {
            Stream.of(mock(Type1.class)).map(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
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
    public void checkedExceptionFromIntFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingIntFunction<Type1> f = mock(ThrowingIntFunction.class);

        when(f.apply(anyInt())).thenThrow(CHECKED);

        try {
            IntStream.of(0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionFromIntFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingIntFunction<Type1> f = mock(ThrowingIntFunction.class);

        when(f.apply(anyInt())).thenThrow(UNCHECKED);

        try {
            IntStream.of(0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
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
    public void checkedExceptionFromIntToLongFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingIntToLongFunction f
            = mock(ThrowingIntToLongFunction.class);

        when(f.apply(anyInt())).thenThrow(CHECKED);

        try {
            IntStream.of(0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionFromIntToLongFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingIntToLongFunction f
            = mock(ThrowingIntToLongFunction.class);

        when(f.apply(anyInt())).thenThrow(UNCHECKED);

        try {
            IntStream.of(0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
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
    public void checkedExceptionFromIntToDoubleFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingIntToDoubleFunction f
            = mock(ThrowingIntToDoubleFunction.class);

        when(f.apply(anyInt())).thenThrow(CHECKED);

        try {
            IntStream.of(0).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionFromIntToDoubleFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingIntToDoubleFunction f
            = mock(ThrowingIntToDoubleFunction.class);

        when(f.apply(anyInt())).thenThrow(UNCHECKED);

        try {
            IntStream.of(0).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
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
    public void checkedExceptionFromLongFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingLongFunction<Type1> f = mock(ThrowingLongFunction.class);

        when(f.apply(anyLong())).thenThrow(CHECKED);

        try {
            LongStream.of(0L).mapToObj(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionFromLongFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingLongFunction<Type1> f = mock(ThrowingLongFunction.class);

        when(f.apply(anyLong())).thenThrow(UNCHECKED);

        try {
            LongStream.of(0L).mapToObj(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
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
    public void checkedExceptionFromLongToIntFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingLongToIntFunction f
            = mock(ThrowingLongToIntFunction.class);

        when(f.apply(anyLong())).thenThrow(CHECKED);

        try {
            LongStream.of(0L).mapToInt(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionFromLongToIntFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingLongToIntFunction f
            = mock(ThrowingLongToIntFunction.class);

        when(f.apply(anyLong())).thenThrow(UNCHECKED);

        try {
            LongStream.of(0L).mapToInt(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
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
    public void checkedExceptionFromLongToDoubleFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingLongToDoubleFunction f
            = mock(ThrowingLongToDoubleFunction.class);

        when(f.apply(anyLong())).thenThrow(CHECKED);

        try {
            LongStream.of(0L).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionFromLongToDoubleFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingLongToDoubleFunction f
            = mock(ThrowingLongToDoubleFunction.class);

        when(f.apply(anyLong())).thenThrow(UNCHECKED);

        try {
            LongStream.of(0L).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
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
    public void checkedExceptionFromDoubleFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingDoubleFunction<Type1> f
            = mock(ThrowingDoubleFunction.class);

        when(f.apply(anyDouble())).thenThrow(CHECKED);

        try {
            DoubleStream.of(0.0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionFromDoubleFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingDoubleFunction<Type1> f
            = mock(ThrowingDoubleFunction.class);

        when(f.apply(anyDouble())).thenThrow(UNCHECKED);

        try {
            DoubleStream.of(0.0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
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
    public void checkedExceptionFromDoubleToIntFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingDoubleToIntFunction f
            = mock(ThrowingDoubleToIntFunction.class);

        when(f.apply(anyDouble())).thenThrow(CHECKED);

        try {
            DoubleStream.of(0.0).mapToInt(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionFromDoubleToIntFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingDoubleToIntFunction f
            = mock(ThrowingDoubleToIntFunction.class);

        when(f.apply(anyDouble())).thenThrow(UNCHECKED);

        try {
            DoubleStream.of(0.0).mapToInt(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
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
    public void checkedExceptionFromDoubleToLongFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingDoubleToLongFunction f
            = mock(ThrowingDoubleToLongFunction.class);

        when(f.apply(anyDouble())).thenThrow(CHECKED);

        try {
            DoubleStream.of(0.0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(ThrownFromLambdaException.class);
        } catch (ThrownFromLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionFromDoubleToLongFunctionIsThrownAsIs()
        throws Throwable
    {
        final ThrowingDoubleToLongFunction f
            = mock(ThrowingDoubleToLongFunction.class);

        when(f.apply(anyDouble())).thenThrow(UNCHECKED);

        try {
            DoubleStream.of(0.0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
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
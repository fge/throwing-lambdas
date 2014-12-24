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
    public void checkedExceptionThrownByFunctionIsWrapped()
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
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionThrownByFunctionIsThrownAsIs()
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
    public void errorThrownByFunctionIsThrownAsIs()
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
    public void checkedExceptionThrownByIntFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingIntFunction<Type1> f = mock(ThrowingIntFunction.class);

        when(f.apply(anyInt())).thenThrow(CHECKED);

        try {
            IntStream.of(0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionThrownByIntFunctionIsThrownAsIs()
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
    public void errorThrownByIntFunctionIsThrownAsIs()
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
    public void checkedExceptionThrownByIntToLongFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingIntToLongFunction f
            = mock(ThrowingIntToLongFunction.class);

        when(f.apply(anyInt())).thenThrow(CHECKED);

        try {
            IntStream.of(0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionThrownByIntToLongFunctionIsThrownAsIs()
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
    public void errorThrownByIntToLongFunctionIsThrownAsIs()
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
    public void checkedExceptionThrownByIntToDoubleFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingIntToDoubleFunction f
            = mock(ThrowingIntToDoubleFunction.class);

        when(f.apply(anyInt())).thenThrow(CHECKED);

        try {
            IntStream.of(0).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionThrownByIntToDoubleFunctionIsThrownAsIs()
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
    public void errorThrownByIntToDoubleFunctionIsThrownAsIs()
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
    public void checkedExceptionThrownByLongFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingLongFunction<Type1> f = mock(ThrowingLongFunction.class);

        when(f.apply(anyLong())).thenThrow(CHECKED);

        try {
            LongStream.of(0L).mapToObj(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionThrownByLongFunctionIsThrownAsIs()
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
    public void errorThrownByLongFunctionIsThrownAsIs()
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
    public void checkedExceptionThrownByLongToIntFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingLongToIntFunction f
            = mock(ThrowingLongToIntFunction.class);

        when(f.apply(anyLong())).thenThrow(CHECKED);

        try {
            LongStream.of(0L).mapToInt(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionThrownByLongToIntFunctionIsThrownAsIs()
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
    public void errorThrownByLongToIntFunctionIsThrownAsIs()
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
    public void checkedExceptionThrownByLongToDoubleFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingLongToDoubleFunction f
            = mock(ThrowingLongToDoubleFunction.class);

        when(f.apply(anyLong())).thenThrow(CHECKED);

        try {
            LongStream.of(0L).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionThrownByLongToDoubleFunctionIsThrownAsIs()
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
    public void errorThrownByLongToDoubleFunctionIsThrownAsIs()
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
    public void checkedExceptionThrownByDoubleFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingDoubleFunction<Type1> f
            = mock(ThrowingDoubleFunction.class);

        when(f.apply(anyDouble())).thenThrow(CHECKED);

        try {
            DoubleStream.of(0.0).mapToObj(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionThrownByDoubleFunctionIsThrownAsIs()
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
    public void errorThrownByDoubleFunctionIsThrownAsIs()
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
    public void checkedExceptionThrownByDoubleToIntFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingDoubleToIntFunction f
            = mock(ThrowingDoubleToIntFunction.class);

        when(f.apply(anyDouble())).thenThrow(CHECKED);

        try {
            DoubleStream.of(0.0).mapToInt(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionThrownByDoubleToIntFunctionIsThrownAsIs()
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
    public void errorThrownByDoubleToIntFunctionIsThrownAsIs()
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
    public void checkedExceptionThrownByDoubleToLongFunctionIsWrapped()
        throws Throwable
    {
        final ThrowingDoubleToLongFunction f
            = mock(ThrowingDoubleToLongFunction.class);

        when(f.apply(anyDouble())).thenThrow(CHECKED);

        try {
            DoubleStream.of(0.0).mapToLong(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void uncheckedExceptionThrownByDoubleToLongFunctionIsThrownAsIs()
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
    public void errorThrownByDoubleToLongFunctionIsThrownAsIs()
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
package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import static com.github.fge.lambdas.functions.Functions.rethrow;
import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static com.github.fge.lambdas.helpers.Throwables.ERROR;
import static com.github.fge.lambdas.helpers.Throwables.UNCHECKED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SuppressWarnings("ErrorNotRethrown")
public final class RethrowFunctionsTest
{
    @Test
    public void wrappedFunctionThrowsAppropriateException()
    {
        ThrowingFunction<Type1, Type2> f;

        f = t -> { throw CHECKED; };

        try {
            Stream.of(Type1.mock()).map(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        f = t -> { throw UNCHECKED; };

        try {
            Stream.of(Type1.mock()).map(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        f = t -> { throw ERROR; };

        try {
            Stream.of(Type1.mock()).map(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedToIntFunctionThrowsAppropriateException()
    {
        ThrowingToIntFunction<Type1> f;

        f = value -> { throw CHECKED; };

        try {
            Stream.of(Type1.mock()).mapToInt(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        f = value -> { throw UNCHECKED; };

        try {
            Stream.of(Type1.mock()).mapToInt(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        f = value -> { throw ERROR; };

        try {
            Stream.of(Type1.mock()).mapToInt(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedToLongFunctionThrowsAppropriateException()
    {
        ThrowingToLongFunction<Type1> f;

        f = value -> { throw CHECKED; };

        try {
            Stream.of(Type1.mock()).mapToLong(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        f = value -> { throw UNCHECKED; };

        try {
            Stream.of(Type1.mock()).mapToLong(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        f = value -> { throw ERROR; };

        try {
            Stream.of(Type1.mock()).mapToLong(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedToDoubleFunctionThrowsAppropriateException()
    {
        ThrowingToDoubleFunction<Type1> f;

        f = value -> { throw CHECKED; };

        try {
            Stream.of(Type1.mock()).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        f = value -> { throw UNCHECKED; };

        try {
            Stream.of(Type1.mock()).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        f = value -> { throw ERROR; };

        try {
            Stream.of(Type1.mock()).mapToDouble(rethrow(f)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }

    @Test
    public void wrappedBiFunctionThrowsAppropriateException()
    {
        @SuppressWarnings("unchecked")
        final BinaryOperator<Type2> o = Mockito.mock(BinaryOperator.class);
        when(o.apply(Type2.any(), Type2.any())).thenReturn(Type2.mock());

        ThrowingBiFunction<Type2, Type1, Type2> f;

        f = (t, u) -> { throw CHECKED; };

        try {
            Stream.of(Type1.mock()).reduce(Type2.mock(), rethrow(f), o);
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        f = (t, u) -> { throw UNCHECKED; };

        try {
            Stream.of(Type1.mock()).reduce(Type2.mock(), rethrow(f), o);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        f = (t, u) -> { throw ERROR; };

        try {
            Stream.of(Type1.mock()).reduce(Type2.mock(), rethrow(f), o);
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}
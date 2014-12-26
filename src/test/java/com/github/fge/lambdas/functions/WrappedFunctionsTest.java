package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import org.testng.annotations.Test;

import static com.github.fge.lambdas.functions.Functions.wrap;
import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static org.assertj.core.api.Assertions.assertThat;

public final class WrappedFunctionsTest
{
    @Test
    public void wrappedFunctionDoesWhatIsExpected()
    {
        final ThrowingFunction<Type1, Type2> f
            = t -> { throw CHECKED; };

        final Type2 expected = Type2.mock();
        final Type2 actual = wrap(f).orReturn(expected).apply(Type1.mock());

        assertThat(actual).isSameAs(expected);

        try {
            wrap(f).orThrow(MyException.class).apply(Type1.mock());
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void wrappedToIntFunctionDoesWhatIsExpected()
    {
        final ThrowingToIntFunction<Type1> f = value -> { throw CHECKED; };

        final int expected = 42; // perfectly random, and the universal answer
        final int actual = wrap(f).orReturn(expected).applyAsInt(Type1.mock());

        assertThat(actual).isEqualTo(expected);

        try {
            wrap(f).orThrow(MyException.class).applyAsInt(Type1.mock());
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void wrappedToLongFunctionDoesWhatIsExpected()
    {
        final ThrowingToLongFunction<Type1> f = value -> { throw CHECKED; };

        final long expected = 42L;
        final long actual = wrap(f).orReturn(expected)
            .applyAsLong(Type1.mock());

        assertThat(actual).isEqualTo(expected);

        try {
            wrap(f).orThrow(MyException.class).applyAsLong(Type1.mock());
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void wrappedToDoubleFunctionDoesWhatIsExpected()
    {
        final ThrowingToDoubleFunction<Type1> f = value -> { throw CHECKED; };

        final double expected = 42.0;
        final double actual = wrap(f).orReturn(expected)
            .applyAsDouble(Type1.mock());

        assertThat(actual).isEqualTo(expected);

        try {
            wrap(f).orThrow(MyException.class).applyAsDouble(Type1.mock());
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }
}

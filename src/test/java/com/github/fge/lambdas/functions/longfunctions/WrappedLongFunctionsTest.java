package com.github.fge.lambdas.functions.longfunctions;

import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import static com.github.fge.lambdas.functions.Functions.wrap;
import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static org.assertj.core.api.Assertions.assertThat;

public final class WrappedLongFunctionsTest
{
    @Test
    public void wrappedLongFunctionDoesWhatIsExpected()
    {
        final ThrowingLongFunction<Type1> f = value -> { throw CHECKED; };

        final Type1 expected = Type1.mock();
        final Type1 actual = wrap(f).orReturn(expected).apply(0L);

        assertThat(actual).isSameAs(expected);

        try {
            wrap(f).orThrow(MyException.class).apply(0L);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void wrappedLongToIntFunctionDoesWhatIsExpected()
    {
        final ThrowingLongToIntFunction f = value -> { throw CHECKED; };

        final int expected = 42;
        final int actual = wrap(f).orReturn(expected).applyAsInt(0L);

        assertThat(actual).isEqualTo(expected);

        try {
            wrap(f).orThrow(MyException.class).applyAsInt(0L);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void wrappedLongToDoubleFunctionDoesWhatIsExpected()
    {
        final ThrowingLongToDoubleFunction f = value -> { throw CHECKED; };

        final double expected = 42.0;
        final double actual = wrap(f).orReturn(expected).applyAsDouble(0L);

        assertThat(actual).isEqualTo(expected);

        try {
            wrap(f).orThrow(MyException.class).applyAsDouble(0L);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }
}

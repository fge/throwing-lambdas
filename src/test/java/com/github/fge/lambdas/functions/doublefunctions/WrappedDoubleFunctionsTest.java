package com.github.fge.lambdas.functions.doublefunctions;

import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import static com.github.fge.lambdas.functions.Functions.wrap;
import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static org.assertj.core.api.Assertions.assertThat;

public final class WrappedDoubleFunctionsTest
{
    @Test
    public void wrappedDoubleFunctionDoesWhatIsExpected()
    {
        final ThrowingDoubleFunction<Type1> f = value -> { throw CHECKED; };

        final Type1 expected = Type1.mock();
        final Type1 actual = wrap(f).orReturn(expected).apply(0.0);

        assertThat(actual).isSameAs(expected);

        try {
            wrap(f).orThrow(MyException.class).apply(0.0);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void wrappedDoubleToIntFunctionDoesWhatIsExpected()
    {
        final ThrowingDoubleToIntFunction f = value -> { throw CHECKED; };

        final int expected = 42;
        final int actual = wrap(f).orReturn(expected).applyAsInt(0.0);

        assertThat(actual).isEqualTo(expected);

        try {
            wrap(f).orThrow(MyException.class).applyAsInt(0.0);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void wrappedDoubleToLongFunctionDoesWhatIsExpected()
    {
        final ThrowingDoubleToLongFunction f = value -> { throw CHECKED; };

        final long expected = 42L;
        final long actual = wrap(f).orReturn(expected).applyAsLong(0.0);

        assertThat(actual).isEqualTo(expected);

        try {
            wrap(f).orThrow(MyException.class).applyAsLong(0.0);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }
}

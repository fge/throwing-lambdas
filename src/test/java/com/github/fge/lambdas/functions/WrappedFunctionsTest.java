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
}

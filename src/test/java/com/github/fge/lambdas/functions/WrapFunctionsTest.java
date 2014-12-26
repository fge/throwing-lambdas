package com.github.fge.lambdas.functions;

import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static com.github.fge.lambdas.functions.Functions.wrap;
import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static org.assertj.core.api.Assertions.assertThat;

public final class WrapFunctionsTest
{
    @Test
    public void wrappedFunctionDoesWhatIsExpected()
    {
        final ThrowingFunction<Type1, Type2> f
            = wrap(t -> { throw CHECKED; });

        final Type2 defaultValue = Type2.mock();
        final Optional<Type2> optional = Stream.of(Type1.mock())
            .map(f.orReturn(defaultValue))
            .findFirst();

        assertThat(optional.isPresent()).isTrue();
        assertThat(optional.get()).isSameAs(defaultValue);

        try {
            Stream.of(Type1.mock()).map(f.orThrow(MyException.class)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }
}

package com.github.fge.lambdas.comparators;

import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import static com.github.fge.lambdas.comparators.Comparators.wrap;
import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static org.assertj.core.api.Assertions.assertThat;

public final class WrappedComparatorsTest
{
    @Test
    public void wrappedComparatorDoesWhatIsExpected()
    {
        final Type1 t1 = Type1.mock();
        final Type1 t2 = Type1.mock();

        final int defaultValue = 42;

        ThrowingComparator<Type1> c;

        c = (o1, o2) -> 3;

        assertThat(wrap(c).orReturn(defaultValue).compare(t1, t2))
            .isEqualTo(3);
        assertThat(wrap(c).orThrow(MyException.class).compare(t1, t2))
            .isEqualTo(3);

        c = (o1, o2) -> { throw CHECKED; };

        assertThat(wrap(c).orReturn(defaultValue).compare(t1, t2))
            .isEqualTo(defaultValue);

        try {
            wrap(c).orThrow(MyException.class).compare(t1, t2);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }
}

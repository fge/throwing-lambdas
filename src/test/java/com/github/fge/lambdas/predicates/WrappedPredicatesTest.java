package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static com.github.fge.lambdas.predicates.Predicates.wrap;
import static org.assertj.core.api.Assertions.assertThat;

public final class WrappedPredicatesTest
{
    @Test
    public void wrappedPredicateDoesWhatIsExpected()
    {
        final ThrowingPredicate<Type1> p = t -> { throw CHECKED; };

        final Type1 t = Type1.mock();

        assertThat(wrap(p).orReturn(false).test(t)).isFalse();

        try {
            wrap(p).orThrow(MyException.class).test(t);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void wrappedIntPredicateDoesWhatIsExpected()
    {
        final ThrowingIntPredicate p = value -> { throw CHECKED; };

        final int tested = 42;

        assertThat(wrap(p).orReturn(false).test(tested)).isFalse();

        try {
            wrap(p).orThrow(MyException.class).test(tested);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void wrappedLongPredicateDoesWhatIsExpected()
    {
        final ThrowingLongPredicate p = value -> { throw CHECKED; };

        final long tested = 42L;

        assertThat(wrap(p).orReturn(false).test(tested)).isFalse();

        try {
            wrap(p).orThrow(MyException.class).test(tested);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void wrappedDoublePredicateDoesWhatIsExpected()
    {
        final ThrowingDoublePredicate p = value -> { throw CHECKED; };

        final double tested = 42.0;

        assertThat(wrap(p).orReturn(false).test(tested)).isFalse();

        try {
            wrap(p).orThrow(MyException.class).test(tested);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }
}

package com.github.fge.lambdas.comparators;

import com.github.fge.lambdas.ThrownByLambdaException;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.stream.Stream;

import static com.github.fge.lambdas.comparators.Comparators.rethrow;
import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static com.github.fge.lambdas.helpers.Throwables.ERROR;
import static com.github.fge.lambdas.helpers.Throwables.UNCHECKED;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("ErrorNotRethrown")
public final class RethrowComparatorsTest
{
    @Test
    public void wrappedComparatorThrowsAppropriateException()
    {
        ThrowingComparator<Type1> c;

        c = (o1, o2) -> { throw CHECKED; };

        try {
            Stream.of(Type1.mock(), Type1.mock()).max(rethrow(c));
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        c = (o1, o2) -> { throw UNCHECKED; };

        try {
            Stream.of(Type1.mock(), Type1.mock()).max(rethrow(c));
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        c = (o1, o2) -> { throw ERROR; };

        try {
            Stream.of(Type1.mock(), Type1.mock()).max(rethrow(c));
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}

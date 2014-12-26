package com.github.fge.lambdas.suppliers;

import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import static com.github.fge.lambdas.suppliers.Suppliers.wrap;
import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static org.assertj.core.api.Assertions.assertThat;

public final class WrappedSuppliersTest
{
    @Test
    public void wrappedSupplierDoesWhatIsExpected()
    {
        final ThrowingSupplier<Type1> s = () -> { throw CHECKED; };

        final Type1 expected = Type1.mock();
        final Type1 actual = wrap(s).orReturn(expected).get();

        assertThat(actual).isSameAs(expected);

        try {
            wrap(s).orThrow(MyException.class).get();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }
    
    @Test
    public void wrappedIntSupplierDoesWhatIsExpected()
    {
        final ThrowingIntSupplier s = () -> { throw CHECKED; };

        final int expected = 0xD0; // I have no idea what this is but eh.
        final int actual = wrap(s).orReturn(expected).getAsInt();

        assertThat(actual).isSameAs(expected);

        try {
            wrap(s).orThrow(MyException.class).getAsInt();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }
    
    @Test
    public void wrappedDoubleSupplierDoesWhatIsExpected()
    {
        final ThrowingDoubleSupplier s = () -> { throw CHECKED; };

        final double expected = 12.345; // Yeah sequence!
        final double actual = wrap(s).orReturn(expected).getAsDouble();

        assertThat(actual).isSameAs(expected);

        try {
            wrap(s).orThrow(MyException.class).getAsDouble();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }
    
    @Test
    public void wrappedLongSupplierDoesWhatIsExpected()
    {
        final ThrowingLongSupplier s = () -> { throw CHECKED; };

        final long expected = 316006L; // "GOOGLE" upside down on a calculator
        final long actual = wrap(s).orReturn(expected).getAsLong();

        assertThat(actual).isSameAs(expected);

        try {
            wrap(s).orThrow(MyException.class).getAsLong();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }
}

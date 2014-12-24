package com.github.fge.lambdas;

import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.suppliers.ThrowingSupplier;
import org.testng.annotations.Test;

import java.util.stream.Stream;

import static com.github.fge.lambdas.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.Rethrow.rethrow;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static com.github.fge.lambdas.helpers.Throwables.ERROR;
import static com.github.fge.lambdas.helpers.Throwables.UNCHECKED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({ "unchecked", "ProhibitedExceptionDeclared",
    "ErrorNotRethrown" })
public final class RethrowSuppliersTest
{
    @Test
    public void wrappedSupplierThrowsAppropriateExceptions()
        throws Throwable
    {
        final ThrowingSupplier<Type1> s = mock(ThrowingSupplier.class);

        when(s.get()).thenThrow(CHECKED).thenThrow(UNCHECKED)
            .thenThrow(ERROR);

        try {
            Stream.generate(rethrow(s)).count();
            shouldHaveThrown(ThrownByLambdaException.class);
        } catch (ThrownByLambdaException e) {
            assertThat(e.getCause()).isSameAs(CHECKED);
        }

        try {
            Stream.generate(rethrow(s)).count();
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isSameAs(UNCHECKED);
        }

        try {
            Stream.generate(rethrow(s)).count();
            shouldHaveThrown(Error.class);
        } catch (Error e) {
            assertThat(e).isSameAs(ERROR);
        }
    }
}

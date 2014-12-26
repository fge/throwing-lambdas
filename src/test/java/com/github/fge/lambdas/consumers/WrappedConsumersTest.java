package com.github.fge.lambdas.consumers;

import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;
import com.github.fge.lambdas.helpers.Type2;

import org.testng.annotations.Test;

import static com.github.fge.lambdas.consumers.Consumers.wrap;
import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static org.assertj.core.api.Assertions.assertThat;

public final class WrappedConsumersTest
{
    @Test
    public void wrappedConsumerDoesWhatIsExpected()
    {
        final ThrowingConsumer<Type1> s = t -> { throw CHECKED; };

        wrap(s).orDoNothing().accept(Type1.mock());

        try {
            wrap(s).orThrow(MyException.class).accept(Type1.mock());
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }
    
    @Test
    public void wrappedIntConsumerDoesWhatIsExpected()
    {
        final ThrowingIntConsumer s = t -> { throw CHECKED; };

        wrap(s).orDoNothing().accept(42);

        try {
            wrap(s).orThrow(MyException.class).accept(42);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }
    
    @Test
    public void wrappedLongConsumerDoesWhatIsExpected()
    {
        final ThrowingLongConsumer s = t -> { throw CHECKED; };

        wrap(s).orDoNothing().accept(42L);

        try {
            wrap(s).orThrow(MyException.class).accept(42L);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }
    
    @Test
    public void wrappedDoubleConsumerDoesWhatIsExpected()
    {
        final ThrowingDoubleConsumer s = t -> { throw CHECKED; };

        wrap(s).orDoNothing().accept(42.0);

        try {
            wrap(s).orThrow(MyException.class).accept(42.0);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }
    
    @Test
    public void wrappedBiConsumerDoesWhatIsExpected()
    {
        final ThrowingBiConsumer<Type1, Type2> s = (t, u) -> { throw CHECKED; };

        wrap(s).orDoNothing().accept(Type1.mock(), Type2.mock());

        try {
            wrap(s).orThrow(MyException.class).accept(Type1.mock(), Type2.mock());
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }
    
    @Test
    public void wrappedObjIntConsumerDoesWhatIsExpected()
    {
        final ThrowingObjIntConsumer<Type1> s = (t, value) -> { throw CHECKED; };

        wrap(s).orDoNothing().accept(Type1.mock(), 42);

        try {
            wrap(s).orThrow(MyException.class).accept(Type1.mock(), 42);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }
    
    @Test
    public void wrappedObjLongConsumerrDoesWhatIsExpected()
    {
        final ThrowingObjLongConsumer<Type1> s = (t, value) -> { throw CHECKED; };

        wrap(s).orDoNothing().accept(Type1.mock(), 42L);

        try {
            wrap(s).orThrow(MyException.class).accept(Type1.mock(), 42L);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }
    
    @Test
    public void wrappedObjDoubleConsumerDoesWhatIsExpected()
    {
        final ThrowingObjDoubleConsumer<Type1> s = (t, u) -> { throw CHECKED; };

        wrap(s).orDoNothing().accept(Type1.mock(), 42.0);

        try {
            wrap(s).orThrow(MyException.class).accept(Type1.mock(), 42.0);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }
}

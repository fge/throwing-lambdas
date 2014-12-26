package com.github.fge.lambdas.functions.operators;

import com.github.fge.lambdas.helpers.MyException;
import com.github.fge.lambdas.helpers.Type1;
import org.testng.annotations.Test;

import java.util.Random;

import static com.github.fge.lambdas.functions.operators.Operators.wrap;
import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static com.github.fge.lambdas.helpers.Throwables.CHECKED;
import static org.assertj.core.api.Assertions.assertThat;

public final class WrappedOperatorsTest
{
    private final Random random = new Random(System.currentTimeMillis());

    @Test
    public void wrappedUnaryOperatorDoesWhatIsExpected()
    {
        final ThrowingUnaryOperator<Type1> o = t -> { throw CHECKED; };

        final Type1 arg = Type1.mock();
        final Type1 expected = Type1.mock();

        Type1 actual;

        actual = wrap(o).orReturnSelf().apply(arg);

        assertThat(actual).isSameAs(arg);

        actual = wrap(o).orReturn(expected).apply(arg);

        assertThat(actual).isSameAs(expected);

        try {
            wrap(o).orThrow(MyException.class).apply(arg);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void wrappedIntUnaryOperatorDoesWhatIsExpected()
    {
        final ThrowingIntUnaryOperator o = operand -> { throw CHECKED; };

        final int arg = random.nextInt();
        final int expected = 42;

        int actual;

        actual = wrap(o).orReturnSelf().applyAsInt(arg);
        assertThat(actual).isEqualTo(arg);

        actual = wrap(o).orReturn(expected).applyAsInt(arg);
        assertThat(actual).isEqualTo(expected);

        try {
            wrap(o).orThrow(MyException.class).applyAsInt(arg);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void wrappedLongUnaryOperatorDoesWhatIsExpected()
    {
        final ThrowingLongUnaryOperator o = operand -> { throw CHECKED; };

        final long arg = random.nextLong();
        final long expected = 42L;

        long actual;

        actual = wrap(o).orReturnSelf().applyAsLong(arg);
        assertThat(actual).isEqualTo(arg);

        actual = wrap(o).orReturn(expected).applyAsLong(arg);
        assertThat(actual).isEqualTo(expected);

        try {
            wrap(o).orThrow(MyException.class).applyAsLong(arg);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void wrappedDoubleUnaryOperatorDoesWhatIsExpected()
    {
        final ThrowingDoubleUnaryOperator o = operand -> { throw CHECKED; };

        final double arg = random.nextDouble();
        final double expected = 42.0;

        double actual;

        actual = wrap(o).orReturnSelf().applyAsDouble(arg);
        assertThat(actual).isEqualTo(arg);

        actual = wrap(o).orReturn(expected).applyAsDouble(arg);
        assertThat(actual).isEqualTo(expected);

        try {
            wrap(o).orThrow(MyException.class).applyAsDouble(arg);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void wrappedBinaryOperatorDoesWhatIsExpected()
    {
        final ThrowingBinaryOperator<Type1> o = (t, u) -> { throw CHECKED; };

        final Type1 left = Type1.mock();
        final Type1 right = Type1.mock();
        final Type1 expected = Type1.mock();

        Type1 actual;

        actual = wrap(o).orReturnLeft().apply(left, right);
        assertThat(actual).isSameAs(left);

        actual = wrap(o).orReturnRight().apply(left, right);
        assertThat(actual).isSameAs(right);

        actual = wrap(o).orReturn(expected).apply(left, right);
        assertThat(actual).isSameAs(expected);

        try {
            wrap(o).orThrow(MyException.class).apply(left, right);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void wrappedIntBinaryOperatorDoesWhatIsExpected()
    {
        final ThrowingIntBinaryOperator o
            = (left, right) -> { throw CHECKED; };

        final int left = -2;
        final int right = 2;
        final int expected = 42;

        int actual;

        actual = wrap(o).orReturnLeft().applyAsInt(left, right);
        assertThat(actual).isEqualTo(left);

        actual = wrap(o).orReturnRight().applyAsInt(left, right);
        assertThat(actual).isEqualTo(right);

        actual = wrap(o).orReturn(expected).applyAsInt(left, right);
        assertThat(actual).isEqualTo(expected);

        try {
            wrap(o).orThrow(MyException.class).applyAsInt(left, right);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void wrappedLongBinaryOperatorDoesWhatIsExpected()
    {
        final ThrowingLongBinaryOperator o
            = (left, right) -> { throw CHECKED; };

        final long left = -2L;
        final long right = 2L;
        final long expected = 42L;

        long actual;

        actual = wrap(o).orReturnLeft().applyAsLong(left, right);
        assertThat(actual).isEqualTo(left);

        actual = wrap(o).orReturnRight().applyAsLong(left, right);
        assertThat(actual).isEqualTo(right);

        actual = wrap(o).orReturn(expected).applyAsLong(left, right);
        assertThat(actual).isEqualTo(expected);

        try {
            wrap(o).orThrow(MyException.class).applyAsLong(left, right);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }

    @Test
    public void wrappedDoubleBinaryOperatorDoesWhatIsExpected()
    {
        final ThrowingDoubleBinaryOperator o
            = (left, right) -> { throw CHECKED; };

        final double left = -2.0;
        final double right = 2.0;
        final double expected = 42.0;

        double actual;

        actual = wrap(o).orReturnLeft().applyAsDouble(left, right);
        assertThat(actual).isEqualTo(left);

        actual = wrap(o).orReturnRight().applyAsDouble(left, right);
        assertThat(actual).isEqualTo(right);

        actual = wrap(o).orReturn(expected).applyAsDouble(left, right);
        assertThat(actual).isEqualTo(expected);

        try {
            wrap(o).orThrow(MyException.class).applyAsDouble(left, right);
            shouldHaveThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isExactlyInstanceOf(MyException.class);
            assertThat(e.getCause()).isSameAs(CHECKED);
        }
    }
}

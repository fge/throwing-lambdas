package com.github.fge.lambdas;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.github.fge.lambdas.helpers.CustomAssertions.shouldHaveThrown;
import static org.assertj.core.api.Assertions.assertThat;

public final class ChainerRethrowTest
{
    private static final Throwable THROWABLE = new Throwable();

    /*
     * Case 1: constructor is there but not public
     */
    private static final class PrivateException
        extends RuntimeException
    {
        PrivateException(final Throwable cause)
        {
            super(cause);
        }
    }

    /*
     * Case 2: class is public, but no constructor
     */
    public static final class NoConstructorException
        extends RuntimeException
    {
        public NoConstructorException()
        {
        }
    }

    /*
     * Case 3: class is public constructor is there, but it throws a checked
     * exception
     */
    public static final class ThrowingConstructorException
        extends RuntimeException
    {
        public ThrowingConstructorException(final Throwable cause)
            throws Exception
        {
            throw new Exception();
        }
    }

    @DataProvider
    public Iterator<Object[]> brokenThrowables()
    {
        final List<Object[]> list = new ArrayList<>();

        list.add(new Object[] { PrivateException.class });
        list.add(new Object[] { NoConstructorException.class });
        list.add(new Object[] { ThrowingConstructorException.class });

        return list.iterator();
    }

    @Test(dataProvider = "brokenThrowables")
    public void instantiationFailsWithAppropriateException(
        final Class<? extends RuntimeException> c)
    {
        try {
            Chainer.rethrow(c, THROWABLE);
            shouldHaveThrown(Chainer.InstantiationException.class);
        } catch (Chainer.InstantiationException e) {
            final Throwable[] suppressed = e.getSuppressed();
            assertThat(suppressed).hasSize(1);
            assertThat(suppressed[0]).isSameAs(THROWABLE);
        }
    }
}

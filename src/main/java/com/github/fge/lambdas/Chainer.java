package com.github.fge.lambdas;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Base class for chaining throwing interfaces
 *
 * <p>If you want to add your own implementation of a chainer, extend this class
 * and make it implement the <em>throwing</em> version of the interface. The
 * default set by this package is to wrap the exception into a {@link
 * ThrownByLambdaException} when an exception is thrown.</p>
 *
 * <p>Since all throwing instances extend the non throwing instances by default,
 * this means you can use an instance of a chain as a non throwing instance; for
 * example:</p>
 *
 * <code>
 *     final ToLongFunction&lt;Path&gt;
 *         = Throwing.function(Files::size).orReturn(OL);
 * </code>
 *
 * @param <N> parameter type of the non throwing interface
 * @param <T> parameter type of the throwing interface
 * @param <C> parameter type of the chainer
 *
 * @see Throwing
 */
public abstract class Chainer<N, T extends N, C extends Chainer<N, T, C>>
{
    private static final Map<Class<? extends RuntimeException>, MethodHandle>
        HANDLES = new ConcurrentHashMap<>();

    private static final Lookup LOOKUP = MethodHandles.publicLookup();

    private static final MethodType TYPE
        = MethodType.methodType(void.class, Throwable.class);

    private static MethodHandle getHandle(
        final Class<? extends RuntimeException> c)
    {
        return HANDLES.computeIfAbsent(c, key -> {
            try {
                return LOOKUP.findConstructor(c, TYPE)
                    .asType(TYPE.changeReturnType(RuntimeException.class));
            } catch (IllegalAccessException | NoSuchMethodException e) {
                throw new InstantiationException(e);
            }
        });
    }

    @SuppressWarnings("unchecked")
    protected static <E extends RuntimeException> E rethrow(final Class<E> c,
        final Throwable t)
    {
        try {
            return (E) getHandle(c).invokeExact(t);
        } catch (InstantiationException e) {
            e.addSuppressed(t);
            throw e;
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable oops) {
            final RuntimeException exception = new InstantiationException(oops);
            exception.addSuppressed(t);
            throw exception;
        }
    }

    protected final T throwing;

    protected Chainer(final T throwing)
    {
        this.throwing = throwing;
    }

    /**
     * Try with another throwing instance if the first instance fails
     *
     * @param other the other throwing instance
     * @return a new chain
     */
    public abstract C orTryWith(T other);

    /**
     * Rethrow the exception using a custom (unchecked!) exception class if this
     * throwing instance fails
     *
     * <p>Your custom exception class <strong>must</strong> have a constructor
     * accepting a single {@link Throwable} as an argument.</p>
     *
     * <p>The original exception thrown will be the {@link Throwable#getCause()
     * cause} of the generated exception.</p>
     *
     * <p>All instances of {@link Error} or {@link RuntimeException} thrown by
     * the instance are thrown as is.</p>
     *
     * @param exclass the exception class
     * @param <E> type parameter of the exception class
     * @return a throwing instance
     */
    public abstract <E extends RuntimeException> T orThrow(Class<E> exclass);

    /**
     * Fall back to a non throwing instance if this instance fails
     *
     * @param fallback the fallback instance
     * @return a non throwing instance
     */
    public abstract N fallbackTo(N fallback);

    /**
     * Exception thrown when an instance of an exception class cannot be built
     *
     * @see #orThrow(Class)
     */
    public static final class InstantiationException
        extends RuntimeException
    {
        InstantiationException(final Throwable cause)
        {
            super(cause);
        }
    }
}

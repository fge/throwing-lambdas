package com.github.fge.lambdas;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Chain<N, T extends N, C extends Chain<N, T, C>>
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

    protected Chain(final T throwing)
    {
        this.throwing = throwing;
    }

    public abstract C orTryWith(T other);

    public abstract <E extends RuntimeException> T orThrow(Class<E> exclass);

    public abstract N fallbackTo(N fallback);

    /**
     * Exception thrown by {@link #rethrow(Class, Throwable)} when an instance
     * of an exception class cannot be built
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

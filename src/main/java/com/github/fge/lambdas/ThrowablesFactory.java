package com.github.fge.lambdas;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum ThrowablesFactory
{
    INSTANCE;

    private final Map<Class<? extends RuntimeException>, MethodHandle> handles
        = new ConcurrentHashMap<>();

    private static final MethodHandles.Lookup LOOKUP
        = MethodHandles.publicLookup();

    private static final MethodType TYPE
        = MethodType.methodType(void.class, Throwable.class);

    private MethodHandle getHandle(final Class<? extends RuntimeException> c)
    {
        return handles.computeIfAbsent(c, key -> {
            try {
                return LOOKUP.findConstructor(c, TYPE)
                    .asType(TYPE.changeReturnType(RuntimeException.class));
            } catch (IllegalAccessException | NoSuchMethodException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    public <E extends RuntimeException> E get(final Class<E> c,
        final Throwable t)
    {
        try {
            return (E) getHandle(c).invokeExact(t);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable oops) {
            throw new IllegalStateException(oops);
        }
    }
}

package com.github.fge.lambdas;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * An "enum singleton" for building {@link RuntimeException} instances
 *
 * <p>This factory is used to build instances of runtime exception classes
 * which you specify using {@link Chain#orThrow(Class)}, for instance:</p>
 *
 * <pre>
 *     final ThrowingFunction&lt;Foo, Bar&gt; f
 *         = Throwing.function(some::reference).orThrow(MyException.class);
 * </pre>
 *
 * <p>It is thread safe and uses a simple {@link ConcurrentHashMap} to collect
 * constructors of exception classes (as {@link MethodHandle}s).</p>
 *
 * <p>Your exception class <strong>must</strong> have a constructor accepting a
 * single {@link Throwable} as an argument. When the throwing lambda throws a
 * checked exception, this factory will build an instance of your exception
 * class and pass the checked exception as an argument to the constructor.</p>
 *
 * <p>In code, you will then be able to access the exception thrown originally
 * by invoking {@link Throwable#getCause()}.</p>
 *
 * @see RuntimeException#RuntimeException(Throwable)
 * @see MethodHandles#publicLookup()
 * @see Lookup#findConstructor(Class, MethodType)
 * @see MethodHandle#invokeExact(Object...)
 * @see Map#computeIfAbsent(Object, Function)
 */
public enum ThrowablesFactory
{
    INSTANCE;

    private final Map<Class<? extends RuntimeException>, MethodHandle> handles
        = new ConcurrentHashMap<>();

    private static final Lookup LOOKUP
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
                throw new InstantiationException(e);
            }
        });
    }

    /**
     * Build a new instance of a runtime exception
     *
     * <p>If the exception cannot be built for a reason or another, this method
     * throws an {@link InstantiationException}. In this exception:</p>
     *
     * <ul>
     *     <li>the exception having caused the instantiation failure is set as
     *     the {@link Throwable#getCause() cause};</li>
     *     <li>the {@link Throwable} thrown by the lambda is {@link
     *     Throwable#addSuppressed(Throwable) suppressed} (use {@link
     *     Throwable#getSuppressed()} to access it).</li>
     * </ul>
     *
     * @param c the runtime exception class
     * @param t the throwable to attach
     * @param <E> the type of the runtime exception class
     * @return an instance of this runtime exception with the throwable passed
     * as an argument
     *
     * @throws InstantiationException see description
     * @throws Error unexpected error when building the instance
     * @throws RuntimeException unexpected exception when building the instance
     */
    @SuppressWarnings("unchecked")
    public <E extends RuntimeException> E get(final Class<E> c,
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

    /**
     * Exception thrown by {@link #get(Class, Throwable)} when an instance of an
     * exception class cannot be built
     */
    public static final class InstantiationException
        extends RuntimeException
    {
        public InstantiationException(final Throwable cause)
        {
            super(cause);
        }
    }
}

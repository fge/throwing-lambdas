package com.github.fge.lambdas.v2;

public abstract class Chain<N, T extends N, C extends Chain<N, T, C>>
{
    protected final T throwing;

    protected Chain(final T throwing)
    {
        this.throwing = throwing;
    }

    public abstract C orTryWith(T other);

    public abstract <E extends RuntimeException> T orThrow(Class<E> exclass);

    public abstract N fallbackTo(N fallback);

    public final <E extends RuntimeException> T as(final Class<E> exclass)
    {
        return orThrow(exclass);
    }
}

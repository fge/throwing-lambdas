package com.github.fge.lambdas.v2;

public abstract class Chainer<N, T extends N, C extends Chainer<N, T, C>>
{
    protected final T throwing;

    protected Chainer(final T throwing)
    {
        this.throwing = throwing;
    }

    public abstract C orTryWith(T other);

    public abstract <E extends RuntimeException> T orThrow(
        final Class<E> exclass);

    public abstract N fallbackTo(N fallback);

    public final <E extends RuntimeException> T as(final Class<E> exclass)
    {
        return orThrow(exclass);
    }
}

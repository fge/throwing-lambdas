package com.github.fge.lambdas.predicates;

import com.github.fge.lambdas.helpers.Type1;
import org.mockito.Mockito;

public class SpiedThrowingPredicate
    implements ThrowingPredicate<Type1>
{
    public static ThrowingPredicate<Type1> newSpy()
    {
        return Mockito.spy(new SpiedThrowingPredicate());
    }

     @Override
     public boolean doTest(final Type1 t)
         throws Throwable
     {
          return false;
     }
}

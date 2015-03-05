## 0.4.0

* Refactor to use an abstract class as the base.
* ThrowingFunctionalInterface has disappeared: it is now up to the abstract
  class to chain.
* Introduce Throwing.*() to replace Functions, Comparators etc etc.

## 0.3.0

* Add new .tryWith() methods.
* Add new ThrowingFunctionalInterface interface; make all interfaces extend it.
  All interfaces now inherit .orTryWith(), .orThrow() and .fallbackTo() from it.
* Javadoc. Incomplete.
* Rewrite all tests.
* Fix ThrowablesFactory exception handling.

## 0.2.0

* Add new .wrap() methods.
* Add .orThrow() methods to Throwing* interfaces.
* Add .or{Return*,DoNothing}() methods to Throwing* interfaces.

## 0.1.0

* First version

/**
 * Provides functional programming constructs for handling computations that may result in success or failure.
 *
 * <p>This package includes:</p>
 * <ul>
 *   <li>{@code Either<L, R>}: A sealed class representing a value that can be either a {@code Left} (error) or a {@code Right} (success).</li>
 *   <li>{@code Try<T>}: A monadic container representing computations that may throw exceptions.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 *   Either<String, Integer> result = Either.right(42);
 *   if (result.isRight()) {
 *       System.out.println("Success: " + result.getRight());
 *   } else {
 *       System.out.println("Error: " + result.getLeft());
 *   }
 * </pre>
 *
 * <h2>Thread Safety:</h2>
 * <p>Instances of the classes in this package are immutable and therefore thread-safe.</p>
 *
 * @since 1.0
 * @author Fungover
 */
package org.fungover.breeze.funclib.control;
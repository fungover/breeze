/**
 * This package provides an implementation of the {@code Option} type,
 * which represents a value that may be present ({@code Some}) or absent ({@code None}).
 *
 * <p>The {@code Option} type is a functional alternative to {@code null},
 * promoting safer and more predictable handling of optional values.</p>
 *
 * <h2>Core Classes:</h2>
 * <ul>
 *   <li>{@link org.fungover.breeze.control.Option} - An abstract class representing an optional value.</li>
 *   <li>{@link org.fungover.breeze.control.Some} - A concrete implementation of {@code Option} that holds a non-null value.</li>
 *   <li>{@link org.fungover.breeze.control.None} - A singleton implementation of {@code Option} representing the absence of a value.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * Option<String> maybeValue = Option.ofNullable("Hello");
 * String result = maybeValue.fold(() -> "Default", v -> v.toUpperCase());
 * System.out.println(result); // Outputs: HELLO
 * }</pre>
 *
 * <h2>Benefits:</h2>
 * <ul>
 *   <li>Eliminates {@code NullPointerException} risks by using explicit handling.</li>
 *   <li>Encourages functional programming paradigms with methods like {@code map}, {@code fold}, and {@code toList}.</li>
 *   <li>Provides a clear and consistent way to represent optional values.</li>
 * </ul>
 *
 * @since 1.0
 * @author Your Name
 * @version 1.0
 */
package org.fungover.breeze.control;
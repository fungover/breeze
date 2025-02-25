/**
 * Provides core functional constructs, including {@link org.fungover.breeze.control.Option},
 * {@link org.fungover.breeze.control.Some}, and {@link org.fungover.breeze.control.None}.
 *
 * <p>This package introduces a functional approach to handling optional values,
 * inspired by Scala's Option type. It provides a safer alternative to null references,
 * enabling expressive and predictable handling of missing values.</p>
 *
 * <h2>Core Components:</h2>
 * <ul>
 *   <li>{@link org.fungover.breeze.control.Option} - Abstract class representing a value
 *       that may or may not be present.</li>
 *   <li>{@link org.fungover.breeze.control.Some} - Represents a non-empty option
 *       containing a value.</li>
 *   <li>{@link org.fungover.breeze.control.None} - Represents an empty option,
 *       signifying the absence of a value.</li>
 * </ul>
 *
 * <h2>Usage Examples:</h2>
 * <pre>
 *     Option<String> maybeName = Option.of("Alice");
 *     String name = maybeName.getOrElse("Unknown");
 *
 *     Option<Integer> emptyOption = Option.none();
 *     int value = emptyOption.getOrElse(42); // Defaults to 42
 * </pre>
 *
 * <h2>Key Benefits:</h2>
 * <ul>
 *   <li>Eliminates null-related bugs by forcing explicit handling of absence.</li>
 *   <li>Encourages functional programming practices through map, flatMap, and filter.</li>
 *   <li>Interoperability with Java's {@link java.util.Optional} and exception handling via toEither.</li>
 * </ul>
 */

package org.fungover.breeze.control;

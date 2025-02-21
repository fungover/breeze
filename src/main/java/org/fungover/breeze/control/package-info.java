package org.fungover.breeze.control;

/**
 * This package provides a functional approach to handling optional values,
 * inspired by Scala's Option type. It introduces a safer alternative to null
 * references, enabling expressive and predictable handling of missing values.
 *
 * <p>The core component is {@link org.fungover.breeze.control.Option},
 * an abstract class that represents a value that may or may not be present.
 * It has two concrete implementations:
 * <ul>
 *   <li>{@link org.fungover.breeze.control.Some} - Represents a non-empty option
 *   containing a value.</li>
 *   <li>{@link org.fungover.breeze.control.None} - Represents an empty option,
 *   signifying the absence of a value.</li>
 * </ul>
 *
 * <p>Usage examples:
 * <pre>
 *     Option<String> maybeName = Option.of("Alice");
 *     String name = maybeName.getOrElse("Unknown");
 *
 *     Option<Integer> emptyOption = Option.none();
 *     int value = emptyOption.getOrElse(42); // Defaults to 42
 * </pre>
 *
 * <p>Key benefits include:
 * <ul>
 *   <li>Eliminates null-related bugs by forcing explicit handling of absence.</li>
 *   <li>Encourages functional programming practices through map, flatMap, and filter.</li>
 *   <li>Interoperability with Java's {@link java.util.Optional} and exception handling via toEither.</li>
 * </ul>
 */



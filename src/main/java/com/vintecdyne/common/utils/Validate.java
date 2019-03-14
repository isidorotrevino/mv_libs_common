/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vintecdyne.common.utils;

/**
 * <p>
 * This class assists in validating arguments. The validation methods are based
 * along the following principles:
 * <ul>
 * <li>An invalid {@code null} argument causes a
 * {@link NullPointerException}.</li>
 * <li>A non-{@code null} argument causes an
 * {@link IllegalArgumentException}.</li>
 * <li>An invalid index into an array/collection/map/string causes an
 * {@link IndexOutOfBoundsException}.</li>
 * </ul>
 *
 * <p>
 * All exceptions messages are <a href=
 * "http://docs.oracle.com/javase/1.5.0/docs/api/java/util/Formatter.html#syntax">format
 * strings</a> as defined by the Java platform. For example:
 * </p>
 *
 * <pre>
 * Validate.isTrue(i &gt; 0, "The value must be greater than zero: %d", i);
 * Validate.notNull(surname, "The surname must not be %s", null);
 * </pre>
 *
 * <p>
 * #ThreadSafe#
 * </p>
 * 
 * @see java.lang.String#format(String, Object...)
 */
public class Validate {

	// isTrue
	// ---------------------------------------------------------------------------------

	/**
	 * <p>
	 * Validate that the argument condition is {@code true}; otherwise throwing an
	 * exception with the specified message. This method is useful when validating
	 * according to an arbitrary boolean expression, such as validating a primitive
	 * number or using your own custom validation expression.
	 * </p>
	 *
	 * <pre>
	 * Validate.isTrue(i &gt; 0.0, "The value must be greater than zero: &#37;d", i);
	 * </pre>
	 *
	 * <p>
	 * For performance reasons, the long value is passed as a separate parameter and
	 * appended to the exception message only in the case of an error.
	 * </p>
	 *
	 * @param expression the boolean expression to check
	 * @param message    the {@link String#format(String, Object...)} exception
	 *                   message if invalid, not null
	 * @param value      the value to append to the message when invalid
	 * @throws IllegalArgumentException if expression is {@code false}
	 * @see #isTrue(boolean)
	 * @see #isTrue(boolean, String, double)
	 * @see #isTrue(boolean, String, Object...)
	 */
	public static void isTrue(final boolean expression, final String message, final long value) {
		if (!expression) {
			throw new IllegalArgumentException(String.format(message, Long.valueOf(value)));
		}
	}

	/**
	 * <p>
	 * Validate that the argument condition is {@code true}; otherwise throwing an
	 * exception with the specified message. This method is useful when validating
	 * according to an arbitrary boolean expression, such as validating a primitive
	 * number or using your own custom validation expression.
	 * </p>
	 *
	 * <pre>
	 * Validate.isTrue(i &gt;= min &amp;&amp; i &lt;= max, "The value must be between &#37;d and &#37;d", min, max);
	 * Validate.isTrue(myObject.isOk(), "The object is not okay");
	 * </pre>
	 *
	 * @param expression the boolean expression to check
	 * @param message    the {@link String#format(String, Object...)} exception
	 *                   message if invalid, not null
	 * @param values     the optional values for the formatted exception message,
	 *                   null array not recommended
	 * @throws IllegalArgumentException if expression is {@code false}
	 * @see #isTrue(boolean)
	 * @see #isTrue(boolean, String, long)
	 * @see #isTrue(boolean, String, double)
	 */
	public static void isTrue(final boolean expression, final String message, final Object... values) {
		if (!expression) {
			throw new IllegalArgumentException(String.format(message, values));
		}
	}

}
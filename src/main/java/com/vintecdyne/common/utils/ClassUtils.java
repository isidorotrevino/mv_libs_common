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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * <p>
 * Operates on classes without using reflection.
 * </p>
 *
 * <p>
 * This class handles invalid {@code null} inputs as best it can. Each method
 * documents its behaviour in more detail.
 * </p>
 *
 * <p>
 * The notion of a {@code canonical name} includes the human readable name for
 * the type, for example {@code int[]}. The non-canonical method variants work
 * with the JVM names, such as {@code [I}.
 * </p>
 *
 * @since 2.0 
 */
public class ClassUtils {

	/**
	 * <p>
	 * Gets a {@code List} of all interfaces implemented by the given class and its
	 * superclasses.
	 * </p>
	 *
	 * <p>
	 * The order is determined by looking through each interface in turn as declared
	 * in the source file and following its hierarchy up. Then each superclass is
	 * considered in the same way. Later duplicates are ignored, so the order is
	 * maintained.
	 * </p>
	 *
	 * @param cls the class to look up, may be {@code null}
	 * @return the {@code List} of interfaces in order, {@code null} if null input
	 */
	public static List<Class<?>> getAllInterfaces(final Class<?> cls) {
		if (cls == null) {
			return null;
		}

		final LinkedHashSet<Class<?>> interfacesFound = new LinkedHashSet<>();
		getAllInterfaces(cls, interfacesFound);

		return new ArrayList<>(interfacesFound);
	}

	/**
	 * Get the interfaces for the specified class.
	 *
	 * @param cls             the class to look up, may be {@code null}
	 * @param interfacesFound the {@code Set} of interfaces for the class
	 */
	private static void getAllInterfaces(Class<?> cls, final HashSet<Class<?>> interfacesFound) {
		while (cls != null) {
			final Class<?>[] interfaces = cls.getInterfaces();

			for (final Class<?> i : interfaces) {
				if (interfacesFound.add(i)) {
					getAllInterfaces(i, interfacesFound);
				}
			}

			cls = cls.getSuperclass();
		}
	}

}

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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Utilities for working with {@link Field}s by reflection. Adapted and refactored from the dormant [reflect] Commons
 * sandbox component.
 * <p>
 * The ability is provided to break the scoping restrictions coded by the programmer. This can allow fields to be
 * changed that shouldn't be. This facility should be used with care.
 *
 * @since 2.5
 */
public class FieldUtils {

	/**
	 * Gets an accessible {@link Field} by name, breaking scope if requested.
	 * Superclasses/interfaces will be considered.
	 *
	 * @param cls         the {@link Class} to reflect, must not be {@code null}
	 * @param fieldName   the field name to obtain
	 * @param forceAccess whether to break scope restrictions using the
	 *                    {@link java.lang.reflect.AccessibleObject#setAccessible(boolean)}
	 *                    method. {@code false} will only match {@code public}
	 *                    fields.
	 * @return the Field object
	 * @throws IllegalArgumentException if the class is {@code null}, or the field
	 *                                  name is blank or empty or is matched at
	 *                                  multiple places in the inheritance hierarchy
	 */
	public static Field getField(final Class<?> cls, final String fieldName, final boolean forceAccess) {
		Validate.isTrue(cls != null, "The class must not be null");
		Validate.isTrue(StringUtils.isNotBlank(fieldName), "The field name must not be blank/empty");
		// FIXME is this workaround still needed? lang requires Java 6
		// Sun Java 1.3 has a bugged implementation of getField hence we write the
		// code ourselves

		// getField() will return the Field object with the declaring class
		// set correctly to the class that declares the field. Thus requesting the
		// field on a subclass will return the field from the superclass.
		//
		// priority order for lookup:
		// searchclass private/protected/package/public
		// superclass protected/package/public
		// private/different package blocks access to further superclasses
		// implementedinterface public

		// check up the superclass hierarchy
		for (Class<?> acls = cls; acls != null; acls = acls.getSuperclass()) {
			try {
				final Field field = acls.getDeclaredField(fieldName);
				// getDeclaredField checks for non-public scopes as well
				// and it returns accurate results
				if (!Modifier.isPublic(field.getModifiers())) {
					if (forceAccess) {
						field.setAccessible(true);
					} else {
						continue;
					}
				}
				return field;
			} catch (final NoSuchFieldException ex) { // NOPMD
				// ignore
			}
		}
		// check the public interface case. This must be manually searched for
		// incase there is a public supersuperclass field hidden by a private/package
		// superclass field.
		Field match = null;
		for (final Class<?> class1 : ClassUtils.getAllInterfaces(cls)) {
			try {
				final Field test = class1.getField(fieldName);
				Validate.isTrue(match == null, "Reference to field %s is ambiguous relative to %s"
						+ "; a matching field exists on two or more implemented interfaces.", fieldName, cls);
				match = test;
			} catch (final NoSuchFieldException ex) { // NOPMD
				// ignore
			}
		}
		return match;
	}

}

/*
 * Copyright (C) 2015 CapTech Ventures, Inc.
 * (http://www.captechconsulting.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.monarchapis.driver.model;

/**
 * Stores the current operation name in a thread local. This value is later sent
 * to the analytics service as part of the traffic statistics.
 * 
 * @author Phil Kedy
 */
public abstract class OperationNameHolder {
	/**
	 * The current resource operation name being requested for the thread.
	 */
	private static InheritableThreadLocal<String> current = new InheritableThreadLocal<String>();

	public static String getCurrent() {
		return current.get();
	}

	public static void setCurrent(String value) {
		if (value != null) {
			current.set(value);
		} else {
			current.remove();
		}
	}

	public static void remove() {
		current.remove();
	}
}

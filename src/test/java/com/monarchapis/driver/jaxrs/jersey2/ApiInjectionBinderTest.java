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

package com.monarchapis.driver.jaxrs.jersey2;

import static org.mockito.Mockito.*;

import org.glassfish.hk2.api.DynamicConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ApiInjectionBinderTest {
	@Mock
	private DynamicConfiguration configuration;

	@Test
	public void testConfigure() {
		ApiInjectionBinder binder = new ApiInjectionBinder();
		binder = spy(binder);
		binder.bind(configuration);
		verify(binder).bind(ApiValueFactoryProvider.class);
		verify(binder).bind(ApiValueFactoryProvider.InjectionResolver.class);
	}
}

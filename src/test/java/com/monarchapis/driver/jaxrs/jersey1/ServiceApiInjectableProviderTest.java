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

package com.monarchapis.driver.jaxrs.jersey1;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.monarchapis.api.v1.client.ServiceApi;
import com.monarchapis.driver.util.ServiceResolver;
import com.sun.jersey.api.core.HttpContext;

@RunWith(MockitoJUnitRunner.class)
public class ServiceApiInjectableProviderTest {
	@Mock
	protected ServiceResolver serviceResolver;

	@Mock
	private ServiceApi serviceApi;

	@Before
	public void setup() {
		ServiceResolver.setInstance(serviceResolver);
		when(serviceResolver.required(ServiceApi.class)).thenReturn(serviceApi);
	}

	@After
	public void teardown() {
		ServiceResolver.setInstance(null);
	}

	@Test
	public void testGetValue() {
		ServiceApiInjectableProvider provider = new ServiceApiInjectableProvider();
		ServiceApi actual = provider.getValue((HttpContext) null);
		assertSame(serviceApi, actual);
	}
}

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

import javax.inject.Inject;
import javax.inject.Singleton;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.internal.inject.AbstractContainerRequestValueFactory;
import org.glassfish.jersey.server.internal.inject.AbstractValueFactoryProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.internal.inject.ParamInjectionResolver;
import org.glassfish.jersey.server.model.Parameter;

import com.monarchapis.api.v1.client.CommandApi;
import com.monarchapis.api.v1.client.ServiceApi;
import com.monarchapis.driver.annotation.ApiInject;
import com.monarchapis.driver.model.Claims;
import com.monarchapis.driver.model.ClaimsHolder;
import com.monarchapis.driver.util.ServiceResolver;

/**
 * Class to automatically inject {@link Claims}, {@link ServiceApi}, and
 * {@link CommandApi} into resources.
 * 
 * @author Phil Kedy
 */
@Singleton
final class ApiValueFactoryProvider extends AbstractValueFactoryProvider {

	/**
	 * Injection resolver for {@link ApiInject} annotation. Will create a
	 * Factory Provider for the actual resolving of the {@link Claims} object.
	 */
	@Singleton
	static final class InjectionResolver extends ParamInjectionResolver<ApiInject> {

		/**
		 * Create new {@link ApiInject} annotation injection resolver.
		 */
		public InjectionResolver() {
			super(ApiValueFactoryProvider.class);
		}
	}

	/**
	 * Factory implementation for resolving request-based attributes and other
	 * information.
	 */
	private static final class ClaimsValueFactory extends AbstractContainerRequestValueFactory<Claims> {
		/**
		 * Fetch the ApiContext object from the request.
		 * 
		 * @return {@link Claims} stored on the request, or NULL if no object
		 *         was found.
		 */
		public Claims provide() {
			return ClaimsHolder.getCurrent();
		}
	}

	private static final class ServiceApiValueFactory extends AbstractContainerRequestValueFactory<ServiceApi> {
		public ServiceApi provide() {
			return ServiceResolver.getInstance().required(ServiceApi.class);
		}
	}

	private static final class CommandApiValueFactory extends AbstractContainerRequestValueFactory<CommandApi> {
		public CommandApi provide() {
			return ServiceResolver.getInstance().required(CommandApi.class);
		}
	}

	/**
	 * {@link ApiInject} annotation value factory provider injection
	 * constructor.
	 * 
	 * @param mpep
	 *            multivalued parameter extractor provider.
	 * @param injector
	 *            injector instance.
	 */
	@Inject
	public ApiValueFactoryProvider(MultivaluedParameterExtractorProvider mpep, ServiceLocator injector) {
		super(mpep, injector, Parameter.Source.UNKNOWN);
	}

	/**
	 * Return a factory for the provided parameter. We only expect
	 * {@link Claims} objects being annotated with {@link ApiInject} annotation
	 * 
	 * @param parameter
	 *            Parameter that was annotated for being injected
	 * @return {@link ClaimsValueFactory} if parameter matched {@link Claims}
	 *         type
	 */
	@Override
	public AbstractContainerRequestValueFactory<?> createValueFactory(Parameter parameter) {
		Class<?> classType = parameter.getRawType();

		if (classType != null) {
			if (classType.equals(Claims.class)) {
				return new ClaimsValueFactory();
			} else if (classType.equals(ServiceApi.class)) {
				return new ServiceApiValueFactory();
			} else if (classType.equals(CommandApi.class)) {
				return new CommandApiValueFactory();
			}
		}

		return null;
	}
}
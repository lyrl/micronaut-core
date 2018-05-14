/*
 * Copyright 2017-2018 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * This package contains client APIs, service discovery and distributed configuration integration between Micronaut and AWS Service Registory (https://docs.aws.amazon.com/Route53/latest/APIReference/overview-service-discovery.html).
 *
 * @author ryan vanderwerf
 * @since 1.0
 */
@Configuration
@Requires(classes = {AWSClientConfiguration.class, AWSServiceDiscovery.class})

package io.micronaut.discovery.aws;

import com.amazonaws.services.servicediscovery.AWSServiceDiscovery;
import io.micronaut.configurations.aws.AWSClientConfiguration;
import io.micronaut.context.annotation.Configuration;
import io.micronaut.context.annotation.Requires;

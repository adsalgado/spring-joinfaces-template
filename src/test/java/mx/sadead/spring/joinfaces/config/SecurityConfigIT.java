/*
 * Copyright 2016-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mx.sadead.spring.joinfaces.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import mx.sadead.spring.joinfaces.config.SecurityConfig;

public class SecurityConfigIT {

	@Test
	public void exceptionOnConfigureNull() {
		Assertions.assertThrows(RuntimeException.class, () -> new SecurityConfig().configure((HttpSecurity) null));
	}

}

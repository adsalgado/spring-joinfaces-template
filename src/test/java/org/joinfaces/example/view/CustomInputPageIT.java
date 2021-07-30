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

package org.joinfaces.example.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import mx.sadead.spring.joinfaces.JoinFacesExampleApplication;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JoinFacesExampleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomInputPageIT extends AbstractPageIT {

	@Test
	public void checkCustomInputElement() {
		CustomInputPage customInputPage = initElements(CustomInputPage.class);
		customInputPage.navegateTo();

		assertThat(customInputPage.getOutputText())
			.isEqualTo("You entered: null");
	}

	@Test
	public void submitHello() {
		CustomInputPage customInputPage = initElements(CustomInputPage.class);
		customInputPage.navegateTo();

		customInputPage.submit("Hello");

		assertThat(customInputPage.getOutputText())
			.isEqualTo("You entered: Hello");
	}
}

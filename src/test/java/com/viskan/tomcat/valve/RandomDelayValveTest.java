/*
 * Copyright 2010 Linus Brimstedt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.viskan.tomcat.valve;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Valve;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for RandomDelayValve
 *
 * @author Linus Brimstedt
 *
 */
public class RandomDelayValveTest {

	private RandomDelayValve valve;
	private Context context;
	private Request req;
	private Response resp;
	private Container container;

	@Before
	public void setup() {
		valve = new RandomDelayValve();

		req = mock(Request.class);
		resp = mock(Response.class);
		context = mock(Context.class);
		container = mock(Container.class);
	}

	@Test
	public void test_maxdelay() throws Exception {
		long maxTime = 0;
		
		valve.setMaxDelay(100);
		for(int i = 0; i < 100; i++)
		{
			long millis = System.currentTimeMillis();
			valve.invoke(req, resp);
			long time = System.currentTimeMillis() - millis;
			if(time > maxTime)
			{
				maxTime = time;
			}
		}
		assertTrue("Time taken < 130 millis", maxTime < 130);

	}
	

	@Test
	public void test_mindelay() throws Exception {
		long minTime = 10000;
		
		valve.setMinDelay(100);
		for(int i = 0; i < 100; i++)
		{
			long millis = System.currentTimeMillis();
			valve.invoke(req, resp);
			long time = System.currentTimeMillis() - millis;
			if(time < minTime)
			{
				minTime = time;
			}
		}
		assertTrue("Time taken > 100 millis", minTime > 99);

	}




	private void doRequest(Valve valveToTestWith) throws Exception {
		when(context.getParent()).thenReturn(container);
		when(container.getName()).thenReturn("xxx");
		when(req.getContext()).thenReturn(context);

		valveToTestWith.invoke(req, resp);
	}


}

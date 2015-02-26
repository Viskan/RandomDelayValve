/*
 * Copyright 2015 Linus Brimstedt
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

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * A valve that imposes a random delay on each request.
 *
 * @author Linus Brimstedt
 * @version 1.0
 */
public final class RandomDelayValve extends ValveBase
{

	private static Log log = LogFactory.getLog(RandomDelay.class);

	private int maxValue = 0;
	private int minValue = 0;
	
	/**
	 * The descriptive information regarding the implementation
	 */
	private static final String info = "com.viskan.tomcat.valve.RandomDelayValve/1.0";

	RandomDelay randomDelay;

	public RandomDelayValve()
	{
	}

	private void enableDelay()
	{
		
        if (randomDelay == null) 
        {
            synchronized (RandomDelay.class) 
            {
                if (randomDelay == null) 
                {
                	String name = "unnamed";
                	if(getContainer() != null)
                	{
                		name = getContainer().getName();
                	}
                	randomDelay = new RandomDelay(name, minValue, maxValue);
                	
                	log.info(this.getClass().getSimpleName() + " enabled (" + name + ")");
                }
            }
        }
	}

	/**
	 * Returns the descriptive information regarding this valve implementation
	 */
	@Override
	public String getInfo()
	{
		return info;
	}

	/**
	 *
	 * @param request
	 *            The servlet request to be processed
	 * @param response
	 *            The servlet response to be created
	 *
	 * @exception IOException
	 *                IOException if an input/output error occurs
	 * @exception ServletException
	 *                ServletException if a servlet error occurs
	 */
	@Override
	public void invoke(Request request, Response response) throws IOException, ServletException
	{
		if(randomDelay == null)
		{
			enableDelay();
		}
		
		if(randomDelay != null)
		{
			randomDelay.delay();
		}
		
		if (getNext() != null)
		{
			getNext().invoke(request, response);
		}

		
	}

	
	/**
	 * Initial min delay
	 * 
	 * @return
	 */
	public int getMinDelay()
	{
		if(randomDelay != null)	
		{
			return randomDelay.getMinDelay();
		}
		return 0;
	}

	/**
	 * Set initial min delay
	 * 
	 * @param minDelay
	 */
	public void setMinDelay(int minDelay)
	{
		this.minValue = minDelay;
		if(randomDelay != null)	
		{
			randomDelay.setMinDelay(minDelay);
		}
	}

	/**
	 * Initial max delay
	 * @return
	 */
	public int getMaxDelay()
	{
		if(randomDelay != null)	
		{
			return randomDelay.getMaxDelay();
		}
		return 0;
	}

	/**
	 * Set initial max delay
	 * @param maxDelay
	 */
	public void setMaxDelay(int maxDelay)
	{
		this.maxValue = maxDelay;
		if(randomDelay != null)	
		{
			randomDelay.setMaxDelay(maxDelay);
		}
	}


}

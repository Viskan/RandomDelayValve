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

import java.lang.management.ManagementFactory;
import java.util.Random;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * Aggregation for one host.
 * <br/>
 * Stores aggregated number of accesses and bytes transferred.
 * <br />
 * The values are published as MBeans and periodically logged to Tomcat log.
 *
 * @author Linus Brimstedt
 *
 */
class RandomDelay implements RandomDelayMBean
{
    private static Log log = LogFactory.getLog(RandomDelay.class);
	private int minDelay;
	private int maxDelay;
	private Random random;


	public RandomDelay(String host, int minValue, int maxValue)
	{
		random = new Random();
		
		setMaxDelay(maxValue);
		setMinDelay(minValue);
		
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
	    ObjectName name;
		try
		{
			name = new ObjectName("com.viskan.tomcat.valve:type=RandomDelay,name=" + host);
			mbs.registerMBean(this, name);
		}
		catch (Exception e)
		{
			log.error("Unable to register mbean for " + host + " (" + e.getMessage() + ")", e);
		}
	}


		
	
	@Override
	public void setMinDelay(int delay)
	{
		
		minDelay = delay;
		if(minDelay < 0)
		{
			minDelay = 0;
		}
		if(minDelay > maxDelay)
		{
			maxDelay = minDelay;
		}	
		log.info(this.getClass().getSimpleName() + " changed delays to " + minDelay + " to " + maxDelay + "ms delay");
	}

	@Override
	public int getMinDelay()
	{
		return minDelay;
	}
	
	@Override
	public void setMaxDelay(int delay)
	{
		maxDelay = delay;
		if(maxDelay < 0)
		{
			maxDelay = 0;
		}
		if(maxDelay < minDelay)
		{
			minDelay = maxDelay;
		}
		log.info(this.getClass().getSimpleName() + " changed delays to " + minDelay + " to " + maxDelay + "ms delay");		
	}

	@Override
	public int getMaxDelay()
	{
		return maxDelay;
	}
	
	@Override
	public void delay()
	{
		int millis = random.nextInt(1 + maxDelay - minDelay) + minDelay;
		try
		{
			Thread.sleep(millis);
		}
		catch (InterruptedException e)
		{
			
		}
	}


}

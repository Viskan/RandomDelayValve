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

/**
 * MBean interface for {@link RandomDelay}
 * @author Linus Brimstedt
 *
 */
public interface RandomDelayMBean
{


	/**
	 * Sets a new max delay
	 */
	public void setMaxDelay(int delay);

	/**
	 * Gets the max delay
	 */
	public int getMaxDelay();

	/**
	 * Sets a new min delay
	 */
	public void setMinDelay(int delay);

	/**
	 * Gets the min delay
	 */
	public int getMinDelay();

	
	/**
	 * Delays execution
	 */
	public void delay();
	
}

Random Delay Valve
==================

Description
-----------
Tomcat valve that imposes a random delay for each request.

This is useful for testing environments when you want to force ajax calls to get slightly out of sync in a non deterministic way or to simulate a server under heavy load that does not respond to all requests in a nice and timely manner.

Example:
<code>
 &lt;Valve className="com.viskan.tomcat.valve.RandomDelayValve" minDelay="50" maxDelay="900" /&gt;
<code>

Preqs
-----------
  * Maven to build and test.
  * Tomcat to use.

Thanks
-----------
The initial code was based on the code for
https://github.com/xlson/tomcat-valves by Leonard Axelsson, https://github.com/xlson

License
-----------
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

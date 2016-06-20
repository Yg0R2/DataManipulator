/**
 * Copyright 2016 Yg0R2
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
 */
package yg0r2.dm.entry;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yg0R2
 */
public class TMP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> a = new HashMap<>();
		a.put("a1", "a1value");

		Map<String, String> b = new HashMap<>(a);
		b.put("a1", "a1 edited value");
		b.put("b1", "b1value");

		System.out.println(a.get("a1"));
		System.out.println(a.get("b1"));

		System.out.println(b.get("a1"));
		System.out.println(b.get("b1"));
	}

}

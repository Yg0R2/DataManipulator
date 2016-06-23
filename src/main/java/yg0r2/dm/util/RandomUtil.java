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
package yg0r2.dm.util;

import java.util.Random;

/**
 * @author Yg0R2
 */
public class RandomUtil {

	private static final Random _rnd = new Random();;

	public static int nextInt() {
		return _rnd.nextInt();
	}

	public static int nextInt(int max) {
		if (max <= 0) {
			return 0;
		}

		return _rnd.nextInt(max);
	}

	public static int nextInt(int min, int max) {
		return _rnd.nextInt(max - min) + min;
	}
 
	public static String nextString() {
		return nextString(12);
	}

	public static String nextString(int maxCharacter) {
		StringBuilder sb = new StringBuilder(maxCharacter);

		for (int i = 0; i < maxCharacter; i++) {
			int index = nextInt(_ALLOWED_DIGITS.length);

			sb.append(_ALLOWED_DIGITS[index]);
		}

		return sb.toString();
	}

	private static final char[] _ALLOWED_DIGITS = {
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
		'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
		'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
		'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
	};
 
}

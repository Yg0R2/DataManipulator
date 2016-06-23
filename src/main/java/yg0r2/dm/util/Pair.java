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


/**
 * @author Yg0R2
 */
public final class Pair<K, V> {

	private K _key;
	private V _value;

	public Pair() {
	}

	public Pair(K key, V value) {
		_key = key;
		_value = value;
	}

	public boolean equals(Pair<K, V> p) {
		return this.getKey().equals(p.getKey()) && this.getValue().equals(p.getValue());
	}

	/**
	 * @return the key
	 */
	public K getKey() {
		return _key;
	}

	/**
	 * @return the value
	 */
	public V getValue() {
		return _value;
	}

	/**
	 * @param key the key to set
	 */
	public void set_key(K key) {
		this._key = key;
	}

	/**
	 * @param value the value to set
	 */
	public void set_value(V value) {
		this._value = value;
	}

}

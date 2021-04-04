/*
Copyright 2020 Viktor Gubin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.greentree.bulbgl;

public enum DataType {
	
	BYTE(Byte.BYTES),
	UNSIGNED_BYTE(Byte.BYTES),	
	SHORT(Short.BYTES),
	UNSIGNED_SHORT(Short.BYTES),
	INT(Integer.BYTES),
	UNSIGNED_INT(Integer.BYTES),
	FLOAT(Float.BYTES),
	DOUBLE(Double.BYTES);

	private final int elementSize;

	private DataType(final int size) {
		this.elementSize = size;
	}

	public int sizeOf() {
		return this.elementSize;
	}
	
}

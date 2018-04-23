/*
 * This file is part of the BedrockPacketLib distribution (https://github.com/DragonetMC/DragonProxy).
 * Copyright (c) 2018 Dragonet Foundation.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.dragonet.bedrockpacketlib.data;

import lombok.Getter;
import lombok.NonNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public abstract class AbstractBedrockPacketData<T> {

    @Getter
    private boolean initialized = false;

    private T value;

    public T get() {
        if (!initialized) {
            throw new IllegalStateException("PacketData not initialized!");
        }
        return value;
    }

    public void set(@NonNull T value) {
        initialized = true;
        this.value = value;
    }

    public void clear() {
        initialized = false;
        value = null;
    }

    public final void encode(@NonNull ByteArrayOutputStream outputStream) throws IOException {
        toStream(outputStream, get());
    }

    protected abstract void toStream(ByteArrayOutputStream outputStream, T value) throws IOException;

    public final void decode(@NonNull ByteArrayInputStream inputStream) throws IOException {
        set(fromStream(inputStream));
        initialized = true;
    }

    protected abstract T fromStream(ByteArrayInputStream inputStream) throws IOException;

}

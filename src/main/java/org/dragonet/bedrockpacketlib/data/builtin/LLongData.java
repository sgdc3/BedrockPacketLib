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
package org.dragonet.bedrockpacketlib.data.builtin;

import org.dragonet.bedrockpacketlib.data.AbstractBedrockPacketData;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class LLongData extends AbstractBedrockPacketData<Long> {

    @Override
    protected void toStream(ByteArrayOutputStream outputStream, Long value) throws IOException {
        outputStream.write(new byte[]{
                value.byteValue(),
                (byte) (value >>> 8),
                (byte) (value >>> 16),
                (byte) (value >>> 24),
                (byte) (value >>> 32),
                (byte) (value >>> 40),
                (byte) (value >>> 48),
                (byte) (value >>> 56),
        });
    }

    @Override
    protected Long fromStream(ByteArrayInputStream inputStream) throws IOException {
        byte[] bytes = new byte[8];
        if (inputStream.read(bytes) != bytes.length) {
            throw new IOException("Unexpected end of stream!");
        }
        return (((long) bytes[7] << 56) +
                ((long) (bytes[6] & 0xFF) << 48) +
                ((long) (bytes[5] & 0xFF) << 40) +
                ((long) (bytes[4] & 0xFF) << 32) +
                ((long) (bytes[3] & 0xFF) << 24) +
                ((bytes[2] & 0xFF) << 16) +
                ((bytes[1] & 0xFF) << 8) +
                ((bytes[0] & 0xFF)));
    }

}

/*
 * This file is part of the BedrockPacketLib distribution (https://github.com/DragonetMC/BedrockPacketLib).
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
package org.dragonet.bedrockpacketlib.util.type;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@UtilityClass
public class LLongUtils {

    public byte[] toByteArray(long value) {
        return new byte[]{
            (byte) value,
            (byte) (value >>> 8),
            (byte) (value >>> 16),
            (byte) (value >>> 24),
            (byte) (value >>> 32),
            (byte) (value >>> 40),
            (byte) (value >>> 48),
            (byte) (value >>> 56),
        };
    }

    public long fromByteArray(byte[] byteArray) {
        if (byteArray.length != 8) {
            throw new IllegalArgumentException("The array must be 8 byte long!");
        }
        return (((long) byteArray[7] << 56) +
            ((long) (byteArray[6] & 0xFF) << 48) +
            ((long) (byteArray[5] & 0xFF) << 40) +
            ((long) (byteArray[4] & 0xFF) << 32) +
            ((long) (byteArray[3] & 0xFF) << 24) +
            ((byteArray[2] & 0xFF) << 16) +
            ((byteArray[1] & 0xFF) << 8) +
            ((byteArray[0] & 0xFF)));
    }

    public void writeToStream(OutputStream outputStream, long value) throws IOException {
        outputStream.write(toByteArray(value));
    }

    public long readFromStream(InputStream inputStream) throws IOException {
        byte[] byteArray = new byte[8];
        if (inputStream.read(byteArray) != byteArray.length) {
            throw new IOException("Unexpected end of stream!");
        }
        return fromByteArray(byteArray);
    }

}

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
public class VarTypeUtils {

    public long encodeZigZag32(int value) {
        return (long) ((value << 1) ^ (value >> 31));
    }

    public int decodeZigZag32(long value) {
        return (int) (value >> 1) ^ -(int) (value & 1);
    }

    public long encodeZigZag64(long value) {
        return (value << 1) ^ (value >> 63);
    }

    public long decodeZigZag64(long value) {
        return (value >>> 1) ^ -(value & 1);
    }

    public void writeToStream(OutputStream outputStream, long value) throws IOException {
        do {
            byte buffer = (byte) (value & 0b01111111);
            // Means that the sign bit is shifted with the rest of the number rather than being left alone.
            value >>>= 7;
            if (value != 0) {
                buffer |= 0b10000000;
            }
            outputStream.write(buffer);
        } while (value != 0);
    }

    public long readFromStream(InputStream inputStream, int maxSize) throws IOException {
        long value = 0;
        int size = 0;
        int currentByte;
        while (((currentByte = inputStream.read()) & 0x80) == 0x80) {
            value |= (long) (currentByte & 0x7F) << (size++ * 7);
            if (size >= maxSize) {
                throw new IllegalArgumentException("VarLong too big");
            }
        }
        return value | ((long) (currentByte & 0x7F) << (size * 7));
    }

}

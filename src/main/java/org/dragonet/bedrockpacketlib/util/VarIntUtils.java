/*
 * This file is part of the Nukkit distribution (https://github.com/Nukkit/Nukkit).
 * Copyright (c) 2018 Nukkit Project.
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
package org.dragonet.bedrockpacketlib.util;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Tool class for VarInt or VarLong operations.
 *
 * This class was part of the Nukkit/NukkitX project.
 * Some code was taken from http://wiki.vg/Protocol.
 *
 * @author MagicDroidX
 * @author lmlstarqaq
 */
@UtilityClass
public class VarIntUtils {

    private long encodeZigZag32(int v) {
        return (long) ((v << 1) ^ (v >> 31));
    }

    private int decodeZigZag32(long v) {
        return (int) (v >> 1) ^ -(int) (v & 1);
    }

    private long encodeZigZag64(long v) {
        return (v << 1) ^ (v >> 63);
    }

    private long decodeZigZag64(long v) {
        return (v >>> 1) ^ -(v & 1);
    }

    private long read(InputStream stream, int maxSize) throws IOException {
        long value = 0;
        int size = 0;
        int b;
        while (((b = stream.read()) & 0x80) == 0x80) {
            value |= (long) (b & 0x7F) << (size++ * 7);
            if (size >= maxSize) {
                throw new IllegalArgumentException("VarLong too big");
            }
        }
        return value | ((long) (b & 0x7F) << (size * 7));
    }

    public int readVarInt(InputStream stream) throws IOException {
        return decodeZigZag32(readUnsignedVarInt(stream));
    }

    public long readUnsignedVarInt(InputStream stream) throws IOException {
        return read(stream, 5);
    }

    public long readVarLong(InputStream stream) throws IOException {
        return decodeZigZag64(readUnsignedVarLong(stream));
    }

    public long readUnsignedVarLong(InputStream stream) throws IOException {
        return read(stream, 10);
    }

    private void write(OutputStream stream, long value) throws IOException {
        do {
            byte buffer = (byte) (value & 0b01111111);
            // Means that the sign bit is shifted with the rest of the number rather than being left alone.
            value >>>= 7;
            if (value != 0) {
                buffer |= 0b10000000;
            }
            stream.write(buffer);
        } while (value != 0);
    }

    public void writeVarInt(OutputStream stream, int value) throws IOException {
        writeUnsignedVarInt(stream, encodeZigZag32(value));
    }

    public void writeUnsignedVarInt(OutputStream stream, long value) throws IOException {
        write(stream, value);
    }

    public void writeVarLong(OutputStream stream, long value) throws IOException {
        writeUnsignedVarLong(stream, encodeZigZag64(value));
    }

    public void writeUnsignedVarLong(OutputStream stream, long value) throws IOException {
        write(stream, value);
    }

}

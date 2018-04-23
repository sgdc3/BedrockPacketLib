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
public class ByteArrayUtils {

    public void writeToStream(OutputStream outputStream, byte[] byteArray) throws IOException {
        UnsignedVarIntUtils.writeToStream(outputStream, byteArray.length);
        outputStream.write(byteArray);
    }

    public byte[] readFromStream(InputStream inputStream) throws IOException {
        byte[] byteArray = new byte[(int) UnsignedVarIntUtils.readFromStream(inputStream)];
        if (inputStream.read(byteArray) != byteArray.length) {
            throw new IOException("Unexpected end of stream!");
        }
        return byteArray;
    }

}

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
package org.dragonet.bedrockpacketlib.data.builtin;

import org.dragonet.bedrockpacketlib.data.AbstractBedrockPacketData;
import org.dragonet.bedrockpacketlib.util.type.VarIntUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class VarIntData extends AbstractBedrockPacketData<Integer> {

    @Override
    protected void writeToStream(ByteArrayOutputStream outputStream, Integer value) throws IOException {
        VarIntUtils.writeToStream(outputStream, value);
    }

    @Override
    protected Integer readFromStream(ByteArrayInputStream inputStream) throws IOException {
        return VarIntUtils.readFromStream(inputStream);
    }

}

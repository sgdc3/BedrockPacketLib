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
import java.util.UUID;

public class UUIDData extends AbstractBedrockPacketData<UUID> {

    private final static LLongData L_LONG = new LLongData();

    @Override
    protected void toStream(ByteArrayOutputStream outputStream, UUID value) throws IOException {
        L_LONG.toStream(outputStream, value.getMostSignificantBits());
        L_LONG.toStream(outputStream, value.getLeastSignificantBits());
    }

    @Override
    protected UUID fromStream(ByteArrayInputStream inputStream) throws IOException {
        return new UUID(L_LONG.fromStream(inputStream), L_LONG.fromStream(inputStream));
    }

}

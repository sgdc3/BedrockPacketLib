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
package org.dragonet.bedrockpacketlib.packet;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.dragonet.bedrockpacketlib.data.AbstractBedrockPacketData;
import org.dragonet.bedrockpacketlib.exception.BedrockDecodePacketException;
import org.dragonet.bedrockpacketlib.exception.BedrockEncodePacketException;
import org.dragonet.bedrockpacketlib.util.ReflectionUtils;
import org.dragonet.bedrockpacketlib.util.VarIntUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

@RequiredArgsConstructor
public class PacketEncoderService {

    @NonNull
    private final PacketRegister packetRegister;

    public ByteArrayOutputStream encode(AbstractBedrockPacket packet) throws BedrockEncodePacketException {
        ByteArrayOutputStream encoded = new ByteArrayOutputStream();
        for (Field field : packet.getClass().getDeclaredFields()) {
            try {
                if (Modifier.isTransient(field.getModifiers())) {
                    continue;
                }
                if (!field.getType().isAssignableFrom(AbstractBedrockPacketData.class)) {
                    throw new BedrockEncodePacketException("Found a non-transient field which is not a valid packet data type!");
                }
                AbstractBedrockPacketData value = ReflectionUtils.readField(field, packet, AbstractBedrockPacketData.class);
                value.encode(encoded);
            } catch (Throwable e) {
                throw new BedrockEncodePacketException("Unable to encode the packet field value "
                        + packet.getClass().getSimpleName() + "@" + field.getName());
            }
        }
        return encoded;
    }

    public AbstractBedrockPacket decode(ByteArrayInputStream encoded) throws BedrockDecodePacketException {
        Objects.requireNonNull(encoded);
        byte packetId;
        try {
            packetId = (byte) VarIntUtils.readUnsignedVarInt(encoded);
        } catch (IOException e) {
            throw new BedrockDecodePacketException("Unable to read the packetId!", e);
        }
        Class<? extends AbstractBedrockPacket> packetClass = packetRegister.getPacketClass(packetId)
                .orElseThrow(() -> new BedrockDecodePacketException("Missing packet type with id " + packetId));
        AbstractBedrockPacket packet;
        try {
            packet = packetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BedrockDecodePacketException("Unable to construct packet!", e);
        }
        for (Field field : packetClass.getDeclaredFields()) {
            try {
                if (Modifier.isTransient(field.getModifiers())) {
                    continue;
                }
                if (!field.getType().isAssignableFrom(AbstractBedrockPacketData.class)) {
                    throw new BedrockDecodePacketException("Found a non-transient field which is not a valid packet data type!");
                }
                AbstractBedrockPacketData value = ReflectionUtils.readField(field, packet, AbstractBedrockPacketData.class);
                value.decode(encoded);
            } catch (IllegalAccessException | IOException e) {
                throw new BedrockDecodePacketException("Unable to decode the packet field value "
                        + packet.getClass().getSimpleName() + "@" + field.getName());
            }
        }
        return packet;
    }

}

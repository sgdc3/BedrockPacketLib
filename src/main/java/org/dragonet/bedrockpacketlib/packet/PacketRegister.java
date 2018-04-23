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
package org.dragonet.bedrockpacketlib.packet;

import org.dragonet.bedrockpacketlib.util.AnnotationUtils;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class PacketRegister {

    private final Map<Byte, Class<? extends AbstractBedrockPacket>> registeredPackets;

    public PacketRegister() {
        registeredPackets = new LinkedHashMap<>();
        registerBuiltinPackets();
    }

    public void registerPacket(byte packetId, Class<? extends AbstractBedrockPacket> packetClass) {
        registeredPackets.put(packetId, packetClass);
    }

    public Optional<Class<? extends AbstractBedrockPacket>> getPacketClass(byte packetId) {
        return Optional.ofNullable(registeredPackets.get(packetId));
    }

    private void registerBuiltinPackets() {
        Reflections reflections = new Reflections(getClass().getPackage().getName() + ".builtin");
        reflections.getSubTypesOf(AbstractBedrockPacket.class).stream()
            .filter(packetClass -> !packetClass.isInterface() && !Modifier.isAbstract(packetClass.getModifiers()))
            .forEach(packetClass -> {
                registerPacket(AnnotationUtils.getMandatory(packetClass, BedrockPacketInfo.class).packetId(), packetClass);
            });
    }

}

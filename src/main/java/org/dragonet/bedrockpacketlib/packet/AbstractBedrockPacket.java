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

import org.dragonet.bedrockpacketlib.util.AnnotationUtils;

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class AbstractBedrockPacket {

    public final int getPacketId() {
        return AnnotationUtils.getMandatory(getClass(), BedrockPacketInfo.class).packetId();
    }

}

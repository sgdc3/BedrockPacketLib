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
package org.dragonet.bedrockpacketlib.packet.builtin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dragonet.bedrockpacketlib.data.builtin.*;
import org.dragonet.bedrockpacketlib.packet.AbstractBedrockPacket;
import org.dragonet.bedrockpacketlib.packet.BedrockPacketInfo;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@BedrockPacketInfo(packetId = 0x0c)
public class AddPlayerPacket extends AbstractBedrockPacket {

    private UUIDData uuid;
    private StringData username;
    private StringData thirdPartyName;
    private VarIntData platformId;
    private VarLongData entityUniqueId;
    private UnsignedVarLongData entityRuntimeId;
    private StringData platformChatId;

    /*
    private float x;
    private float y;
    private float z;
    private float speedX;
    private float speedY;
    private float speedZ;
    private float pitch;
    private float yaw;
    private Object item;
    private Object metadata;
    */

}

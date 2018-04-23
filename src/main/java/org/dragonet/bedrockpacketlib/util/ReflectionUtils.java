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
package org.dragonet.bedrockpacketlib.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

@UtilityClass
public class ReflectionUtils {

    public <T> T readField(@NonNull Field field, @NonNull Object object, @NonNull Class<T> type) throws IllegalAccessException {
        field.setAccessible(true);
        return type.cast(field.get(object));
    }

}

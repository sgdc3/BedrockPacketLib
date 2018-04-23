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

import java.lang.annotation.Annotation;
import java.util.Optional;

@UtilityClass
public class AnnotationUtils {

    public <A extends Annotation> Optional<A> get(@NonNull Class<?> subjectClass, @NonNull Class<A> annotationClass) {
        return Optional.ofNullable(subjectClass.getAnnotation(annotationClass));
    }

    public <A extends Annotation> A getMandatory(Class<?> subjectClass, Class<A> annotationClass) {
        return get(subjectClass, annotationClass).orElseThrow(() -> new IllegalStateException(subjectClass.getSimpleName()
            + " is missing the mandatory @" + annotationClass.getSimpleName() + " annotation!"));
    }

}

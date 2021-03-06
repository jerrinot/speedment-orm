/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.orm.config.model.impl;

import com.speedment.orm.config.model.ConfigEntity;
import com.speedment.orm.config.model.External;
import com.speedment.orm.config.model.aspects.Child;
import com.speedment.orm.config.model.aspects.Node;
import com.speedment.orm.config.model.aspects.Parent;
import java.util.Objects;
import java.util.Optional;

/**
 * Generic representation of a ConfigEntity.
 *
 * This class is thread safe.
 *
 * @author pemi
 */
public abstract class AbstractConfigEntity implements ConfigEntity {

    private boolean enabled;
    private String name;

    protected AbstractConfigEntity(String defaultName) {
        enabled = true;
        name = defaultName;
        setDefaults();
    }

    protected abstract void setDefaults();

    @External
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @External
    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @External
    @Override
    public String getName() {
        return name;
    }

    @External
    @Override
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("A name can't be null");
        }
        if (name.contains(".")) {
            throw new IllegalArgumentException("A name can't contain a '.' character");
        }
        if (name.contains(" ")) {
            throw new IllegalArgumentException("A name can't contain a space character");
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return getInterfaceMainClass().getSimpleName()
                + " '" + Optional.of(this)
                .filter(e -> e.isChildInterface())
                .map(e -> (Child<?>) e)
                .flatMap(e -> e.getParent())
                .map(e -> getRelativeName(e) + "." + getName())
                .orElse(getName())
                + "'";
    }
}

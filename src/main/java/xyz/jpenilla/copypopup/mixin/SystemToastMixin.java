/*
 * Minecraft Copy Popup
 * Copyright (c) 2024 Jason Penilla
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xyz.jpenilla.copypopup.mixin;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import xyz.jpenilla.copypopup.SystemToastAccess;

@Mixin(SystemToast.class)
abstract class SystemToastMixin implements SystemToastAccess {

    @Mutable @Shadow @Final private int width;

    @Shadow private boolean changed;

    @Shadow private List<FormattedCharSequence> messageLines;

    @Override
    public void mc_copy_popup$resetMultiline(
        final Minecraft client,
        final Component header,
        final @Nullable Component description
    ) {
        final Font font = client.font;
        final List<FormattedCharSequence> descriptionList = font.split(description, 200);
        this.width = 30 + Math.max(200, descriptionList.stream().mapToInt(font::width).max().orElse(200));
        this.messageLines = descriptionList;
        this.changed = true;
    }
}

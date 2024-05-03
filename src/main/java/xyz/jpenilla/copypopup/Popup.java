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
package xyz.jpenilla.copypopup;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public final class Popup {
    private static final SystemToast.SystemToastId TOAST_ID = new SystemToast.SystemToastId();

    private Popup() {
    }

    public static void showPopup(final String value) {
        final Minecraft client = Minecraft.getInstance();
        if (client.player != null) {
            addOrUpdate(
                client,
                TOAST_ID,
                Component.translatable("mc-copy-popup.toast.header"),
                Component.literal(value)
            );
        }
    }

    private static void add(
        final Minecraft client,
        final SystemToast.SystemToastId systemToastId,
        final Component header,
        final @Nullable Component description
    ) {
        client.getToasts().addToast(SystemToast.multiline(client, systemToastId, header, description));
    }

    private static void addOrUpdate(
        final Minecraft client,
        final SystemToast.SystemToastId systemToastId,
        final Component header,
        final @Nullable Component description
    ) {
        final SystemToast systemToast = client.getToasts().getToast(SystemToast.class, systemToastId);
        if (systemToast == null) {
            add(client, systemToastId, header, description);
        } else {
            ((SystemToastAccess) systemToast).mc_copy_popup$resetMultiline(client, header, description);
        }
    }
}

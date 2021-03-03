/**
 * Copyright 2021 Markus Bordihn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.markusbordihn.adaptiveperformancetweaks.commands;

import java.util.Set;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.util.ResourceLocation;

public class CommandEntities extends CustomCommand {

  private static final CommandEntities command = new CommandEntities();

  public static ArgumentBuilder<CommandSource, ?> register() {
    return Commands.literal("entities").requires(cs -> cs.hasPermissionLevel(2)).executes(command);
  }

  @Override
  public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
    Set<ResourceLocation> entitiesKeys = ForgeRegistries.ENTITIES.getKeys();
    if (entitiesKeys.isEmpty()) {
      sendFeedback(context,
          "Unable to find any entities. Server / World is not loaded?");
    } else {
      sendFeedback(context, "Entity Overview,  please check info.log for the full output.\n===");
      log.info("Entity overview: {}", entitiesKeys);
      for (ResourceLocation entityKey : entitiesKeys) {
        sendFeedback(context, String.format("\u25CB %s", entityKey));
      }
    }
    return 0;
  }

}
package dfutils.commands;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.init.SoundEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandHelp extends CommandBase implements IClientCommand {
    
    private final Minecraft minecraft = Minecraft.getMinecraft();
    
    public String getName() {
        return "dfutils";
    }
    
    public String getUsage(ICommandSender sender) {
        return "§c/dfutils";
    }
    
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
    
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] commandArgs) {
        String helpTitle = null;

        if (commandArgs.length == 0) {
            minecraft.player.playSound(SoundEvents.BLOCK_SHULKER_BOX_OPEN, 1F, 2F);
            helpTitle = "§3§m          §3§l [§b DF Utilities§3§l ] §m          ";
            String[] helpCommands = {
                    "/dfutils codetools",
                    "/dfutils items",
                    "/dfutils shortcuts",
                    "/dfutils credits"
            };
            String[] helpDescriptions = {
                    "/dfutils codetools",
                    "/dfutils items",
                    "/dfutils shortcuts",
                    "/dfutils credits"
            };
        } else {
            minecraft.player.sendMessage(new TextComponentString("§4\u258E §cCategory \"§4" + commandArgs[0] + "§c\" not found."));
            minecraft.player.playSound(SoundEvents.ENTITY_CAT_HURT, 1F, 1F);
        }

        if (helpTitle != null)

            minecraft.player.sendMessage(new TextComponentString(helpTitle + "\n"));


        /*minecraft.player.playSound(SoundEvents.BLOCK_SHULKER_BOX_OPEN, 0.75F, 2F);

        //Displays default help message.
        if (commandArgs.length == 0) {
            String[] helpMessage = {
                    "§3§l<«§b §nDF Utilities§3 §l»>",
                    "",
                    "§3> §b/dfutils commands",
                    "§3> §b/dfutils shortcuts",
                    "§3> §b/dfutils codetools",
                    ""
            };

            for (String messageLine : helpMessage) {
                minecraft.player.sendMessage(new TextComponentString(messageLine));
            }
        } else {
            switch (commandArgs[0]) {
                case "commands":
                    commandHelp(commandArgs);
                    break;

                case "shortcuts":
                    shortcutHelp();
                    break;

                case "codetools":
                    codeToolHelp();
                    break;

                default:
                    errorMessage("Category \"" + commandArgs[0] + "\" not found.");
            }
        } */
    }

    private void commandHelp(String[] commandArgs) {
        String[] helpMessage;
        if(commandArgs.length >= 2) {
            switch(commandArgs[1]) {
                case "1":
                    helpMessage = new String[] {
                        "§6§l<« §eCommands §6- §eItems §6§l»>",
                        "§6> §e/give",
                        "§9> §7Similar to the default command.",
                        "§6> §e/itemdata",
                        "§9> §7Displays the NBT for the held item.",
                        "§6> §e/attribute",
                        "§9> §7No information specified.",
                        "§6> §e/lore",
                        "§9> §7No information specified.",
                        "§6> §e/candestroy",
                        "§9> §7No information specified.",
                        "§6> §e/canplace",
                        "§9> §7No information specified.",
                        "",
                        "§7Page: §61§7/§63",
                    };
                    break;
                case "2":
                    helpMessage = new String[] {
                        "§6§l<« §eCommands §6- §eItems §6§l»>",
                        "§6> §e/breakable",
                        "§9> §7No information specified.",
                        "§6> §e/showflags",
                        "§9> §7No information specified.",
                        "§6> §e/setflags",
                        "§9> §7No information specified.",
                        "§6> §e/disenchant",
                        "§9> §7Removes a specific enchantment of the held item.",
                        "§6> §e/clearenchants",
                        "§9> §7Removes all enchantments of the held item.",
                        "",
                        "§7Page: §62§7/§63",
                    };
                    break;
                case "3":
                    helpMessage = new String[] {
                        "§6§l<« §eCommands §6- §eCode §6§l»>",
                        "§6> §e/code",
                        "§9> §7Code copying/pasting.",
                        "§6> §e/num",
                        "§9> §7Gives you the specified range of number items.",
                        "§6> §e/txt",
                        "§9> §7Gives you a text book item.",
                        "§6> §e/var",
                        "§9> §7Gives you a variable item.",
                        "§6> §e/loc",
                        "§9> §7Contains various location manipulation commands.",
                        "",
                        "§7Page: §62§7/§63",
                    };
                    break;
                default:
                    helpMessage = new String[] {
                        "§6§m    §6[§eCommands§6]§m    ",
                        "",
                        "  §cNote: §7Type the a command into chat",
                        "  §7(for example, /give) to get the command's",
                        "  §7arguments and sometimes some extra information.",
                        "",
                        "§6> §e/give §7Similar to the default /give command.",
                        "§6> §e/itemdata §7Displays the NBT for the currently held item.",
                        "§6> §e/attribute",
                        "§6> §e/lore",
                        "§6> §e/candestroy",
                        "§6> §e/canplace",
                        "§6> §e/renameanvil §7Renames the item as if it were renamed in an anvil.",
                        "§6> §e/breakable",
                        "§6> §e/showflags",
                        "§6> §e/setflags",
                        "§6> §e/disenchant",
                        "§6> §e/clearenchants",
                        "",
                        "§6> §e/code §7Used for code copy pasting.",
                        "§6> §e/num §7Gives you the specified range of number items.",
                        "§6> §e/txt §7Gives you a text book item.",
                        "§6> §e/loc §7Contains various location manipulation commands.",
                        "§6> §e/var §7Gives you a variable item.",
                        ""
                };
                break;
            }

        } else {
            helpMessage = new String[] {
                    "§6§m    §6[§eCommands§6]§m    ",
                    "",
                    "§6> §71 §6- §7Item manipulation",
                    "§6> §72 §6- §7Item manipulation",
                    "§6> §73 §6- §7Code tools"
            };
        }

        for (String messageLine : helpMessage) {
            minecraft.player.sendMessage(new TextComponentString(messageLine));
        }
    }

    private void shortcutHelp() {
        String[] helpMessage = {
                "§5§m    §5[§dShortcuts§5]§m    ",
                "",
                "§5> §d/l §7Messages the last person you messaged.",
                "§5> §d/sb §7Shortcut for /support b.",
                ""
        };

        for (String messageLine : helpMessage) {
            minecraft.player.sendMessage(new TextComponentString(messageLine));
        }
    }

    private void codeToolHelp() {
        String[] helpMessage = {
                "§2§m    §2[§aCode Tools§2]§m    ",
                "",
                "§2> §a§nPiston Highlighting",
                "",
                "  §7Shift left click a piston to highlight it.",
                "",
                "§2> §a§nLocation Setting",
                "",
                "  §7Left click with a location item to set it to your",
                "  §7target block location without adding on an extra 0.5.",
                "",
                "§2> §a§nLocation Highlighting",
                "",
                "  §7Hold a location to highlight the block that it's set to.",
                "",
                "§2> §a§nQuick Item Renaming",
                "",
                "  §7Hold a text book, number, or variable item and press",
                "  §7shift + left click (note that you need to also be looking",
                "  §7at air) to open up the chat window with the name of",
                "  §7the item automatically set inside the chatbox.",
                "",
                "§2> §a§nCode Quick Selection",
                "",
                "  §7Press 'V' (or whatever you set the keybind to) while looking",
                "  §7at a code block to get a selection item, you can then use",
                "  §7this item to automatically set the sign arguments for a code",
                "  §7block by right clicking the code block.",
                "",
                "§2> §a§nCode Copy/Pasting",
                "",
                "  §7Get a selection stick by typing /code select and select",
                "  §7the code you want to copy, when you have done that",
                "  §7do /code copy to copy the code and get a code template",
                "  §7item, this item can later be placed down to paste the",
                "  §7copied code. Note however that the copied code is stored",
                "  §7as item NBT on the code template, this means that you",
                "  §7can give the code template to other people or take it to",
                "  §7other nodes or plots!",
                ""
        };

        for (String messageLine : helpMessage) {
            minecraft.player.sendMessage(new TextComponentString(messageLine));
        }
    }
}

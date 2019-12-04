package fr.cocoraid.oldschoolpvp.commands;

import fr.cocoraid.oldschoolpvp.commands.admin.PvpToggleCMD;
import fr.cocoraid.oldschoolpvp.commands.kit.SharpnessCMD;
import fr.cocoraid.oldschoolpvp.commands.kit.UpgradeCMD;
import fr.cocoraid.oldschoolpvp.commands.player.LanguageCMD;
import fr.cocoraid.oldschoolpvp.commands.player.MenuCMD;
import fr.cocoraid.oldschoolpvp.utils.ezcommand.CommandRegistry;

public class RegisterCommands {

    public RegisterCommands() {
        CommandRegistry.register(new SharpnessCMD());
        CommandRegistry.register(new PvpToggleCMD());
        CommandRegistry.register(new LanguageCMD());
        CommandRegistry.register(new UpgradeCMD());
        CommandRegistry.register(new MenuCMD());
    }
}

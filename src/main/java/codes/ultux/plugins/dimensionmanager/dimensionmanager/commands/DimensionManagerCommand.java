package codes.ultux.plugins.dimensionmanager.dimensionmanager.commands;

import codes.ultux.plugins.dimensionmanager.dimensionmanager.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class DimensionManagerCommand implements CommandExecutor {
    private Main instance;

    public DimensionManagerCommand(Main instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (args.length == 0){
                return displayDimensionsState(sender);
            }
            else if (sender.isOp() || sender instanceof ConsoleCommandSender) {
                if (args.length == 1) {
                    return toggleCommand(sender, args);
                }
                sender.sendMessage(ChatColor.RED+"Niepoprawne użycie komendy.");
            }
            else sender.sendMessage(ChatColor.RED+"Ta komenda może być użyta tylko przez opa lub konsolę!");
            return false;
    }

    private boolean displayDimensionsState(CommandSender sender) {
        StringBuilder sb = new StringBuilder();
        if (!instance.netherManager.getState() || !instance.endManager.getState() || !instance.endIslandManager.getState()){
            sb.append(ChatColor.GRAY).append("Aktualnie wyłączone wymiary: ");
            if (!instance.netherManager.getState()) sb.append("\n    ").append(ChatColor.RED).append("- Nether");
            if (!instance.endManager.getState()) sb.append("\n    ").append(ChatColor.RED).append("- End");
            if (!instance.endIslandManager.getState()) sb.append("\n    ").append(ChatColor.RED).append("- Wyspy Endu");
        }
        else {
            sb.append(ChatColor.GRAY).append("Aktualnie brak wyłączonych wymiarów.");
        }
        sender.sendMessage(sb.toString());
        return true;
    }

    private boolean toggleCommand(CommandSender sender, String[] args) {
        switch (args[0].toLowerCase()) {
            case "end":
                String endState = instance.endManager.toggle() ? ChatColor.GREEN+"włączony" : ChatColor.RED+"wyłączony";
                sender.sendMessage(ChatColor.GRAY+"End jest teraz "+endState);
                return true;
            case "nether":
                String netherState = instance.netherManager.toggle() ? ChatColor.GREEN+"włączony" : ChatColor.RED+"wyłączony";
                sender.sendMessage(ChatColor.GRAY+"Nether jest teraz "+netherState);
                return true;
            case "endislands":
                String endIslandsState = instance.endIslandManager.toggle() ? ChatColor.GREEN+"włączone" : ChatColor.RED+"wyłączone";
                sender.sendMessage(ChatColor.GRAY+"Wyspy endu są teraz "+endIslandsState);
                return true;
            default:
                sender.sendMessage(ChatColor.RED+"Zły argument.");
                return false;
        }
    }

}

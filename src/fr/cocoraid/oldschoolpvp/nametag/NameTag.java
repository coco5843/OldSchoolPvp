package fr.cocoraid.oldschoolpvp.nametag;

import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;

public class NameTag {

    private Scoreboard scoreboard;
    private Team pvpTeam;
    private Team taggedTeam;

    public NameTag() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        pvpTeam = scoreboard.registerNewTeam("pvp");
        pvpTeam.setPrefix(ChatColor.AQUA.toString());
        taggedTeam = scoreboard.registerNewTeam("tagged");
        taggedTeam.setPrefix(ChatColor.RED.toString());

        Bukkit.getOnlinePlayers().forEach(cur -> {
            cur.setScoreboard(scoreboard);
        });
    }

    private net.minecraft.server.v1_8_R3.Scoreboard nmsScoreboard = new net.minecraft.server.v1_8_R3.Scoreboard();
    public void setPvpTeam(Player p) {
        ScoreboardTeam nmsTeam = new ScoreboardTeam(nmsScoreboard, pvpTeam.getName());
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(nmsTeam, Arrays.asList(p.getName()), 3);
        Bukkit.getOnlinePlayers().forEach(cur -> {
            ((CraftPlayer) cur).getHandle().playerConnection.sendPacket(packet);
        });

    }

    public void setTaggedTeam(Player p) {
        ScoreboardTeam nmsTeam = new ScoreboardTeam(nmsScoreboard, taggedTeam.getName());
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(nmsTeam, Arrays.asList(p.getName()), 3);
        Bukkit.getOnlinePlayers().forEach(cur -> {
            ((CraftPlayer) cur).getHandle().playerConnection.sendPacket(packet);
        });
    }

    public void removeTeam(Player p, String team) {
        ScoreboardTeam nmsTeam = new ScoreboardTeam(nmsScoreboard, team);
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(nmsTeam, Arrays.asList(p.getName()), 4);
        Bukkit.getOnlinePlayers().forEach(cur -> {
            ((CraftPlayer) cur).getHandle().playerConnection.sendPacket(packet);
        });
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}

package fr.cocoraid.oldschoolpvp.events.listeners;

import fr.cocoraid.oldschoolpvp.OldSchoolPlayer;
import fr.cocoraid.oldschoolpvp.OldSchoolPvp;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

public class DeathEvent implements Listener {

    @EventHandler
    public void deathEvent(PlayerDeathEvent e) {
        OldSchoolPlayer op = OldSchoolPlayer.getOldSchoolPlayer(e.getEntity());
        OldSchoolPvp.getInstance().getNametag().removeTeam(e.getEntity(),op.hasRecentHit() ? "tagged": "pvp");
        op.setRecentHit(false);
        op.getKit().removeKit();
        op.setTagged(false);

        e.getDrops().removeIf(itemStack -> itemStack.getType() == Material.MUSHROOM_SOUP
                || itemStack.getType() == Material.BOWL
                || (itemStack.getType() == Material.DIAMOND_SWORD && !itemStack.getEnchantments().containsKey(Enchantment.DAMAGE_ALL))
                || itemStack.getType() == Material.IRON_BOOTS
                || itemStack.getType() == Material.IRON_HELMET
                || itemStack.getType() == Material.IRON_LEGGINGS
                || itemStack.getType() == Material.IRON_CHESTPLATE);

        //Credit redistribution algorithm
        //first get total damage received by other players
        final double totalDamageGaveByDamagers = op.getTotalDamageReceived().values().stream().mapToDouble(Double::doubleValue).sum();
        //then get total damage that I gave to other players
        double totalDamageGaveByVictim = 0;
        for(UUID uuid: OldSchoolPlayer.getOldschoolplayers().keySet()) {
            if(!uuid.equals(e.getEntity().getUniqueId()) && OldSchoolPlayer.getOldSchoolPlayer(Bukkit.getPlayer(uuid)).getTotalDamageReceived().get(e.getEntity().getUniqueId()) != null) {
                totalDamageGaveByVictim += OldSchoolPlayer.getOldSchoolPlayer(Bukkit.getPlayer(uuid)).getTotalDamageReceived().get(e.getEntity().getUniqueId());
            }
        }

        //Now start redistribution
        for (UUID damagerUUID : op.getTotalDamageReceived().keySet()) {
            double damage = op.getTotalDamageReceived().get(damagerUUID);
            int percentageGaveByDamager = (int) (damage * 100 / totalDamageGaveByDamagers); //percentage that damager made

            //now check if the dead player has gave damage too
            OldSchoolPlayer other = OldSchoolPlayer.getOldSchoolPlayer(Bukkit.getPlayer(damagerUUID));
            int percentageGaveByVictim = 0;
            if(other.getTotalDamageReceived().get(e.getEntity().getUniqueId()) != null) {
                percentageGaveByVictim = (int) (other.getTotalDamageReceived().get(e.getEntity().getUniqueId()) * 100 / (totalDamageGaveByVictim + totalDamageGaveByDamagers));
                String damageInfo = other.getLanguage().damageInfo.replace("%percentGaveByDamager",
                        String.valueOf(percentageGaveByDamager)).replace("%percentGaveByVictim",
                        String.valueOf(percentageGaveByVictim)).replace("%vitimName",e.getEntity().getName());
                Bukkit.getPlayer(damagerUUID).sendMessage(damageInfo);
                other.getTotalDamageReceived().remove(e.getEntity().getUniqueId());
            } else {
                String damageInfo = other.getLanguage().damageInfo.replace("%percentGaveByDamager",
                        String.valueOf(percentageGaveByDamager)).replace("%percentGaveByVictim",
                        "0").replace("%vitimName",e.getEntity().getName());
                Bukkit.getPlayer(damagerUUID).sendMessage(damageInfo);
            }

            int credit = 1;
            if(percentageGaveByVictim > 0) {
                //Algogo super cool
                double moy = (percentageGaveByVictim + percentageGaveByDamager) / 2;
                double exactCredit = moy / 10;
                credit = (int) Math.rint(exactCredit);
            }
            Bukkit.getPlayer(damagerUUID).sendMessage("§bYou earned " + credit + " credit");

        }
        e.getEntity().teleport(OldSchoolPvp.SPAWN);
        op.getTotalDamageReceived().clear();

    }
}

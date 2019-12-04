package fr.cocoraid.oldschoolpvp.kit;

import fr.cocoraid.oldschoolpvp.OldSchoolPlayer;
import fr.cocoraid.oldschoolpvp.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ClassicKit {


    private Player p;
    private OldSchoolPlayer op;
    private Language lang;
    public ClassicKit(Player p) {
        this.p = p;
        this.op = OldSchoolPlayer.getOldSchoolPlayer(p);
        this.lang = op.getLanguage();

    }

    public void giveKit() {
        p.setHealth(p.getMaxHealth());
        p.setFoodLevel(20);

        PlayerInventory inv = p.getInventory();
        inv.clear();
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName("§cOldSchool Sword");
        sword.setItemMeta(meta);
        inv.setItem(OldSchoolPlayer.getOldSchoolPlayer(p).getPreferences().getSwordPosition(),sword);
        inv.setBoots(new ItemStack(Material.IRON_BOOTS));
        inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        inv.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        inv.setHelmet(new ItemStack(Material.IRON_HELMET));

        for (int i = 0; i < inv.getSize(); i++) {
            if ((i < 9 && i < 17) || i > 17) {
                ItemStack itemStack = inv.getItem(i);
                if (itemStack == null || itemStack.getType() == Material.AIR) {
                    ItemStack soup = new ItemStack(Material.MUSHROOM_SOUP);
                    ItemMeta soupMeta = soup.getItemMeta();
                    soupMeta.setDisplayName("§bMushroom Stew");
                    soup.setItemMeta(soupMeta);
                    inv.setItem(i, soup);
                }
            }
        }

        OldSchoolPlayer.getOldSchoolPlayer(p).setKit(this);
    }


    public void removeKit() {
        PlayerInventory inv = p.getInventory();
        inv.clear();
        inv.setChestplate(null);
        inv.setHelmet(null);
        inv.setLeggings(null);
        inv.setBoots(null);
        p.setHealth(p.getMaxHealth());
        p.setFoodLevel(20);
        p.getActivePotionEffects().forEach(potion -> p.removePotionEffect(potion.getType()));
        //TODO donner le kit de base pour selectionner des choses
        OldSchoolPlayer.getOldSchoolPlayer(p).setKit(null);
    }

    public void refill() {
        if(op.withdraw(5,"soups refill")) {
            PlayerInventory inv = p.getInventory();
            for (int i = 0; i < inv.getSize(); i++) {
                if ((i < 9 && i < 17) || i > 17) {
                    ItemStack itemStack = inv.getItem(i);
                    if (itemStack == null || itemStack.getType() == Material.AIR) {
                        ItemStack soup = new ItemStack(Material.MUSHROOM_SOUP);
                        ItemMeta soupMeta = soup.getItemMeta();
                        soupMeta.setDisplayName("§bMushroom Stew");
                        soup.setItemMeta(soupMeta);
                        inv.setItem(i, soup);
                    }
                }
            }
        }
    }

    public void sharpness() {
        ItemStack sword = p.getItemInHand();
        if(sword.getType() != Material.DIAMOND_SWORD) {
            p.sendMessage(ChatColor.RED + lang.holdsword);
            return;
        }


        if (sword.getEnchantments().containsKey(Enchantment.DAMAGE_ALL)) {
            if (sword.getEnchantments().get(Enchantment.DAMAGE_ALL) >= 3) {
                p.sendMessage(ChatColor.RED + lang.maxSharpReach);
            } else {
                if(op.withdraw(10,"sharpness")) {
                    p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1F, 1F);
                    sword.addEnchantment(Enchantment.DAMAGE_ALL, sword.getEnchantmentLevel(Enchantment.DAMAGE_ALL) + 1);
                }
            }
        } else {
            if(op.withdraw(10,"sharpness")) {
                p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1F, 1F);
                sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
            }
        }

    }

    public void repair() {
        if(op.withdraw(5,"repair")) {
            //TODO get sword pos
            PlayerInventory inv = p.getInventory();
            inv.setBoots(new ItemStack(Material.IRON_BOOTS));
            inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            inv.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            inv.setHelmet(new ItemStack(Material.IRON_HELMET));
        }
    }


    public void chestPlate() {
    }
}

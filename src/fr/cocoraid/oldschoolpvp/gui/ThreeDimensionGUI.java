package fr.cocoraid.oldschoolpvp.gui;

import fr.cocoraid.oldschoolpvp.OldSchoolPlayer;
import fr.cocoraid.oldschoolpvp.OldSchoolPvp;
import fr.cocoraid.oldschoolpvp.gui.item.InteractableItem;
import fr.cocoraid.oldschoolpvp.gui.item.Item3D;
import fr.cocoraid.oldschoolpvp.gui.menu.MainMenu;
import fr.cocoraid.oldschoolpvp.gui.menu.UpgradePvpMenu;
import fr.cocoraid.oldschoolpvp.language.Language;
import fr.cocoraid.oldschoolpvp.utils.Head;
import fr.cocoraid.oldschoolpvp.utils.Reflection;
import fr.cocoraid.oldschoolpvp.utils.UtilItem;
import fr.cocoraid.oldschoolpvp.utils.UtilMath;
import fr.cocoraid.oldschoolpvp.utils.packet.PacketArmorStand;
import fr.cocoraid.oldschoolpvp.utils.textanim.Glow;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * Created by cocoraid on 30/06/2016.
 */
public abstract class ThreeDimensionGUI {


    private static final int itemNumber = 5;


    /**
     * Optionals
     */
    protected Item3D stop;
    protected Item3D credit;


    /**
     * Required
     */
    private PacketArmorStand displayName;
    private PacketArmorStand description;
    private Item3D pageNext;
    private Item3D pagePrevious;



    /**
     * needed for process
     */

    private Glow glow;


    private Item3D lastSelected;
    private int selected = 0;
    private Location center;
    private LinkedList<InteractableItem> items = new LinkedList<>();
    protected Player player;
    protected Location[] positions = new Location[itemNumber];
    protected OldSchoolPlayer op;
    protected Language lang;
    private List<InteractableItem> interactableItems = new ArrayList<>();
    private boolean isReady = false;
    private int currentPage = 0;
    private Location previous;
    private Location next;
    private Location stopLocation;
    private Item3D invPrevious;
    private boolean allItemsAreDisplayed = false;
    private double radius = 3;

    private String title;
    private boolean lastMenu;
    public ThreeDimensionGUI(Player player, String title, boolean lastMenu) {
        this.player = player;
        this.lastMenu = lastMenu;
        this.title = "§b§l" + title;
        glow = new Glow(title,"§b§l", "§6§l","§e§l","§6§l",5,60);
        this.center = player.getLocation().subtract(0,1,0);
        this.op = OldSchoolPlayer.getOldSchoolPlayer(player);
        lang = op.getLanguage();

        inizializeGUI();

    }

    public ThreeDimensionGUI setRotation(float yaw) {
        center.setYaw(yaw);
        return this;
    }

    protected void inizializeGUI() {

    }

    private final int totaldegree = 160;
    public void openGui() {

        if (op.getThreeDimensionGUI() != null) {

            if (!op.getThreeDimensionGUI().isReady) return;

            if (op.getThreeDimensionGUI().getClass().equals(this.getClass())) {
                op.getThreeDimensionGUI().closeGui(false);
                op.getPreviousMenus().clear();
                return;
            }

            if (!(this instanceof MainMenu) && !(this instanceof UpgradePvpMenu))
                op.addPreviousMenu(op.getThreeDimensionGUI());

            setRotation(op.getThreeDimensionGUI().getCenter().getYaw());
            op.getThreeDimensionGUI().closeGui(false);

        }


        double buttonsHeight = 0.7;
        //if (core.selector3DCoinItemEnable && ProdigyGadget.economy != null) buttonsHeight = -0.5;



        Location l = center.clone().subtract(0, 1, 0);
        l.setPitch(0);

        l.setYaw(center.getYaw() - 60);
        previous = l.clone().add(0, buttonsHeight, 0).toVector().add(l.getDirection().multiply(radius)).toLocation(center.getWorld());
        l.setYaw(center.getYaw() + 120);
        /*if (core.selector3DCoinItemEnable && ProdigyGadget.economy != null) {
            coin = new Item3D(player, UtilItem.getSkull(CC.headkey + core.selector3DCoinItemTexture), core.selector3DCoinItemName.replace("%coin", String.valueOf(UtilGeneric.getMoney(player)))).setPosition(previous.clone().add(0, 1.4, 0), UtilMath.getLookAtYaw(center.toVector().subtract(previous.toVector())));
        }*/
        next = l.clone().add(0, buttonsHeight, 0).toVector().add(l.getDirection().multiply(radius)).toLocation(center.getWorld());


        l.setYaw(center.getYaw());
        displayName =  new PacketArmorStand(player,l.clone().add(0, 2.2, 0).toVector().add(l.getDirection().multiply(radius + 0.2)).toLocation(center.getWorld())).setAsNameDisplayer(title);

        stopLocation = displayName.getLocation().clone().add(0, 1, 0);
        op.setThreeDimensionGUI(this);
        inizializeGUI();
        interactableItems.addAll(items);

        this.currentPage = 0;
        allItemsAreDisplayed = true;
        setupItems();
        //Place next page or not ?
        if (Math.ceil(items.size() / itemNumber) >= 2f) {
            // addNextPageButton();
        }


        if (!(this instanceof MainMenu) && !(this instanceof UpgradePvpMenu)) {
            addPreviousMenuButton();
        }


        if (op.getThreeDimensionGUI() != null) {
            displayName.spawn();
            isReady = true;
        }


    }

    private void addPreviousMenuButton() {
        invPrevious = new Item3D(player,"§f◀◀◀");

        if (op.getThreeDimensionGUI() != null) {
            invPrevious.setPosition(previous);
        }


       interactableItems.add(new InteractableItem(invPrevious, (InteractableItem.InteractClickable) (e) -> {
            try {
                ThreeDimensionGUI gui = (ThreeDimensionGUI) Reflection.getConstructor(op.getPreviousMenus().get(op.getPreviousMenus().size() - 1), Player.class).invoke(player);
                gui.openGui();
                op.removePreviousMenu(gui);
                e.getItem().remove();

                invPrevious = null;
            } catch (Exception exp) {
                exp.printStackTrace();

            }
        }));


    }

    private void addPreviousPageButton() {

        pagePrevious = new Item3D(player,"§7←");
        if (pagePrevious != null) {
            pagePrevious.setPosition(previous);
        }

        interactableItems.add(new InteractableItem(pagePrevious, (InteractableItem.InteractClickable) (e) -> {
            if (!allItemsAreDisplayed) return;
            allItemsAreDisplayed = false;
            currentPage--;
            switchPage();
        }));
    }

    private void addNextPageButton() {
        ItemStack nextItem = Head.NEXT.getItem();
        UtilItem.setDisplayName(nextItem,ChatColor.AQUA + lang.nextButton);
        pageNext = new Item3D(player,nextItem);
        Vector vPageNext = player.getLocation().toVector().subtract(next.toVector());


        if (pageNext != null) {
            pageNext.setPosition(next, UtilMath.getLookAtYaw(vPageNext));
        }

        interactableItems.add(new InteractableItem(pageNext, (InteractableItem.InteractClickable) (e) -> {
            if (!allItemsAreDisplayed) return;
            allItemsAreDisplayed = false;
            currentPage++;
            switchPage();
        }));
    }


    private final int stepDegree = totaldegree / (itemNumber - 1);
    private void setupItems() {


        //setupAlgorithm();
        //maybe we can modify this algo to step more items
        //Calculate max Page number                  // Last number    //0,1,2,3,4
        int maxNumber = Math.ceil(items.size() / itemNumber) <= currentPage ? items.size() : (itemNumber * (currentPage + 1));
        int p = 0;
        for (int k = 0; k < items.size(); k++) {
            if (k % itemNumber == 0) {
                if (p == currentPage) {

                    Location l = center.clone();
                    l.setPitch(0);
                    l.setYaw(l.getYaw() - (totaldegree / 2));

                    int index = 0;
                    Map<Item3D, Location> map = new HashMap<>();
                    for (int i = (p * itemNumber); i < maxNumber; i++) {
                        positions[i] = l.toVector().clone().add(l.getDirection().multiply(radius)).toLocation(center.getWorld()).add(0,1.3,0);
                        Item3D it = items.get(i).getItem();
                        map.put(it, positions[index]);
                        index++;
                        l.setYaw(l.getYaw() + stepDegree);

                    }

                    if (op.getThreeDimensionGUI() != null) {
                        map.keySet().forEach(it -> {
                            Vector vector = center.toVector().subtract(map.get(it).toVector());
                            it.setPosition(map.get(it), UtilMath.getLookAtYaw(vector));
                        });
                        allItemsAreDisplayed = true;
                    }
                    break;
                } else
                    p++;

            }
        }

    }

    private void switchPage() {

        //Remove malefic items
        getItems().forEach(item -> {
            if (item.getItem().isSpawned())
                item.getItem().remove();
        });

        //Setup pageNext or not
        if (currentPage >= (int) Math.ceil(items.size() / 5f) - 1) {
            if (pageNext != null) {
                pageNext.remove();
            }
        } else {
            if (pageNext != null && !pageNext.isSpawned()) {
                addNextPageButton();
            }
        }

        //Setup previous page or not
        if (currentPage == 1) {
            if (pagePrevious == null) {
                if (invPrevious != null)
                    invPrevious.remove();
                addPreviousPageButton();
            } else {
                if (!pagePrevious.isSpawned()) {
                    if (invPrevious != null)
                        invPrevious.remove();
                    addPreviousPageButton();
                }
            }
        } else if (currentPage == 0) {
            if (pagePrevious != null)
                pagePrevious.remove();

        }

        //Cool algo again
        setupItems();


    }

    private void reset() {
        getDisplayName().remove();
        if (pageNext != null)
            pageNext.remove();
        if (pagePrevious != null)
            pagePrevious.remove();
        if (invPrevious != null)
            invPrevious.remove();
        if (stop != null)
            stop.remove();
        if (description != null)
            description.remove();

        op.setThreeDimensionGUI(null);

        interactableItems.clear();

    }

    public void closeGui(boolean progressive) {

        if (op.getThreeDimensionGUI() == null) return;
        //Progressive means if instant or with the animation
        if (progressive) {
            op.getPreviousMenus().clear();
            isReady = false;
            new BukkitRunnable() {
                int min = currentPage * 5;
                int max = currentPage >= (int) Math.ceil(items.size() / 5) ? items.size() : min + 5;

                @Override
                public void run() {
                    try {
                        if (player.isOnline()) {
                            //java.lang.IndexOutOfBoundsException: Index: 0, Size: 0

                            Item3D item = items.get(min).getItem();
                            if (item != null && item.getLocation() != null) {
                                player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 0);
                                item.remove();
                                items.remove(item);
                                min++;
                                if (min >= max || min > items.size()) {
                                    this.cancel();
                                    reset();
                                }
                            } else {
                                this.cancel();
                                getItems().forEach(i -> {
                                    if (i.getItem().isSpawned())
                                        i.getItem().remove();
                                });
                                reset();

                            }
                        } else {
                            this.cancel();
                            getItems().forEach(item -> {
                                if (item.getItem().isSpawned())
                                    item.getItem().remove();
                            });
                            reset();

                        }
                    } catch(IndexOutOfBoundsException ex) {
                        this.cancel();
                        getItems().forEach(i -> {
                            if (i.getItem().isSpawned())
                                i.getItem().remove();
                        });
                        reset();
                    }


                }

            }.runTaskTimer(OldSchoolPvp.getInstance(), 2, 2);
        } else {
            getItems().forEach(item -> {
                if (item.getItem().isSpawned()) {
                    item.getItem().remove();
                }
            });
            reset();
        }
    }

    public void update() {
        if(displayName != null && glow != null) {
            displayName.updateName(glow.next());
        }
    }


    protected void stopItem() {
    }

    /*public void description() {
        if (getLastSelected() == null) return;

        String name = nameToClass.get(getLastSelected().getItem().getItemMeta().getDisplayName());
        if (cosmetics.containsKey(name)) {
            if (!cosmetics.get(name).stream().filter(rank -> new Perm("rank", rank).hasPermissionWhitoutMessage(player)).findFirst().isPresent()) {
                if (description == null) {
                    if (displayName != null) {
                        displayName.teleport(player, displayName.getLocation().clone().add(0, 0.2, 0));
                        this.description = new ReflectedArmorStand(displayName.getLocation().clone().subtract(0, 0.4, 0));
                        description.setVisible(false);
                        description.setDisplayName(" ");
                        description.spawnArmorStand(player);
                    }
                }
                this.rank = cosmetics.get(name).getFirst();
                updateDescription();

            }
        } else {
            if (getDescription() != null)
                removeDescription();
        }

    }

    public void updateDescription() {
        if (rank == null) return;
        getDescription().updateName(CC.colored(core.invDescriptionRank.replace("%rank", rankToFormat.get(rank))), player);
    }

    public void removeDescription() {
        if (description != null) {
            description.remove();
            description = null;
            if (displayName != null) {
                displayName.teleport(displayName.getLocation().clone().subtract(0, 0.2, 0));
            }
        }
    }*/


   /* protected void addStopItem() {
        Vector vector = center.toVector().subtract(previous.toVector());
        stop = new Item3D(player, new UtilItem(core.stopCurrentID, (byte) core.stopCurrentDATA, core.invStopCurrentName).getItem(), core.invStopCurrentName);

        interactableItems.add(new InteractableItem(stop, (InteractableItem.InteractClickable) (e) -> {
            stopItem();
            e.getItem().remove();
            interactableItems.remove(e);
        }));


        if (stop != null) {
            stop.setPosition(stopLocation, UtilMath.getLookAtYaw(vector));
        }


    }*/

    public LinkedList<InteractableItem> getItems() {
        return items;
    }

    public PacketArmorStand getDisplayName() {
        return displayName;
    }

    public Location getCenter() {
        return center;
    }

    public boolean isReady() {
        return isReady;
    }

    public int getCurrentPage() {
        return currentPage;
    }


    public Item3D getLastSelected() {
        return lastSelected;
    }

    public void setLastSelected(Item3D lastSelected) {
        this.lastSelected = lastSelected;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public void addSelected() {
        this.selected++;
    }


    public List<InteractableItem> getInteractableItems() {
        return interactableItems;
    }

    public PacketArmorStand getDescription() {
        return description;
    }

}

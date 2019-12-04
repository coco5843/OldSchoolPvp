package fr.cocoraid.oldschoolpvp.utils;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.LinkedList;
import java.util.Random;


public class UtilMath {

    public static final Random random = new Random(System.nanoTime());
    public static final BlockFace[] axis = {BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH, BlockFace.EAST};
    public static final byte[] axisByte = {3, 4, 2, 5};

    public static double offset(Location a, Location b) {
        return offset(a.toVector(), b.toVector());
    }

    public static double offset(Vector a, Vector b) {
        return a.subtract(b).length();
    }

    public static float randomRange(float min, float max) {
        return min + (float) Math.random() * (max - min);
    }

    public static int randomRange(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static double randomRange(double min, double max) {
        return Math.random() < 0.5 ? ((1 - Math.random()) * (max - min) + min) : (Math.random() * (max - min) + min);
    }

    public static byte toPackedByte(float f) {
        return (byte) ((int) (f * 256.0F / 360.0F));
    }



    public static float getLookAtYaw(Vector motion) {
        double dx = motion.getX();
        double dz = motion.getZ();
        double yaw = 0;
        // Set yaw
        if (dx != 0) {
            // Set yaw start value based on dx
            if (dx < 0) {
                yaw = 1.5 * Math.PI;
            } else {
                yaw = 0.5 * Math.PI;
            }
            yaw -= Math.atan(dz / dx);
        } else if (dz < 0) {
            yaw = Math.PI;
        }
        return (float) (-yaw * 180 / Math.PI - 90) + 90;
    }

    public static boolean elapsed(long from, long required) {
        return System.currentTimeMillis() - from > required;
    }

    /**
     * Un vecteur de bump (projection)
     *
     * @param entity
     * @param from
     * @param power
     * @return
     */
    public static Vector getBumpVector(Entity entity, Location from, double power) {
        Vector bump = entity.getLocation().toVector().subtract(from.toVector()).normalize();
        if(Double.isNaN(bump.getX()))
            bump.setX(0);
        if(Double.isNaN(bump.getZ()))
            bump.setZ(0);
        bump.multiply(power);
        return bump;
    }

    /**
     * Un vecteur de pull (attraction)
     *
     * @param entity
     * @param
     * @param power
     * @return
     */
    public static Vector getPullVector(Entity entity, Location to, double power) {
        Vector pull = to.toVector().subtract(entity.getLocation().toVector()).normalize();
        pull.multiply(power);
        return pull;
    }

    /**
     * Projette l'entité à partir d'une location
     *
     * @param entity
     * @param from   projeté à partir de
     * @param power  multiplicateur de puissance
     */
    public static void bumpEntity(Entity entity, Location from, double power) {
        if (entity instanceof Player && entity.hasMetadata("NPC")) return;
        entity.setVelocity(getBumpVector(entity, from, power));
    }

    /**
     * Projette l'entité
     *
     * @param entity
     * @param from
     * @param power
     * @param fixedY fix le Y
     */
    public static void bumpEntity(Entity entity, Location from, double power, double fixedY) {
        if (entity instanceof Player && entity.hasMetadata("NPC")) return;
        Vector vector = getBumpVector(entity, from, power);
        vector.setY(fixedY);
        entity.setVelocity(vector);
    }

    /**
     * Attire l'entité vers une location
     *
     * @param entity
     * @param to     attiré vers
     * @param power  multiplicateur de puissance
     */
    public static void pullEntity(Entity entity, Location to, double power) {
        entity.setVelocity(getPullVector(entity, to, power));
    }

    /**
     * Attiré l'entité
     *
     * @param entity
     * @param from
     * @param power
     * @param fixedY fix le Y
     */
    public static void pullEntity(Entity entity, Location from, double power, double fixedY) {
        Vector vector = getPullVector(entity, from, power);
        vector.setY(fixedY);
        entity.setVelocity(vector);
    }

    /**
     * Vecteur qui s'update autour de l'axe X avec un angle
     *
     * @param v
     * @param angle
     * @return
     */
    public static final Vector rotateAroundAxisX(Vector v, double angle) {
        double y, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        y = v.getY() * cos - v.getZ() * sin;
        z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    /**
     * Vecteur qui s'update autour de l'axe Y avec un angle
     *
     * @param v
     * @param angle
     * @return
     */
    public static final Vector rotateAroundAxisY(Vector v, double angle) {
        double x, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    /**
     * Vecteur qui s'update autour de l'axe Z avec un angle
     *
     * @param v
     * @param angle
     * @return
     */
    public static final Vector rotateAroundAxisZ(Vector v, double angle) {
        double x, y, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos - v.getY() * sin;
        y = v.getX() * sin + v.getY() * cos;
        return v.setX(x).setY(y);
    }


    public static Vector getRandomVector() {
        double x, y, z;
        x = random.nextDouble() * 2 - 1;
        y = random.nextDouble() * 2 - 1;
        z = random.nextDouble() * 2 - 1;

        return new Vector(x, y, z).normalize();
    }

    public static Vector getRandomCircleVector() {
        double rnd, x, z, y;
        rnd = random.nextDouble() * 2 * Math.PI;
        x = Math.cos(rnd);
        z = Math.sin(rnd);
        y = Math.sin(rnd);

        return new Vector(x, y, z);
    }

    public static Vector getRandomVectorline() {

        int min = -5;
        int max = 5;
        int rz = (int) (Math.random() * (max - min) + min);
        int rx = (int) (Math.random() * (max - min) + min);

        double miny = -5;
        double maxy = -1;
        double ry = (Math.random() * (maxy - miny) + miny);

        return new Vector(rx, ry, rz).normalize();
    }


}

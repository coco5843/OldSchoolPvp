package fr.cocoraid.oldschoolpvp.area;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Cuboid {
	
	public Cuboid(Location point1, Location point2) {
		this.xMin = Math.min(point1.getBlockX(), point2.getBlockX());
		this.xMax = Math.max(point1.getBlockX(), point2.getBlockX());
		this.yMin = Math.min(point1.getBlockY(), point2.getBlockY());
		this.yMax = Math.max(point1.getBlockY(), point2.getBlockY());
		this.zMin = Math.min(point1.getBlockZ(), point2.getBlockZ());
		this.zMax = Math.max(point1.getBlockZ(), point2.getBlockZ());
		this.world = point1.getWorld();

		locationsSquared = getHollowCube(point1,point2,1);
	}

	public int xMin, xMax, yMin, yMax, zMin, zMax;
	public World world;

	public boolean isIn(Location loc) {
		if (loc.getWorld() != this.world) return false;
		if (loc.getBlockX() < xMin) return false;
		if (loc.getBlockX() > xMax) return false;
		if (loc.getBlockY() < yMin) return false;
		if (loc.getBlockY() > yMax) return false;
		if (loc.getBlockZ() < zMin) return false;
		if (loc.getBlockZ() > zMax) return false;
		return true;
	}



	public Vector getMinimumPoint() {
		return new Vector(xMin, yMin, zMin);
	}


	public Vector getMaximumPoint() {
		return new Vector(xMax, yMax, zMax);
	}


	public Block[] corners() {
		Block[] res = new Block[8];
		World w = world;
		res[0] = w.getBlockAt(xMin, yMin, zMin);
		res[1] = w.getBlockAt(xMin, yMin, zMax);
		res[2] = w.getBlockAt(xMin, yMax, zMin);
		res[3] = w.getBlockAt(xMin, yMax, zMax);
		res[4] = w.getBlockAt(xMax, yMin, zMin);
		res[5] = w.getBlockAt(xMax, yMin, zMax);
		res[6] = w.getBlockAt(xMax, yMax, zMin);
		res[7] = w.getBlockAt(xMax, yMax, zMax);
		return res;
	}



	public int getLength() {
		Vector min = getMinimumPoint();
		Vector max = getMaximumPoint();

		return (int) (max.getZ() - min.getZ() + 1);
	}

	public List<Location> locationsSquared;


	public List<Location> getHollowCube(Location corner1, Location corner2, double particleDistance) {
		List<Location> result = new ArrayList<Location>();
		World world = corner1.getWorld();
		double minX = Math.min(corner1.getX(), corner2.getX());
		double minY = Math.min(corner1.getY(), corner2.getY());
		double minZ = Math.min(corner1.getZ(), corner2.getZ());
		double maxX = Math.max(corner1.getX(), corner2.getX());
		double maxY = Math.max(corner1.getY(), corner2.getY());
		double maxZ = Math.max(corner1.getZ(), corner2.getZ());

		for (double x = minX; x <= maxX; x+=particleDistance) {
			for (double y = minY; y <= maxY; y+=particleDistance) {
				for (double z = minZ; z <= maxZ; z+=particleDistance) {
					int components = 0;
					if (x == minX || x == maxX) components++;
					if (y == minY || y == maxY) components++;
					if (z == minZ || z == maxZ) components++;
					if (components >= 2) {
						result.add(new Location(world, x, y, z));
					}
				}
			}
		}

		return result;
	}


	public List<Location> getLocationsSquared() {
		return locationsSquared;
	}

	public Location getCenter() {
		int x1 = xMax + 1;
		int y1 = yMax + 1;
		int z1 = zMax + 1;
		return new Location(world, xMin + (x1 - xMin) / 2.0,
				yMin + (y1 - yMin) / 2.0,
				zMin + (z1 - zMin) / 2.0);
	}




	public int getXWidth() {
		return xMax - xMin;
	}
	public int getZWidth() {
		return zMax - zMin;
	}
	public int getHeight() {
		return yMax - yMin;
	}
	public int getArea() {
		return getHeight() * getXWidth() * getZWidth();
	}
}
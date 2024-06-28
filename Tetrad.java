/**
 * Tetrad.java  4/30/2014
 *
 * @author - Jane Doe
 * @author - Period n
 * @author - Id nnnnnnn
 *
 * @author - I received help from ...
 *
 */

import java.awt.Color;
import java.util.List;

// Represents a Tetris piece.
public class Tetrad
{
    private Block[] blocks;	// The blocks for the piece.

    // Constructs a Tetrad.
    public Tetrad(BoundedGrid<Block> grid)
    {
        int random = (int) (Math.random() * 7);
        blocks = new Block[4];
        blocks[0] = new Block();
        blocks[1] = new Block();
        blocks[2] = new Block();
        blocks[3] = new Block();
        Location[] location = new Location[4];
        Color color = Color.RED;
        if (random == 0) {
            location[0] = new Location(0, 4);
            location[1] = new Location(0, 3);
            location[2] = new Location(0, 5);
            location[3] = new Location(0, 6);
        } else if (random == 1) {
            color = Color.GRAY;
            location[0] = new Location(0, 4);
            location[1] = new Location(0, 3);
            location[2] = new Location(0, 5);
            location[3] = new Location(1, 4);
        } else if(random == 2) {
            color = Color.CYAN;
            location[0] = new Location(0, 3);
            location[1] = new Location(0, 4);
            location[2] = new Location(1, 3);
            location[3] = new Location(1, 4);
        } else if(random == 3) {
            color = Color.YELLOW;
            location[0] = new Location(0, 4);
            location[1] = new Location(0, 3);
            location[2] = new Location(0, 5);
            location[3] = new Location(1, 3);
        } else if (random == 4) {
            color = Color.MAGENTA;
            location[0] = new Location(0, 4);
            location[1] = new Location(0, 3);
            location[2] = new Location(0, 5);
            location[3] = new Location(1, 5);
        } else if (random == 5) {
            color = Color.BLUE;
            location[0] = new Location(1, 4);
            location[1] = new Location(1, 3);
            location[2] = new Location(0, 4);
            location[3] = new Location(0, 5);
        } else {
            color = Color.GREEN;
            location[0] = new Location(0, 4);
            location[1] = new Location(0, 3);
            location[2] = new Location(1, 4);
            location[3] = new Location(1, 5);
        }
        for (Block blocks : blocks) {
            blocks.setColor(color);
        }
        addToLocations(grid, location);
    }


    // Postcondition: Attempts to move this tetrad deltaRow rows down and
    //						deltaCol columns to the right, if those positions are
    //						valid and empty.
    //						Returns true if successful and false otherwise.
    public boolean translate(int deltaRow, int deltaCol)
    {
        BoundedGrid grid = blocks[0].getGrid();
        Location [] newLocs = new Location[4];
        Location [] locs = removeBlocks();
        for (int i = 0; i < 4; i++)
        {
            newLocs[i] = new Location(locs[i].getRow() + deltaRow, locs[i].getCol() + deltaCol);
        }
        if (areEmpty(grid, newLocs))
        {
            addToLocations(grid, newLocs);
            return true;
        }
        addToLocations(grid, locs);
        return false;

    }

    // Postcondition: Attempts to rotate this tetrad clockwise by 90 degrees
    //                about its center, if the necessary positions are empty.
    //                Returns true if successful and false otherwise.
    public boolean rotate()
    {
        BoundedGrid grid = blocks[0].getGrid();
        Location one = new Location(blocks[0].getLocation().getRow() - blocks[0].getLocation().getCol() + blocks[1].getLocation().getCol(), blocks[0].getLocation().getRow() + blocks[0].getLocation().getCol() - blocks[1].getLocation().getRow());
        Location two = new Location(blocks[0].getLocation().getRow() - blocks[0].getLocation().getCol() + blocks[2].getLocation().getCol(), blocks[0].getLocation().getRow() + blocks[0].getLocation().getCol() - blocks[2].getLocation().getRow());
        Location three = new Location(blocks[0].getLocation().getRow() - blocks[0].getLocation().getCol() + blocks[3].getLocation().getCol(), blocks[0].getLocation().getRow() + blocks[0].getLocation().getCol() - blocks[3].getLocation().getRow());

        if (grid == null || !grid.isValid(one) || !grid.isValid(two) || !grid.isValid(three)) {
            return false;
        }
        Location[] oldLocs = new Location[4];
        for (int i = 0; i < blocks.length; i++) {
            oldLocs[i] = blocks[i].getLocation();
        }
        Location[] newLocs = new Location[4];
        int pos = 0;

        for (Location locs : oldLocs) {
            newLocs[pos] = new Location(blocks[0].getLocation().getRow() - blocks[0].getLocation().getCol() + locs.getCol(), blocks[0].getLocation().getRow() + blocks[0].getLocation().getCol() - locs.getRow());
            pos++;
        }
        removeBlocks();

        if (areEmpty(grid, newLocs)) {
            addToLocations(grid, newLocs);
        } else {
            addToLocations(grid, oldLocs);
        }
        return true;
    }


    // Precondition:  The elements of blocks are not in any grid;
    //                locs.length = 4.
    // Postcondition: The elements of blocks have been put in the grid
    //                and their locations match the elements of locs.
    private void addToLocations(BoundedGrid<Block> grid, Location[] locs)
    {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].putSelfInGrid(grid, locs[i]);
        }

    }

    // Precondition:  The elements of blocks are in the grid.
    // Postcondition: The elements of blocks have been removed from the grid
    //                and their old locations returned.
    private Location[] removeBlocks()
    {
        Location[] loc = new Location[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            loc[i] = blocks[i].getLocation();
            blocks[i].removeSelfFromGrid();
        }
        return loc;
    }

    // Postcondition: Returns true if each of the elements of locs is valid
    //                and empty in grid; false otherwise.
    private boolean areEmpty(BoundedGrid<Block> grid, Location[] locs)
    {
        List<Location> occupied = grid.getOccupiedLocations();
        for (Location loc : locs) {
            if (occupied.contains(loc) || !grid.isValid(loc)) {
                return false;
            }
        }
        return true;
    }
}
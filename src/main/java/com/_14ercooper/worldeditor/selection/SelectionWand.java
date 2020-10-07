package com._14ercooper.worldeditor.selection;

import com._14ercooper.worldeditor.wrapper.Player;

public class SelectionWand {
    public Player owner; // Store the owner so people can have different selections
    public SelectionManager manager; // Same reason as above

    public static String wandName = "Selection Wand";

    // The wand has NBT so that it can be told apart from a standard axe
    // This determines if a new wand is needed, and if so, creates one
    public static SelectionWand giveNewWand(Player player) {
	for (SelectionWand s : SelectionWandListener.wands) {
	    // Player already has a wand registered to them, give it back
	    if (s.owner.equals(player)) {
		s.givePlayerWand();
		return s;
	    }
	}
	// Player needs a new wand
	SelectionWand wand = new SelectionWand();
	wand.owner = player;
	wand.manager = new SelectionManager();
	wand.givePlayerWand();
	return wand;
    }

    // This actually defines the wand and gives it to the player
    private void givePlayerWand() {
	ItemStack item = new ItemStack(Material.WOODEN_AXE); // Checked by the wand listener
	ItemMeta meta = item.getItemMeta();
	meta.setDisplayName(wandName); // Checked by the wand listener
	item.setItemMeta(meta);
	item.addUnsafeEnchantment(Enchantment.MENDING, 1); // Checked by the wand listener (though not the level)
	owner.getInventory().addItem(item);
    }
}

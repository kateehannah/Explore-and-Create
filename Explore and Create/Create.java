public class Create {
    private final Inventory inventory;

    public Create(Inventory inventory) {
        this.inventory = inventory;
    }

    public void showCraftable() {
        System.out.println("\n--- Crafting Suggestions ---");
        boolean canMakeRevival = inventory.hasMaterial("Nitric Acid") && inventory.hasMaterial("Grapes");
        boolean canMakeSpear = inventory.hasMaterial("Wood") && inventory.hasMaterial("Stone");

        if (!canMakeRevival && !canMakeSpear) {
            System.out.println("No craftable items available right now.");
            return;
        }
        if (canMakeRevival) System.out.println("- Revival Potion (Nitric Acid + Grapes)");
        if (canMakeSpear) System.out.println("- Spear (Wood + Stone)");
    }

    public void craftItem(String itemName) {
        try {
            if (itemName == null || itemName.isBlank()) {
                System.out.println("No item specified to craft.");
                return;
            }
            String normalized = itemName.trim().toLowerCase();
            switch (normalized) {
                case "revival potion", "revival", "revivalpotion" -> craftRevivalPotion();
                case "spear" -> craftSpear();
                default -> System.out.println("Unknown craftable: " + itemName);
            }
        } catch (Exception e) {
            System.out.println("Crafting error: " + e.getMessage());
        }
    }

    private void craftRevivalPotion() {
        if (inventory.useMaterial("Nitric Acid") && inventory.useMaterial("Grapes")) {
            inventory.addItem("Revival Potion");
            System.out.println("You crafted: Revival Potion");
        } else {
            System.out.println("Insufficient materials to craft Revival Potion.");
        }
    }

    private void craftSpear() {
        if (inventory.useMaterial("Wood") && inventory.useMaterial("Stone")) {
            inventory.addItem("Spear");
            System.out.println("You crafted: Spear");
        } else {
            System.out.println("Insufficient materials to craft Spear.");
        }
    }
}

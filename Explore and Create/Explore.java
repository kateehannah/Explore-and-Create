import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Explore {
    private final GameState state;
    private final Inventory inventory;
    private final Random random = new Random();

    public Explore(GameState state) {
        this.state = state;
        this.inventory = state.getInventory();
    }

    public void explorePlace(String place) {
        try {
            if (place == null) {
                throw new PlaceNotFoundException("No place given.");
            }

            String normalized = place.trim().toLowerCase();

            if (state.isAkioRevived()) {
                System.out.println("\nAkio revived! He stole all your things... Inventory reset.");
                inventory.clearAll();
                return;
            }

            System.out.println("\nExploring the " + capitalize(normalized) + "...");

            List<String> possible;
            switch (normalized) {
                case "river" -> possible = List.of("Wood", "Stone", "Grapes");
                case "forest" -> possible = List.of("Wood", "Grapes");
                case "cave" -> possible = List.of("Stone", "Nitric Acid");
                default -> throw new PlaceNotFoundException("Unknown place: " + place);
            }

            List<String> found = getMaterials(possible);

            if (found.isEmpty()) {
                System.out.println("No luck. You didn't find any material.");
                return;
            }

            if (state.isKinoRevived()) {
                System.out.println("Kino's blessing doubled your findings!");
                List<String> duplicated = new ArrayList<>(found);
                found.addAll(duplicated);
            }

            if (state.isBemRevived()) {
                System.out.println("Bem accompanies you. You feel safer.");
            }

            for (String item : found) {
                inventory.addRawMaterial(item);
            }

            System.out.println("You found: " + found);
        } catch (PlaceNotFoundException e) {
            System.out.println("Explore error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error during explore: " + e.getMessage());
        }
    }

    private List<String> getMaterials(List<String> possible) {
        List<String> found = new ArrayList<>();
        for (String material : possible) {
            if (random.nextBoolean()) {
                found.add(material);
            }
        }
        return found;
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}

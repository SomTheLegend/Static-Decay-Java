package in.stl.staticdecay;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The main class for the Static Decay game. Handles game logic, player input, and rendering the game state.
 */
public class StaticDecayGame {

    private final Player player = new Player(1, 1);
    private GameZone currentZone;
    private final Map<String, Item> items = new HashMap<>();
    private final List<CraftingRecipe> recipes = new ArrayList<>();
    private final List<String> messageLog = new ArrayList<>();
    private boolean gameOver = false;
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    /**
     * Constructs a new StaticDecayGame object, initializing items, recipes, and the starting zone.
     */
    public StaticDecayGame() {
        initializeItems();
        initializeRecipes();
        currentZone = createSubwayZone();
        revealInitialArea();
        log("You awaken in a cold, damp subway tunnel. The silence is deafening.");
    }

    /**
     * Initializes all the items available in the game.
     */
    private void initializeItems() {
        //WEAPONS
        items.put("Makeshift Shiv", new Weapon("Makeshift Shiv", "A crude, sharp piece of metal.", 15));
        items.put("9mm Pistol", new Weapon("9mm Pistol", "A standard handgun. Reliable", 35));

        //CONSUMABLES
        items.put("Bandage", new Consumable("Bandage", "Stops bleeding and restores a little health", p -> {
            p.heal(20);
            return "You apply the bandage. It stings, but you feel better (+20 HP)";
        }));
        items.put("Med-kit", new Consumable("Med-kit", "A proper medical kit. Restores significant health.", p -> {
            p.heal(75);
            return "You use the med-kit. The relief is immediate. (+75 HP)";
        }));
        items.put("Canned Food", new Consumable("Canned Food", "Suspicious but edible.", p -> {
            p.eat(40);
            return "It doesn't taste like royalty, but it's food. (+40 HG)";
        }));
        items.put("Thrown Bottle", new Consumable("Thrown Bottle", "Creates a noise to distract enemies", p -> "You get ready to throw the bottle."));

        //RESOURCES
        items.put("Scrap Metal", new Resource("Scrap Metal", "Could be useful for crafting."));
        items.put("Dirty Rags", new Resource("Dirty Rags", "Filthy, but might have a use."));
        items.put("Chemicals", new Resource("Chemicals", "A volatile mix of unknown substances."));
        items.put("Herbs", new Resource("Herbs", "Some strange-looking plants"));
        items.put("Wood", new Resource("Wood", "A splintered piece of wood."));
        items.put("Ammo", new Resource("Ammo", "Rounds for a firearm."));
        items.put("Batteries", new Resource("Batteries", "Power for your flashlight."));

        //QUEST ITEMS
        items.put("Crowbar", new QuestItem("Crowbar", "Useful for prying things open."));
        items.put("Security Keycard", new QuestItem("Security Keycard", "Opens electronically locked doors."));
    }

    /**
     * Initializes all the crafting recipes available in the game.
     */
    private void initializeRecipes() {
        recipes.add(new CraftingRecipe(Map.of("Dirty Rags", 1, "Chemicals", 1), items.get("Bandage")));
        recipes.add(new CraftingRecipe(Map.of("Scrap Metal", 1, "Wood", 1), items.get("Makeshift Shiv")));
        recipes.add(new CraftingRecipe(Map.of("Herbs", 1, "Chemicals", 1), items.get("Med-kit")));
    }

    /**
     * Converts a string array to a 2D character array.
     *
     * @param stringArray The string array to convert.
     * @return The converted 2D character array.
     */
    private char[][] stringArrayToCharArray(String[] stringArray) {
        return Arrays.stream(stringArray).map(String::toCharArray).toArray(char[][]::new);
    }

    /**
     * Creates the Subway zone.
     *
     * @return The Subway GameZone.
     */
    private GameZone createSubwayZone() {
        String[] layoutStr = {
                "####################",
                "#@........#.........#",
                "#.C.M.##.D.#.M.....#",
                "#.....##...#.......#",
                "######?#####.......#",
                "#............M.....#",
                "####################"
        };
        List<Creature> creatures = new ArrayList<>(List.of(new Shambler(5, 2), new Shambler(15, 2), new Shambler(15, 5)));
        Map<Pair<Integer, Integer>, String> interactables = new HashMap<>();
        interactables.put(new Pair<>(2, 2), "C:A rusted locker.");
        interactables.put(new Pair<>(9, 2), "D:A door, jammed shut. It leads to the surface.");
        interactables.put(new Pair<>(6, 4), "?:A blood-stained journal lies on the ground.");
        player.setX(1);
        player.setY(1);
        return new GameZone("Abandoned Subway", 20, 7, stringArrayToCharArray(layoutStr), creatures, interactables);
    }

    /**
     * Creates the City Center zone.
     *
     * @return The City Center GameZone.
     */
    private GameZone createCityCenterZone() {
        String[] layoutStr = {
                "#######################",
                "#@........#...........#",
                "#..M..C...#...M.......#",
                "#.........D...........#",
                "#.........#....?......#",
                "#..C......#...........#",
                "#######################"
        };
        List<Creature> creatures = new ArrayList<>(List.of(new Stalker(4, 2), new Stalker(14, 2), new Stalker(14, 5)));
        Map<Pair<Integer, Integer>, String> interactables = new HashMap<>();
        interactables.put(new Pair<>(6, 2), "C:A ransacked storefront.");
        interactables.put(new Pair<>(4, 5), "C:An overturned police car.");
        interactables.put(new Pair<>(10, 3), "D:A maintenance hatch, sealed tight.");
        interactables.put(new Pair<>(17, 4), "?:A police report flutters in the wind.");
        player.setX(1);
        player.setY(1);
        return new GameZone("Overgrown City Center", 23, 7, stringArrayToCharArray(layoutStr), creatures, interactables);
    }

    /**
     * Creates the Hospital zone.
     *
     * @return The Hospital GameZone.
     */
    private GameZone createHospitalZone() {
        String[] layoutStr = {
                "####################",
                "#@....#......M.....#",
                "#.?.C.#............#",
                "#.....#......D.....#",
                "######M############",
                "#..................#",
                "####################"
        };
        List<Creature> creatures = new ArrayList<>(List.of(new Whisperer(12, 1), new Whisperer(6, 4), new Whisperer(12, 5)));
        Map<Pair<Integer, Integer>, String> interactables = new HashMap<>();
        interactables.put(new Pair<>(2, 2), "?:A patient's chart with frantic scribbles");
        interactables.put(new Pair<>(4, 2), "C:A medical supply cabinet.");
        interactables.put(new Pair<>(15, 3), "D:A door to the security office.");
        player.setX(1);
        player.setY(1);
        return new GameZone("Eerie Hospital", 20, 7, stringArrayToCharArray(layoutStr), creatures, interactables);
    }

    /**
     * Creates the Radio Tower zone.
     *
     * @return The Radio Tower GameZone.
     */
    private GameZone createRadioTowerZone() {
        String[] layoutStr = {
                "##########",
                "#@.......#",
                "#........#",
                "#...A....#",
                "#........#",
                "#...E....#",
                "##########"
        };
        List<Creature> creatures = new ArrayList<>(List.of(new Anomaly(4, 3)));
        Map<Pair<Integer, Integer>, String> interactables = new HashMap<>();
        interactables.put(new Pair<>(4, 5), "E:The broadcast equipment. It needs repairs");
        player.setX(1);
        player.setY(1);
        return new GameZone("Silent Radio Tower", 10, 7, stringArrayToCharArray(layoutStr), creatures, interactables);
    }

    /**
     * Reveals the initial area around the player.
     */
    private void revealInitialArea() {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                currentZone.markVisited(player.getX() + dx, player.getY() + dy);
            }
        }
    }

    /**
     * The main game loop.
     */
    public void run() {
        while (!gameOver) {
            printGameState();
            handlePlayerTurn();
            if (!gameOver) {
                handleCreatureTurn();
                updatePlayerStats();
            }
        }
        printGameOver();
    }

    /**
     * Prints the current game state to the console.
     */
    private void printGameState() {
        System.out.print("[H[2J");
        System.out.flush();
        System.out.println("xxx STATIC DECAY xxx");
        System.out.printf("HP: %d/100 | Hunger: %d/100 | Sanity: %d/100"
                , player.getHp(), player.getHunger(), player.getSanity());
        System.out.println("-".repeat(60));
        String mapStr = buildMapString();
        if (player.getSanity() < 30) {
            System.out.println(scrambleText(mapStr));
        } else {
            System.out.println(mapStr);
        }
        System.out.println("-".repeat(60));
        System.out.println("LOG:");
        messageLog.stream().skip(Math.max(0, messageLog.size() - 5)).forEach(msg -> System.out.println("> " + msg));
        System.out.println("-".repeat(60));
        System.out.println("COMMANDS: [W/A/S/D] Move, [I]nventory, [C]raft, [L]ook, [Q]uit");
    }

    /**
     * Builds the map string for the current zone.
     *
     * @return The map string.
     */
    private String buildMapString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < currentZone.getHeight(); y++) {
            for (int x = 0; x < currentZone.getWidth(); x++) {
                int finalX = x;
                int finalY = y;
                Creature creature = currentZone.getCreatures().stream().filter(c -> c.getX() == finalX && c.getY() == finalY).findFirst().orElse(null);
                boolean isVisible = currentZone.isVisited(x, y);
                if (player.getX() == x && player.getY() == y) {
                    sb.append("@");
                } else if (creature != null && isVisible) {
                    sb.append(creature instanceof Anomaly ? 'A' : 'M');
                } else if (isVisible) {
                    sb.append(currentZone.getTile(x, y));
                } else {
                    sb.append(' ');
                }
            }
            sb.append(' ');
        }
        return sb.toString();
    }

    /**
     * Scrambles the text to simulate the player's insanity.
     *
     * @param text The text to scramble.
     * @return The scrambled text.
     */
    private String scrambleText(String text) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetterOrDigit(chars[i]) && random.nextInt(100) < 20) {
                chars[i] = "?!#%$*&".charAt(random.nextInt(7));
            }
        }
        return new String(chars);
    }

    /**
     * Handles the player's turn.
     */
    private void handlePlayerTurn() {
        System.out.println("Your action: ");
        String input = scanner.nextLine().trim().toUpperCase();
        if (!input.isEmpty()) {
            switch (input.charAt(0)) {
                case 'W', 'A', 'S', 'D' -> movePlayer(input.charAt(0));
                case 'I' -> showInventory();
                case 'C' -> showCrafting();
                case 'L' -> look();
                case 'Q' -> {
                    log("You give up hope.");
                    gameOver = true;
                }
                default -> log("Unknown command.");
            }
        }
    }

    /**
     * Moves the player in the specified direction.
     *
     * @param dir The direction to move.
     */
    private void movePlayer(char dir) {
        int newX = player.getX();
        int newY = player.getY();
        switch (dir) {
            case 'W' -> newY--;
            case 'A' -> newX--;
            case 'S' -> newY++;
            case 'D' -> newX++;
        }
        char targetTile = currentZone.getTile(newX, newY);
        int finalNewX = newX;
        int finalNewY = newY;
        Creature creature = currentZone.getCreatures().stream().filter(c -> c.getX() == finalNewX && c.getY() == finalNewY).findFirst().orElse(null);
        if (creature != null) {
            startCombat(creature);
            return;
        }
        if (targetTile != '#') {
            player.setX(newX);
            player.setY(newY);
            player.loseHunger(1);
            for (int dy = -1; dy <= 1; dy++) {
                for (int dx = -1; dx <= 1; dx++) {
                    currentZone.markVisited(player.getX() + dx, player.getY() + dy);
                }
            }
            String interactable = currentZone.getInteractables().get(new Pair<>(player.getX(), player.getY()));
            if (interactable != null) {
                log("You see something: " + interactable.substring(interactable.indexOf(':') + 1));
            }
        } else {
            log("You can't go that way.");
        }
    }

    /**
     * Allows the player to look at their surroundings.
     */
    private void look() {
        String interactable = currentZone.getInteractables().get(new Pair<>(player.getX(), player.getY()));
        if (interactable != null) {
            handleInteraction(interactable);
        } else {
            log("There is nothing of interest here.");
        }
    }

    /**
     * Handles the player's interaction with an object.
     *
     * @param interaction The interaction string.
     */
    private void handleInteraction(String interaction) {
        char type = interaction.charAt(0);
        String description = interaction.substring(interaction.indexOf(':') + 1);
        log(description);
        switch (type) {
            case 'C' -> {
                log("You search the " + description.toLowerCase() + "...");
                switch (random.nextInt(5)) {
                    case 0 -> {
                        player.addItem(items.get("Canned Food"), 1);
                        log("You found a can of food!");
                    }
                    case 1 -> {
                        player.addItem(items.get("Dirty Rags"), 1);
                        log("You found Dirty Rags!");
                    }
                    case 2 -> {
                        player.addItem(items.get("Scrap Metal"), 1);
                        log("You found Scrap Metal!");
                    }
                    case 3 -> {
                        player.addItem(items.get("Chemicals"), 1);
                        log("You found Chemicals!");
                    }
                    default -> log("...it's empty.");
                }
                currentZone.removeInteractable(player.getX(), player.getY());
            }
            case '?' -> {
                switch (currentZone.getName()) {
                    case "Abandoned Subway" ->
                            log("Journal Entry 1: '...static on the radio for days. Maria thinks she saw something moving in the tunnels. I think she's just scared. We have to try for the surface. The radio tower is our only hope.'");
                    case "Overgrown City Center" ->
                            log("Police Report: '...reports of violent erratic behavior city-wide. Subjects show extreme aggression. Quarantine protocols failing. It's not a riot...it's something else.'");
                    case "Eerie Hospital" -> {
                        log("Patient Chart: 'Patient X exhibits extreme paranoia, muttering about 'the whispers in the static'. Physical form is... unstable. Rapid cellular decay observed. God help us all.'");
                        log("You find a Security Keycard on a nearby desk!");
                        player.addItem(items.get("Security Keycard"), 1);
                    }
                }
                currentZone.removeInteractable(player.getX(), player.getY());
            }
            case 'D' -> {
                switch (currentZone.getName()) {
                    case "Abandoned Subway" -> {
                        if (player.hasItem("Crowbar")) {
                            log("You use the crowbar to force the door open! The city air hits you");
                            currentZone = createCityCenterZone();
                            revealInitialArea();
                        } else {
                            log("It's jammed tight. You need something to pry it open.");
                        }
                    }
                    case "Overgrown City Center" -> {
                        if (player.hasItem("Crowbar")) {
                            log("With a loud groan, the maintenance hatch opens, revealing a dark descent.");
                            currentZone = createHospitalZone();
                            revealInitialArea();
                        } else {
                            log("It's sealed shut. A crowbar might work.");
                        }
                    }
                    case "Eerie Hospital" -> {
                        if (player.hasItem("Security Keycard")) {
                            log("The keycard beeps and the lock clicks open. The air feels heavy.");
                            currentZone = createRadioTowerZone();
                            revealInitialArea();
                        } else {
                            log("It's an electronic lock. You need a keycard.");
                        }
                    }
                }
            }
            case 'E' -> {
                boolean anomalyPresent = currentZone.getCreatures().stream().anyMatch(c -> c instanceof Anomaly);
                if (anomalyPresent) {
                    log("The broadcast equipment is shielded by a strange psychic energy. You can't get close");
                } else {
                    log("With the Anomaly gone, you approach the console. You find enough working parts to send a simple repeating message: '...is anyone out there? We are alive. We are at...' You give the coordinates. You've done it. You've sent a message of hope into the static.");
                    System.out.println("CONGRATULATIONS! YOU HAVE BEATEN STATIC DECAY!");
                    gameOver = true;
                }
            }
        }
    }

    /**
     * Shows the player's inventory.
     */
    private void showInventory() {
        if (player.getInventory().isEmpty()) {
            log("Your inventory is empty.");
            return;
        }
        System.out.println("--- INVENTORY ---");
        player.getInventory().forEach((item, count) -> System.out.printf("%s x%d - %s",
                item.getName(), count, item.getDescription()));
        System.out.println("-".repeat(60));
        System.out.println("Enter item name to use/equip, or [B] to go back: ");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("B") || input.isEmpty()) return;
        Item itemToUse = player.getInventory().keySet().stream().filter(i -> i.getName().equalsIgnoreCase(input)).findFirst().orElse(null);
        if (itemToUse != null) {
            if (itemToUse instanceof Consumable) {
                if (player.removeItem(itemToUse, 1)) {
                    log(((Consumable) itemToUse).applyEffect(player));
                }
            } else if (itemToUse instanceof Weapon) {
                player.setEquippedWeapon((Weapon) itemToUse);
                log("You equipped the " + itemToUse.getName() + ".");
            } else {
                log("You can't use that right now.");
            }
        } else {
            log("You don't have that item.");
        }
    }

    /**
     * Shows the crafting menu.
     */
    private void showCrafting() {
        System.out.println("--- CRAFTING ---");
        for (int i = 0; i < recipes.size(); i++) {
            CraftingRecipe recipe = recipes.get(i);
            String ingredientStr = recipe.getIngredients()
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + " x" + e.getValue())
                    .collect(Collectors.joining(", "));
            System.out.printf("[%d] %s <- %s", i, recipe.getResult().getName(), ingredientStr);
        }
        System.out.println("-".repeat(60));
        System.out.print("Enter recipe number to craft, or [B] to go back: ");
        String inputStr = scanner.nextLine().trim();
        if (inputStr.equalsIgnoreCase("B") || inputStr.isEmpty()) return;
        try {
            int input = Integer.parseInt(inputStr);
            if (input >= 0 && input < recipes.size()) {
                CraftingRecipe recipe = recipes.get(input);
                boolean canCraft = recipe.getIngredients()
                        .entrySet()
                        .stream()
                        .allMatch(entry -> {
                    Item requiredItem = items.get(entry.getKey());
                    return player.getInventory().getOrDefault(requiredItem, 0) >= entry.getValue();
                });
                if (canCraft) {
                    recipe.getIngredients()
                            .forEach((itemName, count) ->
                                    player.removeItem(items.get(itemName), count));
                    player.addItem(recipe.getResult(), 1);
                    log("You successfully crafted a " + recipe.getResult().getName() + "!");
                } else {
                    log("You don't have the required ingredients.");
                }
            }
        } catch (NumberFormatException e) {
            log("Invalid recipe number.");
        }
    }

    /**
     * Starts combat with a creature.
     *
     * @param creature The creature to fight.
     */
    private void startCombat(Creature creature) {
        log("You encounter a " + creature.getName() + "!");
        while (creature.getHp() > 0 && player.getHp() > 0) {
            System.out.println("--- COMBAT ---");
            System.out.println(creature.getName() + " HP: " + creature.getHp());
            System.out.println("Your HP: " + player.getHp());
            System.out.println("Actions: [A]ttack, [I]tem, [R]un");
            System.out.print("Your choice: ");
            String action = scanner.nextLine().trim().toUpperCase();
            boolean playerActed = false;
            if (!action.isEmpty()) {
                switch (action.charAt(0)) {
                    case 'A' -> {
                        int damage = (player.getEquippedWeapon() != null) ? player.getEquippedWeapon().getDamage() : 5;
                        creature.takeDamage(damage);
                        log("You attack the " + creature.getName() + " for " + damage + " damage.");
                        playerActed = true;
                    }
                    case 'I' -> {
                        System.out.println("Use which item? (Type name or B for back)");
                        player.getInventory()
                                .keySet()
                                .stream()
                                .filter(item -> item instanceof Consumable)
                                .forEach(item -> System.out.println("-" + item.getName()));
                        String input = scanner.nextLine();
                        Item itemToUse = player.getInventory()
                                .keySet().stream()
                                .filter(item -> item.getName().equalsIgnoreCase(input)
                                        && item instanceof Consumable).findFirst().orElse(null);
                        if (itemToUse != null) {
                            if (player.removeItem(itemToUse, 1)) {
                                log(((Consumable) itemToUse).applyEffect(player));
                                playerActed = true;
                            }
                        } else {
                            log("Invalid item or action.");
                        }
                    }
                    case 'R' -> {
                        if (random.nextInt(100) < 40) {
                            log("You successfully escaped!");
                            return;
                        } else {
                            log("You failed to escape!");
                            playerActed = true;
                        }
                    }
                }
            }
            if (playerActed && creature.getHp() > 0) {
                if (creature instanceof Whisperer) {
                    int sanityDmg = 20;
                    player.loseSanity(sanityDmg);
                    log("The " + creature.getName() + "'s whispers echo in your mind! You lose " + sanityDmg + " sanity.");
                } else {
                    player.takeDamage(creature.getAttack());
                    log("The " + creature.getName() + " attacks you for " + creature.getAttack() + " damage.");
                }
                if (player.getHp() <= 0) {
                    gameOver = true;
                    return;
                }
            }
        }
        if (creature.getHp() <= 0) {
            log("You defeated the " + creature.getName() + "!");
            currentZone.getCreatures().remove(creature);
        }
    }

    /**
     * Handles the creatures' turn.
     */
    private void handleCreatureTurn() {
        for (Creature creature : new ArrayList<>(currentZone.getCreatures())) {
            if (Math.abs(player.getX() - creature.getX()) + Math.abs(player.getY() - creature.getY()) < 5) {
                int dx = Integer.compare(player.getX(), creature.getX());
                int dy = Integer.compare(player.getY(), creature.getY());
                int newX = creature.getX() + dx;
                int newY = creature.getY() + dy;
                boolean positionOccupied = currentZone.getCreatures()
                        .stream()
                        .anyMatch(c -> c.getX() == newX && c.getY() == newY);
                if (currentZone.getTile(newX, newY) != '#' && !positionOccupied) {
                    creature.setX(newX);
                    creature.setY(newY);
                }
            }
        }
    }

    /**
     * Updates the player's stats.
     */
    private void updatePlayerStats() {
        if (player.getHp() <= 0) {
            gameOver = true;
        }
        String zoneName = currentZone.getName();
        if (zoneName.equals("Eerie Hospital") || zoneName.equals("Abandoned Subway")) {
            if (player.getSanity() > 0) {
                log("The oppressive atmosphere wears on your mind");
                player.loseSanity(2);
            }
        }
    }

    /**
     * Adds a message to the message log.
     *
     * @param message The message to add.
     */
    private void log(String message) {
        messageLog.add(message);
    }

    /**
     * Prints the game over screen.
     */
    private void printGameOver() {
        System.out.println("====================");
        System.out.println("---GAME OVER---");
        System.out.println("====================");
        if (!messageLog.isEmpty()) {
            System.out.println(messageLog.get(messageLog.size() - 1));
        }
    }
}

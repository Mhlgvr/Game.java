import java.util.Random;
import java.util.Scanner;
public class Game {
    //Characteristics
    private static int health = 7;
    private static int energy = 5;
    private static int magic = 3;
    private static int poisons = 0;
    public static int runes = 0;
    //Visits
    private static boolean isMagicCrystalUsed = false;
    private static boolean isLootCampUsed = false;
    private static boolean isCampUsed = false;
    private static boolean secretPathFound = false;

    /**
     * Attempts to use a poison to increase health.
     * If poisons are available, it reduces the number of poisons by one and
     * increases health by 5. If no poisons are available, it will print a
     * message indicating so.
     *
     * @return true if a poison was used successfully, false otherwise.
     */
    public static boolean usePoison() {
        if (poisons > 0) {
            health += 5;
            poisons--;
            return true;
        } else {
            System.out.println("No more poisons");
            return false;
        }
    }
    public static void printStatus() {
        System.out.println("Health: " + health);
        System.out.println("Energy: " + energy);
        System.out.println("Poisons: " + poisons);
        System.out.println("Magic: " + magic);
        System.out.println("Runes: " + runes);
    }
    public static void checkGameOver() {
        if (energy <= 0) {
            gameOver();
        }
        if (health <= 0) {
            if (usePoison()) {
                System.out.println("You used a poison to heal, your health is now " + health);
            } else {
                System.out.println("You have no more poisons");
                gameOver();
            }
        }
    }

    public static void gameOver() {
        System.out.println("~Game Over~");
        
        System.exit(0);
    }


    // Game loop
    /**
     * Main game loop.
     * Prints status, checks for game over, asks the user for a location and
     * executes the corresponding action.
     * The loop runs until the game is over.
     */
    public static void runGame(Scanner scanner) {
        // String[] locations = new String[]{"Camp", "Forest", "Altar", "Portal"};
        boolean[] used_locations = new boolean[]{false, false, false, false};
        while (true) {
            checkGameOver();
            System.out.println("Where do you want to go?\n1. Camp\n2. Forest\n3. Altar\n4. Portal\n5. Status\n6. Exit");
            int answer = scanner.nextInt();
            
            switch (answer) {
                case 1 -> {
                    
                    System.out.println("You have entered the Camp");
                    used_locations[0] = true;
                    exploreCamp(scanner);
                    
                }
                case 2 -> {
                    if (used_locations[1]) {
                        System.out.println("You have already been to this location");
                    } else {
                        System.out.println("You have entered the Forest");
                        used_locations[1] = true;
                        exploreForest(scanner);
                    }
                }
                case 3 -> {
                    if (used_locations[2]) {
                        System.out.println("You have already been to this location");
                    } else {
                        System.out.println("You have entered the Altar");
                        used_locations[2] = true;
                        exploreAltar(scanner);
                    }
                }
                case 4 -> {
                    boolean status = (runes >= 1) & (health >= 5) & (energy >= 3) & (magic >= 1);
                    if (!status) {
                        System.out.println("Портал не активируется, вы можете завершить задание, пополнив ресурсы");
                    } else {
                        System.out.println("You have entered the Portal");
                        used_locations[3] = true;
                        explorePortal();
                    }
                }
                case 5 -> {
                    printStatus();
                }
                case 6 -> {
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                }
                default -> {
                    System.out.println("Invalid input");
                }
            }
        }
    }

    private static void exploreCamp(Scanner scanner) {
        // String[] locations = new String[]{"Map", "Magic crystal", "Loot camp", "Use poison"};
        System.out.println("Where do you want to go?\n1. Map\n2. Magic crystal\n3. Loot camp\n4. Use poison");
        int answer = scanner.nextInt();
        switch (answer) {
            case 1 -> {
                
                if (!isCampUsed) {
                    System.out.println("ADD 2 energy");
                    energy += 2;
                    isCampUsed = true;
                }
                
                // Get random phrase from map
                String[] phrases = new String[]{
                    "На карте отмечены три возможных пути к первым рунам: один через густой лес, другой — по старой заброшенной дороге, третий — скрытая тропа, найденная рядом с лагерем",
                    "Густой лес полон ловушек, но в нём есть полезные ресурсы. Старый путь безопасен, но долго обходить. Скрытая тропа — наиболее безопасна, но её сложно найти без магии",
                    "Вижу, что на карте рядом с вами есть магический источник. Пройди его, если хочешь увеличить свои силы"
                };
                Random random = new Random();
                int roll = random.nextInt(2);
                if (roll == 1) {
                    secretPathFound = true;
                }
                System.out.println(phrases[roll]);
            }
            case 2 -> {
                if (isMagicCrystalUsed) {
                    System.out.println("You have already used this resource");
                    return;
                }
                System.out.println("You used Magic crystal");
                isMagicCrystalUsed = true;
            }
            case 3 -> {
                if (isLootCampUsed) {
                    System.out.println("You have already looted this camp");
                    return;
                }
                System.out.println("You looted camp. Found 1 poison");
                isLootCampUsed = true;
                poisons++;
                energy--;
            }
            case 4 -> {
                if (usePoison()) {
                    System.out.println("You used poison");
                } else {
                    System.out.println("You have no poisons");
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game.runGame(scanner);
    }

    private static void exploreForest(Scanner scanner) {

        System.out.println("""
                Выберите путь:
                1. Следовать основным путём через густой лес.
                2. Вызвать магического фамильяра для разведки пути.
                3. Назад.
                """);
        if (secretPathFound) {
            System.out.print("4. Пойти по скрытой тропе, обнаруженной в лагере");
        }
        int answer = scanner.nextInt();
        if ((answer < 1) | (answer > 4)) {
            System.out.println("Такого пункта нет");
            return;
        }

        switch (answer) {
            case 1 -> {
                if ((health >= 3) & (energy >= 2)) {
                    System.out.println("Вы уверенно идёте по основному пути, избегая некоторых ловушек, но несколько всё-таки срабатывают. К счастью, вы не получили серьёзных травм, и ваш путь продолжается");
                    energy += 2;
                    health -= 3;
                } else {
                    System.out.println("Вы не успеваете избежать всех ловушек, и несколько из них активируются. Вам удаётся выбраться, но ваши силы на исходе");
                    health -= 5;
                    energy -= 2;
                }
            }
            case 2 -> {
                if (magic >= 2) {
                    System.out.println("Вы вызываете своего фамильяра, и он, используя свои способности, находит безопасный путь через лес, избегая большинства опасностей");
                    magic -= 2;
                    health += 3;
                } else {
                    System.out.println("Магия фамильяра не срабатывает должным образом, и вы теряете силы, не получив нужной помощи");
                    magic -= 2;
                    energy -= 2;
                }
            }
            case 3 -> {
                System.out.println("Возвращаемся в лагерь...");
                exploreCamp(scanner);
            }
            case 4 -> {
                if (energy >= 2) {
                    System.out.println("Вы выбрали скрытую тропу, и, благодаря подсказкам на карте, смогли избежать ловушек и не потерять много сил");
                    energy += 2;
                } else {
                    System.out.println("Скрытая тропа оказывается полна неожиданных магических ловушек. Вы теряете драгоценную энергию, пытаясь от них защититься");
                    health -= 1;
                    energy -= 3;
                }
            }
        }
    }

    private static void exploreAltar(Scanner scanner) {
        System.out.println("""
                        Выберите пункт:
                        1. Исследовать символы на алтаре.
                        2. Использовать магический кристалл, найденный ранее.
                        3. Попытаться разрушить алтарь.
                        4. Назад.
                        """);

        int answer = scanner.nextInt();
        if ((answer < 1) | (answer > 4)) {
            System.out.println("Такого пункта нет");
            return;
        }

        switch (answer) {
            case 1 -> {
                if ((magic >= 1) & (energy >= 3)) {
                    System.out.println("Вы внимательно изучаете символы и, приложив усилия, расшифровываете их значение. Это открывает секретный механизм и позволяет вам получить первую руну без лишних трудностей");
                    energy += 2;
                    magic++;
                } else {
                    System.out.println("Ваши попытки расшифровать символы не приводят к успеху. Алтарь выбрасывает магический импульс, и вы теряете несколько сил");
                    health -= 3;
                }
            }
            case 2 -> {
                if (energy >= 2) {
                    System.out.println("Вы активируете кристалл, и его свет облучает алтарь. Он указывает путь к руне, и вы успеваете пройти к ней без потерь");
                    energy -= 2;
                    health += 3;
                } else {
                    System.out.println("Когда вы активировали кристалл, его энергия не совпала с энергией алтаря, что привело к небольшому откату магии. Портал остаётся заблокированным");
                    health--;
                    energy--;
                }
            }
            case 3 -> {
                if ((health >= 3) & (magic >= 3)) {
                    System.out.println("Вы решаете разрушить алтарь и, используя свою магическую мощь, разрушаете его. Это даёт вам доступ к первой руне");
                    runes ++;
                    health -= 5;
                } else {
                    System.out.println("Ваши попытки разрушить алтарь не увенчались успехом. Алтарь выбрасывает мощный магический импульс, отбрасывая вас назад");
                    energy -= 3;
                }
            }
            case 4 -> {
                System.out.println("Возвращаемся в лагерь...");
                runGame(scanner);
            }
        }
    }

    private static void explorePortal() {
        System.out.println("Вы открываете портал и проходите через него, разгадка раскрыта!");
        gameOver();
        System.exit(0);
    }
}

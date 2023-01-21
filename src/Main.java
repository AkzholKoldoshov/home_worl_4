import java.util.Random;

public class Main {

    public static int bossHealth = 700;

    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {280, 270, 250,150,250,300,310,290};
    public static int[] heroesDamage = {10, 15, 20,0,22,25,8,17};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic" , "Medic", "Lucky" , "Thor","golem","berserk"};
    public static int roundNumber;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            playRound();
        }
    }

    public static void berserkHit(){
        Random random = new Random();
        boolean berserkHit = random.nextBoolean();
        if (berserkHit){
            bossDamage = 0;
            System.out.println("Stun");
        }else {
            bossDamage = 50;
        }
    }

    public static void berserk(){
        Random random = new Random();
        int berserk1 = random.nextInt(10) + 1;
        int berserk2 = random.nextInt(5) + 1;
        if (heroesHealth[7] > 0 && bossHealth > 0){
            switch (berserk2){
                case 1:
                    heroesDamage[7] = (heroesDamage[7] + bossDamage) - berserk1;
                    System.out.println("blow returm" + berserk1);
                    break;
                case 2:
                    bossDamage = 50;
                    break;
                case 3:
                    bossDamage = 50;
                    break;
            }
        }
    }
    public static void golem() {
        Random random = new Random();
        boolean golem1 = random.nextBoolean();
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[6] > 0 && heroesHealth[i] > 0 && heroesHealth[6] != heroesHealth[i]){
                heroesHealth[i] += bossDamage / 5;
                heroesHealth[6] -= bossDamage / 5;
            }
        }
    }

    public static void thor(){
        Random random = new Random();
        boolean thor1 = random.nextBoolean();
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[5] > 0 ){
                if (thor1){
                    bossDamage = 0;
                    System.out.println("Thor stunned " + thor1);
                    break;
                }
            } else {
                bossDamage = 50;
                break;
            }

        }
    }

    public static void lucky(){
        Random random = new Random();
        boolean lucky1 = random.nextBoolean();
        if (heroesHealth[4] > 0 && lucky1){
            heroesHealth[4] += bossDamage;
            System.out.println("Lucky hit " + lucky1);
        }

    }

    public static void medicSkill(){
        for (int i = 0; i < heroesHealth.length; i++) {
            if (i == 3){
                continue;
            }
            if (heroesHealth[i] > 0 && heroesHealth[i] < 100  && heroesHealth [3] > 0){
                heroesHealth[i] += 30;
                System.out.println("Medic treatment: " + heroesAttackType[i]);
                break;
            }
        }
    }



    public static void playRound() {
        roundNumber++; // roundNumber = roundNumber + 1
        chooseBossDefence();
        bossHits();
        heroesHit();
        printStatistics();
        medicSkill();
        lucky();
        thor();
        golem();
        berserk();
        berserkHit();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0, 1, 2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coefficient = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = coefficient * heroesDamage[i];
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        /*String defence = "No defence";
        if (bossDefence != null) {
            defence = bossDefence;
        }*/
        System.out.println("ROUND " + roundNumber + " --------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence != null ? bossDefence : "No defence"));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
        }
    }
}
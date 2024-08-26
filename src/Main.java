public class Main {
    public static void main(String[] args) {
        Blackjack blackjack = new Blackjack();
        blackjack.intro();
        do {
            blackjack.play();
        } while (blackjack.getContinuePlay());

        System.out.println("Thanks for playing!");
    }
}
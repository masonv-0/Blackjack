import java.util.Scanner;

public class Blackjack {
    boolean continue_play = false;

    int startingMoney;

    public void intro() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to Blackjack! How much money would you like to start?");
        startingMoney = scan.nextInt();
    }

    public void play() {

        continue_play = false;
        Scanner scan = new Scanner(System.in);
        Scanner scan2 = new Scanner(System.in);

        Player player = new Player(startingMoney);
        Player dealer = new Player(0);

        player.generateCards(0,1);
        dealer.generateCards(0,1);
        player.totalValue();

        player.displayMoney();

        System.out.println("How much would you like to bet?");
        int playerBet = scan.nextInt();

        if (playerBet > startingMoney) {
            System.out.println("You cannot bet more money than you have!");
            continue_play = true;
            return;
        }

        player.setBet(playerBet);

        System.out.print("Here are the dealer's cards: ");
        System.out.print("Unknown, ");
        dealer.printCards(1);
        System.out.println();

        System.out.print("Here are your cards: ");
        player.printCards(0);
        System.out.println();

        //Checking for naturals

        if (dealer.checkNaturals()) {
            if (player.checkNaturals()) {
                player.setBet(0);
                System.out.println("You and the dealer both had a natural, so your bet has been returned!");
                player.displayMoney();
                this.setContinuePlay();
                return;
            } else {
                player.loseBet();
                player.displayMoney();
                this.setContinuePlay();
                return;
            }
        }

        if (player.checkNaturals()) {
            if (dealer.checkNaturals()) {
                player.setBet(0);
                System.out.println("You and the dealer both had a natural, so your bet has been returned!");
                player.displayMoney();
                this.setContinuePlay();
                return;
            }
            else {
                player.setMoney((int) (player.getBet() * 1.5) + player.getMoney());
                System.out.println("You had a natural, so you get 1.5x your bet!");
                startingMoney = player.getMoney();
                player.displayMoney();
                this.setContinuePlay();
                return;
                }
            }

            if (player.getHasAce()) {
                System.out.println("You have an ace! Do you want to count it as 1 or 11?");
                int aceValue = scan.nextInt();
                player.setCardValue(player.getAceIndex(), aceValue);
            }

            if (player.potentialDouble() && playerBet*2 <= player.getMoney()) {
                System.out.println("Would you like to double down on your bet? Y/N");
                boolean doubleDown = scan2.nextLine().equals("Y");
                if (doubleDown) {
                    player.setBet(player.getBet()*2);
                }
            }

            boolean keepAsking = false;
            boolean extraCheck = false;
            do {
                System.out.println("Would you like to Hit or Stand?");
                boolean choice = scan2.nextLine().equals("Hit");
                if (choice) {
                    player.hit();
                    startingMoney = player.getMoney();
                    if (player.getBet() == 0) {
                        extraCheck = true;
                        break;
                    }
                    else {
                        keepAsking = true;
                    }
                }
                else {
                    dealer.autoPlay(player);
                    startingMoney = player.getMoney();
                    if (player.getBet() != 0) {
                        player.stand(dealer);
                        startingMoney = player.getMoney();
                    }
                    this.setContinuePlay();
                    keepAsking = false;
                }
            } while (keepAsking);
        if (extraCheck) {
            this.setContinuePlay();
        }
    }

    public void setContinuePlay() {
        Scanner scan2 = new Scanner(System.in);
        System.out.println("Would you like to continue playing? Y/N");
        continue_play = scan2.nextLine().equals("Y");
        }

    public boolean getContinuePlay() {
        return continue_play;
    }
}


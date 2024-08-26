import java.lang.Math;

public class Player {
    private int money;

    private int bet;

    private int aceIndex = -1;

    boolean hasAce = false;

    private String[] cards = new String[11];

    private int[] cardValues = new int[11];

    int totalCardValues;

    public Player(int StartMoney) {
        money = StartMoney;
    }

    public void generateCards(int startIndex, int endIndex) {
        for (int i = startIndex;i<=endIndex;i++) {
            cardValues[i] = (int)(1+Math.random()*13);
            cards[i] = this.assignCardNumbers(cardValues[i]);
            if (cardValues[i] > 10) {
                cardValues[i] = 10;
            }
            if (cardValues[i] == 1) {
                aceIndex = i;
                hasAce = true;
                cardValues[i] = 11;
            }
        }

    }

    public String assignCardNumbers(int cardNumber) {
        if (cardNumber==1) {
            return "Ace";
        }
        else if (cardNumber<11) {
            return Integer.toString(cardNumber);
        }
        else if (cardNumber==11) {
            return "Jack";
        }
        else if (cardNumber==12) {
            return "Queen";
        }
        else if (cardNumber==13) {
            return "King";
        }
        return null;
    }

    public void printCards(int startIndex) {
        int a = this.findFirstNull();
        for (int i = startIndex;i<a;i++) {
            if (i==a-1) {
                System.out.print(cards[i]);
            }
            else
                System.out.print(cards[i] + ", ");
        }
    }

    public int findFirstNull() {
        int firstNull = 0;
        for (int i = 0;i<cards.length;i++) {
            if (cards[i] == null) {
                firstNull = i;
                break;
            }
        }
        return firstNull;
    }

    public void totalValue() {
        int total = 0;
        for (int i = 0;i<11;i++) {
            total+=cardValues[i];
        }
        totalCardValues = total;
    }

    public boolean checkNaturals() {
        if (totalCardValues == 21) {
            return true;
        }
        return false;
    }

    public boolean potentialDouble() {
        if (totalCardValues > 8 && totalCardValues < 12)
            return true;
        return false;
    }

    public void autoPlay(Player p) {
        System.out.print("The dealer reveals his cards: ");
        this.printCards(0);
        System.out.println();
        while (totalCardValues < 17) {
            this.generateCards(this.findFirstNull(),this.findFirstNull());
            if (this.getHasAce() && ((totalCardValues+10 >= 17) && (totalCardValues+10 <=21))) {
                cardValues[this.findFirstNull()] = 11;
            }
            this.totalValue();
            System.out.println("The dealer draws a " + cards[this.findFirstNull()-1]);
        }
        if (totalCardValues > 21) {
            System.out.println("The dealer busted. You win $" + p.getBet() + "!");
            p.winBet();
        }
    }

    public void hit() {
        this.generateCards(this.findFirstNull(),this.findFirstNull());
        System.out.println("You got a " + cards[this.findFirstNull()-1] + ".");
        System.out.print("Your cards are: ");
        this.printCards(0);
        System.out.println(".");
        this.totalValue();
        if (totalCardValues <= 21) {
            System.out.println("Your total is " + totalCardValues + ".");
        }
        else {
            if (this.getHasAce() && (cardValues[aceIndex] == 11)) {
                cardValues[aceIndex] = 1;
                return;
            }
            System.out.println("You busted.");
            System.out.println("You lost $" + bet + "!");
            this.loseBet();
            this.displayMoney();
        }
    }

    public void stand(Player a) {
        if (this.getTotalCardValues() > a.getTotalCardValues()) {
            System.out.print("The dealer had the following cards: ");
            a.printCards(0);
            System.out.println();
            System.out.println("You win $" + bet + "!");
            this.winBet();
        }
        else if (this.getTotalCardValues() < a.getTotalCardValues()) {
            System.out.print("The dealer had the following cards: ");
            a.printCards(0);
            System.out.println();
            System.out.println("You lose $" + bet + "!");
            this.loseBet();
        }
        else {
            bet = 0;
            System.out.print("The dealer had the following cards: ");
            a.printCards(0);
            System.out.println();
            System.out.println("You and the dealer had equal totals. Your bet has been returned.");
        }
        this.displayMoney();
    }

    public void displayMoney() {
        System.out.println("You have $" + money + ".");
    }

    public void loseBet() {
        money-=bet;
        bet = 0;
    }

    public void winBet() {
        money+=bet;
        bet = 0;
    }

    public void setBet(int b) {
        bet = b;
    }

    public int getBet() {
        return bet;
    }

    public void setMoney(int m) {
        money = m;
    }

    public int getMoney() {
        return money;
    }

    public int getTotalCardValues() {
        return totalCardValues;
    }

    public void setCardValue(int index, int value) {
        cardValues[index] = value;
    }

    public int getAceIndex() {
        return aceIndex;
    }
    public boolean getHasAce()
    {
        return hasAce;
    }
}

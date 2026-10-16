import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Scanner;
public class Blackjack {

    // Method converting the input ArrayList into a string with commas seperating the elements
    public static String stringify(ArrayList<String> array){
        String outputString = "";
        for(int i=0;i<array.size();i++){
            outputString += array.get(i);
            if(i!=array.size()-1){
                outputString += ", ";
            }
        }
        return outputString;
    }

    // Method getting the sum blackjack value of an array of cards
    public static int getValue(ArrayList<String> input){
        int output = 0;
        int aces = 0;
        for(int i=0;i<input.size();i++){

            String card = input.get(i);

            for(int a=2; a<11; a++){
                if(card.contains(Integer.toString(a))){
                    output += a;
                }
            }

            if(card.contains("Jack") || card.contains("Queen") || card.contains("King")){
                output += 10;
            }

            if(card.contains("Ace")){
                aces += 1;
                output += 11;
            }
                
        }

        while(output > 21 && aces < 0){
            aces -= 1;
            output -= 9;
        }
        return output;
    }

    // Method checking whether the deck is empty or not
    public static boolean checkShuffle(ArrayList<String> deck, ArrayList<String> discard){
        if(deck.size() == 0){
            return true;
        }
        else{
            return false;
        }
    }

    // Method that adds the input discard into the input deck, shuffles, and returns the new deck
    public static ArrayList<String> shuffleDeck(ArrayList<String> deck, ArrayList<String> discard, Random random){
        System.out.println("\nShuffling...\n");
        for(int i=0; i<discard.size(); i++){
            deck.add(discard.getFirst());
            discard.remove(discard.getFirst());
        }
        Collections.shuffle(deck, random);
        return deck;
    }

    public static void main(String[] args) {
        ArrayList<String> deck = new ArrayList<String>();
        ArrayList<String> discard = new ArrayList<String>();
        ArrayList<String> playerHand = new ArrayList<String>();
        ArrayList<String> dealerHand = new ArrayList<String>();
        Scanner input = new Scanner(System.in);
        Random shuffleVal = new Random();
        Long seed = shuffleVal.nextLong();
        boolean cont = true;
        int playerHandSize;
        int dealerHandSize;

        System.out.print("Current seed is: " + seed + "\nSpecify seed? (y/N) ");

        if(input.next().equalsIgnoreCase("y")){
            System.out.print("\nSpecify seed: ");

            try{
                seed = input.nextLong();
                
            }
            catch(Exception e){
                System.out.println("Invalid Seed. Seed must be an integer\nUsing fallback seed: " + seed);
                
            }
        }

        System.out.print("Display blackjack rules? (Y/n) ");

        if(!input.next().equalsIgnoreCase("n")){
            System.out.println(
                "\nBlackjack: \n"+
                "The goal is to be as close to a card value of 21 without going over\n"+
                "Numbered cards are simply worth their value\n"+
                "Face cards are always worth 10\n"+
                "Aces are worth either 1 or 11 depending on what leads to a better result\n"+
                "Busting (going over 21) is an immediate loss\n"+
                "The dealer (the computer) always draws below a 17, and holds on a 17 or above\n"+
                "You begin by drawing 2 cards apeice\n"+
                "You are always allowed to hit (draw) or hold (keep your current values)"
            );
        }

        System.out.println();

        shuffleVal.setSeed(seed);

        // Create cards and add them to the deck
        deck.add("Ace of Spades");
        deck.add("Ace of Hearts");
        deck.add("Ace of Clubs");
        deck.add("Ace of Diamonds");
        for(int i=2; i<11; i++){
            deck.add(i + " of Spades");
            deck.add(i + " of Hearts");
            deck.add(i + " of Clubs");
            deck.add(i + " of Diamonds");
        }
        deck.add("Jack of Spades");
        deck.add("Jack of Hearts");
        deck.add("Jack of Clubs");
        deck.add("Jack of Diamonds");
        deck.add("Queen of Spades");
        deck.add("Queen of Hearts");
        deck.add("Queen of Clubs");
        deck.add("Queen of Diamonds");
        deck.add("King of Spades");
        deck.add("King of Hearts");
        deck.add("King of Clubs");
        deck.add("King of Diamonds");

        // Shuffle the deck
        Collections.shuffle(deck, shuffleVal);

        while(cont){

        for(int i=0; i<2; i++){

            if(checkShuffle(deck, discard)){
                shuffleDeck(deck, discard, shuffleVal);
            }

            dealerHand.add(deck.getFirst());    
            deck.removeFirst();
            
            if(checkShuffle(deck, discard)){
                shuffleDeck(deck, discard, shuffleVal);
            }

            playerHand.add(deck.getFirst());
            deck.removeFirst();

            System.out.println("Your current cards are: " + stringify(playerHand));
            System.out.println("Current value of hand: " + getValue(playerHand));
            System.out.println();
        }

        boolean hold = false;

        while(!hold){

            System.out.print("Hit or Hold? ");
            String hitOrHold = input.next();

            if(hitOrHold.equalsIgnoreCase("Hit")){
            
                if(checkShuffle(deck, discard)){
                    shuffleDeck(deck, discard, shuffleVal);
                }

                playerHand.add(deck.getFirst());
                deck.removeFirst();
                System.out.println("Your current cards are: " + stringify(playerHand));
                System.out.println("Current value of hand: " + getValue(playerHand));
                if(getValue(playerHand) > 21){
                    System.out.println("You busted!");
                    hold = true;
                }

            }

            else if(hitOrHold.equalsIgnoreCase("Hold")){
                hold = true;
            }
            
            System.out.println();
        }

        boolean dealerHold = false;

        while(!dealerHold){

            if(getValue(dealerHand)<17){
            
                if(checkShuffle(deck, discard)){
                    shuffleDeck(deck, discard, shuffleVal);
                }

                dealerHand.add(deck.getFirst());
                deck.removeFirst();

            }

            else{
                dealerHold = true;
            }
        }

        if(getValue(dealerHand) > getValue(playerHand)){
            if(getValue(dealerHand) <= 21){
                System.out.println("Dealer wins! Dealer had a value of " + getValue(dealerHand));
            }

            else if(getValue(playerHand) > 21){
                System.out.println("You both busted. Dealer had a value of " + getValue(dealerHand));
            }

            else{
                System.out.println("You win! Dealer had a value of " + getValue(dealerHand));
            }
        }

        else{
            if(getValue(playerHand) <= 21){
                System.out.println("You win! Dealer had a value of " + getValue(dealerHand));
            }
            else if(getValue(dealerHand) > 21){
                System.out.println("You both busted. Dealer had a value of " + getValue(dealerHand));
            }
            else{
                System.out.println("Dealer wins! Dealer had a value of " + getValue(dealerHand));
            }
        }

        playerHandSize = playerHand.size();
        dealerHandSize = dealerHand.size();

        for(int a=0; a<dealerHandSize; a++){
            discard.add(dealerHand.getFirst());
            dealerHand.removeFirst();
        }

        for(int a=0; a<playerHandSize; a++){
            discard.add(playerHand.getFirst());
            playerHand.removeFirst();
        }

        System.out.print("Another round? (Y/n) ");
            if(!input.next().equalsIgnoreCase("N")){
                cont = true;
            }
            else{
                cont = false;
            }
        }
        System.out.println();
    }
}

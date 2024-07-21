import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String args[]){
        Game game = new Game();
        Random rn = new Random();
        Scanner sn = new Scanner(System.in);
        int randomNumber = rn.nextInt(2);
        int number;

        while(!game.gameFinished()){
            game.display();
            if(randomNumber == 0){
                System.out.print("Turn of player 1: ");
                do {
                    number = sn.nextInt();
                    if(game.pl1.doesContain(number) || game.pl2.doesContain(number)){
                        System.out.println("Already selected!!!!");
                    }
                }while(game.pl1.doesContain(number) || game.pl2.doesContain(number));
                game.pl1.addMove(number);
                randomNumber = 1;
            }
            else {
                System.out.print("Turn of player 2: ");
                do {
                    number = sn.nextInt();
                    if(game.pl1.doesContain(number) || game.pl2.doesContain(number)){
                        System.out.println("Already selected!!!!");
                    }
                }while(game.pl1.doesContain(number) || game.pl2.doesContain(number));
                game.pl2.addMove(number);
                randomNumber = 0;
            }
        }

        game.display();
        if(game.pl1.didFinish()){
            System.out.println("Winner is player 1!!!");
        }
        else{
            System.out.println("Winner is player 2!!!");
        }
    }
}

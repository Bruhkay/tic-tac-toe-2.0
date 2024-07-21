public class Game {
    Player pl1;
    Player pl2;

    Game(){
        pl1 = new Player();
        pl2 = new Player();
    }
    public boolean gameFinished(){
        return pl1.didFinish() || pl2.didFinish();
    }
    public void display(){

        System.out.println("");

        int[][] pattern ={{7,8,9}, {4,5,6}, {1,2,3}};
        for(int j = 0; j<3; j++){
            for(int i= pattern[j][0]; i<=pattern[j][2]; i++){
                if(pl1.doesContain(i)){
                    System.out.print("X");
                }
                else if(pl2.doesContain(i)){
                    System.out.print("O");
                }
                else{
                    System.out.print(" ");
                }

                if(i%3!=0){
                    System.out.print("|");
                }
            }
            System.out.println("");
            if(j!=2){
                System.out.println("-----");
            }
        }
        System.out.println("");
    }
}

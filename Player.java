public class Player {
    int[] hand;
    Player(){
        hand = new int[3];
    }
    public void addMove(int move){
        for(int i = 2; i>0; i--){
            hand[i]=hand[i-1];
        }
        hand[0]=move;
    }
    public boolean didFinish(){
        int[][] possibleFinishes ={{1,2,3},{4,5,6},{7,8,9},{1,5,9,},
                {3,5,7},{1,4,7,},{2,5,8},{3,6,9}};
        for(int i = 0; i<8; i++){
            if(doesContain(possibleFinishes[i][0]) && doesContain(possibleFinishes[i][1])
                    && doesContain(possibleFinishes[i][2])){
                return true;
            }
        }
        return false;
    }
    public boolean doesContain(int wanted){
        for(int i = 0; i< 3; i++){
            if(hand[i]==wanted){
                return true;
            }
        }
        return false;
    }
}


/**
 * This is a class representing a solution to Wil Shortz NPR puzzle from
 * August 17th, 2014. 
 * 
 */
class BruteForceTargetGame{
    int targets[] = new int[]{16,17,23,24,39,40};
    /** 
     * This is just our entry point for running on the command line.
     */
    public static void main(String[] args){
        new BruteForceTargetGame();
    }

    /** 
     * Our constructor it will basically read each file until it finds a match.
     */
    public BruteForceTargetGame(){
        this.addValues(0);                
    }

    private boolean addValues(int total){
        int nTotal = 0;

        for(int x = 0; x < this.targets.length; x++){
            nTotal = this.targets[x] + total;
            if(nTotal == 100){
                System.out.print(this.targets[x]);
                return true;
            }
            else if((nTotal + 16) < 100){
                if(this.addValues(nTotal)){
                    System.out.print(" + " + this.targets[x]);
                    return true;
                }
            }
        }
        return false;
    }

}
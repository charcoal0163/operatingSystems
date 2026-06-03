package bankersalgorithm;
class algorithm{
    int processes;
    int resources;
    int[][] alloc;
    int[][] need;
    int[][] max;
    int[] available;
    boolean[] finish;
    int[] sequence;
    int[] totalInst;

    public algorithm(int processes, int resources, int[][] max, int[][] alloc, int[] available, int[] totalInst){
        this.processes = processes;
        this.resources = resources;
        this.max = max;
        this.alloc = alloc;
        this.available = available;
        this.totalInst = totalInst;
        this.need = new int[processes][resources];
    }
    public void calcNeed(int[][] maximum, int[][] allocation){
        for(int i = 0; i < processes; i++){
            for(int j = 0; j < resources; j++){
                need[i][j] = maximum[i][j] - allocation[i][j];
            }
        }
    }
    public void printNeed(){
        System.out.println("The Need Matrix:");
        for(int i = 0; i < processes; i++){
            for(int j = 0; j < resources; j++){
                System.out.print(need[i][j] + "\t");
            }
            System.out.println();
        }
    }
    public boolean request(int processID, int[] request){
        for(int i = 0; i < resources; i++){
            if(request[i] > need[processID][i]){
                System.out.println("INVALID request. Request cannot be greater than need.");
                return false;
            }
        }
        for(int i = 0; i < resources; i++){
            if(request[i] > available[i]){
                System.out.println("INVALID request. Resources not available.");
                return false;
            }
        }
        for(int i = 0; i < resources; i++){
            alloc[processID][i] += request[i];
            available[i] -= request[i];
            need[processID][i] -= request[i];
        }
        if(isSafe(alloc)){
            System.out.println("System remains in safe state. Request granted.");
            System.out.print("The safety sequence is as follows: ");
            for(int i = 0; i < processes; i++){
                System.out.print("P" + sequence[i]);
                if(i != processes - 1){
                    System.out.print(" -> ");
                }
            }
            System.out.println();
            return true;
        }
        else{
            for(int i = 0; i < resources; i++){
                alloc[processID][i] -= request[i];
                available[i] += request[i];
                need[processID][i] += request[i];
            }
            System.out.println("Request may lead to unsafe state. Request denied.");
            return false;
        }
    }
    public boolean isSafe(int[][] allocation){
        int[] tempSeq = new int[processes];
        finish = new boolean[processes];
        int[] work = new int[resources];
        for(int i = 0; i < resources; i++){
            work[i] = available[i];
        }
        int counter = 0;
        while(counter < processes){
            boolean found = false;
            for(int a = 0; a < processes; a++){
                if(!finish[a]){
                    int b;
                    for(b = 0; b < resources; b++){
                        if(need[a][b] > work[b]){
                            break;
                        }
                    }
                    if(b == resources){
                        for(int c = 0; c < resources; c++){
                            work[c] += allocation[a][c];
                        }
                        tempSeq[counter++] = a;
                        finish[a] = true;
                        found = true;
                    }
                }
            }
            if(!found){
                return false;
            }
        }
        sequence = tempSeq;
        return true;
    }
    public void resourceRelease(){
        if(sequence == null || sequence.length == 0){
            System.out.println("Execution halted. Safe sequence unverified.");
            return;
        }
        for(int i = 0; i < processes; i++){
            int processID = sequence[i];
            System.out.println("Releasing allocated resources for P" + processID);
            for(int j = 0; j < resources; j++){
                available[j] += alloc[processID][j];
                alloc[processID][j] = 0;
            }
            System.out.print("  Available Vector State: ");
            for(int j = 0; j < resources; j++){
                System.out.print(available[j] + "  ");
            }
            System.out.println();
        }
        for(int i = 0; i < resources; i++){
            if(totalInst[i] != available[i]){
                System.out.println("There's something wrong.");
                return;
            }
        }
        System.out.println("Total number of instances is correct.");
        System.out.println("System resource reallocation and release complete.");
    }
}

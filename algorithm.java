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
    
    public algorithm(int processes, int resources, int[][] max, int[][] alloc, int[] available){
        this.processes = processes;
        this.resources = resources;
        this.max = max;
        this.alloc = alloc;
        this.available = available;
        this.need = new int[processes][resources];
    }
    public void calcNeed(){
        // need = maximum - allocation
        for(int i = 0; i < processes; i++){
            for(int j = 0; j < resources; j++){
                need[i][j] = max[i][j] - alloc[i][j];
            }
        }
    }
    public void printNeed(){
        System.out.println("The Need Matrix:");
        for(int i = 0; i < processes; i++){
            for(int j = 0; j < resources; j++){
                System.out.print(need[i][j] + " ");
            }
            System.out.println();
        }
    }
    public boolean request(int processID, int[] request){
        // necessary: request <= need
        for (int i = 0; i < resources; i++){
            if (request[i] > need[processID][i]){
                System.out.println("INVALID request.");
                return false;
            }
        }
        // necesssary: request <= available
        for (int i = 0; i < resources; i++){
            if (request[i] > available[i]){
                System.out.println("Resources not available.");
                return false;
            }
        }
        for (int i = 0; i < resources; i++){
            alloc[processID][i] += request[i];
            available[i] -= request[i];
            need[processID][i] -= request[i];
        }
        if(isSafe()){
            System.out.println("System remains in safe state. Request granted.");
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
    public boolean isSafe(){
        int[] tempSeq;
        tempSeq = new int[processes];
        finish = new boolean[processes];
        int[] work = new int[resources];
        for (int i = 0; i < resources; i++){
            work[i] = available[i];
        }
        int counter = 0;
        while (counter < processes){
            boolean found = false;
            for (int a = 0; a < processes; a++){
                if (!finish[a]) {
                    int b;
                    for (b = 0; b < resources; b++){
                        if (need[a][b] > work[b]){
                            break;
                        }
                    }
                    if (b == resources){
                        for (int c = 0; c < resources; c++){
                            work[c] += alloc[a][c];
                        }
                        tempSeq[counter++] = a;
                        finish[a] = true;
                        found = true;
                    }
                }
            }
            if (!found){
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
                need[processID][j] += alloc[processID][j];
                alloc[processID][j] = 0;
            }

            System.out.print("Available Vector State: ");
            for(int j = 0; j < resources; j++){
                System.out.print(available[j] + " ");
            }
            System.out.println();
        }
        System.out.println("System resource reclamation complete.");
    }
}

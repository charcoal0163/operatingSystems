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
    public void resourceRelease(int processID, int[] release){
        if(processID < 0 || processID >= processes){
            System.out.println("Invalid Process ID.");
            return;
        }
        if(release.length != resources){
            System.out.println("Invalid release array length.");
            return;
        }
        System.out.println("Releasing resources of process P" + processID);
        for(int i = 0; i < resources; i++){
            if(release[i] < 0){
                System.out.println("Invalid. Cannot release negative resource amounts.");
                return;
            }
            if(release[i] > alloc[processID][i]){
                System.out.println("Invalid. Process is attempting to release more resources than allocated.");
                return;
            }
        }
        for (int i = 0; i < resources; i++) {
            alloc[processID][i] -= release[i];
            available[i] += release[i];
            need[processID][i] += release[i];
        }

        System.out.println("Updated Allocation Matrix:");
        for (int i = 0; i < processes; i++) {
            for (int j = 0; j < resources; j++) {
                System.out.print(alloc[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Updated available resources:");
        for(int i = 0; i < resources; i++){
            System.out.print(available[i] + " ");
        }
        System.out.println();
        System.out.println("Updated Need Matrix:");
        for(int i = 0; i < processes; i++){
            for(int j = 0; j < resources; j++){
                System.out.print(need[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Resources released successfully.");        
    }
    /*public void resourceRelease(int processID, int[] release){
        System.out.println("Releasing resources of process P" + processID);
        for(int i = 0; i < resources; i++){
            if(release[i] > alloc[processID][i]){
                System.out.println("Invalid. Process is attempting to release more resources than allocated.");
                return;
            }
        }
        // updating the matrices
        for (int i = 0; i < resources; i++) {
            alloc[processID][i] -= release[i];
            available[i] += release[i];
            need[processID][i] += release[i];
        }
        // print allocation matrix after release (decreased)
        System.out.println("Updated Allocation Matrix:");
        for (int i = 0; i < processes; i++) {
            for (int j = 0; j < resources; j++) {
                System.out.print(alloc[i][j] + " ");
            }
            System.out.println();
        }
        // print available vector after release (increased)
        System.out.println("Updated available resources:");
        for(int i = 0; i < resources; i++){
            System.out.print(available[i] + " ");
        }
        // print need matrix after release (increased)
        System.out.println("Updated Need Matrix:");
        for(int i = 0; i < processes; i++){
            for(int j = 0; j < resources; j++){
                System.out.print(need[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Resources released successfully.");        
    }*/
}
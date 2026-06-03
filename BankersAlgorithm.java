package bankersalgorithm;
import java.util.Scanner;
public class BankersAlgorithm{
    public static void main(String[] args) {
        Scanner test = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int processes = test.nextInt();
        System.out.print("Enter the number of resource types: ");
        int resources = test.nextInt();
        int[] totalInst = new int[resources];
        for(int i = 0; i < resources; i++){
            System.out.print("Enter the total number of instances for Resource " + (i + 1) + ": ");
            totalInst[i] = test.nextInt();
        }
        int[][] alloc = new int[processes][resources];
        System.out.println("Enter the Allocation Matrix:");
        for(int i = 0; i < processes; i++){
            for(int j = 0; j < resources; j++){
                alloc[i][j] = test.nextInt();
            }
        }
        int[][] max = new int[processes][resources];
        System.out.println("Enter the Maximum Matrix:");
        for(int i = 0; i < processes; i++){
            for(int j = 0; j < resources; j++){
                max[i][j] = test.nextInt();
            }
        }
        for(int i = 0; i < processes; i++){
            for(int j = 0; j < resources; j++){
                if(alloc[i][j] > max[i][j]){
                    System.out.println("Invalid input. Allocation cannot be greater than maximum.");
                    return;
                }
            }
        }
        for(int j = 0; j < resources; j++){
            for(int i = 0; i < processes; i++){
                if(max[i][j] > totalInst[j]){
                    System.out.println("Invalid input. A process can't have a maximum greater than total number of instances.");
                    return;
                }
            }
        }
        int[] available = new int[resources];
        System.out.println("Enter the Available Resources Vector:");
        for(int i = 0; i < resources; i++){
            available[i] = test.nextInt();
        }
        algorithm obj = new algorithm(processes, resources, max, alloc, available, totalInst);

        obj.calcNeed(max, alloc);
        obj.printNeed();

        if(obj.isSafe(alloc)){
            System.out.println("System in SAFE state.");
            System.out.print("The safety sequence is as follows: ");
            for(int i = 0; i < processes; i++){
                System.out.print("P" + obj.sequence[i]);
                if(i != processes - 1){
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }
        else{
            System.out.println("System in UNSAFE state. Cannot execute sequence.");
            return;
        }
        System.out.print("Request new resources? (Enter 0 or 1): ");
        byte requestInput = test.nextByte();
        if(requestInput == 0){
            System.out.println("Program terminated.");
        }
        else if(requestInput == 1){
            System.out.print("Enter process number: ");
            int processID = test.nextInt();
            System.out.print("Enter request vector: ");
            int[] request = new int[resources];
            for(int i = 0; i < resources; i++){
                request[i] = test.nextInt();
            }
            obj.request(processID, request);
        }
        else{
            System.out.println("Invalid input. Program terminated.");
            return;
        }
        obj.resourceRelease();
    }
}

package bankersalgorithm;
import java.util.Scanner;
public class BankersAlgorithm{
    public static void main(String[] args) {
        Scanner test = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int processes = test.nextInt();
        System.out.print("Enter the number of resource types: ");
        int resources = test.nextInt();
        
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
        int[] available = new int[resources];
        System.out.println("Enter the Available Resources Vector:");
        for(int i = 0; i < resources; i++){
            available[i] = test.nextInt();
        }
        
        algorithm obj= new algorithm(processes, resources, max, alloc, available);
        
        obj.calcNeed();
        obj.printNeed();
        
        if(obj.isSafe()){
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
            System.out.println("System in UNSAFE state.");
        }
        
        if(obj.isSafe()){
            System.out.println("System in SAFE state.");
            System.out.print("The safety sequence is as follows: ");
            for(int i = 0; i < processes; i++){
                System.out.print("P" + obj.sequence[i]);
                if(i != processes - 1){
                    System.out.print(" -> ");
                }
            }
            System.out.println();
            
            obj.resourceRelease();
        }
        else{
            System.out.println("System in UNSAFE state. Cannot execute sequence.");
            return;
        }        
        System.out.println("Request a new process? (Enter 0 or 1)");
        byte requestInput = test.nextByte();
        if(requestInput == 0){
            System.out.println("Program terminated.");
        }
        else if(requestInput == 1){
            System.out.print("Enter process number: ");
            int processID = test.nextInt();
            System.out.print("Enter request details: ");
            int[] request = new int[resources];
            for(int i = 0; i < resources; i++){
                request[i] = test.nextInt();
            }
            obj.request(processID, request);
        }
        else{
            System.out.println("Process terminated. Invalid input.");
        }
    }
}

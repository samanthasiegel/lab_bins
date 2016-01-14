import java.util.*;

/**
 * Runs a number of algorithms that try to fit files onto disks.
 */
public class Bins {
    public static final String DATA_FILE = "example.txt";
    public static int total;

    /**
     * Reads list of integer data from the given input.
     *
     * @param input tied to an input source that contains space separated numbers
     * @return list of the numbers in the order they were read
     */
    public List<Integer> readData (Scanner input) {
        List<Integer> results = new ArrayList<Integer>();
        while (input.hasNext()) {
            results.add(input.nextInt());
        }
        return results;
    }

    /**
     * Implements worst-fit and worst-fit decreasing method using priority queue
     * @param boolean true if worst-fit decreasing method, false if worst-fit method
     * @param list of numbers in the order they were read
     * @return priority queue of disk contents
     */
    public static PriorityQueue<Disk> worstFitMethod(boolean worstFitDecreasing, List<Integer> data){
    	PriorityQueue<Disk> pq = new PriorityQueue<Disk>();
        int diskId = 0;
        pq.add(new Disk(diskId++));
    	for(Integer size:data){
    		Disk d = pq.peek();
    		if((d.freeSpace()>size&&!worstFitDecreasing)||(d.freeSpace()>=size && worstFitDecreasing)){
    			pq.poll();
                d.add(size);
                pq.add(d);
            } else {
                Disk d2 = new Disk(diskId++);
                d2.add(size);
                pq.add(d2);
    		}
    		total+=size;
    	}
    	return pq;
    }
    
    /**
     * The main program.
     */
    public static void main (String args[]) {
        Bins b = new Bins();
        Scanner input = new Scanner(Bins.class.getClassLoader().getResourceAsStream(DATA_FILE));
        List<Integer> data = b.readData(input);
        PriorityQueue<Disk> pq = worstFitMethod(false, data);
        
        System.out.println("total size = " + total / 1000000.0 + "GB");
        System.out.println();
        System.out.println("worst-fit method");
        System.out.println("number of pq used: " + pq.size());
        
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
        System.out.println();
        Collections.sort(data, Collections.reverseOrder());
        pq = worstFitMethod(true, data);
        
        System.out.println();
        System.out.println("worst-fit decreasing method");
        System.out.println("number of pq used: " + pq.size());
        
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
        System.out.println();
    }
}

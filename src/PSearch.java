import java.util.concurrent.*;

public class PSearch implements Callable<Integer> {
  static ExecutorService threadPool;
  int x;
  int[] A;
  int begin;
  int end;

  public PSearch(int x, int[] A, int begin, int end) {
    // x: the target that you want to search
    // A: the array that you want to search for the target
    // begin: the beginning index (inclusive)
    // end: the ending index (exclusive)
    this.x = x;
    this.A = A;
    this.begin = begin;
    this.end = end; 
  }

  public Integer call() throws Exception {
    try{
      for (int i=this.begin; i<this.end; i++) {
        if (this.A[i] == this.x) return i;
      }
    } catch (Exception e){};
    return Integer.valueOf(-1);
  }

  public static int parallelSearch(int x, int[] A, int n) {
    threadPool = Executors.newFixedThreadPool(n);
    int segmentLength = A.length/n;
    int start = 0;
    int result;
	 for(int i=0; i<n; i++) {
      Future<Integer> f = threadPool.submit(new PSearch(x, A, start, start + segmentLength));
      try {
        	result = f.get();
      	if (result > -1) return result;
    	} catch (Exception e){};
      start += segmentLength;
    }
    return -1; // return -1 if the target is not found
  }

  public static void main(String args[]){
    int a[] = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
    int x=1;
    int n=3;
    
    try {
        for (int i=0; i<20; i++) {
			int answer = PSearch.parallelSearch(x, a, n);
			System.out.println("The index is " + answer);
        }
	 } catch (Exception e) { System.err.println(e);}
    
    threadPool.shutdown();
  }
}
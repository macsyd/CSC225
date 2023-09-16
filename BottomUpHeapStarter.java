import java.util.Arrays;

class BottomUpHeap {
  
  public static int[] heapify(int[] S) {
    return bottomUpHeap(S);
  }

  private static int[] bottomUpHeap(int[] S) {
    if(S.length == 1){
      return S;
    }
    int s1len = (int)Math.floor((S.length-1)/2);
    int s2len = (int)Math.ceil((S.length-1)/2);
    int[] s1 = new int[s1len];
    int[] s2 = new int[s2len];
    int i;
    for(i = 0; i < s1len; i++){
      s1[i] = S[i+1];
      s2[i] = S[1+s1len+i];
    }
    if((S.length-1)%2 != 0) {
      s2[i] = S[i+s1len];
    }
    s1 = bottomUpHeap(s1);
    s2 = bottomUpHeap(s2);
    S = merge(S[0], s1, s2);
    downHeap(S);
    return S;
  }

  private static void downHeap(int[] S) {
    int i = 0;
    while(2*i+2 < S.length){
      if(S[i] > S[2*i+1] || S[i] > S[2*i+2]){
        int minInd = S[2*i+1] < S[2*i+2] ? 2*i+1 : 2*i+2;
        swap(S, i, minInd);
        i = minInd;
      } else {
        return;
      }
    }
    
  }

  private static void swap(int[] S, int ind1, int ind2) {
      int tmp = S[ind1];
      S[ind1] = S[ind2];
      S[ind2] = tmp;
  }

  private static int[] merge(int k, int[] s1, int[] s2) {
    int len = s1.length + s2.length + 1;
    int[] S = new int[len];

    S[0] = k;
    int num_elems_in_S = 1;
    int lvl = 0;
    int num_nodes_at_lvl = 1;

    while (num_elems_in_S < S.length) {
      int start_ind = (num_elems_in_S-1)/2;

      for(int i=start_ind; i<start_ind+num_nodes_at_lvl; i++) {
        S[num_elems_in_S++] = s1[i];
      }
      for(int i=start_ind; i<start_ind+num_nodes_at_lvl; i++) {
        S[num_elems_in_S++] = s2[i];
      }
      lvl++;
      num_nodes_at_lvl = (int) Math.pow(2, lvl);      
    }
    return S;
  }


  public static void main(String[] args) {

    int[] S = {9, 3, 5};
    S = heapify(S);
    System.out.println(Arrays.toString(S));
    // Should be {3,9,5}

    // answer in Lecture 24
    int[] S1 = {23, 100, 39, 96, 54, 17, 83, 48, 56, 67, 77, 110, 1, 14, 22};
    S1 = heapify(S1);
    System.out.println(Arrays.toString(S1));
    // Should be {1, 17, 14, 39, 48, 67, 22, 96, 54, 83, 100, 77, 110, 56, 23}
  }
}
    

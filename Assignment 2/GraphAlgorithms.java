import java.awt.Color;
import java.util.*;

public class GraphAlgorithms{

  /* 
   * To draw a list of integers int_list (of type List<Integer)
   * to the canvas, call drawSequence(int_list, writer).
   *
   * The index of each integer in the list will be
   * plotted along the x-axis; the integer value itself
   * is plotted on the y-axis.
   *                                                      */
  private static final int NUM_DIGITS = 10;

  public static List<Integer> MergeSort(List<Integer> S, PixelWriter writer) {
    if(S.size() < 2){
      return S;
    }

    List<Integer> S1 = new ArrayList<>((int)Math.floor(S.size()/2.0));
    List<Integer> S2 = new ArrayList<>((int)Math.ceil(S.size()/2.0));
    divide(S1, S2, S);

    S1 = MergeSort(S1, writer);
    S2 = MergeSort(S2, writer);

    merge(S1, S2, S);
    drawSequence(S, writer);
    return S;
  }

  public static void divide(List<Integer> S1, List<Integer> S2, List<Integer> S){
    int i;
    int mid = (int)Math.floor(S.size()/2.0);
    for(i = 0; i < mid; i++){
      S1.add(i, S.get(i));
      S2.add(i, S.get(i+mid));
    }
    if(S.size()%2 != 0){
      S2.add(i, S.get(i+mid));
    }
  }

  public static void merge(List<Integer> S1, List<Integer> S2, List<Integer> S){
    int i = 0;
    int j = 0;

    while(i < S1.size() && j < S2.size()){
      if(S1.get(i) <= S2.get(j)){
        S.set(i+j, S1.get(i));
        i++;
      } else {
        S.set(i+j, S2.get(j));
        j++;
      }
    }
    while(i < S1.size()){
      S.set(i+j, S1.get(i));
      i++;
    }
    while(j < S2.size()){
      S.set(i+j, S2.get(j));
      j++;
    }
  }


  public static List<Integer> QuickSort(List<Integer> S, PixelWriter writer) {
    if(S.size() < 2){
      return S;
    }
    
    int x = pivot(S);

    List<Integer> L = new ArrayList<>();
    List<Integer> E = new ArrayList<>();
    List<Integer> G = new ArrayList<>();
    split(L, E, G, S, x);
    
    L = QuickSort(L, writer);
    G = QuickSort(G, writer);

    concatenate(L, E, G, S);
    drawSequence(S, writer);
    return S;
  }

  public static int pivot(List<Integer> S){
    return S.get(S.size()/2);
  }

  public static void split(List<Integer> L, List<Integer> E, List<Integer> G, List<Integer> S, int x){
    for(int i = 0; i < S.size(); i++){
      if(S.get(i) < x){
        L.add(S.get(i));
      } else if(S.get(i) > x){
        G.add(S.get(i));
      } else {
        E.add(S.get(i));
      }
    }
  }

  public static void concatenate(List<Integer> L, List<Integer> E, List<Integer> G, List<Integer> S){
    int c = 0;
    for(int i = 0; i < L.size(); i++){
      S.set(c, L.get(i));
      c++;
    }
    for(int i = 0; i < E.size(); i++){
      S.set(c, E.get(i));
      c++;
    }
    for(int i = 0; i < G.size(); i++){
      S.set(c, G.get(i));
      c++;
    }
  }


  public static List<Integer> InsertionSort(List<Integer> S, PixelWriter writer) {
    for(int i = 1; i < S.size(); i++){
      Integer val = S.get(i);
      int j = i-1;
      while(j >= 0 && S.get(j) > val){
        S.set(j+1, S.get(j));
        j--;
      }
      S.set(j+1, val);
      drawSequence(S, writer);
    }
    return S;
  }

  public static List<Integer> RadixSort(List<Integer> S, PixelWriter writer) {
    List<String> tempS = stringify(S);
    int maxLen = String.format("%d", Collections.max(S)).length();
    
    for(int d = 0; d < maxLen; d++){
      tempS = BucketSort(tempS, d);
      S = intify(tempS);
      drawSequence(S, writer);
    }
    
    return S;
  }

  public static List<String> stringify(List<Integer> S){
    List<String> newStringList = new ArrayList<>(S.size());
    for(int i = 0; i < S.size(); i++){
      String s = String.format("%d", S.get(i));
      newStringList.add(s);
    }
    return newStringList;
  }

  public static List<Integer> intify(List<String> S){
    List<Integer> newIntList = new ArrayList<>(S.size());
    for (String string : S) {
      newIntList.add(Integer.valueOf(string));
    }
    return newIntList;
  }

  public static List<String> BucketSort(List<String> S, int d) {
    List<List<String>> buckets = new ArrayList<List<String>>(NUM_DIGITS);
    for(int k = 0; k < NUM_DIGITS; k++){
      buckets.add(new ArrayList<>());
    }
    for(int i = 0; i < S.size(); i++){
      if(S.get(i).length()-1-d < 0){
        buckets.get(0).add(S.get(i));
      } else {
        char val = S.get(i).charAt(S.get(i).length()-1-d);
        buckets.get(Character.getNumericValue(val)).add(S.get(i));
      }
    }
    S.clear();
    for(int j = 0; j < buckets.size(); j++){
      S.addAll(buckets.get(j));
    }
    return S;
  }

  /* DO NOT CHANGE THIS METHOD */
  private static void drawSequence(List<Integer> sequence, PixelWriter writer) {
    for (Integer curr : sequence) {
      for (int j=0; j<sequence.size(); j++) {
        Color c = writer.getColor(j, curr);
        if (c.equals(Color.BLACK))
          writer.setPixel(j, curr, Color.WHITE);
      }
      int x = sequence.indexOf(curr);
      if (!writer.getColor(x, curr).equals(Color.BLACK)) {
        writer.setPixel(sequence.indexOf(curr), curr, Color.BLACK);
      }
    }
  } 


  /* THE FOLLOWING METHODS WILL NOT BE MARKED;
   * YOU MAY IMPLEMENT THEM OPTIONALLY
   */

	/* FloodFillDFS(v, writer, fillColour)
	   Traverse the component the vertex v using DFS and set the colour 
	   of the pixels corresponding to all vertices encountered during the 
	   traversal to fillColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			writer.setPixel(x,y,c);
	*/
	public static void FloodFillDFS(PixelVertex v, PixelWriter writer, Color fillColour){
	}
	
	/* FloodFillBFS(v, writer, fillColour)
	   Traverse the component the vertex v using BFS and set the colour 
	   of the pixels corresponding to all vertices encountered during the 
	   traversal to fillColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			writer.setPixel(x,y,c);
	*/
	public static void FloodFillBFS(PixelVertex v, PixelWriter writer, Color fillColour){
	}
	
}

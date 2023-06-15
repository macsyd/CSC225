import java.util.ArrayList;
import java.util.Arrays;

public class Group {

  ArrayList<Frog> frogs;
  int numFrogs;

  Group() {
    frogs = new ArrayList<Frog>();
    numFrogs = 0;
  }

  public void addFrog(Frog f) {
    if(numFrogs == 0){
      frogs.add(f);
      numFrogs++;
      return;
    }
    
    int low = 0;
    int high = numFrogs - 1;
    int mid = (high + low)/2;
    
    while(low < high){
      if(f.compareTo(frogs.get(mid)) == 0){
        frogs.add(mid, f);
        numFrogs++;
        return;
      } else if(f.compareTo(frogs.get(mid)) < 0){
        high = mid - 1;
      } else {
        low = mid + 1;
      }
      mid = (high + low)/2;
    }

    if(f.compareTo(frogs.get(mid)) == 0){
      frogs.add(mid, f);
    } else if(f.compareTo(frogs.get(mid)) < 0){
      frogs.add(mid, f);
    } else {
      frogs.add(mid+1, f);
    }
    numFrogs++;
  }

  public int size() {
    return numFrogs;
  }


  public Frog get(int i) {
    return frogs.get(i);
  }

  public Group[] halfGroups() {
    int n = numFrogs/2;
    Group g1 = new Group();
    for(int i = 0; i < n; i++){
      g1.addFrog(frogs.get(i));
    }
    Group g2 = new Group();
    for(int j = n; j < numFrogs; j++){
      g2.addFrog(frogs.get(j));
    }

    Group[] halfGroups = {g1, g2};
    return halfGroups;
  }

  @Override
  public String toString() {
    Object[] list = frogs.toArray();
    return Arrays.toString(list);
  }

  public static boolean FrogEquals(Group g1, Group g2) {
    if(g1.size() != g2.size()){
      return false;
    }
    boolean equalFrogs = true;
    for(int i = 0; i < g1.size(); i++){
      if(!g1.get(i).equals(g2.get(i))){
        equalFrogs = false;
        break;
      }
    }
    
    if(g1.size() < 2){
      return equalFrogs;
    }

    Group[] g1halves = g1.halfGroups();
    Group[] g2halves = g2.halfGroups();

    return equalFrogs || FrogEquals(g1halves[0], g2halves[1]) || FrogEquals(g1halves[1], g2halves[0]);
  }
}

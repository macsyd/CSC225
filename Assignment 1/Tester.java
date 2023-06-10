import java.util.Collections;
import java.util.Arrays;
import java.lang.StringBuilder;
import java.util.Random;

public class Tester {

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_RED = "\u001B[31m";

  private static String getRandomString() {
        final char[] CHARS = new char[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        while (sb.length() < 3) { // length of the random string.
            int index = (rnd.nextInt(CHARS.length));
            sb.append(CHARS[index]);
        }
        String str = sb.toString();
        return str;
}

  private static void PrintTest(String testDesc, boolean result) {
    System.out.println(testDesc + ": " + (result? "passed" : ANSI_RED+"failed"+ANSI_RESET));
  }


  private static boolean TestAddFrog(int numFrogs) {
    Frog[] frogs = new Frog[numFrogs];
    for (int i=1; i<=numFrogs; i++) {
      frogs[i-1] = new Frog(getRandomString());
    }

    Group g1 = new Group();
    for(int i=0; i<numFrogs; i++) {
      g1.addFrog(frogs[i]);
    }

    Collections.sort(Arrays.asList(frogs));
    return g1.toString().equals(Arrays.toString(frogs));
  }

  private static boolean TestAddFrogSize(int numFrogs) {
    Group g1 = new Group();
    for(int i=0; i<numFrogs; i++) {
      g1.addFrog(new Frog(getRandomString()));
    }
    return g1.size() == numFrogs;
  }

  private static boolean TestHalfGroups(int numFrogs){
    Frog[] frogs = new Frog[numFrogs];
    for (int i=1; i<=numFrogs; i++) {
      frogs[i-1] = new Frog(getRandomString());
    }
    Collections.sort(Arrays.asList(frogs));
    
    Group g1 = new Group();
    for(int i=0; i<numFrogs; i++) {
      g1.addFrog(new Frog(frogs[i].toString()));
    }

    Group collectionsTop = new Group();
    Group collectionsBot = new Group();
    for (int i=0; i<(numFrogs/2); i++) {
      collectionsTop.addFrog(frogs[i]);
      collectionsBot.addFrog(frogs[i+(numFrogs/2)]);
    }
    if ((numFrogs % 2) == 1) {
      collectionsBot.addFrog(frogs[numFrogs-1]);
    }

    Group[] twoGroups = g1.halfGroups();
    Group top = twoGroups[0];
    Group bot = twoGroups[1];

    boolean topCorrect = top.toString().equals(collectionsTop.toString());
    boolean bottomCorrect = bot.toString().equals(collectionsBot.toString());

    return topCorrect && bottomCorrect;
  }

  private static boolean TestFrogEquals(Group g1, Group g2, boolean correctResult) {
    return Group.FrogEquals(g1, g2) == correctResult;
  }

  public static void main(String[] args) {
    
    System.out.println("Initializing frogs...");
    PrintTest("Test frog ordering", TestAddFrog(1000));
    PrintTest("Test group size()", TestAddFrogSize(1000));
    PrintTest("Halfgroup() even", TestHalfGroups(8));
    PrintTest("Halfgroup() odd", TestHalfGroups(9));

    String[] f1 = new String[] {"A","B","C","D"};
    String[] f2 = new String[] {"B","A","C","D"};
    Group g1 = new Group();
    Group g2 = new Group();
   
    for(int i=0; i<f1.length; i++) {
      g1.addFrog(new Frog(f1[i]));
      g2.addFrog(new Frog(f2[i]));
    }

    PrintTest("FrogsEquals() basic test 1", TestFrogEquals(g1, g2, true));

    f1 = new String[] {"A","B","C","D","E","F","H","G"};
    f2 = new String[] {"O","N","M","L","K","J","I","H"};
    g1 = new Group();
    g2 = new Group();
   
    for(int i=0; i<f1.length; i++) {
      g1.addFrog(new Frog(f1[i]));
      g2.addFrog(new Frog(f2[i]));
    }

    PrintTest("FrogsEquals() basic test 2", TestFrogEquals(g1, g2, true));

    f1 = new String[] {"A","B","C","D","E","F"};
    f2 = new String[] {"F","G","H","I","J","K"};
    g1 = new Group();
    g2 = new Group();
   
    for(int i=0; i<f1.length; i++) {
      g1.addFrog(new Frog(f1[i]));
      g2.addFrog(new Frog(f2[i]));
    }

    PrintTest("FrogEquals() basic test 3", TestFrogEquals(g1, g2, false));

    f1 = new String[] {"H","I","J","K","L","M","N","O"};
    f2 = new String[] {"H","I","J","K","A","B","C","D"};
    g1 = new Group();
    g2 = new Group();
   
    for(int i=0; i<f1.length; i++) {
      g1.addFrog(new Frog(f1[i]));
      g2.addFrog(new Frog(f2[i]));
    }

    PrintTest("FrogEquals() basic test 4", TestFrogEquals(g1, g2, true));

    f1 = new String[] {"A","B"};
    f2 = new String[] {"A"};
    g1 = new Group();
    g2 = new Group();
   
    for(int i=0; i<f1.length; i++) {
      g1.addFrog(new Frog(f1[i]));
      if (i<f1.length-1) {
        g2.addFrog(new Frog(f2[i]));
      }
    }

    PrintTest("FrogEquals() basic test 4", TestFrogEquals(g1, g2, false));
  }
    


}


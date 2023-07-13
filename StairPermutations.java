public class StairPermutations {

    public static int mathematicalFloor (double f) //java floor doesn't lower the value of an integer
    {
        int out = (int)Math.floor(f);
        if (out == f) out--;
        return out;
    }

    public static int possiblePermuations (int N, int s) //Given Number of Steps to Work With S, this will return the number of permutations of the step before it. (Hard to explain without the visual aid I Made) For s = 2 it returns the possible permutations.
    {
        int x = 0;
        if (s % 2 == 0) x = Math.round((float)N / s);
        else x = (int)((float)N / s);
        return x - ((int)(s / 2));
    }

    public static int iterate(int N, int s) //I found that a Recursive Function Works Best For this Program. During My Invesigations of This Problem I Did Begin to Formulate a Geometric Series.
    {
        int permuations = 0;
        int curIts = possiblePermuations(N, s);
        
        //System.out.println( "N = " + Integer.toString(N) + " s = " + Integer.toString(s));
        //System.out.println( "curIts = " + Integer.toString(curIts));

        if (s == 2) return curIts;
        
        
        for (int i = 0; i < curIts; i++)
        {
            N -= s;
            permuations += iterate(N, s - 1); 
            //System.out.println( "permuations = " + Integer.toString(permuations) + " i = " + Integer.toString(i));
        }
        
        return permuations;
    }

    public static int calcBase (int n) //Something I Noticed After Extensive Investigation.
    {
        int x = (int)Math.round(((-1f + Math.sqrt(1f + 8 * n)) / 4f));
        int b = (int)(((float)n) / ((float)x));
        return b;
    }

    public static int solution(int n) {
        
        int base = calcBase(n);
        int permuations = 0;
        
        //System.out.println( "base = " + Integer.toString(base));

        for (int i = 2; i <= base; i++)
        {
            permuations += iterate(n, i);
            //System.out.println( "Permuations = " + Integer.toString(permuations));
        }
        
        return permuations;
    }
}
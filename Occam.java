public class Occam {

    /*public static void main (String[] args)
    {
        System.out.println(Integer.toString(solution(new int[] {1, 1, 1})));
        System.out.println(Integer.toString(solution(new int[] {1, 2, 3, 4, 5, 6})));
        System.out.println(Integer.toString(solution(new int[] {1, 1, 2, 2, 5, 6})));
        System.out.println(Integer.toString(solution(new int[] {1, 1, 2, 2, 2, 5, 6, 12})));
        System.out.println(Integer.toString(solution(new int[] {2, 3, 7, 10, 19})));
    }*/

    public static int solution(int[] l) {

        if (l.length < 3) return 0;

        int triplets = 0;
        int factors[] = new int[l.length]; 

        for (int i = 0; i < l.length - 1; i++) //Occam's Razor... -_-
        {
            for (int j = i + 1; j < l.length; j++)
            {
                if (l[j] % l[i] == 0)
                {
                    factors[j]++;
                    triplets += factors[i];
                }
            }
        }
        
        return triplets;
    }

    /*
    * Usually My Code in Other Programs Have More Clues as to the thought proccesses behind it
    * but this one doesn't really have that so I'm gonna give some background here at the bottom
    * in case someone reading this is interested.
    * 
    * I tried *Really Hard* to See if there was a Way to *Somehow* Get it To Run At O(nlog(n))
    * Instead of O(n^2) But Alas, a guy can dream...
    * 
    * I wanted to try some fancy stuff with hash tables and was looking into instead of straight up checking
    * if a % b == 0 what if some fancy math could be done with prime numbers or the multiple of the numbers.
    * 
    * The Furthest I Got Was a Potential Approach Where it Would Check if Multiples were in a hash table,
    * but couldnt find a way to do that where it wouldn't be outweighed by the fact that the maximum potential
    * size of an integer is *way* larger than the maxmimum length of the list. Looked into Prime factorization
    * and counting certain patterns but I found that what little you *might* save in time optomization was being
    * outweighed by the space and the overall complexity of the program. 
    */



    /* Old Code From Before When I Figured Out "Hey, maybe the List is presorted..."
        This Code Also Deals With Doubles and Stuff and all that.

    import java.util.Collections;
    import java.util.HashMap;
    import java.util.ArrayList;
    import java.util.List;

        List<Integer> _list = new ArrayList<Integer>(l.length);
        HashMap<Integer, Integer> appearences = new HashMap<>(l.length);
        HashMap<Integer, Integer> iterations = new HashMap<>(l.length);
        

        for (Integer i : l) {
            _list.add(i);
            if (!appearences.containsKey(i)) iterations.put(i, 0);
            if (appearences.containsKey(i)) appearences.replace(i, appearences.get(i) + 1);
            else appearences.put(i, 1);
        }

        Collections.sort(_list);

        
        for (int i = 0; i < _list.size() - 1; i++)
        {
            iterations.replace(_list.get(i), iterations.get(_list.get(i)) + 1);
            appearences.replace(_list.get(i), appearences.get(_list.get(i)) - 1);
            for (int j = i + 1; j < _list.size(); j++)
            {
                if (_list.get(j) % _list.get(i) == 0)
                {
                    if (iterations.get(_list.get(i)) == 1) factors[j]++;
                    if (iterations.get(_list.get(i)) <= 2) triplets += factors[i];
                    j += appearences.get(_list.get(j)) - 1;
                }
            }
        }
     */
}
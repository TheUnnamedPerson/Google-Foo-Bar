import java.lang.ref.Reference;
import java.util.Arrays;
import java.util.Set;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Micali_Vazirani {

    public static void main (String[] args)
    {
        System.out.println("Sol - " + Integer.toString(solution(new int[] {30, 36, 16, 33, 24, 32, 28, 31, 34, 48, 32, 40})));
        //System.out.println("Sol - " + Integer.toString(solution(new int[] {1, 1})));
        //System.out.println("Sol - " + Integer.toString(solution(new int[] {1, 7, 3, 21, 13, 19}))); 
        //System.out.println("Sol - " + Integer.toString(solution(new int[] {1,1,1,2}))); 
        //System.out.println("Sol - " + Integer.toString(solution(new int[] {1,1073741823,1,3, 1073741822})));
    }

    public static int MV_Matching (int[] _list) //An Implementation of the Micali-Vazirani General Graph Maximum Matching Algorithm.
    {
        Node[] graph = new Node[_list.length];
        HashMap<Integer, Integer> _matchMap = new HashMap<Integer, Integer>(_list.length);
        Set<Integer> unmatchedNodes = ConcurrentHashMap.newKeySet(_list.length);

        //Solving for Possible Edges.
        for (int i = 0; i < _list.length; i++)
        {
            graph[i] = new Node(_list[i]);
            unmatchedNodes.add(i);
            for (int j = 0; j < _list.length; j++)
            {
                //If m (((int)m & ((int)m - 1)) == 0)), that Means m is a power of 2 and the Pairing Will Terminate.
                //If it Divides By Zero, That Means the 2 Numbers are Equal.
                double m = ((double)(_list[j] + _list[i]) / 2) / Math.abs(_list[j] - _list[i]);
                if (Double.isFinite(m) && (m != (int)m || (((int)m & ((int)m - 1)) == 0)))
                {
                    graph[i]._unMatchedNodes.add(j);
                }
            }
        }

        //Matching
        boolean ExistsAugmentingPath = true;

        HashMap<Integer, Blossom> blossoms = new HashMap<Integer, Blossom>(_list.length / 3); //List Of Possible Blossoms
        HashMap<Integer, Integer> blossomsPointers = new HashMap<Integer, Integer>(_list.length); //The Blossom Key Each Node points to
        HashMap<Integer, TreeNode> TreeNodes = new HashMap<Integer, TreeNode>(_list.length); // THey Corresponding Node for each Number
        Set<Integer> CurrentTreeNodes = ConcurrentHashMap.newKeySet(_list.length);
        Set<Integer> UpcommingTreeNodes = ConcurrentHashMap.newKeySet(_list.length);
        

        while (ExistsAugmentingPath)
        {
            //Clearing Out The Maps and Sets
            blossoms.clear();
            blossomsPointers.clear();
            TreeNodes.clear();
            CurrentTreeNodes.clear();

            //Creating Nodes Using the Unmatched Vertices.
            for (Integer i : unmatchedNodes)
            {
                TreeNodes.put(i, new TreeNode(i, -1, 0));
                CurrentTreeNodes.add(i);
            }
            int level = 0;
            boolean foundAugPath = false;
            while (!foundAugPath) //Preforming a Breadth First Search From all of the Vertices, 1 Level at a Time
            {
                blossoms.clear();

                for (Integer i : CurrentTreeNodes)
                {

                    if (!CurrentTreeNodes.contains(i)) continue;
                    if (level % 2 == 0) //Current Level is Even = Find The Match
                    {
                        //If the Vertex that is Being Connected to is Already in the Map
                        //Then Either an Alternating Path to an unmatched Vertex has Been Found, or a Blossom Has Been Found.
                        if (TreeNodes.keySet().contains(graph[i].match))
                        {
                            //If Scanned, Don't Do Anything.
                            if (TreeNodes.get(graph[i].match).DDFS_Searched) continue;

                            //Blossom / Branch Behavior. Double Depth First Search.
                            //Preform Double Depth Search.
                            int a = i; int b = graph[i].match; //In the bridge, a is the "left node" and b is the "right node"
                            int curA = a; int curB = b;
                            Stack<Integer> A_Stack = new Stack<Integer>();
                            Stack<Integer> B_Stack = new Stack<Integer>();
                            Stack<Integer> A_Pers_Stack = new Stack<Integer>();
                            Stack<Integer> B_Pers_Stack = new Stack<Integer>();
                            {
                                boolean finishedDDFS = false;
                                while (!finishedDDFS)
                                {
                                    //Get Next for A
                                    {
                                        for (Integer parent : TreeNodes.get(A_Stack.peek()).parents)
                                        {
                                            //If Blossom, Go To Base
                                            while (blossomsPointers.containsKey(parent))
                                            {
                                                parent = blossoms.get(blossomsPointers.get(parent)).base;
                                            }

                                            if (TreeNodes.get(parent).DDFS_Searched)
                                            {
                                                curA = parent;
                                                break;
                                            }
                                        }

                                        A_Stack.add(curA);
                                        A_Pers_Stack.add(curA);

                                        TreeNodes.get(A_Stack.peek()).maxLevel = level * 2 + 1 - TreeNodes.get(A_Stack.peek()).level;
                                    }

                                    //Get Next for B
                                    {
                                        for (Integer parent : TreeNodes.get(B_Stack.peek()).parents)
                                        {
                                            //If Blossom, Go To Base
                                            while (blossomsPointers.containsKey(parent))
                                            {
                                                parent = blossoms.get(blossomsPointers.get(parent)).base;
                                            }

                                            if (!TreeNodes.get(parent).DDFS_Searched)
                                            {
                                                curB = parent;
                                                break;
                                            }
                                        }

                                        B_Stack.add(curB);
                                        B_Pers_Stack.add(curB);

                                    }

                                    //Backtracking if both Equal Each Other
                                    {
                                        //First B
                                        if (B_Stack.peek() == A_Stack.peek())
                                        {
                                            boolean stopBacktracking = false;
                                            while (!B_Stack.isEmpty() && !stopBacktracking)
                                            {
                                                B_Stack.pop();
                                                for (Integer parent : TreeNodes.get(B_Stack.peek()).parents)
                                                {
                                                    //If Blossom, Go To Base
                                                    while (blossomsPointers.containsKey(parent))
                                                    {
                                                        parent = blossoms.get(blossomsPointers.get(parent)).base;
                                                    }

                                                    if (!TreeNodes.get(parent).DDFS_Searched)
                                                    {
                                                        curB = parent;
                                                        stopBacktracking = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            B_Stack.add(curB);
                                            B_Pers_Stack.add(curB);
                                        }
                                        //Then A
                                        if (B_Stack.peek() == A_Stack.peek())
                                        {
                                            boolean stopBacktracking = false;
                                            while (!A_Stack.isEmpty() && !stopBacktracking)
                                            {
                                                A_Stack.pop();
                                                for (Integer parent : TreeNodes.get(A_Stack.peek()).parents)
                                                {
                                                    //If Blossom, Go To Base
                                                    while (blossomsPointers.containsKey(parent))
                                                    {
                                                        parent = blossoms.get(blossomsPointers.get(parent)).base;
                                                    }

                                                    if (!TreeNodes.get(parent).DDFS_Searched)
                                                    {
                                                        curA = parent;
                                                        stopBacktracking = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            A_Stack.add(curA);
                                            A_Pers_Stack.add(curA);
                                        }
                                    }
                                    
                                
                                    //If Both *Still* Equal Each Other, a Bottleneck Was Found and Create a New Blossom.
                                    if (B_Stack.peek() == A_Stack.peek())
                                    {
                                        Blossom discoveredBlossom = new Blossom();
                                        discoveredBlossom.base = A_Stack.peek();
                                        while (!A_Pers_Stack.isEmpty())
                                        {
                                            blossomsPointers.put(A_Pers_Stack.peek(), discoveredBlossom.base);
                                            discoveredBlossom._leftNodes.add(A_Pers_Stack.pop());
                                        }
                                        while (!B_Pers_Stack.isEmpty())
                                        {
                                            blossomsPointers.put(B_Pers_Stack.peek(), discoveredBlossom.base);
                                            discoveredBlossom._rightNodes.add(B_Pers_Stack.pop());
                                        }
                                        discoveredBlossom.a = a;
                                        discoveredBlossom.b = b;
                                        finishedDDFS = true;
                                        break;
                                    }
                                    else if (unmatchedNodes.contains(A_Stack.peek()) || unmatchedNodes.contains(B_Stack.peek()))
                                    { //Found an Unmatched Node
                                        
                                    }
                                        
                                    TreeNodes.get(curB).maxLevel = level * 2 + 1 - TreeNodes.get(B_Stack.peek()).level;
                                        
                                    
                                }
                            }
                        }
                        else //If It Doesn't Yet Exist in the Map, Create a New Node and Keep Up the BFS.
                        {
                            { 

                            }

                            //If 
                            {
                                TreeNode matchedNode = new TreeNode(graph[i].match, i, level + 1);
                                TreeNodes.put(matchedNode.value, matchedNode);
                                UpcommingTreeNodes.add(matchedNode.value);
                            }
                        }
                    }
                    else //Level is Odd = Find it's unmatched nodes
                    {
                        for (Integer j : graph[i]._unMatchedNodes)
                        {
                            if (unmatchedNodes.contains(j))
                            {
                                //Code For Creating Matches and Inverting Paths.
                                { //Logic: Match i to j, go up to parent, use parent[0] 
                                    
                                }
                            }
                            else //Unmatched Edge has a Match.
                            {
                                //First, Check Unmatched Edge Leads to a Lower Level
                                {

                                }

                                {

                                }
                            }
                        }
                    }
                }

                level++;
                //iterating each node, stepping up a level.
                
            }
        }

        return 0; //Temp
    }

    public static class Node
    {
        int value;
        HashSet<Integer> _unMatchedNodes; //Indexes
        int match; //Index
        int tenacity;

        public Node (int v)
        {
            this.value = v;
        }
    }

    public static class TreeNode
    {
        int value; //Index
        List<Integer> children; //Indexes;
        List<Integer> parents; //Index, -1 = No Parent
        int level;
        int maxLevel;
        boolean DDFS_Searched = false;

        public TreeNode (int v, int p, int l)
        {
            this.value = v;
            this.parents = new ArrayList<Integer>(16);
            this.parents.add(p);
            this.level = l;
            children = new ArrayList<Integer>();
        }

        public TreeNode (int v, int p, int l, ArrayList<Integer> ch)
        {
            this.value = v;
            this.parents = new ArrayList<Integer>(16);
            this.parents.add(p);
            this.level = l;
            this.children = ch;
        }
    }

    public static class Blossom
    {
        HashSet<Integer> _leftNodes;
        HashSet<Integer> _rightNodes;
        int base;
        int a;
        int b;
        int tenacity;
    }

    public static int solution(int[] banana_list) {  

        int freeTrainers = banana_list.length; //The Output
        int perfectInts = 0; //Number of Values That Don't Have Invalid Pairs;

        //This Is Used To Keep Track of Pairs that End. Double Hashmaps Since It can Check if it Contains a Key in Constant Time.
        ConcurrentHashMap<Integer, HashSet<Integer>> badPairs = new ConcurrentHashMap<Integer, HashSet<Integer>>(banana_list.length);

        boolean[] isPerfect = new boolean[banana_list.length]; //Keeps Track of if an entry has had an invalid pair;
        Arrays.fill(isPerfect, true);

        for (int i = 0; i < banana_list.length; i++)
        {
            if (!isPerfect[i]) badPairs.putIfAbsent(banana_list[i], new HashSet<>(banana_list.length)); //If a Different Value Has Had It a a pair, add it.
            for (int j = i + 1; j < banana_list.length; j++)
            {
                //If m (((int)m & ((int)m - 1)) == 0)), that Means m is a power of 2 and the Pairing Will Terminate.
                //If it Divides By Zero, That Means the 2 Numbers are Equal.
                double m = ((double)(banana_list[j] + banana_list[i]) / 2) / Math.abs(banana_list[j] - banana_list[i]);
                if (Double.isFinite(m) && m != (int)m) continue;
                if (Double.isInfinite(m) || Double.isNaN(m) || (((int)m & ((int)m - 1)) == 0))
                {
                    System.out.println("   bad - " + Integer.toString(banana_list[i]) + " ; " + Integer.toString(banana_list[j])); 
                    badPairs.putIfAbsent(banana_list[i], new HashSet<>(banana_list.length));
                    badPairs.get(banana_list[i]).add(banana_list[j]);
                    isPerfect[i] = false;
                    isPerfect[j] = false;
                }
            }
            if (isPerfect[i]) perfectInts++;
        }

        //This Will Go Through Each Key and Check if Another Key Can Be Used to Form a Pair.
        //It Will Match it With The Key That Has the Highest Number of Bad Pairs To Allow For Maximum Pairings.
        for (Integer i : badPairs.keySet())
        {
            if (!badPairs.containsKey(i)) continue; //Just In Case

            //Key With The Largest Number of Bad Pairs and It's Corresponding Size.
            int maxKey = 0; 
            int maxSize = 0;
            for (Integer j : badPairs.keySet())
            {
                if (i != j && !badPairs.get(i).containsKey(j))
                {
                    if (maxSize <= badPairs.get(j).size()) { maxKey = j; maxSize = badPairs.get(j).size(); }
                }
            }

            if (maxKey == 0) //If No Key Was Found, Match it With a Perfect Entry.
            {
                if (perfectInts > 0)
                {
                    perfectInts--;
                    badPairs.remove(i);
                    freeTrainers -= 2;
                }
            }
            else //Match it With The Corresponding Key
            {
                badPairs.remove(maxKey);
                badPairs.remove(i);
                freeTrainers -= 2;
            }
        }

        //The Remaining Perfect Keys Can Be Matched Amongst Themselves Trivially.
        //If There is an Odd Amount That Means There Is One That Can't Be Matched With Anything
        //Since Either The Imperfect Entries Already Would Have Been Matched To One.
        freeTrainers -= perfectInts - (perfectInts % 2);

        return freeTrainers;
    }
}
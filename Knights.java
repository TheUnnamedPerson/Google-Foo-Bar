import javax.swing.text.StyledEditorKit.BoldAction;

public class Knights {

    public static int CalculateJumps (Coords coords, int safe)
    {
        if (Coords.Comp(coords, new Coords(-3, 0)))
        {
            return 5;
        }
        else if ((Coords.Comp(coords, new Coords(-1, -1)) && safe == 2) || (Coords.Comp(coords, new Coords(1, 1)) && safe == 1))
        {
            return 4;
        }
        coords.x = Math.abs(coords.x);
        coords.y = Math.abs(coords.y);
        if (Coords.Comp(coords, new Coords(1, 2)))
        {
            return 1;
        }
        else if (Coords.Comp(coords, new Coords(1, 1)) || Coords.Comp(coords, new Coords(4, 0)) || Coords.Comp(coords, new Coords(2, 0)) || Coords.Comp(coords, new Coords(4, 2)) || Coords.Comp(coords, new Coords(3, 1)) || Coords.Comp(coords, new Coords(3, 3)))
        {
            return 2;
        }
        else if (Coords.Comp(coords, new Coords(2, 2)) || Coords.Comp(coords, new Coords(4, 4)))
        {
            return 4;
        }
        return 3;
    }

    public static class Coords
    {
        int x;
        int y;
        public Coords (int x, int y) { this.x = x; this.y = y; }
        public static Boolean Comp (Coords a, Coords b)
        {
            return ((a.x == b.x && a.y == b.y) || (a.x == b.y && a.y == b.x));
        }
        public Coords subtractCoords (Coords b)
        {
            return new Coords(this.x - b.x, this.y - b.y);
        }
    }

    public static Coords GetCoords (int n)
    {
        return new Coords(n % 8, n / 8);
    } 


    public static int solution(int src, int dest) {
        //Your code here

        if (src == dest) return 0;

        Coords source = GetCoords(src);
        Coords destination = GetCoords(dest);

        Coords rel = destination.subtractCoords(source);
        rel.x *= (source.x < 4) ? 1 : -1;
        rel.y *= (source.y < 4) ? 1 : -1;

        int margin = (source.x == 0 || source.y == 0 || source.x == 7 || source.y == 7) ? 1 : ((source.x == 1 || source.y == 1 || source.x == 6 || source.y == 6) ? 2 : 0);

        if (Math.abs(rel.x) < 5 && Math.abs(rel.y) < 5) return CalculateJumps(rel, margin);
        else if (((float)rel.x / 2f) == rel.y) return rel.y;
        else if (((float)rel.y / 2f) == rel.x) return rel.x;
        
        int minJumps = 7;

        for (int i = 0; i < 63; i++)
        {
            Coords iCoords = GetCoords(i);
            if (
            ((iCoords.x >= source.x && iCoords.x <= destination.x) || (iCoords.x >= destination.x && iCoords.x <= source.x)) && 
            ((iCoords.y >= source.y && iCoords.y <= destination.y) || (iCoords.y >= destination.y && iCoords.y <= source.y))
            )
            {
                Coords iRel1 = iCoords.subtractCoords(source);
                iRel1.x *= (source.x < 4) ? 1 : -1;
                iRel1.y *= (source.y < 4) ? 1 : -1;
                Coords iRel2 = iCoords.subtractCoords(destination);
                iRel2.x *= (destination.x < 4) ? 1 : -1;
                iRel2.y *= (destination.y < 4) ? 1 : -1;
                
                if ((Math.abs(iRel1.x) < 5 && Math.abs(iRel1.y) < 5) && Math.abs(iRel2.x) < 5 && Math.abs(iRel2.y) < 5)
                {
                    int margin2 = (destination.x == 0 || destination.y == 0 || destination.x == 7 || destination.y == 7) ? 1 : ((destination.x == 1 || destination.y == 1 || destination.x == 6 || destination.y == 6) ? 2 : 0);
                    int totalJumps = CalculateJumps(iRel1, margin) + CalculateJumps(iRel2, margin2);
                    if (minJumps > totalJumps) minJumps = totalJumps;
                }
            }
        }

        return minJumps;
    }
}
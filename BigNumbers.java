import java.math.BigInteger;

public class BigNumbers {
    public static String solution(int[] xs) {
        if (xs.length == 1) return Integer.toString(xs[0]);
        int smallNegative = 1;
        int negatives = 0; 
        BigInteger out = BigInteger.ZERO;
		
        for (int i = 0; i < xs.length; i++)
        {
            if (xs[i] < 0)
            {
                negatives++;
                if (smallNegative == 1 || smallNegative < xs[i])
                {
                    if (out == BigInteger.ZERO && smallNegative != 1) out = BigInteger.ONE;
                    out = out.multiply(BigInteger.valueOf(smallNegative));
                    smallNegative = xs[i];
                }
                else
                {
                    if (out == BigInteger.ZERO) out = BigInteger.ONE;
                    out = out.multiply(BigInteger.valueOf(xs[i]));
                }
            }
            else if (xs[i] != 0)
            {
                if (out == BigInteger.ZERO) out = BigInteger.ONE;
                out = out.multiply(BigInteger.valueOf(xs[i]));
            }
        }
        
        if (negatives % 2 == 0) out = out.multiply(BigInteger.valueOf(smallNegative));
        
        return out.toString();
    }
}
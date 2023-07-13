
public class Checksum {

    public static int solution(int start, int length) {

        int _Checksum = 0;
        int j = start;

        for (int i = 0; i < length; i++)
        {
            for (int ii = 0; ii < length; ii++)
            {
                if (ii < length - i)
                {
                    _Checksum = _Checksum ^ j;
                }
                j++;
            }
        }

        return _Checksum;
    }
}
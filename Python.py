def solution(s): #I had a space between solution and (s) ealier that kept causing me a FILENAME_MISMATCH error
    outputString = ""

    table = [
        "000000", #Space
        "100000", "110000", "100100", "100110", #a b c d
        "100010", "110100", "110110", "110010", #e f g h
        "010100", "010110", "101000", "111000", #i j k l
        "101100", "101110", "101010", "111100", #m n o p
        "111110", "111010", "011100", "011110", #q r s t
        "101001", "111001", "010111", "101101", #u v w x
        "101111", "101011" #y z
    ]

    for i in range(len(s)):
        byte = ord(s[i])
        x = 0
        if byte >= 65 and byte <= 90:
            x = byte - 64
            outputString += "000001"
        elif byte >= 97 and byte <= 122:
            x = byte - 96
        outputString += table[x]
    return outputString

print(solution("The quick brown fox jumps over the lazy dog"))
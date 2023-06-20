import sys


def sum(a, b, c):
    return a + b + c


if __name__ == "__main__":
    a = sys.argv[1]
    b = sys.argv[2]
    c = sys.argv[3]
    s = sum(a, b, c)
    print("finish!!!")
    print(s)

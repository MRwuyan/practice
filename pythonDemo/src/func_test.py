import math


def my_abs(a):
    if not isinstance(a, (int, float)):
        raise TypeError('bad operand type')
    if a > 0:
        return a
    else:
        return -a


# print(my_abs('a'))


def test_pass():
    pass


test_pass()


def move(x, y, step, angle=0):
    nx = x + step * math.cos(angle)
    ny = y - step * math.sin(angle)
    return nx, ny
print(move(100,100,60,math.pi/6))

def power(x,n=2,m=3):
    print('n=',n,'m=',m,'x=',x)
    n=n-1;
    print(range(n))
    for i in range(n):
        print(i)
        x*=x

    return x

# print(power(5,m=-1, n=2))
def add_end(L=None):
    if L is None:
        L = []
    L.append('END')
    return L
L=[1,2,3]
# print(add_end(L))
# print(add_end(L))
def calc(*numbers):
    sum = 0
    for n in numbers:
        sum = sum + n * n
    return sum

nums = [1, 2, 3]
print(calc(*nums))
# print(calc(1,2,3,4,5))

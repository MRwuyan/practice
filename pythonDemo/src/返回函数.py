from functools import reduce

DIGITS = {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}


def str2int(s):
    return reduce(lambda x, y: x * 10 + y, (1, 2, 3))


print('str2int----', str2int(DIGITS))


def lazy_sum(*args):
    def sum():
        return reduce(lambda x, y: x + y, args)
    return sum


my_sum = lazy_sum(*[1, 2, 3])
print("my_sum", my_sum())


def count():
    fs = []
    for i in range(1, 4):
        def f():
            return i * i

        fs.append(f)
    return fs


f1, f2, f3 = count()
print(f1())


def inc():
    x = 0
    def fn():
        # 仅读取x的值:
        return x + 1
    return fn


in1 = inc()
print(in1())
print('-------------------')


def count1():
    def f(j):
        return lambda: j * j;

    fs = []
    for i in range(1, 4):
        fs.append(f(i))  # f(i)立刻被执行，因此i的当前值被传入f()
    return fs


c1, c2, c3 = count1()
print(c1())
print(c2())
print(c3())
print('-----------------')
def inc():
    x = 0
    def fn():
        # 仅读取x的值:
        nonlocal x
        x = x + 1
        return x
    return fn
inc1 = inc()
counterB = inc()
print(inc1())
print(inc1())
if [counterB(), counterB(), counterB(), counterB()] == [1, 2, 3, 4]:
    print('测试通过!')
else:
    print('测试失败!')
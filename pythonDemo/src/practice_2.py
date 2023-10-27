from collections.abc import Iterable

d = {'a': 1, 'b': 2, 'c': 3}
for key in d:
    print(d[key])

for value in d.values():
    print(value)

for ch in 'ABC':
    print(ch)

#判断一个变量是否是可迭代对象
def demo1():
    print(isinstance([1,2,4], Iterable))
demo1()



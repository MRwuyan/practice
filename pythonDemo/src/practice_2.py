from collections.abc import Iterable
import os
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

#列表生成
[x * x for x in range(1,10)]
[x * x for x in range(1,10) if x%2==0]

print([m+n for m  in 'ABC' for n in 'XYZ'])

print([d for d in os.listdir('.')]) #os.listdir可以列出文件和目录
d={'x':'A','y':'B','z':'C'}
for k,v in d.items():
    print(k,'=',v)
L = ['Hello', 'World', 'IBM', 'Apple']

print([x.lower() for x in L])
#可见，在一个列表生成式中，for前面的if ... else是表达式，而for后面的if是过滤条件，不能带else。
print([x if x>2 else - x for x in range(1, 11)])

#如果list中既包含字符串，又包含整数，由于非字符串类型没有lower()方法，所以列表生成式会报错：
L = ['Hello', 'World', 18, 'Apple', None]
print([x.lower() for x in L if isinstance(x,Iterable)])

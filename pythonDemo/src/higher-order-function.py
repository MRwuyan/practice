from functools import reduce
# 编写高阶函数，就是让函数的参数能够接收别的函数。
f=abs
def add(x,y,f):
    return f(x)+f(y)
print(add(-5,6,f))
#map ,reduce
def f(x):
    return x*x
r=map(f,range(1,10))

print(list(r))
print('------------------')
strrrrr=map(str,range(1,10))
print(list(strrrrr))
#reduce(f, [x1, x2, x3, x4]) = f(f(f(x1, x2), x3), x4)
print('------------------')
def add(x,y):
    return x+y


print("reduce(add,range(1,10))==>",reduce(add,range(1,10)))
print('------------------')
def fn(x,y):
    return x+y
print(reduce(fn,['a','b','c','d']))

print('------------------')
def char2num(s):
    return {'0':0,'1':1,'2':2,'3':3,'4':4}[s]

# print(list(map(char2num,'1234')))
print(reduce(fn,map(char2num,'1234')))

print('------------------')
DIGITS = {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}
def str2int(s):
    def fn(x,y):
        return x*10+y
    def char2num(s):
        return DIGITS[s]
    return reduce(fn,map(char2num,s))

print(str2int(DIGITS))
print('------------------')

def char2num(s):
    return DIGITS[s]

def str2int(s):
    return reduce(lambda x,y:x*10+y,(map(char2num,s)))
# 利用map()函数，把用户输入的不规范的英文名字，变为首字母大写，其他小写的规范名字。输入：['adam', 'LISA', 'barT']，输出：['Adam', 'Lisa', 'Bart']：
print('------------------')
def normalize(name):
    return name[0].upper()+name[1:].lower()
L1=['adam', 'LISA', 'barT']
print(list(map(normalize,L1)))
#请编写一个prod()函数，可以接受一个list并利用reduce()求积：

print('------------------')
L=[1,2,3,4,5]
def prod(L):
    return reduce(lambda x,y:x*y,L)
print( reduce(lambda x,y:x*y,L))
# 利用map和reduce编写一个str2float函数，把字符串'123.456'转换成浮点数123.456：
print('------------------')
#和map()类似，filter()也接收一个函数和一个序列。和map()不同的是，filter()把传入的函数依次作用于每个元素，然后根据返回值是True还是False决定保留还是丢弃该元素。

print(list(filter(lambda x:x%2==1,range(1,20))))

print('-------------------------')
str1 = ['1','2',' ']
print("trim",' '.strip())
print(list(filter(lambda x:x.strip()!='',str1)))

# 获取素数
def _odd_iter():
    n=1;
    while True:
        n=n+2
        yield n

def _not_divisible(n):
    return lambda x: x % n >0;

print(_not_divisible([1,2,3,4,5]))

print(list(filter((_not_divisible(2)),[1,2,3,4,5])))
def primes():
    yield 2
    it = _odd_iter()
    while True:
        n = next(it)
        yield n
        it = filter((_not_divisible(n)),it)

for n in primes():
    if n < 1000:
        # print(n)
        pass
    else:
        break
#
print('-------------------------')

a = [1,2,3,44,55,1233321,123321]
def is_palindrome(x):
    x = str(x)
    return x==x[::-1]


print(list(filter(lambda n: str(n)==str(n)[::-1],range(1,1000))))
# sort
print('==================================')
print(sorted(['bob', 'about', 'Zoo', 'Credit'],key=str.lower,reverse=True))
print('------------------------')
L = [('Bob', 75), ('Adam', 92), ('Bart', 66), ('Lisa', 88)]
print(sorted(L,key=lambda x: x[1] ,reverse=True))


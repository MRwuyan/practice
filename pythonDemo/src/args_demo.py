
# *args是可变参数，args接收的是一个tuple；
#
# **kw是关键字参数，kw接收的是一个dict。


# 关键字参数
def person(name, age, **kw):
    print('name:', name, 'age:', age, 'other:', kw)

person("zhangsan",18,city="beijing",job="IT")
extra = {'city': 'Beijing', 'job': 'Engineer'}
# person('Jack', 24, **extra)
# person('Jack', 24, city=extra['city'], job=extra['job'])
def person1(name, age, *, city, job):
    print(name, age, city, job)
#关键字指定city,job,不能再传入其他参数
# person1('Jack', 24, city='Beijing', aa='Engineer')
def person2(name, age, *args, city, job):
    print(name, age, args, city, job)

person2('Jack', 24,1,2,3,4, city='Beijing', job='Engineer')


def person3(name, age, city, job):
    # 缺少 *，city和job被视为位置参数
    pass

def f1(a, b, c=0, *args, **kw):
    print('a =', a, 'b =', b, 'c =', c, 'args =', args, 'kw =', kw)

# f1(1, 2,3,1,2,3,city="beijing",job="IT")
def f2(a, b, c=0, *, d, **kw):
    print('a =', a, 'b =', b, 'c =', c, 'd =', d, 'kw =', kw)

args = (1, 2, 3, 4)
kw = {'d': 99, 'x': '#'}

def f2(a, b, c=0, *, d, **kw):
    print('a =', a, 'b =', b, 'c =', c, 'd =', d, 'kw =', kw)


# f2(1, 2, d=99, ext=None)

def f3(a, b, c):
    print(a, b, c)

# c=(1,2,3)
# print(1,2,c)

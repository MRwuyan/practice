# 要创建一个generator，有很多种方法。第一种方法很简单，只要把一个列表生成式的[]改成()，就创建了一个generator：
g = (x * x for x in range(10))
print(g)

for x in g :
    print(x)

def fib(max):
    n,a,b=0,0,1
    while n<max:
        yield b
        a,b=b,a+b
        n=n+1
    return 'done'
#a, b = b, a + b 相当于：
# t = (b, a + b) # t是一个tuple
# a = t[0]
# b = t[1]
print("---------------------------------")


g=fib(6)
print(next(g))
print(next(g))
for n in fib(6):
    print(n)


print("================================")
g=fib(6)
while True:
    try:
        x = next(g)
        print('g:', x)
    except StopIteration as e:
        print('Generator return value:', e.value)
        break

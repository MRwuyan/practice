print('hello word')
print('The quick brown fox', 'jumps over', 'the lazy dog')
# name = input("请输入名称1\n") #控制台输入
# print('你输入的是',name)

print(1024 * 768)

print(True,False)
print(3 > 2)


print('\\\t\\')

print(r'\\\t\\')
print('''line1
... line2
... line3''')

print(10/3)
print(ord('A'))
print(ord('中'))
x = b'ABC'
print('ABC'.encode('ascii'))
print(len('ABC'))
print( len('中文'))
print(len(b'ABC'))

print(b'\xe4\xb8\xad\xe6\x96\x87'.decode('utf-8'))

# ，%运算符就是用来格式化字符串的。在字符串内部，%s表示用字符串替换，%d表示用整数替换，有几个%?占位符，后面就跟几个变量或者值，顺序要对应好。如果只有一个%?，括号可以省略。
print('Hello, %s' % 'world')
print('Hi, %s, you have $%d.' % ('Michael', 1000000))
print('%0004d-%02d' % (3, 1))
print('%.3f' % 3.1415926)
# 有些时候，字符串里面的%是一个普通字符怎么办？这个时候就需要转义，用%%来表示一个%：
print( 'growth rate: %d %%' % 7)
print('Hello, {0}, 成绩提升了 {1:.1f}%'.format('小明', 17.125))

r = 2.5
s = 3.14 * r ** 2
print( r ** 2)
print(f'The area of a circle with radius {r} is {s:.2f}')
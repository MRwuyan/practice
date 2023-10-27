score = 'B'

# match score:
#     case 'A':
#         print('score is A.')
#     case 'B':
#         print('score is B.')
#     case 'C':
#         print('score is C.')
#     case _: # _表示匹配到其他任何情况
#         print('score is ???.')
names = ['Michael', 'Bob', 'Tracy']
for name in names:
    print(name)
a = list(range(5))
print('==================')
for x in range(5):
    if x == 3:
        continue
    print(x)

d = {'Michael': 95, 'Bob': 75, 'Tracy': 85}

print('a' in d)

s = set([1, 2, 3])

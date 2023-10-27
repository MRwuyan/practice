classmates = ['Michael', 'Bob', 'Tracy']
classmates.append('Adam')
classmates.pop(1)
classmates.insert(5, 'Jack')
print(classmates)

sortList = (1, 2, 3, 4, 5, 6, 7, 8, 9)
print(sortList[0:3])

L = [
    ['Apple', 'Google', 'Microsoft'],
    ['Java', 'Python', 'Ruby', 'PHP'],
    ['Adam', 'Bart', 'Lisa']
]
print(L[0][0])
print(L[1][1])

age = 3
if age > 3:
    print('age>3')
elif age == 3:
    print('age==3')
else:
    print('age<3')

listtest=(1,);
if listtest:
    print('True')

s = input('birth: ')
birth = int(s)

if birth < 2000:
    print('00前')
else:
    print('00后')
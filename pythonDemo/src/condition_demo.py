age = 1
if age >= 18:
    print('your age is', age)
    print('adult')
print("sssss")
list =None;
if list:
    print("list is not empty")
str='  '
print('s',str.strip())
if str:
    print("str is not empty")


import re
args = ['gcc', 'hello.c', 'world.c']
# args = ['clean']
# args = ['gcc']

text = "Hello, my email is example@example.com. Please contact me."
pattern = r'\b\w+@\w+\.\w+\b'  # 正则表达式模式，匹配电子邮件地址

matches = re.findall(pattern, text)
for match in matches:
    print(match)

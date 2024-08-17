iter = iter([1,2,3,4,5])
while True:
    try:
        print(next(iter))
    except StopIteration:
       break;
print("--------------------")
for x in (1,2,3,4,5):
    print(x)
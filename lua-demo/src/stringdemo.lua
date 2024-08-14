local a = "你好"
local b = "worDD"
local c = a .. b
print(a .. b)
local len = string.len(a .. b)
print(len)

local myString = "Hello, 世界!"
print(string.upper(c))
print(string.lower(c))
local g1, g2 = string.gsub(c, 'D', 'x', 2)
print(g1)
print(string.gsub(c, 'D', 'x', 2))
print(string.find("aa bb cc aa", 'aa', 1, true))
local match = string.gmatch("a b c d", " ")
for word in string.gmatch("Hello Lua user", "%a+") do
    print(word)
end
local text = "123 12345 "
local match = string.match(text, "%d+")--匹配数字
print(match) -- 输出 "12345"
local match1,match2 = string.match("I have 2 questions for you.", "(%d+) (%a+)")
print(match1,match2)
string.format("%d, %q", 12	,'questions\d')
--%c - 接受一个数字, 并将其转化为ASCII码表中对应的字符
--%d, %i - 接受一个数字并将其转化为有符号的整数格式
--%o - 接受一个数字并将其转化为八进制数格式
--%u - 接受一个数字并将其转化为无符号整数格式
--%x - 接受一个数字并将其转化为十六进制数格式, 使用小写字母
--%X - 接受一个数字并将其转化为十六进制数格式, 使用大写字母
--%e - 接受一个数字并将其转化为科学记数法格式, 使用小写字母e
--%E - 接受一个数字并将其转化为科学记数法格式, 使用大写字母E
--%f - 接受一个数字并将其转化为浮点数格式
--%g(%G) - 接受一个数字并将其转化为%e(%E, 对应%G)及%f中较短的一种格式
--%q - 接受一个字符串并将其转化为可安全被Lua编译器读入的格式
--%s - 接受一个字符串并按照给定的参数格式化该字符串
--为进一步细化格式, 可以在%号后添加参数. 参数将以如下的顺序读入:
--
--(1) 符号: 一个+号表示其后的数字转义符将让正数显示正号. 默认情况下只有负数显示符号.
--(2) 占位符: 一个0, 在后面指定了字串宽度时占位用. 不填时的默认占位符是空格.
--(3) 对齐标识: 在指定了字串宽度时, 默认为右对齐, 增加-号可以改为左对齐.
--(4) 宽度数值
--(5) 小数位数/字串裁切: 在宽度数值后增加的小数部分n, 若后接f(浮点数转义符, 如%6.3f)则设定该浮点数的小数只保留n位, 若后接s(字符串转义符, 如%5.3s)则设定该字符串只显示前n位.
-- 日期格式化

date = 2; month = 1; year = 2014
print(string.format("日期格式化 %02d/%02d/%05d", date, month, year))
-- 十进制格式化
print(string.format("%.10f",1.11*1.11111111))
--[[
下面的表列出了Lua支持的所有字符类：

单个字符(除 ^$()%.[]*+-? 外): 与该字符自身配对

.(点): 与任何字符配对
%a: 与任何字母配对
%c: 与任何控制符配对(例如\n)
%d: 与任何数字配对
%l: 与任何小写字母配对
%p: 与任何标点(punctuation)配对
%s: 与空白字符配对
%u: 与任何大写字母配对
%w: 与任何字母/数字配对
%x: 与任何十六进制数配对
%z: 与任何代表0的字符配对
%x(此处x是非字母非数字字符): 与字符x配对. 主要用来处理表达式中有功能的字符(^$()%.[]*+-?)的配对问题, 例如%%与%配对
[数个字符类]: 与任何[]中包含的字符类配对. 例如[%w_]与任何字母/数字, 或下划线符号(_)配对
[^数个字符类]: 与任何不包含在[]中的字符类配对. 例如[^%s]与任何非空白字符配对
]]--

print(string.gsub("hello, up-down!", "%A", "."))
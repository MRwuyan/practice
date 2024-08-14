---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by cxp.
--- DateTime: 2023-09-10 14:57
---__add    对应的运算符 '+'.
--__sub	对应的运算符 '-'.
--__mul	对应的运算符 '*'.
--__div	对应的运算符 '/'.
--__mod	对应的运算符 '%'.
--__unm	对应的运算符 '-'.
--__concat	对应的运算符 '..'.
--__eq	对应的运算符 '=='.
--__lt	对应的运算符 '<'.
--__le	对应的运算符 '<='.
mytable1 = { 1, 2 }                          -- 普通表
mytable2 = { 3,3}                      -- 元表
mymetatable = {}
mymetatable.__add = function(t1, t2)
    return {t1+t2}
end
setmetatable(mytable1, mymetatable)     -- 把 mymetatable 设为 mytable 的元表
setmetatable(mytable2, mymetatable)     -- 把 mymetatable 设为 mytable 的元表
t3 = mytable1 + mytable2
for k, v in pairs(t3) do
    print(v)
end
---
--- Generated by Luanalysis
--- Created by cxp.
--- DateTime: 2023-09-10 13:54
---

-- 文件名为 module.lua
-- 定义一个名为 module 的模块
module = {}

function module.fun1()
    print('公有函数')
end

local function fun2()
    print('私有函数')
end
return module
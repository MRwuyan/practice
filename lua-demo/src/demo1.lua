---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by 14758.
--- DateTime: 2023/9/6 15:18
---
---
function main()
    print("sss")
    print(7.8263692594256e-06)
    a = [[sss]]
    print(a)
    print("a" .. "b")
    tbl2 = { "apple", "pear", "orange", "grape" }
    print(#tbl2)
end
function test1()
    a = {}
    a['1a'] = "a"
    a['bb'] = "b"
    for k, v in pairs(a) do
        print(k .. '=' .. v)

    end
    tbl2 = { "apple", "pear", "orange", "grape" }
    for k, v in pairs(tbl2) do
        print(k .. '=' .. v)
    end
    a3 = {}
    a3["key"]=''
    print(a3["key"])
    print(a3["none"])
    print(#a3)
end
function testFun(a, func)
    for k, v in pairs(a) do
        print(func(k, v))
    end
end

function test2()
    tab = { 1, 2, 3 }
    testFun(tab,
            function(k, v)
                return k..v;
            end
    );

end

test2()
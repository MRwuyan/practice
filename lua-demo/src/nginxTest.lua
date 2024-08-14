--local getArg=ngx.req.get_uri_args()
--for k, v in pairs(getArg) do
--    ngx.say('get key:',k,'v:',v)
--    ngx.say('<br>')
--end

--获取post请求参数
--[[ngx.req.read_body()--解析body参数之前一定要先读取body
local postArg = ngx.req.get_post_args()
for k, v in pairs(postArg) do
    ngx.say('post key:',k,'v:',v)
    ngx.say('<br>')
end]]
ngx.req.read_body()
local data = ngx.req.get_body_data()
ngx.say(data)


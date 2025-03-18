*2025-3-14*  
**熟悉登录流程**  

- 请求数据传递的流程：EmployeeController.login(接收请求信息，传递到登录验证方法) ->employeeService.login(根据信息进行查找、验证，错误则抛出异常，正确则返回对应用户信息)  
- 响应数据生成的流程：根据返回的类生成JWT令牌，其中jwtProperties由application.yml中sky.jwt配置，生成令牌后build employeeLoginVO并返回。

**nginx反向代理**

前端->nginx服务器->Tomcat
好处：

- 提高访问速度(nginx缓存)
- 负载均衡，可以访问多台服务器
- 后端服务安全

配置：

- server.location /请求路径{proxy_pass  转发路径;}  *请求路径后的路径也会拼接在转发路径后*
- 负载均衡：
	upstream webservers{server地址}
	server.location /请求路径{proxy_pass  *http://webservers/转发路径;*}

**完善登录功能**

将密码进行MD5加密：

- 数据库中密码改成密文

- EmployeeService.login中将password进行MD5加密


----

*2025-3-15*

**新增员工模块开发**

- 接收JSON数据，通过*@RequestBody*转化为DTO对象，在employeeService.save中转化为Employee对象并填充剩余属性，调用employeeMapper.insert将数据插入数据库。

- 补充用户名重复时的异常捕获方法

- **获取当前操作人的ID**
	- 每次请求都会被分配一个单独的线程，每个线程有单独的存储空间
	- 通过已经被封装好的类sky-common/context/BaseContext进行线程中ID属性的设置
	- 在JWT拦截器中setCurrentId，在需要时调用

----

*2025-3-16*

**员工分页查询模块开发**

mysql中：

```mysql
<if test="name != null and name != ''"> 
#检查 name 不是 null 且不等于空字符串 ""。
<if test="name != null and name != ' '"> 
#检查 name 不是 null 且不等于单个空格字符 " "。
```

设置消息转换器

```java
 @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器");
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        //为消息转换器设置对象转换器，将java对象转换为json数据
        converter.setObjectMapper(new JacksonObjectMapper());
        //将教习转换器加入容器中
        converters.add(0,converter);
    }
```

*MappingJackson2HttpMessageConverter不要导成MappingJackson2**Cobr**HttpMessageConverter*
后者用于处理Cobr格式的JSON数据

**启用/禁用员工账号**

EZ,don’t have anything to say.
update的xml：

```mysql
    <update id="update">
        update employee
        <set>
            <if test="name != null">name = #{name}</if>
            <if test="username != null">username = #{username}</if>
            <if test="password != null">password = #{password}</if>
            <if test="phone != null">phone = #{phone}</if>
            <if test="sex != null">sex = #{sex}</if>
            <if test="idNumber != null">id_Number = #{idNumber}</if>
            <if test="updateTime != null">update_Time = #{updateTime}</if>
            <if test="updateUser != null">update_User = #{updateUser}</if>
            <if test="status != null">status = #{status}</if>
        </set>
        where id = #{id}
    </update>
```

**编辑员工信息**

- 员工信息回显 EZ
- 修改员工信息 EZ2 json->实体类employeeDTO->employee

**导入分类管理功能**

只是导入

----

*2025-3-17*

**公共字段自动填充**

----

*2025-3-18*

无事发生，傻逼晚课




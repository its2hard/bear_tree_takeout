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

----

*2025-3-19*

**新增菜品**

文件上传模块：阿里云

----

*2025-3-20*

**新增菜品**

其他部分

```mysql
 <insert id="insert" useGeneratedKeys="true" keyProperty="id">
```

*useGeneratedKeys* 用来表示 *允许JDBC支持自动生成主键*，好在之后提取

```java
		//向菜品表插入一条数据
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.insert(dish);
        Long dishId = dish.getId();
        //向口味表插入N条数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null&& !flavors.isEmpty()){
            flavors.forEach(df->{
                df.setDishId(dishId);
            });
            dishFlavorMapper.insertbatch(flavors);
        }
```

----

*2025-3-21*

**菜品分页查询**

```mysql
select d.*,c.name as categoryName from dish d left outer join category c on d.categoryId = c.id
```

ps:通过类名 `DishMapper` 调用一个非静态方法 `pageQuary`。在 Java 中，非静态方法必须通过对象实例来调用，而不能通过类名直接调用。需要通过声明DishMapper对象dishMapper调用

**删除菜品**

---

*2025-3-22*

**修改菜品**

修改菜品基本信息、删除口味信息、插入新的口味信息

**添加套餐**

- 根据类型查询菜品
- 添加

----

*2025-3-23*

**删除套餐**

当方法参数名和请求参数名不一致时必须用：@RequestParam

**更新套餐**

----

*2025-3-24*

**设置套餐和菜品的状态**

----

*2025-3-25*

**设置店铺营业状态**

redis

----

*2025-3-27*

**微信小程序**

----

*2025-3-28*

**微信登录**

@Service注解用于类上，标记当前类是一个service类，加上该注解会将当前类自动注入到spring容器中，不需要再在applicationContext.xml文件定义bean了。


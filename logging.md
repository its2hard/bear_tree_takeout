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

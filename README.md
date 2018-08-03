testjs
=======
### mesh分类 ###
普通mesh：个体  
模板mesh：提供复制原型  
复制mesh：安装模板mesh进行复制

### 待办事项 ###
springmvc 加入template功能  
~~增加鼠标移动捕捉Mesh(是)~~  
~~增加右键隐藏其余网卡(是)~~  
~~增加左键单击显示网卡信息(是)~~  
~~支持利用率查看(是)~~  
支持数据库查询(是)  
~~支持新增设备建议(是)~~  
~~支持网线选中(是)~~   
~~连续点击同一网线，仅第一次显示提示窗~~  
~~点击设备，显示设备信息~~  
~~机柜左右上下重新建块，为了方便各自贴图，尤其是内壁贴U格数~~  
~~每台设备显示设备名称标签~~  
模拟一些东环数据(否)  
增加电缆布线功能 

### 设备上架 ###
弹出对话框（可手动关闭)  
对话框包括
*   所属区域
*   所属系统
*   所占U数
*   额定容量
候选位置

### js鼠标事件 ###

### Map Usage ###
set(key, val)  
get(key)  
has(key)  
size()  

### Validator Rule ### 
```
notEmpty: {//检测非空,radio也可用
    message: '文本框必须输入'
},
greaterThan: {
    value: 18
},
lessThan: {
    value: 100
},
uri: {},
stringLength: {//检测长度
    min: 6,
    max: 30,
    message: '长度必须在6-30之间'
},
regexp: {//正则验证
    regexp: /^[a-zA-Z0-9_\.]+$/,
    message: '所输入的字符不符要求'
},
remote: {//将内容发送至指定页面验证，返回验证结果，比如查询用户名是否存在
    url: '指定页面',
    message: 'The username is not available'
},
different: {//与指定文本框比较内容相同
    field: '指定文本框name',
    message: '不能与指定文本框内容相同'
},
emailAddress: {//验证email地址
    message: '不是正确的email地址'
},
identical: {//与指定控件内容比较是否相同，比如两次密码不一致
    field: 'confirmPassword',//指定控件name
    message: '输入的内容不一致'
},
date: {//验证指定的日期格式
    format: 'YYYY/MM/DD',
    message: '日期格式不正确'
},
digits: {
 message: '该值只能包含数字。'
}
choice: {//check控件选择的数量
    min: 2,
    max: 4,
    message: '必须选择2-4个选项'
}
stringCase: {
  message: '姓氏必须只包含大写字符。',
  case: 'upper'//其他值或不填表示只能小写字符
},
threshold :  6 , //有6字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
    url: 'exist2.do',//验证地址
    message: '用户已存在',//提示消息
    delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
    type: 'POST'//请求方式
},
```
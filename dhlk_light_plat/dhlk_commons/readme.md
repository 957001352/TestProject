# 基本的公共代码

### 1. 自定义注解
* 管理员账号
* Email
* 用户账号
* 手机号
* 为空和数字
* 密码
* 网址
* @Null	限制只能为null
* @NotNull	限制必须不为null
* @AssertFalse	限制必须为false
* @AssertTrue	限制必须为true
* @DecimalMax(value)	限制必须为一个不大于指定值的数字
* @DecimalMin(value)	限制必须为一个不小于指定值的数字
* @Digits(integer,fraction)	限制必须为一个小数，且整数部分的位数不能超过integer，小数部分的位数不能超过fraction
* @Future	限制必须是一个将来的日期
* @Max(value)	限制必须为一个不大于指定值的数字
* @Min(value)	限制必须为一个不小于指定值的数字
* @Past	限制必须是一个过去的日期
* @Pattern(value)	限制必须符合指定的正则表达式
* @Size(max,min)	限制字符长度必须在min到max之间
* @Past	验证注解的元素值（日期类型）比当前时间早
* @NotEmpty	验证注解的元素值不为null且不为空（字符串长度不为0、集合大小不为0）
* @NotBlank	验证注解的元素值不为空（不为null、去除首位空格后长度为0），不同于@NotEmpty，@NotBlank只应用于字符串且在比较时会去除字符串的空格
* @Email	验证注解的元素值是Email，也可以通过正则表达式和flag指定自定义的email格式


### domain
* JsonResut 包含统一异常处理JSON格式结果返回
* Result<T>
* UploadFileResult

### enums
* SystemEnums 平台的统一的枚举自定义

### exceptions
* MyException  自定义异常
* ResulutException 基于自定义枚举的异常

### impls 
* IEmail 邮件发送接口
* IEmailCallBack 邮件CallBack
* ISMS 短信发送接口
* ISMSCallBack 短信CallBack
* SendEmail 类
* SMS 短信类

### systemconst
* Const 平台统一的常量定义

### utils
* Base64Utils
* CheckUtils 所有与判断是否相关的工具类，例如是否为空
* Convert 所有与转换格式相关的工具类，例如将String转换成Integer
* EncryUtils MD5加密
* PinyinUtils 汉字转拼音工具类
* RMBUtils 人民币大小写转换类
* StringUtils 与String有关的工具类

   



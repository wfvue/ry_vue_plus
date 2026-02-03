# GenTable / GenTableColumn 数据结构

## GenTable 代码生成主表

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| tableId | Long | 是 | 表ID，主键 |
| dataName | String | 是 | 数据源名称 |
| tableName | String | 是 | 表名称 |
| tableComment | String | 是 | 表描述/注释 |
| subTableName | String | 否 | 关联子表名 |
| subTableFkName | String | 否 | 关联子表外键 |
| className | String | 是 | 实体类名称（首字母大写） |
| tplCategory | String | 否 | 模板类型：crud(单表)/tree(树表)/sub(主子表) |
| packageName | String | 是 | 生成包路径 |
| moduleName | String | 是 | 生成模块名 |
| businessName | String | 是 | 生成业务名 |
| functionName | String | 是 | 生成功能名 |
| functionAuthor | String | 是 | 生成作者 |
| genType | String | 否 | 生成方式：0-zip压缩包 1-自定义路径 |
| genPath | String | 否 | 生成路径 |
| options | String | 否 | 其他生成选项(JSON格式) |
| remark | String | 否 | 备注 |

### 扩展字段（非数据库字段）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| pkColumn | GenTableColumn | 主键列信息 |
| columns | List<GenTableColumn> | 表列信息列表 |
| treeCode | String | 树编码字段 |
| treeParentCode | String | 树父编码字段 |
| treeName | String | 树名称字段 |
| menuIds | List<Long> | 菜单ID列表 |
| parentMenuId | Long | 上级菜单ID |
| parentMenuName | String | 上级菜单名称 |

### Options JSON 结构

```json
{
  "parentMenuId": 3,
  "parentMenuName": "系统管理",
  "treeCode": "dept_id",
  "treeParentCode": "parent_id",
  "treeName": "dept_name"
}
```

## GenTableColumn 代码生成列

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| columnId | Long | 是 | 列ID，主键 |
| tableId | Long | 是 | 归属表ID |
| columnName | String | 是 | 列名称 |
| columnComment | String | 是 | 列描述 |
| columnType | String | 是 | 列类型（数据库原始类型） |
| javaType | String | 是 | JAVA类型：String/Integer/Long/Date/BigDecimal/Double |
| javaField | String | 是 | JAVA字段名 |
| isPk | String | 否 | 是否主键：1-是 0-否 |
| isIncrement | String | 否 | 是否自增：1-是 0-否 |
| isRequired | String | 否 | 是否必填：1-是 0-否 |
| isInsert | String | 否 | 是否为插入字段：1-是 0-否 |
| isEdit | String | 否 | 是否编辑字段：1-是 0-否 |
| isList | String | 否 | 是否列表字段：1-是 0-否 |
| isQuery | String | 否 | 是否查询字段：1-是 0-否 |
| queryType | String | 否 | 查询方式：EQ/NE/GT/LT/LIKE/LEFT_LIKE/RIGHT_LIKE/BETWEEN |
| htmlType | String | 否 | 显示类型：input/textarea/select/checkbox/radio/datetime/imageUpload/fileUpload/editor |
| dictType | String | 否 | 字典类型 |
| sort | Integer | 否 | 排序 |

### 列类型映射

| 数据库类型 | JAVA类型 | 说明 |
|-----------|----------|------|
| varchar, char, text, json | String | 字符串 |
| int, integer | Integer | 整数 |
| bigint | Long | 长整数 |
| tinyint | Integer | 小整数 |
| decimal, numeric | BigDecimal | 精确小数 |
| double, float | Double | 浮点数 |
| datetime, timestamp, date | Date | 日期时间 |
| blob, binary | byte[] | 二进制 |

### 显示类型映射建议

| 字段后缀/特征 | htmlType | 说明 |
|--------------|----------|------|
| _status, _type, _sex, _level | select | 下拉选择 |
| _remark, _content, _desc | textarea | 文本域 |
| _time, _date | datetime | 日期时间选择器 |
| _img, _image, _avatar | imageUpload | 图片上传 |
| _file, _attachment | fileUpload | 文件上传 |
| _flag, _is_ | checkbox/radio | 是否标志 |

## 模板常量 GenConstants

```java
public class GenConstants {
    // 模板类型
    public static final String TPL_CRUD = "crud";      // 单表
    public static final String TPL_TREE = "tree";      // 树表
    public static final String TPL_SUB = "sub";        // 主子表
    
    // 基类字段（父类已有，子类不需要生成）
    public static final String[] BASE_ENTITY = {
        "createBy", "createTime", "updateBy", "updateTime", "remark"
    };
    
    // 树表选项字段
    public static final String TREE_CODE = "treeCode";           // 树编码
    public static final String TREE_PARENT_CODE = "treeParentCode"; // 树父编码
    public static final String TREE_NAME = "treeName";           // 树名称
    public static final String PARENT_MENU_ID = "parentMenuId";  // 上级菜单ID
    public static final String PARENT_MENU_NAME = "parentMenuName"; // 上级菜单名称
    
    // JAVA类型
    public static final String TYPE_STRING = "String";
    public static final String TYPE_INTEGER = "Integer";
    public static final String TYPE_LONG = "Long";
    public static final String TYPE_DATE = "Date";
    public static final String TYPE_BIGDECIMAL = "BigDecimal";
    public static final String TYPE_DOUBLE = "Double";
    
    // HTML类型
    public static final String HTML_INPUT = "input";
    public static final String HTML_TEXTAREA = "textarea";
    public static final String HTML_SELECT = "select";
    public static final String HTML_RADIO = "radio";
    public static final String HTML_CHECKBOX = "checkbox";
    public static final String HTML_DATETIME = "datetime";
    public static final String HTML_IMAGE_UPLOAD = "imageUpload";
    public static final String HTML_FILE_UPLOAD = "fileUpload";
    public static final String HTML_EDITOR = "editor";
    
    // 查询类型
    public static final String QUERY_EQ = "EQ";           // 等于
    public static final String QUERY_NE = "NE";           // 不等于
    public static final String QUERY_GT = "GT";           // 大于
    public static final String QUERY_LT = "LT";           // 小于
    public static final String QUERY_LIKE = "LIKE";       // 模糊查询
    public static final String QUERY_LEFT_LIKE = "LEFT_LIKE";   // 左模糊
    public static final String QUERY_RIGHT_LIKE = "RIGHT_LIKE"; // 右模糊
    public static final String QUERY_BETWEEN = "BETWEEN"; // 范围查询
}
```

## 文件生成路径规则

### Java 后端

| 模板 | 生成路径 |
|------|----------|
| domain.java.vm | `main/java/{package}/domain/{ClassName}.java` |
| vo.java.vm | `main/java/{package}/domain/vo/{ClassName}Vo.java` |
| bo.java.vm | `main/java/{package}/domain/bo/{ClassName}Bo.java` |
| mapper.java.vm | `main/java/{package}/mapper/{ClassName}Mapper.java` |
| service.java.vm | `main/java/{package}/service/I{ClassName}Service.java` |
| serviceImpl.java.vm | `main/java/{package}/service/impl/{ClassName}ServiceImpl.java` |
| controller.java.vm | `main/java/{package}/controller/{ClassName}Controller.java` |
| mapper.xml.vm | `main/resources/mapper/{module}/{ClassName}Mapper.xml` |

### 前端

| 模板 | 生成路径 |
|------|----------|
| api.ts.vm | `vue/api/{module}/{business}/index.ts` |
| types.ts.vm | `vue/api/{module}/{business}/types.ts` |
| index.vue.vm | `vue/views/{module}/{business}/index.vue` |
| index-tree.vue.vm | `vue/views/{module}/{business}/index.vue` (树表) |

### SQL

| 模板 | 生成路径 |
|------|----------|
| sql.vm | `{business}Menu.sql` |

## Velocity 上下文变量

```java
VelocityContext context = new VelocityContext();
context.put("tplCategory", genTable.getTplCategory());      // 模板类型
context.put("tableName", genTable.getTableName());          // 表名
context.put("functionName", genTable.getFunctionName());    // 功能名
context.put("ClassName", genTable.getClassName());          // 类名（大写）
context.put("className", StringUtils.uncapitalize(genTable.getClassName())); // 类名（小写）
context.put("moduleName", genTable.getModuleName());        // 模块名
context.put("BusinessName", StringUtils.capitalize(genTable.getBusinessName())); // 业务名（大写）
context.put("businessName", genTable.getBusinessName());    // 业务名
context.put("basePackage", getPackagePrefix(packageName));  // 基础包名
context.put("packageName", packageName);                    // 包名
context.put("author", genTable.getFunctionAuthor());        // 作者
context.put("datetime", DateUtils.getDate());               // 日期
context.put("pkColumn", genTable.getPkColumn());            // 主键列
context.put("importList", getImportList(genTable));         // 导入包列表
context.put("permissionPrefix", getPermissionPrefix(moduleName, businessName)); // 权限前缀
context.put("columns", genTable.getColumns());              // 列列表
context.put("table", genTable);                             // 表对象
context.put("dicts", getDicts(genTable));                   // 字典组
context.put("parentMenuId", parentMenuId);                  // 上级菜单ID
// 树表特有
context.put("treeCode", treeCode);                          // 树编码
context.put("treeParentCode", treeParentCode);              // 树父编码
context.put("treeName", treeName);                          // 树名称
context.put("expandColumn", expandColumn);                  // 展开列序号
```

## 智能分析示例

### 表结构示例

```java
GenTable table = new GenTable();
table.setTableName("sys_order");
table.setTableComment("订单信息表");
table.setClassName("Order");
table.setModuleName("order");
table.setBusinessName("order");
table.setFunctionName("订单管理");
table.setTplCategory("crud");
table.setPackageName("org.dromara.order");

// 列信息
List<GenTableColumn> columns = Arrays.asList(
    // 主键
    createColumn("order_id", "订单ID", "bigint", "Long", true, true),
    // 业务字段
    createColumn("order_no", "订单编号", "varchar(64)", "String", false, false),
    createColumn("customer_id", "客户ID", "bigint", "Long", false, false),
    createColumn("total_amount", "订单总金额", "decimal(18,2)", "BigDecimal", false, false),
    createColumn("status", "订单状态", "char(1)", "String", false, false),
    createColumn("order_time", "下单时间", "datetime", "Date", false, false),
    createColumn("remark", "备注", "varchar(500)", "String", false, false)
);
table.setColumns(columns);
```

### AI 分析输出

```
表名分析: sys_order → 系统订单表
业务类型: 电商订单管理
关键字段: 
  - order_no: 订单编号（唯一标识）
  - customer_id: 关联客户（外键关系）
  - total_amount: 金额计算（BigDecimal）
  - status: 状态流转（字典值）
  - order_time: 时间查询（范围查询）

建议生成:
  - Controller: 包含导出功能
  - Service: 订单状态流转校验
  - VO: 关联客户名称显示
  - 前端: 状态筛选、时间范围查询
```

# RuoYi-Vue-Plus 架构指南

## 项目结构

```
ruoyi-modules/
├── ruoyi-generator/          # 代码生成模块
│   ├── controller/GenController.java
│   ├── domain/
│   │   ├── GenTable.java     # 代码生成主表
│   │   └── GenTableColumn.java # 代码生成列
│   ├── service/
│   │   ├── IGenTableService.java
│   │   └── GenTableServiceImpl.java  # 核心生成逻辑
│   └── util/
│       ├── VelocityUtils.java        # 模板工具
│       └── GenUtils.java             # 通用工具
```

## 命名规范

### Java 后端

| 元素 | 命名规则 | 示例 |
|------|----------|------|
| 类名 | 大驼峰 | `UserController`, `UserServiceImpl` |
| 接口 | 大驼峰，I 前缀 | `IUserService` |
| 方法 | 小驼峰 | `selectUserList()`, `insertUser()` |
| 变量 | 小驼峰 | `userName`, `userList` |
| 常量 | 大写下划线 | `MAX_RETRY_COUNT` |
| 包名 | 小写 | `org.dromara.system` |

### Vue 前端

| 元素 | 命名规则 | 示例 |
|------|----------|------|
| 组件 | 大驼峰 | `UserTable.vue` |
| 文件 | 小写横线 | `user-management/index.vue` |
| 方法 | 小驼峰 | `handleQuery()`, `handleAdd()` |
| 变量 | 小驼峰 | `userList`, `loading` |

## 代码规范

### Controller 规范

```java
@RestController
@RequestMapping("/system/user")
@Validated
public class UserController extends BaseController {
    
    private final IUserService userService;
    
    /**
     * 查询用户列表
     */
    @SaCheckPermission("system:user:list")
    @GetMapping("/list")
    public TableDataInfo<UserVo> list(UserBo bo, PageQuery pageQuery) {
        return userService.selectPageUserList(bo, pageQuery);
    }
    
    /**
     * 导出用户列表
     */
    @SaCheckPermission("system:user:export")
    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(UserBo bo, HttpServletResponse response) {
        List<UserVo> list = userService.selectUserList(bo);
        ExcelUtil.exportExcel(list, "用户数据", UserVo.class, response);
    }
    
    /**
     * 获取用户详细信息
     */
    @SaCheckPermission("system:user:query")
    @GetMapping(value = "/{userId}")
    public R<UserVo> getInfo(@PathVariable Long userId) {
        return R.ok(userService.selectUserById(userId));
    }
    
    /**
     * 新增用户
     */
    @SaCheckPermission("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@Validated @RequestBody UserBo bo) {
        userService.insertUser(bo);
        return R.ok();
    }
    
    /**
     * 修改用户
     */
    @SaCheckPermission("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@Validated @RequestBody UserBo bo) {
        userService.updateUser(bo);
        return R.ok();
    }
    
    /**
     * 删除用户
     */
    @SaCheckPermission("system:user:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@PathVariable Long[] userIds) {
        userService.deleteUserByIds(userIds);
        return R.ok();
    }
}
```

### Service 规范

```java
public interface IUserService {
    
    TableDataInfo<UserVo> selectPageUserList(UserBo bo, PageQuery pageQuery);
    
    List<UserVo> selectUserList(UserBo bo);
    
    UserVo selectUserById(Long userId);
    
    void insertUser(UserBo bo);
    
    void updateUser(UserBo bo);
    
    void deleteUserByIds(Long[] userIds);
}

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    
    private final UserMapper userMapper;
    
    @Override
    public TableDataInfo<UserVo> selectPageUserList(UserBo bo, PageQuery pageQuery) {
        return userMapper.selectPageUserList(bo, pageQuery);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUser(UserBo bo) {
        // 业务校验
        if (userMapper.checkUserNameUnique(bo.getUserName())) {
            throw new ServiceException("新增用户" + bo.getUserName() + "失败，登录账号已存在");
        }
        // 业务逻辑
        User user = BeanUtil.toBean(bo, User.class);
        userMapper.insert(user);
    }
}
```

### Domain/Entity 规范

```java
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class User extends BaseEntity {
    
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;
    
    @Schema(description = "用户账号")
    private String userName;
    
    @Schema(description = "用户昵称")
    private String nickName;
    
    @Schema(description = "用户邮箱")
    private String email;
    
    @Schema(description = "手机号码")
    private String phonenumber;
    
    @Schema(description = "用户性别")
    private String sex;
    
    @Schema(description = "头像地址")
    private String avatar;
    
    @Schema(description = "密码")
    private String password;
    
    @Schema(description = "帐号状态")
    private String status;
    
    @Schema(description = "删除标志")
    @TableLogic
    private String delFlag;
}
```

### VO/BO 规范

```java
@Data
@ExcelIgnoreUnannotated
public class UserVo implements Serializable {
    
    @Schema(description = "用户ID")
    @ExcelProperty(value = "用户序号")
    private Long userId;
    
    @Schema(description = "用户账号")
    @ExcelProperty(value = "登录名称")
    private String userName;
    
    @Schema(description = "用户昵称")
    @ExcelProperty(value = "用户名称")
    private String nickName;
    
    @Schema(description = "用户邮箱")
    @ExcelProperty(value = "用户邮箱")
    private String email;
    
    @Schema(description = "手机号码")
    @ExcelProperty(value = "手机号码")
    private String phonenumber;
    
    @Schema(description = "用户性别")
    @ExcelProperty(value = "用户性别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_sex")
    private String sex;
    
    @Schema(description = "头像地址")
    private String avatar;
    
    @Schema(description = "帐号状态")
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    private String status;
    
    @Schema(description = "创建时间")
    @ExcelProperty(value = "创建时间")
    private Date createTime;
}

@Data
public class UserBo implements Serializable {
    
    @Schema(description = "用户ID")
    private Long userId;
    
    @Schema(description = "用户账号", required = true)
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    private String userName;
    
    @Schema(description = "用户昵称", required = true)
    @NotBlank(message = "用户昵称不能为空")
    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickName;
    
    @Schema(description = "用户邮箱")
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    private String email;
    
    @Schema(description = "手机号码")
    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    private String phonenumber;
    
    @Schema(description = "用户性别")
    private String sex;
    
    @Schema(description = "头像地址")
    private String avatar;
    
    @Schema(description = "密码")
    private String password;
    
    @Schema(description = "帐号状态")
    private String status;
    
    @Schema(description = "备注")
    private String remark;
}
```

## 权限前缀规范

格式：`模块名:业务名:操作`

| 操作 | 权限标识 | 说明 |
|------|----------|------|
| 列表查询 | `module:business:list` | 查询列表（分页） |
| 单条查询 | `module:business:query` | 查询详情 |
| 新增 | `module:business:add` | 新增数据 |
| 修改 | `module:business:edit` | 修改数据 |
| 删除 | `module:business:remove` | 删除数据 |
| 导出 | `module:business:export` | 导出Excel |

示例：`system:user:add`, `system:role:edit`

## API 返回规范

```java
// 成功返回
R.ok()                           // 无数据
R.ok(data)                       // 有数据
R.ok(msg, data)                  // 消息+数据

// 失败返回
R.fail(msg)                      // 错误消息
R.fail(code, msg)                // 状态码+消息
```

## Vue3 前端规范

### API 模块

```typescript
// api/system/user/types.ts
export interface UserVO {
  userId: number;
  userName: string;
  nickName: string;
  email: string;
  phonenumber: string;
  sex: string;
  avatar: string;
  status: string;
  createTime: string;
}

export interface UserForm {
  userId?: number;
  userName: string;
  nickName: string;
  email: string;
  phonenumber: string;
  sex: string;
  avatar: string;
  password?: string;
  status: string;
  remark?: string;
}

export interface UserQuery extends PageQuery {
  userName?: string;
  nickName?: string;
  phonenumber?: string;
  status?: string;
}

// api/system/user/index.ts
import request from '@/utils/request';
import { UserVO, UserForm, UserQuery } from './types';

// 查询用户列表
export const listUser = (query?: UserQuery) => {
  return request({
    url: '/system/user/list',
    method: 'get',
    params: query
  });
};

// 查询用户详细
export const getUser = (userId: number | string) => {
  return request({
    url: '/system/user/' + userId,
    method: 'get'
  });
};

// 新增用户
export const addUser = (data: UserForm) => {
  return request({
    url: '/system/user',
    method: 'post',
    data
  });
};

// 修改用户
export const updateUser = (data: UserForm) => {
  return request({
    url: '/system/user',
    method: 'put',
    data
  });
};

// 删除用户
export const delUser = (userId: number | string | Array<number | string>) => {
  return request({
    url: '/system/user/' + userId,
    method: 'delete'
  });
};
```

### Vue 页面规范

```vue
<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="用户名称" prop="userName">
        <el-input v-model="queryParams.userName" placeholder="请输入用户名称" clearable />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="用户状态" clearable>
          <el-option label="正常" value="0" />
          <el-option label="停用" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['system:user:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate" v-hasPermi="['system:user:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['system:user:remove']">删除</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户编号" align="center" prop="userId" />
      <el-table-column label="用户名称" align="center" prop="userName" />
      <el-table-column label="用户昵称" align="center" prop="nickName" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="sys_normal_disable" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:user:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:user:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="userRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名称" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户名称" />
        </el-form-item>
        <el-form-item label="用户昵称" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入用户昵称" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="User">
import { listUser, getUser, delUser, addUser, updateUser } from '@/api/system/user';

const { proxy } = getCurrentInstance();
const { sys_normal_disable, sys_user_sex } = proxy.useDict('sys_normal_disable', 'sys_user_sex');

const userList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref('');

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    userName: undefined,
    status: undefined
  },
  rules: {
    userName: [{ required: true, message: '用户名称不能为空', trigger: 'blur' }],
    nickName: [{ required: true, message: '用户昵称不能为空', trigger: 'blur' }]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询用户列表 */
function getList() {
  loading.value = true;
  listUser(queryParams.value).then(response => {
    userList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** 取消按钮 */
function cancel() {
  open.value = false;
  reset();
}

/** 表单重置 */
function reset() {
  form.value = {
    userId: undefined,
    userName: undefined,
    nickName: undefined,
    email: undefined,
    status: '0',
    remark: undefined
  };
  proxy.resetForm('userRef');
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm('queryRef');
  handleQuery();
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.userId);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = '添加用户';
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const userId = row.userId || ids.value;
  getUser(userId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = '修改用户';
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs.userRef.validate(valid => {
    if (valid) {
      if (form.value.userId !== undefined) {
        updateUser(form.value).then(() => {
          proxy.$modal.msgSuccess('修改成功');
          open.value = false;
          getList();
        });
      } else {
        addUser(form.value).then(() => {
          proxy.$modal.msgSuccess('新增成功');
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const userIds = row.userId || ids.value;
  proxy.$modal.confirm('是否确认删除用户编号为"' + userIds + '"的数据项？').then(() => {
    delUser(userIds).then(() => {
      getList();
      proxy.$modal.msgSuccess('删除成功');
    });
  });
}

getList();
</script>
```

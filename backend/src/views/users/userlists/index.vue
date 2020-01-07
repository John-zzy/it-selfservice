<template>
  <div class="app-container">

    <!-- 新增用户Dialog -->
    <el-dialog
      title="创建用户"
      :visible.sync="AddUserdialogVisible"
      width="40%"
      @close="closeAddUserDialog">
      <!-- 添加用户表单 -->
      <el-form :model="addUserForm" :rules="addUserFormRoles" ref="addUserFormRef" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="addUserForm.username"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="addUserForm.nickName"></el-input>
        </el-form-item>
        <el-form-item label="邮箱地址" prop="email">
          <el-input v-model="addUserForm.email"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="addUserForm.password"></el-input>
        </el-form-item>
        <!-- 密码强度 -->
        <div class="input_span">
          <label style="margin-left:80px;"></label>
          <span id="one"></span>
          <span id="two"></span>
          <span id="three"></span>
        </div>
        <div id="font" style="margin-left: 30px">
          <span>弱</span>
          <span>中</span>
          <span>强</span>
        </div>
        <el-form-item label="确认密码" prop="rePassword">
          <el-input type="password" v-model="addUserForm.rePassword"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="note">
          <el-input v-model="addUserForm.note"></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="AddUserdialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="createUser">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑用户的对话框 -->
    <el-dialog
      title="编辑用户"
      :visible.sync="editUserVisible"
      width="40%">
      <el-form :model="editUserForm" :rules="editUserFormRules" ref="editUserFormRef" label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="editUserForm.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="editUserForm.nickName"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editUserForm.email"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="editUserForm.note"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editUserVisible = false">取 消</el-button>
        <el-button type="primary" @click="editUser">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 搜索 -->
    <el-card>
      <el-row :gutter="10">

        <!-- 关键字查找 -->
        <el-col :span="1.2">
          <div style="text-align: center;line-height: 40px">登录名称：</div>
        </el-col>
        <el-col :span="3">
          <el-input placeholder="请输入用户名或昵称："
                    v-model="UsersParam.query"
                    clearable
                    @clear="getUserLists"></el-input>
        </el-col>
        <el-col :span="1">
          <el-button type="primary" icon="el-icon-search" @click="getUserLists">搜索</el-button>
        </el-col>
      </el-row>
    </el-card>

    <!-- 用户列表 -->
    <el-card style="margin-top: 10px">
      <!-- 功能区域之创建用户 -->
      <el-button type="primary"
                 @click="showAddUserDialog()">
        创建用户
      </el-button>

      <!-- 表格 -->
      <el-table
        style="margin-top: 15px"
        :data="userLists"
        stripe
        border>
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="username" label="用户名" width="180px"></el-table-column>
        <el-table-column prop="nickName" label="昵称" width="180px"></el-table-column>
        <el-table-column label="头像" width="60px">
          <template slot-scope="scope">
            <img :src="scope.row.icon" width="40px" height="40px"/>
          </template>
        </el-table-column>
        <el-table-column prop="email" label="邮箱"></el-table-column>
        <el-table-column label="用户状态">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.status !== 0"
              active-color="#13ce66"
              inactive-color="#ff4949"
              @change="switchChange(scope.row)"
            />
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" content="编辑用户" placement="top">
              <el-button
                type="primary"
                icon="el-icon-edit"
                circle
                @click="editUserDialog(scope.row.id)"
              />
            </el-tooltip>
            <el-tooltip class="item" effect="dark" content="分配角色" placement="top">
              <el-button type="warning" icon="el-icon-star-off" circle />
            </el-tooltip>
            <el-tooltip class="item" effect="dark" content="删除用户" placement="top">
              <el-button
                type="danger"
                icon="el-icon-delete"
                circle
                @click="deleteUserById(scope.row.id)"
              />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        style="margin-top: 15px"
        :page-size="UsersParam.pageSize"
        :page-sizes="[1, 3, 5, 10]"
        :current-page="UsersParam.pageNum"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </el-card>
  </div>
</template>

<script>
import { userlists, updateStatus, createUser, getById, updateById, deleteById } from '@/api/user'
import { validateEmail } from '@/utils/validate'

export default {
  name: 'UsersList',
  data() {
    // 二次密码验证规则
    const validateRePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码！'))
      } else if (value !== this.addUserForm.password) {
        callback(new Error('两次输入密码不一致！'))
      } else {
        callback()
      }
    }

    return {
      // 分页查询参数
      UsersParam: {
        query: '',
        pageNum: 1,
        pageSize: 5
      },
      // 用户列表数组
      userLists: [],
      // 总记录数
      total: 0,
      // 搜索下拉列表
      options: [
        {
          value: '2',
          label: '所有'
        }, {
          value: '1',
          label: '正常'
        }, {
          value: '0',
          label: '停用'
        }
      ],
      // 搜索下拉列表默认所有
      selectDefaultValue: '2',
      // 新增用户Dialog
      AddUserdialogVisible: false,
      addUserForm: {
        username: '',
        password: '',
        rePassword: '',
        email: '',
        nickName: '',
        note: ''
      },
      passwordValue: '',
      addUserFormRoles: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        email: [
          { required: true, message: '请正确输入邮箱', trigger: 'blur' },
          { validator: validateEmail, trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '密码不少于6位', trigger: 'blur' }
        ],
        rePassword: [{ required: true, validator: validateRePassword, trigger: 'change' }]
      },
      // 编辑用户Dialog
      editUserVisible: false,
      editUserForm: {},
      editUserFormRules: {
        email: [
          { required: true, message: '请正确输入邮箱', trigger: 'blur' },
          { validator: validateEmail, trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    newPassword() {
      return this.addUserForm.password
    }
  },
  watch: {
    newPassword(val) {
      this.passwordValue = val
    },
    passwordValue(newname, oldname) {
      this.msgText = this.checkStrong(newname)
      if (this.msgText > 1 || this.msgText === 1) {
        document.getElementById('one').style.background = 'red'
      } else {
        document.getElementById('one').style.background = '#eee'
      }
      if (this.msgText > 2 || this.msgText === 2) {
        document.getElementById('two').style.background = 'orange'
      } else {
        document.getElementById('two').style.background = '#eee'
      }
      if (this.msgText === 4) {
        document.getElementById('three').style.background = '#00D1B2'
      } else {
        document.getElementById('three').style.background = '#eee'
      }
    }
  },
  created() {
    this.getUserLists()
  },
  methods: {
    // 获取用户列表
    getUserLists() {
      userlists(this.UsersParam).then(response => {
        if (response.code === 20000) {
          console.log(response)
          this.userLists = response.data.list
          this.total = response.data.total
        } else {
          return this.$message.error('获取用户列表失败')
        }
      })
    },

    // 监听 pagesize 的改变事件
    handleSizeChange(newSize) {
      this.UsersParam.pageSize = newSize
      this.getUserLists()
    },

    // 监听 页码值 改变的监听事件
    handleCurrentChange(newPage) {
      this.UsersParam.pageNum = newPage
      this.getUserLists()
    },

    // switch开关change事件
    switchChange(userInfo) {
      // 启用状态
      if (userInfo.status === 1) {
        updateStatus(userInfo.id, 0).then(response => {
          if (response.code === 20000) {
            this.getUserLists()
            return this.$message.success('更新用户状态成功')
          }
          this.$message.error('更新用户状态失败')
        })
      } else { // 停用状态
        updateStatus(userInfo.id, 1).then(response => {
          if (response.code === 20000) {
            this.getUserLists()
            return this.$message.success('更新用户状态成功')
          }
          this.$message.error('更新用户状态失败')
        })
      }
    },

    // 新增用户Dialog
    showAddUserDialog() {
      this.AddUserdialogVisible = true
    },

    // 监听新增用户Dialog关闭事件
    closeAddUserDialog() {
      this.$refs['addUserFormRef'].resetFields()
    },

    // 验证密码强度
    checkStrong(sValue) {
      var modes = 0
      // 正则表达式验证符合要求的
      if (sValue.length < 1) return modes
      if (/\d/.test(sValue)) modes++ // 数字
      if (/[a-z]/.test(sValue)) modes++ // 小写
      if (/[A-Z]/.test(sValue)) modes++ // 大写
      if (/\W/.test(sValue)) modes++ // 特殊字符

      // 逻辑处理
      switch (modes) {
        case 1:
          return 1
          // break
        case 2:
          return 2
          // break
        case 3:
        case 4:
          return sValue.length < 4 ? 3 : 4
          // break
      }
      return modes
    },

    // 创建用户确认
    createUser() {
      this.$refs['addUserFormRef'].validate(valid => {
        if (!valid) return
        createUser(this.addUserForm).then(response => {
          if (response.code === 20000) {
            this.$message.success('用户创建成功')
            this.AddUserdialogVisible = false
            this.getUserLists()
          } else {
            this.$message.error('用户创建失败')
            this.AddUserdialogVisible = false
          }
        })
      })
    },

    // 编辑用户Dialog
    editUserDialog(id) {
      this.editUserVisible = true
      getById(id).then(response => {
        if (response.code === 20000) {
          this.editUserForm = response.data
        } else {
          this.$message.error('查询用户信息失败')
        }
      })
    },

    // 编辑用户确认
    editUser() {
      updateById(this.editUserForm.id, this.editUserForm).then(response => {
        if (response.code === 20000) {
          this.$message.success('更新成功')
          this.editUserVisible = false
          this.getUserLists()
        } else {
          this.$message.error('更新失败')
          this.editUserVisible = false
        }
      })
    },

    // 删除用户信息
    deleteUserById(id) {
      // 弹窗提示
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 发起删除请求
        deleteById(id).then(response => {
          if (response.code === 20000) {
            this.$message.success('删除成功!')
            this.getUserLists()
          } else {
            this.$message.error('删除失败!')
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }
}
</script>

<style scoped>

  .input_span span {
    display: inline-block;
    width: 85px;
    height: 10px;
    background: #eee;
    line-height: 20px;
  }

  #one {
    border-top-left-radius: 5px;
    border-bottom-left-radius: 5px;
    border-right: 0px solid;
    margin-left: 20px;
    margin-right: 3px;
  }

  #two {
    border-left: 0px solid;
    border-right: 0px solid;
    margin-left: -5px;
    margin-right: 3px;
  }

  #three {
    border-top-right-radius: 5px;
    border-bottom-right-radius: 5px;
    border-left: 0px solid;
    margin-left: -5px;
  }

  #font span:nth-child(1) {
    color: red;
    margin-left: 70px;
  }

  #font span:nth-child(2) {
    color: orange;
    margin: 0 68px;
  }

  #font span:nth-child(3) {
    color: #00d1b2;
  }

</style>

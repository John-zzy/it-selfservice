<template>
  <div class="app-container">
    <el-form
      ref="profileForm"
      v-loading="formLoading"
      :data="profileForm"
      element-loading-text="加载中..."
      :model="profileForm"
      label-width="120px"
    >
      <el-form-item label="头像">
        <img :src="profileForm.icon" width="60" height="60">
      </el-form-item>
      <el-form-item label="账号">
        <el-input v-model="profileForm.username" :disabled="true"/>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="profileForm.nickName"/>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="profileForm.note"/>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="profileForm.email"/>
      </el-form-item>
      <el-form-item label="是否启用">
        <el-radio-group v-model="profileForm.status">
          <el-radio :label="0">禁用</el-radio>
          <el-radio :label="1">启用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import {info, update} from '@/api/profile'

  export default {
    name: "ProfileInfo",
    created() {
      this.fetchData()
    },
    data() {
      return {
        // 页面加载
        formLoading: true,
        // 用户信息
        profileForm: {
          id: '',
          username: '',
          nickName: '',
          note: '',
          icon: '',
          email: '',
          status: ''
        }
      }
    },
    methods: {
      // 请求用户信息
      fetchData() {
        info(this.$store.getters.name).then(response => {
          // 请求成功
          if (response.code === 20000) {
            this.profileForm = response.data
            this.formLoading = false
          }
          // 请求失败
          else {
            this.$message.error("请求用户信息失败")
            this.goChoicePeople()
          }
        })
      },

      // 失败请求2秒后跳转
      goChoicePeople() {
        const TIME_COUNT = 2
        if (!this.timer) {
          this.count = TIME_COUNT
          this.show = false
          this.timer = setInterval(() => {
            if (this.count > 0 && this.count <= TIME_COUNT) {
              this.count--
            } else {
              this.show = true
              clearInterval(this.timer)
              this.timer = null
              this.$router.push('/')
            }
          }, 1000)
        }
      },

      // 提交保存信息
      onSubmit() {
        this.formLoading = true
        update(this.profileForm).then(response => {
          this.$message.success("保存信息成功")
          this.formLoading = false
        }).catch(() => {
          this.$message.error("保存信息失败")
          this.formLoading = false
        })

      }
    }
  }
</script>

<style scoped>

</style>

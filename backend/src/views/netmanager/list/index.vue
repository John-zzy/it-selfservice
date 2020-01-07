<template>
  <div class="app-container">
    <!-- 创建交换机Dialog -->
    <el-dialog
      title="创建交换机"
      :visible.sync="addSwiDialogVisible"
      width="40%"
      top="8vh"
    >
      <!-- 步骤条 -->
      <el-steps :active="active" align-center finish-status="success">
        <el-step title="步骤 1" description="选择交换机端口数"></el-step>
        <el-step title="步骤 2" description="选择交换机位置"></el-step>
        <el-step title="步骤 3" description="填写交换机属性"></el-step>
      </el-steps>

      <!-- 创建交换机表单 -->
      <el-form ref="stepFormRef" :rules="stepFormRules" :model="stepForm"
               label-width="80px">
        <!-- 步骤 1 -->
        <div class="info" v-show="active === 1">
          <el-form-item label-width="120px" label="选择交换机：" prop="radio">
            <el-radio-group v-model="stepForm.radio">
              <el-radio :label="1">48口交换机</el-radio>
              <el-radio :label="2">24口交换机</el-radio>
              <el-radio :label="3">12口交换机</el-radio>
            </el-radio-group>
          </el-form-item>
        </div>
        <!-- 步骤 2 -->
        <div class="info" v-show="active === 2">
          <el-form-item label-width="120px" label="位置：" prop="position">
            <el-select v-model="stepForm.position" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </div>
        <!-- 步骤 3 -->
        <div class="info" v-show="active === 3">
          <el-form-item label-width="120px" label="交换机名称" prop="swiName">
            <el-input v-model="stepForm.swiName" placeholder="请输入交换机名称"/>
          </el-form-item>
          <el-form-item label-width="120px" label="交换机IP地址" prop="swiAddress">
            <el-input v-model="stepForm.swiAddress" placeholder="请输入交换机IP地址"/>
          </el-form-item>
          <el-form-item label-width="120px" label="备注" prop="swiComment">
            <el-input v-model="stepForm.swiComment" placeholder="请输入备注"/>
          </el-form-item>
        </div>
      </el-form>
      <!-- 控制按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button style="margin-top: 12px;"
                   @click="pre"
                   v-if="active > 1">上一步
        </el-button>
        <el-button style="margin-top: 12px;"
                   @click="next"
                   v-if="active < 3">下一步
        </el-button>
        <el-button type="primary" style="margin-top: 12px;"
                   @click="createSwich"
                   v-if="active === 3">提交
        </el-button>
      </span>
    </el-dialog>

    <!-- IP详细信息Dialog -->
    <el-dialog
      title="IP地址详情"
      :visible.sync="getDetailDialogVisible"
      width="40%"
      top="8vh">
      <div style="display: flex; justify-content: space-between;">
        <h2>端口交换机信息</h2>
        <el-tag style="margin-top: 12px;">交换机IP：{{ ipDetail.swiIpv4 }}</el-tag>
      </div>
      <el-form ref="ipDetailRef" :model="ipDetail" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="交换机端口号" prop="swiPortnum">
              <el-input :readonly="true" v-model="ipDetail.swiPortnum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="交换机地址" prop="swiName">
              <el-input :readonly="true" v-model="ipDetail.swiName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="交换机注册表" prop="swiAccesslist">
              <el-input :readonly="true" v-model="ipDetail.swiAccesslist"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="VLAN" prop="swiVlan">
              <el-input :readonly="true" v-model="ipDetail.swiVlan"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <h2 v-if="ipDetail.status">端口IP地址信息
        <el-tag type="success">状态：UP</el-tag>
      </h2>
      <h2 v-else>端口IP地址信息
        <el-tag type="warning">状态：DOWN</el-tag>
      </h2>
      <el-form ref="ipDetailRef" :model="ipDetail" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="墙面端口号" prop="portid">
              <el-input :readonly="true" v-model="ipDetail.portid"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="IPV4" prop="ipv4">
              <el-input :readonly="true" v-model="ipDetail.ipv4"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="使用人" prop="username">
              <el-input :readonly="true" v-model="ipDetail.username"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="IPV6" prop="ipv6">
              <el-input :readonly="true" v-model="ipDetail.ipv6"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="位置" prop="location">
              <el-input :readonly="true" v-model="ipDetail.location"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="MAC" prop="mac">
              <el-input :readonly="true" v-model="ipDetail.mac"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="楼层" prop="floorNum">
              <el-input :readonly="true" v-model="ipDetail.floorNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="房间号" prop="roomNum">
              <el-input :readonly="true" v-model="ipDetail.roomNum"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="comment">
          <el-input :readonly="true" v-model="ipDetail.comment"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="getDetailDialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>

    <!-- IP配置信息Dialog -->
    <el-dialog
      title="IP地址配置"
      :visible.sync="editDetailDialogVisible"
      width="40%"
      top="8vh">
      <h2>端口交换机信息</h2>
      <el-form ref="ipDetailRef" :model="ipDetail" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="交换机端口号" prop="swiPortnum">
              <el-input :readonly="true" v-model="ipDetail.swiPortnum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="交换机地址" prop="swiName">
              <el-input :readonly="true" v-model="ipDetail.swiName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="交换机注册表" prop="swiAccesslist">
              <el-input v-model="ipDetail.swiAccesslist"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="VLAN" prop="swiVlan">
              <el-input v-model="ipDetail.swiVlan"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <div style="display: flex; justify-content: space-between;">
        <h2>端口IP地址信息</h2>
        <div>
          <h4 style="float: left;">端口状态：</h4>
          <el-switch
            style="position: relative; top: 50%; margin-top: -25px"
            v-model="valueStatus"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="UP"
            inactive-text="DOWN"
            @change="valueStatusChange">
          </el-switch>
        </div>
      </div>

      <el-form ref="ipDetailRef" :model="ipDetail" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="墙面端口号" prop="portid">
              <el-input v-model="ipDetail.portid"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="IPV4" prop="ipv4">
              <el-input v-model="ipDetail.ipv4"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="使用人" prop="username">
              <el-input v-model="ipDetail.username"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="IPV6" prop="ipv6">
              <el-input v-model="ipDetail.ipv6"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="位置" prop="location">
              <el-input v-model="ipDetail.location"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="MAC" prop="mac">
              <el-input v-model="ipDetail.mac"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="楼层" prop="floorNum">
              <el-input v-model="ipDetail.floorNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="房间号" prop="roomNum">
              <el-input v-model="ipDetail.roomNum"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="comment">
          <el-input v-model="ipDetail.comment"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDetailDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateDetail">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 配置交换机Dialog Outter -->
    <el-dialog
      title="配置交换机信息"
      :visible.sync="editSwitchDialogVisible"
      width="60%"
      center
      top="8vh"
    >
      <el-button class="edSwiBtn" type="primary">添加位置</el-button>
      <!-- TreeTable 表格 -->
      <tree-table
        :data="swiList"
        :columns="treeTableCols"
        :selection-type="false"
        :expand-type="false"
        show-index
        border>
        <template slot="opt" slot-scope="scope">
          <div v-if="scope.row.id !== 447 && scope.row.parentId !== 447">
            <el-button size="mini" type="primary" icon="el-icon-edit"
                       @click="editInnerVisible(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" icon="el-icon-delete"
                       @click="deleteSwitch(scope.row.id)">删除</el-button>
          </div>
        </template>
      </tree-table>
    </el-dialog>

    <!-- 配置交换机Dialog Inner -->
    <el-dialog
      title="配置交换机"
      :visible.sync="innerVisible"
      width="40%"
      top="8vh">
      <el-form ref="switchDetailRef" :model="switchDetail" label-width="60px">
        <el-form-item label="ID">
          <el-input disabled v-model="switchDetail.id"></el-input>
        </el-form-item>
        <el-form-item label="parentId">
          <el-input disabled v-model="switchDetail.parentId"></el-input>
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="switchDetail.swiName"></el-input>
        </el-form-item>
        <el-form-item label="IP">
          <el-input v-model="switchDetail.swiIpv4"></el-input>
        </el-form-item>
        <el-form-item label="位置">
          <el-input v-model="switchDetail.swiLocation"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="switchDetail.swiComment"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="innerVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateSwichInfo">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 主体区域 -->
    <el-card>
      <el-container style="height: 100%; border: 1px solid #eee">
        <!-- 侧边栏 -->
        <el-aside width="230px" style="background-color: white;">
          <el-button class="asideBtn" size="mini" type="primary"
                     @click="addSwi()">创建交换机
          </el-button>
          <!-- 菜单 -->
          <el-menu :default-openeds="['447']"
                   :unique-opened="true"
                   active-text-color="#ffd04b">
            <el-submenu v-for="item in swiList" :key="item.id" :index="item.id + ''">
              <template slot="title"><i class="el-icon-caret-bottom"></i>{{ item.swiLocation }}</template>
              <el-submenu class="menuScroll"
                          v-for="item in item.children"
                          :key="item.id"
                          :index="item.id + ''">
                <template slot="title"><i class="el-icon-caret-right"></i>{{ item.swiLocation }}</template>
                <!-- 交换机列表名称 -->
                <el-menu-item @click="getSwiIp(item.id)"
                              v-for="item in item.children"
                              :key="item.id"
                              :index="item.id + ''">
                  {{ item.swiName }}
                </el-menu-item>
              </el-submenu>
            </el-submenu>
          </el-menu>
        </el-aside>

        <el-container>
          <!-- 导航条 -->
          <el-header class="header-breadcrumb">
            <div class="header-container">
              <!-- 横向导航条/ pageHeader -->
              <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item>公司</el-breadcrumb-item>
                <el-breadcrumb-item>{{this.$store.getters.getSwiName.swiName}}</el-breadcrumb-item>
              </el-breadcrumb>
            </div>

            <div style="float: right">
              <el-tooltip class="item" effect="dark" content="交换机设置" placement="left">
                <el-button
                  style="border: none;"
                  icon="el-icon-setting"
                  circle
                  @click="editSwitch"
                />
              </el-tooltip>
            </div>
          </el-header>

          <!-- 主页 -->
          <el-main>
            <el-row :gutter="10">
              <!-- 关键字查找 -->
              <el-col :span="1.2">
                <div style="text-align: center;line-height: 40px">关键字：</div>
              </el-col>
              <el-col :span="4">
                <el-input
                  style="width: 200px;"
                  placeholder="请输入端口号或IP地址："
                  v-model="searchQuery"
                  clearable
                  @clear="search(searchQuery)">
                </el-input>
              </el-col>
              <el-col :span="1">
                <el-button type="primary" icon="el-icon-search" @click="search(searchQuery)">搜索</el-button>
              </el-col>
            </el-row>

            <el-table style="margin-top: 10px" border :data="ipListPage">
              <el-table-column type="index"></el-table-column>
              <el-table-column prop="portid" label="端口号"></el-table-column>
              <el-table-column prop="location" label="位置"></el-table-column>
              <el-table-column prop="floorNum" label="楼层号"></el-table-column>
              <el-table-column prop="roomNum" label="房间号"></el-table-column>
              <el-table-column prop="username" label="使用人"></el-table-column>
              <el-table-column prop="ipv4" label="IPV4地址"></el-table-column>
              <el-table-column label="交换机ID">
                <template slot-scope="scope">
                  {{ scope.row.swiAddress}}
                </template>
              </el-table-column>
              <el-table-column label="操作" width="220px">
                <template slot-scope="scope">
                  <el-button size="mini" type="primary" plain round
                             @click="getDetail(scope.row.id)">详情
                  </el-button>
                  <el-button size="mini" type="success" plain round
                             @click="editDetail(scope.row.id)">编辑
                  </el-button>
                  <el-button size="mini" type="danger" plain round
                             @click="deleteDetail(scope.row.id)">删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-main>

          <!-- 分页插件 -->
          <el-pagination
            style="margin-top: 15px"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageParams.pageNum"
            :page-sizes="[3, 5, 10, 15]"
            :page-size="pageParams.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
          </el-pagination>

        </el-container>
      </el-container>
    </el-card>
  </div>
</template>

<script>
  import {
    getSwiListFun,
    getLists,
    getSwiLists,
    getIpPageList,
    getIpListBySwiAddress,
    getDetailById,
    updateDetail,
    updatePortStatus,
    deletePort,
    createSwitch,
    getSwitchDetail,
    updateSwitchDetail,
    deleteSwitch
  } from '@/api/netmanager'

  import { Loading } from 'element-ui'

  export default {
    name: "List",
    created() {
      this.getSwiList()
      this.getIpPageList()
    },
    data() {
      // 步骤3 验证
      const validateRequire = (rule, value, callback) => {
        if (this.active === 3 && !value) {
          callback(new Error('此选项不能为空'))
        } else {
          callback()
        }
      }
      return {
        // 分页
        pageParams: {
          query: '',
          pageNum: 1,
          pageSize: 10
        },
        total: 0,
        // 交换机列表
        swiList: [],
        // 所有IP地址
        ipListPage: [],
        // 配置交换机
        addSwiDialogVisible: false,
        // IP详细信息
        getDetailDialogVisible: false,
        ipDetail: {
          comment: '',
          created: '',
          floorNum: '',
          id: '',
          ipv4: '',
          ipv6: '',
          location: '',
          mac: '',
          portid: '',
          roomNum: '',
          status: '',
          swiAccesslist: '',
          swiName: '',
          swiAddress: '',
          swiIpv4: '',
          swiPortnum: '',
          swiVlan: '',
          updated: '',
          username: ''
        },
        // 编辑IP地址信息
        editDetailDialogVisible: false,
        // 端口状态
        valueStatus: true,
        // 步骤条index
        active: 1,
        // 创建交换机表单属性
        stepForm: {
          radio: 1,
          position: '',
          swiName: '',
          swiAddress: '',
          swiComment: ''
        },
        stepFormRules: {
          swiAddress: [{required: true, validator: validateRequire, trigger: 'blur'}],
          swiName: [{required: true, validator: validateRequire, trigger: 'blur'}]
        },
        // 交换机列表属性
        options: [],
        // 搜索关键字
        searchQuery: '',
        // 配置交换机
        editSwitchDialogVisible: false,
        innerVisible: false,
        // TreeTable的列的定义
        treeTableCols: [
          {
            label: '交换机位置',
            prop: 'swiLocation'
          },
          {
            label: '交换机名称',
            prop: 'swiName'
          },
          {
            label: '交换机地址',
            prop: 'swiIpv4'
          },
          {
            label: '操作',
            type: 'template',
            template: 'opt',
            minWidth: '100px'
          }
        ],
        // 交换机详细信息
        switchDetail: {}
      }
    },
    methods: {
      // 获取交换机列表
      getSwiList() {
        getSwiListFun().then(response => {
          // 请求成功
          if (response.code === 20000) {
            this.swiList = response.data
            console.log(this.swiList)
          }
          // 请求失败
          else {
            this.$message.error('获取交换机列表失败')
          }
        })
      },

      // 分页获取IP地址列表
      getIpPageList() {
        this.$store.dispatch('asyncSetSwiName', '')
        getIpPageList(this.pageParams).then(response => {
          // 请求成功
          if (response.code === 20000) {
            this.ipListPage = response.data.list
            this.total = response.data.total
          }
          // 请求失败
          else {
            this.$message.error("分页请求失败");
          }
        })
      },

      // 监听 pagesize 的改变事件
      handleSizeChange(newSize) {
        this.pageParams.pageSize = newSize
        this.getIpPageList()
      },

      // 监听 页码值 改变的监听事件
      handleCurrentChange(newPage) {
        this.pageParams.pageNum = newPage
        this.getIpPageList()
      },

      // 获取交换机IP列表
      getSwiIp(swiId) {
        getSwitchDetail(swiId).then(response => {
          this.$store.dispatch('asyncSetSwiName', response.data.swiName)
        })
        this.pageParams.query = swiId
        this.getIpPageList()
      },

      // 获取详细信息
      getDetail(id) {
        this.getDetailDialogVisible = true
        getDetailById(id).then(response => {
          // 请求成功
          if (response.code === 20000) {
            this.ipDetail = response.data
          } else {
            this.$message.error("获取IP详细信息失败！")
            this.getDetailDialogVisible = false
          }
        })
      },

      // 编辑详细信息
      editDetail(id) {
        this.editDetailDialogVisible = true
        getDetailById(id).then(response => {
          // 请求成功
          if (response.code === 20000) {
            this.ipDetail = response.data
            this.valueStatus = this.ipDetail.status === 1
          } else {
            this.$message.error("获取IP详细信息失败！")
            this.editDetailDialogVisible = false
          }
        })
      },

      // 更新详细信息
      updateDetail() {
        updateDetail(this.ipDetail.id, this.ipDetail)
          .then(response => {
            // 请求成功
            if (response.code === 20000) {
              this.$message.success("IP地址更新成功")
              this.editDetailDialogVisible = false
              this.getIpPageList()
            }
            // 请求失败
            else {
              this.$message.error("IP地址更新失败")
              this.editDetailDialogVisible = false
            }
          })
      },

      // 端口状态修改
      valueStatusChange(val) {
        // true
        if (!val) {
          updatePortStatus(this.ipDetail.id, 0).then(response => {
            // 请求成功
            if (response.code === 20000) {
              this.$message.success("IP端口状态更新成功")
            }
            // 请求失败
            else {
              this.$message.error("IP端口状态更新失败")
            }
          })
        }
        // false
        else {
          updatePortStatus(this.ipDetail.id, 1).then(response => {
            // 请求成功
            if (response.code === 20000) {
              this.$message.success("IP端口状态更新成功")
            }
            // 请求失败
            else {
              this.$message.error("IP端口状态更新失败")
            }
          })
        }
      },

      // 删除端口信息
      deleteDetail(id) {
        // 弹窗提示
        this.$confirm('此操作将永久删除该端口信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 发起删除请求
          deletePort(id).then(response => {
            // 请求成功
            if (response.code === 20000) {
              this.$message.success("删除成功!")
              this.getIpPageList()
            }
            // 请求失败
            else {
              this.$message.error("删除失败!")
            }
          })

        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },

      /** 创建交换机方法 begin **/
      addSwi() {
        // 初始化属性
        this.active = 1
        this.stepForm = {
          radio: 1,
          position: '',
          swiName: '',
          swiAddress: '',
          swiComment: ''
        }
        this.options = []
        getSwiLists().then(response => {
          // 请求成功
          if (response.code === 20000) {
            response.data.forEach(element => {
              this.options.push({
                value: element.id,
                label: element.swiLocation
              })
            })
          }
          // 请求失败
          else {
            this.$message.error("获取交换机列表失败")
          }
        })
        this.addSwiDialogVisible = true
      },

      next() {
        if (this.active++ > 3) this.active = 1
      },

      pre() {
        if (this.active-- < 2) this.active = 1
      },

      createSwich() {
        this.$refs['stepFormRef'].validate(valid => {
          if (!valid || this.stepForm.radio == null || this.stepForm.position == "") {
            return alert('表单验证不通过,请检查是否已全部填写!')
          }

          console.log('提交表单')
          createSwitch(this.stepForm).then(response => {
            // 请求成功
            if (response.code === 20000) {
              this.addSwiDialogVisible = false
              this.$message.success('创建成功，跳转编辑端口页面...')
            }

            // 请求失败
            else {
              this.addSwiDialogVisible = false
              this.$message.error('请求失败!')
            }
          })
        })
        this.openFullScreen2S()
        this.$router.go(0)
      },

      openFullScreen2S() {
        const loading = this.$loading({
          lock: true,
          text: 'Loading',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        setTimeout(() => {
          loading.close();
        }, 5000);
      },
      /** 创建交换机方法 end **/

      // 搜索
      search(query) {
        this.pageParams.query = query
        this.getIpPageList()
      },

      // 编辑交换机outter
      editSwitch() {
        this.editSwitchDialogVisible = true
      },

      // 编辑交换机inner
      editInnerVisible(row) {
        this.innerVisible = true
        getSwitchDetail(row.id).then(response => {
          if (response.code === 20000) {
            this.switchDetail = response.data
          }
          else {
            this.$message.error('获取交换机信息失败')
          }
        })
      },

      // 更新交换机信息
      updateSwichInfo() {
        updateSwitchDetail(this.switchDetail.id, this.switchDetail)
          .then(response => {
            if (response.code === 20000) {
              this.innerVisible = false
              this.$message.success('交换机信息更新成功')
            }
          })
      },

      // 删除交换机信息
      deleteSwitch(id) {
        alert(id)
      }
    }
  }
</script>

<style scoped>

  .el-header {
    background-color: #eee;
    color: #333;
    line-height: 60px;
  }

  .el-aside {
    color: #333;
  }

  .menuScroll {
    max-height: 500px;
    overflow-y: auto;
  }

  .asideBtn {
    width: 80%;
    margin: 10px 20px;
  }

  .el-menu {
    border-right: none;
  }

  .info {
    margin-top: 30px;
  }

  .header-breadcrumb {
    background-color: white;
    position: relative;
    border-bottom: 2px solid #eee;
  }

  .header-container {
    position: absolute;
    top: 50%;
    margin-top: -10px;
  }

  .edSwiBtn {
    margin-bottom: 15px;
  }

</style>

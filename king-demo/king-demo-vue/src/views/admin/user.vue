<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input @keyup.enter.native="handleSearch" style="width: 200px;" class="filter-item" v-model="listQuery.username" :placeholder="$t('username')">
      </el-input>
      <el-select @change="handleSearch" clearable style="width: 90px" class="filter-item" v-model="listQuery.status" :placeholder="$t('status')">
        <el-option v-for="item in statusList" :key="item" :label="$t(item)" :value="item">
        </el-option>
      </el-select>
      <el-select @change="handleSearch" clearable class="filter-item" style="width: 130px" v-model="listQuery.level" :placeholder="$t('level')">
        <el-option v-for="item in  levelList" :key="item" :label="$t(item)" :value="item">
        </el-option>
      </el-select>
    </div>
    <div class="filter-container">
      <el-button round class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleSearch">{{$t('search')}}</el-button>
      <el-button round class="filter-item" style="margin-left: 10px;" :loading="loading_add" @click="handleAdd" type="primary" icon="el-icon-edit">{{$t('add')}}</el-button>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" border fit highlight-current-row
      style="width: 100%;min-height:500px;">
      <el-table-column  min-width="60px"align="center" :label="$t('No')">
        <template slot-scope="scope">
          <span>{{scope.row.No}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="130px" align="center" :label="$t('id')" >
        <template slot-scope="scope">
          <span>{{scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" :label="$t('username')">
        <template slot-scope="scope">
          <span>{{scope.row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="95px" align="center" :label="$t('status')">
        <template slot-scope="scope">
          <template v-if="scope.row.status === 'able'">
            <el-popover placement="top" width="160" v-model="scope.row.visible_updateStatus">
              <p>确定要<b>禁用</b>么？</p>
              <div style="text-align: right; margin: 0">
                <el-button round size="mini" type="text" @click="scope.row.visible_updateStatus = false">取消</el-button>
                <el-button round type="primary" size="mini" @click="handleUpdateStatus(scope.row, 'disable')" >确定</el-button>
              </div>
              <el-button round slot="reference" size="mini" :loading="scope.row.loading_updateStatus" :type="typeStatus(scope.row.status)" @click="scope.row.visible_updateStatus = true">{{$t(scope.row.status)}}</el-button>
            </el-popover>
          </template>
          <template v-else-if="scope.row.status === 'disable'">
            <el-popover placement="top" width="160" v-model="scope.row.visible_updateStatus">
              <p>确定要<b>启用</b>么？</p>
              <div style="text-align: right; margin: 0">
                <el-button round size="mini" type="text" @click="scope.row.visible_updateStatus = false">取消</el-button>
                <el-button round type="primary" size="mini" @click="handleUpdateStatus(scope.row, 'able')" >确定</el-button>
              </div>
              <el-button round slot="reference" size="mini" :loading="scope.row.loading_updateStatus" :type="typeStatus(scope.row.status)" @click="scope.row.visible_updateStatus = true">{{$t(scope.row.status)}}</el-button>
            </el-popover>
          </template>
          <template v-else>
            <el-button round size="mini" :type="typeStatus(scope.row.status)" >{{$t(scope.row.status)}}</el-button>
          </template>
        </template>
      </el-table-column>
      <el-table-column min-width="95px" class-name="status-col" :label="$t('level')">
        <template slot-scope="scope">
          <el-tag :type="typeLevel(scope.row.level)">
            {{ $t(scope.row.level) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column min-width="85px" align="center" :label="$t('routeRole')">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.roleRouteName }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column min-width="85px" :label="$t('dataRole')">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.roleDataName }}</el-tag>
        </template>
      </el-table-column>
      <!--表数据固定字段信息 start-->
      <el-table-column min-width="170px" class-name="status-col" :label="$t('insertTime')">
        <template slot-scope="scope">
          <span>{{scope.row.insertTime | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
        </template>
      </el-table-column>
      <!--表数据固定字段信息 end-->
      <el-table-column align="center" :label="$t('actions')" width="230" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!--固定操作功能 start-->
          <template v-if="scope.row.waitingForFlush">
            <span>{{ $t('waitingForFlush') }}</span>
          </template>
          <template v-else>
            <el-button round type="primary" size="mini" :loading="scope.row.loading_handleUpdate" @click="handleUpdate(scope.row)">{{$t('edit')}}</el-button>
            <el-popover placement="top" width="160" v-model="scope.row.visible_del">
              <p>确定要删除么？</p>
              <div style="text-align: right; margin: 0">
                <el-button round size="mini" type="text" @click="scope.row.visible_del = false">取消</el-button>
                <el-button round type="primary" size="mini" @click="delData(scope.row)" >确定</el-button>
              </div>
              <el-button round slot="reference" type="info" size="small" :loading="scope.row.loading_del" @click="scope.row.visible_del = true">{{$t('del')}}</el-button>
            </el-popover>
          </template>
          <!--固定操作功能 end-->
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handlePageChange" :current-page="listQuery.page" :page-sizes="[10,20,30, 50]" :page-size="listQuery.limit" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <!--固定弹出层 start-->
    <el-dialog :title="$t(dialogTitle)" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :model="temp" :rules="rules" label-position="left" label-width="90px" style='width: 400px; margin-left:50px;'>
        <el-form-item :label="$t('id')" prop="id" v-show="false">
          <el-input :disabled="true" v-model="temp.id"></el-input>
        </el-form-item>
        <el-form-item :label="$t('username')" prop="username">
          <el-input :disabled="temp.disabled_username" v-model="temp.username"></el-input>
        </el-form-item>
        <el-form-item :label="$t('password')" prop="password">
          <el-input v-model="temp.password" placeholder="修改模式，若修改密码，则输入"></el-input>
        </el-form-item>
        <el-form-item :label="$t('status')" prop="status">
          <el-select class="filter-item" v-model="temp.status">
            <el-option v-for="item in statusList" :key="item" :label="$t(item)" :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('level')" prop="level">
          <el-select class="filter-item" v-model="temp.level">
            <el-option v-for="item in levelList" :key="item" :label="$t(item)" :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('roleRoute')" prop="roleRouteId">
          <el-select class="filter-item" v-model="temp.roleRouteId">
            <el-option v-for="route in roleRouteList" :key="route.id" :label="route.name" :value="route.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('roleData')" prop="roleDataId">
          <el-select class="filter-item" v-model="temp.roleDataId">
            <el-option v-for="data in roleDataList" :key="data.id" :label="data.name" :value="data.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button round v-if="dialogType === 'add'" type="primary" :loading="loading_addData" @click="addData">{{$t('confirm')}}</el-button>
        <el-button round v-else="dialogType === 'update'" type="primary" :loading="loading_updateData" @click="updateData">{{$t('confirm')}}</el-button>
        <el-button round @click="cleanDialog">{{$t('cancel')}}</el-button>
      </div>
    </el-dialog>
    <!--固定弹出层 end-->
  </div>
</template>

<script>
  import { getList, add, get, update, del } from '@/api/admin/user'
  import { getList as getRoleRouteList } from '@/api/admin/role/routeRole'
  import { getList as getRoleDataList } from '@/api/admin/role/dataRole'
  import waves from '@/directive/waves' // 水波纹指令
  import { parseTime } from '@/utils'
  import store from '@/store'

  export default {
    name: 'user',
    directives: {
      waves
    },
    data() {
      return {
        /* 固定功能字段 start */
        loading_add: false,
        loading_addData: false,
        loading_updateData: false,
        tableKey: 0,
        list: null,
        total: null,
        listLoading: false,
        listQuery: {
          page: 1,
          limit: 10,
          level: undefined,
          status: undefined,
          username: undefined
        },
        temp: {
          disabled_username: false
        },
        dialogFormVisible: false,
        dialogType: '',
        dialogTitle: '',
        /* 固定功能字段 start */
        levelList: ['user', 'vip', 'svip', 'admin', 'super'],
        statusList: ['able', 'disable'],
        roleDataList: '',
        roleRouteList: '',
        rules: {
          id: [
            { required: true, message: this.$t('required'), trigger: 'blur' }
          ],
          username: [
            { required: true, message: this.$t('required'), trigger: 'blur' }
          ],
          password: [
            { required: true, message: this.$t('required'), trigger: 'blur' }
          ],
          status: [
            { required: true, message: this.$t('required'), trigger: 'blur' }
          ],
          level: [
            { required: true, message: this.$t('required'), trigger: 'blur' }
          ],
          roleData: [
            { required: true, message: this.$t('required'), trigger: 'blur' }
          ],
          roleRoute: [
            { required: true, message: this.$t('required'), trigger: 'blur' }
          ]
        }
      }
    },
    created() {
      this.resetTemp()
      this.getList()
      this.getRoleDataList()
      this.getRoleRouteList()
    },
    methods: {
      /* 固定功能方法 start */
      resetTemp() {
        this.temp = {
          id: '',
          username: '',
          password: '',
          status: '',
          level: '',
          disabled_username: false
        }
      },
      cleanDialog() {
        this.resetTemp()
        this.dialogType = ''
        this.dialogFormVisible = false
        this.dialogTitle = ''
      },
      notifyClicking(loading, callBack) {
        if (loading) {
          this.$notify({
            title: '警告',
            message: '重复操作了',
            type: 'warning',
            duration: 1000
          })
        } else {
          callBack()
        }
      },
      getList() {
        this.notifyClicking(this.listLoading, () => {
          this.listLoading = true
          getList(this.listQuery).then(data => {
            this.formaterList(data.list)
            this.list = data.list
            this.total = data.total
            this.listLoading = false
          }).catch(() => {
            this.listLoading = false
          })
        })
      },
      handleSearch() {
        this.listQuery.page = 1
        this.getList()
      },
      handleSizeChange(val) {
        this.listQuery.page = 1
        this.listQuery.limit = val
        this.getList()
      },
      handlePageChange(val) {
        this.listQuery.page = val
        this.getList()
      },
      handleAdd() {
        this.notifyClicking(this.loading_add, () => {
          this.loading_add = true
          this.resetTemp()
          this.dialogType = 'add'
          this.dialogFormVisible = true
          this.dialogTitle = 'add'
          this.rules.id[0].required = false
          this.rules.password[0].required = true
          this.temp.disabled_username = false
          this.$nextTick(() => {
            this.$refs['dataForm'].clearValidate()
          })
          this.loading_add = false
        })
      },
      addData() {
        this.notifyClicking(this.loading_addData, () => {
          this.loading_addData = true
          this.$refs['dataForm'].validate((valid) => {
            if (valid) {
              add(this.temp).then(() => {
                this.temp.waitingForFlush = true
                this.cacheGet(this.temp, 'add')
                this.$notify({
                  title: '成功',
                  message: '创建成功',
                  type: 'success',
                  duration: 2000
                })
                this.cleanDialog()
                this.$nextTick(() => {
                  this.$refs['dataForm'].clearValidate()
                })
                this.loading_addData = false
              }).catch(() => {
                this.loading_addData = false
              })
            } else {
              this.loading_addData = false
            }
          })
        })
      },
      handleUpdate(row) {
        this.notifyClicking(row.loading_handleUpdate, () => {
          row.loading_handleUpdate = true
          get(row.id).then((data) => {
            this.temp = Object.assign({}, data.vo)
            this.dialogType = 'update'
            this.dialogFormVisible = true
            this.dialogTitle = 'update'
            this.rules.id[0].required = true
            this.rules.password[0].required = false
            this.temp.disabled_username = true
            this.$nextTick(() => {
              this.$refs['dataForm'].clearValidate()
            })
            row.loading_handleUpdate = false
          }).catch(() => {
            this.$notify({
              title: '失败',
              message: '获取信息失败',
              type: 'error',
              duration: 2000
            })
            row.loading_handleUpdate = false
          })
        })
      },
      updateData() {
        this.notifyClicking(this.loading_updateData, () => {
          this.loading_updateData = true
          this.$refs['dataForm'].validate((valid) => {
            if (valid) {
              update(this.temp).then(() => {
                this.temp.waitingForFlush = true
                this.cacheGet(this.temp, 'replace')
                this.$notify({
                  title: '成功',
                  message: '更新成功',
                  type: 'success',
                  duration: 2000
                })
                this.cleanDialog()
                this.$nextTick(() => {
                  this.$refs['dataForm'].clearValidate()
                })
                this.loading_updateData = false
              }).catch(() => {
                this.loading_updateData = false
              })
            } else {
              this.loading_updateData = false
            }
          })
        })
      },
      delData(row) {
        this.notifyClicking(row.loading_del, () => {
          row.loading_del = true
          row.visible_del = false
          del(row.id).then(() => {
            this.cacheGet(row, 'remove')
            this.$notify({
              title: '成功',
              message: '删除成功',
              type: 'success',
              duration: 2000
            })
            row.loading_del = false
          }).catch(() => {
            row.loading_del = false
          })
        })
      },
      cacheGet(row, action) {
        if (action === 'add') {
          this.list.unshift(row)
        } else {
          for (const v of this.list) {
            if (v.id === row.id) {
              const index = this.list.indexOf(v)
              if (action === 'replace') {
                this.list.splice(index, 1, row)
              } else if (action === 'remove') {
                this.list.splice(index, 1)
              }
              break
            }
          }
        }
      },
      formaterList(list) {
        let i = 1
        for (const v of list) { // 响应
          v.No = i++
          v.loading_handleUpdate = false
          v.loading_updateData = false
          v.waitingForFlush = false
          v.loading_del = false
          v.visible_del = false
          v.loading_updateStatus = false
          v.visible_updateStatus = false
        }
      },
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => {
          if (j === 'timestamp') {
            return parseTime(v[j])
          } else {
            return v[j]
          }
        }))
      },
      checkLevel(level) {
        let fg = store.getters.level === level
        fg = true
        return fg
      },
      /* 固定功能方法 end */
      // 个性化定义方法
      getRoleDataList() {
        const pageRequest = {
          page: 1,
          limit: -1
        }
        getRoleDataList(pageRequest).then(data => {
          this.roleDataList = data.list
        }).catch(() => {
          this.$notify({
            title: '警告',
            message: '数据角色加载异常',
            type: 'warning',
            duration: 2000
          })
        })
      },
      getRoleRouteList() {
        const pageRequest = {
          page: 1,
          limit: -1
        }
        getRoleRouteList(pageRequest).then(data => {
          this.roleRouteList = data.list
        }).catch(() => {
          this.$notify({
            title: '警告',
            message: '路由角色加载异常',
            type: 'warning',
            duration: 2000
          })
        })
      },
      handleUpdateStatus(row, status) {
        this.notifyClicking(row.loading_updateStatus, () => {
          row.loading_updateStatus = true
          row.visible_updateStatus = false
          const vo = {
            id: row.id,
            status: status
          }
          update(vo).then(() => {
            const index = this.list.indexOf(row)
            row.status = vo.status
            this.list.splice(index, 1, row)
            this.$notify({
              title: '成功',
              message: '更新成功',
              type: 'success',
              duration: 2000
            })
            row.loading_updateStatus = false
          }).catch(() => {
            row.loading_updateStatus = false
          })
        })
      },
      typeLevel(level) {
        if (level === 'vip') return 'danger'
        if (level === 'svip') return 'warning'
        if (level === 'admin') return ''
        if (level === 'super') return 'success'
        return 'info'
      },
      typeStatus(status) {
        if (status === 'able') return 'success'
        if (status === 'disable') return 'danger'
        return 'warning'
      }
    }
  }
</script>

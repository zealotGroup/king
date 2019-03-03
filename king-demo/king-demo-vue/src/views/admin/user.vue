<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input @keyup.enter.native="handleSearch" style="width: 200px;" class="filter-item"
                v-model="listQuery.username" :placeholder="$t('table.user.username')">
      </el-input>
      <el-select @change="handleSearch" clearable style="width: 90px" class="filter-item" v-model="listQuery.status"
                 :placeholder="$t('table.user.status')">
        <el-option v-for="item in statusList" :key="item" :label="$t(item)" :value="item">
        </el-option>
      </el-select>
      <el-select @change="handleSearch" clearable class="filter-item" style="width: 130px" v-model="listQuery.level"
                 :placeholder="$t('table.user.level')">
        <el-option v-for="item in  levelList" :key="item" :label="$t(item)" :value="item">
        </el-option>
      </el-select>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleSearch">
        {{$t('table.search')}}
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleAdd" type="primary" icon="el-icon-edit">
        {{$t('table.add')}}
      </el-button>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" border fit highlight-current-row
              style="width: 100%;min-height:700px;">
      <el-table-column min-width="60px" align="center" :label="$t('table.No')">
        <template slot-scope="scope">
          <span>{{scope.row.No}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="50px" align="center" :label="$t('table.user.id')" v-if="checkLevel('super')">
        <template slot-scope="scope">
          <span>{{scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" :label="$t('table.user.username')">
        <template slot-scope="scope">
          <span>{{scope.row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" align="center" :label="$t('table.user.password')" v-if="checkLevel('super')">
        <template slot-scope="scope">
          <span>{{scope.row.password}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="95px" align="center" :label="$t('table.user.status')"
                       v-if="checkLevel('super') || checkLevel('admin')">
        <template slot-scope="scope">
          <el-button :type="typeStatus(scope.row.status)" size="mini">{{ $t(scope.row.status)}}</el-button>
        </template>
      </el-table-column>
      <el-table-column min-width="95px" class-name="status-col" :label="$t('table.user.level')"
                       v-if="checkLevel('super') || checkLevel('admin')">
        <template slot-scope="scope">
          <el-tag :type="typeLevel(scope.row.level)">
            {{ $t(scope.row.level) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column min-width="170px" class-name="status-col" :label="$t('table.user.lastLoginTime')">
        <template slot-scope="scope">
          <span>{{ scope.row.lastLoginTime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" class-name="status-col" :label="$t('table.user.loginTimes')"
                       v-if="checkLevel('super') || checkLevel('admin')">
        <template slot-scope="scope">
          <span>{{scope.row.loginTimes}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="85px" align="center" :label="$t('table.user.routeRole')"
                       v-if="checkLevel('super') || checkLevel('admin')">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.routeRole }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column min-width="85px" :label="$t('table.user.dataRole')"
                       v-if="checkLevel('super') || checkLevel('admin')">
        <template slot-scope="scope">
          <el-tag>{{scope.row.dataRole }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column min-width="170px" class-name="status-col" :label="$t('table.user.createTime')"
                       v-if="checkLevel('super')">
        <template slot-scope="scope">
          <span>{{scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" class-name="status-col" :label="$t('table.user.createUser')"
                       v-if="checkLevel('super')">
        <template slot-scope="scope">
          <span>{{scope.row.createUser}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="170px" class-name="status-col" :label="$t('table.user.lastUpdateTime')"
                       v-if="checkLevel('super')">
        <template slot-scope="scope">
          <span>{{scope.row.lastUpdateTime | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" class-name="status-col" :label="$t('table.user.lastUpdateUser')"
                       v-if="checkLevel('super')">
        <template slot-scope="scope">
          <span>{{scope.row.lastUpdateUser}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" class-name="status-col" :label="$t('table.user.remark')">
        <template slot-scope="scope">
          <span>{{scope.row.remark}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('table.actions')" width="230" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">{{$t('btn.edit')}}</el-button>
          <el-button v-if="scope.row.status != 'deleted'" size="mini" type="danger"
                     @click="handleUpdateStatus(scope.row,'deleted')">{{$t('btn.delete')}}
          </el-button>
          <el-button v-if="scope.row.status === 'deleted'" size="mini" type="success"
                     @click="handleUpdateStatus(scope.row,'able')">{{$t('btn.able')}}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handlePageChange"
                     :current-page="listQuery.page" :page-sizes="[10,20,30, 50]" :page-size="listQuery.limit"
                     layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <el-dialog :title="$t(dialogTitle)" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :model="temp" label-position="left" label-width="70px"
               style='width: 400px; margin-left:50px;'>
        <el-form-item :label="$t('table.user.id')" prop="id" v-show="true">
          <el-input v-model="temp.id"></el-input>
        </el-form-item>
        <el-form-item :label="$t('table.user.username')" prop="username" :rules="rules.username">
          <el-input v-model="temp.username"></el-input>
        </el-form-item>
        <el-form-item :label="$t('table.user.password')" prop="password" :rules="rules.password">
          <el-input v-model="temp.password"></el-input>
        </el-form-item>
        <el-form-item :label="$t('table.user.status')" prop="status" :rules="rules.status">
          <el-select class="filter-item" v-model="temp.status" :placeholder="$t('table.user.status')">
            <el-option v-for="item in statusList" :key="item" :label="$t(item)" :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('table.user.level')" prop="level" :rules="rules.level">
          <el-select class="filter-item" v-model="temp.level" :placeholder="$t('table.user.level')">
            <el-option v-for="item in levelList" :key="item" :label="$t(item)" :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('table.user.remark')">
          <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="Please input"
                    v-model="temp.remark">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="dialogType=='add'" type="primary" @click="addData">{{$t('table.confirm')}}</el-button>
        <el-button v-else="dialogType=='update'" type="primary" @click="updateData">{{$t('table.confirm')}}</el-button>
        <el-button @click="cleanDialog">{{$t('table.cancel')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {getList, add, update, del} from '@/api/admin/user'
  import waves from '@/directive/waves' // 水波纹指令
  import {parseTime} from '@/utils'
  import store from '@/store'

  export default {
    name: 'complexTable',
    directives: {
      waves
    },
    data() {
      return {
        tableKey: 0,
        list: null,
        total: null,
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 10,
          level: undefined,
          title: undefined,
          status: undefined
        },
        levelList: ['user', 'vip', 'svip', 'admin', 'super'],
        statusList: ['able', 'disable', 'deleted'],
        temp: {
          id: '',
          username: '',
          password: '',
          status: '',
          level: '',
          remark: ''
        },
        dialogFormVisible: false,
        dialogType: '',
        dialogTitle: '',
        rules: {
          username: [{required: true, message: this.$t('table.required'), trigger: 'change'}],
          password: [{required: true, message: this.$t('table.required'), trigger: 'blur'}],
          status: [{required: true, message: this.$t('table.required'), trigger: 'blur'}],
          level: [{required: true, message: this.$t('table.required'), trigger: 'blur'}]
        }
      }
    },
    created() {
      this.getList()
    },
    methods: {
      resetTemp() {
        this.temp = {
          id: '',
          username: '',
          password: '',
          status: '',
          level: '',
          remark: ''
        }
      },
      cleanDialog() {
        this.resetTemp()
        this.dialogType = ''
        this.dialogFormVisible = false
        this.dialogTitle = ''
      },
      getList() {
        this.listLoading = true
        getList(this.listQuery).then(data => {
          this.list = data.items
          this.total = data.total

          // Just to simulate the time of the request
          setTimeout(() => {
            this.listLoading = false
          }, 0.5 * 1000)
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
        this.resetTemp()
        this.dialogType = 'add'
        this.dialogFormVisible = true
        this.dialogTitle = 'table.add'
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      },
      addData() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            add(this.temp).then(() => {
              this.cleanDialog()
              this.list.unshift(this.temp)
              this.$notify({
                title: '成功',
                message: '创建成功',
                type: 'success',
                duration: 2000
              })
            })
          }
        })
      },
      handleUpdate(row) {
        this.temp = Object.assign({}, row) // copy obj
        this.dialogType = 'update'
        this.dialogFormVisible = true
        this.dialogTitle = 'table.update'
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      },
      updateData() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            update(this.temp).then(() => {
              for (const v of this.list) {
                if (v.id === this.temp.id) {
                  const index = this.list.indexOf(v)
                  this.list.splice(index, 1, this.temp)
                  break
                }
              }
              this.cleanDialog()
              this.$notify({
                title: '成功',
                message: '更新成功',
                type: 'success',
                duration: 2000
              })
            })
          }
        })
      },
      handleUpdateStatus(row, status) {
        const vo = {
          id: row.id,
          status: status
        }
        update(vo).then(() => {
          for (const v of this.list) {
            if (v.id === this.temp.id) {
              const index = this.list.indexOf(v)
              this.list.splice(index, 1, this.temp)
              break
            }
          }
          this.cleanDialog()
          this.$notify({
            title: '成功',
            message: '更新成功',
            type: 'success',
            duration: 2000
          })
        })
      },
      handleDel(row) {
        del(row.id).then(() => {
          const index = this.list.indexOf(row)
          this.list.splice(index, 1)
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
        })
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
        if (status === 'deleted') return ''
        return 'warning'
      },
      checkLevel(level) {
        let fg = store.getters.level === level
        fg = true
        return fg
      }
    }
  }
</script>

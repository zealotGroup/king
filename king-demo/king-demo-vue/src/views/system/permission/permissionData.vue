<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input @keyup.enter.native="handleSearch" style="min-width: 200px;" class="filter-item" v-model="listQuery.like" placeholder="输入关键词搜索">
      </el-input>
    </div>
    <div class="filter-container">
      <el-button round class="filter-item" type="primary" icon="el-icon-search" @click="handleSearch">{{$t('search')}}</el-button>
      <el-button round class="filter-item" style="margin-left: 10px;" @click="handleAdd" type="primary" icon="el-icon-edit">{{$t('add')}}</el-button>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" border fit highlight-current-row style="width: 100%;min-height:500px;">
      <el-table-column min-width="60px"align="center" :label="$t('No')">
        <template slot-scope="scope">
          <span>{{scope.row.No}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="130px" align="center" :label="$t('id')">
        <template slot-scope="scope">
          <span>{{scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" align="center" :label="$t('name')">
        <template slot-scope="scope">
          <template v-if="scope.row.loading_handleUpdate">
            <el-input v-model="scope.row.name_"></el-input>
          </template>
          <span v-else>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <!--表数据固定字段信息 start-->
      <el-table-column min-width="170px" class-name="status-col" :label="$t('insertTime')" >
        <template slot-scope="scope">
          <span>{{scope.row.insertTime | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="170px" class-name="status-col" :label="$t('updateTime')" >
        <template slot-scope="scope">
          <span>{{scope.row.updateTime | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
        </template>
      </el-table-column>
      <!--表数据固定字段信息 end-->
      <el-table-column align="center" :label="$t('actions')" min-width="250" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!--固定操作功能 start-->
          <template v-if="scope.row.waitingForFlush">
            <span>{{ $t('waitingForFlush') }}</span>
          </template>
          <template v-else>
            <span v-show="scope.row.loading_handleUpdate" >
              <el-button round type="success" size="mini" :loading="scope.row.loading_updateData" @click="updateData(scope.row)" >{{ $t('ok') }}</el-button>
            </span>
            <span v-show="scope.row.loading_handleUpdate" >
              <el-button round type="warning" size="mini" @click="cancelUpdate(scope.row)" >{{ $t('cancel') }}</el-button>
            </span>
            <span v-show="!scope.row.loading_handleUpdate" >
              <el-button round type="primary" size="mini" @click="handleUpdate(scope.row)" >{{ $t('edit') }}</el-button>
            </span>

            <template v-if="!scope.row.loading_handleUpdate">
              <el-popover placement="top" width="160" v-model="scope.row.visible_del">
                <p>确定要删除么？</p>
                <div style="text-align: right; margin: 0">
                  <el-button round size="mini" type="text" @click="scope.row.visible_del = false">取消</el-button>
                  <el-button round type="primary" size="mini" @click="delData(scope.row)">确定</el-button>
                </div>
                <el-button round slot="reference" type="info" size="small" :loading="scope.row.loading_del"
                           @click="scope.row.visible_del = true">{{$t('del')}}
                </el-button>
              </el-popover>
            </template>
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
    <el-dialog :title="$t('add')" :visible.sync="dialogFormVisible">
      <el-form ref="dataFormDataRole" :model="temp" :rules="rules" label-position="left" label-width="70px" style='width: 400px; margin-left:50px;'>
        <el-form-item :label="$t('id')" prop="id" v-show="false">
          <el-input v-model="temp.id"></el-input>
        </el-form-item>
        <el-form-item :label="$t('name')" prop="name">
          <el-input v-model="temp.name"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button round type="primary" :loading="loading_addData" @click="addData">{{$t('confirm')}}</el-button>
        <el-button round @click="cleanDialog">{{$t('cancel')}}</el-button>
      </div>
    </el-dialog>
    <!--固定弹出层 end-->
  </div>
</template>

<script>
import { getList, add, update, del } from '@/api/system/permission/permissionData'
import { parseTime } from '@/utils'
import store from '@/store'

export default {
  name: 'permissionData',
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
        like: undefined
      },
      temp: undefined,
      dialogFormVisible: false,
      /* 固定功能字段 start */
      rules: {
        name: [
          { required: true, message: this.$t('required'), trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.resetTemp()
    this.getList()
  },
  methods: {
    /* 固定功能方法 start */
    resetTemp() {
      this.temp = {
        id: '',
        name: ''
      }
    },
    editTemp(row, action) {
      if (action === 'handleUpdate') {
        row.name_ = row.name
      } else if (action === 'updateData') {
        row.name = row.name_
      }
    },
    cleanDialog() {
      this.resetTemp()
      this.dialogFormVisible = false
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
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dataFormDataRole'].clearValidate()
        })
        this.loading_add = false
      })
    },
    addData() {
      this.notifyClicking(this.loading_addData, () => {
        this.loading_addData = true
        this.$refs['dataFormDataRole'].validate((valid) => {
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
                this.$refs['dataFormDataRole'].clearValidate()
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
        this.editTemp(row, 'handleUpdate')
      })
    },
    cancelUpdate(row) {
      row.loading_handleUpdate = false
    },
    updateData(row) {
      this.notifyClicking(row.loading_updateData, () => {
        row.loading_updateData = true
        this.editTemp(row, 'updateData')
        const valid = true
        if (valid) {
          update(row).then(() => {
            row.waitingForFlush = true
            this.cacheGet(row, 'replace')
            this.$notify({
              title: '成功',
              message: '更新成功',
              type: 'success',
              duration: 2000
            })
            row.loading_handleUpdate = false
            row.loading_updateData = false
          }).catch(() => {
            row.loading_updateData = false
          })
        } else {
          row.loading_updateData = false
        }
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
      fg = false
      return fg
    }
    /* 固定功能方法 end */
  }
}
</script>


<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input @keyup.enter.native="handleSearch" style="min-width: 200px;" class="filter-item" v-model="listQuery.like" placeholder="输入关键词搜索">
      </el-input>
      <el-button round class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleSearch">{{$t('table.search')}}</el-button>
      <el-button round class="filter-item" style="margin-left: 10px;" :loading="loading_add" @click="handleAdd" type="primary" icon="el-icon-edit">{{$t('table.add')}}</el-button>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" border fit highlight-current-row
      style="width: 100%;min-height:500px;">
      <el-table-column  min-width="60px"align="center" :label="$t('table.No')">
        <template slot-scope="scope">
          <span>{{scope.row.No}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="50px" align="center" :label="$t('table.id')" v-if="checkLevel('super')">
        <template slot-scope="scope">
          <span>{{scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" :label="$t('name')">
        <template slot-scope="scope">
          <span>{{scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" align="center" :label="$t('phone')" v-if="checkLevel('super')">
        <template slot-scope="scope">
          <span>{{scope.row.phone}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" align="center" :label="$t('region')" v-if="checkLevel('super')">
        <template slot-scope="scope">
          <span>{{scope.row.region}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" align="center" :label="$t('remarks')" v-if="checkLevel('super')">
        <template slot-scope="scope">
          <span>{{scope.row.remarks}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('actions')" min-width="250" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button round type="primary" size="mini" :loading="scope.row.update_loading" @click="handleUpdate(scope.row)">{{$t('edit')}}</el-button>

          <el-popover v-if="!scope.row.deleted" placement="top" width="160" v-model="scope.row.visible_deleted">
            <p>确定要删除么？</p>
            <div style="text-align: right; margin: 0">
              <el-button round size="mini" type="text" @click="scope.row.visible_deleted = false">取消</el-button>
              <el-button round type="primary" size="mini" @click="handleDel(scope.row)" >确定</el-button>
            </div>
            <el-button round slot="reference" size="mini" type="danger" @click="scope.row.visible_deleted = true">{{$t('del')}}</el-button>
          </el-popover>

          <el-popover v-else placement="top" width="160" v-model="scope.row.visible_recover">
            <p>确定要恢复么？</p>
            <div style="text-align: right; margin: 0">
              <el-button round size="mini" type="text" @click="scope.row.visible_recover = false">取消</el-button>
              <el-button round type="primary" size="mini" @click="handleRecover(scope.row)" >确定</el-button>
            </div>
            <el-button round slot="reference" size="mini" type="success" @click="scope.row.visible_recover = true">{{$t('recover')}}</el-button>
          </el-popover>

          <el-popover placement="top" width="160" v-model="scope.row.visible_readDel">
            <p>确定要物理删除么？</p>
            <div style="text-align: right; margin: 0">
              <el-button round size="mini" type="text" @click="scope.row.visible_readDel = false">取消</el-button>
              <el-button round type="primary" size="mini" @click="handleReadDel(scope.row)" >确定</el-button>
            </div>
            <el-button round slot="reference" type="info" size="small" @click="scope.row.visible_readDel = true">{{$t('readDel')}}</el-button>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handlePageChange" :current-page="listQuery.page" :page-sizes="[10,20,30, 50]" :page-size="listQuery.limit" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <el-dialog :title="$t(dialogTitle)" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :model="temp" label-position="left" label-width="70px" style='width: 400px; margin-left:50px;'>
        <el-form-item :label="$t('id')" v-show="false">
          <el-input v-model="temp.id"></el-input>
        </el-form-item>
        <el-form-item :label="$t('name')" :rules="rules.name">
          <el-input v-model="temp.name"></el-input>
        </el-form-item>
        <el-form-item :label="$t('phone')" >
          <el-input v-model="temp.phone"></el-input>
        </el-form-item>
        <el-form-item :label="$t('region')" >
          <el-input v-model="temp.region"></el-input>
        </el-form-item>
        <el-form-item :label="$t('remarks')">
          <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="备注信息" v-model="temp.remarks">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button round v-if="dialogType === 'add'" type="primary" :loading="loading_addData" @click="addData">{{$t('confirm')}}</el-button>
        <el-button round v-else="dialogType === 'update'" type="primary" :loading="loading_updateData" @click="updateData">{{$t('confirm')}}</el-button>
        <el-button round @click="cleanDialog">{{$t('cancel')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getList, add, update, del, realDel } from '@/api/supermarket/supplier'
import waves from '@/directive/waves' // 水波纹指令
import { parseTime } from '@/utils'
import store from '@/store'

export default {
  // name: this.$t('supplier'),
  directives: {
    waves
  },
  data() {
    return {
      loading_add: false,
      loading_addData: false,
      loading_updateData: false,
      loading_delData: false,
      loading_recoverData: false,
      loading_readDelData: false,
      tableKey: 0,
      list: null,
      total: null,
      listLoading: false,
      listQuery: {
        page: 1,
        limit: 10,
        like: undefined
      },
      temp: {
        id: '',
        name: '',
        phone: '',
        region: '',
        remarks: ''
      },
      dialogFormVisible: false,
      dialogType: '',
      dialogTitle: '',
      rules: {
        name: [{ required: true, message: this.$t('required'), trigger: 'change' }]
      },
      fullscreenLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    resetTemp() {
      this.temp = {
        id: '',
        name: '',
        phone: '',
        region: '',
        remarks: ''
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
              this.list.unshift(this.temp)
              this.$notify({
                title: '成功',
                message: '创建成功',
                type: 'success',
                duration: 2000
              })
              this.cleanDialog()
              this.$refs['dataForm'].clearValidate()
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
      this.notifyClicking(this.update_loading, () => {
        this.update_loading = true
        this.temp = Object.assign({}, row) // copy obj
        this.dialogType = 'update'
        this.dialogFormVisible = true
        this.dialogTitle = 'update'
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
        this.update_loading = false
      })
    },
    updateData() {
      this.notifyClicking(this.loading_updateData, () => {
        this.loading_updateData = true
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
              this.$notify({
                title: '成功',
                message: '更新成功',
                type: 'success',
                duration: 2000
              })
              this.cleanDialog()
              this.$refs['dataForm'].clearValidate()
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
    handleDel(row) {
      this.notifyClicking(this.loading_delData, () => {
        this.loading_delData = true
        row.visible_deleted = false
        del(row.id, true).then(() => {
          const temp = Object.assign({}, row) // copy obj
          const index = this.list.indexOf(row)
          temp.deleted = true
          this.list.splice(index, 1, temp)
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
          this.loading_delData = false
        }).catch(() => {
          this.loading_delData = false
        })
      })
    },
    handleRecover(row) {
      this.notifyClicking(this.loading_recoverData, () => {
        this.loading_recoverData = true
        row.visible_recover = false
        del(row.id, false).then(() => {
          const temp = Object.assign({}, row) // copy obj
          const index = this.list.indexOf(row)
          temp.deleted = false
          this.list.splice(index, 1, temp)
          this.$notify({
            title: '成功',
            message: '恢复成功',
            type: 'success',
            duration: 2000
          })
          this.loading_recoverData = false
        }).catch(() => {
          this.loading_recoverData = false
        })
      })
    },
    handleReadDel(row) {
      this.notifyClicking(this.loading_readDelData, () => {
        this.loading_readDelData = true
        row.visible_readDel = false
        realDel(row.id).then(() => {
          const index = this.list.indexOf(row)
          this.list.splice(index, 1)
          this.$notify({
            title: '成功',
            message: '物理删除成功',
            type: 'success',
            duration: 2000
          })
          this.loading_readDelData = false
        }).catch(() => {
          this.loading_readDelData = false
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
    checkLevel(level) {
      let fg = store.getters.level === level
      fg = true
      return fg
    }
  }
}
</script>

<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input @keyup.enter.native="handleSearch" style="min-width: 200px;" class="filter-item" v-model="listQuery.like" placeholder="输入关键词搜索">
      </el-input>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleSearch">{{$t('table.search')}}</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleAdd" type="primary" icon="el-icon-edit">{{$t('table.add')}}</el-button>
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
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">{{$t('edit')}}</el-button>
          <el-button v-if="!scope.row.deleted" size="mini" type="danger" @click="handleDel(scope.row, scope.row.deleted)">{{$t('del')}}
          </el-button>
          <el-button v-else size="mini" type="success" @click="handleDel(scope.row, !scope.row.deleted)">{{$t('able')}}
          </el-button>
          <el-button type="info" size="small" @click="handleReadDel(scope.row)">{{$t('readDel')}}</el-button>
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
        <el-button v-if="dialogType === 'add'" type="primary" @click="addData">{{$t('confirm')}}</el-button>
        <el-button v-else="dialogType === 'update'" type="primary" @click="updateData">{{$t('confirm')}}</el-button>
        <el-button @click="cleanDialog">{{$t('cancel')}}</el-button>
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
      clicking: false,
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
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
    notifyClicking(callBack) {
      if (this.clicking) {
        this.$notify({
          title: '警告',
          message: '重复操作了',
          type: 'warning',
          duration: 1000
        })
      } else {
        this.clicking = !this.clicking
        callBack()
      }
    },
    getList() {
      this.notifyClicking(() => {
        this.listLoading = true
        getList(this.listQuery).then(data => {
          this.list = data.list
          this.total = data.total

          // Just to simulate the time of the request
          setTimeout(() => {
            this.listLoading = false
            this.clicking = !this.clicking
          }, 0.5 * 1000)
        }).catch(() => {
          this.clicking = !this.clicking
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
      this.notifyClicking(() => {
        this.resetTemp()
        this.dialogType = 'add'
        this.dialogFormVisible = true
        this.dialogTitle = 'add'
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
        this.clicking = !this.clicking
      })
    },
    addData() {
      this.notifyClicking(() => {
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
              this.clicking = !this.clicking
            }).catch(() => {
              this.clicking = !this.clicking
            })
          } else {
            this.clicking = !this.clicking
          }
        })
      })
    },
    handleUpdate(row) {
      this.notifyClicking(() => {
        this.temp = Object.assign({}, row) // copy obj
        this.dialogType = 'update'
        this.dialogFormVisible = true
        this.dialogTitle = 'update'
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
        this.clicking = !this.clicking
      })
    },
    updateData() {
      this.notifyClicking(() => {
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
              this.clicking = !this.clicking
            }).catch(() => {
              this.clicking = !this.clicking
            })
          } else {
            this.clicking = !this.clicking
          }
        })
      })
    },
    handleUpdateStatus(row, status) {
      this.notifyClicking(() => {
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
          this.$notify({
            title: '成功',
            message: '更新成功',
            type: 'success',
            duration: 2000
          })
          this.cleanDialog()
          this.clicking = !this.clicking
        }).catch(() => {
          this.clicking = !this.clicking
        })
      })
    },
    handleDel(row, deleted) {
      this.notifyClicking(() => {
        del(row.id, deleted).then(() => {
          const index = this.list.indexOf(row)
          this.list.splice(index, 1)
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
          this.clicking = !this.clicking
        }).catch(() => {
          this.clicking = !this.clicking
        })
      })
    },
    handleReadDel(row) {
      this.notifyClicking(() => {
        realDel(row.id).then(() => {
          const index = this.list.indexOf(row)
          this.list.splice(index, 1)
          this.$notify({
            title: '成功',
            message: '物理删除成功',
            type: 'success',
            duration: 2000
          })
          this.clicking = !this.clicking
        }).catch(() => {
          this.clicking = !this.clicking
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

<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input @keyup.enter.native="search" style="width: 200px;" class="filter-item" v-model="table.query.name" :placeholder="$t('name')">
      </el-input>
      <el-select @change="search" filterable multiple clearable class="filter-item" style="width: 300px" v-model="table.query.lableIds" :placeholder="$t('lable')">
        <el-option v-for="item in lableList" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
    </div>
    <div class="filter-container">
      <el-button round class="filter-item" type="primary" icon="el-icon-search" @click="search">{{$t('search')}}</el-button>
      <el-button round class="filter-item" style="margin-left: 10px;" :loading="loading_add" @click="clickAdd" type="primary" icon="el-icon-edit">{{$t('add')}}</el-button>
    </div>

    <el-table :key='table.key' :data="table.list" v-loading="table.loading" border fit highlight-current-row
              style="width: 100%;min-height:500px;">
      <el-table-column  min-width="60px"align="center" :label="$t('No')">
        <template slot-scope="scope">
          <span>{{scope.row.No}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="70px" align="center" :label="$t('id')">
        <template slot-scope="scope">
          <span>{{scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="150px" :label="$t('name')">
        <template slot-scope="scope">
          <span>{{scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="70px" :label="$t('price')">
        <template slot-scope="scope">
          <span>{{scope.row.price }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="70px" :label="$t('unit')">
        <template slot-scope="scope">
          <span>{{scope.row.unitName }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="300px" class-name="status-col" :label="$t('lable')" >
        <template slot-scope="scope">
          <el-tag
            v-for="tag in scope.row.lableList"
            :key="tag.id"
            closable
            :disable-transitions="false"
            @close="handleCloseTag(scope.row, tag)">
            {{ tag.name }}
          </el-tag>
          <el-input
            class="input-new-tag"
            v-if="scope.row.inputTagVisible"
            v-model="scope.row.inputTagValue"
            size="small"
            @keyup.enter.native="handleInputTagConfirm(scope.row)"
            @blur="handleInputTagConfirm(scope.row)"
          >
          </el-input>
          <el-button v-else class="button-new-tag" size="small" @click="showInputTag(scope.row)">+</el-button>
        </template>
      </el-table-column>
      <!--表数据固定字段信息 start-->
      <el-table-column min-width="170px" class-name="status-col" :label="$t('insertTime')" >
        <template slot-scope="scope">
          <span>{{scope.row.insertTime | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="170px" class-name="status-col" :label="$t('updateTime')">
        <template slot-scope="scope">
          <span>{{scope.row.updateTime | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
        </template>
      </el-table-column>
      <!--表数据固定字段信息 end-->
      <el-table-column align="center" :label="$t('actions')" min-width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!--固定操作功能 start-->
          <template v-if="scope.row.waiting_for_flush">
            <span>{{ $t('waiting_for_flush') }}</span>
          </template>
          <template v-else>
            <el-button round type="primary" size="mini" :loading="scope.row.loading_update" @click="clickUpdate(scope.row)">{{$t('edit')}}</el-button>
            <el-popover placement="top" width="160" v-model="scope.row.visible_del">
              <p>确定要删除么？</p>
              <div style="text-align: right; margin: 0">
                <el-button round size="mini" type="text" @click="scope.row.visible_del = false">取消</el-button>
                <el-button round type="primary" size="mini" @click="delData(scope.row)" >确定</el-button>
              </div>
              <el-button round slot="reference" type="info" size="small" :loading="scope.row.loading_del" @click="clickDel(scope.row)">{{$t('del')}}</el-button>
            </el-popover>
          </template>
          <!--固定操作功能 end-->
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination background @size-change="sizeChange" @current-change="pageChange"
                     :current-page="table.query.page"
                     :page-size="table.query.limit"
                     :page-sizes="table.pagesizes"
                     :total="table.total"
                     layout="total, sizes, prev, pager, next, jumper" >
      </el-pagination>
    </div>

    <!--固定弹出层 start-->
    <el-dialog :title="$t(dialog.title)" :visible.sync="dialog.visible" :before-close='dialogCancel'>
      <el-form ref="form" :model="temp" :rules="dialog.rules" label-position="left" label-width="120px" style='width: 400px; margin-left:50px;'>
        <el-form-item :label="$t('id')" prop="id" v-show="false">
          <el-input v-model="temp.id" ></el-input>
        </el-form-item>
        <el-form-item :label="$t('name')" prop="name">
          <el-input v-model="temp.name"></el-input>
        </el-form-item>
        <el-form-item :label="$t('price')" prop="price">
          <el-input type="number" v-model="temp.price"></el-input>
        </el-form-item>
        <el-form-item :label="$t('unit')" prop="unitId">
          <el-select class="filter-item" v-model="temp.unitId">
            <el-option v-for="item in unitList" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button round type="primary" :loading="dialog.loading_confirm" @click="dialogConfirm">{{$t('confirm')}}</el-button>
        <el-button round @click="dialogCancel">{{$t('cancel')}}</el-button>
      </div>
    </el-dialog>
    <!--固定弹出层 end-->
  </div>
</template>
<style>
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
</style>
<script>
  import { getList, get, add, update, del, addLable, delLable, getGoodsLableList } from '@/api/jxc/goods'
  import { notifyClicking, cacheGet, copy } from '@/utils/myUtil'
  import { getList as getUnitList } from '@/api/admin/unit'

  export default {
    name: 'goods',
    data() {
      return {
        unitList: undefined,
        lableList: undefined,
        /* 固定功能字段 start */
        loading_add: false,
        table: {
          key: 0,
          list: null,
          total: null,
          loading: false,
          pagesizes: [10, 20, 30, 50],
          query: {
            page: 1,
            limit: 10
          }
        },
        dialog: undefined,
        temp: undefined
        /* 固定功能字段 end */
      }
    },
    created() {
      this.resetTemp()
      this.resetDialog()
      this.getList()
      getGoodsLableList().then((data) => {
        this.lableList = data.list
      }).catch(() => {
        this.lableList = []
        this.$notify({
          title: '失败',
          message: '获取标签信息失败',
          type: 'error',
          duration: 2000
        })
      })
    },
    methods: {
      handleCloseTag(row, tag) {
        // 删除 请求后台
        delLable({ goodsId: row.id, lableId: tag.id }).then(() => {
          row.lableList.splice(row.lableList.indexOf(tag), 1)
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
        }).catch(() => {
          this.$notify({
            title: '失败',
            message: '删除失败',
            type: 'error',
            duration: 2000
          })
        })
      },
      showInputTag(row) {
        row.inputTagVisible = true
        row.inputTagValue = ''
        cacheGet(this.table.list, copy(row), 'replace')
      },
      handleInputTagConfirm(row) {
        // 新增 请求后台
        const inputTagValue = row.inputTagValue
        if (inputTagValue) {
          addLable({ goodsId: row.id, lableName: inputTagValue }).then(data => {
            row.lableList.push(data.vo)
            row.inputTagVisible = false
            row.inputTagValue = ''
            cacheGet(this.table.list, copy(row), 'replace')
            this.formaterList(this.table.list)
            this.$notify({
              title: '成功',
              message: '创建成功',
              type: 'success',
              duration: 2000
            })
          }).catch(() => {
            row.inputTagVisible = false
            row.inputTagValue = ''
            cacheGet(this.table.list, copy(row), 'replace')
            this.formaterList(this.table.list)
          })
        } else {
          row.inputTagVisible = false
          row.inputTagValue = ''
          cacheGet(this.table.list, copy(row), 'replace')
          this.formaterList(this.table.list)
        }
      },
      /* 固定功能方法 start */
      resetDialog() {
        const validatePrice = (rule, value, callback) => {
          if (!value) {
            callback(new Error('必填'))
          } else if (value <= 0) {
            callback(new Error('值必须大于0'))
          } else {
            callback()
          }
        }
        this.dialog = {
          visible: false,
          loading_confirm: false,
          type: '',
          title: '',
          rules: {
            id: [
              { required: true, message: this.$t('required'), trigger: 'blur' }
            ],
            name: [
              { required: true, message: this.$t('required'), trigger: 'blur' }
            ],
            price: [
              { required: true, trigger: 'blur', validator: validatePrice }
            ],
            unitId: [
              { required: true, message: this.$t('required'), trigger: 'blur' }
            ]
          }
        }
        getUnitList({ page: 1, limit: -1, type: 'PRICE' }).then((data) => {
          this.unitList = data.list
        })
      },
      resetTemp() {
        this.temp = {
          id: '',
          name: ''
        }
      },
      getList() {
        notifyClicking(this.table.loading, () => {
          this.table.loading = true
          getList(this.table.query).then(data => {
            this.table.list = data.list
            this.table.total = data.total
            this.formaterList(this.table.list)
            this.table.loading = false
          }).catch(() => {
            this.table.loading = false
          })
        })
      },
      search() {
        this.table.query.page = 1
        this.getList()
      },
      sizeChange(val) {
        this.table.query.page = 1
        this.table.query.limit = val
        this.getList()
      },
      pageChange(val) {
        this.table.query.page = val
        this.getList()
      },
      dialogConfirm() {
        notifyClicking(this.dialog.loading_confirm, () => {
          this.dialog.loading_confirm = true
          if (this.dialog.type === 'add') {
            this.addData()
          } else {
            this.updateData()
          }
        })
      },
      dialogCancel() {
        this.resetTemp()
        this.resetDialog()
      },
      addData() {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            add(this.temp).then(() => {
              this.temp.waiting_for_flush = true
              cacheGet(this.table.list, this.temp, 'add')
              this.$notify({
                title: '成功',
                message: '创建成功',
                type: 'success',
                duration: 2000
              })
              this.resetDialog()
              this.resetTemp()
              this.dialog.visible = false
              this.$nextTick(() => {
                this.$refs['form'].clearValidate()
              })
            }).catch(() => {
              this.dialog.loading_confirm = false
              this.$notify({
                title: '失败',
                message: '创建失败',
                type: 'error',
                duration: 2000
              })
            })
          } else {
            this.dialog.loading_confirm = false
          }
        })
      },
      updateData() {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            update(this.temp).then(() => {
              this.temp.waiting_for_flush = true
              cacheGet(this.table.list, this.temp, 'replace')
              this.$notify({
                title: '成功',
                message: '更新成功',
                type: 'success',
                duration: 2000
              })
              this.resetDialog()
              this.resetTemp()
              this.$nextTick(() => {
                this.$refs['form'].clearValidate()
              })
            }).catch(() => {
              this.dialog.loading_confirm = false
              this.$notify({
                title: '失败',
                message: '更新失败',
                type: 'error',
                duration: 2000
              })
            })
          } else {
            this.dialog.loading_confirm = false
          }
        })
      },
      clickAdd() {
        notifyClicking(this.loading_add, () => {
          this.loading_add = true
          this.resetTemp()
          this.resetDialog()
          this.dialog.type = 'add'
          this.dialog.title = 'add'
          this.dialog.rules.id[0].required = false
          this.loading_add = false
          this.dialog.visible = true
          this.$nextTick(() => {
            this.$refs['form'].clearValidate()
          })
        })
      },
      clickUpdate(row) {
        notifyClicking(row.loading_update, () => {
          row.loading_update = true
          this.resetTemp()
          this.resetDialog()
          this.dialog.type = 'update'
          this.dialog.title = 'update'
          this.dialog.rules.id[0].required = true
          cacheGet(this.table.list, copy(row), 'replace')
          get(row.id).then((data) => {
            this.temp = Object.assign({}, data.vo)
            this.dialog.visible = true
            row.loading_update = false
            cacheGet(this.table.list, copy(row), 'replace')
            this.$nextTick(() => {
              this.$refs['form'].clearValidate()
            })
          }).catch(() => {
            this.$notify({
              title: '失败',
              message: '获取信息失败',
              type: 'error',
              duration: 2000
            })
            row.loading_update = false
            cacheGet(this.table.list, copy(row), 'replace')
          })
        })
      },
      clickDel(row) {
        notifyClicking(row.loading_del, () => {
          row.loading_del = true
          row.visible_del = true
          cacheGet(this.table.list, copy(row), 'replace')
          row.loading_del = false
          cacheGet(this.table.list, copy(row), 'replace')
        })
      },
      delData(row) {
        notifyClicking(row.loading_del, () => {
          row.loading_del = true
          row.visible_del = false
          cacheGet(this.table.list, copy(row), 'replace')
          del(row.id).then(() => {
            row.loading_del = false
            cacheGet(this.table.list, row, 'remove')
            this.$notify({
              title: '成功',
              message: '删除成功',
              type: 'success',
              duration: 2000
            })
          }).catch(() => {
            row.loading_del = false
            cacheGet(this.table.list, copy(row), 'replace')
            this.$notify({
              title: '失败',
              message: '删除失败',
              type: 'error',
              duration: 2000
            })
          })
        })
      },
      formaterList(list) {
        let i = 1
        for (const v of list) { // 响应
          v.No = i++
          v.waiting_for_flush = false
          v.loading_update = false
          v.loading_del = false
          v.visible_del = false
        }
      }
      /* 固定功能方法 end */
    }
  }
</script>

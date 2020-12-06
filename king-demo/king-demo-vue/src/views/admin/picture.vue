<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input @keyup.enter.native="search" style="width: 200px;" class="filter-item" v-model="table.query.name" :placeholder="$t('name')">
      </el-input>
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
      <el-table-column min-width="130px" align="center" :label="$t('id')">
        <template slot-scope="scope">
          <span>{{scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" :label="$t('name')">
        <template slot-scope="scope">
          <span>{{scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" :label="$t('picture')">
        <template slot-scope="scope">
          <img :src="scope.row.img" width="40" height="40" class="head_pic"/>
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
    <el-dialog :title="$t(dialog.title)" :visible.sync="dialog.visible">
      <el-form ref="form" :model="temp" :rules="dialog.rules" label-position="left" label-width="120px" style='width: 400px; margin-left:50px;'>
        <el-form-item :label="$t('id')" prop="id" v-show="false">
          <el-input v-model="temp.id" ></el-input>
        </el-form-item>
        <el-form-item :label="$t('name')" prop="name">
          <el-input v-model="temp.name"></el-input>
        </el-form-item>
        <el-form-item>
          <el-upload v-if="dialog.title === 'add'"
            class="upload-demo"
            ref="upload_add"
            :action=upload_action_add
            list-type="picture"
            :data="temp"
            :on-success="submitSuccess"
            :on-error="submitError"
            :limit="upload.limit"
            :headers="upload.headers"
            :file-list=temp.fileList
            :auto-upload="false">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <div slot="tip" class="el-upload__tip">只能上传1张图片</div>
          </el-upload>
          <el-upload v-if="dialog.title === 'update'"
                     class="upload-demo"
                     ref="upload_update"
                     :action=upload_action_update
                     list-type="picture"
                     :data="temp"
                     :on-success="submitSuccess"
                     :on-error="submitError"
                     :limit="upload.limit"
                     :headers="upload.headers"
                     :file-list=upload.fileList
                     :auto-upload="false">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <div slot="tip" class="el-upload__tip">只能上传1张图片</div>
        </el-upload>
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

<script>
  import { getList, get, del } from '@/api/admin/picture'
  import { getToken } from '@/utils/auth'
  import { notifyClicking, cacheGet, getPictureUrl, copy } from '@/utils/myUtil'

  export default {
    name: 'picture_',
    data() {
      return {
        upload_action_add: process.env.BASE_API + '/admin/picture/add',
        upload_action_update: process.env.BASE_API + '/admin/picture/update',
        upload: undefined,
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
        dialog: {
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
            ]
          }
        },
        temp: undefined
        /* 固定功能字段 end */
      }
    },
    created() {
      this.resetTemp()
      this.resetDialog()
      this.getList()
    },
    methods: {
      submitUpload() {
        notifyClicking(this.dialog.loading_confirm, () => {
          this.dialog.loading_confirm = true
          this.$refs['form'].validate((valid) => {
            if (valid) {
              if (this.dialog.type === 'add') {
                this.$refs['upload_add'].submit()
              } else {
                this.$refs['upload_update'].submit()
              }
            }
          })
        })
      },
      submitSuccess() {
        let message = ''
        let row
        if (this.dialog.type === 'add') {
          row = cacheGet(this.table.list, this.temp, 'add')
          message = '创建成功'
        } else {
          row = cacheGet(this.table.list, this.temp, 'replace')
          message = '更新成功'
        }
        this.resetTemp()
        this.resetDialog()
        row.waiting_for_flush = true
        cacheGet(this.table.list, copy(row), 'replace')
        this.dialog.loading_confirm = false
        this.$nextTick(() => {
          this.$refs['form'].clearValidate()
        })
        this.$notify({
          title: '成功',
          message: message,
          type: 'success',
          duration: 2000
        })
      },
      submitError() {
        this.dialog.loading_confirm = false
        let message = ''
        if (this.dialog.type === 'add') {
          message = '创建失败'
        } else {
          message = '更新失败'
        }
        this.$notify({
          title: '失败',
          message: message,
          type: 'error',
          duration: 2000
        })
      },
      /* 固定功能方法 start */
      resetDialog() {
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
            ]
          }
        }
        this.upload = {
          headers: { 'auth_session_id': getToken().auth_session_id },
          limit: 1,
          fileList: []
        }
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
        this.submitUpload()
      },
      dialogCancel() {
        this.resetTemp()
        this.resetDialog()
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
            this.upload.fileList.push({ name: this.temp.name, url: getPictureUrl(this.temp.id) })
            this.dialog.visible = true
            row.loading_update = false
            cacheGet(this.table.list, copy(row), 'replace')
            this.$nextTick(() => {
              this.$refs['form'].clearValidate()
            })
          }).catch(() => {
            row.loading_update = false
            cacheGet(this.table.list, copy(row), 'replace')
            this.$notify({
              title: '失败',
              message: '获取信息失败',
              type: 'error',
              duration: 2000
            })
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
          v.img = getPictureUrl(v.id, v.name)
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

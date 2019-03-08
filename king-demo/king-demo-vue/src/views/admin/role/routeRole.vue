<template>
  <div class="app-container">

    <el-table :data="list" v-loading="listLoading" border fit highlight-current-row style="width: 100%;min-height:500px;">
      <el-table-column  min-width="60px"align="center" :label="$t('No')">
        <template slot-scope="scope">
          <span>{{scope.row.No}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="130px" align="center" :label="$t('id')" v-if="checkLevel('super')">
        <template slot-scope="scope">
          <span>{{scope.row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="100px" align="center" :label="$t('dataRole')">
        <template slot-scope="scope">
          <template v-if="scope.row.edit">
            <el-input v-model="scope.row.editName"></el-input>
          </template>
          <span v-else>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" class-name="status-col" :label="$t('table.user.remark')">
        <template slot-scope="scope">
          <span>{{scope.row.remark}}</span>
        </template>
      </el-table-column>
      <!--表数据固定字段信息 start-->
      <el-table-column min-width="170px" class-name="status-col" :label="$t('createTime')" v-if="checkLevel('super')">
        <template slot-scope="scope">
          <span>{{scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" class-name="status-col" :label="$t('createUser')" v-if="checkLevel('super')">
        <template slot-scope="scope">
          <span>{{scope.row.createUser}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="170px" class-name="status-col" :label="$t('lastUpdateTime')" v-if="checkLevel('super')">
        <template slot-scope="scope">
          <span>{{scope.row.lastUpdateTime | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" class-name="status-col" :label="$t('lastUpdateUser')" v-if="checkLevel('super')">
        <template slot-scope="scope">
          <span>{{scope.row.lastUpdateUser}}</span>
        </template>
      </el-table-column>
      <!--表数据固定字段信息 end-->

      <el-table-column align="center" :label="$t('table.actions')" width="230" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.edit" type="success" size="mini" @click="updateData(scope.row)" icon="el-icon-circle-check-outline">{{ $t('ok') }}</el-button>
          <el-button v-if="scope.row.edit" type="warning" size="mini" @click="cancelUpdate(scope.row)"icon="el-icon-refresh" >{{ $t('cancel') }}</el-button>
          <el-button v-if="!scope.row.edit" type="primary" size="mini" @click='handleUpdate(scope.row)' icon="el-icon-edit">{{ $t('edit') }}</el-button>
          <!--固定操作功能 start-->
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
          <!--固定操作功能 end-->
        </template>
      </el-table-column>

    </el-table>
  </div>
</template>

<script>
  import { getList } from '@/api/admin/role/routeRole'
  import store from '@/store'

  export default {
    name: 'routeRole',
    data() {
      return {
        list: null,
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 10
        }
      }
    },
    created() {
      // this.getList()
    },
    methods: {
      getList() {
        this.listLoading = true
        getList(this.listQuery).then(data => {
          const items = data.items
          this.list = items.map(v => {
            this.$set(v, 'edit', false) // https://vuejs.org/v2/guide/reactivity.html
            return v
          })
          this.listLoading = false
        })
      },
      cancelUpdate(row) {
        row.edit = false
        this.$message({
          message: '已取消修改',
          type: 'warning'
        })
      },
      handleUpdate(row) {
        row.edit = true
        this.setEditData(row)
      },
      updateData(row) {
        row.edit = false
        this.updateEditData(row)
        this.$message({
          message: '修改成功',
          type: 'success'
        })
      },
      setEditData(row) {
        row.editName = row.name
      },
      updateEditData(row) {
        row.name = row.editName
      },
      checkLevel(level) {
        let fg = store.getters.level === level
        fg = true
        return fg
      }
    }
  }
</script>


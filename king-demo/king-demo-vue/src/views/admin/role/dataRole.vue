<template>
  <div class="app-container">

    <el-table :data="list" v-loading="listLoading" border fit highlight-current-row style="width: 100%;min-height:500px;">
      <el-table-column  min-width="60px"align="center" :label="$t('table.No')">
        <template slot-scope="scope">
          <span>{{scope.row.No}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('id')" min-width="80">
        <template slot-scope="scope">
          <span>{{scope.row.id}}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="100px" align="center" :label="$t('role.dataRole.name')">
        <template slot-scope="scope">
          <template v-if="scope.row.edit">
            <el-input v-model="scope.row.editName"></el-input>
          </template>
          <span v-else>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="170px" class-name="status-col" :label="$t('table.user.createTime')" v-if="checkLevel('super')">
        <template slot-scope="scope">
          <span>{{scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" class-name="status-col" :label="$t('table.user.createUser')" v-if="checkLevel('super')">
        <template slot-scope="scope">
          <span>{{scope.row.createUser}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" class-name="status-col" :label="$t('table.user.remark')">
        <template slot-scope="scope">
          <span>{{scope.row.remark}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" :label="$t('table.actions')" width="230" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.edit" type="success" size="mini" @click="updateData(scope.row)" icon="el-icon-circle-check-outline">{{ $t('btn.ok') }}</el-button>
          <el-button v-if="scope.row.edit" type="warning" size="mini" @click="cancelUpdate(scope.row)"icon="el-icon-refresh" >{{ $t('btn.cancel') }}</el-button>
          <el-button v-if="!scope.row.edit" type="primary" size="mini" @click='handleUpdate(scope.row)' icon="el-icon-edit">{{ $t('btn.edit') }}</el-button>
          <el-button v-if="!scope.row.edit" type="danger" size="mini" @click="handleDelete(scope.row)" icon="el-icon-delete">{{$t('btn.delete')}}</el-button>
        </template>
      </el-table-column>

    </el-table>
  </div>
</template>

<script>
  import { getList } from '@/api/admin/role'
  import store from '@/store'

  export default {
    name: 'dataRole',
    data() {
      return {
        list: null,
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 5
        }
      }
    },
    created() {
      this.getList()
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


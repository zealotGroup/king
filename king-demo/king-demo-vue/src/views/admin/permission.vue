<template>
  <div class="tab-container">
    <el-tabs style='margin-top:15px;' v-model="viewName" type="border-card">
      <el-tab-pane v-for="item in tabMapOptions" :label="$t(item.label)" :key='item.key' :name="item.key">
        <permissionData v-show="viewName === 'permissionData'"></permissionData>
        <permissionRoute v-show="viewName === 'permissionRoute'"></permissionRoute>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import permissionData from './permission/permissionData'
  import permissionRoute from './permission/permissionRoute'

  export default {
    name: 'permission',
    components: { 'permissionData': permissionData, 'permissionRoute': permissionRoute },
    data() {
      return {
        tabMapOptions: [
          { label: 'permissionData', key: 'permissionData' },
          { label: 'permissionRoute', key: 'permissionRoute' }
        ],
        viewName: 'permissionData'
      }
    }
  }
</script>

<style scoped>
  .tab-container{
    margin: 30px;
  }
</style>

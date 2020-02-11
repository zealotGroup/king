<template>
  <div class="tab-container">
    <el-tabs style='margin-top:15px;' v-model="viewName" type="border-card">
      <el-tab-pane v-for="item in tabMapOptions" :label="$t(item.label)" :key='item.key' :name="item.key">
        <dataPermission v-show="viewName === 'dataPermission'"></dataPermission>
        <routePermission v-show="viewName === 'routePermission'"></routePermission>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import dataPermission from './permission/dataPermission'
  import routePermission from './permission/routePermission'

  export default {
    name: 'tab',
    components: { 'dataPermission': dataPermission, 'routePermission': routePermission },
    data() {
      return {
        tabMapOptions: [
          { label: 'dataPermission', key: 'dataPermission' },
          { label: 'routePermission', key: 'routePermission' }
        ],
        viewName: 'dataPermission'
      }
    }
  }
</script>

<style scoped>
  .tab-container{
    margin: 30px;
  }
</style>

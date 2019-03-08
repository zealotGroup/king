<template>
  <div class="tab-container">
    <el-tabs style='margin-top:15px;' v-model="viewName" type="border-card">
      <el-tab-pane v-for="item in tabMapOptions" :label="$t(item.label)" :key='item.key' :name="item.key">
        <dataRole v-show="viewName === 'dataRole'"></dataRole>
        <routeRole v-show="viewName === 'routeRole'"></routeRole>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import dataRole from './role/dataRole'
  import routeRole from './role/routeRole'

  export default {
    name: 'tab',
    components: { 'dataRole': dataRole, 'routeRole': routeRole },
    data() {
      return {
        tabMapOptions: [
          { label: 'dataRole', key: 'dataRole' },
          { label: 'routeRole', key: 'routeRole' }
        ],
        viewName: 'dataRole'
      }
    },
    methods: {
    }
  }
</script>

<style scoped>
  .tab-container{
    margin: 30px;
  }
</style>

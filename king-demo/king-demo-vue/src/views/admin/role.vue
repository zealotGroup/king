<template>
  <div class="tab-container">
    <el-tabs style='margin-top:15px;' v-model="viewName" type="border-card">
      <el-tab-pane v-for="item in tabMapOptions" :label="$t(item.label)" :key='item.key' :name="item.key">
        <roleData v-show="viewName === 'roleData'"></roleData>
        <roleRoute v-show="viewName === 'roleRoute'"></roleRoute>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import roleData from './role/roleData'
  import roleRoute from './role/roleRoute'

  export default {
    name: 'role',
    components: { 'roleData': roleData, 'roleRoute': roleRoute },
    data() {
      return {
        tabMapOptions: [
          { label: 'roleData', key: 'roleData' },
          { label: 'roleRoute', key: 'roleRoute' }
        ],
        viewName: 'roleData'
      }
    },
    methods: {
      formaterTree(tree) {
        console.error(2)
        tree.name = this.$t('index')
        tree.children.forEach(function(item) {
          this.formaterTree(item)
        })
      }
    }
  }
</script>

<style scoped>
  .tab-container{
    margin: 30px;
  }
</style>

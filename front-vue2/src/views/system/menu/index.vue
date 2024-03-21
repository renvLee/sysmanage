<template>
  <div class="menu-management">
    <!-- 搜索区域 -->
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>菜单搜索</span>
      </div>
      <el-form :model="searchForm" label-width="80px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="菜单名称:">
              <el-input v-model="searchForm.name" placeholder="请输入菜单名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="状态:">
              <el-select v-model="searchForm.status" placeholder="请选择状态">
                <el-option label="启用" value="0"></el-option>
                <el-option label="禁用" value="1"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-button type="primary" @click="fetchData">查询</el-button>
            <el-button @click="resetForm()">重置</el-button>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
    <!-- 菜单列表 -->
    <div>
      <menu-table :menus="menuList" ></menu-table>
    </div>

  </div>
</template>
<script>
import MenuTable from '@/layout/component/menu/MenuTable.vue'
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import store from "@/store";
import {queryMenu} from "@/api/menu/menu";
import {initMenu} from "@/utils/menu";

export default {
  name: 'MenuManagement',
  components: {
    Treeselect,
    MenuTable,
  },
  data() {
    return {
      searchForm: {
        name: '',
        status: '0',
      },
      menuList: [], // 菜单列表数据
    };
  },

  methods: {
    // 获取菜单数据
    fetchData() {
      new Promise((resolve, reject) => {
        queryMenu(this.searchForm).then(response => {
          // 处理成功的情况
          console.log(response)
          this.menuList=response.data.menus
          resolve(response); // 使用获取到的响应数据解析外部Promise
        }).catch(error => {
          // 处理失败的情况
          reject(error); // 使用错误信息拒绝外部Promise
        });
      });

    },
    // 状态格式化显示
    statusFormatter(row, column, value, index) {
      return value === '0' ? '启用' : '禁用';
    },
    resetForm(){
      this.searchForm = {
        name: '',
        status: '0',
        // 重置其他字段到初始值...
      };
    }
  },
  created() {
    this.fetchData();
  },
  mounted() {
    initMenu(store).then(()=>{
      this.menuList = this.$store.state.menuList;
    })
  },
}
</script>

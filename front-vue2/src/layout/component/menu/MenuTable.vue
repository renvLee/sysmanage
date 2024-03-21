<template>
  <div>
    <el-table
        :data="menus"
        style="width: 100%"
        @row-click="handleRowClick"
        row-key="id"
    >
      <el-table-column
          prop="menuName"
          label="名称"
          width="180"
      ></el-table-column>
      <el-table-column
          prop="id"
          label="ID"
          width="180"
      ></el-table-column>
      <el-table-column
          prop="perms"
          label="访问权限"
          width="280"
      ></el-table-column>
      <el-table-column
          prop="path"
          label="菜单路径"
          width="180"
      ></el-table-column>
      <el-table-column
          prop="menuType"
          label="类型"
          width="180"
      >
        <template slot-scope="scope">
          <!-- 在这里添加判断逻辑 -->
          <span>{{ getMenuTypeText(scope.row.menuType) }}</span>
        </template>
      </el-table-column>
      <el-table-column
          prop="remark"
          label="备注"
          width="280"
      ></el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="mini" @click.stop="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click.stop="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>

    </el-table>

    <!--添加或修改菜单对话框-->
    <el-dialog :visible.sync="dialogVisible" title="添加/编辑菜单" width="25%">
      <el-form :model="form" label-width="100px">
        <el-form-item label="编辑类型:">
          <el-select v-model="editType" placeholder="请选择" @change="editTypeChange">
            <el-option label="修改" value="修改"></el-option>
            <el-option label="新增" value="新增"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="上级菜单">
          <treeselect
              v-model="selectedParent"
              :options="menuOptions"
              placeholder="请选择上级菜单"
              clearable
              @input="selectParentId"
          />
        </el-form-item>
        <el-form-item label="菜单名称">
          <el-input v-model="form.menuName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="菜单类型">
          <el-select v-model="form.menuType" placeholder="请选择">
            <el-option label="目录" value="m"></el-option>
            <el-option label="菜单" value="c"></el-option>
            <el-option label="按钮" value="f"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="组件路径" v-if="form.menuType === 'c'">
          <el-input v-model="form.component" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="访问路径" v-if="form.menuType === 'c'">
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="访问权限">
          <el-select v-model="form.perms" placeholder="请选择">
            <el-option
                v-for="type in $store.state.userInfo.permissions"
                :key="type"
                :label="type"
            :value="type">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="菜单图标" v-if="form.menuType !== 'f'">
          <el-input v-model="form.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="dialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import {messageInfo} from "@/utils/MessageInfo";
import {addMenu, updateMenu,deleteMenu} from "@/api/menu/menu";
import store from "@/store";
import {convertMenuListToOptions, findMenuItemById} from "@/utils/menu";

export default {
  name: 'MenuTable',
  components: {
    Treeselect,
  },
  props: {
    menus: Array,
  },
  data() {
    return {
      selectedParent: null,
      pastForm:{},
      editType: '修改',
      menuOptions: {},
      expandedRows: [],
      dialogVisible: false, // 控制对话框显示
      form: {
        icon: '',
        component: '',
        id: '',
        menuName: '',
        menuType: '',
        parentId: '',
        path: '',
        perms: '',
        status: '',
        remark:''
      },
      selectedParentMenu:{}
    };
  },
  methods: {
    selectParentId(parentID){
      let menuLists=store.state.menuList
      this.selectedParentMenu=findMenuItemById(menuLists,parentID)
      console.log(this.selectedParentMenu)
    },
    handleRowClick(row, column, event) {
      const rowIndex = this.expandedRows.indexOf(row.id);
      if (rowIndex === -1) {
        this.expandedRows.push(row.id);
      } else {
        this.expandedRows.splice(rowIndex, 1);
      }
      this.$emit('update:expand', this.expandedRows);
    },
    updateExpand(rows) {
      this.expandedRows = rows;
    },
    editTypeChange() {
      this.form.component = ''
      this.form.menuName = ''
      this.form.menuType = ''
      this.form.parentId = ''
      this.form.path = ''
      this.form.perms = ''
      this.form.status = '0'
      this.form.remark = ''
      if (this.editType === '修改') this.handleEdit(this.pastForm)
    },
    handleEdit(menu) {
      this.dialogVisible = true;
      //备份表单数据
      this.pastForm=menu
      // 编辑菜单逻辑
      if (this.editType === '修改') {
        this.form.icon = menu.icon//下拉框
        this.form.component = menu.component
        this.form.id = menu.id
        this.form.menuName = menu.menuName
        this.form.menuType = menu.menuType
        this.form.parentId = menu.parentId
        this.form.path = menu.path
        this.form.perms = menu.perms//下拉框
        this.form.status = menu.status
        this.form.remark = menu.remark
      } else {
        this.editTypeChange()
      }
    },
    handleDelete(menu) {
      // 删除菜单逻辑
      deleteMenu(menu.id)
          .then(res => {
            if (res.status) {
              //更新菜单后，需要重新初始化菜单路由
              messageInfo({type:'success',message:res.message})
            } else {
              messageInfo({type: 'error', message: res.message});
            }
          })
    },
    // 重置搜索表单
    resetForm(formName) {
    },
    handleUpdate(){
      updateMenu(this.form)
          .then(res => {
            if (res.status) {
              //更新菜单后，需要重新初始化菜单路由
              this.pastForm=this.form
              messageInfo({type:'success',message:res.message})
            } else {
              messageInfo({type: 'error', message: res.message});
            }
          })
    },
    handleAdd(){
      const { id, ...rest } = this.form
      addMenu(rest)
      .then(res=>{
        if (res.status) {
          //更新菜单后，需要重新初始化菜单路由

          messageInfo({type:'success',message:res.message})
        } else {
          messageInfo({type: 'error', message: res.message});
        }
      })
    },
    // 提交表单
    submitForm() {
      // 在这里实现表单提交逻辑，例如调用API
      this.form.parentId = this.selectedParent
      if(this.editType==='修改'){
        this.handleUpdate()
        this.form=this.pastForm;
      }
      if(this.editType==='新增')this.handleAdd()
      // 清除表单，关闭对话框
      this.resetForm();
      this.dialogVisible = false;
    },
    // 获取菜单选项
    fetchMenuOptions() {
      this.menuOptions = convertMenuListToOptions(store.state.menuList);
    },
    getMenuTypeText(menuType) {
      switch (menuType) {
        case 'm':
          return '目录';
        case 'c':
          return '菜单';
        case 'f':
          return '按钮';
        default:
          return '未知类型';
      }
    },
  },
  created() {
    this.fetchMenuOptions();
  }
};
</script>

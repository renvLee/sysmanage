<template>
  <div class="main-container">
    <div class="logo-container">
      <logo :collapse="isCollapse"></logo>
    </div>
    <div class="menu-container"v-if="!isCollapse">
        <el-menu
            :collapse="isCollapse"
            :unique-opened="true"
            :default-active="this.$route.path"
            :collapse-transition="false"
            background-color="whitesmoke"
            text-color="black"
            active-text-color="#5a9cf8"
            router
        >
          <template v-for="menuLevel1 in this.menuList">
            <el-submenu style=" border-bottom: 1px solid #e0e0e0;"  :index="menuLevel1.id" v-if="menuLevel1.children && menuLevel1.children.length > 0">
              <span slot="title" > {{ menuLevel1.menuName }}</span>
              <span v-for="menuLevel2 in menuLevel1.children">
                <el-submenu :index="menuLevel2.id" v-if="menuLevel2.children && menuLevel2.children.length > 0">
                  <span slot="title">{{ menuLevel2.menuName }}</span>
                  <span v-for="menuLevel3 in menuLevel2.children">
                    <el-menu-item style=" border-bottom: 1px solid #e0e0e0;":index="menuLevel3.path">{{ menuLevel3.menuName }}</el-menu-item>
                  </span>
                </el-submenu>
                <el-menu-item class="menu_item_class" v-else :index="menuLevel2.path">{{ menuLevel2.menuName }}</el-menu-item>
              </span>
            </el-submenu>
            <el-menu-item class="menu_item_class" v-else :index="menuLevel1.path">{{ menuLevel1.menuName }}</el-menu-item>
          </template>
        </el-menu>
    </div>
  </div>
</template>

<script>
import Logo from "@/layout/component/sidebar/Logo";
import {mapState} from "vuex";

export default {
  name: "SideBar",
  components: {
    Logo,
  },
  data(){
    return {
    }
  },
  created() {
  },
  methods:{
  },
  computed: {
    ...mapState(["isCollapse", "menuList"]),
  },
};
</script>

<style scoped>
.menu-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.scrollbar-wrapper {
  height: 100%;
}
el-menu {
  background-color: whitesmoke;
  border-right: 1px solid #ccc; /* 可选：添加右边框线 */
}
.menu_item_class, el-submenu__title {
  font-size: 14px;
  color: #303133 !important;
  border-bottom: 1px solid #e0e0e0;
  padding: 10px 20px;
}

el-submenu {
  padding-left: 20px;
}
</style>
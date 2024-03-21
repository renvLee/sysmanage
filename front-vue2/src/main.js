import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from '@/store/index'


//=======================  CSS  ===========================
import 'animate.css';

//=======================  JS  ===========================

//=======================  Utils  ===========================

//=======================  UI/插件 ===========================
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import {addDRouter, generateChildRoutes, initMenu} from "@/utils/menu";

//=======================  全局变量  ===========================
Vue.config.productionTip = false
//=======================  Vue引用  ===========================
Vue.use(ElementUI);
//=======================  Vue  ===========================
let isRegister=false;
//=======================  路由守卫  ===========================
router.beforeEach(((to, from, next) => {
    let token=window.localStorage.getItem('token');
    if (token!=null) {
        console.log("路由守卫，token")
        if(!isRegister) {
            if(store.state.menuList.length===0){
                initMenu(store).then(menus=> {
                    isRegister=true
                    addDRouter();
                    router.push(to.path)
                    next(false);
                });
            }else {
                isRegister=true
                addDRouter();
                router.push(to.path)
                next(false);
            }
        }else {
            next();
        }
    } else {
        console.log("路由守卫，未检测到token")
        if (to.path === '/login') {
            next();
        } else {
            next('/login');
        }
    }
}));

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
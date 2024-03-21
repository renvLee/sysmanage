
import { messageInfo } from "@/utils/MessageInfo";
import {listMenu} from "@/api/menu/menu";
import store from '@/store/index'
import router from "@/router";

export const addDRouter = () =>{
    router.addRoute( {
        path: '/home',
        name: '首页',
        redirect: '/welcome',
        component:() => import(`@/layout/Home`) ,
        children: generateChildRoutes(),
    })
}
export const initMenu = (store) => {
    //获取菜单信息
    return new Promise((resolve, reject) =>{
        listMenu()
            .then(res => {
                if (res.status) {
                    //将路由信息存入store中
                    store.dispatch('setMenulist', res.data.menus);
                    resolve(res.data.menus)
                } else {
                    messageInfo({type:'error',message:res.message});
                    reject({type:'error',message:res.message})
                }
            }).catch(err=>reject(err))
    })
};
export const generateChildRoutes = () => {
    if( store.state.menuList.length<=0)
        initMenu(store)
    let childRoutes=[];
    childRoutes.push({
        path: '/welcome',
        name: '欢迎页',
        component: () => import(`@/views/welcome/Welcome`),
    })
    store.state.menuList.forEach(menu1=>{
        if(menu1.children.length>0){//二级结构
            menu1.children.forEach(menu2=>{
               if(menu2.children.length>0){//三级
                   menu2.children.forEach(menu3=>{
                   childRoutes.push({
                       path: '/'+menu3.path,
                       name: menu3.menuName,
                       component: () => import(`@/views/${menu3.component}`),
                   })
                   })
               }else{
                   childRoutes.push({
                       path: '/'+menu2.path,
                       name: menu2.menuName,
                       component: () => import(`@/views/${menu2.component}`),
                   })
               }
            })
        }else {//一级结构
            childRoutes.push({
                path: '/'+menu1.path,
                name: menu1.menuName,
                component: () => import(`@/views/${menu1.component}`),
            })
        }
    })
    return childRoutes
};

export const convertMenuListToOptions=(menuList)=> {
    return menuList.map(menuItem => {
        const option = {
            id: menuItem.id, // 假设后端数据中每个菜单项都有id和label字段
            label: menuItem.menuName,
        };
        // 如果当前菜单项有子菜单，递归调用此函数来转换子菜单
        if (menuItem.children && menuItem.children.length) {
            option.children = convertMenuListToOptions(menuItem.children);
        }
        return option;
    });
}

/**
 * 查找具有嵌套结构的，元素
 * @param menuList
 * @param targetId
 * @returns {null|*}
 */
export const findMenuItemById=(menuList, targetId)=> {
    for (const item of menuList) {
        if (item.id === targetId) {
            return item;
        } else if (item.children && item.children.length) {
            const foundInChildren = findMenuItemById(item.children, targetId);
            if (foundInChildren) {
                return foundInChildren;
            }
        }
    }
    return null; // 如果没有找到，返回null
}
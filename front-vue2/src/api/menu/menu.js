

// 查询菜单列表
import {
  dataPostRequest,
  dataPutRequest,
  deleteRequest,
  getRequest,

  getRequest2
} from "@/utils/request";



// 查询菜单列表
export function queryMenu(data) {
  console.log('/menu/query')
  return  getRequest('/menu/query',data)
}

// 查询菜单详细
export function getMenu(id) {
  let url='/system/menu/get?id='
  return getRequest2(`${url}${id}`)
}

// 新增菜单
export function addMenu(data) {
  return dataPostRequest('/menu/add',data)
}

// 修改菜单
export function updateMenu(data) {
  console.log('/menu/update')
  return dataPutRequest('/menu/update',data)
}

// 删除菜单
export function deleteMenu(id) {
  return deleteRequest(`/menu/delete/${id}`)
}

/**
 * 查询菜单路由信息
 * @param {*} url 接口路径
 * @returns  菜单路由信息
 */
export const listMenu = () => {
  console.log("/getRouters")
  return getRequest2("/getRouters");
};
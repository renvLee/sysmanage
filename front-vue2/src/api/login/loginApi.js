import {bodyPostRequest, getRequest, paramsPostRequest} from "@/utils/request";

/**
 *  用户登陆
 * @param url 登陆请求接口
 * @param params 参数
 * @returns {AxiosPromise}
 * @constructor
 */
export const Login = (bodies) => {
  return bodyPostRequest('/user/login',bodies);
};


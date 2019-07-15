// 路由的配置文件
import createRole from './page/create-role.vue'



export default [
    {path: '/', redirect: '/index'},
    {path: '/index', component: createRole}

]


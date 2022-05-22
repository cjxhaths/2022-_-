import Vue from 'vue'
import VueRouter from 'vue-router'
import showPage from '@/views/showPage'
Vue.use(VueRouter)
const routes = [
  {
    path: '/',
    redirect: '/showPage'
  },
  {
    path:'/showpage',
    component:showPage
  }
]

const router = new VueRouter({
  routes
})

export default router

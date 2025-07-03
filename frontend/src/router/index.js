import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import NewReport from '../views/Reports/New.vue'
import ReportSuccess from '../views/Reports/Success.vue'
import NotFound from '../views/NotFound.vue'
import About from '../views/About.vue'


const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/reports/new',
    name: 'NewReport',
    component: NewReport
  },
  {
    path: '/report-success',
    name: 'ReportSuccess',
    component: ReportSuccess
  },
  {
    path: '/about',
    name: 'About',
    component: About
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: NotFound
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router

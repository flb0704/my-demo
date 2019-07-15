<template>
  <div id="app">
    <div>
      <navigator/>
    </div>
    <div>
      <div><team/></div>
      <div><router-view></router-view></div>
    </div>
  </div>
</template>

<script>
import navigator from './components/navigator.vue'
import team from './components/team.vue'

export default {
  name: 'app',
  components: {
    navigator,
    team
  }
}
</script>



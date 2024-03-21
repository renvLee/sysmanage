<template>
  <div class="animate__animated animate__fadeIn">
   Welcome.vue
  </div>
</template>

<script>
import {mapState} from "vuex";

export default {
  data() {
    return {
      avatar: [],
    }
  },
  computed: {
    ...mapState(['userInfo'])
  },
  created() {
    this.avatar.push(this.userInfo.avatar);
  }
}
</script>

<style>

</style>
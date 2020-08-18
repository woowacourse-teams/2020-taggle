<template>
  <v-snackbar v-model="snackbar" :timeout="1000">
    <div class="text-center width-100 font-size-15">{{ snackbarMsg }}</div>
  </v-snackbar>
</template>

<script>
import { mapGetters } from 'vuex';
import { IS_SHOW, MESSAGE } from '@/store/share/gettersType.js';

export default {
  name: 'Snackbar',
  computed: {
    ...mapGetters([IS_SHOW, MESSAGE]),
  },
  watch: {
    [IS_SHOW]() {
      this.showSnackbar(this[MESSAGE]);
    },
  },
  methods: {
    showSnackbar(msg) {
      this.snackbarMsg = msg;
      this.snackbar = true;
    },
  },
  data() {
    return {
      snackbar: false,
      snackbarMsg: '',
    };
  },
};
</script>

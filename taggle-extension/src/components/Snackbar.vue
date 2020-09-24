<template>
  <v-snackbar v-model="snackbar" :timeout="1000">
    <div class="text-center width-100 font-size-15">{{ snackbarMsg }}</div>
  </v-snackbar>
</template>

<script>
import { mapGetters } from 'vuex';
import { IS_SHOWN_SNACKBAR, SNACKBAR_MESSAGE } from '@/store/share/getterTypes.js';

export default {
  name: 'Snackbar',
  computed: {
    ...mapGetters([IS_SHOWN_SNACKBAR, SNACKBAR_MESSAGE]),
  },
  watch: {
    [IS_SHOWN_SNACKBAR]() {
      this.showSnackbar(this[SNACKBAR_MESSAGE]);
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

<template>
  <v-dialog v-model="show" :max-width="options.width" :style="{ zIndex: options.zIndex }" @keydown.esc="cancel">
    <v-card>
      <v-app-bar :color="options.color" dense flat>
        <v-card-title>{{ title }}</v-card-title>
      </v-app-bar>
      <v-card-text class="pa-4">
        {{ message }}
      </v-card-text>
      <v-card-actions class="pt-0 px-4 pb-4">
        <v-spacer></v-spacer>
        <v-btn @click.native="cancel">취소</v-btn>
        <v-btn @click.native="agree" color="error">확인</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: 'ConfirmDialog',
  computed: {
    show: {
      get() {
        return this.dialog;
      },
      set(value) {
        this.dialog = value;
        if (value === false) {
          this.cancel();
        }
      },
    },
  },
  methods: {
    open(title, message, options) {
      this.dialog = true;
      this.title = title;
      this.message = message;
      this.options = Object.assign(this.options, options);
      return new Promise((resolve, reject) => {
        this.resolve = resolve;
        this.reject = reject;
      });
    },
    agree() {
      this.resolve(true);
      this.dialog = false;
    },
    cancel() {
      this.resolve(false);
      this.dialog = false;
    },
  },
  data() {
    return {
      dialog: false,
      resolve: null,
      reject: null,
      message: null,
      title: null,
      options: {
        width: 500,
        zIndex: 200,
      },
    };
  },
};
</script>
